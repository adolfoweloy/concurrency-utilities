package br.com.devmedia.crawler.prodcons.simples;

import java.util.Queue;

import br.com.devmedia.crawler.prodcons.model.DadosLink;

public class ConsumidorBasico implements Runnable {
   private Queue<DadosLink> filaTrabalho;

   public ConsumidorBasico(Queue<DadosLink> filaTrabalho) {
      this.filaTrabalho = filaTrabalho;
   }

   @Override
   public void run() {
      while (true) {
         DadosLink item = null;
         synchronized (filaTrabalho) {
            while (filaTrabalho.isEmpty() && ProdutorBasico.continuaProcessamento) {
               try {
                  filaTrabalho.wait();
               } catch (InterruptedException e) { }
            }
            
            if (filaTrabalho.isEmpty()) return;
            item = filaTrabalho.poll();
         }

         processarLink(item);
      }
   }

   private void processarLink(DadosLink link) {
      System.out.println(Thread.currentThread().getName() + " processando "
            + link.getLink());
   }

}
