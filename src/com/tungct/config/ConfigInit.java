package com.tungct.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.tungct.utils.ConfigManager;

@SuppressWarnings("serial")
public class ConfigInit extends HttpServlet {

	final static private Logger logger = Logger.getLogger(ConfigInit.class);

	public void init() {
		/*
		 * Khởi tạo ConfigManager
		 */
		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("custom-config-init-file");

		if (file != null) {
			try {
				ConfigManager.configure(prefix + file);
				logger.info("init() - ConfigManager khoi tao thanh cong...");
			} catch (Exception e) {
				logger.info("ConfigInit.init() throw Exception: " + e.getMessage());
			}
		}
		
		/*
		 * Khởi tạo Memcached
		 */
		/*try {
			String sMaxCacheClient = getInitParameter("cached-max-client");
			String sCacheServerAddress = getInitParameter("cached-server-address");
			String sCachedTTL = getInitParameter("cached-ttl");
			MemcachedBroker.configure(Integer.parseInt(sMaxCacheClient), sCacheServerAddress, Integer.parseInt(sCachedTTL));
			logger.info("init() - MemcachedBroker khoi tao thanh cong...");
		} catch (Exception e) {
			logger.error("init() - Khoi tao MemcachedBroker exception: " + e.getMessage());
			e.printStackTrace();
		}*/
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
}
