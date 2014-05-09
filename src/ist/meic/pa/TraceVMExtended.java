package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.Translator;

public class TraceVMExtended {

    public static void main(String[] args) throws Throwable {
        ClassPool pool = ClassPool.getDefault();
        CtClass tracer = pool.get("ist.meic.pa.Trace");
        modifyPrint(tracer);
        Translator translator = new TraceTranslator(TraceVMExtended.class);
        Loader classLoader = new Loader();
        classLoader.addTranslator(pool, translator);
        String[] restArgs = new String[args.length - 1];
        System.arraycopy(args, 1, restArgs, 0, restArgs.length);
        classLoader.run(args[0], restArgs);
    }

    private static void modifyPrint(CtClass tracer) {
        String newMethodBody =
                "{ String ret = \"\"; try {if ($1.getClass().isArray()) { ret += \"[ \"; for (int i = 0; i < java.lang.reflect.Array.getLength($1); i++) { ret += objectToString(java.lang.reflect.Array.get($1, i)); ret += \" \"; } ret += \"]\"; } else if ($1.getClass().isPrimitive()) { ret += $1; } else {  ret += $1.toString(); } } catch (java.lang.NullPointerException e) { ret += \"null\"; } return ret; }";
        try {
            CtMethod objectToString = tracer.getDeclaredMethod("objectToString");
            objectToString.setName("objectToString$original");
            objectToString = CtNewMethod.copy(objectToString, "objectToString", tracer, null);
            objectToString.setBody(newMethodBody);
            tracer.addMethod(objectToString);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

    }
}
