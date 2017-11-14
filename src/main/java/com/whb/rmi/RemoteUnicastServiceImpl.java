package com.whb.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.whb.demo.Student;

/**
 * RMI 服务接口RemoteServiceInterface的具体实现<br>
 * 请注意这里继承的是UnicastRemoteObject父类。
 * 继承于这个父类，表示这个Remote Object是“存在于本地”的RMI服务实现
 * （这句话后文会解释）
 * @author yinwenjie
 *
 */
public class RemoteUnicastServiceImpl extends UnicastRemoteObject implements RemoteServiceInterface {
    /**
     * 注意Remote Object没有默认构造函数
     * @throws RemoteException
     */
    protected RemoteUnicastServiceImpl() throws RemoteException {
        super();
    }

    private static final long serialVersionUID = 6797720945876437472L;

    @Override
    public List<Student> queryAllUserinfo() throws RemoteException {
        List<Student> users = new ArrayList<Student>();

        Student user1 = new Student(11, "11");
        users.add(user1);

        Student user2 = new Student(22, "22");
        users.add(user2);
        return users;
    }
}
