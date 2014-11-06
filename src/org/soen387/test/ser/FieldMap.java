package org.soen387.test.ser;

import java.util.HashMap;
import java.util.Map;

import org.dsrg.soenea.service.threadLocal.ThreadLocalTracker;

public class FieldMap extends ThreadLocal<Map<String, String>> {
	public static FieldMap current = new FieldMap();
	
	@Override
	protected Map<String, String> initialValue() {
		// TODO Auto-generated method stub
		return new HashMap<String, String>();
	}
	
	static {
		ThreadLocalTracker.registerThreadLocal(current);
	}
}
