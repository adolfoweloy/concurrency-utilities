package br.com.devmedia.crawler.prodcons.executor;

public class ConsumidorExecutor implements Runnable {
	private ControleProcessamentoExecutor controle;
	
	public ConsumidorExecutor(ControleProcessamentoExecutor controle) {
		this.controle = controle;
	}

	@Override
	public void run() {
		while (ControleProcessamentoExecutor.continuaProcessamento 
				|| controle.getTotalPendente() > 0) {
			Integer id = controle.getLinkPendente();
			processarLink(id);
		}
	}

	private void processarLink(Integer id) {
		String det = controle.getDetalheDoLink(id);
		System.out.println(Thread.currentThread().getName() + " processando " + det);
		controle.addLinkProcessado(id);
	}

}
