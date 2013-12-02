package org.nagars;

public class ChessBoard 
{
	ChessPiece[][] board;
	ChessPiece[] all_pieces;
	
	public ChessBoard() throws ChessException
	{
		board = new ChessPiece[8][];
		
		for (int i = 0; i < 8; i++)
		{
			board[i] = new ChessPiece[8];
			for (int j = 0; j < 8; j++)
				board[i][j] = null;
		}
		
		// Create all the pieces
		all_pieces = new ChessPiece[32];
		ChessColor c = ChessColor.COLOR_BLACK;
		int j = 0;
		for (int i = 0; i < 2; i++)
		{
			all_pieces[j++] = new King(c);
			all_pieces[j++] = new Queen(c);
			all_pieces[j++] = new Bishop(c, 1);
			all_pieces[j++] = new Bishop(c, 2);
			all_pieces[j++] = new Knight(c, 1);
			all_pieces[j++] = new Knight(c, 2);
			all_pieces[j++] = new Rook(c, 1);
			all_pieces[j++] = new Rook(c, 2);
			for (int k = 1; k < 9; k++)
				all_pieces[j++] = new Pawn(c, k);
			c = ChessColor.COLOR_WHITE;
		}
		
		for (int i = 0; i < 32; i++)
			board[all_pieces[i].getPosition().getRow()][all_pieces[i].getPosition().getCol()] = all_pieces[i];
	}
	
	public void makeMove(ChessPosition from, ChessPosition to) throws ChessException
	{
		if ((from == null) || (to == null))
			throw new ChessException("Invalid position to move from or move to");

		ChessPiece p = board[from.getRow()][from.getCol()];
		if (p == null)
			throw new ChessException("No piece to move at origin");
		
		ChessPiece t = board[to.getRow()][to.getCol()];
		if (t != null)
			t.capture();

		p.makeMove(this, to);
		
		// If move was made, switch its position on the board
		board[from.getRow()][from.getCol()] = null;
		board[to.getRow()][to.getCol()] = p;
	}
	
	public ChessPiece pieceAt(ChessPosition pos)
	{
		return board[pos.getRow()][pos.getCol()];
	}
	
}
