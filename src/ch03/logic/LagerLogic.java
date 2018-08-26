package ch03.logic;

import java.util.ArrayList;
import java.util.List;

import ch03.gui.GuiPiece;
import ch03.gui.LagerGui;

public class LagerLogic {
	

	
	// 0 = bottom, size = top
	private List<Piece> pieces = new ArrayList<Piece>();

	/**
	 * initialize game
	 */
	public LagerLogic(){
		
		// create and place pieces
		// rook, knight, bishop, queen, king, bishop, knight, and rook
		createAndAddPiece(0, Piece.ROW_1, Piece.COLUMN_A);

		



		

	}

	/**
	 * create piece instance and add it to the internal list of pieces
	 * 
	 * @param color on of Pieces.COLOR_..
	 * @param row on of Pieces.ROW_..
	 * @param column on of Pieces.COLUMN_..
	 */
	public Piece createAndAddPiece(int color, int row, int column) {
		
		Piece piece = new Piece(color, row, column);
		this.pieces.add(piece);
		return piece;
	}
	
	

	
public boolean placePiece(int targetRow, int targetColumn, int width, int height) {
		

		
				
				for (Piece otherPiece : this.pieces)
				{

						int widtho = otherPiece.getWidth(); // = 2
						int heighto = otherPiece.getHeight(); // = 1					
												
						for(int i = width-1; i >= 0; i--)
						{
							for(int j = height-1; j >= 0; j--)
							{	
								for(int k = widtho-1; k>=0;k--)
								{
									for(int l = heighto-1;l>=0;l--)
									{
										if (targetRow+j == otherPiece.getRow()+l && targetColumn+i == otherPiece.getColumn()+k)
										{	
											System.out.println("besetzt bei" + targetColumn + "/" + targetRow);
											return true;						
										}
									}
								}

							}
						}	
				}
				return false;
			}
				

	
	
	


	/**
	 * Move piece to the specified location. If the target location is occupied
	 * by an opponent piece, that piece is marked as 'captured'
	 * @param sourceRow the source row (Piece.ROW_..) of the piece to move
	 * @param sourceColumn the source column (Piece.COLUMN_..) of the piece to move
	 * @param targetRow the target row (Piece.ROW_..)
	 * @param targetColumn the target column (Piece.COLUMN_..)
	 */
	public void movePiece(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		

		//überprüfe ob Ziel frei
		for (Piece piece : this.pieces)
		{
			if (piece.getRow() == sourceRow && piece.getColumn() == sourceColumn)
			{			
				int width = piece.getWidth(); // = 2
				int height = piece.getHeight(); // = 1
				
				for (Piece otherPiece : this.pieces)
				{
					if (piece != otherPiece)
					{
						
						int widtho = otherPiece.getWidth(); // = 2
						int heighto = otherPiece.getHeight(); // = 1					
												
						for(int i = width-1; i >= 0; i--)
						{
							for(int j = height-1; j >= 0; j--)
							{	
								for(int k = widtho-1; k>=0;k--)
								{
									for(int l = heighto-1;l>=0;l--)
									{
										if (targetRow+j == otherPiece.getRow()+l && targetColumn+i == otherPiece.getColumn()+k)
										{						
											return;						
										}
									}
								}

							}
						}	
					}
				}	
			}
		}
		
		for (Piece piece : this.pieces)
		{
			if (piece.getRow() == sourceRow && piece.getColumn() == sourceColumn)
			{							
				piece.setRow(targetRow);
				piece.setColumn(targetColumn);
			}
		}
		

	}


	/**
	 * @return the internal list of pieces
	 */
	public List<Piece> getPieces() {
		return this.pieces;
	}



}
