package br.com.devcase.boot.users.webadmin.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

@Controller
public class VueController {

	@RequestMapping("")
	public View vue(HttpServletRequest request) {
		return new InternalResourceView("WEB-INF/jsp/vue.jsp");
	}
}
