package com.makro.service;

import com.makro.bean.DingTalkProcess;
import com.makro.bean.DingTalkProcessStatus;

public interface DingTalkService {
	int addDingTalkProcessStatus(DingTalkProcessStatus processStatus);
	DingTalkProcess getBusIdByInstanceId(String processInstanceId);
	int updateDingTalkProcess(String processInstanceId,int isValid);
}
