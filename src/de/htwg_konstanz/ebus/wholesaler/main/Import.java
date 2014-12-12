package de.htwg_konstanz.ebus.wholesaler.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCountry;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCurrency;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOProduct;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOPurchasePrice;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOSalesPrice;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOSupplier;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.PriceBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.ProductBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.SupplierBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa._BaseBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Country;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Currency;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Import {

	public void load(HttpServletRequest request, List<String> errorList){
		InputStream importXml = catchFileUpload(request, errorList);
		Document catalog = createImportFileDOM(importXml, errorList);
		if(catalog != null){
			validateXml(catalog, errorList);
			BOSupplier supplier = getSupplier(catalog, errorList);
			if(supplier != null){
				System.out.println("LOAD SUPPLIER != NULL");
				insertProductsIntoDB(supplier, catalog, errorList);
			} else {
				errorList.add("Supplier not found");
			}
			
		} else {
			errorList.add("Error in createImportFileDOM");
		}
	}
	
	/**
	 * Creates FileItemFactory and FileUpload to catch the upload request
	 * @param request 
	 * @param errorList
	 * @return
	 */
	public InputStream catchFileUpload(HttpServletRequest request, List<String> errorList){
		//Factory and Servlet for FileUpload
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		InputStream file = null;
		try {
			List<FileItem> items = upload.parseRequest(request);
			file = items.get(0).getInputStream();
			
		} catch (FileUploadException e) {
			errorList.add("Error with file upload!");
			e.printStackTrace();
		} catch (IOException e) {
			errorList.add("IOException!");
			e.printStackTrace();
		}
		return file;
	}
	
	public boolean validateXml(Document document, List<String> errorList){
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Validator validator = null;
		Schema bmeCat = null;
		boolean valid = false;
		try {
			bmeCat = schemaFactory.newSchema(new File("C:/Users/dominic.DIE-SICKELS/Downloads/bmecat_new_catalog_1_2_simple_without_NS.xsd"));
			validator = bmeCat.newValidator();
			//Validate Uploaded XML File
			
		} catch (SAXException e) {
			errorList.add("SCHEMA PROBLEM");
			e.printStackTrace();
		} 
		try{
			validator.validate(new DOMSource(document));
			valid = true;
		}
		catch (IOException e) {
			errorList.add("Error during validation");
			e.printStackTrace();
		} catch (SAXException e) {
			errorList.add("Error Document not valid");
			e.printStackTrace();
		}
		
		return valid;
	}
	
	public Document createImportFileDOM(InputStream xmlFile, List<String> errorList ){
		Document document = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setValidating(false);
		DocumentBuilder parser;
		try {
			parser = dbf.newDocumentBuilder();
			document = parser.parse(xmlFile);
		} catch (ParserConfigurationException e) {
			errorList.add("Error during parser configuration!");
			e.printStackTrace();
		} catch (SAXException e) {
			errorList.add("Error during parsing process!");
			e.printStackTrace();
		} catch (IOException e) {
			errorList.add("Error during upload!");
			e.printStackTrace();
		}
		return document;
	}
		
	public BOSupplier getSupplier(Document catalog, List<String> errorList){
		System.out.println("ERREICHT GET_SUPPLIER");
		//Supplier in uploaded catalog
		NodeList suppliers = catalog.getElementsByTagName("SUPPLIER_NAME");
		
		//Save Supplier into a List
		List<BOSupplier> supplierList = SupplierBOA.getInstance().findByCompanyName((suppliers.item(0).getChildNodes().item(0).getNodeValue()));
		if(!supplierList.isEmpty()){
			System.out.println("ERREICHT IF ABFRAGE IN GET_SUPPLIER");
			return supplierList.get(0);
		}
		return null;
	}
	
	public void insertProductsIntoDB(BOSupplier supplier, Document catalog, List<String> errorList){
		System.out.println("ERREICHT INSERT PRODUCT INTO DB");
		//Get all Articles of the uploaded catalog
		NodeList articleList = catalog.getElementsByTagName("ARTICLE");
		
		//Iterate over every "ARTICLE" in catalog 
		for(int i = 0; i < articleList.getLength(); i++){
			BOProduct product = new BOProduct();
			Element article = (Element) articleList.item(i);
			//Search for "DESCRIPTION_SHORT" and set the value for a new Product
			NodeList description_short = article.getElementsByTagName("DESCRIPTION_SHORT"); 
			product.setShortDescription(description_short.item(0).getChildNodes().item(0).getNodeValue());
			System.out.println("PRODUKT GEFUNDEN  SD: " + product.getShortDescription());
			
			//Search for "DESCRIPTION_LONG" and set the value for a new Product
			NodeList description_long = article.getElementsByTagName("DESCRIPTION_LONG");
			product.setLongDescription(description_long.item(0).getChildNodes().item(0).getNodeValue());
			System.out.println("----------------- LD " + product.getLongDescription());
			
			//Search for "SUPPLIER_AID" and set the value for a new Product
			NodeList supplier_aid = article.getElementsByTagName("SUPPLIER_AID");
			product.setOrderNumberSupplier(supplier_aid.item(0).getChildNodes().item(0).getNodeValue());
			System.out.println("----------------- SA " + product.getOrderNumberSupplier());
			
			product.setOrderNumberCustomer(supplier_aid.item(0).getChildNodes().item(0).getNodeValue());
			product.setSupplier(supplier);
			
			//If Product is already in DB an error gets thrown, else we save the product inside our DB
			List<BOProduct> productDB = ProductBOA.getInstance().findByShortdescription(product.getShortDescription());
			
			//if(!productDB.isEmpty()){
				System.out.println("UPDATE PRODUCT");
				ProductBOA.getInstance().saveOrUpdate(product);
				_BaseBOA.getInstance().commit();
				NodeList articlePriceList = article.getElementsByTagName("ARTICLE_PRICE");
				insertProductPricesIntoDB(product, articlePriceList, errorList);
			/*} else {
				errorList.add("Product: " + product.getShortDescription() + " already in DB");
			}*/
		}			
	}
	
	public void insertProductPricesIntoDB(BOProduct product, NodeList articlePriceList, List<String> errorList){
		System.out.println("ERREICHT INSERT PRICES INTO DB");
		
		BOSalesPrice salesPrice = new BOSalesPrice();
		BOPurchasePrice purchasePrice = new BOPurchasePrice();
	
		//SalePrice = 1.5 x purchasePrice
		BigDecimal profit = new BigDecimal(1.5);
		
		//Iterate over any "ARTICLE_DETAIL" in catalog
		for(int i = 0; i < articlePriceList.getLength(); i++){
			Element articlePrice =  (Element) articlePriceList.item(i);
			
			//Set Attribute "price_type"
			purchasePrice.setPricetype(articlePrice.getAttribute("price_type"));
			salesPrice.setPricetype(articlePrice.getAttribute("price_type"));
		
			//Set "PRICE_AMOUNT"
			NodeList article_price_amount = articlePrice.getElementsByTagName("PRICE_AMOUNT");
			purchasePrice.setAmount(BigDecimal.valueOf(Double.valueOf(article_price_amount.item(0).getChildNodes().item(0).getNodeValue())));
			salesPrice.setAmount(BigDecimal.valueOf(Double.valueOf(article_price_amount.item(0).getChildNodes().item(0).getNodeValue())).multiply(profit));
			System.out.println("----------------- " + purchasePrice.getAmount());
			System.out.println("----------------- " + salesPrice.getAmount());
		
			//Set "TERRITORY"
			NodeList territory = articlePrice.getElementsByTagName("TERRITORY");
			purchasePrice.setCountry(new BOCountry(new Country(territory.item(0).getChildNodes().item(0).getNodeValue())));
			salesPrice.setCountry(new BOCountry(new Country(territory.item(0).getChildNodes().item(0).getNodeValue())));
			System.out.println("----------------- " + purchasePrice.getCountry());
			System.out.println("----------------- " + salesPrice.getCountry());
			
			//Set "PRICE_CURRENCY"
			NodeList price_currency = articlePrice.getElementsByTagName("PRICE_CURRENCY");
			purchasePrice.getCountry().setCurrency(new BOCurrency(new Currency(price_currency.item(0).getChildNodes().item(0).getNodeValue())));
			salesPrice.getCountry().setCurrency(new BOCurrency(new Currency(price_currency.item(0).getChildNodes().item(0).getNodeValue())));
			System.out.println("----------------- " + purchasePrice.getCountry().getCurrency());
			System.out.println("----------------- " + salesPrice.getCountry().getCurrency());
			
			//Set "TAX"
			NodeList tax = articlePrice.getElementsByTagName("TAX");
			purchasePrice.setTaxrate(BigDecimal.valueOf(Double.valueOf(tax.item(0).getChildNodes().item(0).getNodeValue())));
			salesPrice.setTaxrate(BigDecimal.valueOf(Double.valueOf(tax.item(0).getChildNodes().item(0).getNodeValue())));
			System.out.println("----------------- " + purchasePrice.getTaxrate());
			System.out.println("----------------- " + salesPrice.getTaxrate());
			
			//Set "LOWERBOUNDSCALEDPRICE"
			purchasePrice.setLowerboundScaledprice(1);
			salesPrice.setLowerboundScaledprice(1);
			System.out.println("----------------- " + purchasePrice.getLowerboundScaledprice());
			System.out.println("----------------- " + salesPrice.getLowerboundScaledprice());
			
			//Save Prices in DB
			purchasePrice.setProduct(product);
			salesPrice.setProduct(product);
			
			PriceBOA.getInstance().saveOrUpdate(purchasePrice);
			PriceBOA.getInstance().saveOrUpdate(salesPrice);
			
			_BaseBOA.getInstance().commit();
		}
	}
}
