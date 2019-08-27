package com.moke.Demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moke.Demo.Dao.UserDao;
import com.moke.Demo.Domain.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;

	public User getById(int id) {
		return userDao.getById(id);
	}
	
	@Transactional
	public boolean tx() {
		User user1 = new User();
		user1.setId(3);
		user1.setName("moke3");
		userDao.insert(user1);
		
		User user2 = new User();
		user2.setId(1);
		user2.setName("moke1");
		userDao.insert(user2);
		
		return true;
	}
}
