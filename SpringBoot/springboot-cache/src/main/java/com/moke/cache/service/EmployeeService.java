package com.moke.cache.service;

import com.moke.cache.bean.Employee;
import com.moke.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    /*
    @Cacheable的几个属性
        cacheNmaes/value：指定缓存组件的名字
        key：缓存数据使用的key，默认是使用方法参数的值
        keyGenerator：key的生成器，也可以自己指定
        cacheManager/cacheResolver：指定缓存管理器/缓存解析器
        condition：指定符合条件的情况下，才进行缓存
            condition = "#id>0",
        unless：否定缓存，当unless指定的条件为true，不会被缓存，可以获取到结果进行判断
            unless = "#result==null"
        sync：是由使用异步模式
     */
    @Cacheable(cacheNames = "emp")
    public Employee getEmp(Integer id){
        return employeeMapper.getEmpById(id);
    }

    @CachePut(value = "emp",key = "#employee.id")
    public Employee updateEmp(Employee employee){
        employeeMapper.updateEmp(employee);
        return employee;
    }

    @CacheEvict(value = "emp",key = "#id")
    public void deleteEmp(Integer id){
        employeeMapper.deleteById(id);
    }
}
