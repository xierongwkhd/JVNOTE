package com.moke.Demo.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.moke.Demo.Domain.PUser;

@Mapper
public interface PUserDao {
	
	@Select("select * from t_user where id = #{id}")
	public PUser getById(@Param("id") long id);

}
