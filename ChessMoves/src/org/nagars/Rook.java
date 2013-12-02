package org.nagars;

public class Rook extends ChessPiece {

	public Rook(ChessColor c, int num) throws ChessException
	{
		super(c);
		int r = (c == ChessColor.COLOR_BLACK?0:7);

		if ((num != 1) && (num != 2))
			throw new ChessException("Only two Rooks are allowed for each color");
		
		if (num == 1)
			setPosition(new ChessPosition(r, 0));
		else
			setPosition(new ChessPosition(r, 7));
	}
	
	public Rook(ChessColor c, ChessPosition p) throws ChessException
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
		if ((p == null) || (b == null))
			return ChessMoves.MoveType.INVALID;
		
		if ((p.getCol() < 0) || (p.getCol() > 7) || (p.getRow() < 0) || (p.getRow() > 7))
			return ChessMoves.MoveType.INVALID;
		
		int i = this.getPosition().getRow();
		int j = this.getPosition().getCol();

		int row_diff = i - p.getRow();
		int col_diff = j - p.getCol();
		
		if (((row_diff == 0) && (col_diff == 0)) || ((row_diff != 0) && (col_diff != 0)))
			return ChessMoves.MoveType.INVALID;
		
		ChessMoves.MoveType can_move = ChessMoves.MoveType.NORMAL;
		try
		{
			if (col_diff == 0)
			{
				// Figure out if you are going forward or backward
				int increment = (i < p.getRow())?1:-1;;
				
				for (i += increment; i != p.getRow(); i += increment)
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
				// Figure out if you are going forward or backward
				int increment = (j < p.getCol())?1:-1;;
	
				for (j += increment; j != p.getCol(); j += increment)
				{
					ChessPiece piece = b.pieceAt(new ChessPosition(i, j));
					if (piece != null)
					{
						can_move = ChessMoves.MoveType.INVALID;
						break;
					}
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
			return new String("r");
		else
			return new String("R");
	}

	@Override
	public String getImage() 
	{
		if (this.clr == ChessColor.COLOR_BLACK)
			return new String("\u265C");
		else
			return new String("\u2656");
	}
}
