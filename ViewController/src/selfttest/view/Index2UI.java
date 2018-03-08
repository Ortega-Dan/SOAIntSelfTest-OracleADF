package selfttest.view;

import com.soaint.JSFUtil;

import java.util.List;

import selfttest.model.Empleado;
import selfttest.model.PrimitiveContainer;

public class Index2UI {
    
    private List<Empleado> listapag2;
    private PrimitiveContainer paramcontpag2;
    
    public Index2UI() {
        super();
        
        this.listapag2 = (List<Empleado>) JSFUtil.resolveExpression("#{pageFlowScope.Index1Bean.listaemp1}");
        this.paramcontpag2 = new PrimitiveContainer();
        this.paramcontpag2.setShowcontrols(false);
    }


    public void setListapag2(List<Empleado> listapag2) {
        this.listapag2 = listapag2;
    }

    public List<Empleado> getListapag2() {
        return listapag2;
    }


    public void setParamcontpag2(PrimitiveContainer paramcontpag2) {
        this.paramcontpag2 = paramcontpag2;
    }

    public PrimitiveContainer getParamcontpag2() {
        return paramcontpag2;
    }

}
