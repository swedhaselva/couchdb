package com.example.demo.repository;

import java.net.URISyntaxException;

import com.example.demo.model.User;

public interface UserRepository {

User saveUser(User user) throws URISyntaxException;

User getuser(String id);

User  deleteUser(String id,String revision);
User  updateUser(User user );
}
