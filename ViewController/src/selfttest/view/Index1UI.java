package selfttest.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import selfttest.model.Empleado;
import selfttest.model.PrimitiveContainer;

public class Index1UI {

    private List<Empleado> listaemp1;
    private PrimitiveContainer primcontainer;

    public Index1UI() {
        super();
        this.listaemp1 = new ArrayList<Empleado>();
        this.primcontainer = new PrimitiveContainer();
        this.primcontainer.setShowcontrols(true);
        
    }


    public void setListaemp1(List<Empleado> listaemp1) {
        this.listaemp1 = listaemp1;
    }

    public List<Empleado> getListaemp1() {
        return listaemp1;
    }


    public int getListasize() {
        
        return this.listaemp1.size();
    }


    public void setPrimcontainer(PrimitiveContainer primcontainer) {
        this.primcontainer = primcontainer;
    }

    public PrimitiveContainer getPrimcontainer() {
        return primcontainer;
    }


}
