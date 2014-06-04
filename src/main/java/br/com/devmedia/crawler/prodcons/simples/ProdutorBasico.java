package br.com.devmedia.crawler.prodcons.simples;

import java.util.List;
import java.util.Queue;
import java.util.Random;

import br.com.devmedia.crawler.prodcons.model.DadosLink;

public class ProdutorBasico implements Runnable {
   private Queue<DadosLink> filaTrabalho;
   private List<DadosLink> linksDb;
   public static boolean continuaProcessamento = true;
   
   public ProdutorBasico(Queue<DadosLink> filaTrabalho, List<DadosLink> linksDb) {
      this.filaTrabalho = filaTrabalho;
      this.linksDb = linksDb;
   }

   @Override
   public void run() {
      Random random = new Random();
      
      for (DadosLink link : linksDb) {
         
         try {
            Thread.sleep(random.nextInt(3));
         } catch (InterruptedException e) {}
         
         synchronized (filaTrabalho) {
            System.out.println(Thread.currentThread().getName() + 
               " adicionando o link " + link.getLink());
            filaTrabalho.add(link);
            filaTrabalho.notifyAll();
         }
      }
      
      continuaProcessamento = false;
   }
   
}
