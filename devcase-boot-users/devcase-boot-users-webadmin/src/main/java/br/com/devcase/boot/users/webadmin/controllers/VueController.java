package br.com.devcase.boot.users.webadmin.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VueController {

	@RequestMapping("")
	public String vue(HttpServletRequest request, Model model) {
		model.addAttribute("_csrf", request.getAttribute("_csrf"));
		return "webadmin/vue";
	}
}
