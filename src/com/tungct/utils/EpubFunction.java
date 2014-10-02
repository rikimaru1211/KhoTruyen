package com.tungct.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.tungct.domain.luutru.ChuongTruyen;

public class EpubFunction {

	public static boolean CreateDirectory(String sDirectory){
		boolean result = true;
		File theDir = new File(sDirectory);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			try{
				theDir.mkdir();
			} catch(SecurityException se){
				result = false;
			}        
		}
		return result;
	}
	
	public static boolean CreateFile(String sPath, String sContent){
		boolean result = true;
		File file = new File(sPath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Tạo file thất bại: " + sPath);
				return false;
			}
        }
		
		if(!HelpFunction.isEmpty(sContent)){
			try {
				HelpFunction.writeUTF8Text(sPath, sContent, false);
			} catch (Exception e) {
				System.out.println("Ghi file thất bại: " + sPath);
				result = false;
			}
		}
		return result;
	}
	
	public static String CreateMapFileHeader(String id, String sTitle){
		StringBuilder str = new StringBuilder();
		str.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<package xmlns=\"http://www.idpf.org/2007/opf\" unique-identifier=\"BookID\" version=\"2.0\">\n" + 
				"	<metadata xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:opf=\"http://www.idpf.org/2007/opf\">\n" + 
				"		<dc:title>");
		str.append(sTitle);
		str.append("</dc:title>\n" + 
				"	<dc:language>en</dc:language>\n" + 
				"		<dc:rights>Public Domain</dc:rights>\n" + 
				"		<dc:creator opf:role=\"aut\">Yoda47</dc:creator>\n" + 
				"		<dc:publisher>rikimaru</dc:publisher>\n" + 
				"		<dc:identifier id=\"BookID\" opf:scheme=\"UUID\">");
		str.append(id);
		str.append("</dc:identifier>\n" + 
				"		<meta name=\"Sigil version\" content=\"0.2.4\"/>\n" + 
				"	</metadata>\n" + 
				"	<manifest>\n" + 
				"		<item id=\"ncx\" href=\"toc.ncx\" media-type=\"application/x-dtbncx+xml\"/>\n");
		//str.append("<item id=\"sample.png\" href=\"Images/sample.png\" media-type=\"image/png\"/>");
		//str.append("<item id=\"stylesheet.css\" href=\"Styles/stylesheet.css\" media-type=\"text/css\"/>");
		
		return str.toString();
	}
	public static String CreateMapFileItem(int index, String src){
		StringBuilder str = new StringBuilder();
		str.append("		<item id=\"chapter").append(index).append("\" href=\"").append(src).append("\" media-type=\"application/xhtml+xml\"/>\n");
		return str.toString();
	}
	public static String CreateMapFileEnd(int length){
		StringBuilder str = new StringBuilder();
		str.append("	</manifest>\n");
		str.append("	<spine toc=\"ncx\">\n");
		for (int i = 1; i <= length; i++) {
			str.append("		<itemref idref=\"chapter").append(i).append("\"/>\n");
		}
		str.append("	</spine>\n");
		str.append("</package>");
		return str.toString();
	}
	
	public static String CreateIndexFileHeader(String id, String sTitle){
		StringBuilder str = new StringBuilder();
		str.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<!DOCTYPE ncx PUBLIC \"-//NISO//DTD ncx 2005-1//EN\"\n" + 
				"	\"http://www.daisy.org/z3986/2005/ncx-2005-1.dtd\">\n" + 
				"\n" + 
				"<ncx xmlns=\"http://www.daisy.org/z3986/2005/ncx/\" version=\"2005-1\">\n" + 
				"	<head>\n" + 
				"		<meta name=\"dtb:uid\" content=\"");
		str.append(id);
		str.append("\"/>\n" + 
				"		<meta name=\"dtb:depth\" content=\"1\"/>\n" + 
				"		<meta name=\"dtb:totalPageCount\" content=\"0\"/>\n" + 
				"		<meta name=\"dtb:maxPageNumber\" content=\"0\"/>\n" + 
				"	</head>\n" + 
				"	<docTitle>\n" + 
				"		<text>");
		str.append(sTitle);
		str.append("</text>\n" + 
				"	</docTitle>\n" + 
				"	<navMap>\n");
		
		return str.toString();
	}
	public static String CreateIndexFileItem(int index, String label, String src){
		StringBuilder str = new StringBuilder();
		str.append("		<navPoint id=\"chapter").append(index).append("\" playOrder=\"").append(index);
		str.append("\">\n" + 
				"			<navLabel>\n" + 
				"				<text>");
		str.append(label).append("</text>\n" + 
				"			</navLabel>\n" + 
				"			<content src=\"");
		str.append(src).append("\" />\n" + 
				"		</navPoint>\n");
		return str.toString();
	}
	public static String CreateIndexFileEnd(){
		StringBuilder str = new StringBuilder();
		str.append("	</navMap>\n");
		str.append("</ncx>");
		return str.toString();
	}
	
	public static void CreateEpubFile(String tentruyen, List<ChuongTruyen> lstChuong){
        
        String sRoot = "/home/tungct/Documents/epub/" + tentruyen + "/";
        EpubFunction.CreateDirectory(sRoot);
        EpubFunction.CreateFile(sRoot + EpubFile.MINETYPE, EpubFile.MINETYPE_CONTENT);
        EpubFunction.CreateDirectory(sRoot + EpubFile.META_INF);
        EpubFunction.CreateFile(sRoot + EpubFile.CONTAINER_XML, EpubFile.CONTAINER_XML_CONTENT);
        EpubFunction.CreateDirectory(sRoot + EpubFile.OEBPS);
        EpubFunction.CreateDirectory(sRoot + EpubFile.TEXT);
        
        String id = UUID.randomUUID().toString();
        String sMapFile = sRoot + EpubFile.CONTENT_OPF;
        EpubFunction.CreateFile(sMapFile, EpubFunction.CreateMapFileHeader(id, tentruyen));
        
        String sIndexFile = sRoot + EpubFile.TOC_NCX;
        EpubFunction.CreateFile(sIndexFile, EpubFunction.CreateIndexFileHeader(id, tentruyen));
        
        for (ChuongTruyen vChuong : lstChuong) {
            try {
                
                String sTieuDe = vChuong.getTieude();
                
                StringBuilder strNoiDung = new StringBuilder();
                strNoiDung.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + 
			                		"<head>\n" + 
			                		"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>truyen</title>\n" + 
			                		"</head>\n" + 
			                		"<body>");
                strNoiDung.append("<p>").append(sTieuDe).append("</p>");
                strNoiDung.append("<p>").append(vChuong.getNoidung().replace("\n", "</p><p>")).append("</p>");
                strNoiDung.append("</body>\n" + 
                					"</html>");
                String sNoiDung = strNoiDung.toString();
                
                int index = vChuong.getStt();
                
                if(!HelpFunction.isEmpty(sNoiDung)){
                	
                    String sTenFile = "/chuong"+index+".xhtml";
                	EpubFunction.CreateFile(sRoot + EpubFile.TEXT +  sTenFile, sTieuDe + "\n\n" + sNoiDung + "\n");
                	
                	HelpFunction.writeUTF8Text(sMapFile, EpubFunction.CreateMapFileItem(index, "Text" + sTenFile ), true);
                	HelpFunction.writeUTF8Text(sIndexFile, EpubFunction.CreateIndexFileItem(index, sTieuDe, "Text" + sTenFile ), true);
                	
                }
                
            }catch (Exception e) {
                System.out.println("failed to connect");
            }
        }
        
        try {
			HelpFunction.writeUTF8Text(sMapFile, EpubFunction.CreateMapFileEnd(lstChuong.size()), true);
			HelpFunction.writeUTF8Text(sIndexFile, EpubFunction.CreateIndexFileEnd(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        System.out.println("CreateEpubFile() - ket thuc");
	}
}
