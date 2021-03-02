package com.cg.em.service;

import java.util.Comparator;

import com.cg.em.model.Event;

public class DateComparator  implements Comparator<Event> {
	@Override
	public int compare(Event e1, Event e2) {
		String firstDate=e1.getDateScheduled().toString();
		String secondDate=e2.getDateScheduled().toString();
		return firstDate.compareTo(secondDate);
	}
}
