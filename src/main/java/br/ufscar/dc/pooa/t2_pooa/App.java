package br.ufscar.dc.pooa.t2_pooa;

import java.io.IOException;

import br.ufscar.dc.pooa.t2_pooa.algorithms.NewsToCsv;
import br.ufscar.dc.pooa.t2_pooa.parser.EstadaoParser;
import br.ufscar.dc.pooa.t2_pooa.parser.GloboParser;
import br.ufscar.dc.pooa.t2_pooa.parser.UolParser;

public class App {
	
	public static void allSitesToCsv() throws IOException {
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
	
    public static void main(String[] args) throws IOException {
    	// Se nenhum argumento for passado, os arquivo csv serão gerados, por padrão
    	if (args.length < 1) {
    		allSitesToCsv();
    	}
    	else {
    		// Caso um argumento tenha sido passado, verifica-se qual método deve ser rodado
    		switch(args[0]) {
	    		case "csv":
	    			allSitesToCsv();
	    			break;
	    		// Novos métodos devem ser chamados aqui
    			default:
    				System.out.println("Método " + args[0] + " não implementado" );
    				break;
    		}
    	}
    }
}