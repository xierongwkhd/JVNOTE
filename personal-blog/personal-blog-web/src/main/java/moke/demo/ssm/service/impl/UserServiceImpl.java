package moke.demo.ssm.service.impl;

import moke.demo.ssm.dao.UserMapper;
import moke.demo.ssm.entity.User;
import moke.demo.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by wly on 2017/12/15.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public int regist(User user) {
        return userMapper.insert(user);
    }

    public User login(String name, String password) {
        User user = new User();
        user.setEmail( name );
        user.setPassword( password );
        return userMapper.selectOne( user );
        //return userMapper.findUserByNameAndPwd( name,password );
    }

    public User findByEmail(String email) {
        User user = new User();
        user.setEmail( email );
        return userMapper.selectOne( user );
       // return userMapper.findByEmail(email);
    }

    @Override
    public User findByPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        return userMapper.selectOne(user);
    }

    @Override
    public User findById(Long id) {
        User user = new User();
        user.setId(id);
        return userMapper.selectOne(user);
    }

    public User findByEmailActive(String email) {
        User user = new User();
        user.setEmail( email );
        return userMapper.selectOne( user );
        // return userMapper.findByEmail(email);
    }

    public User findById(String id) {
        User user = new User();
        Long uid = Long.parseLong( id );
        user.setId( uid );
        return userMapper.selectOne( user );
    }

    public User findById(long id) {
        User user = new User();
        user.setId( id );
        return userMapper.selectOne( user );
    }
    @Transactional
    public void deleteByEmail(String email) {
        User user = new User();
        user.setEmail( email );
        userMapper.delete( user );
    }
    @Transactional
    public void deleteByEmailAndFalse(String email) {
        User user = new User();
        user.setEmail( email );
        userMapper.delete( user );
    }
    @Transactional
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective( user );
    }
}
