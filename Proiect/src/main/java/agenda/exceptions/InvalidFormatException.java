package agenda.exceptions;

public class InvalidFormatException extends Exception{
	private static final long serialVersionUID = -6262759468431626763L;

	public InvalidFormatException(String msg)
	{
		super(msg);
	}
	
	public InvalidFormatException(String msg, String reason)
	{
		super(msg, new Throwable(reason));
	}	
	
}
