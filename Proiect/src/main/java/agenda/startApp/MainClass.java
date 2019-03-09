package agenda.startApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import agenda.exceptions.InvalidFormatException;

import agenda.model.base.Activity;
import agenda.model.base.Contact;
import agenda.model.base.User;
import agenda.model.repository.classes.RepositoryActivityFile;
import agenda.model.repository.classes.RepositoryContactFile;
import agenda.model.repository.classes.RepositoryUserFile;
import agenda.model.repository.interfaces.RepositoryActivity;
import agenda.model.repository.interfaces.RepositoryContact;
import agenda.model.repository.interfaces.RepositoryUser;

//functionalitati
//F01.	 adaugarea de contacte (nume, adresa, numar de telefon, adresa email);
//F02.	 programarea unor activitati (denumire, descriere, data, locul, ora inceput, durata, contacte).
//F03.	 generarea unui raport cu activitatile pe care le are utilizatorul (nume, user, parola) la o anumita data, ordonate dupa ora de inceput.

public class MainClass {

	public static void main(String[] args) {
		BufferedReader in = null;
		try {
			RepositoryContact contactRep = new RepositoryContactFile();
			RepositoryUser userRep = new RepositoryUserFile();
			RepositoryActivity activityRep = new RepositoryActivityFile(
					contactRep);

			User user = null;
			in = new BufferedReader(new InputStreamReader(System.in));
			while (user == null) {
                System.out.print("Enter username: ");
				String u = in.readLine();
                System.out.print("Enter password: ");
				String p = in.readLine();

				user = userRep.getByUsername(u);
				if (!user.isPassword(p))
					user = null;
			}

			int chosen = 0;
			while (chosen != 4) {
				printMenu();
				chosen = Integer.parseInt(in.readLine());
				try {
					switch (chosen) {
					case 1:
						AddContact(contactRep, in);
						break;
					case 2:
						AddActivity(activityRep, contactRep, in, user);
						break;
					case 3:
						ShowActivities(activityRep, in, user);
						break;
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			// List<Activity> act =
			// activityRep.activitiesByName(user.getName());
			// for(Activity a : act)
			// System.out.println(a.toString());

		} catch (Exception e) {
            System.out.println(e.getMessage());
		}
		System.out.println("Program over and out\n");
	}

	private static void ShowActivities(RepositoryActivity activityRep,
			BufferedReader in, User user) {
		try {
            System.out.print("Show Activities: \n");
            System.out.print("Date(format: mm/dd/yyyy): ");
			String dateS = in.readLine();
			Calendar c = Calendar.getInstance();
			c.set(Integer.parseInt(dateS.split("/")[2]),
					Integer.parseInt(dateS.split("/")[0]) - 1,
					Integer.parseInt(dateS.split("/")[1]));
			Date d = c.getTime();

			System.out.println("Activities from " + d.toString() + ": ");

			List<Activity> act = activityRep
					.activitiesOnDate(user.getName(), d);
			for (Activity a : act) {
				System.out.printf("%s - %s: %s - %s with: ",
                        a.getName(),
                        a.getDescription(),
                        a.getStart().toString(),
                        a.getDuration().toString());
				for (Contact con : a.getContacts())
					System.out.printf("%s, ", con.getName());
				System.out.println();
			}
		} catch (IOException e) {
            System.out.print("Reading error: %s\n" + e.getMessage());
		}
	}

	private static void AddActivity(RepositoryActivity activityRep,
			RepositoryContact contactRep, BufferedReader in, User user) {
		try {
            System.out.print("Add Activity: \n");
            System.out.print("Description: ");
            String description = in.readLine();
            System.out.print("Location: ");
            String location = in.readLine();
            System.out.print("Start Date(format: mm/dd/yyyy): ");
			String dateS = in.readLine();
            System.out.print("Start Time(hh:mm): ");
			String timeS = in.readLine();
			Calendar c = Calendar.getInstance();
			c.set(Integer.parseInt(dateS.split("/")[2]),
					Integer.parseInt(dateS.split("/")[0]) - 1,
					Integer.parseInt(dateS.split("/")[1]),
					Integer.parseInt(timeS.split(":")[0]),
					Integer.parseInt(timeS.split(":")[1]));
			Date start = c.getTime();

            System.out.print("End Date(format: mm/dd/yyyy): ");
			String dateE = in.readLine();
            System.out.print("End Time(hh:mm): ");
			String timeE = in.readLine();
			
			c.set(Integer.parseInt(dateE.split("/")[2]),
					Integer.parseInt(dateE.split("/")[0]) - 1,
					Integer.parseInt(dateE.split("/")[1]),
					Integer.parseInt(timeE.split(":")[0]),
					Integer.parseInt(timeE.split(":")[1]));
			Date end = c.getTime();

			Activity act = new Activity(user.getName(), start, end,
					new LinkedList<Contact>(), description,location);

			if(activityRep.addActivity(act)){
			    if(activityRep.saveActivities()) System.out.print("Success!\n");
			    else System.out.println("Save problem!");
            }else System.out.println("Add problem!");
		} catch (IOException e) {
            System.out.print("Reading error:" + e.getMessage()+"\n");
		}

	}

	private static void AddContact(RepositoryContact contactRep,
			BufferedReader in) {

		try {
			System.out.print("Add Contact: \n");
			System.out.print("Name: ");
			String name = in.readLine();
			System.out.print("Address: ");
			String adress = in.readLine();
            System.out.print("Telephone: ");
            String telefon = in.readLine();
            System.out.print("Email: ");
            String email = in.readLine();
			
			Contact c = new Contact(name, adress, telefon,email);

			contactRep.addContact(c);
			contactRep.saveContacts();
			System.out.print("Success!\n");
		} catch (IOException e) {

			System.out.printf("Reading error %s\n",e.getMessage());
		} catch (InvalidFormatException e) {
			if (e.getCause() != null)
				System.out.printf("Error: %s - %s\n" , e.getMessage(), e
						.getCause().getMessage());
			else
				System.out.printf("Error: %s\n" , e.getMessage());
		}

	}

	private static void printMenu() {
		System.out.print("Please choose option:\n");
		System.out.print("1. Add contact\n");
		System.out.print("2. Add activity\n");
		System.out.print("3. Show activities from...\n");
		System.out.print("4. Exit\n");
		System.out.print("Choose: ");
	}
}
