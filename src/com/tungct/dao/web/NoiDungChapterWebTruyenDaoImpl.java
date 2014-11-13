package com.tungct.dao.web;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tungct.domain.luutru.ChuongTruyen;
import com.tungct.utils.HelpFunction;

public class NoiDungChapterWebTruyenDaoImpl implements NoiDungChapterDao{

	private String SelectorTitle;
	private String SelectorContent;
	
	public NoiDungChapterWebTruyenDaoImpl(String selectorTitle,
			String selectorContent) {
		SelectorTitle = selectorTitle;
		SelectorContent = selectorContent;
	}

	@Override
	public ChuongTruyen GetNoiDung(Integer stt, String URL) throws Exception {
		
		ChuongTruyen vChuongTruyen = new ChuongTruyen();
		vChuongTruyen.setUrlgoc(URL);
		vChuongTruyen.setStt(stt);
		
        String sTieuDe = "";
        String sNoiDung = "";
        Document doc = null;
        
//        if(URL.contains("truyenyy")){
//        	URL url_temp = new URL(URL);
//        	URLConnection con = url_temp.openConnection();
//            BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8").newDecoder()));
//              
//            String inputLine;
//            StringBuilder str = new StringBuilder();
//            while ((inputLine = in.readLine()) != null){
//                str.append(inputLine);
//            }
//            in.close();
//              
//            String sHTML = str.toString();
//            doc = Jsoup.parse(sHTML);
//        }
        
        if(URL.contains("truyenyy")){
        	doc = Jsoup.connect(URL).userAgent("Mozilla").ignoreHttpErrors(true).timeout(0).get();
        }
        
        if(doc == null)
        	doc = Jsoup.connect(URL).get();
        
//        System.out.println(doc.toString());
        
		Element tieude = doc.select(SelectorTitle).first();
		if(tieude != null){
			sTieuDe = tieude.ownText();
		} else {
			sTieuDe = "Chương "+stt;
		}
		
		Elements lstNoiDung = doc.select(SelectorContent);
		if(lstNoiDung != null && !lstNoiDung.isEmpty()){
			for (Element noidung : lstNoiDung) {
				Elements div = noidung.select("div:not("+SelectorContent+")");
				if(!div.isEmpty()){
//					sNoiDung += div.text();
					div.remove();
					if(URL.contains("isach.info")){
						sNoiDung += div.text();
					}
				}
				sNoiDung += noidung.html() + "<br/>";
			}
		}
        
        if(!HelpFunction.isEmpty(sNoiDung)){
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
        } else {
        	sNoiDung = "Lỗi khi lấy dữ liệu.";
        }
        
        vChuongTruyen.setTieude(sTieuDe);
        vChuongTruyen.setNoidung(sNoiDung);
        
		return vChuongTruyen;
	}

}
