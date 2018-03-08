package selfttest.view;

import com.soaint.JSFUtil;

import selfttest.model.Empleado;

public class SecondPopUpUI {
    
    Empleado emplopopup;
    
    
    public SecondPopUpUI() {
        super();
        
        this.emplopopup = (Empleado) JSFUtil.resolveExpression("#{pageFlowScope.emplopop}");
        
    }


    public void setEmplopopup(Empleado emplopopup) {
        this.emplopopup = emplopopup;
    }

    public Empleado getEmplopopup() {
        return emplopopup;
    }

}
