package agenda.model.repository.classes;

import java.util.LinkedList;
import java.util.List;

import agenda.model.base.Contact;
import agenda.model.repository.interfaces.RepositoryContact;
import agenda.exceptions.InvalidFormatException;

public class RepositoryContactMock implements RepositoryContact {

private List<Contact> contacts;
	
	public RepositoryContactMock() {
		contacts = new LinkedList<Contact>();
		try {
			Contact c = new Contact("Name1", "address1", "+4071122334455","email@domain.com");
			contacts.add(c);
			c = new Contact("Name2", "address 2", "+4071122334466","email@2domain.com");
			contacts.add(c);
			c = new Contact("Name3", "address 3", "+4071122338866","email@3domain.com");
			contacts.add(c);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Contact> getContacts() {
		return new LinkedList<Contact>(contacts);
	}

	@Override
	public void addContact(Contact contact) {
		contacts.add(contact);
	}

	@Override
	public boolean removeContact(Contact contact) {
		int index = contacts.indexOf(contact);
		if (index < 0) return false;
		else contacts.remove(index);
		return true;
	}

	@Override
	public boolean saveContacts() {
		return true;
	}

	@Override
	public int count() {
		return contacts.size();
	}

	@Override
	public Contact getByName(String string) {
		for(Contact c : contacts)
			if (c.getName().equals(string)) return c;
		return null;
	}
	
}
