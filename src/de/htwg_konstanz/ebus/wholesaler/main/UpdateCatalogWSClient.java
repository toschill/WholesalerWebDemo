package de.htwg_konstanz.ebus.wholesaler.main;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceRef;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOProduct;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.ProductBOA;
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
	UpdateResponseType response = factory.createUpdateResponseType();
	AuthenticationType auth = factory.createAuthenticationType();
	private ProductBOA  productBoa = ProductBOA.getInstance();
	
	public UpdateCatalogWSClient(ArrayList<String> erroList){
		this.errorList = erroList;
	}
	
	public void startClient(){
	
		client = new UpdateCatalogWSService();
		UpdateCatalogInterface port =  client.getUpdateCatalogWSPort();
		
		AuthenticationType auth = factory.createAuthenticationType();
		auth.setWholesalerName("HTWG Konstanz");
		auth.setWsPassword("ws_htwg_1");
		auth.setWsUsername("ws_htwg_1");
		
		UpdateCatalogRequestType request = factory.createUpdateCatalogRequestType();
		ListOfProductsType list = factory.createListOfProductsType();
		
		
		for(BOProduct productBo : productBoa.findAll()){
			SupplierProductType spt = factory.createSupplierProductType();
			spt.setSupplierAID(productBo.getOrderNumberSupplier());
			spt.setShortDescription(productBo.getShortDescription());
			spt.setLongDescription(productBo.getLongDescription());
			System.out.println("Produkte aus DB gelesen:" + productBo.getShortDescription());
			System.out.println(spt.getShortDescription());
			list.getSupplierProduct().add(spt);
		}
		
		request.setListOfProducts(list);
		request.setAuthentication(auth);
		
		//Für uns zur Ausgabe aller Produkte in unserer Datenbank
		/*for(int i=0; i<request.getListOfProducts().getSupplierProduct().size(); i++){
			System.out.println(request.getListOfProducts().getSupplierProduct().get(i).getSupplierAID() + " || " +
			request.getListOfProducts().getSupplierProduct().get(i).getShortDescription() + " || " +
			request.getListOfProducts().getSupplierProduct().get(i).getLongDescription());
		}*/
		
		try {
			response = port.updateCatalog(request);
			System.out.println(response.getUpdateDate());
			for(int i=0; i<response.getListOfUpdatedProducts().getSupplierProduct().size(); i++){
				System.out.println(response.getListOfUpdatedProducts().getSupplierProduct().get(i).getSupplierAID() + " || " +
				response.getListOfUpdatedProducts().getSupplierProduct().get(i).getShortDescription() + " || " +
				response.getListOfUpdatedProducts().getSupplierProduct().get(i).getLongDescription());
			}
			
		} catch (AuthenticationFaultMessage e) {
			errorList.add(e.getMessage());
			e.printStackTrace();
		}
	}
}
