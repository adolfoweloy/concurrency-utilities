package br.com.devmedia.crawler.livros;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class CrawlerIterativo {
	private String link;
	
	public static void main(String[] args) {
		String link = "http://artigojavamagazine";
		CrawlerIterativo me = new CrawlerIterativo(link);
		me.start();
	}
	
	public CrawlerIterativo(String link) {
		this.link = link;
	}
	
	public void start() {
		
		try {
			URL url = new URL(link);
			Parser parser = new Parser(url.openConnection());
			NodeList list = parser.parse(new NodeClassFilter(LinkTag.class));
			SimpleNodeIterator iterator = list.elements();
			
			// percorre todas as categorias de livros
			while (iterator.hasMoreNodes()) {
				LinkTag linkLivrosCategoria = (LinkTag) iterator.nextNode();
				url = new URL(linkLivrosCategoria.extractLink());
				parser = new Parser(url.openConnection());
				
				// recupera a lista de livros da categoria selecionada
				list = parser.parse(new NodeClassFilter(LinkTag.class));

				// grava os dados dos livros da categoria selecionada
				gravarLivrosDetalhados(list.elements());
			}
			
		} catch (ParserException | IOException e) {
			e.printStackTrace();
		}
		
	}

	private List<Livro> gravarLivrosDetalhados(SimpleNodeIterator elements) 
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
			
			gravarLivro(livro);
		}
		
		return livros;
	}

	private void gravarLivro(Livro livro) {
		System.out.println("gravando o livro: " + livro.getTitulo());
	}
	
}