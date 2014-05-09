package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

public class TraceExprEditor extends ExprEditor {

    @Override
    public void edit(MethodCall m) throws CannotCompileException {

        String r = "";
        if (m.where().getDeclaringClass().getName().equals("ist.meic.pa.Trace")) {
            return;
        }

        try {
            r +=
                    "for(int i = 0; i < $args.length; i++) {if(!$args[i].getClass().isPrimitive()) ist.meic.pa.Trace.addTraceInfo($args[i], \"-> "
                            + getLineInfo(m) + "\");}$_= $proceed($$);";
            if (!(m.getMethod().getReturnType().equals(CtClass.voidType) || m.getMethod().getReturnType().isPrimitive())) {
                r += "ist.meic.pa.Trace.addTraceInfo(($r)$_,\"<- " + getLineInfo(m) + "\");";
            }
            m.replace(r);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void edit(NewExpr e) throws CannotCompileException {
        if (e.where().getDeclaringClass().getName().equals("ist.meic.pa.Trace")) {
            return;
        }

        try {
            e.replace("$_= $proceed($$); ist.meic.pa.Trace.addTraceInfo(($r)$_,\"<- " + getLineInfo(e) + "\");");
        } catch (NotFoundException e1) {
            e1.printStackTrace();
        }

    }

    private String getLineInfo(NewExpr e) throws NotFoundException {
        return e.getConstructor().getLongName() + " on " + e.getFileName() + ":" + e.getLineNumber();
    }

    private String getLineInfo(MethodCall m) throws NotFoundException {
        return m.getMethod().getLongName() + " on " + m.getFileName() + ":" + m.getLineNumber();
    }

}
