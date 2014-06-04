package br.com.devmedia.crawler.prodcons.model;

import java.util.ArrayList;
import java.util.List;

public class DaoCrawler {

   public List<DadosLink> getLinksCapturados(int qtd) {
      List<DadosLink> r = new ArrayList<>();
      for (int i = 0; i < qtd; i++)
            r.add(new DadosLink(
               "http://redesocial.com/amigos/" + i, 
               "fulano " + i, 
               "fulano" + i + "@ymail.com"));

      return r;
   }

}
