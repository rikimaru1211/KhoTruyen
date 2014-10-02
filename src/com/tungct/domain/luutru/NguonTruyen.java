package com.tungct.domain.luutru;

import java.util.Date;

public class NguonTruyen {
	
	public static class KieuURLGoc {
		public static final String GoogleSearch = "google-search";
		public static final String TuTao = "tu-tao";
	}
	public static class KieuIndex {
		public static final String DatTrongSelector = "trong-selector";
		public static final String TuTao = "tu-tao";
	}
	
	public static final String ID = "_id";
	public static final String MA_NGUON = "manguon";
	public static final String URL_GOC = "urlgoc";
	public static final String URL_INDEX = "urlindex";
	public static final String KIEU_URL_GOC = "kieuurlgoc";
	public static final String KIEU_INDEX = "kieuindex";
	public static final String DIV_CHAPTER = "divchapter";
	public static final String DIV_PAGE = "divpage";
	public static final String URL_DOC = "urldoc";
	public static final String TIEN_TO = "tiento";
	public static final String DIV_TITLE = "divtitle";
	public static final String DIV_CONTENT = "divcontent";
	public static final String THOI_DIEM_TAO = "tdtao";
	public static final String THOI_DIEM_CAP_NHAT = "tdcapnhat";
	
	private String _id;
	private String manguon;
	private String urlgoc;
	private String urlindex;
	private String kieuurlgoc;
	private String kieuindex;
	private String divchapter;
	private String divpage;
	private String urldoc;
	private String tiento;
	private String divtitle;
	private String divcontent;
	private Date tdtao;
	private Date tdcapnhat;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	
	public String getManguon() {
		return manguon;
	}
	public void setManguon(String manguon){
		this.manguon = manguon;
	}
	
	public String getUrlgoc() {
		return urlgoc;
	}
	public void setUrlgoc(String urlgoc) {
		this.urlgoc = urlgoc;
	}
	
	public String getUrlindex() {
		return urlindex;
	}
	public void setUrlindex(String urlindex) {
		this.urlindex = urlindex;
	}
	
	public String getKieuurlgoc() {
		return kieuurlgoc;
	}
	public void setKieuurlgoc(String kieuurlgoc) {
		this.kieuurlgoc = kieuurlgoc;
	}
	
	public String getKieuindex() {
		return kieuindex;
	}
	public void setKieuindex(String kieuindex) {
		this.kieuindex = kieuindex;
	}
	
	public String getDivchapter() {
		return divchapter;
	}
	public void setDivchapter(String divchapter) {
		this.divchapter = divchapter;
	}
	
	public String getDivpage() {
		return divpage;
	}
	public void setDivpage(String divpage) {
		this.divpage = divpage;
	}
	
	public String getUrldoc() {
		return urldoc;
	}
	public void setUrldoc(String urldoc) {
		this.urldoc = urldoc;
	}
	
	public String getTiento() {
		return tiento;
	}
	public void setTiento(String tiento) {
		this.tiento = tiento;
	}
	
	public String getDivtitle() {
		return divtitle;
	}
	public void setDivtitle(String divtitle) {
		this.divtitle = divtitle;
	}
	
	public String getDivcontent() {
		return divcontent;
	}
	public void setDivcontent(String divcontent) {
		this.divcontent = divcontent;
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
		return "NguonTruyen [_id=" + _id + ", manguon=" + manguon + ", urlgoc="
				+ urlgoc + ", urlindex=" + urlindex + ", kieuurlgoc="
				+ kieuurlgoc + ", kieuindex=" + kieuindex + ", divchapter="
				+ divchapter + ", divpage=" + divpage + ", urldoc=" + urldoc
				+ ", tiento=" + tiento + ", divtitle=" + divtitle
				+ ", divcontent=" + divcontent + ", tdtao=" + tdtao
				+ ", tdcapnhat=" + tdcapnhat + "]";
	}
}
