package ist.meic.pa.test;

import ist.meic.pa.Trace;

class Test {
	public Object foo() {
		return new String("Foo");

	}

	public Object bar() {
		return foo();
	}
	
	public Object baz(){
		return bar();
	}

	public Object identity(Object o) {
		return o;
	}

	public void test() {
		Trace.print(foo());
		Trace.print(bar());
		Trace.print(baz());
	}
}

public class Test0 {
	public static void main(String args[]) {
		(new Test()).test();
	}
}
