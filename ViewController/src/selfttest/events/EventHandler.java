package selfttest.events;

import com.soaint.JSFUtil;

import selfttest.view.Fragment2_1UI;

public class EventHandler {
    public EventHandler() {
        super();
    }

    public static void processEventYay(Object carga) {
        
        Fragment2_1UI fragment2 = (Fragment2_1UI) JSFUtil.resolveExpression("#{pageFlowScope.Fragment2_1Bean}");
        fragment2.saySomething(carga);
    
    }
}
