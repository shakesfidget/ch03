package ch03.gui;

import java.awt.Color;

import ch03.logic.Piece;

public class GuiPiece {
	
	private int x;
	private int y;
	private Piece piece;
	private int height;
	private int width;

	public GuiPiece(Piece piece, int width, int height) {
		this.piece = piece;
		this.width = width;
		this.height = height;
		this.resetToUnderlyingPiecePosition();
	}


	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Color getColor() {
		return this.piece.getColor();
	}
	
	@Override
	public String toString() {
		return this.piece+" "+x+"/"+y;
	}

	/**
	 * move the gui piece back to the coordinates that
	 * correspond with the underlying piece's row and column
	 */
	public void resetToUnderlyingPiecePosition() {
		this.x = ChessGui.convertColumnToX(piece.getColumn());
		this.y = ChessGui.convertRowToY(piece.getRow());
	}

	public Piece getPiece() {
		return piece;
	}
	
	
}
