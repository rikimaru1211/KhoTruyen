package com.tungct.dao.web;

import java.util.ArrayList;
import java.util.List;

import com.tungct.utils.HelpFunction;

public class DanhSachLinkTuTaoDaoImpl implements DanhSachLinkChapterDao{
//http://truyenyy.com/doc-truyen/do-thi-tuyet-sac-bang/chuong-45/
	private String UrlDoc;
	private String TienTo;
	
	public String getUrlDoc() {
		return UrlDoc;
	}
	public void setUrlDoc(String urlDoc) {
		UrlDoc = urlDoc;
	}

	public String getTienTo() {
		return TienTo;
	}
	public void setTienTo(String tienTo) {
		TienTo = tienTo;
	}

	public DanhSachLinkTuTaoDaoImpl() {}
	public DanhSachLinkTuTaoDaoImpl(String urlDoc, String tienTo) {
		UrlDoc = urlDoc;
		TienTo = tienTo;
	}
	
	@Override
	public List<String> LayDanhSachURL() {
		List<String> lstUrl = new ArrayList<String>();
		if(KiemTraTruyen())	{
			int nChuongCuoi = TimChuongCuoi(0, 5000);
			for (int i = 1; i <= nChuongCuoi; i++) {
				lstUrl.add(getChuong(i));
			}
		}
		return lstUrl;
	}

	private String getChuong(int i){
		return UrlDoc + TienTo + i;
	}
	private int TimChuongCuoi(Integer nBatDau, Integer nKetThuc){
        Integer nGiua = (nBatDau + nKetThuc) / 2;
        while(nGiua.intValue() != nBatDau.intValue()){
            if(HelpFunction.UrlHopLe(getChuong(nGiua))){
                nBatDau = nGiua.intValue();
                nGiua = (nBatDau + nKetThuc) / 2;
            } else {
                nKetThuc = nGiua.intValue();
                nGiua = (nBatDau + nKetThuc) / 2;
            }
        }
        if(HelpFunction.UrlHopLe(getChuong(nKetThuc)))
            return nKetThuc;
        else
            return nGiua;
    }
	private boolean KiemTraTruyen(){
		try {
			String trang_dau = getChuong(1);
			if(HelpFunction.UrlHopLe(trang_dau)){
				return true;
			}else{
			    return false;
			}
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return false;
    }
}
