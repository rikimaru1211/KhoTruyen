package com.tungct.dao.web;

import com.tungct.domain.luutru.ChuongTruyen;

public interface NoiDungChapterDao {

	public ChuongTruyen GetNoiDung(Integer stt, String URL) throws Exception;
	
}
