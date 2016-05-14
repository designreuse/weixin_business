/**
 * 
 */
package com.cht.emm.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.propertyeditors.ClassEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@Controller
public class BaseController {

	@InitBinder
	protected void ininBinder(WebDataBinder binder) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Calendar.class, "mntm", new ClassEditor() {

		});
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
