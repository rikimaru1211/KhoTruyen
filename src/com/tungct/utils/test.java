package com.tungct.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.tungct.dao.luutru.ChuongTruyenDao;
import com.tungct.dao.luutru.ChuongTruyenDaoImpl;
import com.tungct.domain.luutru.ChuongTruyen;

public class test {
	
	public static void main(String[] args) {
		
		try {
			/* * load file appcontext*/
			new FileSystemXmlApplicationContext(new String[] { "WebContent/WEB-INF/DBConnection.xml", "WebContent/WEB-INF/DaoNghiepVu.xml"});
			ChuongTruyenDao ctDao = (ChuongTruyenDaoImpl) SpringApplicationContext.getBean("ChuongTruyen-Dao");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//	        System.out.print("id: ");
//	        String id1 = br.readLine();
			//			 543c8fcbccf2ed7626d89bdb
			//			 543c8fccccf2ed7626d89bdc
			String id = "543c8fdcccf2ed7626d89c38";
			
			Runtime.getRuntime().addShutdownHook(new Thread() {
			    public void run() { System.out.println("end"); }
			});
			
			ChuongTruyen vChuong = ctDao.SelectByID(id);
			if(vChuong != null){
				System.out.println(vChuong.getTieude());
				String sNoiDung = vChuong.getNoidung();
				sNoiDung.replaceAll("[^a-zA-Z0-9]\"", "\"");
				String [] arrNoiDung = sNoiDung.split("[\n.,]");
				int n = arrNoiDung.length;
				System.out.println(n);Thread.sleep(2000);
				String sLine = "";
				for (int i = 0; i < n; i++) {
//					if(i < 380)
//						continue;
					sLine = arrNoiDung[i];
					if(!HelpFunction.isEmpty(sLine.trim())){
						System.out.print("\n");
						System.out.println(sLine);
						System.out.print("\n\n\n");
						if(sLine.length() > 50)
							Thread.sleep(3500);
						else
							Thread.sleep(1500);
					}
					if(i % 17 == 0){
						System.out.print("\n");
						br.readLine();
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
