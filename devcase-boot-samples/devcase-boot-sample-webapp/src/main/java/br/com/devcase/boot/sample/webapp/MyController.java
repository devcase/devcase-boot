package br.com.devcase.boot.sample.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my")
public class MyController {

	@RequestMapping("")
	public String index() {
		return "my/index";
	}
}
