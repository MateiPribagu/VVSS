package agenda.startApp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServiceTest {
    Service service = null;
    @Before
    public void setUp() throws Exception {
        service = new Service();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addContact() {
        int count;
        //ECP Valid 1,3,5,7
        count = service.contactRep.count();
        service.AddContact("Name","adress","+07","email");
        assertEquals(service.contactRep.count(),count+1);
        //ECP Non-Valid 1,4,5,7
        count = service.contactRep.count();
        service.AddContact("","adress","+07","email");
        assertEquals(service.contactRep.count(),count);
        //ECP Non-Valid 1,3,5,9
        count = service.contactRep.count();
        service.AddContact("","adress","7","email");
        assertEquals(service.contactRep.count(),count);
        //BVA Valid name.length() = 1
        count = service.contactRep.count();
        service.AddContact("N","adress","+07","email");
        assertEquals(service.contactRep.count(),count+1);
        //BVA Non-Valid telephone[0] is not '+' or '0'
        count = service.contactRep.count();
        service.AddContact("Name","adress","7","email");
        assertEquals(service.contactRep.count(),count);
        //BVA Valid telephone[0] is '0'
        count = service.contactRep.count();
        service.AddContact("Name","adress","07","email");
        assertEquals(service.contactRep.count(),count+1);
        //BVA Non-Valid
        count = service.contactRep.count();
        service.AddContact("Name","adress","7","email");
        assertEquals(service.contactRep.count(),count);
    }
}