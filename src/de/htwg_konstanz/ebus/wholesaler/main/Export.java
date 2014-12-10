package de.htwg_konstanz.ebus.wholesaler.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOProduct;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOSalesPrice;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.ProductBOA;

public class Export {
	
	/**
	 * exports the whole Catalog
	 * @return Product catalog
	 */
	public static Document exportAll(ArrayList<String> errorList){
		Document document= null;
		try {
			 document = createCatalog();
		} catch (ParserConfigurationException e) {
			errorList.add("Error in DOM Basis creation");
			e.printStackTrace();
		}
		return document;
	}
	
	/**
	 * exports the Catalog with Articles that match search
	 * @param errorList The ErrorList to inform user
	 * @param search The Serch string
	 * @return BMECAT conform document
	 */
	public static Document exportSearch(ArrayList<String> errorList, String search){
		Document document= null;
		try {
			 document = createSelectedCatalog(errorList, search);
		} catch (ParserConfigurationException e) {
			errorList.add("Error in DOM Basis creation");
			e.printStackTrace();
		}
		return document;
	}
	
	/**
	 * Creates the BMECat catalog
	 * @return catalog
	 * @throws ParserConfigurationException
	 */
	public static Document createCatalog() throws ParserConfigurationException{
		List<BOProduct> productList = ProductBOA.getInstance().findAll();
		Document document = createDocument();
		document = createBasisDOM(document);
		for(BOProduct bop : productList){
			createArticleDOM(document, bop);
		}
		return document;
	}

	/**
	 * Creates the BMECat catalog
	 * @return catalog
	 * @throws ParserConfigurationException
	 */
	public static Document createSelectedCatalog(ArrayList<String> errorList, String search) throws ParserConfigurationException{
		List<BOProduct> productList = ProductBOA.getInstance().findAll();
		Boolean foundOne=false;
		Document document = createDocument();
		document = createBasisDOM(document);
		for(BOProduct bop : productList){
			if(bop.getShortDescription().toLowerCase().contains(search.toLowerCase())){
				foundOne=true;
				createArticleDOM(document, bop);
			}
		}
		if(!foundOne){
			errorList.add("Es wurde kein Artikel gefunden, dessen Kurzbeschreibung " +search+ " enthält");
		}
		return document;
	}
	
	public static String convertToXhtml(String pathXML, ServletContext context, Integer userId, ArrayList<String> errorList){
		String path ="catalog_export"+userId+".XHTML";
		File file = new File(context.getRealPath(path));
		try {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer;
		transformer = factory.newTransformer(new StreamSource("/Users/tobias/Documents/workspace/Studium/EBUT_DEV/WholesalerWebDemo/files/transform.xslt"));
		transformer.transform(new StreamSource(context.getRealPath(pathXML)), new StreamResult(file));
		} catch (TransformerConfigurationException e) {
			errorList.add("Error while Transforming File");
			e.printStackTrace();
		} catch (TransformerException e) {
			errorList.add("Error while Transforming File");
			e.printStackTrace();
		}
		return path;
	}
	
	
	
	/**
	 * Writes Document into File
	 * @param doc The Document that should be transformed 
	 * @param context The ServletContext to get the relative path
	 * @param userId The userId is needed for the filename
	 * @param errorList The errorList to inform User in case of error
	 * @return the Path to the File
	 */
	public static String makeFile(Document doc, ServletContext context, Integer userId, ArrayList<String> errorList){
		String path="catalog_export"+userId+".XML";
		File file=null;
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			file = new File(context.getRealPath(path));
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			errorList.add("Configuration Error while transforming");
			e.printStackTrace();
		} catch (TransformerException e) {
			errorList.add("Error while transforming");
			e.printStackTrace();
		}
		
		return path;
	}
	
	/**
	 * Creates an empty document
	 * @return empty document
	 * @throws ParserConfigurationException
	 */
	public static Document createDocument() throws ParserConfigurationException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();
		
		return document;
	}
	
	/**
	 * Creates the Basis of our DOM 
	 * @param document empty document in which gets filled with the BasisDOM
	 * @return document with BasisDOM
	 * @throws ParserConfigurationException
	 */
	public static Document createBasisDOM(Document document) throws ParserConfigurationException{
		
		//Create ROOT-Element with attributes
		Element root = document.createElement("BMECAT");
		root.setAttribute("version", "1.2");
		root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("xsi:noNamespaceSchemaLocation", "bmecat_new_catalog_1_2_simple_V0.96.xsd");
		//Append ROOT-Element to our document
		document.appendChild(root);
		
		//Create HEADER-Element
		Element header = document.createElement("HEADER");
		//Append HEADER-Element to "BMECAT"
		root.appendChild(header);
		
		//Create CATALOG-Element
		Element catalog = document.createElement("CATALOG");
		//Append CATALOG-Element to "HEADER"
		header.appendChild(catalog);
		
		//Create LANGUAGE-Element
		Element language = document.createElement("LANGUAGE");
		//Append LANGUAGE-Element to "CATALOG"
		catalog.appendChild(language);
		
		//Fill LANGUAGE-Element with Text
		language.insertBefore(document.createTextNode("deu"), language.getLastChild());
		
		//Create CATALOG_ID-Element
		Element catalog_id = document.createElement("CATALOG_ID");
		//Append CATALOG_ID-Element to "CATALOG"
		catalog.appendChild(catalog_id);
		
		//Fill CATALOG_ID-Element with Text
		catalog_id.insertBefore(document.createTextNode("HTWG_EBUS_07"), catalog_id.getLastChild());
		
		//Create CATALOG_VERSION-Element
		Element catalog_version = document.createElement("CATALOG_VERSION");
		//Append CATALOG_VERSION-Element to "CATALOG"
		catalog.appendChild(catalog_version);
		
		//Fill CATALOG_VERSION-Element with Text
		catalog_version.insertBefore(document.createTextNode("1.0"), catalog_version.getLastChild());
		
		//Create CATALOG_NAME-Element
		Element catalog_name = document.createElement("CATALOG_NAME");
		//Append CATALOG_NAME-Element to "CATALOG"
		catalog.appendChild(catalog_name);
		
		//Fill CATALOG_NAME-Element with Text
		catalog_name.insertBefore(document.createTextNode("Dominic und Tobias Produktkatalog - EBUT"), catalog_name.getLastChild());
		
		//Create SUPPLIER-Element
		Element supplier = document.createElement("SUPPLIER");
		//Append SUPPLIER to "HEADER"
		header.appendChild(supplier);
		
		//Create SUPPLIER_NAME-Element
		Element supplier_name = document.createElement("SUPPLIER_NAME");
		//Append SUPPLIER-Element to "SUPPLIER"
		supplier.appendChild(supplier_name);
		
		//Fill SUPPLIER_NAME-Element with Text
		supplier_name.insertBefore(document.createTextNode("DT GMBH"), supplier_name.getLastChild());
		
		//Create T_NEW_CATALOG-Element
		Element t_new_catalog = document.createElement("T_NEW_CATALOG");
		//Append T_NEW_CATALOG to "HEADER"
		root.appendChild(t_new_catalog);
	
		return document;
	}
	
	/**
	 * Adds productinformation as Article to the Document -> BMECAT conform
	 * DOCUMENT NEEDS A T_NEW_CATALOG Attribute
	 * @param document The BMECAT Document where the Product should be added
	 * @param bop The Product
	 * @return
	 */
	public static void createArticleDOM(Document document, BOProduct bop){
		
		Node t_new_catalog = document.getElementsByTagName("T_NEW_CATALOG").item(0);
		
		//Create ARTICLE-Element
		Element article = document.createElement("ARTICLE");
		t_new_catalog.appendChild(article);
		
		//Create SUPPLIER_AID-Element
		Element supplier_aid = document.createElement("SUPPLIER_AID");
		//Insert content for SUPPLIER_AID
		supplier_aid.insertBefore(document.createTextNode(bop.getOrderNumberSupplier()), supplier_aid.getLastChild());
		//Append SUPPLIER_AID to "ARTICLE"
		article.appendChild(supplier_aid);
		
		//Create ARTICLE_DETAILS-Element
		Element article_details = document.createElement("ARTICLE_DETAILS");
		//Append ARTICLE_DETAILS to "ARTICLE"
		article.appendChild(article_details);
		
		//Create DESCRIPTION_SHORT-Element
		Element description_short = document.createElement("DESCRIPTION_SHORT");
		//Insert content for DESCRIPTION_SHORT
		description_short.insertBefore(document.createTextNode(bop.getShortDescription()), description_short.getLastChild());
		//Append DESCRIPTION_SHORT to "ARTICLE_DETAILS"
		article_details.appendChild(description_short);
		
		//Create DESCRIPTION_LONG-Element
		Element description_long = document.createElement("DESCRIPTION_LONG");
		//Insert content for DESCRIPTION_LONG
		description_long.insertBefore(document.createTextNode(bop.getLongDescription()), description_long.getLastChild());
		//Append DESCRIPTION_LONG to "ARTICLE_DETAILS"
		article_details.appendChild(description_long);
		
		//Create EAN-Element
		Element ean = document.createElement("EAN");
		//Insert content for EAN
		ean.insertBefore(document.createTextNode("DEFAULT"), ean.getLastChild());
		//Append EAN to "ARTICLE_DETAILS"
		article_details.appendChild(ean);
		
		//Create ARTICLE_ORDER_DETAILS-Element
		Element article_order_details = document.createElement("ARTICLE_ORDER_DETAILS");
		//Append ARTICLE_ORDER_DETAILS to "ARTICLE"
		article.appendChild(article_order_details);
		
		//Create ORDER_UNIT-Element
		Element order_unit = document.createElement("ORDER_UNIT");
		//Insert content for ORDER_UNIT
		order_unit.insertBefore(document.createTextNode("DEFAULT"), order_unit.getLastChild());
		//Append ORDER_UNIT to "ARTICLE_ORDER_DETAILS"
		article_order_details.appendChild(order_unit);
		
		//Create CONTENT_UNIT-Element
		Element content_unit = document.createElement("CONTENT_UNIT");
		//Insert content for CONTENT_UNIT
//Optional	order_unit.insertBefore(document.createTextNode("000"), order_unit.getLastChild());
		//Append CONTENT_UNIT to "ARTICLE_ORDER_DETAILS"
		article_order_details.appendChild(content_unit);
		
		//Create NO_CU_PER_OU-Element
		Element no_cu_per_ou = document.createElement("NO_CU_PER_OU");
		//Insert content for NO_CU_PER_OU
//Optional	order_unit.insertBefore(document.createTextNode("000"), order_unit.getLastChild());
		//Append NO_CU_PER_OU to "ARTICLE_ORDER_DETAILS"
		article_order_details.appendChild(no_cu_per_ou);
		
		//Create ARTICLE_DETAILS-Element
		Element article_price_details = document.createElement("ARTICLE_PRICE_DETAILS");
		//Append ARTICLE_DETAILS to "ARTICLE"
		article.appendChild(article_price_details);
			
		createArticlePriceDOM(document, article_price_details, bop);
	}
	

	
	/**
	 * Appends an all Child's to "ARTICLE_PRICE_DETAILS" for every Article of the catalog
	 * @param document DOM filled with all Articles
	 * @param element "ARTICLE_PRICE_DETAILS" Element
	 * @param bop BOProduct that is needed to get the "salePrices"
	 */
	public static void createArticlePriceDOM(Document document, Element element, BOProduct bop){
		Element article_price_details = element;
		List<BOSalesPrice> salePrices = bop.getSalesPrices();
		
		for(BOSalesPrice salePrice : salePrices){
			//Create ARICLE_PRICE-Element
			Element article_price = document.createElement("ARTICLE_PRICE");
			//Set Attribute for ARTICLE_PRICE
			article_price.setAttribute("price_type", salePrice.getPricetype());
			//Append ARTICLE_PRICE to "ARTICLE_PRICE_DETAILS"
			article_price_details.appendChild(article_price);
			
			//Create PRICE_AMOUNT-Element
			Element price_amount = document.createElement("PRICE_AMOUNT");
			//Append PRICE_AMOUNT to "ARTICLE_PRICE"
			article_price.appendChild(price_amount);
			price_amount.insertBefore(document.createTextNode(salePrice.getAmount().toString()), price_amount.getLastChild());		
			
			//Create PRICE_CURRENCY-Element
			Element price_currency = document.createElement("PRICE_CURRENCY");
			//Append PRICE_CURRENCY to "ARTICLE_PRICE"
			article_price.appendChild(price_currency);
			price_currency.insertBefore(document.createTextNode(salePrice.getCountry().getCurrency().getCode()), price_currency.getLastChild());
			
			//Create TAX-Element
			Element tax = document.createElement("TAX");
			//Append TAX to "ARTICLE_PRICE"
			article_price.appendChild(tax);
			tax.insertBefore(document.createTextNode(salePrice.getTaxrate().toString()), tax.getLastChild());
			
			//Create TERRITORY
			Element territory = document.createElement("TERRITORY");
			//Append TERRITORY to "ARTICLE_PRICE"
			article_price.appendChild(territory);
			territory.insertBefore(document.createTextNode(salePrice.getCountry().getIsocode()), territory.getLastChild());
		}

	}

	
	
}
