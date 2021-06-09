package com.example.api.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/account")
public class UserManagementController {
 
	@GetMapping("/check")
	public String check() {
		return "Working";
	}
}
