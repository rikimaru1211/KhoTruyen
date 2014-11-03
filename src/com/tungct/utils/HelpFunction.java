package com.tungct.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HelpFunction {
	
	
//	static String convertStreamToString(java.io.InputStream is) {
//	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
//	    return s.hasNext() ? s.next() : "";
//	}

	public static String slurp(final InputStream is, final int bufferSize)
	{
		final char[] buffer = new char[bufferSize];
		final StringBuilder out = new StringBuilder();
		try {
			final Reader in = new InputStreamReader(is, "UTF-8");
			try {
				for (;;) {
					int rsz = in.read(buffer, 0, buffer.length);
					if (rsz < 0)
						break;
					out.append(buffer, 0, rsz);
				}
			}
			finally {
				in.close();
			}
		}
		catch (UnsupportedEncodingException ex) {
			/* ... */
		}
		catch (IOException ex) {
			/* ... */
		}
		return out.toString();
	}
	
	public static String readFully(InputStream inputStream, String encoding)
	        throws IOException {
	    return new String(readFully(inputStream), encoding);
	}    
	private static byte[] readFully(InputStream inputStream)
	        throws IOException {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    byte[] buffer = new byte[1048576];
	    int length = 0;
	    while ((length = inputStream.read(buffer)) != -1) {
	        baos.write(buffer, 0, length);
	    }
	    return baos.toByteArray();
	}
	
    public static boolean UrlHopLe(String Url){
        /*// cách 1:
        try {
            URL url = new URL(Url);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("HEAD");
            System.out.println(huc.getResponseCode());
            return (huc.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }*/
          
       /* // cách 2:
        try {
            URL url = new URL(Url);
            url.openStream();
              
            return true;
        } catch (IOException e) {
            return false;
        }*/
    	
    	// cách 3:
    	try {
            
            Document doc = Jsoup.connect(Url).userAgent("Mozilla").ignoreHttpErrors(true).timeout(0).get();
            if(doc == null)
            	return false;
            String sTitle = doc.head().text();
            System.out.println(sTitle);
            if(!sTitle.toLowerCase().contains("lỗi 404")){
            	return true;
            } else {
            	return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void SplitFile(String sTenFile, int nKB)throws FileNotFoundException, UnsupportedEncodingException, IOException{
    	String sCurrenDir = System.getProperty("user.dir");
    	String sShellScriptPath = sCurrenDir + "/SplitFileBySize.sh";
        ProcessBuilder pb = new ProcessBuilder(sShellScriptPath, sTenFile, String.valueOf(nKB));
        // sed -i '1s/^/Begin\n\n&/g' tenfile
        //pb.directory(new File(sCurrenDir));
        pb.start();
        
    }
    
    public static String HienThiThoiGian(Long time){
        String hms = String.format("%02d giờ : %02d phút : %02d giây : %02d", TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
                TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)),
                time - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(time)));
        return hms;
    }
	
    public static void writeUTF8Text(String file, String text, boolean append) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        FileOutputStream fo = new FileOutputStream(file, append);
        OutputStreamWriter osw = new OutputStreamWriter(fo, "utf-8");
        osw.write(text);
        osw.flush();
        fo.close();
    }
    
	private static char[] SPECIAL_CHARACTERS = { 'À', 'Á', 'Â', 'Ã', 'È', 'É', 'Ê', 'Ì', 'Í', 'Ò',
        'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â', 'ã', 'è', 'é', 'ê',
        'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý', 'Ă', 'ă', 'Đ', 'đ',
        'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ', 'ạ', 'Ả', 'ả', 'Ấ',
        'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ', 'Ắ', 'ắ', 'Ằ', 'ằ',
        'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ', 'ẻ', 'Ẽ', 'ẽ', 'Ế',
        'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ', 'Ỉ', 'ỉ', 'Ị', 'ị',
        'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ', 'ổ', 'Ỗ', 'ỗ', 'Ộ',
        'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ', 'Ợ', 'ợ', 'Ụ', 'ụ',
        'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự'
    };
  
    private static char[] REPLACEMENTS = { 'A', 'A', 'A', 'A', 'E', 'E', 'E',
        'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a', 'a', 'a',
        'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y', 'A',
        'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u', 'A', 'a',
        'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
        'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E', 'e',
        'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'I',
        'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
        'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
        'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
        'U', 'u'
    };
  
  
    public static String GetTenTruyenTrenWeb(String s) {
    	if(isEmpty(s))
    		return "";
        int maxLength = s.length();
        char[] buffer = new char[maxLength];
        int n = 0;
        for (int i = 0; i < maxLength; i++) {
            char ch = s.charAt(i);
            int index = Arrays.binarySearch(SPECIAL_CHARACTERS, ch);
            if (index >= 0) {
                buffer[n] = REPLACEMENTS[index];
            } else {
                if(ch == ' ')
                    buffer[n] = '-';
                else
                    buffer[n] = ch;
            }
            // skip not printable characters
            /*if (buffer[n] > 31) {
                n++;                
            }*/
            n++;
        }
        // skip trailing slashes
        /*while (n > 0 && buffer[n - 1] == '/') {
            n--;
        }*/
        return String.valueOf(buffer, 0, n);
    }
    
    public static String LayChuoiTimKiem(String sDuLieuDauVao) {
    	if(isEmpty(sDuLieuDauVao))
    		return "";
        String sKetQua = sDuLieuDauVao.toLowerCase();
          
        sKetQua = sKetQua.replaceAll("[^a-z0-9]", "");
      
        return sKetQua;
    }
    
    public static String ChuyenTiengVietKhongDau(String sDuLieuDauVao) {
    	if(isEmpty(sDuLieuDauVao))
    		return "";
        String sKetQua = sDuLieuDauVao;
          
        // Unicode dung san
        sKetQua = sKetQua.replaceAll("á|à|ả|ã|ạ|ă|ắ|ằ|ẳ|ẵ|ặ|â|ấ|ầ|ẩ|ẫ|ậ", "a");
        sKetQua = sKetQua.replaceAll("đ", "d");
        sKetQua = sKetQua.replaceAll("é|è|ẻ|ẽ|ẹ|ê|ế|ề|ể|ễ|ệ", "e");
        sKetQua = sKetQua.replaceAll("í|ì|ỉ|ĩ|ị", "i");
        sKetQua = sKetQua.replaceAll("ó|ò|ỏ|õ|ọ|ô|ố|ồ|ổ|ỗ|ộ|ơ|ớ|ờ|ở|ỡ|ợ", "o");
        sKetQua = sKetQua.replaceAll("ú|ù|ủ|ũ|ụ|ư|ứ|ừ|ử|ữ|ự", "u");
        sKetQua = sKetQua.replaceAll("ý|ỳ|ỷ|ỹ|ỵ", "y");
        sKetQua = sKetQua.replaceAll("Á|À|Ả|Ã|Ạ|Ă|Ắ|Ằ|Ẳ|Ẵ|Ặ|Â|Ấ|Ầ|Ẩ|Ẫ|Ậ", "A");
        sKetQua = sKetQua.replaceAll("Đ", "D");
        sKetQua = sKetQua.replaceAll("É|È|Ẻ|Ẽ|Ẹ|Ê|Ế|Ề|Ể|Ễ|Ệ", "E");
        sKetQua = sKetQua.replaceAll("Í|Ì|Ỉ|Ĩ|Ị", "I");
        sKetQua = sKetQua.replaceAll("Ó|Ò|Ỏ|Õ|Ọ|Ô|Ố|Ồ|Ổ|Ỗ|Ộ|Ơ|Ớ|Ờ|Ở|Ỡ|Ợ", "O");
        sKetQua = sKetQua.replaceAll("Ú|Ù|Ủ|Ũ|Ụ|Ư|Ứ|Ừ|Ử|Ữ|Ự", "U");
        sKetQua = sKetQua.replaceAll("Ý|Ỳ|Ỷ|Ỹ|Ỵ", "Y");
      
        // Unicode To Hop
        sKetQua = sKetQua.replaceAll("á|à|ả|ã|ạ|ă|ắ|ằ|ẳ|ẵ|ặ|â|ấ|ầ|ẩ|ẫ|ậ", "a");
        sKetQua = sKetQua.replaceAll("é|è|ẻ|ẽ|ẹ|ê|ế|ề|ể|ễ|ệ", "e");
        sKetQua = sKetQua.replaceAll("í|ì|ỉ|ĩ|ị", "i");
        sKetQua = sKetQua.replaceAll("ó|ò|ỏ|õ|ọ|ô|ố|ồ|ổ|ỗ|ộ|ơ|ớ|ờ|ở|ỡ|ợ", "o");
        sKetQua = sKetQua.replaceAll("ú|ù|ủ|ũ|ụ|ư|ứ|ừ|ử|ữ|ự", "u");
        sKetQua = sKetQua.replaceAll("ý|ỳ|ỷ|ỹ|ỵ", "y");
      
        sKetQua = sKetQua.replaceAll("Á|À|Ả|Ã|Ạ|Ă|Ắ|Ằ|Ẳ|Ẵ|Ặ|Â|Ấ|Ầ|Ẩ|Ẫ|Ậ", "A");
        sKetQua = sKetQua.replaceAll("É|È|Ẻ|Ẽ|Ẹ|Ê|Ế|Ề|Ề|Ể|Ễ|Ệ", "E");
        sKetQua = sKetQua.replaceAll("Í|Ì|Ỉ|Ĩ|Ị", "I");
        sKetQua = sKetQua.replaceAll("Ó|Ò|Ỏ|Õ|Ọ|Ô|Ố|Ồ|Ổ|Ỗ|Ộ|Ơ|Ớ|Ờ|Ở|Ỡ|Ợ", "O");
        sKetQua = sKetQua.replaceAll("Ú|Ù|Ủ|Ũ|Ụ|Ư|Ứ|Ừ|Ử|Ữ|Ự", "U");
        sKetQua = sKetQua.replaceAll("Ý|Ỳ|Ỷ|Ỹ|Ỵ", "Y");     
      
        return sKetQua;
    }
      
    public static boolean isEmpty(String sDuLieuDauVao) {
        return sDuLieuDauVao == null ? true : sDuLieuDauVao.isEmpty();
    }
	
}
