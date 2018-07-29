package ch03.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch03.logic.ChessGame;


public class ChangeGameStateButtonActionListener implements ActionListener {

	private ChessGui chessGui;
	private ChessGame chessGame;

	public ChangeGameStateButtonActionListener(ChessGui chessGui, ChessGame chessGame) {
		this.chessGui = chessGui;
		this.chessGame = chessGame;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		


		
		this.chessGui.createAndAddCustomGuiPiece(this.chessGame.createAndAddCustomPiece(0, chessGui.START_CUSTOM_ROW, -chessGui.START_CUSTOM_COLUMN));
		this.chessGui.repaint();
	}
}
