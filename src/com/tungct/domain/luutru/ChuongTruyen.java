package com.tungct.domain.luutru;


public class ChuongTruyen implements Comparable<ChuongTruyen>{
	
	public static final String ID = "_id";
	public static final String TIEU_DE = "tieude";
	public static final String SO_THU_TU = "stt";
	public static final String NOI_DUNG = "noidung";
	public static final String MA_TRUYEN = "matruyen";
	public static final String URL_GOC = "urlgoc";
	
	private String _id;
	private String tieude;
	private Integer stt;
	private String noidung;
	private String matruyen;
	private String urlgoc;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	
	public String getTieude() {
		return tieude;
	}
	public void setTieude(String tieude) {
		this.tieude = tieude;
	}
	
	public Integer getStt() {
		return stt;
	}
	public void setStt(Integer stt) {
		this.stt = stt;
	}
	
	public String getNoidung() {
		return noidung;
	}
	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}
	
	public String getMatruyen() {
		return matruyen;
	}
	public void setMatruyen(String matruyen) {
		this.matruyen = matruyen;
	}
	
	public String getUrlgoc() {
		return urlgoc;
	}
	public void setUrlgoc(String urlgoc) {
		this.urlgoc = urlgoc;
	}
	
	@Override
	public String toString() {
		return "ChuongTruyen [_id=" + _id + ", tieude=" + tieude + ", stt="
				+ stt + ", noidung=" + noidung + ", matruyen=" + matruyen
				+ ", urlgoc=" + urlgoc + "]";
	}
	@Override
	public int compareTo(ChuongTruyen o) {
		int nCompare = 0;
		if(stt < o.getStt())
			nCompare = -1;
		else if(stt == o.getStt())
			nCompare = 0;
		else
			nCompare = 1;
		return nCompare;
	}
}
