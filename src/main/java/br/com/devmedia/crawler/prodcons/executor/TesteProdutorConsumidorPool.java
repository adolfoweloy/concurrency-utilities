package br.com.devmedia.crawler.prodcons.executor;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import br.com.devmedia.crawler.prodcons.model.DadosLink;
import br.com.devmedia.crawler.prodcons.model.DaoCrawler;

public class TesteProdutorConsumidorPool {
   public static void main(String[] args) throws InterruptedException {
      BlockingQueue<DadosLink> filaTrabalho = new LinkedBlockingQueue<>();
      List<DadosLink> linksDb = new DaoCrawler().getLinksCapturados(10);
      
      int corePoolSize = 10;
      int maximumPoolSize = 15;
      long keepAliveTime = 5L;
      BlockingQueue<Runnable> filaTrabalhoPool = new LinkedBlockingQueue<>();
      
      ExecutorService executor = new ThreadPoolExecutor(
      	corePoolSize, maximumPoolSize, keepAliveTime, 
      	TimeUnit.SECONDS, filaTrabalhoPool);
      
      executor.execute(new ProdutorExecutor(
         filaTrabalho, Collections.synchronizedList(linksDb)));
   	executor.execute(new ConsumidorExecutor(filaTrabalho));
      
      executor.shutdown();
   }
}
