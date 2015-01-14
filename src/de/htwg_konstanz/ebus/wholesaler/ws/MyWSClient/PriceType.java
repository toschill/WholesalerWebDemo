
package de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for priceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="priceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://new.webservice.namespace}amount"/>
 *         &lt;element ref="{http://new.webservice.namespace}currency"/>
 *         &lt;element ref="{http://new.webservice.namespace}tax"/>
 *         &lt;element ref="{http://new.webservice.namespace}pricetype"/>
 *         &lt;element ref="{http://new.webservice.namespace}countryISOCode"/>
 *         &lt;element ref="{http://new.webservice.namespace}lowerBound"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "priceType", propOrder = {
    "amount",
    "currency",
    "tax",
    "pricetype",
    "countryISOCode",
    "lowerBound"
})
public class PriceType {

    @XmlElement(required = true)
    protected BigDecimal amount;
    @XmlElement(required = true)
    protected String currency;
    @XmlElement(required = true)
    protected BigDecimal tax;
    @XmlElement(required = true)
    protected String pricetype;
    @XmlElement(required = true)
    protected String countryISOCode;
    @XmlElement(required = true)
    protected BigInteger lowerBound;

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the tax property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTax() {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTax(BigDecimal value) {
        this.tax = value;
    }

    /**
     * Gets the value of the pricetype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricetype() {
        return pricetype;
    }

    /**
     * Sets the value of the pricetype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricetype(String value) {
        this.pricetype = value;
    }

    /**
     * Gets the value of the countryISOCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryISOCode() {
        return countryISOCode;
    }

    /**
     * Sets the value of the countryISOCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryISOCode(String value) {
        this.countryISOCode = value;
    }

    /**
     * Gets the value of the lowerBound property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLowerBound() {
        return lowerBound;
    }

    /**
     * Sets the value of the lowerBound property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLowerBound(BigInteger value) {
        this.lowerBound = value;
    }

}
