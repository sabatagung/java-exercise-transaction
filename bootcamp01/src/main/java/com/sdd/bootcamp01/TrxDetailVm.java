package com.sdd.bootcamp01;

import com.sdd.bootcamp01.domain.Transaction;

public class TrxDetailVm {
	String title;
	Transaction obj;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Transaction getObj() {
		return obj;
	}
	public void setObj(Transaction obj) {
		this.obj = obj;
	}
}
