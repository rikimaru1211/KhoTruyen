package com.tungct.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.click.ActionListener;
import org.apache.click.Context;
import org.apache.click.Control;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Column;
import org.apache.click.control.Decorator;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Submit;
import org.apache.click.control.Table;
import org.apache.click.control.TextField;
import org.apache.click.dataprovider.PagingDataProvider;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tungct.dao.luutru.ChuongTruyenDao;
import com.tungct.dao.luutru.ChuongTruyenDaoImpl;
import com.tungct.dao.luutru.NguonTruyenDao;
import com.tungct.dao.luutru.NguonTruyenDaoImpl;
import com.tungct.dao.luutru.TruyenDao;
import com.tungct.dao.luutru.TruyenDaoImpl;
import com.tungct.dao.web.NoiDungChapterDao;
import com.tungct.dao.web.NoiDungChapterWebTruyenDaoImpl;
import com.tungct.domain.luutru.ChuongTruyen;
import com.tungct.domain.luutru.NguonTruyen;
import com.tungct.domain.luutru.Truyen;
import com.tungct.utils.HelpFunction;
import com.tungct.web.template.HomeTemplate;

@SuppressWarnings("serial")
public class DanhSachChuongView extends HomeTemplate {
	
	private ServletContext context = null;
	private WebApplicationContext appContext = null;
	private ChuongTruyenDao chuongtruyenDao = null;
	private TruyenDao truyenDao = null;
	private NguonTruyenDao nguontruyenDao = null;
	
	private Table table = new Table("table");
	private Form form = new Form("form");
	private HiddenField hfMaTruyen = new HiddenField(ChuongTruyen.MA_TRUYEN, String.class);
	private TextField tfTuKhoa = new TextField("tfTuKhoa", "Từ khóa");
	private Submit sbUpdateAll = new Submit("sbUpdateAll", "Update all", this, "onUpdateAllClick");
	private ActionLink linkUpdate = new ActionLink("linkUpdate", "update",this, "onUpdateClick");
	
	public DanhSachChuongView(){
		
		context = getContext().getServletContext();
		appContext = WebApplicationContextUtils.getWebApplicationContext(context);
		chuongtruyenDao = (ChuongTruyenDaoImpl) appContext.getBean("ChuongTruyen-Dao");
		truyenDao = (TruyenDaoImpl) appContext.getBean("Truyen-Dao");
		nguontruyenDao = (NguonTruyenDaoImpl) appContext.getBean("NguonTruyen-Dao");
		
		String sMaTruyen = getContext().getRequestParameter(ChuongTruyen.MA_TRUYEN);
		if(!HelpFunction.isEmpty(sMaTruyen)){
			form.removeState(getContext());
			hfMaTruyen.setValue(sMaTruyen);
		}
		
		addControl(form);
		addControl(table);
		addControl(linkUpdate);
		
		form.add(hfMaTruyen);
		form.add(tfTuKhoa);
		
		Submit sbSearch = new Submit("sbSearch", "Tìm kiếm", this, "onSearchClick");
		form.add(sbSearch);
		form.add(sbUpdateAll);
		
		table.addStyleClass(Table.CLASS_SIMPLE);
		table.setShowBanner(true);
		table.setPageSize(20);
		
		Column column = new Column(ChuongTruyen.SO_THU_TU, "STT");
		table.addColumn(column);
		
//		column = new Column(ChuongTruyen.MA_TRUYEN, "Mã truyện");
//		table.addColumn(column);
		
		column = new Column(ChuongTruyen.TIEU_DE, "Chương");
		table.addColumn(column);
		
		column = new Column(ChuongTruyen.ID, "id");
		table.addColumn(column);
		
//		column = new Column(ChuongTruyen.URL_GOC, "Link trang gốc");
//		table.addColumn(column);
		
		column = new Column("update", "update");
		column.setDecorator(new Decorator() {
			@Override
			public String render(Object object, Context context) {
				ChuongTruyen obj = (ChuongTruyen) object;
				linkUpdate.setValue(obj.get_id());
				linkUpdate.setParameter(ChuongTruyen.MA_TRUYEN, obj.getMatruyen());
				linkUpdate.setParameter(ChuongTruyen.URL_GOC, obj.getUrlgoc());
				linkUpdate.setParameter(ChuongTruyen.SO_THU_TU, obj.getStt());
				return linkUpdate.toString();
			}
		});
		table.addColumn(column);
		
		table.setDataProvider(new PagingDataProvider<ChuongTruyen>() {

			@Override
			public Iterable<ChuongTruyen> getData() {
				
				table.getControlLink().setParameter(ChuongTruyen.MA_TRUYEN, hfMaTruyen.getValue());
				
				List<ChuongTruyen> lstTruyen = null;
				Integer nFirstRow = table.getFirstRow();
				Integer nPageSize = table.getPageSize();
				String sMaTruyen = hfMaTruyen.getValue();
				String sTuKhoa = tfTuKhoa.getValue();
				try {
					lstTruyen = chuongtruyenDao.SelectByMaTruyenVaTuKhoa(sMaTruyen, sTuKhoa, null, true, nFirstRow, nPageSize);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return lstTruyen;
			}

			@Override
			public int size() {
				Long nCount = 0l;
				String sMaTruyen = hfMaTruyen.getValue();
				String sTuKhoa = tfTuKhoa.getValue();
				try {
					nCount = chuongtruyenDao.CountByMaTruyenVaTuKhoa(sMaTruyen, sTuKhoa);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return nCount.intValue();
			}
		});
	}

	public boolean onSearchClick(){
		form.saveState(getContext());
		table.setPageNumber(0);
		table.removeState(getContext());
		setRedirect(this.getClass());
		return false;
	}
	
	@Override
	public void onInit() {
		Context context = getContext();
		form.restoreState(context);
		table.restoreState(context);
		table.getControlLink().setActionListener(new ActionListener() {
			@Override
			public boolean onAction(Control source) {
				table.saveState(getContext());
				return false;
			}
		});
	}
	
	public boolean onUpdateClick(){
		try {
			String id = linkUpdate.getValue();
			String urlgoc = linkUpdate.getParameter(ChuongTruyen.URL_GOC);
			String matruyen = linkUpdate.getParameter(ChuongTruyen.MA_TRUYEN);
			String sott = linkUpdate.getParameter(ChuongTruyen.SO_THU_TU);
			int stt = Integer.parseInt(sott);

			String sThongBao = "";
			String sMaNguon = matruyen.substring(0, matruyen.lastIndexOf('_'));
//			Truyen vTruyen = truyenDao.SelectByMa(matruyen);
//			if(vTruyen != null){
				NguonTruyen vNguonTruyen = nguontruyenDao.SelectByMa(sMaNguon);
				if(vNguonTruyen != null){
					NoiDungChapterDao noidungchapterDao = new NoiDungChapterWebTruyenDaoImpl(vNguonTruyen.getDivtitle(), vNguonTruyen.getDivcontent());
					System.out.println(urlgoc);
					ChuongTruyen vChuongTruyen = noidungchapterDao.GetNoiDung(stt, urlgoc);
					vChuongTruyen.set_id(id);
					vChuongTruyen.setMatruyen(matruyen);
					chuongtruyenDao.update(vChuongTruyen);
				} else {
					sThongBao = "ko tim thay nguon.";
				}
//			} else {
//				sThongBao = "ko tim thay truyen";
//			}
			
			addModel("thongbao", sThongBao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean onUpdateAllClick(){
		try {
			String matruyen = hfMaTruyen.getValue();
			String sTuKhoa = tfTuKhoa.getValue();
			
			Truyen vTruyen = truyenDao.SelectByMa(matruyen);
			if(vTruyen == null){
				addModel("thongbao", "ko tim thay truyen.");
				return false;
			}
			
			NguonTruyen vNguonTruyen = nguontruyenDao.SelectByMa(vTruyen.getManguon());
			if(vNguonTruyen == null){
				addModel("thongbao", "ko tim thay nguon.");
				return false;
			}
			
			NoiDungChapterDao noidungchapterDao = new NoiDungChapterWebTruyenDaoImpl(vNguonTruyen.getDivtitle(), vNguonTruyen.getDivcontent());
			List<String> lstField = new ArrayList<String>();
			lstField.add(ChuongTruyen.SO_THU_TU);
			lstField.add(ChuongTruyen.URL_GOC);
			lstField.add(ChuongTruyen.MA_TRUYEN);
			lstField.add(ChuongTruyen.TIEU_DE);
			List<ChuongTruyen> lstTruyen = chuongtruyenDao.SelectByMaTruyenVaTuKhoa(matruyen, sTuKhoa, lstField, false, 0, 0);
			int nListSize = lstTruyen.size();
			
			if(lstTruyen != null && lstTruyen.size() > 0){
				if(matruyen.toLowerCase().contains("truyenyy_com")){
					HelpFunction.SetListProxy();
					for(int i = 0; i < nListSize; ){
						System.out.println(i);
						ChuongTruyen cTemp = lstTruyen.get(i);
						ChuongTruyen cUpdate = null;
						if(HelpFunction.isEmpty(cTemp.getTieude()) || cTemp.getTieude().equals("- Chương 18: Phá Thiên")
								|| cTemp.getTieude().equals("Chương thứ yyy: Ra đảo")
								|| cTemp.getTieude().equals("One more step")){
							try {
								cUpdate = noidungchapterDao.GetNoiDung(cTemp.getStt(), cTemp.getUrlgoc());
								HelpFunction.ResetProxy();
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							i++;
							continue;
						}
						
//						System.out.println(cUpdate.getTieude());
						if(cUpdate.getTieude().equals("- Chương 18: Phá Thiên") 
								|| cUpdate.getTieude().equals("Chương thứ yyy: Ra đảo")){
							HelpFunction.ChangeProxy();
							Thread.sleep(3000);
						} else if(cUpdate.getTieude().equals("One more step")){
							HelpFunction.ChangeProxy();
							Thread.sleep(2000);
						} else {
							cUpdate.set_id(cTemp.get_id());
							cUpdate.setMatruyen(cTemp.getMatruyen());
							chuongtruyenDao.update(cUpdate);
							i++;
							Thread.sleep(1000);
						}
					}
				} else {
					
					for (ChuongTruyen chuong : lstTruyen) {
						ChuongTruyen vChuongTruyen = noidungchapterDao.GetNoiDung(chuong.getStt(), chuong.getUrlgoc());
						vChuongTruyen.set_id(chuong.get_id());
						vChuongTruyen.setMatruyen(chuong.getMatruyen());
						chuongtruyenDao.update(vChuongTruyen);
					}
					addModel("thongbao", "update thanh cong.");
					
				}
			} else {
				addModel("thongbao", "ko tim thay chuong.");
			}
			
		} catch (Exception e) {
			addModel("thongbao", "co loi xay ra.");
			e.printStackTrace();
		}
		return false;
	}
}