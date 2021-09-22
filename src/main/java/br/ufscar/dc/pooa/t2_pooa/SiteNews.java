package br.ufscar.dc.pooa.t2_pooa;

public class SiteNews {
	private String name;
	private String url;
	private String titleClass;
	
	public SiteNews(String title, String url, String titleClass) {
		super();
		this.name = title;
		this.url = url;
		this.titleClass = titleClass;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitleClass() {
		return titleClass;
	}
	public void setTitleClass(String titleClass) {
		this.titleClass = titleClass;
	}
}
