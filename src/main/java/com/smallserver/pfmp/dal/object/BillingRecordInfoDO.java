package com.smallserver.pfmp.dal.object;

import com.smallserver.pfmp.util.BaseUitls;
import org.thymeleaf.util.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;

/**
 * Created by WQ on 2018/2/1.
 */
public class BillingRecordInfoDO implements Serializable{

    private Integer id;
    private Integer type;
    private String seqId;
    private String billingType;
    private Date addTime;
    private BigDecimal moneyNum;
    private String moneyDesc;
    private Timestamp createTime;

    public BillingRecordInfoDO() {
    }

    public BillingRecordInfoDO(Integer type, String seqId, String billingType, Date addTime, BigDecimal moneyNum, String moneyDesc) {
        this.type = type;
        this.billingType = billingType;
        this.seqId = seqId;
        this.addTime = addTime;
        this.moneyNum = moneyNum;
        this.moneyDesc = moneyDesc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getAddTime() {
        return BaseUitls.formatDateToStr2(addTime);
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getMoneyNum() {
        return new DecimalFormat("0.00").format(moneyNum);
    }

    public void setMoneyNum(BigDecimal moneyNum) {
        this.moneyNum = moneyNum;
    }

    public String getMoneyDesc() {
        return moneyDesc;
    }

    public void setMoneyDesc(String moneyDesc) {
        this.moneyDesc = moneyDesc;
    }

    public String getCreateTime() {
        return BaseUitls.formatTimeToStr1(createTime);
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BillingRecordInfoDO{");
        sb.append("id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", seqId='").append(seqId).append('\'');
        sb.append(", billingType='").append(billingType).append('\'');
        sb.append(", addTime=").append(addTime);
        sb.append(", moneyNum=").append(moneyNum);
        sb.append(", moneyDesc='").append(moneyDesc).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}
