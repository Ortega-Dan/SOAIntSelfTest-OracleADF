package selfttest.view;

import com.soaint.JSFUtil;

import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;

import selfttest.model.Empleado;
import selfttest.model.PrimitiveContainer;

public class Fragment1UI {
    
    private List<Empleado> listaempfrag;
    private PrimitiveContainer contenfrag;
    
    public Fragment1UI() {
        super();
        this.listaempfrag = (List<Empleado>) JSFUtil.resolveExpression("#{pageFlowScope.ListaEmpFragmentsFlow}");
        this.contenfrag = (PrimitiveContainer) JSFUtil.resolveExpression("#{pageFlowScope.PrimitiveContainerFragmentsFlow}");
    }


    public void setListaempfrag(List<Empleado> listaempfrag) {
        this.listaempfrag = listaempfrag;
    }

    public List<Empleado> getListaempfrag() {
        return listaempfrag;
    }

    public void setContenfrag(PrimitiveContainer contenfrag) {
        this.contenfrag = contenfrag;
    }

    public PrimitiveContainer getContenfrag() {
        return contenfrag;
    }


    public void fillEmplos(ActionEvent actionEvent) {
        
        this.listaempfrag.clear();
        
        
        this.listaempfrag.add(new Empleado(432, "DanchoMan", 54, new Date()));        
        this.listaempfrag.add(new Empleado(65, "JennyWoman", 34, new Date()));        
        this.listaempfrag.add(new Empleado(7, "AmieBabie", 12, new Date()));        
        this.listaempfrag.add(new Empleado(876, "AaronBoy", 9, new Date()));
        
    }
}
