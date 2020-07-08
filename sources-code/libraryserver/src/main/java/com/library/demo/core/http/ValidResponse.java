package com.library.demo.core.http;
import java.io.Serializable;

@SuppressWarnings("serial")
public class ValidResponse implements Serializable {
	private String status;
	private String info;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
