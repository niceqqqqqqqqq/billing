package com.smallserver.pfmp.common;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class JsonResult extends JSONObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static JSONObject getSuccess(){
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("code", "200");
		return json;
	}
	
	public static JSONObject getError(String desc){
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("desc", desc);
		return json;
	}
	
	public static JSONObject getError(String code, String desc){
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("code", code);
		json.put("desc", desc);
		return json;
	}
	
	public static JSONObject getSuccess(Object data){
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("code", "200");
		json.put("data", data);
		return json;
	}

}
