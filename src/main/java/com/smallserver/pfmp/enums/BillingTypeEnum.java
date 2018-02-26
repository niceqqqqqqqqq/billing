package com.smallserver.pfmp.enums;

/**
 * Created by WQ on 2018/2/4.
 */
public enum  BillingTypeEnum {

    // 收入类型
    PAYROLL("工资"),
    FAMILY_ORIGIN("家庭来源"),
    ACCOUNT_RECEIVABLE("收账"),
    BONUS("奖金"),
    BORROW("借入"),

    // 支出类型
    CHILDREN("子女花费"),
    REPAYMENT("还款"),
    PAY("缴费"),
    LEND("借出"),
    DAILY("日常"),
    FUN("吃喝玩乐"),
    CLOTHES("衣服"),
    HUMAN_FEELINGS("人情来往"),

    // 通用
    HOLIDAY("节日"),
    OTHER("其他");
    private String desc;
    BillingTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据枚举名称获取对应的描述信息
     * @param name
     * @return
     */
    public static String getDesc(String name){
        BillingTypeEnum billingTypeEnum;
        for (BillingTypeEnum billing : BillingTypeEnum.values()){
            if (billing.name().equalsIgnoreCase(name)){
                return billing.getDesc();
            }
        }
        return null;
    }

    public static String getName(String desc){
        BillingTypeEnum billingTypeEnum;
        for (BillingTypeEnum billing : BillingTypeEnum.values()){
            if (billing.getDesc().equalsIgnoreCase(desc)){
                return billing.name();
            }
        }
        return null;
    }
}
