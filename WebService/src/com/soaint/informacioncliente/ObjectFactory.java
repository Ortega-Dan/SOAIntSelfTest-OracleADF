
package com.soaint.informacioncliente;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.soaint.informacioncliente package.
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

    private final static QName _ConsultarClientesRs_QNAME =
        new QName("http://www.soaint.com/InformacionCliente/", "consultarClientesRs");
    private final static QName _ConsultarClientesRq_QNAME =
        new QName("http://www.soaint.com/InformacionCliente/", "consultarClientesRq");
    private final static QName _ClientePoseeMoraRq_QNAME =
        new QName("http://www.soaint.com/InformacionCliente/", "clientePoseeMoraRq");
    private final static QName _ClientePoseeMoraRs_QNAME =
        new QName("http://www.soaint.com/InformacionCliente/", "clientePoseeMoraRs");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.soaint.informacioncliente
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListaCLienteType }
     *
     */
    public ListaCLienteType createListaCLienteType() {
        return new ListaCLienteType();
    }

    /**
     * Create an instance of {@link ClienteType }
     *
     */
    public ClienteType createClienteType() {
        return new ClienteType();
    }

    /**
     * Create an instance of {@link MoraType }
     *
     */
    public MoraType createMoraType() {
        return new MoraType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListaCLienteType }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.soaint.com/InformacionCliente/", name = "consultarClientesRs")
    public JAXBElement<ListaCLienteType> createConsultarClientesRs(ListaCLienteType value) {
        return new JAXBElement<ListaCLienteType>(_ConsultarClientesRs_QNAME, ListaCLienteType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClienteType }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.soaint.com/InformacionCliente/", name = "consultarClientesRq")
    public JAXBElement<ClienteType> createConsultarClientesRq(ClienteType value) {
        return new JAXBElement<ClienteType>(_ConsultarClientesRq_QNAME, ClienteType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClienteType }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.soaint.com/InformacionCliente/", name = "clientePoseeMoraRq")
    public JAXBElement<ClienteType> createClientePoseeMoraRq(ClienteType value) {
        return new JAXBElement<ClienteType>(_ClientePoseeMoraRq_QNAME, ClienteType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoraType }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.soaint.com/InformacionCliente/", name = "clientePoseeMoraRs")
    public JAXBElement<MoraType> createClientePoseeMoraRs(MoraType value) {
        return new JAXBElement<MoraType>(_ClientePoseeMoraRs_QNAME, MoraType.class, null, value);
    }

}
