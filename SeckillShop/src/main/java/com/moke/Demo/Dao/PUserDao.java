package com.moke.Demo.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.moke.Demo.Domain.PUser;

@Mapper
public interface PUserDao {
	
	@Select("select * from t_user where id = #{id}")
	public PUser getById(@Param("id") long id);

	@Update("update t_user set password = #{password} where id = #{id}")
	public void update(PUser upUser);

}
