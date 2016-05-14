/**
 * 
 */
package com.cht.emm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController extends BaseController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(
			@RequestParam(value = "type", required = false) Integer type) {

		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(
			@RequestParam(value = "type", required = false) Integer type) {
		return "login";
	}
	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public String denied() {

		return "denied";
	}

	@RequestMapping(value = "/timeout", method = RequestMethod.GET)
	public String timedout() {

		return "timeout";
	}

}
