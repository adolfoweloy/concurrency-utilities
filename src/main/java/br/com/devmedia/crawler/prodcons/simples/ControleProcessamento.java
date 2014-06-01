package br.com.devmedia.crawler.prodcons.simples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class ControleProcessamento {
   /** representa uma base de dados ficticia com dados de links recuperados por um spider */
   private Map<Integer, String> dbLinks;
   
   /** links pendentes de processamento (fila de trabalho) */
   private Queue<Integer> linksPendentes = new PriorityQueue<>();
   
   /** links ja processados por um crawler ficticio */
   private List<Integer> linksProcessados = new ArrayList<>();
   
   /** flag que indica se deve ou nao continuar processando links */
   public static boolean continuaProcessamento = true;
   
   public ControleProcessamento(int quantidadeLinks) {
      dbLinks = new HashMap<>();
      for (int i = 0; i < quantidadeLinks; i++)
         dbLinks.put(i, "http://redesocial.com/amigos/" + i);
   }
   
   public String getDetalheDoLink(int idLink) {
      return dbLinks.get(idLink);
   }

   public int getDbLinkCount() {
      return dbLinks.size();
   }
   
   public void addLinkPendente(Integer id) {
      linksPendentes.add(id);
   }
   
   public Integer getLinkPendente() {
      return linksPendentes.poll();
   }
   
   public void addLinkProcessado(Integer id) {
      linksProcessados.add(id);
   }
   
   public int getTotalProcessados() {
      return linksProcessados.size();
   }
   
   public int getTotalPendente() {
      return linksPendentes.size();
   }
}
