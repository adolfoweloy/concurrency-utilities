package br.com.devmedia.crawler.prodcons.model;

public class DadosLink {
   private String link;
   private String nomeUsuario;
   private String email;
   
   public DadosLink(String link, String nomeUsuario, String email) {
      super();
      this.link = link;
      this.nomeUsuario = nomeUsuario;
      this.email = email;
   }

   public String getLink() {
      return link;
   }

   public void setLink(String link) {
      this.link = link;
   }

   public String getNomeUsuario() {
      return nomeUsuario;
   }

   public void setNomeUsuario(String nomeUsuario) {
      this.nomeUsuario = nomeUsuario;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }
   
}
