package br.ufscar.dc.pooa.t2_pooa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewsParser {
    public static List<News> getNews(String link, String className) throws IOException{
    	List<News> newsList = new ArrayList<>();
        Document doc = Jsoup.connect(link).get();
        Elements titles = doc.select(className);
        for (Element t : titles) {
        	String newsTitle = t.text();
        	String newsLink = null;
        	
            Element parent = t.parent();
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
