package com.cg.em.service;

import java.util.Comparator;

import com.cg.em.model.Event;

public class LocationComparator implements Comparator<Event> {
	@Override
	public int compare(Event e1, Event e2) {
		String firstLocation=e1.getLocation();
		String secondLocation=e2.getLocation();
		return firstLocation.compareTo(secondLocation);
	}
}
