package com.smallserver.pfmp.controller;

import com.alibaba.fastjson.JSONObject;
import com.smallserver.pfmp.common.AsyncUtil;
import com.smallserver.pfmp.common.JsonResult;
import com.smallserver.pfmp.dal.object.BillingRecordInfoDO;
import com.smallserver.pfmp.dal.object.UserInfoDO;
import com.smallserver.pfmp.dal.object.UserMessageInfoDO;
import com.smallserver.pfmp.enums.BillingTypeEnum;
import com.smallserver.pfmp.enums.FrequencyEnum;
import com.smallserver.pfmp.service.inter.BillingRecordInfoService;
import com.smallserver.pfmp.service.inter.UserMessageInfoService;
import com.smallserver.pfmp.util.AjaxModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by WQ on 2017/11/29.
 *
 */
@Controller
@RequestMapping("/billing")
public class BillingRecordController extends AjaxModel {

    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private BillingRecordInfoService billingRecordInfoService;
    @Autowired
    private UserMessageInfoService userMessageInfoService;

    private static final Integer DAFULT_PAGE_OFFSET = 0; // 默认从第一个位置开始分页查询
    private static final Integer DAFULT_PAGE_SIZE = 10; // 默认每页显示10条

    @GetMapping(value = "/query")
    public void queryByType() {
        Integer type = Integer.parseInt(request.getParameter("type"));
        String seqId = session.getAttribute("seqId").toString();
        JSONObject result = billingRecordInfoService.queryInComeRecordBySeqId(seqId, type);
        print(result);
    }

    @GetMapping(value = "/count/query")
    public String queryCount() {
        Integer type = Integer.parseInt(request.getParameter("type"));
        String seqId = session.getAttribute("seqId").toString();
        if (session.getAttribute("seqId") == null) {
            return "forward:/";
        }
        Map<String, Integer> result = new HashMap<>();
        List<Integer> list = handleBillingCount(seqId, type);
        result.put(FrequencyEnum.seven.name(), list.get(0) == null ? 0 : list.get(0));
        result.put(FrequencyEnum.month.name(), list.get(1) == null ? 0 : list.get(1));
        result.put(FrequencyEnum.year.name(), list.get(2) == null ? 0 : list.get(2));
        result.put(FrequencyEnum.all.name(), list.get(3) == null ? 0 : list.get(3));
        print(JsonResult.getSuccess(result));
        return null;
    }

    @GetMapping(value = "/analysis.data/query")
    public String queryAnalysisDate() {
        Integer days = Integer.parseInt(request.getParameter("days"));
        String seqId = session.getAttribute("seqId").toString();
        if (session.getAttribute("seqId") == null) {
            return "forward:/";
        }
        List<BillingRecordInfoDO> result = billingRecordInfoService.queryRecord(seqId, 1, days, DAFULT_PAGE_OFFSET, DAFULT_PAGE_SIZE);
        Map<String, Object> map = new HashMap<>();
        String[] xNames = {BillingTypeEnum.PAYROLL.getDesc(), BillingTypeEnum.FAMILY_ORIGIN.getDesc(), BillingTypeEnum.ACCOUNT_RECEIVABLE.getDesc(), BillingTypeEnum.BONUS.getDesc(), BillingTypeEnum.BORROW.getDesc(), BillingTypeEnum.HOLIDAY.getDesc(), BillingTypeEnum.OTHER.getDesc()};

        BigDecimal[] yDatas = {new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0)};

        map.put("xNames", xNames);
        map.put("yDatas", yDatas);


        if (result == null) {
            print(JsonResult.getSuccess(map));
            return null;
        }
        for (BillingRecordInfoDO billingRecordInfoDO : result) {
            switch (BillingTypeEnum.getName(billingRecordInfoDO.getBillingType())) {
                case "PAYROLL":
                    String num1 = (billingRecordInfoDO.getMoneyNum() == null) ? "0.00" : billingRecordInfoDO.getMoneyNum();
                    yDatas[0] = new BigDecimal(num1).add(yDatas[0]);
                    break;
                case "FAMILY_ORIGIN":
                    String num2 = (billingRecordInfoDO.getMoneyNum() == null) ? "0.00" : billingRecordInfoDO.getMoneyNum();
                    yDatas[1] = new BigDecimal(num2).add(yDatas[1]);
                    break;
                case "ACCOUNT_RECEIVABLE":
                    String num3 = (billingRecordInfoDO.getMoneyNum() == null) ? "0.00" : billingRecordInfoDO.getMoneyNum();
                    yDatas[2] = new BigDecimal(num3).add(yDatas[2]);
                    break;
                case "BONUS":
                    String num4 = (billingRecordInfoDO.getMoneyNum() == null) ? "0.00" : billingRecordInfoDO.getMoneyNum();
                    yDatas[3] = new BigDecimal(num4).add(yDatas[3]);
                    break;
                case "BORROW":
                    String num5 = (billingRecordInfoDO.getMoneyNum() == null) ? "0.00" : billingRecordInfoDO.getMoneyNum();
                    yDatas[4] = new BigDecimal(num5).add(yDatas[4]);
                    break;
                case "HOLIDAY":
                    String num6 = (billingRecordInfoDO.getMoneyNum() == null) ? "0.00" : billingRecordInfoDO.getMoneyNum();
                    yDatas[5] = new BigDecimal(num6).add(yDatas[5]);
                    break;
                case "OTHER":
                    String num7 = (billingRecordInfoDO.getMoneyNum() == null) ? "0.00" : billingRecordInfoDO.getMoneyNum();
                    yDatas[6] = new BigDecimal(num7).add(yDatas[6]);
                    break;
            }
        }
        map.put("yDatas", yDatas);
        print(JsonResult.getSuccess(map));
        return null;
    }

    @GetMapping(value = "/record/query")
    public String queryRecord() {
        Integer type = Integer.parseInt(request.getParameter("type"));
        Integer days = Integer.parseInt(request.getParameter("days"));
        String seqId = session.getAttribute("seqId").toString();
        if (session.getAttribute("seqId") == null) {
            return "forward:/";
        }
        List<BillingRecordInfoDO> result = billingRecordInfoService.queryRecord(seqId, type, days, DAFULT_PAGE_OFFSET, DAFULT_PAGE_SIZE);
        print(JsonResult.getSuccess(result));
        return null;
    }

    @PostMapping(value = "/add")
    public String addRecord() {
        if (session.getAttribute("seqId") == null) {
            return "forward:/";
        }
        String addTime = request.getParameter("add_time");
        Integer type = Integer.parseInt(request.getParameter("type"));
        String billingType = request.getParameter("billing_type");
        String moneyDesc = request.getParameter("money_desc");
        BigDecimal moneyNum;
        try {
            moneyNum = new BigDecimal(request.getParameter("money_num").toString());
            if (moneyNum.compareTo(new BigDecimal(0)) == -1){
                print(JsonResult.getError("401", "金额格式有误，应该大于0"));
            }
        } catch (Exception e) {
            print(JsonResult.getError("401", "金额格式有误，应为数字"));
            return null;
        }
        String seqId = session.getAttribute("seqId").toString();
        BillingRecordInfoDO billingRecordInfoDO = new BillingRecordInfoDO(type, seqId, billingType, java.sql.Date.valueOf(addTime), moneyNum, moneyDesc);
        JSONObject result = billingRecordInfoService.addRecord(billingRecordInfoDO);
        if ((Boolean) result.get("success")) {
            // 发消息
            UserInfoDO createUser = (UserInfoDO) session.getAttribute("user");
            UserMessageInfoDO userMessageInfoDO = new UserMessageInfoDO();
            userMessageInfoDO.setReceiver(createUser.getPickName());
            userMessageInfoDO.setSender("系统");
            userMessageInfoDO.setType("添加账单");
            userMessageInfoDO.setTitle("添加一笔收入记录");
            userMessageInfoDO.setContent("账号：" + createUser.getPickName() + "添加一笔" + ((type == 1) ? "收入" : "支出") + "记录.");
            AsyncUtil.execute(new Runnable() {
                @Override
                public void run() {
                    userMessageInfoService.send(userMessageInfoDO);
                }
            });
        }
        print(result);
        return null;
    }

    @PostMapping(value = "/edit")
    public String editRecord() {
        if (session.getAttribute("seqId") == null) {
            return "forward:/";
        }
        Integer id = Integer.parseInt(request.getParameter("id"));
        String addTime = request.getParameter("add_time").trim();
        Integer type = Integer.parseInt(request.getParameter("type"));
        String billingType = BillingTypeEnum.getName(request.getParameter("billing_type"));
        billingType = (billingType == null) ? request.getParameter("billing_type") : billingType;
        String moneyDesc = request.getParameter("money_desc");
        BigDecimal moneyNum;
        try {
            moneyNum = new BigDecimal(request.getParameter("money_num").toString());
            if (moneyNum.compareTo(new BigDecimal(0)) == -1){
                print(JsonResult.getError("401", "金额格式有误，应该大于0"));
            }
        } catch (Exception e) {
            print(JsonResult.getError("401", "金额格式有误，应为数字"));
            return null;
        }
        String seqId = session.getAttribute("seqId").toString();
        BillingRecordInfoDO billingRecordInfoDO = new BillingRecordInfoDO(type, seqId, billingType, java.sql.Date.valueOf(addTime), moneyNum, moneyDesc);
        billingRecordInfoDO.setId(id);
        JSONObject result = billingRecordInfoService.editRecord(billingRecordInfoDO);
        if ((Boolean) result.get("success")) {
            // 发消息
            UserInfoDO createUser = (UserInfoDO) session.getAttribute("user");
            UserMessageInfoDO userMessageInfoDO = new UserMessageInfoDO();
            userMessageInfoDO.setReceiver(createUser.getPickName());
            userMessageInfoDO.setSender("系统");
            userMessageInfoDO.setType("编辑账单");
            userMessageInfoDO.setTitle("编辑了一笔账单");
            userMessageInfoDO.setContent("账号：" + createUser.getPickName() + "编辑一笔" + ((type == 1) ? "收入" : "支出") + "记录.");
            AsyncUtil.execute(new Runnable() {
                @Override
                public void run() {
                    userMessageInfoService.send(userMessageInfoDO);
                }
            });
        }
        print(result);
        return null;
    }

    @PostMapping(value = "/delete")
    public void deleteRecord() {
        Integer id = Integer.parseInt(request.getParameter("id"));
        JSONObject result = billingRecordInfoService.deleteRecordById(id);
        if ((Boolean) result.get("success")) {
            // 发消息
            UserInfoDO createUser = (UserInfoDO) session.getAttribute("user");
            UserMessageInfoDO userMessageInfoDO = new UserMessageInfoDO();
            userMessageInfoDO.setReceiver(createUser.getPickName());
            userMessageInfoDO.setSender("系统");
            userMessageInfoDO.setType("删除账单");
            userMessageInfoDO.setTitle("删除一笔记录");
            userMessageInfoDO.setContent("账号：" + createUser.getPickName() + "删除了一笔账单记录.");
            AsyncUtil.execute(new Runnable() {
                @Override
                public void run() {
                    userMessageInfoService.send(userMessageInfoDO);
                }
            });
        }
        print(result);
    }

    /**
     * 启动异步线程查询账单记录
     *
     * @param seqId
     * @param type
     * @return
     */
    private List<Integer> handleBillingCount(String seqId, Integer type) {
        Collection tasks = new ArrayList();
        tasks.add(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return billingRecordInfoService.queryCount(seqId, type, -7);
            }
        });
        tasks.add(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return billingRecordInfoService.queryCount(seqId, type, -30);
            }
        });
        tasks.add(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return billingRecordInfoService.queryCount(seqId, type, -365);
            }
        });
        tasks.add(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return billingRecordInfoService.queryCount(seqId, type, 1);
            }
        });
        List<Future> futureList = null;
        try {
            futureList = AsyncUtil.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Integer> result = new ArrayList();
        if (futureList == null) {
            return result;
        } else {
            for (Future future : futureList) {
                try {
                    result.add((Integer) future.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
