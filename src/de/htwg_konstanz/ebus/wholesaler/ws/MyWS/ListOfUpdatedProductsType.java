
package de.htwg_konstanz.ebus.wholesaler.ws.MyWS;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for listOfUpdatedProductsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="listOfUpdatedProductsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://new.webservice.namespace}supplierProduct" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listOfUpdatedProductsType", propOrder = {
    "supplierProduct"
})
public class ListOfUpdatedProductsType {

    protected List<SupplierProductType> supplierProduct;

    /**
     * Gets the value of the supplierProduct property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supplierProduct property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupplierProduct().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupplierProductType }
     * 
     * 
     */

    public List<SupplierProductType> getSupplierProduct() {
        if (supplierProduct == null) {
            supplierProduct = new ArrayList<SupplierProductType>();
        }
        return this.supplierProduct;
    }

}
