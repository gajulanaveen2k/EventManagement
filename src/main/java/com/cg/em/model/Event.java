package com.cg.em.model;
import java.io.Serializable;
import java.time.LocalDate;



/*
 * Represents a Event.
 */
public class Event implements Serializable, Comparable<Event> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private LocalDate dateScheduled;
	private String location;
	private double cost;

	public Event() {
		/* default constructor */
	}

	public Event(String id, String title, LocalDate dateScheduled,String location, double cost) {
		super();
		this.id = id;
		this.title = title;
		this.location=location;
		this.dateScheduled = dateScheduled;
		this.cost = cost;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getDateScheduled() {
		return dateScheduled;
	}

	public void setDateScheduled(LocalDate dateScheduled) {
		this.dateScheduled = dateScheduled;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder("Event Id : ");
		output.append(id);
		output.append("\tTitle : ");
		output.append(title);
		output.append("\tScheduled Date :");
		output.append(dateScheduled);
		output.append("\tLocation : ");
		output.append(location);
		output.append("\tCost : ");
		output.append(cost);
		return output.toString();
	}

	@Override
	public int compareTo(Event event) {
		String firstId = this.id;
		String secondId = event.id;
		return firstId.compareTo(secondId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((dateScheduled == null) ? 0 : dateScheduled.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (dateScheduled == null) {
			if (other.dateScheduled != null)
				return false;
		} else if (!dateScheduled.equals(other.dateScheduled))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
