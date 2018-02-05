package com.whb.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whb.ssm.dao.IUserDao;
import com.whb.ssm.entity.User;
import com.whb.ssm.service.IUserService;

@Service("userService")
@Transactional  //在类上添加事务注解，对所有的方法起作用
public class IUserServiceImpl  implements IUserService{

	@Autowired
	public IUserDao udao;
	
	@Override
	public User getUserById(int id) {
		return udao.selectByPrimaryKey(id);
	}

}
