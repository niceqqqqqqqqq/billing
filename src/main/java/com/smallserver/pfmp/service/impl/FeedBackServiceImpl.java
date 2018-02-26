package com.smallserver.pfmp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.smallserver.pfmp.common.JsonResult;
import com.smallserver.pfmp.dal.inter.FeedBackDao;
import com.smallserver.pfmp.dal.object.FeedBackDO;
import com.smallserver.pfmp.service.inter.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by WQ on 2018/2/11.
 */
@Service("feedBackService")
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private FeedBackDao feedBackDao;

    @Override
    public JSONObject add(FeedBackDO feedBackDO) {
        if (feedBackDao.insert(feedBackDO)) {
            return JsonResult.getSuccess();
        }
        return JsonResult.getError("302", "意见反馈失败");
    }

    @Override
    public JSONObject queryAll() {
        return JsonResult.getSuccess(feedBackDao.query());
    }

    @Override
    public boolean updateStatus(Integer id) {
        return feedBackDao.update(id);
    }
}
