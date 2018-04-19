package lt.zerum8.wcj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	private static final String HOME_PAGE_NAME = "index";

	@RequestMapping(value = "/")
	public String proceedToHomePage() {
		return HOME_PAGE_NAME;
	}

}