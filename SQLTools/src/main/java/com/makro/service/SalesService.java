package com.makro.service;

import java.util.List;

import com.makro.bean.SalesDetails;

public interface SalesService {
	List<SalesDetails> getSalesDetails(String startDate, String endDate);
}
