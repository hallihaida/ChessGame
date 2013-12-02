package org.nagars;

import java.util.Scanner;
import java.util.regex.MatchResult;

public class TextChessBoard extends ChessBoard 
{

	private void printChessBoard()
	{
		System.out.print("    \t");
		for (int i = 0; i < 8; i++)
			System.out.format("%5c ", (char)('A'+i));
		System.out.print("\n");
		
		// for each row
		for (int i = 0; i < 8; i++)
		{
			// Print the row number
			System.out.format("%4d\t", (8-i));
			
			// Now print the moves for each cell on that row
			for (int j = 0; j < 8; j++)
				if (board[i][j] != null)
					System.out.printf("%5s ", board[i][j].getShortDescription());
				else
					System.out.printf("      ");
			
			// Move to the next line
			System.out.print("\n");
		}		
	}
	
	public TextChessBoard() throws ChessException
	{
		super();
		printChessBoard();
	}

	public void makeMove(ChessPosition from, ChessPosition to) throws ChessException
	{
		super.makeMove(from, to);
		printChessBoard();
	}
	
	public void startGame()
	{
		Scanner s = new Scanner(System.in);
		ChessColor turn = ChessColor.COLOR_WHITE;
		do
		{
			System.out.printf("\n>> ");
			if (s.findInLine("([a-hA-H][1-8])\\s+([a-hA-H][1-8])") != null)
			{
				try
				{
					MatchResult mr = s.match();
					if (mr.groupCount() == 2)
					{
						ChessPosition from_pos = new ChessPosition(mr.group(1));
						ChessPiece from = pieceAt(from_pos);
						if (from != null)
						{
							if (from.getColor() != turn)
								System.out.println("Cannot move this piece. Not your turn");
							else
							{
								// Make a move and switch sides
								ChessPosition to = new ChessPosition(mr.group(2));
								makeMove(from_pos, to);
								turn = (turn == ChessColor.COLOR_BLACK)?ChessColor.COLOR_WHITE:ChessColor.COLOR_BLACK;
							}
						}
						else
							System.out.println("No piece to move at that position");
					}
					else
						System.out.println("You must give the full move");
				}
				catch (IllegalStateException is)
				{
					System.out.println("----> Error in input");
				}
				catch (ChessException ce)
				{
					System.out.println(ce.getMessage());
				}
			}
			else if (s.findInLine("([a-hA-H][1-8])") != null)
			{
				try
				{
					MatchResult mr = s.match();
					if (mr.groupCount() == 1)
					{
						ChessPosition from_pos = new ChessPosition(mr.group(1));
						ChessPiece from = pieceAt(from_pos);
						if (from != null)
						{
							if (from.getColor() != turn)
								System.out.println("Cannot move this piece. Not your turn");
							else
							{
								ChessMoves moves = from.nextMoves(this);
								for (int i = 0; i < moves.getCount(); i++)
								{
									ChessPosition pos = moves.getMove(i);
									ChessMoves.MoveType mt = moves.moveType(i);
									System.out.printf("\t%d: [%c,%c]", i+1, pos.getBoardCol(), pos.getBoardRow());
									switch (mt)
									{
									case CAPTURE:
										System.out.println(" - Capture");
										break;
									case CASTLE:
									case INVALID:
									case NORMAL:
									case PROMOTE:
									default:
										System.out.println();
										break;
									}
								}
							}
						}
						else
							System.out.println("No piece to move at that position");
					}
				}
				catch (IllegalStateException is)
				{
					System.out.println("----> Error in input");
				}
				catch (ChessException ce)
				{
					System.out.println(ce.getMessage());
				}
			}
			else
				System.out.println("Improper input given");
			s.nextLine();
		} while(true);		
	}
}
