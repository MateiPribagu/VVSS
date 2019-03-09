package agenda.test;

import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import agenda.model.base.Activity;
import agenda.model.base.Contact;
import agenda.model.repository.classes.RepositoryActivityFile;
import agenda.model.repository.classes.RepositoryContactFile;
import agenda.model.repository.interfaces.RepositoryActivity;
import agenda.model.repository.interfaces.RepositoryContact;

import org.junit.Before;
import org.junit.Test;

import agenda.exceptions.InvalidFormatException;

public class IntegrationTest {

	private RepositoryActivity repAct;
	private RepositoryContact repCon;

	@Before
	public void setup() throws Exception {
		repCon = new RepositoryContactFile();
		repAct = new RepositoryActivityFile(repCon);

		for (Activity a : repAct.getActivities())
			repAct.removeActivity(a);
	}

	@Test
	public void test1() {
		int n = repCon.count();

		try {
			Contact c = new Contact("name", "address1", "+071122334455","email@domain.com");
			repCon.addContact(c);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}

		assertTrue(n + 1 == repCon.count());
	}

	@Test
	public void test2() {
		Activity act = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try {
			act = new Activity("name1", df.parse("03/20/2013 12:00"),
					df.parse("03/20/2013 13:00"), null, "Lunch break","location");
			repAct.addActivity(act);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assertTrue(repAct.getActivities().get(0).equals(act)
				&& repAct.count() == 1);
	}

	@Test
	public void test3() {
		Calendar c = Calendar.getInstance();
		c.set(2013, 3 - 1, 20, 12, 00);
		Date start = c.getTime();

		c.set(2013, 3 - 1, 20, 12, 30);
		Date end = c.getTime();

		Activity act = new Activity("name1", start, end,
				new LinkedList<Contact>(), "description2","location");

		repAct.addActivity(act);

		c.set(2013, 3 - 1, 20);

		List<Activity> result = repAct.activitiesOnDate("name1", c.getTime());
		assertTrue(result.size() == 1);
	}

	@Test
	public void test4() {
		Activity act = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try {
			act = new Activity("name1", df.parse("03/20/2013 12:00"),
					df.parse("03/20/2013 13:00"), null, "Lunch break","location");
			repAct.addActivity(act);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assertTrue(repAct.getActivities().get(0).equals(act)
				&& repAct.count() == 1);

		Calendar c = Calendar.getInstance();
		c.set(2013, 3 - 1, 20);

		List<Activity> result = repAct.activitiesOnDate("name1", c.getTime());
		assertTrue(result.size() == 1 && result.get(0).equals(act));
	}

	@Test
	public void test5() {
		boolean part1 = false;
		Activity act = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try {
			act = new Activity("name1", df.parse("03/20/2013 12:00"),
					df.parse("03/20/2013 13:00"), null, "Lunch break","location");
			repAct.addActivity(act);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			repAct.addActivity((Activity) (Object) 5);
		} catch (Exception e) {
			part1 = true;
		}
		Calendar c = Calendar.getInstance();
		c.set(2013, 3 - 1, 20);

		List<Activity> result = repAct.activitiesOnDate("name1", c.getTime());
		assertTrue(result.size() == 1 && result.get(0).equals(act) && part1);
	}

	@Test
	public void test6() {
		boolean part1 = false;
		Activity act = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try {
			act = new Activity("name1", df.parse("03/20/2013 12:00"),
					df.parse("03/20/2013 13:00"), null, "Lunch break","location");
			repAct.addActivity(act);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (repAct.getActivities().get(0).equals(act) && repAct.count() == 1)
			part1 = true;

		try {
			repAct.activitiesOnDate("name1", (Date) (Object) "ASD");
		} catch (Exception e) {
			assertTrue(part1);
		}
	}

	@Test
	public void test7() {
		boolean part1 = false, part2 = false;
		int n = repCon.count();

		try {
			Contact c = new Contact("name", "address1", "+071122334455","email@domain.com");
			repCon.addContact(c);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}

		if (n + 1 == repCon.count())
			part1 = true;
		Activity act = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try {
			act = new Activity("name1", df.parse("03/20/2013 12:00"),
					df.parse("03/20/2013 13:00"), null, "Lunch break","location");
			repAct.addActivity(act);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (repAct.getActivities().get(0).equals(act) && repAct.count() == 1)
			part2 = true;

		Calendar c = Calendar.getInstance();
		c.set(2013, 3 - 1, 20);

		List<Activity> result = repAct.activitiesOnDate("name1", c.getTime());
		assertTrue(result.size() == 1 && result.get(0).equals(act) && part1
				&& part2);
	}

	@Test
	public void test8() {
		boolean part1 = false, part2 = false;

		try {
			repCon.addContact((Contact) new Object());
		} catch (Exception e) {
			part1 = true;
		}

		Activity act = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try {
			act = new Activity("name1", df.parse("03/20/2013 12:00"),
					df.parse("03/20/2013 13:00"), null, "Lunch break","location");
			repAct.addActivity(act);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (repAct.getActivities().get(0).equals(act) && repAct.count() == 1)
			part2 = true;

		Calendar c = Calendar.getInstance();
		c.set(2013, 3 - 1, 20);

		List<Activity> result = repAct.activitiesOnDate("name1", c.getTime());
		assertTrue(result.size() == 1 && result.get(0).equals(act) && part1
				&& part2);
	}

	@Test
	public void test9() {
		boolean part1 = false, part2 = false;
		int n = repCon.count();

		try {
			Contact c = new Contact("name", "address1", "+071122334455","email@domain.com");
			repCon.addContact(c);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}

		if (n + 1 == repCon.count())
			part1 = true;
		Activity act = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try {
			act = new Activity("name1", df.parse("03/20/2013 12:00"),
					df.parse("03/20/2013 13:00"), null, "Lunch break","location");
			repAct.addActivity(act);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			repAct.addActivity((Activity) (Object) 5);
		} catch (Exception e) {
			part2 = true;
		}

		Calendar c = Calendar.getInstance();
		c.set(2013, 3 - 1, 20);

		List<Activity> result = repAct.activitiesOnDate("name1", c.getTime());
		assertTrue(result.size() == 1 && result.get(0).equals(act) && part1
				&& part2);
	}

	@Test
	public void test10() {
		boolean part1 = false, part2 = false, part3 = false;
		int n = repCon.count();

		try {
			Contact c = new Contact("name", "address1", "+071122334455","email@domain.com");
			repCon.addContact(c);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}

		if (n + 1 == repCon.count())
			part1 = true;
		Activity act = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try {
			act = new Activity("name1", df.parse("03/20/2013 12:00"),
					df.parse("03/20/2013 13:00"), null, "Lunch break","location");
			repAct.addActivity(act);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (repAct.getActivities().get(0).equals(act) && repAct.count() == 1)
			part2 = true;

		try {
			repAct.activitiesOnDate("name1", (Date) (Object) "ASD");
		} catch (Exception e) {
			part3 = true;
		}
		assertTrue(part1 && part2 && part3);
	}
}
