package com.cg.em.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.em.dao.EventManagementDAOJDBCImpl;
import com.cg.em.dao.IEventManagementDAO;
import com.cg.em.exception.EventManagementException;
import com.cg.em.model.Event;

public class EventServiceImpl implements IEventService {
	
	private IEventManagementDAO eventDao;

	public IEventManagementDAO getDAO(){
		return eventDao;
	}
	
	public EventServiceImpl() throws EventManagementException {
		
		eventDao = new EventManagementDAOJDBCImpl();
		
	}
	
	public boolean isValidId(String id){
		
		/*
		 * First letter must be capital
		 * Followed by three digits
		 */
		Pattern idPattern = Pattern.compile("[A-Z]\\d{3}");
		Matcher idMatcher = idPattern.matcher(id);
		
		return idMatcher.matches();
	}
	
	public boolean isValidTitle(String title){
		/*
		 * First Letter should be capital
		 * Minimum length is 4 chars
		 * Maximum length is 20 chars.		
		 */
		Pattern titlePattern = Pattern.compile("[A-Z]\\w{3,19}");
		Matcher titleMatcher = titlePattern.matcher(title);
		
		return titleMatcher.matches();
	}
	
	public boolean isValidCost(double cost){
		/*
		 * Cost should be above 0
		 */
		return cost>=0;
	}
	
	public boolean isValidDatePublished(LocalDate datePublished){
		LocalDate today = LocalDate.now();
		return today.isAfter(datePublished) || today.equals(datePublished)|| today.isBefore(datePublished);
	}
	
	public boolean isValidEvent(Event event) throws EventManagementException{
		boolean isValid=false;
		
		List<String> errMsgs = new ArrayList<>();
		
		if(!isValidId(event.getId()))
			errMsgs.add("Id should start with a capital alphabet followed by 3 digits");
		
		if(!isValidTitle(event.getTitle()))
			errMsgs.add("Title must start with capital and must be in between 4 to 20 chars in length");
		
		if(!isValidCost(event.getCost()))
			errMsgs.add("Price must be above INR.0");
		
		if(!isValidDatePublished(event.getDateScheduled()))
			errMsgs.add("Publish Date should not be a future date");
		
		if(errMsgs.isEmpty())
			isValid=true;
		else
			throw new EventManagementException(errMsgs.toString());
		
		return isValid;
	}

	
	@Override
	public String add(Event event) throws EventManagementException {
		String id=null;
		if(event!=null && isValidEvent(event)){
			id=eventDao.add(event);
			System.out.println(id);
		}
		return id;
	}
	
	@Override
	public boolean delete(String id) throws EventManagementException {
		boolean isDone=false;
		if(id!=null && isValidId(id)){
			eventDao.delete(id);
			isDone = true;
		} else{
			throw new EventManagementException("id should be a capital letter followed by 3 digits");
		}
		return isDone;
	}
	@Override
	public List<Event> getAllEventsInLocation(String location) throws EventManagementException {
		return eventDao.getAllEventsInLocation(location);
	}
	@Override
	public List<Event> getAllEventsOnMentionedDate(LocalDate dateMentioned) throws EventManagementException {
		return eventDao.getAllEventsOnMentionedDate(dateMentioned);
	}
	public List<Event> getAllEventsInOrderOfLocation() throws EventManagementException{
		return eventDao.getAllEventsInOrderOfLocation();
	}
	public List<Event> getAllEventsInOrderOfDateScheduled() throws EventManagementException{
		return eventDao.getAllEventsInOrderOfDateScheduled();
	}
	



	@Override
	public void persist() throws EventManagementException {
		
	}
}
