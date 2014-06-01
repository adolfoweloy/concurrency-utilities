package br.com.devmedia.crawler.prodcons.executor;

public class ProdutorExecutor implements Runnable {
	private ControleProcessamentoExecutor controle;
	
	public ProdutorExecutor(ControleProcessamentoExecutor controle) {
		this.controle = controle;
	}

	@Override
	public void run() {
		for (int i = 0; i < controle.getDbLinkCount(); i++) {
			System.out.println(Thread.currentThread().getName() + 
				" adicionando " + controle.getDetalheDoLink(i));
			controle.addLinkPendente(i);
		}
		
		ControleProcessamentoExecutor.continuaProcessamento = false;
	}
	
}
