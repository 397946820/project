package com.it.ocs.pic.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImageUtil {
	static BASE64Encoder encoder = new BASE64Encoder();   
	static BASE64Decoder decoder = new BASE64Decoder();
	
	public static String getImageBinary(String path){ 
        File f = new File(path);          
        BufferedImage bi;   
        try {   
            bi = ImageIO.read(f);   
            ByteArrayOutputStream baos = new ByteArrayOutputStream();   
            ImageIO.write(bi, "jpg", baos);   
            byte[] bytes = baos.toByteArray();   
               
            return encoder.encodeBuffer(bytes).trim();   
        } catch (IOException e) { 
        	System.out.println(path);
            e.printStackTrace();   
        }   
        return null;   
   } 
	public static byte[] getFileByte(String path){
		File f = new File(path);
		BufferedImage bi;
		try {
			  bi = ImageIO.read(f);   
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();   
	            ImageIO.write(bi, "jpg", baos);   
	            byte[] bytes = baos.toByteArray();   
	               
	            return bytes;
		} catch (Exception e) {

			return null;
		}
		
	}
	 static void base64StringToImage(String base64String){   
	        try {   
	            byte[] bytes1 = decoder.decodeBuffer(base64String);   
	               
	            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);   
	            BufferedImage bi1 =ImageIO.read(bais);   
	            //File w2 = new File("c://QQ.bmp");//可以是jpg,png,gif格式   
	          //  ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动   
	        } catch (IOException e) {   
	            e.printStackTrace();   
	        }   
	    }  
}
