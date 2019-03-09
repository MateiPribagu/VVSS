package agenda.model.repository.interfaces;

import java.util.List;

import agenda.model.base.Contact;

public interface RepositoryContact {

	List<Contact> getContacts();
	void addContact(Contact contact);
	boolean removeContact(Contact contact);
	boolean saveContacts();
	int count();
	Contact getByName(String string);
}
