package com.zcs.app.monkeyalmanac.entity;

import java.io.Serializable;
import java.util.List;

public class ResponseDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean success;
	private String message;
	private List<DataItem> list;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<DataItem> getList() {
		return list;
	}

	public void setList(List<DataItem> list) {
		this.list = list;
	}
}
