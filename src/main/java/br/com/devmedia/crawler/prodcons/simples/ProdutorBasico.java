package br.com.devmedia.crawler.prodcons.simples;

import java.util.Random;

public class ProdutorBasico implements Runnable {
   private ControleProcessamento controle;
   
   public ProdutorBasico(ControleProcessamento controle) {
      this.controle = controle;
   }

   @Override
   public void run() {
      Random random = new Random();
      
      for (int i = 0; i < controle.getDbLinkCount(); i++) {
         
         // aguarda por um periodo de 0 a 2 segundos
         try {
            Thread.sleep(random.nextInt(3));
         } catch (InterruptedException e) {}
         
         // adiciona o id de um link na fila para processamento.
         synchronized (controle) {
            System.out.println(Thread.currentThread().getName() + 
               " adicionando " + controle.getDetalheDoLink(i));
            controle.addLinkPendente(i);
         }
      }
      
      // finaliza o processamento dos links
      ControleProcessamento.continuaProcessamento = false;
      
   }
   
}
