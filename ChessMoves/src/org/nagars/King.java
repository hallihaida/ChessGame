package org.nagars;

public class King extends ChessPiece {

	public King(ChessColor c) throws ChessException
	{
		super(c);
		int r = (c == ChessColor.COLOR_BLACK?0:7);

		setPosition(new ChessPosition(r, 4));
	}
	
	public King(ChessColor c, ChessPosition p) throws ChessException
	{
		super(c);
		setPosition(p);
	}

	public ChessMoves nextMoves(ChessBoard b)
	{
		// Initialize moves to 0
		ChessMoves moves;
		int i = this.getPosition().getRow();
		int j = this.getPosition().getCol();
		

		moves = new ChessMoves();
		try
		{
			for (int dy = -1; dy < 2; dy++)
			{
				for (int dx = -1; dx < 2; dx++)
				{
					int to_i = i + dy;
					int to_j = j + dx;
					if (!((dx == 0) && (dy == 0)) && (to_i >= 0) && (to_i <=7) && (to_j >= 0) && (to_j <= 7))
					{
						ChessPosition pos = new ChessPosition(to_i, to_j);
						ChessPiece p = b.pieceAt(pos);
						if (p == null)
							moves.addMove(pos);
						else if (p.getColor() != this.getColor())
							moves.addMove(pos, ChessMoves.MoveType.CAPTURE);
					}
				}
			}
			
			// Check the King can castle
			
		}
		catch (Exception e)
		{
			// Since only valid positions are created, don't worry about exceptions
			moves = null;
		}
		
		return moves;		
	}
	

	@Override
	public ChessMoves.MoveType isValidMove(ChessBoard b, ChessPosition p) 
	{
		if ((p.getCol() < 0) || (p.getCol() > 7) || (p.getRow() < 0) || (p.getRow() > 7))
			return ChessMoves.MoveType.INVALID;

		ChessPosition cur_pos = this.getPosition();
		int dx = Math.abs(cur_pos.getCol() - p.getCol());
		int dy = Math.abs(cur_pos.getRow() - p.getRow());
		
		if (((dx == 1) && (dy == 0)) || ((dx == 0) && (dy == 1)) || ((dx == 1) && (dy == 1)))
		{
			ChessPiece cp = b.pieceAt(p);
			if (cp == null)
				return ChessMoves.MoveType.NORMAL;
			else if (cp.getColor() != this.getColor())
				return ChessMoves.MoveType.CAPTURE;
			else
				return ChessMoves.MoveType.INVALID;
		}
		else
			return ChessMoves.MoveType.INVALID;		
	}

	@Override
	public String getShortDescription() 
	{
		if (this.clr == ChessColor.COLOR_BLACK)
			return new String("k");
		else
			return new String("K");
	}

	public String getImage()
	{
		if (this.clr == ChessColor.COLOR_BLACK)
			return new String("\u265A");
		else
			return new String("\u2654");
	}
}
