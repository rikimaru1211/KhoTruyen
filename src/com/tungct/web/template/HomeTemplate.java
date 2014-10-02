package com.tungct.web.template;

import org.apache.click.extras.control.Menu;
import org.apache.click.extras.control.MenuFactory;
import org.apache.click.extras.security.RoleAccessController;

@SuppressWarnings("serial")
public class HomeTemplate extends org.apache.click.Page {
	
	private Menu rootMenu;
	
	public HomeTemplate(){
		MenuFactory menuFactory = new MenuFactory();
		rootMenu = menuFactory.getRootMenu("rootMenu","menu-home.xml", new RoleAccessController(),false/* ko cached*/, null);
	    addControl(rootMenu);
		
	}
	public String getTemplate() {
	      return "web/template/home-template.htm";
	}
}