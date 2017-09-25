package br.com.devcase.boot.users.webadmin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

@Controller
public class VueController {

	@RequestMapping("/vue/**")
	public View vue() {
		return new InternalResourceView("/vue.html");
	}
}
