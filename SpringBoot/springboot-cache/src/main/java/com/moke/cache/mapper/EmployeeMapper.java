package com.moke.cache.mapper;

import com.moke.cache.bean.Employee;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EmployeeMapper {
    @Select("select * from employee where id = #{id}")
    public Employee getEmpById(Integer id);

    @Update("update employee set lastName=#{lastName},email=#{email},gender=#{gender},d_id=#{dId} where id=#{id}")
    public void updateEmp(Employee employee);

    @Delete("delete from employee where id=#{id}")
    public void deleteById(Integer id);

    @Insert("insert into employee(lastNmae,email,gender,d_id) value(#{lastName},#{email},#{gender},#{dId})")
    public void insertEmployee(Employee employee);
}
