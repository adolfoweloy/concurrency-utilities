package br.com.devmedia.media;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

import br.com.devmedia.media.Boletim.Nota;

public class MediaNotasTask extends RecursiveTask<Double> {

	private static final long serialVersionUID = 92163253552120677L;
	private final List<Boletim> boletins;
	private final int inicio, fim;
	
	public MediaNotasTask(List<Boletim> boletins, int inicio, int fim) {
		this.boletins = boletins;
		this.inicio = inicio;
		this.fim = fim;
	}

	@Override
	protected Double compute() {
		double resultado = 0.0;
		
		if (fim - inicio < 10) {
			resultado = somaMediaDosAlunos();
		} else {
			int meio = (inicio + fim) / 2;
			MediaNotasTask t1 = new MediaNotasTask(boletins, inicio, meio);
			MediaNotasTask t2 = new MediaNotasTask(boletins, meio, fim);
			invokeAll(t1, t2);
			
			try {
				resultado = t1.get() + t2.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		return resultado;
	}

	private double somaMediaDosAlunos() {
		double soma = 0.0;
		for (int i=inicio; i<fim; i++) {
			double mediaAluno = 0.0;
			List<Nota> notas = boletins.get(i).getNotas();
			for (Nota nota : notas) {
				mediaAluno += nota.getNota();
			}
			mediaAluno = mediaAluno / notas.size();
			soma += mediaAluno;
		}
		return soma;
	}
	
}