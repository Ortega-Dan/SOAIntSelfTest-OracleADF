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

    public void saySomething(Object carga) {

        if (carga.getClass().equals(Empleado.class)) {
            JSFUtil.addInfoMessage("El empleado eliminado es: " + ((Empleado)carga).getNombre() + "Y mi estado booleano es: " + contenfrag2.isShowcontrols());
        }

    }

}
