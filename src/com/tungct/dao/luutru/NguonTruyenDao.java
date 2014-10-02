package com.tungct.dao.luutru;

import java.util.List;

import com.tungct.domain.luutru.NguonTruyen;

public interface NguonTruyenDao {

	public void insert(NguonTruyen vNguonTruyen) throws Exception;
	public void update(NguonTruyen vNguonTruyen) throws Exception;
	public void delete(String id) throws Exception;
	
	public NguonTruyen SelectByID(String id) throws Exception;
	public NguonTruyen SelectByMa(String sMaNguon) throws Exception;
	public Long Count() throws Exception;
	
	public List<NguonTruyen> SelectByTuKhoa(String sTuKhoa, List<String> lstFields) throws Exception;
	public List<NguonTruyen> SelectByTuKhoa(String sTuKhoa, int nFirstRow, int nPageSize) throws Exception;
	public Long CountByTuKhoa(String sTuKhoa) throws Exception;
	
}
