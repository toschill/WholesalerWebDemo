package de.htwg_konstanz.ebus.wholesaler.main;

import java.util.ArrayList;

import javax.xml.ws.WebServiceRef;

import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.UpdateResponseType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.ListOfProductsType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.AuthenticationFaultMessage;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.AuthenticationType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.ObjectFactory;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.SupplierProductType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.UpdateCatalogInterface;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.UpdateCatalogRequestType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.UpdateCatalogWSService;

public class UpdateCatalogWSClient {
	public ArrayList<String> errorList;
	//@WebServiceRef(wsdlLocation="http://localhost:8080/WholesalerWebDemo/UpdateCatalog?wsdl")
	UpdateCatalogWSService client;
	
	ObjectFactory factory = new ObjectFactory();
	
	public UpdateCatalogWSClient(ArrayList<String> erroList){
		this.errorList = erroList;
	}
	
	public void startClient(){
		
		UpdateResponseType response = factory.createUpdateResponseType();
		client = new UpdateCatalogWSService();
		
		UpdateCatalogInterface port =  client.getUpdateCatalogWSPort();
		AuthenticationType auth = factory.createAuthenticationType();
		auth.setWholesalerName("Wholesaler");
		auth.setWsPassword("password");
		auth.setWsUsername("user");
		
		UpdateCatalogRequestType request = new UpdateCatalogRequestType();
		ListOfProductsType list = new ListOfProductsType();
		SupplierProductType spt = new SupplierProductType();
		spt.setShortDescription("TEST");
		list.getSupplierProduct().add(spt);
		request.setListOfProducts(list);
		request.setAuthentication(auth);
		
		try {
			response = port.updateCatalog(request);
			for(SupplierProductType sp : response.getListOfUpdatedProducts().getSupplierProduct()){
				errorList.add(sp.getShortDescription());
			}
		} catch (AuthenticationFaultMessage e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
