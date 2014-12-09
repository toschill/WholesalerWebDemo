package de.htwg_konstanz.ebus.wholesaler.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.htwg_konstanz.ebus.framework.wholesaler.api.security.Security;
import de.htwg_konstanz.ebus.wholesaler.demo.ControllerServlet;
import de.htwg_konstanz.ebus.wholesaler.demo.IAction;
import de.htwg_konstanz.ebus.wholesaler.demo.LoginBean;
import de.htwg_konstanz.ebus.wholesaler.demo.util.Constants;
import de.htwg_konstanz.ebus.wholesaler.main.Export;

public class ExportAction implements IAction{

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
						
						// Export.exportAll();
						ServletContext context = request.getSession().getServletContext();
						String path = Export.makeFile(Export.exportAll(errorList),context, loginBean.getUser().getId());
						//Set Parameter in Session
						request.getSession(true).setAttribute(Constants.PARAM_EXPORT, path);
						 return "export.jsp";
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

/**
 * Each action itself decides if it is responsible to process the corrensponding request or not.
 * This means that the {@link ControllerServlet} will ask each action by calling this method if it
 * is able to process the incoming action request, or not.
 * 
 * @param actionName the name of the incoming action which should be processed
 * @return true if the action is responsible, else false
 */
	@Override
	public boolean accepts(String actionName) {
		
		return actionName.equalsIgnoreCase(Constants.ACTION_EXPORT);
	}

}
