package selfttest.view;

import com.soaint.JSFUtil;

import selfttest.model.Empleado;
import selfttest.model.PrimitiveContainer;

public class Fragment2_1UI {

    PrimitiveContainer contenfrag2;

    public Fragment2_1UI() {
        super();

        this.contenfrag2 = (PrimitiveContainer) JSFUtil.resolveExpression("#{pageFlowScope.PrimitContFrag2}");

    }


    public void setContenfrag2(PrimitiveContainer contenfrag2) {
        this.contenfrag2 = contenfrag2;
    }

    public PrimitiveContainer getContenfrag2() {
        return contenfrag2;
    }

    public void saySomething(Object objeto) {

        if (objeto.getClass().equals(Empleado.class)) {
            JSFUtil.addInfoMessage("El empleado eliminado es: " + ((Empleado)objeto).getNombre() + ". Y mi estado booleano es: " + this.contenfrag2.isShowcontrols());
        }

    }

}
