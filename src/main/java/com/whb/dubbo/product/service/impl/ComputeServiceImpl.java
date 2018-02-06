package com.whb.dubbo.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.whb.dubbo.product.service.ComputeService;

@Service(version = ComputeService.VERSION,interfaceName="com.whb.dubbo.product.service.ComputeService")
public class ComputeServiceImpl implements ComputeService {

	@Override
	public int addNum(int a, int b) {
		// TODO Auto-generated method stub
		return 0;
	}

}
