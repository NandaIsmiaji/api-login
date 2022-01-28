package com.apilogin.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apilogin.model.TbDiary;
import com.apilogin.repository.TbDiaryRepository;

@Service
public class TbDiaryService {
	@Autowired
	private TbDiaryRepository tbDiaryRepository;
	
	public TbDiary createNew(TbDiary tbDiary,Timestamp today) {
		tbDiary.setCreated_at(today);
		tbDiary.setUpdated_at(today);
		TbDiary data = this.tbDiaryRepository.save(tbDiary);
		return data;
	}
	
	public TbDiary updateExisting(Integer idDiary,TbDiary tbDiary,Timestamp today) {
		TbDiary dataExisting = this.tbDiaryRepository.findById(idDiary).get();
		dataExisting.setTitle(tbDiary.getTitle());
		dataExisting.setBody(tbDiary.getBody());
		dataExisting.setUpdated_at(today);
		TbDiary data = this.tbDiaryRepository.save(dataExisting);
		return data;
	}
	
	public Map<String,Object> deleteExisting(Integer idDiary){
		this.tbDiaryRepository.deleteById(idDiary);
		Map<String, Object> response = new HashMap<>();
		response.put("status", "Success");
		response.put("message", "Diary "+ idDiary +"has been deleted successfully");
		return response;
	}
	
	public List<TbDiary> listDiary(){
		List<TbDiary> data = this.tbDiaryRepository.findAll();
		return data;
	}
}
