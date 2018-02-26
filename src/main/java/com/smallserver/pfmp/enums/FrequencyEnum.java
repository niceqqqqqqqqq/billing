package com.smallserver.pfmp.enums;

/**
 * Created by WQ on 2018/2/8.
 */
public enum FrequencyEnum {

    seven("近7天"),
    month("近一个月"),
    year("近一年"),
    all("全部");

    private String desc;
    FrequencyEnum(String desc){
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
        for (FrequencyEnum frequency : FrequencyEnum.values()){
            if (frequency.name().equalsIgnoreCase(name)){
                return frequency.getDesc();
            }
        }
        return null;
    }
}
