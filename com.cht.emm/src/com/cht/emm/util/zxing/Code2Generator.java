package com.cht.emm.util.zxing;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 二维码生成器
 * @author cht
 *
 */
public class Code2Generator {

	private Code2Generator(){}
	@SuppressWarnings("unchecked")
	public static void generaterCode2File(String content,String filePath,int width,int height){
		
		try {
            
		     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		     
		     Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		     hints.put(EncodeHintType.MARGIN,0);
		     BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height,hints);
		     File file = new File(filePath);
		     MatrixToImageWriter.writeToFile(bitMatrix, "png", file);
		     
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
	}
	
	public static void main(String[] args){
		
		long time = new Date().getTime();
		Code2Generator.generaterCode2File("www.baidu.com","D:/test.png",280,280);
		long interval = new Date().getTime()-time;
		
		System.out.println("生成二维码耗时："+interval+" 毫秒");
	}
}


