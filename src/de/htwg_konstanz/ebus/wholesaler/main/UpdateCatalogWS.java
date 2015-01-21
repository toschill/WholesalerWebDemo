package de.htwg_konstanz.ebus.wholesaler.main;

import java.util.ArrayList;

import javax.jws.WebService;

import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.CustomerBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.ProductBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.SupplierBOA;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWS.AuthenticationFaultMessage;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWS.AuthenticationType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWS.ListOfProductsType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWS.ListOfUnavailableProductsType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWS.ListOfUpdatedProductsType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWS.ObjectFactory;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWS.SupplierProductType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWS.UpdateCatalogInterface;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWS.UpdateCatalogRequestType;
import de.htwg_konstanz.ebus.wholesaler.ws.MyWS.UpdateResponseType;

@WebService(endpointInterface="de.htwg_konstanz.ebus.wholesaler.ws.MyWS.UpdateCatalogInterface", targetNamespace="http://new.webservice.namespace")
public class UpdateCatalogWS implements UpdateCatalogInterface{
	private ArrayList<String> errorList;
	
	//Create a ObjectFactory 
	private ObjectFactory factory = new ObjectFactory();
	private ProductBOA  productBoa = ProductBOA.getInstance();
	private SupplierBOA supplierBoa = SupplierBOA.getInstance();
	private CustomerBOA customerBoa = CustomerBOA.getInstance();
	@Override
	public UpdateResponseType updateCatalog(UpdateCatalogRequestType body)
			throws AuthenticationFaultMessage {
		
		
		UpdateResponseType response = factory.createUpdateResponseType();
		ListOfProductsType listOfProducts = factory.createListOfProductsType();
		ListOfUpdatedProductsType updatedProducts = factory.createListOfUpdatedProductsType();
		ListOfUnavailableProductsType  unavailableProducts = factory.createListOfUnavailableProductsType();
		SupplierProductType spt = factory.createSupplierProductType();
		AuthenticationType authentication = factory.createAuthenticationType();
		
		
		//Check Authentication
		authentication = body.getAuthentication();
		String wholesalerName = authentication.getWholesalerName();
		String userName = authentication.getWsPassword();
		String password = authentication.getWsUsername();
		
		
		if(supplierBoa.findByCompanyName(wholesalerName) == null){
			errorList.add("Your Company does not exist in our Database!");
			System.out.println("Your Company does not exist in our Database");
		} 
		
		listOfProducts = body.getListOfProducts();
		for (SupplierProductType sptIterator : listOfProducts.getSupplierProduct()){
			
		}
		response.getListOfUpdatedProducts().getSupplierProduct().add(spt);
		
		return response;
	}

}
