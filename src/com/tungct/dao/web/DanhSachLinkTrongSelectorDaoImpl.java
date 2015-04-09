package com.tungct.dao.web;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DanhSachLinkTrongSelectorDaoImpl implements DanhSachLinkChapterDao{

	private String UrlGoc;
	private String SelectorListChapter;
	private String SelectorPage;
	
	public String getUrlGoc() {
		return UrlGoc;
	}
	public void setUrlGoc(String urlGoc) {
		UrlGoc = urlGoc;
	}
	
	public String getSelectorListChapter() {
		return SelectorListChapter;
	}
	public void setSelectorListChapter(String selectorListChapter) {
		SelectorListChapter = selectorListChapter;
	}
	
	public String getSelectorPage() {
		return SelectorPage;
	}
	public void setSelectorPage(String selectorNextPage) {
		SelectorPage = selectorNextPage;
	}
	
	public DanhSachLinkTrongSelectorDaoImpl(){};
	public DanhSachLinkTrongSelectorDaoImpl(String urlGoc,
			String selectorListChapter, String selectorPage) {
		//http://truyen.hixx.info/truyen/truyen-kiem-hiep/288195/Thien-Tai-Doa-Lac.html
		UrlGoc = urlGoc;
		SelectorListChapter = selectorListChapter;
		SelectorPage = selectorPage;
	}
	
	@Override
	public List<String> LayDanhSachURL() {
		List<String> lstUrl = new ArrayList<String>();
		try {
			String url = UrlGoc;
			boolean bContinue = true;
			while(bContinue){
				bContinue = false;
				
				Document doc = Jsoup.connect(url).get();
//				System.out.println(doc);
				Elements lstchapter = doc.select(SelectorListChapter);
				if(lstchapter != null) {
					Elements lstLink = lstchapter.select("a");
					for (Element e : lstLink) {
						String sUrlTemp = e.attr("href");
						lstUrl.add(sUrlTemp);
					}
				}
				
				Elements nextitem = doc.select(SelectorPage);
				if(!nextitem.isEmpty()){
					Elements nextpage = nextitem.select("a");
					url = nextpage.first().attr("href");
					bContinue = true;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lstUrl;
	}

}
