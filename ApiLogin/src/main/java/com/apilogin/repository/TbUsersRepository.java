package com.apilogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apilogin.model.TbUsers;

@Repository
public interface TbUsersRepository extends JpaRepository<TbUsers, Integer>{
	@Query(value="SELECT * FROM TB_USERS WHERE USERNAME =:username",nativeQuery = true)
	TbUsers getDataExistUsername(@Param("username")String username);
	
	@Query(value="SELECT COUNT(*) AS total FROM TB_USERS WHERE USERNAME =:username",nativeQuery = true)
	Integer findbyUsername(@Param("username")String username);
	
	
}
