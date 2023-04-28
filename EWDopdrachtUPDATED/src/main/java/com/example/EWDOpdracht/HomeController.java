package com.example.EWDOpdracht;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import repository.AdminRepository;

@Controller
@RequestMapping("/home")
public class HomeController {


	
	@GetMapping
	public String home(Model model) {

		return "home";
	}
}
