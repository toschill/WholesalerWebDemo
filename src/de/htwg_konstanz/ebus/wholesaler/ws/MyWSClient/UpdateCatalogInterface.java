
package de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.0
 * 
 */
@WebService(name = "updateCatalogInterface", targetNamespace = "http://new.webservice.namespace")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface UpdateCatalogInterface {


    /**
     * 
     * @param body
     * @return
     *     returns de.htwg_konstanz.ebus.wholesaler.ws.MyWSClient.UpdateResponseType
     * @throws AuthenticationFaultMessage
     */
    @WebMethod(action = "http://localhost:8080/WholesalerWebDemo/updateCatalogAction")
    @WebResult(name = "updateResponse", targetNamespace = "http://new.webservice.namespace", partName = "body")
    public UpdateResponseType updateCatalog(
        @WebParam(name = "updateCatalogRequest", targetNamespace = "http://new.webservice.namespace", partName = "body")
        UpdateCatalogRequestType body)
        throws AuthenticationFaultMessage
    ;

}
