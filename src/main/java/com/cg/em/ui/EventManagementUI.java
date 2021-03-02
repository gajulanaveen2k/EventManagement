package com.cg.em.ui;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.cg.em.exception.EventManagementException;
import com.cg.em.model.Event;
import com.cg.em.model.EventManagementAppMenu;
import com.cg.em.service.EventServiceImpl;
import com.cg.em.service.IEventService;

public class EventManagementUI {

	private static IEventService eventService;
	private static Scanner scan;
	private static DateTimeFormatter dtFormater;

	public static void main(String[] args) throws EventManagementException {

		try {
			eventService = new EventServiceImpl();
		} catch (EventManagementException e) {
			e.printStackTrace();
		}


		scan = new Scanner(System.in);
		dtFormater = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		EventManagementAppMenu menu = null;
		
		while (menu != EventManagementAppMenu.QUIT) {

			System.out.println("Choice\tMenu Item");
			System.out.println("===========================");
			for (EventManagementAppMenu m : EventManagementAppMenu.values()) {
				System.out.println(m.ordinal() + "\t" + m.name());
			}
			System.out.print("Choice: ");
			int choice = -1;
			if (scan.hasNextInt())
				choice = scan.nextInt();
			else {
				scan.next();
				System.out.println("Pleae choose a choice displayed");
				continue;
			}

			if (choice < 0 || choice >= EventManagementAppMenu.values().length) {
				System.out.println("Invalid Choice");
			} else {
				menu = EventManagementAppMenu.values()[choice];
				switch (menu) {
				case ADD:
					doAdd();
					break;
				case REMOVE:
					doRemove();
					break;
				case ORDEREDINDATESCHEDULED:
					doListOrderedInDateScheduled();
					break;
				case ORDEREDINLOCATION:
					doListOrderedInLocation();
					break;
				case SEARCHLOCATION:
					doSearchLocation();
					break;
				case SEARCHDATESCHEDULED:
					doSearchDateScheduled();
					break;
				case QUIT:
					System.out.println("ThanQ Come Again!");
					break;
				}
			}

		}

		scan.close();
		try {
			eventService.persist();
		} catch (EventManagementException e) {
			e.printStackTrace();
		}

	}
	public static void doListOrderedInDateScheduled() {
		List<Event> events;
		try {
			events = eventService.getAllEventsInOrderOfDateScheduled();
			if (events.isEmpty()) {
				System.out.println("No Event To display");
			} else {
				for (Event b : events)
					System.out.println(b);
			}
		} catch (EventManagementException exp) {
			System.out.println(exp.getMessage());
		}
	}
	public static void doListOrderedInLocation() {
		List<Event> events1;
		try {
			events1 = eventService.getAllEventsInOrderOfLocation() ;
			if (events1.isEmpty()) {
				System.out.println("No Event To display");
			} else {
				for (Event b : events1)
					System.out.println(b);
			}
		} catch (EventManagementException exp) {
			System.out.println(exp.getMessage());
		}
	}
	private static void doAdd() {
		try {
			Event event = new Event();
			System.out.print("Id: ");
			event.setId(scan.next());
			System.out.print("Title: ");
			event.setTitle(scan.next());
			System.out.print("Location: ");
			event.setLocation(scan.next());
			System.out.print("Date Scheduled(dd-MM-yyyy): ");
			String dtScheduledStr = scan.next();

		try {
				event.setDateScheduled(LocalDate.parse(dtScheduledStr, dtFormater));
			} catch (DateTimeException exp) {
				throw new EventManagementException(
						"Date must be in the format day(dd)-month(MM)-year(yyyy)");
			}
			System.out.print("Cost: ");
			if (scan.hasNextDouble())
				event.setCost(scan.nextDouble());
			else {
				scan.next();
				throw new EventManagementException("Cost is a number");
			}

			String id = eventService.add(event);
			System.out.println("Event is Added with code: " + id);
		} catch (EventManagementException exp) {
			System.out.println(exp.getMessage());
		}
	}

	private static void doSearchLocation() {
		System.out.print("Location: ");
		String location = scan.next();

		try {
			List<Event> eventList = eventService.getAllEventsInLocation(location);
			for (Event eventObj: eventList) {
				System.out.println(eventObj);
			}
		} catch (EventManagementException exp) {
			System.out.println(exp.getMessage());
		}
	}
	private static void doSearchDateScheduled() throws EventManagementException {
		System.out.print("Date to display events: ");
		String dateScheduledStr = scan.next();
		
		try {
			List<Event> eventList=eventService.getAllEventsOnMentionedDate(LocalDate.parse(dateScheduledStr, dtFormater));
			for (Event eventObj: eventList) {
				System.out.println(eventObj);
			}
			System.out.println("end of the list");
		} catch (DateTimeException exp) {
			throw new EventManagementException(
					"Date must be in the format day(dd)-month(MM)-year(yyyy)");
		} catch (EventManagementException exp) {
			System.out.println(exp.getMessage());
		}

	}
	private static void doRemove() {
		System.out.print("Id: ");
		String id = scan.next();
		try {
			boolean isDone = eventService.delete(id);
			if (isDone) {
				System.out.println("Event is Deleted");
			} else {
				System.out.println("No Such Event");
			}
		} catch (EventManagementException exp) {
			System.out.println(exp.getMessage());
		}
	}
}