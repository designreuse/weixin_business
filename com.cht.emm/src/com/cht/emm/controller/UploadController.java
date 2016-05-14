/**
 * 
 */
package com.cht.emm.controller;

import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件
 * 
 * @author Administrator
 * @date 2011-12-6
 */
@Controller
public class UploadController extends BaseController {

	@RequestMapping(value = "/imgupload", method = RequestMethod.POST)
	public @ResponseBody
	String upload(@RequestParam("filedata") MultipartFile filedata) {
		FileOutputStream fos = null;
		try {
			if (filedata == null || filedata.isEmpty()) {
				return "fail";
			}
			System.out.println("FILE名称为:" + filedata.getOriginalFilename());
			fos = new FileOutputStream("D://" + filedata.getOriginalFilename());
			byte[] arrayByte = filedata.getBytes();
			fos.write(arrayByte);
			fos.flush();
			fos.close();
			return "{'err':'','msg':'http://localhost:81/images/"
					+ filedata.getOriginalFilename() + "'}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{'err':'" + e.getMessage()
					+ "','msg':'http://localhost:81/images/"
					+ filedata.getOriginalFilename() + "'}";
		}
	}
}
