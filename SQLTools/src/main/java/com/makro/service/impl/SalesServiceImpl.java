package com.makro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makro.bean.SalesDetails;
import com.makro.mapper.SalesMapper;
import com.makro.service.SalesService;
@Service("salesService")
public class SalesServiceImpl implements SalesService{
	@Autowired
	private SalesMapper salesMapper;
	@Override
	public List<SalesDetails> getSalesDetails(String startDate, String endDate) {
		return salesMapper.getSalesDetails(startDate, endDate);
	}
}
