package br.com.devmedia.media;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import br.com.devmedia.media.Boletim.Nota;

/**
 * @author Adolfo Eloy
 * 
 * O projeto trata de um exemplo simples de utilização da técnica dividir para conquistar.
 * Nesse exemplo, é calculada a média das notas de todos alunos (quantidade definida através da constante
 * QUANTIDADE_ALUNOS). As médias de todos os alunos são somadas e a média geral é obtida.
 * 
 * Essa classe, tem a responsabilidade de testar a execução do cálculo descrito, utilizando 2 métodos:
 * - método simples através de iteração O(N^2)
 * - método utilizando o framework Fork/Join 
 * 
 * Após a execução dos diferentes métodos, o tempo total para execução é calculado.
 * Através dessa diferença entre os tempos é possível observar os ganhos de utilização do framework Fork/Join.
 */
public class TesteMediasMain {
	
	public static final int QUANTIDADE_ALUNOS = 1000000;
	public static final String[] materias = new String[] {
		"PORTUGUES", "MATEMATICA", "ESTATISTICA", 
		"LOGICA", "FILOSOFIA", "FISICA", "GEOGRAFIA", 
		"HISTORIA", "ED.ARTISTICA", "PROGRAMACAO", "TAE KWON DO"
	};
	
	public static void main(String[] args) {
		List<Boletim> boletins = notasEscola(QUANTIDADE_ALUNOS, Mes.DEZEMBRO);
		MediaNotasTask task = new MediaNotasTask(boletins, 0, QUANTIDADE_ALUNOS);
		
		long start, end;
		
		// calcula a media das medias sem utilizar threads
		start = System.currentTimeMillis();
		mediaSemForkJoin(boletins);
		end = System.currentTimeMillis();
		System.out.printf("tempo sem f/j: %d\n", end - start);
		
		// calcula a media utilizando threads com a tecnica fork/join
		start = System.currentTimeMillis();
		mediaComForkJoin(task);
		end = System.currentTimeMillis();
		System.out.printf("tempo com f/j: %d\n", end - start);
	}

	/**
	 * Método que calcula a média utilizando o framework Fork/Join.
	 * Dessa maneira o calculo das médias dos alunos é dividido entre
	 * as worker threads através da técnica dividir para conquistar.
	 * 
	 * @param task
	 */
	private static void mediaComForkJoin(MediaNotasTask task) {
		ForkJoinPool pool = new ForkJoinPool();
		pool.execute(task);
		
		while (true) {
			if (task.isDone()) {
				pool.shutdown();
				
				try {
					double mediaTotal = task.get() / QUANTIDADE_ALUNOS;
					System.out.printf("[fork/join] resultado: %.2f\n", mediaTotal);
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Este método exemplifica o cálculo da média das médias sem a utilização 
	 * do framework Fork/Join
	 * @param boletins
	 */
	private static void mediaSemForkJoin(List<Boletim> boletins) {
		double somaTotal = 0.0;
		for (Boletim b : boletins) {
			List<Nota> notas = b.getNotas();
			double mediaAluno = 0.0;
			for (Nota n : notas) {
				mediaAluno += n.getNota();
			}
			somaTotal += (mediaAluno / notas.size());
		}
		System.out.printf("[sem fork/join] resultado: %.2f\n", somaTotal / boletins.size());
	}
	
	/**
	 * Cria uma quantidade especifica de boletins com notas 
	 * aleatórias para a execução dos testes.
	 *  
	 * @param qtd  quantidade de alunos
	 * @param mes  mes para fechamento das notas da escola
	 * @return List<Boletim> Lista de boletins da escola
	 */
	private static List<Boletim> notasEscola(int qtd, Mes mes) {
		Random random = new Random();
		List<Boletim> boletins = new ArrayList<>();
		
		for (int i=0; i<qtd; i++) {
			Boletim boletim = new Boletim(new Aluno("Aluno " + i, i), mes);
			for (int j=0; j<materias.length; j++) {
				boletim.getNotas().add(
					boletim.new Nota(random.nextInt(10), materias[j]));
			}
			boletins.add(boletim);
		}
		
		return boletins;
	}
}
