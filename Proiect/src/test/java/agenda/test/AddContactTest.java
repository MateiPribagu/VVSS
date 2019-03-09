package agenda.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import agenda.exceptions.InvalidFormatException;

import agenda.model.base.Contact;
import agenda.model.repository.classes.RepositoryContactMock;
import agenda.model.repository.interfaces.RepositoryContact;


public class AddContactTest {

	private Contact con;
	private RepositoryContact rep;
	
	@Before
	public void setUp() throws Exception {
		rep = new RepositoryContactMock();
	}
	
	@Test
	public void testCase1()
	{
		try {
			con = new Contact("name", "address1", "+4071122334455","email@domain.com");
		} catch (InvalidFormatException e) {
			assertTrue(false);
		}
		//int n = rep.count();
		rep.addContact(con);
		for(Contact c : rep.getContacts())
			if (c.equals(con))
			{
				assertTrue(true);
				break;
			}
		//assertTrue(n+1 == rep.count());
	}
	
	@Test
	public void testCase2()
	{
		try{
			rep.addContact((Contact) new Object());
		}
		catch(Exception e)
		{
			assertTrue(true);
		}	
	}
	
	@Test
	public void testCase3()
	{
		for(Contact c : rep.getContacts())
			rep.removeContact(c);
		
		try {
			con = new Contact("name", "address1", "+071122334455","email@domain.com");
			rep.addContact(con);
		} catch (InvalidFormatException e) {
			assertTrue(false);
		}
		int n  = rep.count();
		if (n == 1) 
			if (con.equals(rep.getContacts().get(0))) assertTrue(true);
			else assertTrue(false);
		else assertTrue(false);
	}
	
}
