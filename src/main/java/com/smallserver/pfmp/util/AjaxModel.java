package com.smallserver.pfmp.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by WQ on 2017/12/10.
 */
public class AjaxModel {

    private final static Logger logger = LoggerFactory.getLogger(AjaxModel.class);

    @Autowired
    private HttpServletResponse response;


    public void print(Object data){
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf8");
            out = response.getWriter();
            JSONObject json = (JSONObject) data;
            out.print("("+json.toJSONString()+")");
        } catch (Exception e){
            logger.error("print api result error. e={}", e);
            JSONObject json = new JSONObject();
            json.put("success", false);
            json.put("code", "500");
            json.put("desc", "内部执行错误");
            out.print("(" + json.toJSONString() + ")");
        } finally {
            out.flush();
            out.close();
        }
        return;
    }
}
