package br.ufscar.dc.pooa.t2_pooa;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Functions {
    public static void newsToCsv(List<News> newsList) throws IOException {
        String strNow = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss").format(LocalDateTime.now());
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(String.format("%s.csv", strNow))))) {
            for(String field : News.getFields()) {
                pw.print(field + ";");
            }
            pw.print("\n");
            
            for (News n : newsList) {
                pw.print(n.getSiteName() + ";");
                pw.print(n.getTitle().replaceAll(";", ",") + ";");
                pw.print(n.getLink() + ";");
                pw.print("\n");
            }
        }
    }
}
