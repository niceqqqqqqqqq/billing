package com.smallserver.pfmp.enums;

/**
 * Created by WQ on 2018/2/8.
 */
public enum FeedBackEnum {

    question("问题"),
    idea("想法");

    private String desc;
    FeedBackEnum(String desc){
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
        for (FeedBackEnum feedBackEnum : FeedBackEnum.values()){
            if (feedBackEnum.name().equalsIgnoreCase(name)){
                return feedBackEnum.getDesc();
            }
        }
        return null;
    }
}
