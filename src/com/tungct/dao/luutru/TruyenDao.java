package com.tungct.dao.luutru;

import java.util.List;

import com.tungct.domain.luutru.Truyen;

public interface TruyenDao {

	public void insert(Truyen vTruyen) throws Exception;
	public void update(Truyen vTruyen) throws Exception;
	public void delete(String id) throws Exception;
	
	public void DeleteByMaTruyen(String sMaTruyen) throws Exception;
	
	public Truyen SelectByID(String id) throws Exception;
	public Truyen SelectByMa(String sMaTruyen) throws Exception;
	public Long Count() throws Exception;
	
	public List<Truyen> SelectByNguonVaTuKhoa(String sNguon, String sTuKhoa, int nFirstRow, int nPageSize) throws Exception;
	public Long CountByNguonVaTuKhoa(String sNguon, String sTuKhoa) throws Exception;
	
}
