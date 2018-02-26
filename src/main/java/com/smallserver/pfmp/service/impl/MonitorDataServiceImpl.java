package com.smallserver.pfmp.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.smallserver.pfmp.common.JsonResult;
import com.smallserver.pfmp.dal.inter.MonitorIpv4InfoDao;
import com.smallserver.pfmp.dal.object.MonitorIpv4InfoDO;
import com.smallserver.pfmp.service.inter.MonitorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by WQ on 2017/12/30.
 */
@Service("monitorDataService")
public class MonitorDataServiceImpl implements MonitorDataService {

    @Autowired
    private MonitorIpv4InfoDao monitorIpv4InfoDao;

    @Override
    public void monitorRequesterIpv4(MonitorIpv4InfoDO ipInfo) {
        monitorIpv4InfoDao.addMonitor(ipInfo);
    }

    @Override
    public JSONObject queryLoginIpv4Data() {
        List<MonitorIpv4InfoDO> list = monitorIpv4InfoDao.queryLoginIp();
        return JsonResult.getSuccess(list);
    }

    @Override
    public JSONObject queryUserIpv4Data() {
        List<MonitorIpv4InfoDO> list = monitorIpv4InfoDao.queryUserIp();
        return JsonResult.getSuccess(list);
    }

    @Override
    public Boolean existIpv4DataTwoHours(String ip) {
        Timestamp time = new Timestamp(System.currentTimeMillis() - 2*60*60*1000);
        List<MonitorIpv4InfoDO> list = monitorIpv4InfoDao.existTwoHours(ip, time);
        if (CollectionUtils.isEmpty(list) || list.size() == 0){
            return true;
        }
        return false;
    }

    @Override
    public JSONObject queryLog(String seqId) {
        List<MonitorIpv4InfoDO> listIp = new ArrayList<>();
        List<MonitorIpv4InfoDO> list = monitorIpv4InfoDao.queryLogByLogin(seqId);
        if (CollectionUtils.isEmpty(list) || list.size() == 0){
            return JsonResult.getSuccess(Collections.EMPTY_LIST);
        } else {
            for (MonitorIpv4InfoDO ipv4InfoDO : list){
                if (!StringUtils.isEmpty(ipv4InfoDO.getIpv4())){
                    listIp.add(ipv4InfoDO);
                }
            }
        }
        return JsonResult.getSuccess(listIp);
    }
}
