package br.com.devmedia.crawler.prodcons.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import br.com.devmedia.crawler.prodcons.model.DadosLink;

public class ConsumidorExecutor implements Runnable {
   private BlockingQueue<DadosLink> filaTrabalho;
   
   public ConsumidorExecutor(BlockingQueue<DadosLink> filaTrabalho) {
      this.filaTrabalho = filaTrabalho;
   }

   @Override
   public void run() {
      while (ProdutorExecutor.continuaProcessamento || !filaTrabalho.isEmpty()) {
         DadosLink link;
         try {
            link = filaTrabalho.poll(5, TimeUnit.SECONDS);
            if (link == null) return;
            
            processarLink(link);
         } catch (InterruptedException e) {
            // adicionar tratamento adequado
         }
      }
   }

   private void processarLink(DadosLink link) {
      System.out.println(Thread.currentThread().getName() + 
         " processando " + link.getLink());
   }

}
