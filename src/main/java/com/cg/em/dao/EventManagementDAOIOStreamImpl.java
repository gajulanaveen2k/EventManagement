package com.cg.em.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import com.cg.em.exception.EventManagementException;
import com.cg.em.model.Event;
import com.cg.em.service.LocationComparator;
import com.cg.em.service.DateComparator;

public class EventManagementDAOIOStreamImpl implements IEventManagementDAO {
	public static final String DATA_STORE_FILE_NAME = "eventmanagement.dat";

	private Map<String, Event> events;

	public EventManagementDAOIOStreamImpl() throws EventManagementException {
		File file = new File(DATA_STORE_FILE_NAME);

		if (!file.exists()) {
			events = new TreeMap<>();
		} else {
			try (ObjectInputStream fin = new ObjectInputStream(
					
					new FileInputStream(DATA_STORE_FILE_NAME))) {

				Object obj = fin.readObject();

				if (obj instanceof Map) {
					events = (Map<String, Event>) obj;
				} else {
					throw new EventManagementException("Not a valid DataStore");
				}
					
				
			} catch (IOException | ClassNotFoundException exp) {
				throw new EventManagementException("Loading Data Failed");
			}
		}
	}

	@Override
	public String add(Event event) throws EventManagementException {
		String id = null;
		if (event != null) {
			id = event.getId();
			events.put(id, event);
		}
		return event.getId();
	}

	@Override
	public boolean delete(String id) throws EventManagementException {
		boolean isDone = false;
		if (id != null) {
			events.remove(id);
			isDone = true;
		}
		return isDone;
	}

	@Override
	public List<Event> getAllEventsInLocation(String location) throws EventManagementException {
		List<Event> a=new ArrayList<Event>();
		events.entrySet().stream().forEach(e ->{
			if(e.getValue().getLocation().equals(location)) {
				a.add(e.getValue());
			}	
		});
		return a;
	}
	@Override
	public List<Event> getAllEventsOnMentionedDate(LocalDate dateMentioned) throws EventManagementException {
		List<Event> a=new ArrayList<Event>();
		events.entrySet().stream().forEach(e ->{
			if(e.getValue().getDateScheduled().equals(dateMentioned)) {
				a.add(e.getValue());
			}	
		});
		return a;
	}

	
	@Override
	public List<Event> getAllEventsInOrderOfLocation() throws EventManagementException{
		List<Event> sortLocation = new ArrayList<Event>(events.values());
		Collections.sort(sortLocation,new LocationComparator());
		return sortLocation;
	}
	@Override
	public List<Event> getAllEventsInOrderOfDateScheduled() throws EventManagementException{
		List<Event> sortDateScheduled = new ArrayList<Event>(events.values());
		Collections.sort(sortDateScheduled,new DateComparator());
		return sortDateScheduled;
	}


	@Override
	public void persist() throws EventManagementException {
		try (ObjectOutputStream fout = new ObjectOutputStream(
				new FileOutputStream(DATA_STORE_FILE_NAME))) {
			fout.writeObject(events);
		} catch (IOException exp) {
			throw new EventManagementException("Saving Data Failed" + exp.getMessage());
		}
	}
}
