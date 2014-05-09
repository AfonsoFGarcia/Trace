package ist.meic.pa;

import java.util.ArrayList;
import java.util.HashMap;

public class Trace {

    static HashMap<Integer, ArrayList<String>> traceInfo = new HashMap<Integer, ArrayList<String>>();

    static public void addTraceInfo(Object o, String info) {
        if (!traceInfo.containsKey(System.identityHashCode(o))) {
            traceInfo.put(System.identityHashCode(o), new ArrayList<String>());
        }

        traceInfo.get(System.identityHashCode(o)).add(info);

    }

    static public void print(Object o) {
        if (traceInfo.containsKey(System.identityHashCode(o))) {
            System.err.println("Tracing for " + objectToString(o));
            for (String out : traceInfo.get(System.identityHashCode(o))) {
                System.err.println("  " + out);
            }
        } else {
            System.err.println("Tracing for " + o.toString() + " is nonexistent!");
        }
    }

    static public String objectToString(Object o) {
        return o.toString();
    }
}
