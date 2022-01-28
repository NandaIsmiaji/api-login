package com.apilogin.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apilogin.model.TbUsers;
import com.apilogin.repository.TbUsersRepository;
import com.apilogin.security.LoginUser;
import com.apilogin.security.TokenProvider;
import com.apilogin.service.TbUsersService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping(value="/v1/user")
@Api(description = "API Users")
public class TbUsersController {
	String success = "Success";
	String failed = "Fail";
	Timestamp today = new Timestamp(System.currentTimeMillis());
	@Autowired
	private TbUsersService tbUsersService;
	
	@Autowired
	private TbUsersRepository tbUsersRepository;
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;
	
	@PostMapping(value="/register")
	@ApiOperation(value = "Register user")
	private ResponseEntity<?> registerUser(@RequestBody TbUsers item){
		Map<String, Object> body = new HashMap<>();
		try {
			TbUsers saveUser = this.tbUsersService.registerUser(item, today);
			body.put("status", success);
			body.put("data", saveUser);
			return new ResponseEntity<>(body, HttpStatus.OK); 
		} catch (Exception e) {
			e.printStackTrace();
			body.put("status", failed );
			body.put("message", e.getMessage());
			return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
		}
	}
	
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "Login user")
    public ResponseEntity<?> login(@RequestBody LoginUser loginUser) throws AuthenticationException, JsonProcessingException{
    	Map<String, Object> body = new HashMap<>(); 
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )                        
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        Date expired = jwtTokenUtil.getExpirationDateFromToken(token);
        Timestamp expired_time = new Timestamp(expired.getTime());

        // GET DATA USER LOGIN  
        String username = loginUser.getUsername();
        TbUsers user = this.tbUsersRepository.getDataExistUsername(username);
  
        Map<String, String> tokenAndExpired = new HashMap<>();
        tokenAndExpired.put("access_token", token);
        tokenAndExpired.put("expires_time", expired_time.toString());
        
        Map<String, Object> objectTokenAndExpired = new HashMap<>();
        objectTokenAndExpired.put("user", user);
        objectTokenAndExpired.put("token", tokenAndExpired);
        
        body.put("status", "success");
        body.put("data", objectTokenAndExpired);
        return new ResponseEntity<>(body, HttpStatus.OK); 
    }
}
