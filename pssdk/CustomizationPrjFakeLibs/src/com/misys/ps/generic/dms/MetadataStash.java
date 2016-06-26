package com.misys.ps.generic.dms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class MetadataStash {
	public static MetadataStash getInstance() {
		// TODO Auto-generated method stub
		return new MetadataStash();
	}

	public Collection<Map.Entry<String, FieldCode>> getRequiredFieldsByCode() {
		// TODO Auto-generated method stub
		return new ArrayList<Map.Entry<String, FieldCode>>();
	}

	public void setAttribute(String name, String value) {
		// TODO Auto-generated method stub
		
	}
}
