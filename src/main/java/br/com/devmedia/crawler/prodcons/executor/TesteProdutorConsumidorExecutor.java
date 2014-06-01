package br.com.devmedia.crawler.prodcons.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TesteProdutorConsumidorExecutor {
	public static void main(String[] args) {
		ControleProcessamentoExecutor controleProcessamento = new ControleProcessamentoExecutor(100);
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(new ProdutorExecutor(controleProcessamento));
		executor.execute(new ConsumidorExecutor(controleProcessamento));

		try {
			executor.awaitTermination(1, TimeUnit.SECONDS);
			System.out.printf("Total processado: %d", controleProcessamento.getTotalProcessados());
		} catch (InterruptedException e) {
		}
		
		executor.shutdown();
	}
}
