package com.apilogin.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apilogin.model.TbDiary;
import com.apilogin.repository.TbDiaryRepository;
import com.apilogin.service.TbDiaryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping(value="/v1")
@Api(description = "API Diary")
public class TbDiaryController {
	String success = "Success";
	String failed = "Fail";
	Timestamp today = new Timestamp(System.currentTimeMillis());
	
	@Autowired
	private TbDiaryRepository tbDiaryRepository;
	
	@Autowired
	private TbDiaryService tbDiaryService;
	
	@PostMapping(value="/diary-new")
	@ApiOperation(value = "Create new diary")
	private ResponseEntity<?> createNew(@RequestBody TbDiary tbDiary){
		Map<String, Object> body = new HashMap<>();
		try {
			TbDiary saveUser = this.tbDiaryService.createNew(tbDiary, today);
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
	
	@PutMapping(value="/diary-update/{idDiary}")
	@ApiOperation(value = "Update existing diary")
	private ResponseEntity<?> updateExisting(@PathVariable("idDiary")Integer idDiary, @RequestBody TbDiary reqDiary){
		 Map<String, Object> body = new HashMap<>();
		 try {
			if(this.tbDiaryRepository.findById(idDiary).isPresent()) {
				TbDiary data = this.tbDiaryService.updateExisting(idDiary,reqDiary, today);
				body.put("status", success);
				body.put("data", data);
				body.put("message", "Diary has been updated successfully");
				return new ResponseEntity<>(body, HttpStatus.OK); 
			}else {
				body.put("status", failed );
				body.put("message", "Data Diary null");
				return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			body.put("status", failed );
			body.put("message", e.getMessage());
			return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value="/diary-delete/{idDiary}")
	@ApiOperation(value = "Delete diary")
	private ResponseEntity<?> delete(@PathVariable("idDiary") Integer idDiary){
		Map<String, Object> bodyDelete = new HashMap<>();
		try {
			this.tbDiaryService.deleteExisting(idDiary);
			bodyDelete.put("status", success);
			bodyDelete.put("message", "Diary "+ idDiary +"has been deleted successfully");
			return new ResponseEntity<>(bodyDelete,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			bodyDelete.put("status", failed);
			bodyDelete.put("message", "Delete Diary failed");
			return new ResponseEntity<>(bodyDelete,HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value="/diary-list")
	@ApiOperation(value = "List Diary")
	private ResponseEntity<?> listDiary(){
		Map<String, Object> body = new HashMap<>();
		List<TbDiary> data = this.tbDiaryService.listDiary();
		body.put("data", data);
		return new ResponseEntity<>(body,HttpStatus.OK);
	}
}
