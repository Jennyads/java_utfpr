package com.utfpr.corrida;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class CorridaApplication {

	private static final int NUM_COMPETIDORES = 10;
	private static final int NUM_CORRIDAS = 10;

	private static final int[] pontuacaoGeral = new int[NUM_COMPETIDORES];

	public static void main(String[] args) {
		System.out.println("Campeonato de Corrida de Motos");

		for (int corrida = 1; corrida <= NUM_CORRIDAS; corrida++) {
			System.out.printf("%nIniciando Corrida %d...%n", corrida);

			CountDownLatch latch = new CountDownLatch(NUM_COMPETIDORES);
			Corrida corridaAtual = new Corrida(latch);

			for (int i = 0; i < NUM_COMPETIDORES; i++) {
				String nome = "Competidor #" + (i + 1);
				Thread competidor = new Thread(new Competidor(i, nome, corridaAtual));
				competidor.start();
			}

			try {
				latch.await();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Corrida interrompida.");
			}

			System.out.printf("Corrida %d finalizada%n", corrida);
		}

		System.out.println("\nPlacar final:");
		for (int i = 0; i < NUM_COMPETIDORES; i++) {
			System.out.printf("Competidor #%d: %d pontos%n", i + 1, pontuacaoGeral[i]);
		}

		System.out.println("\nPódio do Campeonato:");
		Integer[] ranking = gerarRanking();
		for (int i = 0; i < 3; i++) {
			int idx = ranking[i];
			System.out.printf("%dº lugar: Competidor #%d com %d pontos%n", i + 1, idx + 1, pontuacaoGeral[idx]);
		}
	}

	private static class Corrida {
		private int ordemChegada = 0;
		private final CountDownLatch latch;

		public Corrida(CountDownLatch latch) {
			this.latch = latch;
		}

		public void cruzarLinhaChegada(int id, String nome) {
			synchronized (this) {
				ordemChegada++;
				int pontos = 11 - ordemChegada;
				pontuacaoGeral[id] += pontos;
				System.out.printf("%s chegou em %dº lugar e ganhou %d pontos%n", nome, ordemChegada, pontos);
			}
			latch.countDown();
		}
	}

	private static class Competidor implements Runnable {
		private final int id;
		private final String nome;
		private final Corrida corrida;

		public Competidor(int id, String nome, Corrida corrida) {
			this.id = id;
			this.nome = nome;
			this.corrida = corrida;
		}

		@Override
		public void run() {
			try {
				Thread.sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			corrida.cruzarLinhaChegada(id, nome);
		}
	}

	private static Integer[] gerarRanking() {
		Integer[] ranking = new Integer[NUM_COMPETIDORES];
		for (int i = 0; i < NUM_COMPETIDORES; i++) {
			ranking[i] = i;
		}
		Arrays.sort(ranking, (a, b) -> Integer.compare(pontuacaoGeral[b], pontuacaoGeral[a]));
		return ranking;
	}
}
