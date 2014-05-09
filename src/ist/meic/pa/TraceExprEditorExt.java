package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import javassist.expr.NewArray;

public class TraceExprEditorExt extends TraceExprEditor {

    @Override
    public void edit(NewArray a) throws CannotCompileException {
        if (a.where().getDeclaringClass().getName().equals("ist.meic.pa.Trace")) {
            return;
        }

        try {
            a.replace("$_= $proceed($$); ist.meic.pa.Trace.addTraceInfo(($r)$_,\"<- " + getLineInfo(a) + "\");");
        } catch (NotFoundException e1) {
            e1.printStackTrace();
        }
    }

    private String getLineInfo(NewArray a) throws NotFoundException {
        return a.getComponentType().getName() + "[]" + " on " + a.getFileName() + ":" + a.getLineNumber();
    }
}
