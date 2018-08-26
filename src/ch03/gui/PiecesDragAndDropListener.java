package ch03.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import ch03.logic.LagerLogic;
import ch03.logic.Piece;

public class PiecesDragAndDropListener implements MouseListener, MouseMotionListener {

	//private List<GuiPiece> guiPieces;
	private LagerGui chessGui;
	
	private GuiPiece dragPiece;
	private int dragOffsetX;
	private int dragOffsetY;
	

	public PiecesDragAndDropListener(List<GuiPiece> guiPieces, LagerGui chessGui) {

		this.chessGui = chessGui;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent evt) {
		int x = evt.getPoint().x;
		int y = evt.getPoint().y;
		
		// find out which piece to move.
		// we check the list from top to buttom
		// (therefore we itereate in reverse order)
		//
		for (int i = this.chessGui.getGuiPieces().size()-1; i >= 0; i--) {
			GuiPiece guiPiece = this.chessGui.getGuiPieces().get(i);

			if(mouseOverPiece(guiPiece,x,y)){
				
					// calculate offset, because we do not want the drag piece
					// to jump with it's upper left corner to the current mouse
					// position
					//
					this.dragOffsetX = x - guiPiece.getX();
					this.dragOffsetY = y - guiPiece.getY();
					this.dragPiece = guiPiece;
					break;
				
			}
		}
		
		// move drag piece to the top of the list
		if(this.dragPiece != null){
			this.chessGui.getGuiPieces().remove( this.dragPiece );
			this.chessGui.getGuiPieces().add(this.dragPiece);
		}
	}

	/**
	 * check whether the mouse is currently over this piece
	 * @param piece the playing piece
	 * @param x x coordinate of mouse
	 * @param y y coordinate of mouse
	 * @return true if mouse is over the piece
	 */
	private boolean mouseOverPiece(GuiPiece guiPiece, int x, int y) {

		return guiPiece.getX() <= x 
			&& guiPiece.getX()+guiPiece.getWidth() >= x
			&& guiPiece.getY() <= y
			&& guiPiece.getY()+guiPiece.getHeight() >= y;
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		if( this.dragPiece != null){
			int x = evt.getPoint().x - this.dragOffsetX;
			int y = evt.getPoint().y - this.dragOffsetY;
			
			// set game piece to the new location if possible
			//
			chessGui.setNewPieceLocation(this.dragPiece, x, y);
			this.chessGui.repaint();
			this.dragPiece = null;
		}
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		if(this.dragPiece != null){
			
			int x = evt.getPoint().x - this.dragOffsetX;
			int y = evt.getPoint().y - this.dragOffsetY;
			
			System.out.println(
					"row:"+LagerGui.convertYToRow(y)
					+" column:"+LagerGui.convertXToColumn(x));
			
			this.dragPiece.setX(x);
			this.dragPiece.setY(y);
			
			this.chessGui.repaint();
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {}

}
