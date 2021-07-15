package com.makro.mapper;

import com.makro.bean.DingTalkProcess;

public interface DingTalkProcessMapper {
	DingTalkProcess getBusIdByInstanceId(String processInstanceId);
	int updateDingTalkProcess(String processInstanceId,int isValid);
}
