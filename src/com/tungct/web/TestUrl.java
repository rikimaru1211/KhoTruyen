package com.tungct.web;

import org.apache.click.control.Form;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.util.Bindable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.tungct.web.template.HomeTemplate;

@SuppressWarnings("serial")
public class TestUrl extends HomeTemplate {
	
	@Bindable private Form form = new Form("form");
	private TextField tfURL = new TextField("tfURL", "URL");
	
	public TestUrl(){
		
		tfURL.setWidth("300px");
		form.add(tfURL);
		
		Submit sbOK = new Submit("sbOK", "OK", this, "onOKClick");
		form.add(sbOK);
		
	}
	
	public boolean onOKClick() {
		
		try {
			String sURL = tfURL.getValue();
			Document result = Jsoup.connect(sURL).get();
//			if(result != null) {
//				addModel("result", result.toString());
//			}
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

}