package org.nagars;

public class Bishop extends ChessPiece 
{
	public Bishop(ChessColor c, int num) throws ChessException
	{
		super(c);
		int r = (c == ChessColor.COLOR_BLACK?0:7);

		if ((num != 1) && (num != 2))
			throw new ChessException("Only two Bishops are allowed for each color");
		
		if (num == 1)
			setPosition(new ChessPosition(r, 2));
		else
			setPosition(new ChessPosition(r, 5));
	}
	
	public Bishop(ChessColor c, ChessPosition p) throws ChessException
	{
		super(c);
		setPosition(p);
	}


	public ChessMoves nextMoves(ChessBoard b)
	{
		int i = this.getPosition().getRow();
		int j = this.getPosition().getCol();
		ChessMoves moves = new ChessMoves();

		try
		{
			for (i = 1; i > -2; i -= 2)
			{
				for (j = 1; j > -2; j -= 2)
				{
					boolean good_move;
					int tr = this.getPosition().getRow();
					int tc = this.getPosition().getCol();

					do
					{
						tr = tr + i;
						tc = tc + j;
						if ((tr >= 0) && (tr <= 7) && (tc >= 0) && (tc <= 7))
						{
							// If a piece exists in the current position
							// and it is of the same color, then it cannot move there
							//  otherwise, you can move to capture, but no more moves beyond that
							//  along that direction
							ChessPosition tp = new ChessPosition(tr, tc);
							ChessPiece p = b.pieceAt(tp);
							if (p == null)
							{
								moves.addMove(new ChessPosition(tr, tc));
								good_move = true;
							}
							else if (p.getColor() != this.getColor())
							{
								moves.addMove(new ChessPosition(tr, tc), ChessMoves.MoveType.CAPTURE);
								good_move = false;
							}
							else
								good_move = false;
						}
						else
							good_move = false;
					}
					while (good_move);
				}
			}
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
		if (p == null)
			return ChessMoves.MoveType.INVALID;
		
		if ((p.getCol() < 0) || (p.getCol() > 7) || (p.getRow() < 0) || (p.getRow() > 7))
			return ChessMoves.MoveType.INVALID;
		
		int i = this.getPosition().getRow();
		int j = this.getPosition().getCol();
		
		if ((i == p.getRow()) && (j == p.getCol()))
			return ChessMoves.MoveType.INVALID;
		
		int row_diff = i - p.getRow();
		int col_diff = j - p.getCol();
		
		// Since bishops move only diagonally, this check covers it
		if (Math.abs(row_diff) != Math.abs(col_diff))
			return ChessMoves.MoveType.INVALID;
		
		ChessMoves.MoveType can_move = ChessMoves.MoveType.NORMAL;
		// Figure out if you are going forward or backward
		int row_increment = (i < p.getRow())?1:-1;
		int col_increment = (j < p.getCol())?1:-1;
		
		try
		{
			for (i += row_increment, j += col_increment; (i != p.getRow()) && (j != p.getCol()); i += row_increment, j += col_increment)
			{
				ChessPiece piece = b.pieceAt(new ChessPosition(i, j));
				if (piece != null)
				{
					can_move = ChessMoves.MoveType.INVALID;
					break;
				}
			}
			
			// One last check to make sure that there is nothing in the landing square
			ChessPiece piece = b.pieceAt(new ChessPosition(p.getRow(), p.getCol()));
			if (piece != null)
			{
				if (piece.getColor() != this.getColor())
					can_move = ChessMoves.MoveType.CAPTURE;
				else
					can_move = ChessMoves.MoveType.INVALID;
			}			
		}
		catch (ChessException e)
		{
			can_move = ChessMoves.MoveType.INVALID;
		}
		
		return can_move;
	}

	@Override
	public String getShortDescription() 
	{
		if (this.clr == ChessColor.COLOR_BLACK)
			return new String("b");
		else
			return new String("B");
	}
	
	public String getImage()
	{
		if (this.clr == ChessColor.COLOR_BLACK)
			return new String("\u265D");
		else
			return new String("\u2657");		
	}

}
