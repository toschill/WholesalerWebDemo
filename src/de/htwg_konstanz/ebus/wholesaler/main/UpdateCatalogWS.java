package de.htwg_konstanz.ebus.wholesaler.main;

import javax.jws.WebService;

import de.htwg_konstanz.ebus.wholesaler.ws.MyWS.AuthenticationFaultMessage;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWS.UpdateCatalogInterface;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWS.UpdateCatalogRequestType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWS.UpdateResponseType;

@WebService(endpointInterface="de.htwg_konstanz.ebus.wholesaler.ws.MyWS.UpdateCatalogInterface", targetNamespace="http://new.webservice.namespace")
public class UpdateCatalogWS implements UpdateCatalogInterface{

	@Override
	public UpdateResponseType updateCatalog(UpdateCatalogRequestType body)
			throws AuthenticationFaultMessage {
		return null;
	}

}
