package com.tungct.dao.web;

public class LinkURLGocTuTaoDaoImpl implements LinkURLGocDao{

	private String urlgoc;
	
	public LinkURLGocTuTaoDaoImpl(String urlgoc) {
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
		return urlgoc + sTen + "/";
	}

}
