package com.tungct.dao.web;

import java.net.URLDecoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class LinkURLGocGoogleSearchDaoImpl implements LinkURLGocDao{
	//https://www.google.com.vn/#q=an-sat+site:http:%2F%2Ftruyen.hixx.info%2F
	public static final String URL_GOOGLE = "https://www.google.com.vn/search?q=";
	
	private String urlgoc;
	public LinkURLGocGoogleSearchDaoImpl(String urlgoc) {
		this.urlgoc = urlgoc;
	}
	public String getUrlgoc() {
		return urlgoc;
	}
	public void setUrlgoc(String urlgoc) {
		this.urlgoc = urlgoc;
	}
	
	@Override
	public String LayURLGoc(String sTen) {
		String sURLGoc = "";
		try {
			String linkSearch = URL_GOOGLE + sTen + "+site:" + urlgoc;
			Document doc = Jsoup.connect(linkSearch).userAgent("Mozilla").ignoreHttpErrors(true).timeout(0).get();
			
			Element firstSearch = doc.select("#search .r a").first();
			if(firstSearch != null){
				String url = firstSearch.attr("href");
				url = url.substring(url.indexOf("=") + 1, url.indexOf("&"));
				sURLGoc = URLDecoder.decode(url, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sURLGoc;
	}

}
