package com.tungct.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.click.control.Button;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Option;
import org.apache.click.control.Select;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.extras.control.IntegerField;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tungct.dao.luutru.ChuongTruyenDao;
import com.tungct.dao.luutru.ChuongTruyenDaoImpl;
import com.tungct.dao.luutru.NguonTruyenDao;
import com.tungct.dao.luutru.NguonTruyenDaoImpl;
import com.tungct.dao.luutru.TruyenDao;
import com.tungct.dao.luutru.TruyenDaoImpl;
import com.tungct.dao.web.DanhSachLinkChapterDao;
import com.tungct.dao.web.DanhSachLinkTrongSelectorDaoImpl;
import com.tungct.dao.web.DanhSachLinkTuTaoDaoImpl;
import com.tungct.dao.web.LinkURLGocDao;
import com.tungct.dao.web.LinkURLGocGoogleSearchDaoImpl;
import com.tungct.dao.web.LinkURLGocTuTaoDaoImpl;
import com.tungct.dao.web.NoiDungChapterDao;
import com.tungct.dao.web.NoiDungChapterWebTruyenDaoImpl;
import com.tungct.domain.luutru.ChuongTruyen;
import com.tungct.domain.luutru.NguonTruyen;
import com.tungct.domain.luutru.Truyen;
import com.tungct.utils.HelpFunction;

@SuppressWarnings("serial")
public class PopupThemSuaTruyen extends org.apache.click.Page {
	
	private ServletContext context = null;
	private WebApplicationContext appContext = null;
	private TruyenDao truyenDao = null;
	private NguonTruyenDao nguontruyenDao = null;
	private ChuongTruyenDao chuongtruyenDao = null;
	private LinkURLGocDao linkurlgocDao = null;
	private DanhSachLinkChapterDao danhsachlinkchapterDao = null;
	private NoiDungChapterDao noidungchapterDao = null;
	
	private Truyen vTruyen = null;
	private Form form = new Form("form");
	private HiddenField hfID = new HiddenField(Truyen.ID, String.class);
	private Select seNguon = new Select(Truyen.MA_NGUON, "Nguồn", true);
	private HiddenField hfTenTruyen = new HiddenField(Truyen.TEN, String.class);
	private TextField tfTenTruyen = new TextField(Truyen.TEN_HIEN_THI, "Tên truyện", true);
	private TextField tfUrlGoc = new TextField(Truyen.URL_GOC, "URL gốc");
	private TextField tfMaTruyen = new TextField(Truyen.MA_TRUYEN, "Mã truyện");
	private IntegerField ifChuongDau = new IntegerField("ifChuongDau", "Chương đầu");
	private IntegerField ifChuongCuoi = new IntegerField("ifChuongCuoi", "Chương cuối");
	
	public PopupThemSuaTruyen(){
		
		context = getContext().getServletContext();
		appContext = WebApplicationContextUtils.getWebApplicationContext(context);
		truyenDao = (TruyenDaoImpl) appContext.getBean("Truyen-Dao");
		nguontruyenDao = (NguonTruyenDaoImpl) appContext.getBean("NguonTruyen-Dao");
		chuongtruyenDao = (ChuongTruyenDaoImpl) appContext.getBean("ChuongTruyen-Dao");
		
		String sID = getContext().getRequestParameter("id");
		if(!HelpFunction.isEmpty(sID)){
			try {
				vTruyen = truyenDao.SelectByID(sID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		addControl(form);
		form.add(hfID);
		form.add(hfTenTruyen);
		seNguon.add(new Option("", ""));
		try {
			List<String> lstFields = new ArrayList<String>();
			lstFields.add(NguonTruyen.MA_NGUON);
			List<NguonTruyen> lstNguon = nguontruyenDao.SelectByTuKhoa("", lstFields);
			if(lstNguon != null && lstNguon.size() > 0){
				for (NguonTruyen nguonTruyen : lstNguon) {
					seNguon.add(new Option(nguonTruyen.getManguon(), nguonTruyen.getManguon()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		form.add(seNguon);
		form.add(tfTenTruyen);
		form.add(tfUrlGoc);
		tfMaTruyen.setReadonly(true);
		form.add(tfMaTruyen);
		form.add(ifChuongDau);
		form.add(ifChuongCuoi);
		
		Submit sbOK = new Submit("sbOK","Đồng ý",this,"onUpdateClick");
		form.add(sbOK);
		
		Button btnCancel = new Button("btnCancel","Thoát");
		btnCancel.setAttribute("onclick", "window.close()");
		form.add(btnCancel);
		
		if(!HelpFunction.isEmpty(sID) || !HelpFunction.isEmpty(hfID.getValue())){
			tfTenTruyen.setReadonly(true);
			seNguon.setReadonly(true);
		}
		
		ImportData();
	}
	
	public void ImportData(){
		if(vTruyen != null){
			form.copyFrom(vTruyen);
		}
	}
	
	public boolean onUpdateClick(){
		if(form.isValid()){
			try {
				vTruyen = new Truyen();
				form.copyTo(vTruyen);
				Date dNow = new Date();
				vTruyen.setTdcapnhat(dNow);
				
				NguonTruyen vNguonTruyen = nguontruyenDao.SelectByMa(vTruyen.getManguon());
				if(vNguonTruyen == null){
					form.setError("Nguồn truyện không tồn tại.");
					return false;
				}
				
				String sTen = "";
				if(HelpFunction.isEmpty(vTruyen.getTen())){
					sTen = HelpFunction.GetTenTruyenTrenWeb(vTruyen.getTenhienthi());
					vTruyen.setTen(sTen);
				} else {
					sTen = vTruyen.getTen();
				}
				vTruyen.setMatruyen(vTruyen.getManguon()+"_"+sTen);
				vTruyen.setChuoitimkiem(HelpFunction.LayChuoiTimKiem(vTruyen.getMatruyen()));
				
				String sUrlGoc = "";
				if(HelpFunction.isEmpty(vTruyen.getUrlgoc())){
					if(NguonTruyen.KieuURLGoc.GoogleSearch.equals(vNguonTruyen.getKieuurlgoc())){
						linkurlgocDao = new LinkURLGocGoogleSearchDaoImpl(vNguonTruyen.getUrlgoc());
					} else {
						linkurlgocDao = new LinkURLGocTuTaoDaoImpl(vNguonTruyen.getUrlindex());
					}
					sUrlGoc = linkurlgocDao.LayURLGoc(sTen);
					if(!sUrlGoc.toLowerCase().replaceAll("[^a-zA-Z0-9]", "-").contains(sTen)){
						System.out.println("ko tim thay");
						System.out.println(sUrlGoc);
						return false;
					}
					if(vNguonTruyen.getManguon().equalsIgnoreCase("isach_info")) {
						sUrlGoc += "&chapter=0000";
					}
					vTruyen.setUrlgoc(sUrlGoc);
				} else {
					sUrlGoc = vTruyen.getUrlgoc();
				}
				
				if(NguonTruyen.KieuIndex.DatTrongSelector.equals(vNguonTruyen.getKieuindex())){
					danhsachlinkchapterDao = new DanhSachLinkTrongSelectorDaoImpl(sUrlGoc, vNguonTruyen.getDivchapter(), vNguonTruyen.getDivpage());
				} else if(NguonTruyen.KieuIndex.TuTao.equals(vNguonTruyen.getKieuindex())){
					String urldoc = vNguonTruyen.getUrldoc() + vTruyen.getTen() + "/";
					danhsachlinkchapterDao = new DanhSachLinkTuTaoDaoImpl(urldoc, vNguonTruyen.getTiento());
				}
				if(danhsachlinkchapterDao == null){
					form.setError("Kiểu index không tồn tại.");
					return false;
				}
				
				List<String> lstLink = danhsachlinkchapterDao.LayDanhSachURL();
				if(lstLink != null && lstLink.size() > 0){
					noidungchapterDao = new NoiDungChapterWebTruyenDaoImpl(vNguonTruyen.getDivtitle(), vNguonTruyen.getDivcontent());
					vTruyen.setSochuong(lstLink.size());
					int nChuongDau = 1;
					int nChuongCuoi = lstLink.size();
					if(!HelpFunction.isEmpty(ifChuongDau.getValue())){
						Integer ntemp = (Integer) ifChuongDau.getValueObject();
						if(ntemp > 1)
							nChuongDau = ntemp;
					}
					if(!HelpFunction.isEmpty(ifChuongCuoi.getValue())){
						Integer ntemp = (Integer) ifChuongCuoi.getValueObject();
						if(ntemp < nChuongCuoi)
							nChuongCuoi = ntemp;
					}
					List<Integer> lstLoi = new ArrayList<Integer>();
					for (int i = nChuongDau; i <= nChuongCuoi; i++) {
						ChuongTruyen vChuongTruyen = null;
						int lap = 0;
						while(vChuongTruyen == null && lap < 3) {
							try {
								String sLink = lstLink.get(i - 1);
								if(vNguonTruyen.getManguon().equalsIgnoreCase("isach_info")) {
									sLink = vNguonTruyen.getUrlgoc() + sLink;
									System.out.println(sLink);
								}
								vChuongTruyen = noidungchapterDao.GetNoiDung(i, sLink);
							} catch (Exception e) {
								e.printStackTrace();
								Thread.sleep(30000);
							}
							lap++;
						}
						
						if(vChuongTruyen == null){
							vChuongTruyen = new ChuongTruyen();
							vChuongTruyen.setUrlgoc(lstLink.get(i - 1));
							vChuongTruyen.setStt(i);
							lstLoi.add(i);
						}
						
						vChuongTruyen.setMatruyen(vTruyen.getMatruyen());
						chuongtruyenDao.insert(vChuongTruyen);
					}
					System.out.println("Các chương lỗi : " + lstLoi.toString());
				} else {
					form.setError("Không tìm thấy danh sách chương.");
					return false;
				}
				
				System.out.println(vTruyen);
				if(HelpFunction.isEmpty(vTruyen.get_id())){
					vTruyen.setTdtao(dNow);
					vTruyen.set_id(null);
					truyenDao.insert(vTruyen);
				} else {
					truyenDao.update(vTruyen);
				}
			} catch (Exception e) {
				form.setError("Có lỗi xảy ra.");
				e.printStackTrace();
			}
		}
		return false;
	}
	
}