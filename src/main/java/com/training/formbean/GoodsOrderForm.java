package com.training.formbean;

import org.apache.struts.action.ActionForm;

public class GoodsOrderForm extends ActionForm{
	private String[] goodsID;
	private int inputMoney;
	private int[] buyQuantity;
	private String customerID;
	private String pageNo;
	private String searchKeyword;
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String[] getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String[] goodsIDs) {
		this.goodsID = goodsIDs;
	}
	public int getInputMoney() {
		return inputMoney;
	}
	public void setInputMoney(int inputMoney) {
		this.inputMoney = inputMoney;
	}
	public int[] getBuyQuantity() {
		return buyQuantity;
	}
	public void setBuyQuantity(int[] buyQuantity) {
		this.buyQuantity = buyQuantity;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	
	
	
	
	
	
}
