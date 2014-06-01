package br.com.devmedia.crawler.prodcons.simples;

public class TesteProdutorConsumidorBasico {
   public static void main(String[] args) throws InterruptedException {
      ControleProcessamento controleProcessamento = new ControleProcessamento(100);
      
      // criando o produtor e consumidores
      new Thread(new ProdutorBasico(controleProcessamento)).start();
      
      for (int i=0; i<3; i++)
         new Thread(new ConsumidorBasico(controleProcessamento)).start();
      
      while (ControleProcessamento.continuaProcessamento == true ||
         controleProcessamento.getTotalPendente() > 0) {
         Thread.sleep(500);
         System.out.println(Thread.currentThread().getName() + " aguardando...");
      }
      
      System.out.printf("Total processado: %d", 
            controleProcessamento.getTotalProcessados());
   }
}
