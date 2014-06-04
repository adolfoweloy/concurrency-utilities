package br.com.devmedia.countchars;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ContadorSequencial {
   public static void main(String[] args) {
      
      if (args.length != 1) {
         System.out.println("Usage: ContadorSequencial <caminho do arquivo>");
         System.exit(0);
      }
      
      String path = args[0];
      FileInputStream fis = null;
      
      try {
         fis = new FileInputStream(new File(path));
         int character, contador = 0;
         
         while ((character = fis.read()) != -1) {
            if (character != 32) 
               contador ++;
         }
         
         System.out.printf("quantidade de palavras: %d\n", contador);
      } catch (IOException e) {
         System.out.println(e);
      } finally {
         if (fis != null) {
            try {
               fis.close();
            } catch (IOException e) {
               System.out.println(e);
            }
         }
      }
   }
}
