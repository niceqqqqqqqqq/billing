package com.smallserver.pfmp.service.inter;

import com.alibaba.fastjson.JSONObject;
import com.smallserver.pfmp.dal.object.MonitorIpv4InfoDO;

/**
 * Created by WQ on 2017/12/30.
 */
public interface MonitorDataService {

    /**
     * 监控请求IP地址
     */
    void monitorRequesterIpv4(MonitorIpv4InfoDO ipInfo);
    JSONObject queryLoginIpv4Data();
    JSONObject queryUserIpv4Data();
    Boolean existIpv4DataTwoHours(String ip);
    JSONObject queryLog(String seqId);
}
