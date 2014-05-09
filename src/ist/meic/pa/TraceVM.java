package ist.meic.pa;

import javassist.ClassPool;
import javassist.Loader;
import javassist.Translator;

public class TraceVM {

    public static void main(String[] args) throws Throwable {
        ClassPool pool = ClassPool.getDefault();
        Translator translator = new TraceTranslator(TraceVM.class);
        Loader classLoader = new Loader();
        classLoader.addTranslator(pool, translator);
        String[] restArgs = new String[args.length - 1];
        System.arraycopy(args, 1, restArgs, 0, restArgs.length);
        classLoader.run(args[0], restArgs);
    }
}
