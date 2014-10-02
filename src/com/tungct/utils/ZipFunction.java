package com.tungct.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFunction {

    public static void main(String[] args) {
	         
        ZipFile zipFile = null;
 
        try {
             
            // open a zip file for reading
            zipFile = new ZipFile("/home/tungct/la-ban-van-menh.epub");
 
            // get an enumeration of the ZIP file entries
            Enumeration<? extends ZipEntry> e = zipFile.entries();
 
            while (e.hasMoreElements()) {
                 
                ZipEntry entry = e.nextElement();
 
                // get the compression method of the entry, or -1 if not specified
                int method = entry.getMethod();
 
                if (method == ZipEntry.DEFLATED) {
                    System.out.println(entry.getName() + " is Deflated");
                }
                else if (method == ZipEntry.STORED) {
                    System.out.println(entry.getName() + " is Stored");
                }
                else if (method == -1) {
                    System.out.println(entry.getName() + " is Not Specified");
                }
 
            }
 
        }
        catch (IOException ioe) {
            System.out.println("Error opening zip file" + ioe);
        }
         finally {
             try {
                 if (zipFile!=null) {
                     zipFile.close();
                 }
             }
             catch (IOException ioe) {
                    System.out.println("Error while closing zip file" + ioe);
             }
         }
         
    }
	
    public void zipDir(String dirName, OutputStream out) throws IOException {
		ZipOutputStream zip = null;
        zip = new ZipOutputStream(out);
        File folder = new File(dirName);
        for (String fileName : folder.list()) {
        	File vfile = new File(fileName);
        	if(vfile.isDirectory()){
        		addFolderToZip("", fileName, zip);
        	} else {
        		addFileToZip("", dirName+"/"+fileName, zip, false);
        	}
		}
        
        zip.close();
    }
    
    public void zipDir(String dirName, String nameZipFile) throws IOException {
		ZipOutputStream zip = null;
        FileOutputStream fW = null;
        fW = new FileOutputStream(nameZipFile);
        zip = new ZipOutputStream(fW);
        
        //co folder ngoai cung
        File folder = new File(dirName);
        for (String fileName : folder.list()) {
        	File vfile = new File(fileName);
        	if(vfile.isDirectory()){
        		addFolderToZip("", fileName, zip);
        	} else {
        		addFileToZip("", dirName+"/"+fileName, zip, false);
        	}
		}
        
        //ko co folder ngoai cung
        //addFolderToZip("", dirName, zip);
        
        zip.close();
        fW.close();
    }

    private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws IOException {
        File folder = new File(srcFolder);
        if (folder.list().length == 0) {
            addFileToZip(path , srcFolder, zip, true);
        }
        else {
            for (String fileName : folder.list()) {
                if (path.equals("")) {
                    addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip, false);
                } 
                else {
                     addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip, false);
                }
            }
        }
    }

    private void addFileToZip(String path, String srcFile, ZipOutputStream zip, boolean flag) throws IOException {
        File folder = new File(srcFile);
        if (flag) {
            zip.putNextEntry(new ZipEntry(path + "/" +folder.getName() + "/"));
        }
        else {
            if (folder.isDirectory()) {
                addFolderToZip(path, srcFile, zip);
            }
            else {
                byte[] buf = new byte[1024];
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                
                //ko co folder ngoai cung
//                if (path.equals("")) {
//                    zip.putNextEntry(new ZipEntry(folder.getName()));
//                }
//                else {
//                    zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
//                }
                
                ZipEntry zipentry = new ZipEntry(path + "/" + folder.getName());
                if(HelpFunction.isEmpty(path)){
                	zipentry = new ZipEntry(folder.getName());
                }
                //co folder ngoai cung
                zip.putNextEntry(zipentry);
                
                while ((len = in.read(buf)) > 0) {
                    zip.write(buf, 0, len);
                }
                
                in.close();
            }
        }
    }
}
