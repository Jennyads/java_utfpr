package br.edu.utfpr;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;


import static java.util.stream.Collectors.*;

public class SalesReader {

    private final List<Sale> sales;

    public SalesReader(String salesFile) {

        final var dataStream = ClassLoader.getSystemResourceAsStream(salesFile);

        if (dataStream == null) {
            throw new IllegalStateException("File not found or is empty");
        }

        final var builder = new CsvToBeanBuilder<Sale>(new InputStreamReader(dataStream, StandardCharsets.UTF_8));

        sales = builder
                .withType(Sale.class)
                .withSeparator(';')
                .build()
                .parse();
    }

    public BigDecimal totalOfCompletedSales() {
        return sales.stream()
                .filter(Sale::isCompleted)
                .map(Sale::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal totalOfCancelledSales() {
        return sales.stream()
                .filter(Sale::isCancelled)
                .map(Sale::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Optional<Sale> mostRecentCompletedSale() {
        return sales.stream()
                .filter(Sale::isCompleted)
                .max(Comparator.comparing(Sale::getDeliveryDate));
    }

    public long daysBetweenFirstAndLastCancelledSale() {
        List<LocalDate> dates = sales.stream()
                .filter(Sale::isCancelled)
                .map(Sale::getSaleDate)
                .sorted()
                .toList();
        if (dates.size() < 2) return 0;
        return ChronoUnit.DAYS.between(dates.get(0), dates.get(dates.size() - 1));
    }

    public BigDecimal totalCompletedSalesBySeller(String sellerName) {
        return sales.stream()
                .filter(Sale::isCompleted)
                .filter(s -> s.getSeller().equalsIgnoreCase(sellerName))
                .map(Sale::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long countAllSalesByManager(String managerName) {
        return sales.stream()
                .filter(s -> s.getManager().equalsIgnoreCase(managerName))
                .count();
    }

    public BigDecimal totalSalesByStatusAndMonth(Sale.Status status, Month... months) {
        List<Month> monthList = Arrays.asList(months);
        return sales.stream()
                .filter(s -> s.getStatus() == status)
                .filter(s -> monthList.contains(s.getSaleDate().getMonth()))
                .map(Sale::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<String, Long> countCompletedSalesByDepartment() {
        return sales.stream()
                .filter(Sale::isCompleted)
                .collect(groupingBy(Sale::getDepartment, counting()));
    }

    public Map<Integer, Map<String, Long>> countCompletedSalesByPaymentMethodAndGroupingByYear() {
        return sales.stream()
                .filter(Sale::isCompleted)
                .collect(groupingBy(
                        s -> s.getSaleDate().getYear(),
                        groupingBy(Sale::getPaymentMethod, counting())
                ));
    }

    public Map<String, BigDecimal> top3BestSellers() {
        return sales.stream()
                .filter(Sale::isCompleted)
                .collect(groupingBy(Sale::getSeller, reducing(BigDecimal.ZERO, Sale::getValue, BigDecimal::add)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    public Map<String, BigDecimal> bottom3WorstSellers() {
        return sales.stream()
                .filter(Sale::isCompleted)
                .collect(Collectors.groupingBy(
                        Sale::getSeller,
                        Collectors.reducing(BigDecimal.ZERO, Sale::getValue, BigDecimal::add)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue()) // crescente = piores primeiro
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    // Q1) 3 piores vendedores (menor faturamento entre vendas COMPLETED)
    public Map<String, BigDecimal> worst3Sellers() {
        return sales.stream()
                .filter(Sale::isCompleted)
                .collect(Collectors.groupingBy(
                        Sale::getSeller,
                        Collectors.reducing(BigDecimal.ZERO, Sale::getValue, BigDecimal::add)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue()) // ascendente (piores primeiro)
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a,b) -> a,
                        LinkedHashMap::new
                ));
    }

    // Q2) Total de vendas COMPLETED no estado (UF) e nos meses informados
    public BigDecimal totalCompletedSalesInStateAndMonths(String uf, Month... months) {
        Set<Month> monthSet = new HashSet<>(Arrays.asList(months));
        return sales.stream()
                .filter(Sale::isCompleted)
                .filter(s -> s.getEstate() != null && s.getEstate().equalsIgnoreCase(uf))
                .filter(s -> s.getSaleDate() != null && monthSet.contains(s.getSaleDate().getMonth()))
                .map(Sale::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Q3) Total de vendas COMPLETED entre as datas informadas
    public long countSalesBySellerBetweenDates(String sellerName, LocalDate start, LocalDate end) {
        return sales.stream()
                .filter(s -> s.getSeller().equalsIgnoreCase(sellerName))
                .filter(s -> s.getSaleDate() != null &&
                        !s.getSaleDate().isBefore(start) &&
                        !s.getSaleDate().isAfter(end))
                .count();
    }

    // Q4) Total de vendas COMPLETED por ano
    public Map<Integer, BigDecimal> totalCompletedSalesByYear() {
        return sales.stream()
                .filter(Sale::isCompleted)
                .collect(Collectors.groupingBy(
                        s -> s.getSaleDate().getYear(),
                        Collectors.reducing(BigDecimal.ZERO, Sale::getValue, BigDecimal::add)
                ));
    }

    // Q5) Gerente com mais vendas canceladas
    public String managerWithMostCancellationsBetweenYears(int startYear, int endYear) {
        return sales.stream()
                .filter(Sale::isCancelled)
                .filter(s -> {
                    int year = s.getSaleDate().getYear();
                    return year >= startYear && year <= endYear;
                })
                .collect(Collectors.groupingBy(Sale::getManager, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Nenhum gerente encontrado");
    }






}
