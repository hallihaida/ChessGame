package org.nagars;

public class ChessException extends Exception {

	public static final String DEF_ERROR = "Error has occured. No description available";
	static final long serialVersionUID = 1;
	
	public ChessException() 
	{
		super(DEF_ERROR);
	}

	public ChessException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ChessException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ChessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
