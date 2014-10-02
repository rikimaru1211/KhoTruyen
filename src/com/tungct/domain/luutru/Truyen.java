package com.tungct.domain.luutru;

import java.util.Date;

public class Truyen {
	
	public static final String ID = "_id";
	public static final String TEN = "ten";
	public static final String TEN_HIEN_THI = "tenhienthi";
	public static final String MA_TRUYEN = "matruyen";
	public static final String MA_NGUON = "manguon";
	public static final String URL_GOC = "urlgoc";
	public static final String SO_CHUONG = "sochuong";
	public static final String CHUOI_TIM_KIEM = "chuoitimkiem";
	public static final String THOI_DIEM_TAO = "tdtao";
	public static final String THOI_DIEM_CAP_NHAT = "tdcapnhat";
	
	private String _id;
	private String ten;
	private String tenhienthi;
	private String matruyen;
	private String manguon;
	private String urlgoc;
	private Integer sochuong;
	private String chuoitimkiem;
	private Date tdtao;
	private Date tdcapnhat;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	
	public String getTen() {
		ten.replace("+", "");
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	
	public String getTenhienthi() {
		return tenhienthi;
	}
	public void setTenhienthi(String tenhienthi) {
		this.tenhienthi = tenhienthi;
	}
	
	public String getMatruyen() {
		return matruyen;
	}
	public void setMatruyen(String matruyen) {
		this.matruyen = matruyen;
	}
	
	public String getManguon() {
		return manguon;
	}
	public void setManguon(String manguon) {
		this.manguon = manguon;
	}
	
	public String getUrlgoc() {
		return urlgoc;
	}
	public void setUrlgoc(String urlgoc) {
		this.urlgoc = urlgoc;
	}
	
	public Integer getSochuong() {
		return sochuong;
	}
	public void setSochuong(Integer sochuong) {
		this.sochuong = sochuong;
	}
	
	public String getChuoitimkiem() {
		return chuoitimkiem;
	}
	public void setChuoitimkiem(String chuoitimkiem) {
		this.chuoitimkiem = chuoitimkiem;
	}
	
	public Date getTdtao() {
		return tdtao;
	}
	public void setTdtao(Date tdtao) {
		this.tdtao = tdtao;
	}
	
	public Date getTdcapnhat() {
		return tdcapnhat;
	}
	public void setTdcapnhat(Date tdcapnhat) {
		this.tdcapnhat = tdcapnhat;
	}
	
	@Override
	public String toString() {
		return "Truyen [_id=" + _id + ", ten=" + ten + ", tenhienthi="
				+ tenhienthi + ", matruyen=" + matruyen + ", manguon="
				+ manguon + ", urlgoc=" + urlgoc + ", sochuong=" + sochuong
				+ ", chuoitimkiem=" + chuoitimkiem + ", tdtao=" + tdtao
				+ ", tdcapnhat=" + tdcapnhat + "]";
	}
}
