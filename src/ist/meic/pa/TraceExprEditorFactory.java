package ist.meic.pa;

import javassist.expr.ExprEditor;

public class TraceExprEditorFactory {

    public static ExprEditor getExprEditor(Class<?> c) {
        if (c.equals(TraceVM.class)) {
            return new TraceExprEditor();
        } else {
            return new TraceExprEditorExt();
        }
    }

}
