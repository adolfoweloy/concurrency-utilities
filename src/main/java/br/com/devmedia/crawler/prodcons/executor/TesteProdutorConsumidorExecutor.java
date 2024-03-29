package br.com.devmedia.crawler.prodcons.executor;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import br.com.devmedia.crawler.prodcons.model.DadosLink;
import br.com.devmedia.crawler.prodcons.model.DaoCrawler;

public class TesteProdutorConsumidorExecutor {
   public static void main(String[] args) throws InterruptedException {
      BlockingQueue<DadosLink> filaTrabalho = new LinkedBlockingQueue<>();
      List<DadosLink> linksDb = new DaoCrawler().getLinksCapturados(10);
      
      ExecutorService executor = Executors.newSingleThreadExecutor();
      
      executor.execute(new ProdutorExecutor(
         filaTrabalho, Collections.synchronizedList(linksDb)));
   	executor.execute(new ConsumidorExecutor(filaTrabalho));
      
      executor.shutdown();
   }
}
