package br.ufscar.dc.pooa.t2_pooa.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.ufscar.dc.pooa.t2_pooa.News;

public abstract class NewsParser {
	
	protected String url;
	protected String titleClass;

	// Método padrão para o parsing e coleta de notícias de um site
	// Apesar de não servir para todos os casos, serve para a maioria deles
	// Pode ser reimplementado por cada subclasse para atender suas necessidades
	public List<News> getNews() throws IOException{
		List<News> newsList = new ArrayList<>();
		
		// Se conecta ao site com a url passada
        Document doc = Jsoup.connect(url).get();
        
        // Pega o conteúdo das classe dos títulos
        Elements titles = doc.select(titleClass);
        
        // Percorre todos os títulos encontrados e guarda o título e o link da notícia
        for (Element t : titles) {
        	String newsTitle = t.text();
        	String newsLink = null;
        	
            Element parent = t;
            while (parent != null && !parent.tagName().equals("a")) {
                parent = parent.parent();
            }
            if (parent != null && parent.tagName().equals("a")) {
                newsLink = String.format("\"%s\"", parent.attr("href"));
            }
            News news = new News(newsTitle, newsLink);
            newsList.add(news);
        }
        return newsList;
	}
}
