package com.cg.em.dao;

import java.awt.print.Book;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cg.em.exception.EventManagementException;
import com.cg.em.model.Event;
import com.cg.em.util.ConnectionProvider;

public class EventManagementDAOJDBCImpl implements IEventManagementDAO{
	
	ConnectionProvider conProvider;
	
	public EventManagementDAOJDBCImpl() throws EventManagementException {

		try {
			conProvider = ConnectionProvider.getInstance();
		} catch (ClassNotFoundException | IOException exp) {
			throw new EventManagementException("Database is not reachable");
		}
	}

	@Override
	public String add(Event event) throws EventManagementException {
		String id = null;
		if (event != null) {
			try (Connection con = conProvider.getConnection();
					PreparedStatement pInsert = con.prepareStatement(IQueryMapper.ADD_EVENT_QRY)) {

				pInsert.setString(1, event.getId());
				pInsert.setString(2, event.getTitle());
				pInsert.setDate(3, Date.valueOf(event.getDateScheduled()));
				pInsert.setString(4, event.getLocation());
				pInsert.setDouble(5, event.getCost());

				int rowCount = pInsert.executeUpdate();

				if (rowCount == 1) {
					id = event.getId();
				}
			} catch (SQLException exp) {
				
				throw new EventManagementException("Event Not Added "+ exp );
			}
		}
		return id;
	}

	@Override
	public boolean delete(String id) throws EventManagementException {
		boolean isDone = false;

		try (Connection con = conProvider.getConnection();
				PreparedStatement pDelete = con.prepareStatement(IQueryMapper.DEL_EVENT_QRY)) {

			pDelete.setString(1, id);

			int rowCount = pDelete.executeUpdate();

			if (rowCount == 1) {
				isDone = true;
			}
		} catch (SQLException exp) {
			throw new EventManagementException("Event is not deleted");
		}

		return isDone;

	}

	@Override
	public List<Event> getAllEventsInLocation(String location) throws EventManagementException {
		
		List<Event> events=null;
		try (Connection con = conProvider.getConnection();
				PreparedStatement pSelect = con.prepareStatement(IQueryMapper.GETEVENTSINLOCATION_EVENT_QRY)) {

			pSelect.setString(1, location);
			ResultSet rs = pSelect.executeQuery();
			
			events = new ArrayList<Event>();
			
			while(rs.next()){
				Event event= new Event();
				event.setId(rs.getString("id"));
				event.setTitle(rs.getString("title"));
				event.setCost(rs.getDouble("cost"));
				event.setDateScheduled(rs.getDate("datescheduled").toLocalDate());
				event.setLocation(rs.getString("location"));
				events.add(event);
			}
			
		} catch (SQLException exp) {
			throw new EventManagementException("No Events are available.");
		}		
		return events;	
	}

	@Override
	public List<Event> getAllEventsOnMentionedDate(LocalDate dateMentioned) throws EventManagementException {
		List<Event> events=null;
		try (Connection con = conProvider.getConnection();
				PreparedStatement pSelect = con.prepareStatement(IQueryMapper.GETEVENTSONDATEMENTIONED_EVENT_QRY)) {

			pSelect.setDate(1,Date.valueOf(dateMentioned));
			ResultSet rs = pSelect.executeQuery();
			
			events = new ArrayList<Event>();
			
			while(rs.next()){
				Event event= new Event();
				event.setId(rs.getString("id"));
				event.setTitle(rs.getString("title"));
				event.setCost(rs.getDouble("cost"));
				event.setDateScheduled(rs.getDate("datescheduled").toLocalDate());
				event.setLocation(rs.getString("location"));
				events.add(event);
			}
			
		} catch (SQLException exp) {
			throw new EventManagementException("No Events are available.");
		}		
		return events;	
	}

	@Override
	public List<Event> getAllEventsInOrderOfLocation() throws EventManagementException {
		List<Event> events=null;
		try (Connection con = conProvider.getConnection();
				PreparedStatement pSelect = con.prepareStatement(IQueryMapper.GETEVENTSORDEREDBYLOCATION_EVENT_QRY)) {

			ResultSet rs = pSelect.executeQuery();
			
			events = new ArrayList<Event>();
			
			while(rs.next()){
				Event event= new Event();
				event.setId(rs.getString("id"));
				event.setTitle(rs.getString("title"));
				event.setCost(rs.getDouble("cost"));
				event.setDateScheduled(rs.getDate("datescheduled").toLocalDate());
				event.setLocation(rs.getString("location"));
				events.add(event);
				
			}
			
		} catch (SQLException exp) {
			throw new EventManagementException("No Events are available.");
		}		
		return events;
	}

	@Override
	public List<Event> getAllEventsInOrderOfDateScheduled() throws EventManagementException {
		List<Event> events=null;
		try (Connection con = conProvider.getConnection();
				PreparedStatement pSelect = con.prepareStatement(IQueryMapper.GETEVENTSORDEREDBYDATESCHEDULED_EVENT_QRY)) {

			ResultSet rs = pSelect.executeQuery();
			
			events = new ArrayList<Event>();
			
			while(rs.next()){
				Event event= new Event();
				event.setId(rs.getString("id"));
				event.setTitle(rs.getString("title"));
				event.setCost(rs.getDouble("cost"));
				event.setDateScheduled(rs.getDate("datescheduled").toLocalDate());
				event.setLocation(rs.getString("location"));
				events.add(event);
			}
			
		} catch (SQLException exp) {
			throw new EventManagementException("No Events are available.");
		}		
		return events;
	}


	@Override
	public void persist() throws EventManagementException {
		// TODO Auto-generated method stub
		
	}

}
