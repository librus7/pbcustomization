package com.piraeus.component;

public class EventType implements Comparable<EventType> {
	public final String product_code;
	public final String event_code;
	
	public EventType(String product_code, String event_code) {
		this.product_code = product_code;
		this.event_code = event_code;
	}
	
	public boolean equals(EventType other) {
		return this.product_code.equals(other.product_code) && this.event_code.equals(other.event_code);
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof EventType && equals((EventType)other);
	}

	@Override
	public int compareTo(EventType o) {
		int result = product_code.compareTo(o.product_code);
		if (result == 0) result = event_code.compareTo(o.event_code);
		return result;
	}
	
	@Override
	public int hashCode() {
		return product_code.hashCode() ^ event_code.hashCode();
	}
	
	@Override
	public String toString() {
		return"(" + product_code + ":" + event_code + ")";
	}
}
