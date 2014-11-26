package com.tungct.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.click.Context;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Button;
import org.apache.click.control.Column;
import org.apache.click.control.Decorator;
import org.apache.click.control.Form;
import org.apache.click.control.Option;
import org.apache.click.control.PageLink;
import org.apache.click.control.Select;
import org.apache.click.control.Submit;
import org.apache.click.control.Table;
import org.apache.click.control.TextField;
import org.apache.click.dataprovider.PagingDataProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tungct.dao.luutru.ChuongTruyenDao;
import com.tungct.dao.luutru.ChuongTruyenDaoImpl;
import com.tungct.dao.luutru.NguonTruyenDao;
import com.tungct.dao.luutru.NguonTruyenDaoImpl;
import com.tungct.dao.luutru.TruyenDao;
import com.tungct.dao.luutru.TruyenDaoImpl;
import com.tungct.domain.luutru.ChuongTruyen;
import com.tungct.domain.luutru.NguonTruyen;
import com.tungct.domain.luutru.Truyen;
import com.tungct.utils.EpubFunction;
import com.tungct.utils.HelpFunction;
import com.tungct.utils.ZipFunction;
import com.tungct.web.template.HomeTemplate;

@SuppressWarnings("serial")
public class DanhSachTruyenView extends HomeTemplate {
	
	private ServletContext context = null;
	private WebApplicationContext appContext = null;
	private TruyenDao truyenDao = null;
	private NguonTruyenDao nguontruyenDao = null;
	private ChuongTruyenDao chuongtruyenDao = null;
	
	private Table table = new Table("table");
	private Form form = new Form("form");
	private Select seNguon = new Select(Truyen.MA_NGUON, "Nguồn");
	private TextField tfTuKhoa = new TextField("tfTuKhoa", "Từ khóa");
	private PageLink linkThemMoi = new PageLink(PopupThemSuaTruyen.class);
	private PageLink linkNoiDung = new PageLink(DanhSachChuongView.class);
	private ActionLink linkGetTxt = new ActionLink("linkGetTxt", "txt", this, "onGetTxtClick");
	private ActionLink linkGetEpub = new ActionLink("linkGetEpub", "epub", this, "onGetEpubv2Click");
	private ActionLink linkTest = new ActionLink("linkTest", "test", this, "onTestClick");
	private ActionLink linkDelete = new ActionLink("linkDelete", "delete", this, "onDeleteClick");
	
	public DanhSachTruyenView(){
		
		context = getContext().getServletContext();
		appContext = WebApplicationContextUtils.getWebApplicationContext(context);
		truyenDao = (TruyenDaoImpl) appContext.getBean("Truyen-Dao");
		nguontruyenDao = (NguonTruyenDaoImpl) appContext.getBean("NguonTruyen-Dao");
		chuongtruyenDao = (ChuongTruyenDaoImpl) appContext.getBean("ChuongTruyen-Dao");
		
		String sThongBao = (String) getContext().getSessionAttribute("ThongBao");
		if(!HelpFunction.isEmpty(sThongBao)){
			addModel("ThongBao", sThongBao);
			getContext().removeSessionAttribute("ThongBao");
		}
		
		addControl(form);
		addControl(table);
		addControl(linkGetTxt);
		addControl(linkGetEpub);
		addControl(linkTest);
		addControl(linkDelete);
		linkDelete.setAttribute("onclick", "return window.confirm('Đồng ý xóa?');");
		
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
		form.add(tfTuKhoa);
		
		Submit sbSearch = new Submit("sbSearch", "Tìm kiếm", this, "onSearchClick");
		form.add(sbSearch);
		
		Button btnThemMoi = new Button("btnThemMoi", "Thêm mới");
		btnThemMoi.setAttribute("onclick", "openPopup('"+linkThemMoi.getHref()+"','Thêm mới','500','350')");
		form.add(btnThemMoi);
		
		table.addStyleClass(Table.CLASS_SIMPLE);
		table.setShowBanner(true);
		table.setPageSize(10);
		
		Column column = new Column(Truyen.TEN_HIEN_THI, "Tên");
		table.addColumn(column);
		
		column = new Column(Truyen.SO_CHUONG, "Số chương");
		table.addColumn(column);
		
		column = new Column(Truyen.URL_GOC, "Link trang gốc");
		table.addColumn(column);
		
		column = new Column("xem", "Xem");
		column.setDecorator(new Decorator() {
			@Override
			public String render(Object object, Context context) {
				Truyen obj = (Truyen) object;
				linkNoiDung.setParameter(Truyen.MA_TRUYEN, obj.getMatruyen());
				String urlNoiDung = "<a href='"+linkNoiDung.getHref()+"' >Xem</a>";
				return urlNoiDung;
			}
		});
		table.addColumn(column);
		
		column = new Column("txt", "txt");
		column.setDecorator(new Decorator() {
			@Override
			public String render(Object object, Context context) {
				Truyen obj = (Truyen) object;
				linkGetTxt.setParameter(Truyen.MA_TRUYEN, obj.getMatruyen());
				linkGetTxt.setParameter(Truyen.TEN, obj.getTen());
				return linkGetTxt.toString();
			}
		});
		table.addColumn(column);
		
		column = new Column("epub", "epub");
		column.setDecorator(new Decorator() {
			@Override
			public String render(Object object, Context context) {
				Truyen obj = (Truyen) object;
				linkGetEpub.setParameter(Truyen.MA_TRUYEN, obj.getMatruyen());
				linkGetEpub.setParameter(Truyen.TEN, obj.getTen());
				return linkGetEpub.toString();
			}
		});
		table.addColumn(column);
		
		column = new Column("test", "test");
		column.setDecorator(new Decorator() {
			@Override
			public String render(Object object, Context context) {
				Truyen obj = (Truyen) object;
				linkTest.setParameter(Truyen.MA_TRUYEN, obj.getMatruyen());
				return linkTest.toString();
			}
		});
		table.addColumn(column);
		
		column = new Column("delete", "delete");
		column.setDecorator(new Decorator() {
			@Override
			public String render(Object object, Context context) {
				Truyen obj = (Truyen) object;
				linkDelete.setParameter(Truyen.MA_TRUYEN, obj.getMatruyen());
				return linkDelete.toString();
			}
		});
		table.addColumn(column);
		
		table.setDataProvider(new PagingDataProvider<Truyen>() {

			@Override
			public Iterable<Truyen> getData() {
				List<Truyen> lstTruyen = null;
				Integer nFirstRow = table.getFirstRow();
				Integer nPageSize = table.getPageSize();
				String sTuKhoa = tfTuKhoa.getValue();
				String sNguon = seNguon.getValue();
				try {
					lstTruyen = truyenDao.SelectByNguonVaTuKhoa(sNguon, sTuKhoa, nFirstRow, nPageSize);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return lstTruyen;
			}

			@Override
			public int size() {
				Long nCount = 0l;
				String sTuKhoa = tfTuKhoa.getValue();
				String sNguon = seNguon.getValue();
				try {
					nCount = truyenDao.CountByNguonVaTuKhoa(sNguon, sTuKhoa);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return nCount.intValue();
			}
		});
	}

	public boolean onSearchClick(){
		return false;
	}
	
	public boolean onGetTxtClick(){
		try {
			String sMaTruyen = linkGetTxt.getParameter(Truyen.MA_TRUYEN);
			String sTen = linkGetTxt.getParameter(Truyen.TEN);
			System.out.println("sMaTruyen: " + sMaTruyen);
			List<ChuongTruyen> lst = chuongtruyenDao.SelectByMaTruyenVaTuKhoa(sMaTruyen, "", null, true, 0, 0);
			if(lst != null && !lst.isEmpty()){
				HttpServletResponse response = getContext().getResponse();
				response.setContentType("text/plain");
				response.setHeader("Content-Disposition", "attached; filename="+sTen+".txt");
				response.setCharacterEncoding("UTF-8");
				
				PrintWriter print = response.getWriter();
				
				for (ChuongTruyen chuong : lst) {
					StringBuilder sb = new StringBuilder();
					sb.append(chuong.getTieude()).append("\n\n").append(chuong.getNoidung()).append("\n\n");
					print.write(sb.toString());
					
				}
				
				print.flush();
				print.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean onGetEpubClick(){
		try {
			String sMaTruyen = linkGetEpub.getParameter(Truyen.MA_TRUYEN);
			String sTen = linkGetEpub.getParameter(Truyen.TEN);
			System.out.println("sMaTruyen: " + sMaTruyen);
			List<ChuongTruyen> lst = chuongtruyenDao.SelectByMaTruyenVaTuKhoa(sMaTruyen, "", null, false, 0, 0);
			if(lst != null && !lst.isEmpty()){
				HttpServletResponse response = getContext().getResponse();
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attached; filename="+sTen+".epub");
				response.setCharacterEncoding("UTF-8");
				
				OutputStream out = response.getOutputStream();
				
				Collections.sort(lst);
				EpubFunction.CreateEpubFile(sTen, lst);
				
				String sRoot = "/home/tungct/Documents/epub/" + sTen;
				
				ZipFunction zippy = new ZipFunction();
		        try {
		            zippy.zipDir(sRoot, out);
		        } catch(IOException e2) {
		        	e2.printStackTrace();
		        }
				
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean onGetEpubv2Click(){
		try {
			String sMaTruyen = linkGetEpub.getParameter(Truyen.MA_TRUYEN);
			String sTen = linkGetEpub.getParameter(Truyen.TEN);
			System.out.println("sMaTruyen: " + sMaTruyen);
			List<ChuongTruyen> lst = chuongtruyenDao.SelectByMaTruyenVaTuKhoa(sMaTruyen, "", null, true, 0, 0);
			if(lst != null && !lst.isEmpty()){
				HttpServletResponse response = getContext().getResponse();
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attached; filename="+sTen+".epub");
				response.setCharacterEncoding("UTF-8");
				
				OutputStream out = response.getOutputStream();
				
				EpubFunction.CreateEpubFileV2(sTen, lst, out);
				
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean onTestClick(){
		try {
			String sMaTruyen = linkTest.getParameter(Truyen.MA_TRUYEN);
			System.out.println("sMaTruyen: " + sMaTruyen);
			List<ChuongTruyen> lst = chuongtruyenDao.SelectByMaTruyenVaTuKhoa(sMaTruyen, "", null, true, 0, 1);
			if(lst != null && lst.size() > 0){
				ChuongTruyen vChuongTruyen = lst.get(0);
				System.out.println(vChuongTruyen.getUrlgoc());
				Document doc = Jsoup.connect(vChuongTruyen.getUrlgoc()).get();
				System.out.println(doc.toString());
			} else {
				System.out.println("khong tim thay");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean onDeleteClick(){
		String sMaTruyen = linkDelete.getParameter(Truyen.MA_TRUYEN);
		try {
			System.out.println("sMaTruyen: " + sMaTruyen);
			chuongtruyenDao.DeleteByMaTruyen(sMaTruyen);
			truyenDao.DeleteByMaTruyen(sMaTruyen);
			getContext().setSessionAttribute("ThongBao", "Xóa thành công truyện: " + sMaTruyen);
		} catch (Exception e) {
			getContext().setSessionAttribute("ThongBao", "Có lỗi xảy ra khi xóa truyện: " + sMaTruyen);
			e.printStackTrace();
		}
		setRedirect(this.getClass());
		return false;
	}
}