package com.tungct.utils;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.tungct.dao.luutru.NguonTruyenDao;
import com.tungct.dao.luutru.NguonTruyenDaoImpl;
import com.tungct.dao.web.NoiDungChapterDao;
import com.tungct.dao.web.NoiDungChapterWebTruyenDaoImpl;
import com.tungct.domain.luutru.ChuongTruyen;
import com.tungct.domain.luutru.NguonTruyen;

public class TestConnect {

	public static void main(String[] args) {
		
		try {
			/* * load file appcontext*/
			new FileSystemXmlApplicationContext(new String[] { "WebContent/WEB-INF/DBConnection.xml", "WebContent/WEB-INF/DaoNghiepVu.xml"});
			NguonTruyenDao ntDao = (NguonTruyenDaoImpl) SpringApplicationContext.getBean("NguonTruyen-Dao");
			
			NguonTruyen vNguonTruyen = ntDao.SelectByMa("TRUYENYY_COM");
			if(vNguonTruyen == null){
				System.out.println("khong thay nguon");
				return;
			}
			NoiDungChapterDao noidungchapterDao = new NoiDungChapterWebTruyenDaoImpl(vNguonTruyen.getDivtitle(), vNguonTruyen.getDivcontent());
			
			String sURL = "http://truyenyy.com/doc-truyen/tao-hoa-chi-mon/chuong-378/";
			Integer nTimeNghi = 120000;
			Integer nTimeGiuaCacLanLay = 5000;
			boolean bTiepTuc = true;
			while (bTiepTuc) {
				System.out.println("-----------------------------------------");
				System.out.println("nTimeGiuaCacLanLay: " + nTimeGiuaCacLanLay);
				for (int i = 0; i <= 100; i++) {
					ChuongTruyen cUpdate = noidungchapterDao.GetNoiDung(1, sURL);
					
					if(cUpdate.getTieude().equals("- Chương 18: Phá Thiên") 
							|| cUpdate.getTieude().equals("Chương thứ yyy: Ra đảo")){
						nTimeGiuaCacLanLay += 1000;
						break;
					} else {
						System.out.println(i);
						Thread.sleep(nTimeGiuaCacLanLay);
					}
					
					if(i == 100) {
						bTiepTuc = false;
					}
				}
				Thread.sleep(10000);
				Thread.sleep(nTimeNghi);
			}
			System.out.println("\n\n======================================");
			System.out.println("Thoi gian cuoi cung: " + nTimeGiuaCacLanLay);
			System.out.println("======================================");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
