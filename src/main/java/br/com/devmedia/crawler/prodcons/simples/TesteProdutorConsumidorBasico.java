package br.com.devmedia.crawler.prodcons.simples;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.devmedia.crawler.prodcons.model.DadosLink;
import br.com.devmedia.crawler.prodcons.model.DaoCrawler;

public class TesteProdutorConsumidorBasico {
   public static void main(String[] args) throws InterruptedException {
      Queue<DadosLink> filaTrabalho = new LinkedList<>();
      List<DadosLink> linksDB = new DaoCrawler().getLinksCapturados(100);
      
      new Thread(new ProdutorBasico(filaTrabalho, linksDB)).start();
      
      for (int i=0; i<3; i++)
         new Thread(new ConsumidorBasico(filaTrabalho)).start();
   }
}
