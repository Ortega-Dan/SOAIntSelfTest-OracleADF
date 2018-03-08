package selfttest.service;

import java.util.Iterator;
import java.util.List;

import selfttest.model.Empleado;

import com.soaint.informacioncliente.*;

public class SOAPFacade {
    public SOAPFacade() {
        super();
    }

    public static void fillEmplos(List<Empleado> listaallenar) {
        
        ServicioInformacionCliente servicioInformacionCliente = new ServicioInformacionCliente();
        InformacionClientePort informacionClientePort = servicioInformacionCliente.getInformacionCliente();
        // Add your code to call the desired methods.

        ClienteType rq = new ClienteType();
        rq.setEdad(23);

        ListaCLienteType rs = informacionClientePort.consultarClientes(rq);

        List<ClienteType> listaCliente = rs.getListadoClientes();

        Iterator listaClienteIte = listaCliente.iterator();

        while (listaClienteIte.hasNext()) {
        
            ClienteType currentCli = (ClienteType) listaClienteIte.next();
            
            System.out.println(currentCli.getNombres());
            
            listaallenar.add(new Empleado(currentCli.getEdad(), currentCli.getNombres(), currentCli.getEdad(), currentCli.getFechaRegistro().toGregorianCalendar().getTime()));

        }

    }
}
