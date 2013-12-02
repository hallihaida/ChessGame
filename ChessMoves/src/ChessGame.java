import java.util.Scanner;
import java.util.regex.MatchResult;

import org.nagars.*;

public class ChessGame {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		try
		{
			GUIChessBoard b = new GUIChessBoard();
//			TextChessBoard b = new TextChessBoard();
//			b.startGame();
		}
		catch(ChessException e)
		{
			System.out.println(e.getMessage());
		}
	}

}
