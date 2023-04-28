package com.example.EWDOpdracht;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import repository.AdminRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminRepository repository;
	
	@GetMapping
	public String listAdmin(Model model) {
		model.addAttribute("adminList", repository.findAll());
		model.addAttribute("adminName", repository.findByName("Karina"));
		model.addAttribute("adminFirstName", repository.findByFirstName("Ibisheva"));
		return "admin";
	}
}
