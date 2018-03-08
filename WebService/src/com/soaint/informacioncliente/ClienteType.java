
package com.soaint.informacioncliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ClienteType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ClienteType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tipoIdentificacion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeroIdentificacion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="nombres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="apellidos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="edad" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="fechaRegistro" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClienteType", propOrder = {
         "tipoIdentificacion", "numeroIdentificacion", "nombres", "apellidos", "edad", "fechaRegistro"
    })
public class ClienteType {

    @XmlElement(required = true)
    protected String tipoIdentificacion;
    protected int numeroIdentificacion;
    protected String nombres;
    protected String apellidos;
    protected Integer edad;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaRegistro;

    /**
     * Gets the value of the tipoIdentificacion property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Sets the value of the tipoIdentificacion property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTipoIdentificacion(String value) {
        this.tipoIdentificacion = value;
    }

    /**
     * Gets the value of the numeroIdentificacion property.
     *
     */
    public int getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Sets the value of the numeroIdentificacion property.
     *
     */
    public void setNumeroIdentificacion(int value) {
        this.numeroIdentificacion = value;
    }

    /**
     * Gets the value of the nombres property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Sets the value of the nombres property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNombres(String value) {
        this.nombres = value;
    }

    /**
     * Gets the value of the apellidos property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Sets the value of the apellidos property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setApellidos(String value) {
        this.apellidos = value;
    }

    /**
     * Gets the value of the edad property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getEdad() {
        return edad;
    }

    /**
     * Sets the value of the edad property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setEdad(Integer value) {
        this.edad = value;
    }

    /**
     * Gets the value of the fechaRegistro property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Sets the value of the fechaRegistro property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setFechaRegistro(XMLGregorianCalendar value) {
        this.fechaRegistro = value;
    }

}
