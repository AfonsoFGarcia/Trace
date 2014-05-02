package ist.meic.pa;

import java.util.ArrayList;
import java.util.HashMap;

public class Trace {
	
	static HashMap<Object, ArrayList<String>> traceInfo = new HashMap<Object, ArrayList<String>>();
	
	static public void addTraceInfo (Object o, String info){
		if(!traceInfo.containsKey(o)){
			traceInfo.put(o, new ArrayList<String>());
		}
		
		traceInfo.get(o).add(info);
	
	}

	static public void print(Object o){
		if(traceInfo.containsKey(o)){
			System.err.println("Tracing for " + o.toString());
			for(String out : traceInfo.get(o)){
				System.err.println("  " + out);
			}
		}
		else System.err.println("Tracing for " + o.toString() + " is nonexistent!");
	}
}
