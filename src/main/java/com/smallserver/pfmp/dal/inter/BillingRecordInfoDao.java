package com.smallserver.pfmp.dal.inter;

import com.smallserver.pfmp.dal.object.BillingRecordInfoDO;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

/**
 * Created by WQ on 2018/2/1.
 */
public interface BillingRecordInfoDao {

    boolean insert(BillingRecordInfoDO billingRecordInfoDO);
    boolean update(BillingRecordInfoDO billingRecordInfoDO);
    boolean delete(@Param("id") Integer id);
    Integer selectCount(@Param("seqId") String seqId, @Param("type") Integer type, @Param("date") Date date);
    List<BillingRecordInfoDO> queryBySeqId(@Param("seqId") String seqId, @Param("type") Integer type);
    List<BillingRecordInfoDO> queryRecord(@Param("seqId") String seqId, @Param("type") Integer type, @Param("date") Date date, @Param("offset") Integer offset, @Param("size") Integer size);
}
