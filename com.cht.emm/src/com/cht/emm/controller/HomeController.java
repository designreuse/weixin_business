/**
 * 
 */
package com.cht.emm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController extends BaseController {

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin() {

		return "admin";
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String user() {

		return "user";
	}

	@RequestMapping(value = "/notfound", method = RequestMethod.GET)
	public String notfound() {

		return "notfound";
	}

	@RequestMapping(value = "/servererror", method = RequestMethod.GET)
	public String servererror() {

		return "servererror";
	}

	@RequestMapping(value = "/maximumsession", method = RequestMethod.GET)
	public String maximumsession() {

		return "maximumsession";
	}

	@RequestMapping(value = "/userorpassworderror", method = RequestMethod.GET)
	public String userorpassworderror() {

		return "userorpassworderror";
	}
}
