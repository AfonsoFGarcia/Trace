package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

public class TraceExprEditor extends ExprEditor {

	public void edit(MethodCall m) throws CannotCompileException {
		
		if(m.where().getDeclaringClass().getName().equals("ist.meic.pa.Trace")) return;
		
		try {
			if (!m.getMethod().getReturnType().equals(CtClass.voidType)) {
				m.replace("$_= $proceed($$); ist.meic.pa.Trace.addTraceInfo(($r)$_,\"<-" + getLineInfo(m)
						+ "\");");
			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void edit(ConstructorCall c) {

	}

	public void edit(FieldAccess f) {

	}

	String getLineInfo(MethodCall m) {
		String r = m.where().getLongName() + " on " + m.getFileName() + ":"
				+ m.getLineNumber();

		return r;
	}

}
