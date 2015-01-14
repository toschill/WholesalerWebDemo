
package de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UpdateCatalogRequest_QNAME = new QName("http://new.webservice.namespace", "updateCatalogRequest");
    private final static QName _AuthenticationFault_QNAME = new QName("http://new.webservice.namespace", "authenticationFault");
    private final static QName _UpdateResponse_QNAME = new QName("http://new.webservice.namespace", "updateResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateResponseType }
     * 
     */
    public UpdateResponseType createUpdateResponseType() {
        return new UpdateResponseType();
    }

    /**
     * Create an instance of {@link UpdateCatalogRequestType }
     * 
     */
    public UpdateCatalogRequestType createUpdateCatalogRequestType() {
        return new UpdateCatalogRequestType();
    }

    /**
     * Create an instance of {@link ListOfUpdatedProductsType }
     * 
     */
    public ListOfUpdatedProductsType createListOfUpdatedProductsType() {
        return new ListOfUpdatedProductsType();
    }

    /**
     * Create an instance of {@link ListOfUnavailableProductsType }
     * 
     */
    public ListOfUnavailableProductsType createListOfUnavailableProductsType() {
        return new ListOfUnavailableProductsType();
    }

    /**
     * Create an instance of {@link ListOfProductsType }
     * 
     */
    public ListOfProductsType createListOfProductsType() {
        return new ListOfProductsType();
    }

    /**
     * Create an instance of {@link PriceType }
     * 
     */
    public PriceType createPriceType() {
        return new PriceType();
    }

    /**
     * Create an instance of {@link AuthenticationType }
     * 
     */
    public AuthenticationType createAuthenticationType() {
        return new AuthenticationType();
    }

    /**
     * Create an instance of {@link SupplierProductType }
     * 
     */
    public SupplierProductType createSupplierProductType() {
        return new SupplierProductType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCatalogRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "updateCatalogRequest")
    public JAXBElement<UpdateCatalogRequestType> createUpdateCatalogRequest(UpdateCatalogRequestType value) {
        return new JAXBElement<UpdateCatalogRequestType>(_UpdateCatalogRequest_QNAME, UpdateCatalogRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "authenticationFault")
    public JAXBElement<String> createAuthenticationFault(String value) {
        return new JAXBElement<String>(_AuthenticationFault_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "updateResponse")
    public JAXBElement<UpdateResponseType> createUpdateResponse(UpdateResponseType value) {
        return new JAXBElement<UpdateResponseType>(_UpdateResponse_QNAME, UpdateResponseType.class, null, value);
    }

}
