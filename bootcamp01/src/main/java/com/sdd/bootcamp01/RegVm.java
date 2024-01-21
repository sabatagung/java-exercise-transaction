package com.sdd.bootcamp01;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class RegVm {
	
	private String username;
	
	@Wire
	Window winReg;
	@Wire
	Datebox birthdayBox;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		System.out.println("Init Registration");
		
		winReg.setTitle("Registrasion Form UPDATE");
		birthdayBox.setDisabled(true);
		username = "Fajar Prihadi";
	}
	
	@Command
	public void doRadioSelected() {
		birthdayBox.setDisabled(false);
	}
	
	@Command
	@NotifyChange("*")
	public void doReset() {
		username = null;
	}
	
	@Command
	public void doSubmit() {
		Messagebox.show("Hi " + username);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
