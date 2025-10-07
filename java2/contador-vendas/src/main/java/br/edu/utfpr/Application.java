package br.edu.utfpr;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;

public class Application {

    public static final String SELLER = "Adriana Gomes";
    public static final String MANAGER = "Elenice Mendes";

    public Application() {
        startReading();
    }

    private void startReading() {

        final SalesReader reader = new SalesReader("dados.csv");

        final var totalOfCompletedSales = reader.totalOfCompletedSales();
        System.out.printf("O valor total de vendas completadas foi de %s%n", toCurrency(totalOfCompletedSales));

        final var totalOfCancelledSales = reader.totalOfCancelledSales();
        System.out.printf("O valor total de vendas canceladas foi de %s%n", toCurrency(totalOfCancelledSales));

        reader.mostRecentCompletedSale()
                .ifPresent(sale -> System.out.printf("Venda mais recente foi realizada em %s%n", sale.getSaleDate()));

        final var daysBetweenFirstAndLastSale = reader.daysBetweenFirstAndLastCancelledSale();
        System.out.printf("Se passaram %s dias entre a primeira e a ultima venda cancelada%n", daysBetweenFirstAndLastSale);

        final var totalSalesBySeller = reader.totalCompletedSalesBySeller(SELLER);
        System.out.printf("A vendedora %s totalizou %s em vendas%n", SELLER, toCurrency(totalSalesBySeller));

        final var countOfSalesByManager = reader.countAllSalesByManager(MANAGER);
        System.out.printf("A equipe do gerente %s realizou %s vendas%n", MANAGER, countOfSalesByManager);

        final var totalSalesByStatusAndMonth = reader.totalSalesByStatusAndMonth(Sale.Status.COMPLETED, Month.JULY, Month.SEPTEMBER);
        System.out.printf("As venda com o status no mes indicado somaram %s%n", toCurrency(totalSalesByStatusAndMonth));

        System.out.println("-------------------");
        System.out.println("Contagem de vendas por departamento\n");

        final var salesCountByDepartment = reader.countCompletedSalesByDepartment();
        salesCountByDepartment.forEach((key, value) -> System.out.printf("Departamento %s teve %s vendas", key, value).println());

        System.out.println("-------------------");
        System.out.println("Contagem de vendas por meio de pagamento agrupadas por ano\n");

        final var salesCountByPaymentMethodByYear = reader.countCompletedSalesByPaymentMethodAndGroupingByYear();
        salesCountByPaymentMethodByYear.keySet()
                .forEach(year -> {
                    System.out.println("> No ano de " + year);
                    salesCountByPaymentMethodByYear.get(year)
                            .forEach((key, value) -> System.out.printf("Meio de pagamento %s teve %s vendas", key, value).println());
                });

        System.out.println("-------------------");
        System.out.println("Top 3 de vendedores\n");

        final var topThreeSellers = reader.top3BestSellers();
        topThreeSellers.forEach((key, value) -> System.out.printf("%s com %s em vendas", key, this.toCurrency(value)).println());

        // --- Q1: nomes dos 3 piores vendedores
        var worst = reader.worst3Sellers();
        System.out.println("3 piores vendedores:");
        worst.forEach((seller, total) ->
                System.out.printf("- %s com %s em vendas%n", seller, toCurrency(total)));

        // --- Q2: total COMPLETED no PR em JAN, JUN, DEC
        var totalPR = reader.totalCompletedSalesInStateAndMonths(
                "PR", Month.JANUARY, Month.JUNE, Month.DECEMBER);
        System.out.printf("Total COMPLETED no PR (Jan, Jun, Dez): %s%n", toCurrency(totalPR));

        // --- Q3: vendas por periodo
        var seller = "Rosângela Almeida";
        var start = LocalDate.of(2012, 5, 12);
        var end = LocalDate.of(2012, 7, 29);

        var qtd = reader.countSalesBySellerBetweenDates(seller, start, end);
        System.out.printf("Entre %s e %s a vendedora %s realizou %d vendas%n",
                start, end, seller, qtd);

        // --- Q4: total de vendas por ano
        var totalsByYear = reader.totalCompletedSalesByYear();
        totalsByYear.forEach((year, total) -> {
            System.out.printf("Ano: %d | Total de vendas: %s%n", year, this.toCurrency(total));
            if (total.compareTo(new BigDecimal("3689379.28")) == 0) {
                System.out.printf(">> Encontrado: O ano com R$ 3.689.379,28 em vendas foi %d%n", year);
            }
        });

        // --- Q5: gerente com mais cancelamentos
        var gerente = reader.managerWithMostCancellationsBetweenYears(2013, 2014);
        System.out.printf("Entre 2013 e 2014, o gerente com mais cancelamentos foi: %s%n", gerente);










    }

    private String toCurrency(BigDecimal value) {
        return NumberFormat.getInstance().format(value);
    }

    public static void main(String[] args) {
        new Application();
    }
}
