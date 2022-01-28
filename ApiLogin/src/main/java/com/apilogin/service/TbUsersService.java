package com.apilogin.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apilogin.model.TbUsers;
import com.apilogin.repository.TbUsersRepository;

@Service
public class TbUsersService {
	@Autowired
	private TbUsersRepository usersRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;
	
	public TbUsers registerUser(TbUsers item, Timestamp today) {
		item.setPassword(bcryptEncoder.encode(item.getPassword()));
		item.setCreated_at(today);
		item.setUpdated_at(today);
		TbUsers user = this.usersRepository.save(item);
		return user;
	}
}
