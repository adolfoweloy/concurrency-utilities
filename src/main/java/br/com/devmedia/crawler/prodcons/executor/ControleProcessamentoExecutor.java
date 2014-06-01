package br.com.devmedia.crawler.prodcons.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ControleProcessamentoExecutor {
	public static boolean continuaProcessamento = true;
	private Map<Integer, String> dbLinks;
	private BlockingQueue<Integer> linksPendentes;
	private List<Integer> linksProcessados;
	
	public ControleProcessamentoExecutor(int quantidadeLinks) {
		dbLinks = new HashMap<>();
		for (int i = 0; i < quantidadeLinks; i++)
			dbLinks.put(i, "fulano de tal" + i);
		
		linksPendentes = new LinkedBlockingQueue<>();
		linksProcessados = Collections.synchronizedList(new ArrayList<Integer>());
	}
	
	public String getDetalheDoLink(int idLink) {
		return dbLinks.get(idLink);
	}

	public int getDbLinkCount() {
		return dbLinks.size();
	}
	
	public void addLinkPendente(Integer id) {
		try {
			linksPendentes.put(id);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Integer getLinkPendente() {
		try {
			return linksPendentes.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
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
