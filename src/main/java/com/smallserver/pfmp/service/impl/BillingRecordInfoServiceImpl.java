package com.smallserver.pfmp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.smallserver.pfmp.common.JsonResult;
import com.smallserver.pfmp.dal.inter.BillingRecordInfoDao;
import com.smallserver.pfmp.dal.object.BillingRecordInfoDO;
import com.smallserver.pfmp.enums.BillingTypeEnum;
import com.smallserver.pfmp.service.inter.BillingRecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Created by WQ on 2018/2/2.
 */
@Service("billingRecordInfoService")
public class BillingRecordInfoServiceImpl implements BillingRecordInfoService{

    @Autowired
    private BillingRecordInfoDao billingRecordInfoDao;

    @Override
    public JSONObject addRecord(BillingRecordInfoDO billingRecordInfoDO) {
        if (billingRecordInfoDao.insert(billingRecordInfoDO)){
            return JsonResult.getSuccess();
        }
        return JsonResult.getError("505", "账单添加失败！");
    }

    @Override
    public JSONObject editRecord(BillingRecordInfoDO billingRecordInfoDO) {
        if (billingRecordInfoDao.update(billingRecordInfoDO)){
            return JsonResult.getSuccess();
        }
        return JsonResult.getError("505", "账单编辑失败！");
    }

    @Override
    public JSONObject queryInComeRecordBySeqId(String seqId, Integer type) {
        List<BillingRecordInfoDO> list = billingRecordInfoDao.queryBySeqId(seqId, type);
        if (CollectionUtils.isEmpty(list)){
            return JsonResult.getSuccess(list);
        }
        for (BillingRecordInfoDO billing : list){
            billing.setBillingType(BillingTypeEnum.getDesc(billing.getBillingType()));
        }
        return JsonResult.getSuccess(list);
    }

    @Override
    public Integer queryCount(String seqId, Integer type, Integer days) {
        return billingRecordInfoDao.selectCount(seqId, type, handleDate(days));
    }

    @Override
    public List<BillingRecordInfoDO> queryRecord(String seqId, Integer type, Integer days, Integer offset, Integer size) {
        List<BillingRecordInfoDO> list = billingRecordInfoDao.queryRecord(seqId, type, handleDate(days), offset, size);
        if (CollectionUtils.isEmpty(list)){
            return list;
        }
        for (BillingRecordInfoDO billing : list){
            billing.setBillingType(BillingTypeEnum.getDesc(billing.getBillingType()));
        }
        return list;
    }

    @Override
    public JSONObject deleteRecordById(Integer id) {
        if (id == null){
            return JsonResult.getError("505", "编号不能为空");
        }
        if (billingRecordInfoDao.delete(id)){
            return JsonResult.getSuccess();
        }
        return JsonResult.getError("506", "删除失败，稍后重试");
    }

    private java.sql.Date handleDate(Integer days){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        date = calendar.getTime();
        return days == 1 ? null : new java.sql.Date(date.getTime());
    }
}
