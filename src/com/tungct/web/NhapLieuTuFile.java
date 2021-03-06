package com.tungct.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.click.control.FileField;
import org.apache.click.control.Form;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.util.Bindable;
import org.apache.commons.fileupload.FileItem;
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
public class NhapLieuTuFile extends HomeTemplate {
	
	private ServletContext context = null;
	private WebApplicationContext appContext = null;
	private ChuongTruyenDao chuongtruyenDao = null;
	
	@Bindable private Form form = new Form("form");
	private FileField ffDirectory = new FileField("ffDirectory", "Đường dẫn: ", true);
	private TextField tfTieuDeBatDau = new TextField("tfTieuDeBatDau","Tiêu đề bắt đầu bằng: ");
	private TextField tfTieuDeChua = new TextField("tfTieuDeChua","Tiêu đề có chứa từ: ");
	private TextField tfRegex = new TextField("tfRegex","Regular Express: ");
	private TruyenDao truyenDao = null;
	
	public NhapLieuTuFile(){
		
		context = getContext().getServletContext();
		appContext = WebApplicationContextUtils.getWebApplicationContext(context);
		truyenDao = (TruyenDaoImpl) appContext.getBean("Truyen-Dao");
		chuongtruyenDao = (ChuongTruyenDaoImpl) appContext.getBean("ChuongTruyen-Dao");
		
		form.add(ffDirectory);
		form.add(tfTieuDeBatDau);
		form.add(tfTieuDeChua);
		form.add(tfRegex);
		Submit sbOK = new Submit("sbOK", "OK", this, "onOKClick");
		form.add(sbOK);
	}

	public boolean onOKClick(){
		try {
			if(form.isValid()) {
				String sTieuDeChua = tfTieuDeChua.getValue();
				String sTieuDeBatDau = tfTieuDeBatDau.getValue();
				String sRegex = tfRegex.getValue();
				Pattern patternTieuDe = null;
				
				if(!HelpFunction.isEmpty(sRegex)) {
					patternTieuDe = Pattern.compile(sRegex);
				}
				
				FileItem item = ffDirectory.getFileItem();
				String sNguon = item.getName();
				Truyen vTruyen = new Truyen();
				vTruyen.setManguon("TuFile");
				vTruyen.setUrlgoc("TuFile");
				
				String sTen = sNguon.substring(0, sNguon.lastIndexOf("."));
				vTruyen.setTen(sTen);
				vTruyen.setTenhienthi(sTen);
				vTruyen.setMatruyen("TuFile_" + sTen);
				
				Date dNow = new Date();
				vTruyen.setTdtao(dNow);
				vTruyen.setTdcapnhat(dNow);
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(item.getInputStream(), "utf-8"));
				String sLine = "";
				ChuongTruyen vChuong = new ChuongTruyen();
				String sTieuDe = "";
				String sNoiDung = "";
				int stt = 1;
				while((sLine = reader.readLine()) != null){
					sLine = sLine.trim();
					if(HelpFunction.isEmpty(sLine))
						continue;
					String sLineSearch = sLine.toLowerCase();
					if((HelpFunction.isEmpty(sTieuDeChua) || sLineSearch.contains(sTieuDeChua)) 
							&& (HelpFunction.isEmpty(sTieuDeBatDau) || sLineSearch.startsWith(sTieuDeBatDau))) {
						if(HelpFunction.isEmpty(sRegex) || patternTieuDe.matcher(sLineSearch).find()) {
							if(!HelpFunction.isEmpty(sTieuDe) && !HelpFunction.isEmpty(sNoiDung)) {
								vChuong = new ChuongTruyen();
								vChuong.setMatruyen("TuFile_" + sTen);
								vChuong.setStt(stt++);
								vChuong.setNoidung(sNoiDung);
								vChuong.setTieude(sTieuDe);
								chuongtruyenDao.insert(vChuong);
							}
							
							sTieuDe = sLine;
							sNoiDung = "";
						}
					} else {
						sNoiDung += sLine + "\n";
					}
				}
				
				if(!HelpFunction.isEmpty(sTieuDe) && !HelpFunction.isEmpty(sNoiDung)) {
					vChuong = new ChuongTruyen();
					vChuong.setMatruyen("TuFile_" + sTen);
					vChuong.setStt(stt++);
					vChuong.setNoidung(sNoiDung);
					vChuong.setTieude(sTieuDe);
					chuongtruyenDao.insert(vChuong);
				}
				
				vTruyen.setSochuong(stt - 1);
				truyenDao.insert(vTruyen);
				
				form.setError("insert thanh cong.");
			}
		} catch (Exception e) {
			form.setError("Co loi xay ra.");
			e.printStackTrace();
		}
		return false;
	}
}