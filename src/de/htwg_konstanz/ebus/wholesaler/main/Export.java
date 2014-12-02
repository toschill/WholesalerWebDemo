package de.htwg_konstanz.ebus.wholesaler.main;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Export {

	private String xmlDocument;
	
	/**
	 * exports the whole Catalog
	 * @return 
	 */
	public static String exportAll(ArrayList<String> errorList){
		
	}
	
	public String createDOM() throws ParserConfigurationException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();
		
		//Create Root-Element with attributes
		Element root = document.createElement("BMECAT");
		root.setAttribute("version", "1.2");
		root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("xsi:noNamespaceSchemaLocation", "bmecat_new_catalog_1_2_simple_V0.96.xsd");
		//Append Root-Element to our document
		document.appendChild(root);
		
		//Create Header-Element
		Element header = document.createElement("HEADER");
		//Append Header-Element to "BMECAT"
		root.appendChild(header);
		
		//Create Header-Element
		Element catalog = document.createElement("CATALOG");
		//Append Header-Element to "HEADER"
		header.appendChild(catalog);
		
		//Create Header-Element
		Element language = document.createElement("LANGUAGE");
		//Append Header-Element to "CATALOG"
		root.appendChild();
	}
}
