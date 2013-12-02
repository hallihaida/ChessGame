package org.nagars;

public abstract class ChessPiece 
{	
	protected ChessColor clr;
	protected ChessPosition pos;
	protected boolean has_moved;
	
	protected ChessPiece(ChessColor c)
	{
		has_moved = false;
		clr = c;
		pos = null;
	}
	
	
	public boolean madeFirstMove()
	{
		return has_moved;
	}
	
	protected void setColor(ChessColor c)
	{
		clr = c;
	}
	
	protected void setPosition(ChessPosition p)
	{
		pos = p;
	}
	
	public void capture()
	{
		pos = null;
		
	}
	
	public ChessColor getColor()
	{
		return clr;
	}
	
	public ChessPosition getPosition()
	{
		return pos;
	}
	
	public abstract ChessMoves nextMoves(ChessBoard b);
	public abstract ChessMoves.MoveType isValidMove(ChessBoard b, ChessPosition p);
	protected abstract String getShortDescription();
	protected abstract String getImage();
	
	public void makeMove(ChessBoard b, ChessPosition p) throws ChessException
	{
		ChessMoves.MoveType mt = isValidMove(b, p);
		if (mt != ChessMoves.MoveType.INVALID)
		{
			has_moved = true;
			pos = p;
		}
		else
			throw new ChessException("Invalid move to make for " + getShortDescription());
	}
	
}
