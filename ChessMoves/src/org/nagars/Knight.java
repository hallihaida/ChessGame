package org.nagars;

public class Knight extends ChessPiece {

	public Knight(ChessColor c, int num) throws ChessException
	{
		super(c);
		int r = (c == ChessColor.COLOR_BLACK?0:7);

		if ((num != 1) && (num != 2))
			throw new ChessException("Only two knights are allowed for each color");
		
		if (num == 1)
			setPosition(new ChessPosition(r, 1));
		else
			setPosition(new ChessPosition(r, 6));
	}
	
	public Knight(ChessColor c, ChessPosition p) throws ChessException
	{
		super(c);
		setPosition(p);
	}


	public ChessMoves nextMoves(ChessBoard b)
	{
		// Initialize moves to 0
		int i = this.getPosition().getRow();
		int j = this.getPosition().getCol();

		ChessMoves moves = new ChessMoves();
		try
		{
			if (((i+2) < 8) && ((j+1) < 8))
			{
				ChessPosition p = new ChessPosition(i+2, j+1);
				ChessPiece tp = b.pieceAt(p);
				if ((tp != null) && (tp.getColor() != this.clr))
					moves.addMove(p, ChessMoves.MoveType.CAPTURE);
				else if (tp == null)
					moves.addMove(p);
			}
			if (((i+2) < 8) && ((j-1) >= 0))
			{
				ChessPosition p = new ChessPosition(i+2, j-1);
				ChessPiece tp = b.pieceAt(p);
				if ((tp != null) && (tp.getColor() != this.clr))
					moves.addMove(p, ChessMoves.MoveType.CAPTURE);
				else if (tp == null)
					moves.addMove(p);
			}
			if (((i-2) >= 0) && ((j+1) < 8))
			{
				ChessPosition p = new ChessPosition(i-2, j+1);
				ChessPiece tp = b.pieceAt(p);
				if ((tp != null) && (tp.getColor() != this.clr))
					moves.addMove(p, ChessMoves.MoveType.CAPTURE);
				else if (tp == null)
					moves.addMove(p);
			}
			if (((i-2) >= 0) && ((j-1) >= 0))
			{
				ChessPosition p = new ChessPosition(i-2, j-1);
				ChessPiece tp = b.pieceAt(p);
				if ((tp != null) && (tp.getColor() != this.clr))
					moves.addMove(p, ChessMoves.MoveType.CAPTURE);
				else if (tp == null)
					moves.addMove(p);
			}
			if (((i+1) < 8) && ((j+2) < 8))
			{
				ChessPosition p = new ChessPosition(i+1, j+2);
				ChessPiece tp = b.pieceAt(p);
				if ((tp != null) && (tp.getColor() != this.clr))
					moves.addMove(p, ChessMoves.MoveType.CAPTURE);
				else if (tp == null)
					moves.addMove(p);
			}
			if (((i+1) < 8) && ((j-2) >= 0))
			{
				ChessPosition p = new ChessPosition(i+1, j-2);
				ChessPiece tp = b.pieceAt(p);
				if ((tp != null) && (tp.getColor() != this.clr))
					moves.addMove(p, ChessMoves.MoveType.CAPTURE);
				else if (tp == null)
					moves.addMove(p);
			}
			if (((i-1) >= 0) && ((j+2) < 8))
			{
				ChessPosition p = new ChessPosition(i-1, j+2);
				ChessPiece tp = b.pieceAt(p);
				if ((tp != null) && (tp.getColor() != this.clr))
					moves.addMove(p, ChessMoves.MoveType.CAPTURE);
				else if (tp == null)
					moves.addMove(p);
			}
			if (((i-1) >= 0) && ((j-2) >= 0))
			{
				ChessPosition p = new ChessPosition(i-1, j-2);
				ChessPiece tp = b.pieceAt(p);
				if ((tp != null) && (tp.getColor() != this.clr))
					moves.addMove(p, ChessMoves.MoveType.CAPTURE);
				else if (tp == null)
					moves.addMove(p);
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
		if ((p.getCol() < 0) || (p.getCol() > 7) || (p.getRow() < 0) || (p.getRow() > 7))
			return ChessMoves.MoveType.INVALID;

		ChessPosition cur_pos = this.getPosition();
		int dx = Math.abs(cur_pos.getCol() - p.getCol());
		int dy = Math.abs(cur_pos.getRow() - p.getRow());
		
		if (((dx == 2) && (dy == 1)) || ((dx == 1) && (dy == 2)))
		{
			ChessPiece cp = b.pieceAt(p);
			if ((cp == null) || (cp.getColor() == this.getColor()))
				return ChessMoves.MoveType.NORMAL;
			else
				return ChessMoves.MoveType.CAPTURE;
		}
		else
			return ChessMoves.MoveType.INVALID;		
	}

	@Override
	public String getShortDescription() 
	{
		if (this.clr == ChessColor.COLOR_BLACK)
			return new String("n");
		else
			return new String("N");
	}
	
	public String getImage()
	{
		if (this.clr == ChessColor.COLOR_BLACK)
			return new String("\u265E");
		else
			return new String("\u2658");
	}

}
