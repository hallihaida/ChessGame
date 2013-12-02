package org.nagars;

public class Pawn extends ChessPiece {

	public Pawn(ChessColor c, int num) throws ChessException
	{
		super(c);
		int r = (c == ChessColor.COLOR_BLACK?1:6);

		if ((num < 1) || (num > 8))
			throw new ChessException("Only eight Pawns are allowed for each color");
		
		setPosition(new ChessPosition(r, num - 1));
	}
	
	public Pawn(ChessColor c, ChessPosition p) throws ChessException
	{
		super(c);
		setPosition(p);
	}

	public ChessMoves nextMoves(ChessBoard b)
	{
		int i = this.getPosition().getRow();
		int j = this.getPosition().getCol();
		ChessMoves moves = new ChessMoves();

		// Not dealing with promotion at this time
		try
		{
			if ((this.getColor() == ChessColor.COLOR_BLACK) && ((i + 1) < 8))
			{
				ChessPosition tp = new ChessPosition(i+1, j);
				if (b.pieceAt(tp) == null)
				{
					moves.addMove(tp);
				
					if (i == 1)
					{
						tp = new ChessPosition(i+2, j);
						if (b.pieceAt(tp) == null)
							moves.addMove(tp);
					}
				}
				
				// Check for kill moves
				if ((j+1) < 8)
				{
					tp = new ChessPosition(i+1, j+1);
					ChessPiece p = b.pieceAt(tp);
					if ((p != null) && (p.getColor() != this.getColor()))
						moves.addMove(tp, ChessMoves.MoveType.CAPTURE);
				}
				if ((j-1) >= 0)
				{
					tp = new ChessPosition(i+1, j-1);
					ChessPiece p = b.pieceAt(tp);
					if ((p != null) && (p.getColor() != this.getColor()))
						moves.addMove(tp, ChessMoves.MoveType.CAPTURE);
				}
			}
			else if ((i - 1) >= 0)
			{
				ChessPosition tp = new ChessPosition(i-1, j);
				if (b.pieceAt(tp) == null)
				{
					moves.addMove(tp);
				
					if (i == 6)
					{
						tp = new ChessPosition(i-2, j);
						if (b.pieceAt(tp) == null)
							moves.addMove(tp);
					}
				}
				
				// Check for kill moves
				if ((j+1) < 8)
				{
					tp = new ChessPosition(i-1, j+1);
					ChessPiece p = b.pieceAt(tp);
					if ((p != null) && (p.getColor() != this.getColor()))
						moves.addMove(tp, ChessMoves.MoveType.CAPTURE);
				}
				if ((j-1) >= 0)
				{
					tp = new ChessPosition(i-1, j-1);
					ChessPiece p = b.pieceAt(tp);
					if ((p != null) && (p.getColor() != this.getColor()))
						moves.addMove(tp, ChessMoves.MoveType.CAPTURE);
				}
			}
		}
		catch (Exception e)
		{
			moves = null;
		}
		
		
		return moves;		
	}
	

	@Override
	public ChessMoves.MoveType isValidMove(ChessBoard b, ChessPosition p) 
	{
		if ((p.getCol() < 0) || (p.getCol() > 7) || (p.getRow() < 0) || (p.getRow() > 7))
			return ChessMoves.MoveType.INVALID;

		ChessPiece cp = b.pieceAt(p);
		if (this.getColor() == ChessColor.COLOR_BLACK)
		{
			if ((p.getCol() == this.getPosition().getCol()) &&
				((p.getRow() == this.getPosition().getRow() + 1) ||
				 ((this.getPosition().getRow() == 1) && (p.getRow() == this.getPosition().getRow()+2))) &&
				(cp == null))
				return ChessMoves.MoveType.NORMAL;
			else if ((cp != null) && (cp.getColor() != this.getColor()))
			{
				if (((p.getCol() == this.getPosition().getCol() + 1) || (p.getCol() == this.getPosition().getCol() - 1)) &&
						 (p.getRow() == this.getPosition().getRow() + 1))
					return ChessMoves.MoveType.CAPTURE;
				else
					return ChessMoves.MoveType.INVALID;
			}
			else
				return ChessMoves.MoveType.INVALID;
		}
		else
		{
			if ((p.getCol() == this.getPosition().getCol()) &&
					((p.getRow() == this.getPosition().getRow() - 1) ||
					 ((this.getPosition().getRow() == 6) && (p.getRow() == this.getPosition().getRow()-2))) &&
					(cp == null))
					return ChessMoves.MoveType.NORMAL;
			else if ((cp != null) && (cp.getColor() != this.getColor()))
			{
				if (((p.getCol() == this.getPosition().getCol() + 1) || (p.getCol() == this.getPosition().getCol() - 1)) &&
						 (p.getRow() == this.getPosition().getRow() - 1))
					return ChessMoves.MoveType.CAPTURE;
				else
					return ChessMoves.MoveType.INVALID;
			}
			else
				return ChessMoves.MoveType.INVALID;
		}
	}

	@Override
	public String getShortDescription() 
	{
		if (this.clr == ChessColor.COLOR_BLACK)
			return new String("p");
		else
			return new String("P");
	}

	@Override
	public String getImage() 
	{
		if (this.clr == ChessColor.COLOR_BLACK)
			return new String("\u265F");
		else
			return new String("\u2659");
	}
}
