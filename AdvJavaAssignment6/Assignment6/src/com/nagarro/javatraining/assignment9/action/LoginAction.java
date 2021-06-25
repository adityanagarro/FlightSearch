package com.nagarro.javatraining.assignment9.action;

import java.util.Date;
import java.util.Map;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.nagarro.javatraining.assignment9.BusinessService.LoginProviderService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;


public class LoginAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	LoginProviderService loginProvider = new LoginProviderService();
	
	
	@Action(value="login",
			results={
			   @Result(name="success",type="redirect",location="emplist"),
			   @Result(name="input", location="/index.jsp")
			}) 
	
	   public String exec () 
	   {
			   Map<String, Object> session = ActionContext.getContext().getSession();
			   session.put("logined","true");
			   session.put("context", new Date());
			   session.put("user",(String)loginProvider.getTmpHr().getUserId());
			   return SUCCESS;
	   }
	
	
	
	@RequiredStringValidator(message = "userid is mandatory",  shortCircuit = true)
	public String getUserId() {
		return loginProvider.getTmpHr().getUserId();
	}
	public void setUserId(String userId) {
		loginProvider.getTmpHr().setUserId(userId);
	}
	@RequiredStringValidator(message = "password is mandatory",  shortCircuit = true)
	public String getPass() {
		return loginProvider.getTmpHr().getPass();
	}
	public void setPass(String pass) {
		loginProvider.getTmpHr().setPass(pass);
	}
	
	@Override
	public void validate()
	{
		int res = loginProvider.provideLogin();
		if(res==1)
			addFieldError("pass", "Password is Incorrect");
		else if(res==2)
			addFieldError("userId", "userId is Incorrect");
	}
	
}
