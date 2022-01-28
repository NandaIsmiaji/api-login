package com.apilogin.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apilogin.model.TbUsers;
import com.apilogin.repository.TbUsersRepository;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService{
	@Autowired
	private TbUsersRepository tbUsersRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TbUsers user = tbUsersRepository.getDataExistUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new User(user.getUsername(),user.getPassword(),getAuthority(user));
	}
	
	private Set<SimpleGrantedAuthority> getAuthority(TbUsers user){
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		return authorities;
	}
}
