package com.makro.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.makro.bean.SalesDetails;
import com.makro.config.DataSourceConstants;
import com.makro.config.DynamicDataSourceContextHolder;
import com.makro.config.LogFilter;
import com.makro.service.SalesService;
import com.makro.utils.Result;
import com.makro.utils.ResultFactory;

@Controller
public class SaleController {
	@Autowired
	private SalesService salesService;
	@CrossOrigin
	@RequestMapping(value = "/api/goodsdetails", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	@LogFilter("销售商品明细")
	public Result getGoodsDetails(@RequestBody Map<String, String> dateStr) {
		DynamicDataSourceContextHolder.setContextKey(DataSourceConstants.DS_KEY_POS);
		List<SalesDetails> list = salesService.getSalesDetails(dateStr.get("startDate"), dateStr.get("endDate"));
		return ResultFactory.buildSuccessResult(list);
	}
}
