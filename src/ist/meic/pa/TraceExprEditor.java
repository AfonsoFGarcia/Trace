package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

public class TraceExprEditor extends ExprEditor {

	public void edit(MethodCall m) throws CannotCompileException {

		String r = "";
		if (m.where().getDeclaringClass().getName().equals("ist.meic.pa.Trace"))
			return;

		try {
			r += "for(int i = 0; i < $args.length; i++) {ist.meic.pa.Trace.addTraceInfo($args[i], \"-> " + getLineInfo(m) + "\");}$_= $proceed($$);";
			if (!m.getMethod().getReturnType().equals(CtClass.voidType)) {
				r += "ist.meic.pa.Trace.addTraceInfo(($r)$_,\"<- "
						+ getLineInfo(m) + "\");";
			}
			m.replace(r);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void edit(NewExpr e) throws CannotCompileException{
		if (e.where().getDeclaringClass().getName().equals("ist.meic.pa.Trace"))
			return;
				
		try {
			e.replace("$_= $proceed($$); ist.meic.pa.Trace.addTraceInfo(($r)$_,\"<- "
					+ getLineInfo(e) + "\");");
		} catch (NotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}

	private String getLineInfo(NewExpr e) throws NotFoundException {
		String r = e.getConstructor().getLongName() + " on " + e.getFileName() + ":"
				+ e.getLineNumber();

		return r;
	}

	public void edit(FieldAccess f) {

	}

	String getLineInfo(MethodCall m) throws NotFoundException {
		String r = m.getMethod().getLongName() + " on " + m.getFileName() + ":"
				+ m.getLineNumber();

		return r;
	}

}
