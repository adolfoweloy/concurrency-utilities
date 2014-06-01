package br.com.devmedia.crawler.prodcons.simples;

import java.util.Random;

public class ConsumidorBasico implements Runnable {
   private ControleProcessamento controle;

   public ConsumidorBasico(ControleProcessamento controle) {
      this.controle = controle;
   }

   @Override
   public void run() {
      Random random = new Random();

      while (ControleProcessamento.continuaProcessamento
            || controle.getTotalPendente() > 0) {

         try {
            Thread.sleep(random.nextInt(3));
         } catch (InterruptedException e) {}

         synchronized (controle) {
            if (controle.getTotalPendente() > 0) {
               Integer id = controle.getLinkPendente();
               processarLink(id);
            }
         }

      }
   }

   private void processarLink(Integer id) {
      String det = controle.getDetalheDoLink(id);
      System.out.println(Thread.currentThread().getName() + " processando "
            + det);
      controle.addLinkProcessado(id);
   }

}
