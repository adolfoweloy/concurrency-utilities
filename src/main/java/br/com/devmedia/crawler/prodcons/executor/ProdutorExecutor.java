package br.com.devmedia.crawler.prodcons.executor;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import br.com.devmedia.crawler.prodcons.model.DadosLink;

public class ProdutorExecutor implements Runnable {
   public static boolean continuaProcessamento = true;
   private BlockingQueue<DadosLink> filaTrabalho;
   private List<DadosLink> linksDb;
   
   public ProdutorExecutor(BlockingQueue<DadosLink> filaTrabalho, 
      List<DadosLink> linksDb) {
      this.filaTrabalho = filaTrabalho;
      this.linksDb = linksDb;
   }

   @Override
   public void run() {
      Random random = new Random();
      
      for (DadosLink link : linksDb) {
         try {
            Thread.sleep(random.nextInt(3));
         } catch (InterruptedException e1) {
            // TODO - adicionar tratamento adequado
         }
         
         System.out.println(Thread.currentThread().getName() + 
            " adicionando " + link.getLink());
         try {
            filaTrabalho.put(link);
         } catch (InterruptedException e) { 
            // TODO - adicionar tratamento adequado
         }
      }
      
      continuaProcessamento = false;
   }
   
}
