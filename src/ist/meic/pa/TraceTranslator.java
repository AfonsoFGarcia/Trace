package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.Translator;

public class TraceTranslator implements Translator {

    private Class<?> tracerType;

    public TraceTranslator(Class<?> tracerType) {
        this.tracerType = tracerType;
    }

    @Override
    public void onLoad(ClassPool pool, String className) throws NotFoundException, CannotCompileException {
        CtClass ct = pool.get(className);
        makeTraceable(ct);
    }

    private void makeTraceable(CtClass ct) throws CannotCompileException {

        ct.instrument(TraceExprEditorFactory.getExprEditor(tracerType));

    }

    @Override
    public void start(ClassPool arg0) throws NotFoundException, CannotCompileException {
    }
}
