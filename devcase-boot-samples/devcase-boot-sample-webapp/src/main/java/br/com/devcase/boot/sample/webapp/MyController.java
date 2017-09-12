package br.com.devcase.boot.sample.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my")
public class MyController {

	@RequestMapping("")
	public String index(Model model) {
		model.addAttribute("testtext", "TEXT TEXT TEXT");
		return "my/index";
	}
}
