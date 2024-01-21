package com.sdd.bootcamp01.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction implements Comparable<Transaction> {
	private String trxid;
	private Date trxdate;
	private BigDecimal trxamount;
	private int trxqty;
	private Product product;

	public String getTrxid() {
		return trxid;
	}

	public void setTrxid(String trxid) {
		this.trxid = trxid;
	}

	public Date getTrxdate() {
		return trxdate;
	}

	public void setTrxdate(Date trxdate) {
		this.trxdate = trxdate;
	}

	public BigDecimal getTrxamount() {
		return trxamount;
	}

	public void setTrxamount(BigDecimal trxamount) {
		this.trxamount = trxamount;

	}

	public int getTrxqty() {
		return trxqty;
	}

	public void setTrxqty(int trxqty) {
		this.trxqty = trxqty;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int compareTo(Transaction o) {
		if (o.trxid == null || this.trxid == null) {
			return -1;
		}

		return o.trxid.compareTo(this.trxid);
	}

}
