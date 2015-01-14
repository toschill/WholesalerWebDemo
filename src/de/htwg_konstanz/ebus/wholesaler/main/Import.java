package de.htwg_konstanz.ebus.wholesaler.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import javax.jws.WebMethod;
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
import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
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

public class Import {
	/**
	 * Controller for Import
	 * @param request the Http Request
	 * @param errorList User Notification
	 */
	public void load(HttpServletRequest request, List<String> errorList){
		InputStream importXml = catchFileUpload(request, errorList);
		if(null == importXml){
			return;
		}
		Document catalog = createImportFileDOM(importXml, errorList);
		if(catalog != null){
			//if Document not valid stop process
			if(validateXml(catalog, errorList)){
				BOSupplier supplier = getSupplier(catalog, errorList);
				if(supplier != null){
					System.out.println("LOAD SUPPLIER != NULL");
					insertProductsIntoDB(catalog, errorList, supplier);
				} else {
					//errorList.add("Supplier not found");
					System.out.println("No supplier found");
				}
			} else {
				System.out.println("Dcument not valid!");
			}
		} 

	}
	
	/**
	 * Creates FileItemFactory and FileUpload to catch the upload request
	 * @param request The Http Request for relative Path
	 * @param errorList User Notification
	 * @return Inputstream if successful. null if not successful
	 */
	public InputStream catchFileUpload(HttpServletRequest request, List<String> errorList){
		//Factory and Servlet for FileUpload
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		InputStream file = null;
		try {
			List<FileItem> items = upload.parseRequest(request);
			file = items.get(0).getInputStream();
			for(FileItem item : items){
				if(!item.isFormField()){
					String filename = FilenameUtils.getName(item.getName());
					String fileExtension = FilenameUtils.getExtension(item.getName());
					System.out.println("FilenameExt. : " + filename);
					if(!filename.endsWith(".xml")){
						errorList.add("Uploaded File is from Type '." + fileExtension + "' but only XML Files are accepted"
								+ " please select a XML File to upload!" );
						file=null;
					}
				}
			}
		} catch (FileUploadException e) {
			errorList.add("Error with file upload!");
			e.printStackTrace();
		} catch (IOException e) {
			errorList.add("Could not read input!");
			e.printStackTrace();
		}
		return file;
	}
	
	/**
	 * validates the BME Dom against the bmecat_new_catalog_1_2_simple_without_NS.xsd Doctype 
	 * @param document the BMECAT
	 * @param errorList User notification
	 * @return true if valid, false if not valid
	 */
	public boolean validateXml(Document document, List<String> errorList){
		boolean isValid = false;
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Validator validator = null;
		Schema bmeCat = null;
		try {//C:/Users/dominic.DIE-SICKELS/Documents/Studium/5.Semester/EBUT/Labor/Material_Aufgabe_2_Lab_EBUT_WS1415/Material_Aufgabe_2_Lab_EBUT_WS1415/Vereinfachtes_BMEcat1.2_Format/bmecat_new_catalog_1_2_simple_V0.96.xsd
			///Users/tobias/Documents/workspace/Studium/EBUT_DEV/WholesalerWebDemo/files/bmecat_new_catalog_1_2_simple_without_NS.xsd
			bmeCat = schemaFactory.newSchema(new File("/Users/tobias/Documents/workspace/Studium/EBUT_DEV/WholesalerWebDemo/files/bmecat_new_catalog_1_2_simple_without_NS.xsd"));//C:/Users/dominic.DIE-SICKELS/Downloads/bmecat_new_catalog_1_2_simple_without_NS.xsd
			validator = bmeCat.newValidator();
			//Validate Uploaded XML File
			validator.validate(new DOMSource(document));
			isValid = true;
		} catch (SAXException e) {
			errorList.add("The Uploaded XML File is not valid!");
			e.printStackTrace();
		} catch (IOException e) {
			errorList.add("Error while reading DOM");
			e.printStackTrace();
		} 
		
		return isValid;
	}
	
	/**
	 * Create DOM from InputStream
	 * @param xmlFile the uploaded xmlFile
	 * @param errorList User notification
	 * @return the BMECAT document. null on error.
	 */
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
			errorList.add("Document is not well-formed xml!");
			e.printStackTrace();
		} catch (IOException e) {
			errorList.add("Error during upload!");
			e.printStackTrace();
		}
		return document;
	}
	/**
	 * returns the Supplier from the DB or null if supplier not found
	 * @param catalog the BMECAT document
	 * @param errorList User notification
	 * @return null if supplier is not in DB. the supplier if there was one found
	 */
	public BOSupplier getSupplier(Document catalog, List<String> errorList){
		System.out.println("ERREICHT GET_SUPPLIER");
		//Supplier in uploaded catalog
		NodeList suppliers = catalog.getElementsByTagName("SUPPLIER_NAME");
		
		//Save Supplier into a List
		if(suppliers.getLength() == 0){
			//errorList.add("No Supplier found in Document!");
			System.out.println("No Supplier found in uploaded Document");
			return null;
		}
		List<BOSupplier> supplierList = SupplierBOA.getInstance().findByCompanyName((suppliers.item(0).getChildNodes().item(0).getNodeValue()));
		if(supplierList.isEmpty()){
			errorList.add("Supplier not found in DB!");
			return null;
		}
		
		return supplierList.get(0);
	}
	
	/**
	 * inserts the Products from the uploaded catalog into the DB
	 * @param catalog the BMECAT document
	 * @param errorList for User notification
	 * @param supplier the supplier of the product
	 * @return
	 */
	public BOProduct insertProductsIntoDB(Document catalog, List<String> errorList, BOSupplier supplier){
		System.out.println("ERREICHT INSERT PRODUCT INTO DB");
		//Get all Articles of the uploaded catalog
		NodeList articleList = catalog.getElementsByTagName("ARTICLE");
		BOProduct product =null;
		//Iterate over every "ARTICLE" in catalog 
		for(int i = 0; i < articleList.getLength(); i++){
			product=new BOProduct();
			Element article = (Element) articleList.item(i);
			//Search for "DESCRIPTION_SHORT" and set the value for a new Product
			NodeList description_short = article.getElementsByTagName("DESCRIPTION_SHORT"); 
			product.setShortDescription(description_short.item(0).getChildNodes().item(0).getNodeValue());
			System.out.println("PRODUKT GEFUNDEN: " + product.getShortDescription());
			
			//Search for "DESCRIPTION_LONG" and set the value for a new Product
			NodeList description_long = article.getElementsByTagName("DESCRIPTION_LONG");
			product.setLongDescription(description_long.item(0).getChildNodes().item(0).getNodeValue());
			System.out.println("----------------- " + product.getLongDescription());
			
			
			
			//Search for "SUPPLIER_AID" and set the value for a new Product
			NodeList supplier_aid = article.getElementsByTagName("SUPPLIER_AID");
			product.setOrderNumberSupplier(supplier_aid.item(0).getChildNodes().item(0).getNodeValue());
			System.out.println("----------------- " + product.getOrderNumberSupplier());
			
			product.setOrderNumberCustomer(supplier_aid.item(0).getChildNodes().item(0).getNodeValue());
			product.setSupplier(supplier);
			
			//If Product is already in DB an error gets thrown, else we save the product inside our DB
			List<BOProduct> productDBList = ProductBOA.getInstance().findByShortdescription(product.getShortDescription());
			if(!productDBList.isEmpty()){
				//Product already in DB
				deleteProduct(productDBList.get(0));
				ProductBOA.getInstance().saveOrUpdate(product);
				insertProductPricesIntoDB(catalog, errorList, product);
				errorList.add("INFO: Product " + product.getShortDescription() + " updated");

			} else {
				//Product not in DB
				ProductBOA.getInstance().saveOrUpdate(product);
				insertProductPricesIntoDB(catalog, errorList, product);
				errorList.add("INFO: Product " + product.getShortDescription() + " added");
			}
		}	
		//always do a commit
		_BaseBOA.getInstance().commit();
		return product;
	
		
	}
	/**
	 * Deletets the passed product from the DB 
	 * @param product the product to be deleted
	 */
	public void deleteProduct(BOProduct product){
		ProductBOA.getInstance().delete(product);
		_BaseBOA.getInstance().commit();
	}
	
	/**
	 * Inserts the Prices for the DB into the DB
	 * @param catalog the BMECAT document
	 * @param errorList User notification List
	 * @param product the Product for that the prices will be added
	 */
	public void insertProductPricesIntoDB(Document catalog, List<String> errorList, BOProduct product){
		
		BOSalesPrice salesPrice = new BOSalesPrice();
		BOPurchasePrice purchasePrice = new BOPurchasePrice();
	
		//SalePrice = 1.5 x purchasePrice
		BigDecimal profit = new BigDecimal(1.5);
		
		NodeList articlePriceList = catalog.getElementsByTagName("ARTICLE_PRICE");
		
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
		
			//Set "TERRITORY"
			NodeList territory = articlePrice.getElementsByTagName("TERRITORY");
			purchasePrice.setCountry(new BOCountry(new Country(territory.item(0).getChildNodes().item(0).getNodeValue())));
			salesPrice.setCountry(new BOCountry(new Country(territory.item(0).getChildNodes().item(0).getNodeValue())));
			
			//Set "PRICE_CURRENCY"
			NodeList price_currency = articlePrice.getElementsByTagName("PRICE_CURRENCY");
			purchasePrice.getCountry().setCurrency(new BOCurrency(new Currency(price_currency.item(0).getChildNodes().item(0).getNodeValue())));
			salesPrice.getCountry().setCurrency(new BOCurrency(new Currency(price_currency.item(0).getChildNodes().item(0).getNodeValue())));
			
			//Set "TAX"
			NodeList tax = articlePrice.getElementsByTagName("TAX");
			BigDecimal bDecimal = BigDecimal.valueOf(Double.valueOf(tax.item(0).getChildNodes().item(0).getNodeValue()));
			purchasePrice.setTaxrate(bDecimal);
			salesPrice.setTaxrate(BigDecimal.valueOf(Double.valueOf(tax.item(0).getChildNodes().item(0).getNodeValue())));
			
			purchasePrice.setLowerboundScaledprice(1);
			salesPrice.setLowerboundScaledprice(1);
			purchasePrice.setProduct(product);
			salesPrice.setProduct(product);

			//Save Prices in DB
			PriceBOA.getInstance().saveOrUpdatePurchasePrice(purchasePrice);
			PriceBOA.getInstance().saveOrUpdateSalesPrice(salesPrice);
			//always commit
			_BaseBOA.getInstance().commit();
		}
	}
}
