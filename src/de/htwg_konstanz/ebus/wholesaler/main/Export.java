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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOProduct;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.ProductBOA;

public class Export {

	private String xmlDocument;
	
	/**
	 * exports the whole Catalog
	 * @return 
	 */
	public static Document exportAll(ArrayList<String> errorList){
		Document doc= null;
		try {
			 doc = createBasisDOM();
			 doc = getAllArcticles(doc);
		} catch (ParserConfigurationException e) {
			System.out.println("Error in DOM Basis creation");
			e.printStackTrace();
		}
		
		
		return doc;
	}
	
	public static String makeFile(Document doc, ServletContext context, Integer integer){
		String path="catalog_export.XML";
		File file=null;
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			file = new File(context.getRealPath(path));
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			System.out.println("Configuration Error while transforming");
			e.printStackTrace();
		} catch (TransformerException e) {
			System.out.println("Error while transforming");
			e.printStackTrace();
		}
		
		return path;
	}
	
	public static Document createBasisDOM() throws ParserConfigurationException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();
		
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
	
	public static Document createArticleDOM(Document document, BOProduct bop){
		
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
		
		/** Hier muss �ber den Datenbankzugriff die entsprechende Supplier_AID herausgelesen werden */
		
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
		return document;
		
	}
	
	public static Document getAllArcticles(Document document){
		List<BOProduct> productList = ProductBOA.getInstance().findAll();
		for(BOProduct bop : productList){
			document = createArticleDOM(document, bop);
		}
		return document;
	}
	
	public Document createArticlePriceDOM(Document document){
		return null;
	}
}
