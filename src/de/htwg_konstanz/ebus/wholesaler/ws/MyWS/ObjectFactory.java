
package de.htwg_konstanz.ebus.wholesaler.ws.MyWS;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.htwg_konstanz.ebus.wholesaler.ws.MyWS package. 
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

    private final static QName _ListOfUpdatedProducts_QNAME = new QName("http://new.webservice.namespace", "listOfUpdatedProducts");
    private final static QName _WsUsername_QNAME = new QName("http://new.webservice.namespace", "wsUsername");
    private final static QName _Currency_QNAME = new QName("http://new.webservice.namespace", "currency");
    private final static QName _UpdateDate_QNAME = new QName("http://new.webservice.namespace", "updateDate");
    private final static QName _UpdateCatalogRequest_QNAME = new QName("http://new.webservice.namespace", "updateCatalogRequest");
    private final static QName _AuthenticationFault_QNAME = new QName("http://new.webservice.namespace", "authenticationFault");
    private final static QName _SupplierProduct_QNAME = new QName("http://new.webservice.namespace", "supplierProduct");
    private final static QName _LowerBound_QNAME = new QName("http://new.webservice.namespace", "lowerBound");
    private final static QName _SupplierAID_QNAME = new QName("http://new.webservice.namespace", "supplierAID");
    private final static QName _Pricetype_QNAME = new QName("http://new.webservice.namespace", "pricetype");
    private final static QName _LongDescription_QNAME = new QName("http://new.webservice.namespace", "longDescription");
    private final static QName _ShortDescription_QNAME = new QName("http://new.webservice.namespace", "shortDescription");
    private final static QName _CountryISOCode_QNAME = new QName("http://new.webservice.namespace", "countryISOCode");
    private final static QName _ListOfUnavailableProducts_QNAME = new QName("http://new.webservice.namespace", "listOfUnavailableProducts");
    private final static QName _Tax_QNAME = new QName("http://new.webservice.namespace", "tax");
    private final static QName _WsPassword_QNAME = new QName("http://new.webservice.namespace", "wsPassword");
    private final static QName _Authentication_QNAME = new QName("http://new.webservice.namespace", "authentication");
    private final static QName _UpdateResponse_QNAME = new QName("http://new.webservice.namespace", "updateResponse");
    private final static QName _ListOfProducts_QNAME = new QName("http://new.webservice.namespace", "listOfProducts");
    private final static QName _Amount_QNAME = new QName("http://new.webservice.namespace", "amount");
    private final static QName _Price_QNAME = new QName("http://new.webservice.namespace", "price");
    private final static QName _WholesalerName_QNAME = new QName("http://new.webservice.namespace", "wholesalerName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.htwg_konstanz.ebus.wholesaler.ws.MyWS
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SupplierProductType }
     * 
     */
    public SupplierProductType createSupplierProductType() {
        return new SupplierProductType();
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
     * Create an instance of {@link ListOfUnavailableProductsType }
     * 
     */
    public ListOfUnavailableProductsType createListOfUnavailableProductsType() {
        return new ListOfUnavailableProductsType();
    }

    /**
     * Create an instance of {@link UpdateResponseType }
     * 
     */
    public UpdateResponseType createUpdateResponseType() {
        return new UpdateResponseType();
    }

    /**
     * Create an instance of {@link AuthenticationType }
     * 
     */
    public AuthenticationType createAuthenticationType() {
        return new AuthenticationType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfUpdatedProductsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "listOfUpdatedProducts")
    public JAXBElement<ListOfUpdatedProductsType> createListOfUpdatedProducts(ListOfUpdatedProductsType value) {
        return new JAXBElement<ListOfUpdatedProductsType>(_ListOfUpdatedProducts_QNAME, ListOfUpdatedProductsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "wsUsername")
    public JAXBElement<String> createWsUsername(String value) {
        return new JAXBElement<String>(_WsUsername_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "currency")
    public JAXBElement<String> createCurrency(String value) {
        return new JAXBElement<String>(_Currency_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "updateDate")
    public JAXBElement<XMLGregorianCalendar> createUpdateDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_UpdateDate_QNAME, XMLGregorianCalendar.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link SupplierProductType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "supplierProduct")
    public JAXBElement<SupplierProductType> createSupplierProduct(SupplierProductType value) {
        return new JAXBElement<SupplierProductType>(_SupplierProduct_QNAME, SupplierProductType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "lowerBound")
    public JAXBElement<BigInteger> createLowerBound(BigInteger value) {
        return new JAXBElement<BigInteger>(_LowerBound_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "supplierAID")
    public JAXBElement<String> createSupplierAID(String value) {
        return new JAXBElement<String>(_SupplierAID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "pricetype")
    public JAXBElement<String> createPricetype(String value) {
        return new JAXBElement<String>(_Pricetype_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "longDescription")
    public JAXBElement<String> createLongDescription(String value) {
        return new JAXBElement<String>(_LongDescription_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "shortDescription")
    public JAXBElement<String> createShortDescription(String value) {
        return new JAXBElement<String>(_ShortDescription_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "countryISOCode")
    public JAXBElement<String> createCountryISOCode(String value) {
        return new JAXBElement<String>(_CountryISOCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfUnavailableProductsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "listOfUnavailableProducts")
    public JAXBElement<ListOfUnavailableProductsType> createListOfUnavailableProducts(ListOfUnavailableProductsType value) {
        return new JAXBElement<ListOfUnavailableProductsType>(_ListOfUnavailableProducts_QNAME, ListOfUnavailableProductsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "tax")
    public JAXBElement<BigDecimal> createTax(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Tax_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "wsPassword")
    public JAXBElement<String> createWsPassword(String value) {
        return new JAXBElement<String>(_WsPassword_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthenticationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "authentication")
    public JAXBElement<AuthenticationType> createAuthentication(AuthenticationType value) {
        return new JAXBElement<AuthenticationType>(_Authentication_QNAME, AuthenticationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "updateResponse")
    public JAXBElement<UpdateResponseType> createUpdateResponse(UpdateResponseType value) {
        return new JAXBElement<UpdateResponseType>(_UpdateResponse_QNAME, UpdateResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfProductsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "listOfProducts")
    public JAXBElement<ListOfProductsType> createListOfProducts(ListOfProductsType value) {
        return new JAXBElement<ListOfProductsType>(_ListOfProducts_QNAME, ListOfProductsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "amount")
    public JAXBElement<BigDecimal> createAmount(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Amount_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PriceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "price")
    public JAXBElement<PriceType> createPrice(PriceType value) {
        return new JAXBElement<PriceType>(_Price_QNAME, PriceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://new.webservice.namespace", name = "wholesalerName")
    public JAXBElement<String> createWholesalerName(String value) {
        return new JAXBElement<String>(_WholesalerName_QNAME, String.class, null, value);
    }

}
