package br.com.devmedia.crawler.livros;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class CrawlerExecutor {

	public static void main(String[] args) {
		String link = "http://artigojavamagazine";
		CrawlerExecutor me = new CrawlerExecutor();
		me.start(link);

	}
	private class TaskLivros implements Callable<List<Livro>> {

		private LinkTag link;
		
		public TaskLivros(LinkTag link) {
			this.link = link;
		}
		
		@Override 
		public List<Livro> call() throws Exception {
			URL url = new URL(link.extractLink());
			Parser parser = new Parser(url.openConnection());
			
			// recupera a lista de livros da categoria selecionada
			NodeList nodeList = parser.parse(new NodeClassFilter(LinkTag.class));
			return recuperarDetalhes(nodeList.elements());
		}
		
		private List<Livro> recuperarDetalhes(SimpleNodeIterator elements) 
			throws ParserException, IOException {
			List<Livro> livros = new ArrayList<>();
			
			while (elements.hasMoreNodes()) {
				LinkTag node = (LinkTag) elements.nextNode();
				URL url = new URL(node.extractLink());
				Parser parser = new Parser(url.openConnection());
				NodeList list = parser.parse(new TagNameFilter("li"));
				
				String categoria = list.elementAt(0).getLastChild().getText();
				String titulo    = list.elementAt(1).getLastChild().getText();
				String preco 	 = list.elementAt(2).getLastChild().getText();
				
				Livro livro = new Livro(categoria, titulo, Double.parseDouble(preco));
				livros.add(livro);
				
			}
			
			return livros;
		}
	}
	
	public void start(String link) {
		
		List<Future<List<Livro>>> futures = new ArrayList<>();
		
		try {
			URL url = new URL(link);
			Parser parser = new Parser(url.openConnection());
			NodeList list = parser.parse(new NodeClassFilter(LinkTag.class));
			SimpleNodeIterator categorias = list.elements();
			
			ExecutorService pool = Executors.newFixedThreadPool(100);
			while (categorias.hasMoreNodes()) {
				futures.add(pool.submit(
					new TaskLivros((LinkTag) categorias.nextNode())));
			}
			
			for (Future<List<Livro>> f : futures) {
				List<Livro> livros = f.get();
				for (Livro livro : livros) {
					System.out.println(livro.getTitulo());
				}
			}
			
			pool.shutdown();
		} catch (ParserException | IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
	}
}
