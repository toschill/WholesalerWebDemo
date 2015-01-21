package de.htwg_konstanz.ebus.wholesaler.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOSupplier;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.OrderBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.SupplierBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.security.Security;
import de.htwg_konstanz.ebus.wholesaler.demo.IAction;
import de.htwg_konstanz.ebus.wholesaler.demo.LoginBean;
import de.htwg_konstanz.ebus.wholesaler.demo.util.Constants;
import de.htwg_konstanz.ebus.wholesaler.main.UpdateCatalogWSClient;

public class UpdateAction implements IAction{
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response, ArrayList<String> errorList) {
		// get the login bean from the session
		LoginBean loginBean = (LoginBean)request.getSession(true).getAttribute(Constants.PARAM_LOGIN_BEAN);

		// ensure that the user is logged in
		if (loginBean != null && loginBean.isLoggedIn())
		{
			// ensure that the user is allowed to execute this action (authorization)
			// at this time the authorization is not fully implemented.
			// -> use the "Security.RESOURCE_ALL" constant which includes all resources.
			if (Security.getInstance().isUserAllowed(loginBean.getUser(), Security.RESOURCE_ALL, Security.ACTION_READ))
			{
				String supplier = request.getParameter(Constants.ACTION_UPDATE_SUPPLIER);
				if(supplier!=null){
					System.out.println(supplier);
					BOSupplier supplierBo = SupplierBOA.getInstance().findSupplierById(supplier); 
					UpdateCatalogWSClient client = new UpdateCatalogWSClient(errorList, supplierBo.getWsUserName(), supplierBo.getWsPassword(), supplierBo.getCompanyname());
					client.startClient();	
					List<?> supplierList = SupplierBOA.getInstance().findAll(); 
					request.getSession(true).setAttribute(Constants.PARAM_ORDER_SUPPLIER_LIST, supplierList);
					return "update.jsp";
				}else{
					System.out.println("keine");
					List<?> supplierList = SupplierBOA.getInstance().findAll(); 
					request.getSession(true).setAttribute(Constants.PARAM_ORDER_SUPPLIER_LIST, supplierList);
					return "update.jsp";
				}
			}
			else
			{
				// authorization failed -> show error message
				errorList.add("You are not allowed to perform this action!");
				
				// redirect to the welcome page
				return "welcome.jsp";
			}
		}
		else
			// redirect to the login page
			return "login.jsp";				

	}
	

	@Override
	public boolean accepts(String actionName) {
		
		return actionName.equalsIgnoreCase(Constants.ACTION_UPDATE);
	}
}
