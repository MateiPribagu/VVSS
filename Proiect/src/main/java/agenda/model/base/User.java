package agenda.model.base;

public class User {
	private String username;
	private String password;
	private String name;
	
	public User(String name, String username, String password)
	{
		this.username = username;
		this.password = password;
		this.name = name;
	}
	
	public String getName(){ return name;}
	public String getUsername(){ return username; }
	
	public boolean isPassword(String password)
	{
		if (this.password.equals(password)) return true;
		else return false;
	}
	
	public boolean setPassword(String oldPasswd, String newPasswd)
	{
		if (oldPasswd.equals(password))
		{
			password = newPasswd;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append("#");
		sb.append(username);
		sb.append("#");
		sb.append(password);
		return sb.toString();
	}
	
	public static User fromString(String s)
	{
		String[] str = s.split("#");
		try
		{
			return new User(str[0], str[1], str[2]);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof User)) return false;
		User u = (User)obj;
		if (u.name.equals(name) && u.username.equals(username) && u.password.equals(password)) return true;
		return false;
	}
}
