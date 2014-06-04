package br.com.devmedia.crawler.livros;

public class Livro {
   private String categoria;
   private String titulo;
   private double preco;
   
   public Livro(String categoria, String titulo, double preco) {
      super();
      this.categoria = categoria;
      this.titulo = titulo;
      this.preco = preco;
   }

   public String getCategoria() {
      return categoria;
   }

   public String getTitulo() {
      return titulo;
   }

   public double getPreco() {
      return preco;
   }

}
