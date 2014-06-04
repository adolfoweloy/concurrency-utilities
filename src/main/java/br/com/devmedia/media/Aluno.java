package br.com.devmedia.media;

public class Aluno {
   private final String nome;
   private final long matricula;
   
   public Aluno(String nome, long matricula) {
      super();
      this.nome = nome;
      this.matricula = matricula;
   }

   public String getNome() {
      return nome;
   }

   public long getMatricula() {
      return matricula;
   }
   
}
