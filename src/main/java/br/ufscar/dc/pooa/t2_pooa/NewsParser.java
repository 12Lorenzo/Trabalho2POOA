package br.ufscar.dc.pooa.t2_pooa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewsParser {
    public static List<News> getNews(List<SiteNews> sites) throws IOException{
    	List<News> newsList = new ArrayList<>();
    	for(SiteNews site : sites) {
	        Document doc = Jsoup.connect(site.getUrl()).get();
	        Elements titles = doc.select(site.getTitleClass());
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
	            News news = new News(site.getName(), newsTitle, newsLink);
	            newsList.add(news);
	        }
    	}
        return newsList;
    }
}
