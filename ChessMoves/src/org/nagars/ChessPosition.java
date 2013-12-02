package org.nagars;

import java.util.ArrayList;

public final class ChessPosition {
	// row & col are in Java array position
	int row;
	int col;
	ArrayList<ChessPiece> attack_pieces; 
	
	private void setPosition(char cb_col, char cb_row) throws ChessException
	{
		char tc = Character.toUpperCase(cb_col);
		
		if ((tc < 'A') || (tc > 'H'))
			throw new ChessException("Invalid board column position given: " + cb_col);
		
		col = tc - 'A';
		if (Character.isDigit(cb_row))
		{
			row = Character.getNumericValue(cb_row);
			if ((row < 1) || (row > 8))
				throw new ChessException("Invalid board row position given: " + cb_row);
			row = 8 - row;
		}
		else
		{
			throw new ChessException("Board row position not digit: " + cb_row);
		}						
	}
	
	public ChessPosition(char cb_col, char cb_row) throws ChessException
	{
		attack_pieces = new ArrayList<ChessPiece>();
		setPosition(cb_col, cb_row);
	}

	public ChessPosition(int r, int c) throws ChessException
	{
		if ((c < 0) || (c>7) || (r < 0) || (r > 7))
			throw new ChessException("Invalid board position specified");
		
		attack_pieces = new ArrayList<ChessPiece>();
		row = r;
		col = c;
	}
	
	public ChessPosition(String pos) throws ChessException
	{
		if (pos == null)
			throw new ChessException("No chess position given");
		
		if (pos.length() != 2)
			throw new ChessException("Chess position is not valid");

		attack_pieces = new ArrayList<ChessPiece>();
		setPosition(pos.charAt(0), pos.charAt(1));
	}
	
	public char getBoardRow()
	{
		return (char)('8' - row);
	}
	
	public char getBoardCol()
	{
		return (char)('A' + col);
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public String getDescription()
	{
		return String.format("%c%c", getBoardCol(), getBoardRow());
	}
	
	public void addAttack(ChessPiece p)
	{
		if (p != null)
			attack_pieces.add(p);
	}
	
	public void removeAttack(ChessPiece p)
	{
		if (p == null)
			return;
		
		attack_pieces.remove(p);
	}
	
	public ChessPiece[] underAttack()
	{
		return (ChessPiece [])attack_pieces.toArray();
	}
}
