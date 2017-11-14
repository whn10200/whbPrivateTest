package com.whb.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.whb.demo.Student;

public interface RemoteServiceInterface extends Remote {

	
	/**
     * 这个RMI接口负责查询目前已经注册的所有用户信息
     */
    public List<Student> queryAllUserinfo() throws RemoteException;
}
