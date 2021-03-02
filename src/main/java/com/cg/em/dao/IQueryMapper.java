package com.cg.em.dao;

public interface IQueryMapper {

	public static final String ADD_EVENT_QRY = 
			"INSERT INTO event(id,  title, datescheduled, location, cost) VALUES(?,?,?,?,?)";
	public static final String GETEVENTSINLOCATION_EVENT_QRY = 
			"SELECT * FROM event WHERE location=?";
	public static final String GETEVENTSONDATEMENTIONED_EVENT_QRY = 
			"SELECT * FROM event WHERE datescheduled=?";
	public static final String GETEVENTSORDEREDBYLOCATION_EVENT_QRY = 
			"SELECT * FROM event ORDER BY location";
	public static final String GETEVENTSORDEREDBYDATESCHEDULED_EVENT_QRY = 
			"SELECT * FROM event ORDER BY datescheduled";
	public static final String DEL_EVENT_QRY = 
			"DELETE FROM event WHERE id=?";

}
