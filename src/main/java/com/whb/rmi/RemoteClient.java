package com.whb.rmi;

import java.rmi.Naming;
import java.util.List;

import com.whb.demo.Student;

public class RemoteClient {

	public static void main(String[] args) throws Exception {
        // 您看，这里使用的是java名称服务技术进行的RMI接口查找。
        RemoteServiceInterface remoteServiceInterface = (RemoteServiceInterface)Naming.lookup("rmi://127.0.0.1:1099/queryAllUserinfo");
        List<Student> users = remoteServiceInterface.queryAllUserinfo();
 
        System.out.println(users.get(0).toString());
    }

}
