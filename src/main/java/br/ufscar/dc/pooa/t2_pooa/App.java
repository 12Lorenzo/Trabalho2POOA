package br.ufscar.dc.pooa.t2_pooa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static List<SiteNews> getSites() throws IOException {
    	List<SiteNews> sites = new ArrayList<>();
    	 
    	SiteNews globo = new SiteNews("Globo", "https://www.globo.com", ".post__title");
    	SiteNews uol = new SiteNews("Uol", "https://www.uol.com.br", "h2.titulo");
    	SiteNews estadao = new SiteNews("Estadão", "https://www.estadao.com.br", "h3.title a");
    	
    	sites.add(globo);
    	sites.add(uol);
    	sites.add(estadao);
    	
    	return sites;
    }
	
    public static void main(String[] args) throws IOException {
    	List<SiteNews> sites = getSites();    	
    	List<News> newsList = NewsParser.getNews(sites);
    	Functions.newsToCsv(newsList);
    }
}