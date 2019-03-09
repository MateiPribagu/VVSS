package agenda.model.base;

import agenda.exceptions.InvalidFormatException;

public class Contact {
	private String Name;
	private String Address;
	private String Telefon;
	private String Email;
	
	public Contact(){
		Name = "";
		Address = "";
		Telefon = "";
		Email = "";
	}
	
	public Contact(String name, String address, String telefon, String email) throws InvalidFormatException{
		if (!validTelefon(telefon)) throw new InvalidFormatException("Cannot convert", "Invalid phone number");
		if (!validName(name)) throw new InvalidFormatException("Cannot convert", "Invalid name");
		if (!validAddress(address)) throw new InvalidFormatException("Cannot convert", "Invalid address");
		Name = name;
		Address = address;
		Telefon = telefon;
		Email = email;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) throws InvalidFormatException {
		if (!validName(name)) throw new InvalidFormatException("Cannot convert", "Invalid name");
		Name = name;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) throws InvalidFormatException {
		if (!validAddress(address)) throw new InvalidFormatException("Cannot convert", "Invalid address");
		Address = address;
	}

	public String getTelefon() {
		return Telefon;
	}

	public void setTelefon(String telefon) throws InvalidFormatException {
		if (!validTelefon(telefon)) throw new InvalidFormatException("Cannot convert", "Invalid phone number");
		Telefon = telefon;
	}

	public static Contact fromString(String str, String delim) throws InvalidFormatException
	{
		String[] s = str.split(delim);
		if (s.length!=4) throw new InvalidFormatException("Cannot convert", "Invalid data");
		if (!validTelefon(s[2])) throw new InvalidFormatException("Cannot convert", "Invalid phone number");
		if (!validName(s[0])) throw new InvalidFormatException("Cannot convert", "Invalid name");
		if (!validAddress(s[1])) throw new InvalidFormatException("Cannot convert", "Invalid address");
		
		return new Contact(s[0], s[1], s[2], s[3]);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Name);
		sb.append("#");
		sb.append(Address);
		sb.append("#");
		sb.append(Telefon);
		sb.append("#");
		sb.append(Email);
		return sb.toString();
	}
	
	private static boolean validName(String str)
	{
		
		String[] s = str.split("[\\p{Punct}\\s]+");
		if (s.length>2) return false;
		return true;
	}
	
	private static boolean validAddress(String str)
	{
		return true;
	}
	
	private static boolean validTelefon(String tel)
	{
		String[] s = tel.split("[\\p{Punct}\\s]+");
		if (tel.charAt(0) == '+' && s.length == 2 ) return true;
		if (tel.charAt(0) != '0')return false;
		if (s.length != 1) return false;
		return true;
	}
	
		
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Contact)) return false;
		Contact o = (Contact)obj;
		if (Name.equals(o.Name) && Address.equals(o.Address) &&
				Telefon.equals(o.Telefon))
			return true;
		return false;
	}
	
}
