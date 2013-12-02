package org.nagars;

public class Queen extends ChessPiece {

	public Queen(ChessColor c) throws ChessException
	{
		super(c);
		int r = (c == ChessColor.COLOR_BLACK?0:7);

		setPosition(new ChessPosition(r, 3));
	}
	
	public Queen(ChessColor c, ChessPosition p) throws ChessException
	{
		super(c);
		setPosition(p);
	}

	private boolean addMove(ChessBoard b, ChessMoves moves, int i, int j) throws ChessException
	{
		boolean ret = false;
		
		ChessPosition tp = new ChessPosition(i,j);
		ChessPiece p = b.pieceAt(tp);
		if (p == null)
		{
			moves.addMove(tp);
			ret = true;
		}
		else if (p.getColor() != this.getColor())
			moves.addMove(tp, ChessMoves.MoveType.CAPTURE);
		return ret;
	}
	
	public ChessMoves nextMoves(ChessBoard b)
	{
		int i = this.getPosition().getRow();
		int j = this.getPosition().getCol();

		ChessMoves moves = new ChessMoves();
		try
		{
			for (int k = i; k < 8; k++)
			{
				if (k != i)
				{
					if (!addMove(b, moves, k, j))
						break;
				}
			}
			
			for (int k = i; k >= 0; k--)
			{
				if (k != i)
				{
					if (!addMove(b, moves, k, j))
						break;
				}
			}
	
			for (int k = j; k < 8; k++)
			{
				if (k != j)
				{
					if (!addMove(b, moves, i, k))
						break;
				}
			}

			for (int k = j; k >= 0; k--)
			{
				if (k != j)
				{
					if (!addMove(b, moves, i, k))
						break;
				}
			}
			
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
			moves = null;
		}

		return moves;		
	}


	@Override
	public ChessMoves.MoveType isValidMove(ChessBoard b, ChessPosition p) 
	{
		if ((b == null) || (p == null))
			return ChessMoves.MoveType.INVALID;
		
		if ((p.getCol() < 0) || (p.getCol() > 7) || (p.getRow() < 0) || (p.getRow() > 7))
			return ChessMoves.MoveType.INVALID;
		
		int i = this.getPosition().getRow();
		int j = this.getPosition().getCol();

		if ((i == p.getRow()) && (j == p.getCol()))
			return ChessMoves.MoveType.INVALID;

		int row_diff = i - p.getRow();
		int col_diff = j - p.getCol();		

		// If it is not on the same row, or the same col or the same diagonal, its not valid
		if ((Math.abs(row_diff) != Math.abs(col_diff)) && ((Math.abs(row_diff) > 0) && (Math.abs(col_diff) > 0)))
			return ChessMoves.MoveType.INVALID;
		
		ChessMoves.MoveType can_move = ChessMoves.MoveType.NORMAL;
		// Figure out if you are going forward or backward
		int row_increment = (i < p.getRow())?1:-1;
		int col_increment = (j < p.getCol())?1:-1;
		
		try
		{
			if (row_diff == 0)
			{
				// Moving horizontally
				for (j += col_increment; j != p.getCol(); j += col_increment)
				{
					ChessPiece piece = b.pieceAt(new ChessPosition(i, j));
					if (piece != null)
					{
						can_move = ChessMoves.MoveType.INVALID;
						break;
					}
				}				
			}
			else if (col_diff == 0)
			{
				// Moving vertically
				for (i += row_increment; i != p.getRow(); i += row_increment)
				{
					ChessPiece piece = b.pieceAt(new ChessPosition(i, j));
					if (piece != null)
					{
						can_move = ChessMoves.MoveType.INVALID;
						break;
					}
				}				
			}
			else
			{
				// Moving diagonally
				for (i += row_increment, j += col_increment; (i != p.getRow()) && (j != p.getCol()); i += row_increment, j += col_increment)
				{
					ChessPiece piece = b.pieceAt(new ChessPosition(i, j));
					if (piece != null)
					{
						can_move = ChessMoves.MoveType.INVALID;
						break;
					}
				}				
			}
			
			if (can_move != ChessMoves.MoveType.INVALID)
			{
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
			return new String("q");
		else
			return new String("Q");
	}

	@Override
	public String getImage() 
	{
		if (this.clr == ChessColor.COLOR_BLACK)
			return new String("\u265B");
		else
			return new String("\u2655");
	}
}
