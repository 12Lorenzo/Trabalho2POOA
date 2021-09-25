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
	private List<News> newsList;
	
	public NewsToCsv(String site, List<News> newsList) {
		super();
		this.site = site;
		this.newsList = newsList;
	}
	
	@Override
	public void execute() throws IOException {
        String strNow = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss").format(LocalDateTime.now());
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(String.format("%s_%s.csv", site, strNow))))) {
            for(String field : News.getFields()) {
                pw.print(field + ";");
            }
            pw.print("\n");
            
            for (News n : newsList) {
                pw.print(n.getTitle().replaceAll(";", ",") + ";");
                pw.print(n.getLink() + ";");
                pw.print("\n");
            }
        }
    }
}
