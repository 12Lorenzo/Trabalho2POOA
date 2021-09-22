package br.ufscar.dc.pooa.t2_pooa;

import java.util.ArrayList;
import java.util.List;

public class News {
	private String siteName;
	private String title;
	private String link;
		
	public News(String siteName, String title, String link) {
		super();
		this.siteName = siteName;
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
		fieldList.add("site");
		fieldList.add("title");
		fieldList.add("link");
		return fieldList;
	}
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
}
