package com.tungct.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigManager {

	final static private Logger logger = Logger.getLogger(ConfigManager.class);
	static private ConfigManager singleObject;
	static private Properties props;

	private ConfigManager() {
		logger.info("create ConfigManager()");
		props = new Properties();
	}

	static public void configure(String configFilename) throws Exception {
		getInstance();

		try {

			FileInputStream istream = new FileInputStream(configFilename);
			props.load(istream);
			istream.close();

		} catch (FileNotFoundException e) {
			throw new Exception("ConfigManager.configure() throw Exception: " + e.getMessage());
		} catch (IOException e) {
			throw new Exception("ConfigManager.configure() throw Exception: " + e.getMessage());
		}
	}

	static public ConfigManager getInstance() {
		if (singleObject == null) {
			singleObject = new ConfigManager();
		}
		return singleObject;
	}

	public Object getConfigValue(Object key) {
		if (props != null)
			return props.get(key);
		else return null;
	}
	
	public static class KeyCollection {
		final public static String EmailQuantri = "email-quantri";
		/** Số thiết bị đăng ký tối đa cho 1 KH ứng với Phần mềm */
		//final public static String SoThietBiToiDa = "so-thiet-bi-toi-da";
		
		/** Số ngày chờ để hủy thiết bị tiếp theo */
		final public static String SoNgayChoHuy = "so-ngay-cho-huy";
		
		/** Số ngày chờ để reset số tài khoản theo 1 thiết bị */
		final public static String SoNgayChoResetTaiKhoan = "so-ngay-cho-reset-tai-khoan";
		
		final public static String UrlXacthucEmailXungdot = "url-xacthuc-email-xungdot";
		
		/** Số lượng sản phẩm trên 1 trang tìm kiếm sản phẩm */
		final public static String SoLuongSanPhamMotTrang = "so-luong-san-pham-mot-trang";
		
		/** Dich vu tai file (Download center - ThaiDV) */
		final public static String DownloadCenterThuMucGoc = "download-center-thu-muc-goc";
		
		/** Link tai Phan mem theo Phan mem id cu */
		final public static String LinkTaiPhanMemTheoIdCu = "link-tai-phan-mem-theo-id-cu";
	}
}
