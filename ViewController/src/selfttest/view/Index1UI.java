package selfttest.view;

import java.util.ArrayList;
import java.util.List;

import selfttest.model.Empleado;

public class Index1UI {

    private List<Empleado> listaemp1;
    private int listasize;

    public Index1UI() {
        super();
        this.listaemp1 = new ArrayList<Empleado>();

    }


    public void setListaemp1(List<Empleado> listaemp1) {
        this.listaemp1 = listaemp1;
    }

    public List<Empleado> getListaemp1() {
        return listaemp1;
    }


    public void setListasize(int listasize) {
        this.listasize = listasize;
    }

    public int getListasize() {
        
        return this.listaemp1.size();
    }

}