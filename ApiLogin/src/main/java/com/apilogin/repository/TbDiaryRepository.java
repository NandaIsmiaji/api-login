package com.apilogin.repository;

import com.apilogin.model.TbDiary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbDiaryRepository extends JpaRepository<TbDiary, Integer>{
    
}
