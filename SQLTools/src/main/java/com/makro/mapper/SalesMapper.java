package com.makro.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.makro.bean.SalesDetails;

public interface SalesMapper {
	List<SalesDetails> getSalesDetails(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
