package com.smallserver.pfmp.service.inter;

import com.alibaba.fastjson.JSONObject;
import com.smallserver.pfmp.dal.object.FeedBackDO;

/**
 * Created by WQ on 2018/2/2.
 */
public interface FeedBackService {

    /**
     * 添加意见反馈
     * @return
     */
    JSONObject add(FeedBackDO feedBackDO);

    /**
     * 查询列表
     * @return
     */
    JSONObject queryAll();

    /**
     * 更新状态
     * @param id
     * @return
     */
    boolean updateStatus(Integer id);
}
