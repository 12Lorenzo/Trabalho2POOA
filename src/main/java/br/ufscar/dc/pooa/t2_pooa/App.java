package br.ufscar.dc.pooa.t2_pooa;

import java.io.IOException;

import br.ufscar.dc.pooa.t2_pooa.algorithms.NewsToCsv;
import br.ufscar.dc.pooa.t2_pooa.parser.EstadaoParser;
import br.ufscar.dc.pooa.t2_pooa.parser.GloboParser;
import br.ufscar.dc.pooa.t2_pooa.parser.UolParser;

public class App {
    public static void main(String[] args) throws IOException {
    	GloboParser globo = new GloboParser();
    	NewsToCsv globoNewsToCsv = new NewsToCsv("globo", globo.getNews());
    	globoNewsToCsv.execute();
    	
    	UolParser uol = new UolParser();
    	NewsToCsv uolNewsToCsv = new NewsToCsv("uol", uol.getNews());
    	uolNewsToCsv.execute();
    	
    	EstadaoParser estadao = new EstadaoParser();
    	NewsToCsv estadaoNewsToCsv = new NewsToCsv("estadao", estadao.getNews());
    	estadaoNewsToCsv.execute();
    }
}