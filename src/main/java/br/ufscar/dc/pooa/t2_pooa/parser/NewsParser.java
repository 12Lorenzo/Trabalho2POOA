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

	public List<News> getNews() throws IOException{
		List<News> newsList = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();
        Elements titles = doc.select(titleClass);
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
