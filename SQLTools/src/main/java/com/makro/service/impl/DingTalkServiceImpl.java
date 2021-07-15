package com.makro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makro.bean.DingTalkProcess;
import com.makro.bean.DingTalkProcessStatus;
import com.makro.mapper.DingTalkProcessMapper;
import com.makro.mapper.DingTalkProcessStatusMapper;
import com.makro.service.DingTalkService;

@Service("dingTalkService")
public class DingTalkServiceImpl implements DingTalkService{

	@Autowired
	private DingTalkProcessStatusMapper processStatusMapper;
	@Autowired
	private DingTalkProcessMapper processMapper;
	
	@Override
	public int addDingTalkProcessStatus(DingTalkProcessStatus processStatus) {
		return processStatusMapper.addDingTalkProcessStatus(processStatus);
	}

	@Override
	public DingTalkProcess getBusIdByInstanceId(String processInstanceId) {
		return processMapper.getBusIdByInstanceId(processInstanceId);
	}

	@Override
	public int updateDingTalkProcess(String processInstanceId, int isValid) {
		return processMapper.updateDingTalkProcess(processInstanceId, isValid);
	}

}
