package br.ufscar.dc.pooa.t2_pooa.algorithms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.ufscar.dc.pooa.t2_pooa.News;

public class NewsToCsv extends Algorithm{
	private String site;
	
	public NewsToCsv(String site, List<News> auxNewsList) {
		super();
		this.site = site;
		newsList = auxNewsList;
	}
	
	@Override
	public void execute() throws IOException {
		// Coleta a data e hora atual para colocar no nome do arquivo
        String strNow = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss").format(LocalDateTime.now());
        
        // Cria um arquivo csv com o nome do site e a hora atual
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(String.format("%s_%s.csv", site, strNow))))) {
        	
        	// As classe News tem um método que retorna os campos presentas nela. 
        	// Estes campos são usados como nomes para as colunas do csv
            for(String field : News.getFields()) {
                pw.print(field + ";");
            }
            pw.print("\n");
            
            // Percorre todas as notícias da lista e coloca no csv
            for (News n : newsList) {
                pw.print(n.getTitle().replaceAll(";", ",") + ";");
                pw.print(n.getLink() + ";");
                pw.print("\n");
            }
        }
    }
}
