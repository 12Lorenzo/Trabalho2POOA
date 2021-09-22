package br.ufscar.dc.pooa.t2_pooa;

import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
    	List<News> newsList = NewsParser.getNews("https://www.globo.com", ".post__title");
    	Functions.newsToCsv(newsList);
    }
}