package com.tungct.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.click.control.Form;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.util.Bindable;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tungct.dao.luutru.ChuongTruyenDao;
import com.tungct.dao.luutru.ChuongTruyenDaoImpl;
import com.tungct.dao.luutru.TruyenDao;
import com.tungct.dao.luutru.TruyenDaoImpl;
import com.tungct.domain.luutru.ChuongTruyen;
import com.tungct.domain.luutru.Truyen;
import com.tungct.utils.HelpFunction;
import com.tungct.web.template.HomeTemplate;

@SuppressWarnings("serial")
public class TestUrl extends HomeTemplate {
	
	private ServletContext context = null;
	private WebApplicationContext appContext = null;
	
	@Bindable private Form form = new Form("form");
	private TextField tfTen = new TextField("tfTen", "Ten");
	private TextField tfURL = new TextField("tfURL", "URL");
	private TruyenDao truyenDao = null;
	private ChuongTruyenDao chuongtruyenDao = null;
	
	private Integer nIndex = 0;
	
	public TestUrl(){
		
		context = getContext().getServletContext();
		appContext = WebApplicationContextUtils.getWebApplicationContext(context);
		truyenDao = (TruyenDaoImpl) appContext.getBean("Truyen-Dao");
		chuongtruyenDao = (ChuongTruyenDaoImpl) appContext.getBean("ChuongTruyen-Dao");
		
		tfTen.setWidth("300px");
		form.add(tfTen);
		
		tfURL.setWidth("300px");
		form.add(tfURL);
		
		Submit sbOK = new Submit("sbOK", "OK", this, "onOKClick");
		form.add(sbOK);
		
	}
	
	public boolean onOKClick() {
		
		try {
			String sTen = tfTen.getValue();
			String sTenKoDau = HelpFunction.GetTenTruyenTrenWeb(sTen);
			String sMaTruyen = "TEST_" + sTenKoDau;
			String sURL = tfURL.getValue();
			String sURLGoc = sURL.substring(0, sURL.lastIndexOf("=") + 1);
			for(int nPage = 1; nPage <= 193; nPage++) {
				sURL = sURLGoc + nPage;
				System.out.println(sURL);
				getNoiDung(sURL, sMaTruyen);
			}
			System.out.println(nIndex);
			
			Truyen vTruyen = new Truyen();
			vTruyen.setChuoitimkiem(HelpFunction.LayChuoiTimKiem(sTenKoDau));
			vTruyen.setManguon("TEST");
			vTruyen.setMatruyen(sMaTruyen);
			vTruyen.setSochuong(nIndex);
			vTruyen.setTdcapnhat(new Date());
			vTruyen.setTdcapnhat(new Date());
			vTruyen.setTen(sTenKoDau);
			vTruyen.setTenhienthi(sTen);
			vTruyen.setUrlgoc(sURLGoc);
			
			System.out.println(vTruyen);
			truyenDao.insert(vTruyen);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public void getNoiDung(String sURL, String sMaTruyen){
		try {
			Document result = Jsoup.connect(sURL).get();
			Elements lstPost = result.select("blockquote");
			if(!lstPost.isEmpty()) {
				for (Element post : lstPost) {
					if(!post.select("div div div font").isEmpty()) {
						nIndex ++;
						String sTitle = "Chương " + nIndex;
						Elements title = post.select("font[color]");
						if(!title.isEmpty()) {
							sTitle = "";
							int i = 0;
							for (Element part : title) {
								sTitle += part.text();
								i++;
								if(i == 3)
									break;
							}
							sTitle = sTitle.replaceAll("-+", " - ");
						}
						System.out.println(StringEscapeUtils.unescapeHtml4(sTitle));
						
						Element body = post.select("div div div font").first();
						String sNoiDung = body.html();
						sNoiDung = sNoiDung.replace("&nbsp;", "");
			        	sNoiDung = sNoiDung.replaceAll("<a[^\n]*</a>", "");
			            sNoiDung = sNoiDung.replaceAll("<span[^\n]*</span>", "");
			            sNoiDung = sNoiDung.replaceAll("<br\\s*/*>", "\n");
			            sNoiDung = sNoiDung.replaceAll("</*p>", "\n");
			            sNoiDung = sNoiDung.replaceAll("<[^>]*>", "");
			            sNoiDung = sNoiDung.replaceAll("\\s+(?=\\n)", "");
			            sNoiDung = sNoiDung.replaceAll("(?<=\\n)\\s+", "");
			            sNoiDung = sNoiDung.replaceAll("\\n{2,}", "\n");
			            sNoiDung = sNoiDung.replaceAll("<([a-zA-Z0-9]+)[^>]*>[^<]*</\\1>", "");
			            sNoiDung = StringEscapeUtils.unescapeHtml4(sNoiDung);
//						System.out.println(StringEscapeUtils.unescapeHtml4(body.text()));
//						System.out.println("========================================================");
						
						ChuongTruyen vChuongTruyen = new ChuongTruyen();
						vChuongTruyen.setMatruyen(sMaTruyen);
						vChuongTruyen.setUrlgoc(sURL);
						vChuongTruyen.setStt(nIndex);
						vChuongTruyen.setTieude(sTitle);
						vChuongTruyen.setNoidung(sNoiDung);
						
						try {
							chuongtruyenDao.insert(vChuongTruyen);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}