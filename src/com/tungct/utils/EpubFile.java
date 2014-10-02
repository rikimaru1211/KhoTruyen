package com.tungct.utils;

public class EpubFile {
	public static final String MINETYPE = "mimetype";
	public static final String MINETYPE_CONTENT = "application/epub+zip";
	
	public static final String META_INF = "META-INF";
	public static final String CONTAINER_XML = "META-INF/container.xml";
	public static final String CONTAINER_XML_CONTENT = "<?xml version=\"1.0\"?>\n" + 
			"<container version=\"1.0\" xmlns=\"urn:oasis:names:tc:opendocument:xmlns:container\">\n" + 
			"    <rootfiles>\n" + 
			"        <rootfile full-path=\"OEBPS/content.opf\" media-type=\"application/oebps-package+xml\"/>\n" + 
			"   </rootfiles>\n" + 
			"</container>";
	
	public static final String OEBPS = "OEBPS";
	public static final String CONTENT_OPF = "OEBPS/content.opf";
	public static final String TOC_NCX = "OEBPS/toc.ncx";
	public static final String TEXT = "OEBPS/Text";
	public static final String TEXT_NAME = "Text";
	
}
