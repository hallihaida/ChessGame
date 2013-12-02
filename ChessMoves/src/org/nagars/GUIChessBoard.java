package org.nagars;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GUIChessBoard extends ChessBoard implements MouseListener
{
	protected JFrame board_ui;
	protected ChessColor turn;
	protected ChessPosition from;
	protected ChessMoves moves_from;
	

	public GUIChessBoard() throws ChessException
	{
		super();
		turn = ChessColor.COLOR_WHITE;
		from = null;
		board_ui = null;
		board_ui = new JFrame("Chess Board");
		
		board_ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board_ui.setPreferredSize(new Dimension(400,400));
		
		GridLayout gl = new GridLayout(9,9);
		Container pnl = board_ui.getContentPane();
		pnl.setLayout(gl);
		
		Font f = new Font(null, Font.BOLD|Font.ITALIC, 16);
		Font f2 = new Font(null, Font.BOLD, 26);
		Color clr = new Color(255,0,0);
		Color clr2 = new Color(0, 0, 255);
		
		// Add the first item on the label row
		pnl.setBackground(Color.WHITE);
		JLabel jl = new JLabel();
		jl.setText(" ");
		pnl.add(jl);
		
		
		for (int i = 0; i < 8; i++)
		{
			jl = new JLabel();
			jl.setFont(f);
			jl.setOpaque(true);
			jl.setText(Character.toString((char)('A'+i)));
			jl.setHorizontalAlignment(JLabel.CENTER);
			jl.setVerticalAlignment(JLabel.CENTER);
			jl.setForeground(clr);
			pnl.add(jl);
		}
		for (int i = 0; i < 8; i++)
		{
			// The Row Label
			jl = new JLabel();
			jl.setFont(f);
			jl.setHorizontalAlignment(JLabel.CENTER);
			jl.setVerticalAlignment(JLabel.CENTER);
			jl.setText(Integer.toString(8-i));
			jl.setForeground(clr);
			pnl.add(jl);
			
			for (int j = 0; j < 8; j++)
			{
				jl = new JLabel();
				jl.setBorder(BorderFactory.createLineBorder(Color.black));
				jl.setOpaque(true);
				jl.setHorizontalAlignment(JLabel.CENTER);
				jl.setVerticalAlignment(JLabel.CENTER);
				jl.setFont(f2);
				jl.setForeground(clr2);
				
				if ((i % 2) == 1)
				{
					if ((j%2) == 1)
						jl.setBackground(Color.WHITE);
					else
						jl.setBackground(Color.LIGHT_GRAY);
				}
				else
				{
					if ((j%2) == 0)
						jl.setBackground(Color.WHITE);
					else
						jl.setBackground(Color.LIGHT_GRAY);
				}

				if (board[i][j] != null)
					jl.setText(board[i][j].getImage());
				jl.addMouseListener(this);
				pnl.add(jl);
			}
		}
		board_ui.pack();
		board_ui.setVisible(true);		
	}

	public void makeMove(ChessPosition from, ChessPosition to) throws ChessException
	{
		super.makeMove(from, to);
		int idx = ((from.getRow()+1)*9)+(from.getCol()+1);
		JLabel jl = (JLabel)board_ui.getContentPane().getComponent(idx);
		if (jl == null)
			return;
		
		jl.setText(" ");
		
		if (to == null)
			return;
		
		idx = ((to.getRow()+1)*9)+(to.getCol()+1);
		jl = (JLabel)board_ui.getContentPane().getComponent(idx);
		if (jl == null)
			return;
		
		jl.setText(board[to.getRow()][to.getCol()].getImage());
		board_ui.repaint();
	}
	
	public ChessColor getTurn()
	{
		return turn;
	}
	
	private ChessPosition getChessPositionFromClick(MouseEvent e)
	{
		ChessPosition pos = null;
		
		JLabel jl = (JLabel)e.getSource();
		
		if (jl != null)
		{
			Component jc[] = board_ui.getContentPane().getComponents();
			
			for (int i = 0; i < jc.length; i++)
			{
				if (jc[i] == jl)
				{
					try 
					{
						pos = new ChessPosition((i/9)-1, (i%9)-1);
					} 
					catch (ChessException e1) 
					{
						// TODO Auto-generated catch block
						pos = null;
					}
					break;
				}
			}
		}
		
		return pos;
	}

	private void removeMoveHighlights()
	{
		if (moves_from == null)
			return;
		
		// Remove all the border highlights
		try
		{
			for (int k = 0; k < moves_from.getCount(); k++)
			{
				ChessPosition new_pos = moves_from.getMove(k);
				int idx = ((new_pos.getRow()+1)*9)+(new_pos.getCol()+1);
				JLabel jl = (JLabel)board_ui.getContentPane().getComponent(idx);
				if (jl != null)
				{
					int i = new_pos.getRow();
					int j = new_pos.getCol();
					//jl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					if ((i % 2) == 1)
					{
						if ((j%2) == 1)
							jl.setBackground(Color.WHITE);
						else
							jl.setBackground(Color.LIGHT_GRAY);
					}
					else
					{
						if ((j%2) == 0)
							jl.setBackground(Color.WHITE);
						else
							jl.setBackground(Color.LIGHT_GRAY);
					}
				}
			}
		}
		catch (ChessException ce)
		{
			System.out.println(ce.getMessage());
		}
	}
	
	private void setPieceToMove(ChessPiece p)
	{		
		if ((p == null) || (p.getColor() != turn))
			return;

		// if there was a previous set of moves,
		//  reset the UI from it
		this.removeMoveHighlights();
		
		moves_from = p.nextMoves(this);
		if (moves_from == null)
		{
			System.out.printf("No valid moves for piece at [%c,%c]\n", p.getPosition().getBoardCol(), p.getPosition().getBoardRow());
			return;
		}
		
		from = p.getPosition();
		try
		{
			for (int k = 0; k < moves_from.getCount(); k++)
			{
				ChessPosition new_pos = moves_from.getMove(k);
				ChessMoves.MoveType mt = moves_from.moveType(k);
				int idx = ((new_pos.getRow()+1)*9)+(new_pos.getCol()+1);
				JLabel jl = (JLabel)board_ui.getContentPane().getComponent(idx);
				if (jl != null)
				{
					if (mt == ChessMoves.MoveType.NORMAL)
						jl.setBackground(Color.GREEN);
					else if (mt == ChessMoves.MoveType.CAPTURE)
						jl.setBackground(Color.RED);
				}
			}
		}
		catch (ChessException ce)
		{
			System.out.println(ce.getMessage());
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		// If this is the first time a position is selected
		//  and there is a legitimate piece, then mark it
		if (from == null)
		{
			ChessPosition pos = getChessPositionFromClick(e);
			ChessPiece p = pieceAt(pos);
			if (p == null)
				return;
			setPieceToMove(p);
		}
		else
		{
			try
			{
				ChessPiece moving_piece = pieceAt(from);
				if (moving_piece == null)
				{
					from = null;
					return;
				}
				
				ChessPosition pos = getChessPositionFromClick(e);
				if (pos == null)
				{
					from = null;
					return;
				}
				
				// If there is a piece at the target position
				//  and if that piece is the same color, then
				//  the player has changed the mind about the 
				//  piece they want to move, so reset the piece
				ChessPiece p = this.pieceAt(pos);
				if ((p != null) && (p.getColor() == moving_piece.getColor()))
				{
					setPieceToMove(p);
					return;
				}
				
				makeMove(from, pos);
				// Remove all the border highlights
				this.removeMoveHighlights();
				
				// reset where you came from
				from = null;
				// Switch the sides to make the move
				turn = (turn == ChessColor.COLOR_BLACK)?ChessColor.COLOR_WHITE:ChessColor.COLOR_BLACK;					
			}
			catch (ChessException ce)
			{
				from = null;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
