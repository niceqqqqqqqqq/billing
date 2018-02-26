package com.smallserver.pfmp.controller;

import com.alibaba.fastjson.JSONObject;
import com.smallserver.pfmp.common.AsyncUtil;
import com.smallserver.pfmp.common.JsonResult;
import com.smallserver.pfmp.dal.object.MonitorIpv4InfoDO;
import com.smallserver.pfmp.service.inter.MonitorDataService;
import com.smallserver.pfmp.util.AjaxModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by WQ on 2017/12/30.
 */
@Controller
@RequestMapping(value = "/monitor")
public class MonitorDataController extends AjaxModel {

    @Autowired
    private MonitorDataService monitorDataService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;

    @GetMapping(value = "/ipv4/{type}")
    public void getMonitorIpv4Data(@PathVariable("type") String type) {
        if (StringUtils.isEmpty(type)) {
            print(JsonResult.getError("401", "ip监控数据获取失败，类型不能为空"));
            return;
        }
        if (type.equalsIgnoreCase("login")) {
            print(monitorDataService.queryLoginIpv4Data());
            return;
        } else {
            print(monitorDataService.queryUserIpv4Data());
            return;
        }
    }

    /**
     * 获取个人登录日志
     */
    @GetMapping(value = "/log/login")
    public void queryLogBylogin() {
        String seqId;
        try {
            seqId = (String) session.getAttribute("seqId");
        } catch (Exception e){
            print(JsonResult.getError("404", "登录会话失效"));
            return;
        }
        print(monitorDataService.queryLog(seqId));
    }

    /**
     * 获取登录日志
     */
    @GetMapping(value = "/log/login.all")
    public void queryAllLogBylogin() {
        print(monitorDataService.queryLog("all"));
    }

    @GetMapping(value = "/user/ipv4")
    public void monitorUserIpv4Data() {
        String ipInfoStr = request.getParameter("ipInfo");
        if (StringUtils.isEmpty(ipInfoStr)) {
            print(JsonResult.getError("404", "未获取到ip信息"));
            return;
        }
        JSONObject ipv4;
        String ip, addressType, address, type;
        String[] addressTypes = new String[2];
        try {
            ipv4 = (JSONObject) JSONObject.parse(ipInfoStr);
            ip = String.valueOf(ipv4.get("ip"));
            addressType = String.valueOf(ipv4.get("address"));
            addressTypes = addressType.split(" ");
            address = addressTypes[0];
            type = addressTypes[1];
        } catch (Exception e) {
            print(JsonResult.getError("404", "ip信息解析异常"));
            return;
        }
        if (StringUtils.isEmpty(ip) || StringUtils.isEmpty(address)) {
            print(JsonResult.getError("404", "ip信息获取有误"));
            return;
        }
        // 异步调用监控登录ip、时间点
        AsyncUtil.execute(new Runnable() {
            @Override
            public void run() {
                MonitorIpv4InfoDO ipInfo = new MonitorIpv4InfoDO();
                ipInfo.setType("user");
                ipInfo.setIpv4(ip);
                ipInfo.setAddress(address);
                ipInfo.setNetType(type);
                if (monitorDataService.existIpv4DataTwoHours(ip)) {
                    monitorDataService.monitorRequesterIpv4(ipInfo);
                }
            }
        });
        if (session.getAttribute("ip") == null || !ip.equals(session.getAttribute("ip").toString())) {
            session.setAttribute("ip", ip);
            session.setAttribute("address", address);
            session.setAttribute("net_type", type);
        }
        print(JsonResult.getSuccess(address));
        return;
    }
}
