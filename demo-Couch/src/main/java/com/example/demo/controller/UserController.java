package com.example.demo.controller;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController  {
	
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("/save")
	public String saveUser(@RequestBody User user) {
		
		System.out.println(user);
		
		try {
			userRepo.saveUser(user);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	@GetMapping("/{id}")
	public User getUser(@PathVariable("id") String id ) {
	User user =	userRepo.getuser(id);
		return user;
	}
	
	
	@DeleteMapping("/{id}/{revision}")
	public User deleteUser(@PathVariable("id") String id, @PathVariable("revision") String revision) {
		User user =userRepo.deleteUser(id, revision);
		System.out.println("docs deleted sucessfully");
		return null;
		
	}
	@PutMapping("update")
	public User updateUser(@RequestBody User user) {
		User users=userRepo.updateUser(user);
		System.out.println("updated successfully");
		return users;
		}

}
