# Trabalho 02 - Princípio Aberto-Fechado

## Introdução e motivação

Passamos por vários estágios durante o desenvolvimento deste projeto, cada um destes tendo gerado uma reflexão que instigou a refatoração do código de modo a respeitar os princípios estudados até o momento. Inicialmente buscamos a criação de um método genérico, que pudesse receber o link do site e a classe da tag HTML onde ficam armazenados os títulos e retornar as principais notícias daquele site. Apesar desta abordagem ter resultado em um código bem compacto e funcional para todos os sites testados, começamos a analisar a possibilidade de novos sites serem incluídos no futuro. Nesse contexto, é perfeitamente possível que algum site não se encaixasse no modelo criado, ou que precisasse de algum tipo de processamento adicional, o que faria com que o método genérico criado tivesse de ser alterado, resultando assim na quebra do código para todos os outros sites que funcionavam.

Desse momento em diante buscamos escrever um código com ambos os benefícios: um código genérico, que funcionasse para a grande maioria dos sites sem muitas alterações, e um código expandível, no qual o método genérico pudesse ser reimplementado para atender às necessidades de sites específicos, mas sem afetar os sites já criados.

## O Código

Em um primeiro momento foi criada a classe **News** onde devem ser armazenadas as notícias. A intenção era ter algo concreto para armazenar os resultados do parsing realizado no site, e que pudesse ser utilizado em diversos algorítmos diferentes. Esta classe tem as propriedades **title**, que armazena os títulos das notícias, e **link**, que guarda o link para estas.

```java
// News.java
public class News {
	private String title;
	private String link;

	public News(String title, String link) {
		super();
		this.title = title;
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	public static List<String> getFields(){
		List<String> fieldList = new ArrayList<>();
		fieldList.add("title");
		fieldList.add("link");
		return fieldList;
	}
}
```

Em seguida, foi criada uma classe para realizar o parsing dos sites de notícias, isto é, a obtenção dos títulos e links das principais notícias dos sites em questão. Para isso foi criada a classe abstrata **NewsParser**, a qual tem os atributos **url** (link do site de notícias) e **titleClass** (classe HTML onde ficam os títulos nesse site), além do método **getNews()** que utiliza as propriedades citadas para obter as notícias de um site e armazená-las em uma lista de de notícias (List<News>). Inicialmente o método getNews() era apenas um método abstrato e sua implementação era deixada a cargo de suas subclasses; entretanto, notamos que, como o parsing não muda para a grande maioria dos sites, havia uma grande repetição de código. Por este motivo, optamos por implementar este método na classe abstrata, de modo que todas suas subclasses o herdarão e poderão sobrescrevê-lo, caso necessário. A inicialização dos atributos **url** e **titleClass** fica a cargo dos construtores das subclasses.

```java
public abstract class NewsParser {
	
	protected String url;
	protected String titleClass;

	// Método padrão para o parsing e coleta de notícias de um site
	// Apesar de não servir para todos os casos, serve para a maioria deles
	// Pode ser reimplementado por cada subclasse para atender suas necessidades
	public List<News> getNews() throws IOException{
		List<News> newsList = new ArrayList<>();

		// Se conecta ao site com a url passada
		Document doc = Jsoup.connect(url).get();

		// Pega o conteúdo das classe dos títulos
		Elements titles = doc.select(titleClass);

		// Percorre todos os títulos encontrados e guarda o título e o link da notícia
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
			News news = new News(newsTitle, newsLink);
			newsList.add(news);
		}
        	return newsList;
	}
}
```
Implementada a classe abstrata, implementamos subclasses que extendem a classe **NewsParser** para cada um dos sites que desejávamos obter as notícias. No caso, escolhemos os sites: Globo, Estadão e Uol. Os sites escolhidos acabaram não fugindo do modelo padrão, por isso, bastou inicializar os atributos da classe abstrata **url** e **titleClass** para que o parsing funcionasse.

```java
// GloboParser.java
public class GloboParser extends NewsParser{
	
	public GloboParser() {
		url = "https://www.globo.com";
		titleClass = ".post__title";
	}

}

// EstadaoParser.java
public class EstadaoParser extends NewsParser{
	
	public EstadaoParser() {
		url = "https://www.estadao.com.br";
		titleClass = "h3.title a";
	}
}

// UolParser.java
public class UolParser extends NewsParser{
	
	public UolParser() {
		url = "https://www.uol.com.br";
		titleClass = "h2.titulo";
	}
}
```

Caso o método implementado para a classe abstrata não funcione para algum site que eventualmente deseje-se incluir, basta criar uma nova subclasse de **NewsParser** que sobrescreve o método **getNews()**.

```java
public class NewSiteParser extends NewsParser{
	
    public NewSiteParser() {
		url = "https://www.new-site.com";
		titleClass = ".title";
	}
	
	@Override
	public List<News> getNews() throws IOException{
		// Código específico para o parsing deste site
	}
	
}
```


Após criarmos a lógica para obter as notícias de um site, era preciso criar uma classe que ficaria responsável por manipulá-las, aplicando algum algorítmo e em seguida realizar alguma tarefa com elas. Para isso nós criamos a classe abstrata **Algorithm**, que tem o atributo **newsList** (a lista de notícias que se deseja manipular) e o método abstrato  **execute()** que irá manipular **newsList**.

```java
public abstract class Algorithm {
	protected List<News> newsList;
	
	// Método abstrato que deve, obrigatóriamente ser implementado pelas suas subclasses
	// É nesse método que todo processamento das notícias dever ser feito
    public abstract void execute() throws IOException;
}
```

Como já tínhamos conhecimento de que se desejava colocar as notícias em um arquivo .csv, foi também criada uma subclasse de **Algorithm** chamada **NewsToCsv**, que realiza esta operação. Neste caso, além da lista de notícias (newsList, atributo da classe abstrata), precisávamos também do nome do site, para que este fosse incluído no nome do arquivo csv, visando diferenciar os arquivos gerados pelo processamento de diferentes sites. Para isso foi criada a propriedade **site**.

```java
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
```

Caso seja necessário implementar um outro tipo de processamento além de criar um arquivo csv, pode-se criar uma nova subclasse da classe abstrata **Algorithm**, sobrescrevendo também o método **execute**. Como por exemplo, caso desejássemos printar o conteúdo das notícias na tela:

```java
public class PrintNews extends Algorithm{
	
	public NewsToCsv(List<News> auxNewsList) {
		super();
		newsList = auxNewsList;
	}
	
	@Override
	public void execute() throws IOException {
        // Lógica para printar o conteúdo na tela
    }
}
```

Por fim, essas classes são utilizadas em uma função **main**. Aqui, verificamos os argumentos passados na chamada do programa e decidimos qual tipo de processamento será realizado.

```java
// App.java
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
```

Esta funcão **allSitesToCsv()** apenas instancia as outras classes e faz com que os algorítmos sejam rodados para todos os sites.

```java
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
````
 
