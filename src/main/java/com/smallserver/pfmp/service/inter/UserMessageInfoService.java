package com.smallserver.pfmp.service.inter;

import com.alibaba.fastjson.JSONObject;
import com.smallserver.pfmp.dal.object.UserMessageInfoDO;
import java.util.List;
import java.util.Map;

/**
 * Created by WQ on 2017/12/31.
 */
public interface UserMessageInfoService {

    JSONObject send(UserMessageInfoDO message);
    JSONObject updateToRead(Integer id);
    JSONObject batchSend(List<UserMessageInfoDO> messageList);
    JSONObject query(Map<String, Object> map);
    JSONObject queryNewMessageCount(String loginId);
}
