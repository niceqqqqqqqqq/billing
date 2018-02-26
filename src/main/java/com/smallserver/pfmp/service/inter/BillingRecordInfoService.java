package com.smallserver.pfmp.service.inter;

import com.alibaba.fastjson.JSONObject;
import com.smallserver.pfmp.dal.object.BillingRecordInfoDO;

import java.sql.Date;
import java.util.List;

/**
 * Created by WQ on 2018/2/2.
 */
public interface BillingRecordInfoService {

    /**
     * 添加账单
     * @param billingRecordInfoDO
     * @return
     */
    JSONObject addRecord(BillingRecordInfoDO billingRecordInfoDO);

    /**
     * 编辑账单
     * @param billingRecordInfoDO
     * @return
     */
    JSONObject editRecord(BillingRecordInfoDO billingRecordInfoDO);

    /**
     * 查询账单
     * @param seqId
     * @param type
     * @return
     */
    JSONObject queryInComeRecordBySeqId(String seqId, Integer type);

    /**
     * 按条件查询账单总额
     * @param seqId
     * @param type
     * @return
     */
    Integer queryCount(String seqId, Integer type, Integer days);

    /**
     * 按条件查询账单记录
     * @param seqId
     * @param type
     * @return
     */
    List<BillingRecordInfoDO> queryRecord(String seqId, Integer type, Integer days, Integer offset, Integer size);

    /**
     * 删除账单
     * @param id
     * @return
     */
    JSONObject deleteRecordById(Integer id);
}
