package com.smallserver.pfmp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.smallserver.pfmp.common.JsonResult;
import com.smallserver.pfmp.dal.inter.UserMessageInfoDao;
import com.smallserver.pfmp.dal.object.UserMessageInfoDO;
import com.smallserver.pfmp.service.inter.UserMessageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by WQ on 2017/12/31.
 */
@Service("userMessageInfoService")
public class UserMessageInfoServiceImpl implements UserMessageInfoService {

    @Autowired
    private UserMessageInfoDao userMessageInfoDao;

    @Override
    public JSONObject send(UserMessageInfoDO message) {
        if (userMessageInfoDao.send(message)){
            return JsonResult.getSuccess();
        }
        return JsonResult.getError("505", "消息发送失败");
    }

    @Override
    public JSONObject updateToRead(Integer id) {
        if (userMessageInfoDao.updateToRead(id)){
            return JsonResult.getSuccess();
        }
        return JsonResult.getError("505", "消息状态更新失败");
    }

    @Override
    public JSONObject batchSend(List<UserMessageInfoDO> messageList) {
        if (userMessageInfoDao.batchSend(messageList)){
            return JsonResult.getSuccess();
        }
        return JsonResult.getError("505", "消息批量发送失败");
    }

    @Override
    public JSONObject query(Map<String, Object> map) {
        List<UserMessageInfoDO> list = userMessageInfoDao.query(map);
        return JsonResult.getSuccess(list);
    }

    @Override
    public JSONObject queryNewMessageCount(String loginId) {
        int count = userMessageInfoDao.queryCount(loginId);
        return JsonResult.getSuccess(count);
    }
}
