package com.whb.kafka.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@LogAnnotation
    @RequestMapping("/queryAllUser")
    public void queryAllUserWithoutParent(HttpServletRequest request , HttpServletResponse response) {
        /*
         * 在这里，之前是怎么做业务的，还是怎么做业务
         * 以前是怎样调用服务层的，还是怎么去调用服务层
         * 以前该怎样进行输出，现在还是怎样去进行输出
         * */
    }

}
