package org.nagars;

public class ChessMoves 
{
	public enum MoveType
	{
		CAPTURE,
		CASTLE,
		PROMOTE,
		NORMAL,
		INVALID
	}
	
	class Move
	{
		ChessPosition pos;
		MoveType type;
		Move next;
	}

	private int count;
	private Move moves;
	
	public ChessMoves()
	{
		count = 0;
		moves = null;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public ChessPosition getMove(int num) throws ChessException
	{
		if (num > count)
			throw new ChessException("Moves out of bounds");
		
		if (count == 0)
			throw new ChessException("No moves");
		
		Move m = moves;
		for (int i = 0; i < num; i++)
			m = m.next;
		
		return m.pos;
	}
	
	public MoveType moveType(int num) throws ChessException
	{
		if (num > count)
			throw new ChessException("Moves out of bounds");
		
		if (count == 0)
			throw new ChessException("No moves");
		
		Move m = moves;
		for (int i = 0; i < num; i++)
			m = m.next;
		
		return m.type;
	}
	
	public void addMove(ChessPosition p) throws ChessException
	{
		addMove(p, MoveType.NORMAL);
	}
	
	public void addMove(ChessPosition p, MoveType t) throws ChessException
	{
		if (p == null)
			throw new ChessException("No move to add");
		
		Move m = new Move();
		m.pos = p;
		m.type = t;
		m.next = moves;
		moves = m;
		count++;
	}
}
