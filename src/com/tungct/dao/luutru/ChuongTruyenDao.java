package com.tungct.dao.luutru;

import java.util.List;

import com.tungct.domain.luutru.ChuongTruyen;

public interface ChuongTruyenDao {

	public void insert(ChuongTruyen vChuongTruyen) throws Exception;
	public void update(ChuongTruyen vChuongTruyen) throws Exception;
	public void delete(String id) throws Exception;
	
	public ChuongTruyen SelectByID(String id) throws Exception;
	public Long Count() throws Exception;
	
	public List<ChuongTruyen> SelectByMaTruyenVaTuKhoa(String sMaTruyen, String sTuKhoa, boolean bSort, int nFirstRow, int nPageSize) throws Exception;
	public Long CountByMaTruyenVaTuKhoa(String sMaTruyen, String sTuKhoa) throws Exception;
	
	public void DeleteByMaTruyen(String sMaTruyen) throws Exception;
	
}
