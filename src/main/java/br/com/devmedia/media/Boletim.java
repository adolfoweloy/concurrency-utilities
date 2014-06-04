package br.com.devmedia.media;

import java.util.ArrayList;
import java.util.List;

public class Boletim {
   private final Aluno aluno;
   private final Mes mes;
   private final List<Nota> notas;
   
   public Boletim(Aluno aluno, Mes mes) {
      this.aluno = aluno;
      this.mes = mes;
      notas = new ArrayList<>();
   }
   
   public class Nota {
      private double nota;
      private String materia;
      
      public Nota(double nota, String materia) {
         this.nota = nota;
         this.materia = materia;
      }

      public double getNota() {
         return nota;
      }

      public void setNota(double nota) {
         this.nota = nota;
      }

      public String getMateria() {
         return materia;
      }

      public void setMateria(String materia) {
         this.materia = materia;
      }
   }

   public Mes getMes() {
      return mes;
   }

   public List<Nota> getNotas() {
      return notas;
   }
   
   public Aluno getAluno() {
      return aluno;
   }
}
