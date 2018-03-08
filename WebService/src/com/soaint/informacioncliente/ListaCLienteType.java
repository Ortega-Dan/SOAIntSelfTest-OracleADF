
package com.soaint.informacioncliente;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaCLienteType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ListaCLienteType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="listadoClientes" type="{http://www.soaint.com/InformacionCliente/}ClienteType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaCLienteType", propOrder = { "listadoClientes" })
public class ListaCLienteType {

    protected List<ClienteType> listadoClientes;

    /**
     * Gets the value of the listadoClientes property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listadoClientes property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListadoClientes().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClienteType }
     *
     *
     */
    public List<ClienteType> getListadoClientes() {
        if (listadoClientes == null) {
            listadoClientes = new ArrayList<ClienteType>();
        }
        return this.listadoClientes;
    }

}
