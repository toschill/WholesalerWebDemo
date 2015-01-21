package de.htwg_konstanz.ebus.wholesaler.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCustomer;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOProduct;
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
		
		BOCustomer customer = null;
		
		for(BOCustomer checkCustomer: customerBoa.findAll()){
			if(checkCustomer.getCompanyname().equals(wholesalerName)){
				System.out.println("Wholesaler gefunden");
				if(checkCustomer.getWsUserName().equals(userName)){
					System.out.println("Username gefunden");
					if(checkCustomer.getWsPassword().equals(password)){
						System.out.println("Password passt");
						System.out.println("Authentifiziert");
						customer= checkCustomer;
					}
					else{
						throw new AuthenticationFaultMessage("ERROR: wrong Password", "Info");
					}
					
				} 
				else{
						throw new AuthenticationFaultMessage("ERROR: User not found", "Info");
					}
			} 
			else{
					throw new AuthenticationFaultMessage("ERROR: Wholesaler not found", "Info");
			}
			break;
		}
		
		Collection<String> aids = new ArrayList<String>();
		
		for(SupplierProductType product : body.getListOfProducts().getSupplierProduct()){
			//schreibe alle aids in liste um mit lokalen aids zu vergleichen
			aids.add(product.getSupplierAID());
			boolean updated = false;
			BOProduct productBo = productBoa.findByOrderNumberCustomer(product.getSupplierAID());
			if(productBo!=null){
				if(!productBo.getShortDescription().equals(product.getShortDescription())){
					updated=true;
				}
				if(!productBo.getLongDescription().equals(product.getLongDescription())){
					updated=true;
				}
				if(updated){
					SupplierProductType updatedProduct= factory.createSupplierProductType();
					updatedProduct.setShortDescription(productBo.getShortDescriptionCustomer());
					updatedProduct.setLongDescription(productBo.getLongDescriptionCustomer());
					updatedProducts.getSupplierProduct().add(updatedProduct);
				}
			}
			else{
				unavailableProducts.getSupplierProduct().add(product);
			}
		}
		
		//finde neue Produkte
		for(BOProduct productBo : productBoa.findAll()){
			if(!aids.contains(productBo.getOrderNumberCustomer())){
				//produkt nicht in request
				SupplierProductType updatedProduct= factory.createSupplierProductType();
				updatedProduct.setSupplierAID(productBo.getOrderNumberCustomer());
				updatedProduct.setShortDescription(productBo.getShortDescriptionCustomer());
				updatedProduct.setLongDescription(productBo.getLongDescriptionCustomer());
				updatedProducts.getSupplierProduct().add(updatedProduct);
			}
		}
		
		response.setListOfUnavailableProducts(unavailableProducts);
		response.setListOfUpdatedProducts(updatedProducts);
		
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar xmlGC = null;
		try {
			xmlGC = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			System.out.println("Fehler beim holen der Zeit!!");
			e.printStackTrace();
		}
		response.setUpdateDate(xmlGC);
		return response;
	}

}
