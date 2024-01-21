package com.sdd.bootcamp01;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;

import com.sdd.bootcamp01.domain.Transaction;

public class TrxDetailVm {
	String title;
	Transaction obj;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("obj") Transaction obj, @ExecutionArgParam("index") Integer index) {
		Selectors.wireComponents(view, this, false);
		this.obj = obj;
		title = "Transaction Detail of " + index; 
	}
	
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
