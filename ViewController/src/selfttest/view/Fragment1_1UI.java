package selfttest.view;

import com.soaint.ADFUtil;
import com.soaint.JSFUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.component.rich.nav.RichButton;

import oracle.adf.view.rich.event.DialogEvent;

import selfttest.model.Empleado;
import selfttest.model.PrimitiveContainer;

import selfttest.service.SOAPFacade;

public class Fragment1_1UI {

    private List<Empleado> listaempfrag;
    private PrimitiveContainer contenfrag;
    private int currentemploid;
    private Empleado currentselectedemplo;

    public Fragment1_1UI() {
        super();
        this.listaempfrag = (List<Empleado>) JSFUtil.resolveExpression("#{pageFlowScope.ListaEmpFragmentsFlow}");
        this.contenfrag =
            (PrimitiveContainer) JSFUtil.resolveExpression("#{pageFlowScope.PrimitiveContainerFragmentsFlow}");
        this.currentemploid = 0000;
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

        SOAPFacade.fillEmplos(this.listaempfrag);

//        this.listaempfrag.add(new Empleado(432, "DanchoMan", 54, new Date()));
//        this.listaempfrag.add(new Empleado(65, "JennyWoman", 34, new Date()));
//        this.listaempfrag.add(new Empleado(7, "AmieBabie", 12, new Date()));
//        this.listaempfrag.add(new Empleado(876, "AaronBoy", 9, new Date()));

    }


    public void setCurrentemploid(int currentemploid) {
        this.currentemploid = currentemploid;
    }

    public int getCurrentemploid() {
        return currentemploid;
    }


    public void setCurrentselectedemplo(Empleado currentselectedemplo) {
        this.currentselectedemplo = currentselectedemplo;
    }

    public Empleado getCurrentselectedemplo() {
        return currentselectedemplo;
    }


    public void deleteById(ActionEvent actionEvent) {

        Iterator ite = this.listaempfrag.iterator();

        while (ite.hasNext()) {

            Empleado temply = (Empleado) ite.next();

            if (temply.getId() == this.currentemploid) {
                this.listaempfrag.remove(temply);
                break;
            }

        }
        
        JSFUtil.refreshComponent(JSFUtil.findComponentInRoot("aot1refreshme"));
        
    }

    public void deleteFromRow(ActionEvent actionEvent) {
        
        RichButton button = (RichButton) actionEvent.getComponent();
        Empleado emplodel = (Empleado) button.getAttributes().get("emplo");
        
        this.listaempfrag.remove(emplodel);
        
        JSFUtil.refreshComponent(JSFUtil.findComponentInRoot("ottorefresh1"));
        
        ADFUtil.dispararContextualEvent("onUpdateRunBinding", emplodel);
        
    }

    public void dialogoCerrado(DialogEvent dialogEvent) {
        Outcome out = dialogEvent.getOutcome();
        
        if(out.equals(Outcome.yes)){
            JSFUtil.addErrorMessage("YEAP WAS CLICKED !!");
        } else {
            JSFUtil.addInfoMessage("nope was clicked!");
        }
        
    }

    public void setEmploparaPop2(ActionEvent actionEvent) {
        
        RichButton buttie = (RichButton) actionEvent.getComponent();
        Empleado emplotosentout = (Empleado) buttie.getAttributes().get("emplosent");
        
        this.currentselectedemplo = emplotosentout;

    }
}
