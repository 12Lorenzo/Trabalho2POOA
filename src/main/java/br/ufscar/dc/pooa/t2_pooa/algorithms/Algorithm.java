package br.ufscar.dc.pooa.t2_pooa.algorithms;

import java.io.IOException;
import java.util.List;

import br.ufscar.dc.pooa.t2_pooa.News;

public abstract class Algorithm {
	protected List<News> newsList;
	
	// Método abstrato que deve, obrigatóriamente ser implementado pelas suas subclasses
	// É nesse método que todo processamento das notícias dever ser feito
    public abstract void execute() throws IOException;
}
