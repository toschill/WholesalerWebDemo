
package de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateCatalogRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateCatalogRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authentication" type="{http://new.webservice.namespace}authenticationType"/>
 *         &lt;element name="listOfProducts" type="{http://new.webservice.namespace}listOfProductsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateCatalogRequestType", propOrder = {
    "authentication",
    "listOfProducts"
})
public class UpdateCatalogRequestType {

    @XmlElement(required = true)
    protected AuthenticationType authentication;
    @XmlElement(required = true)
    protected ListOfProductsType listOfProducts;

    /**
     * Gets the value of the authentication property.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticationType }
     *     
     */
    public AuthenticationType getAuthentication() {
        return authentication;
    }

    /**
     * Sets the value of the authentication property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticationType }
     *     
     */
    public void setAuthentication(AuthenticationType value) {
        this.authentication = value;
    }

    /**
     * Gets the value of the listOfProducts property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfProductsType }
     *     
     */
    public ListOfProductsType getListOfProducts() {
        return listOfProducts;
    }

    /**
     * Sets the value of the listOfProducts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfProductsType }
     *     
     */
    public void setListOfProducts(ListOfProductsType value) {
        this.listOfProducts = value;
    }

}
