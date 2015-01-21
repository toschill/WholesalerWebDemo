package de.htwg_konstanz.ebus.wholesaler.main;

import java.util.ArrayList;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOProduct;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOSupplier;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.ProductBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.SupplierBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa._BaseBOA;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.AuthenticationFaultMessage;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.AuthenticationType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.ListOfProductsType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.ObjectFactory;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.SupplierProductType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.UpdateCatalogInterface;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.UpdateCatalogRequestType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.UpdateCatalogWSService;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.UpdateResponseType;

public class UpdateCatalogWSClient {
	public ArrayList<String> errorList;
	public String supplierNumber;
	//@WebServiceRef(wsdlLocation="http://localhost:8080/WholesalerWebDemo/UpdateCatalog?wsdl")
	
	UpdateCatalogWSService client;
	ObjectFactory factory = new ObjectFactory();
	UpdateResponseType response = factory.createUpdateResponseType();
	AuthenticationType auth = factory.createAuthenticationType();
	private ProductBOA  productBoa = ProductBOA.getInstance();
	private String user;
	private String password;
	private String Name;
	
	public UpdateCatalogWSClient(ArrayList<String> erroList, String user, String password, String Name){
		this.errorList = erroList;
		this.user = user;
		this.password = password;
		this.Name = Name;
	}
	
	public void startClient(){
		System.out.println("Client gestartet");
		
		client = new UpdateCatalogWSService();
		UpdateCatalogInterface port =  client.getUpdateCatalogWSPort();
		AuthenticationType auth = factory.createAuthenticationType();
		System.out.println(Name);
		auth.setWholesalerName(Name);
		auth.setWsPassword(password);
		System.out.println(password);
		auth.setWsUsername(user);
		System.out.println(user);

		
		UpdateCatalogRequestType request = factory.createUpdateCatalogRequestType();
		ListOfProductsType list = factory.createListOfProductsType();
		
		
		for(BOProduct productBo : productBoa.findAll()){
			SupplierProductType spt = factory.createSupplierProductType();
			spt.setSupplierAID(productBo.getOrderNumberSupplier());
			spt.setShortDescription(productBo.getShortDescription());
			spt.setLongDescription(productBo.getLongDescription());
		//	System.out.println("Produkte aus DB gelesen:" + productBo.getShortDescription());
		//	System.out.println(spt.getShortDescription());
			list.getSupplierProduct().add(spt);
		}
		
		request.setListOfProducts(list);
		request.setAuthentication(auth);
		
		//F�r uns zur Ausgabe aller Produkte in unserer Datenbank
		/*for(int i=0; i<request.getListOfProducts().getSupplierProduct().size(); i++){
			System.out.println(request.getListOfProducts().getSupplierProduct().get(i).getSupplierAID() + " || " +
			request.getListOfProducts().getSupplierProduct().get(i).getShortDescription() + " || " +
			request.getListOfProducts().getSupplierProduct().get(i).getLongDescription());
		}*/
		
		try {
			response = port.updateCatalog(request);
			System.out.println(response.getUpdateDate());
			for(SupplierProductType spt : response.getListOfUpdatedProducts().getSupplierProduct()){
				System.out.println(spt.getShortDescription()+"|"+spt.getLongDescription());

				BOProduct product = new BOProduct();
				product.setShortDescription(spt.getShortDescription());
				product.setLongDescription(spt.getLongDescription());
				product.setOrderNumberSupplier(spt.getSupplierAID());
				product.setOrderNumberCustomer(spt.getSupplierAID());
				
				BOSupplier supplier = SupplierBOA.getInstance().findByCompanyName(Name).get(0);
				product.setSupplier(supplier);
				ProductBOA.getInstance().saveOrUpdate(product);
				_BaseBOA.getInstance().commit();
			}
			
			for(SupplierProductType spt : response.getListOfUnavailableProducts().getSupplierProduct()){
				BOProduct del = ProductBOA.getInstance().findByOrderNumberSupplier(spt.getSupplierAID());
				errorList.add("INFO: "+spt.getSupplierAID()+ " deleted");
				ProductBOA.getInstance().delete(del);
				_BaseBOA.getInstance().commit();
			}
			
			
		} catch (AuthenticationFaultMessage e) {
			errorList.add(e.getMessage());
			e.printStackTrace();
		}
	}
}
