package ch03.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import ch03.logic.ChessGame;
import ch03.logic.Piece;

/**
 * all x and y coordinates point to the upper left position of a component all
 * lists are treated as 0 being the bottom and size-1 being the top piece
 * 
 */
public class ChessGui extends JPanel {
	
	private static final long serialVersionUID = 3951307773685425235L;
	
	private static final int BOARD_START_X = 300;
	private static final int BOARD_START_Y = 50;

	private static final int SQUARE_WIDTH = 25;
	private static final int SQUARE_HEIGHT = 25;
	
	private static final int PIECE_WIDTH = 25;
	private static final int PIECE_HEIGHT = 25;
	
	public final int START_CUSTOM_ROW = 200 / SQUARE_HEIGHT; //unschön, wird in button und custom gui verwendet
	public final int START_CUSTOM_COLUMN = 250 / SQUARE_WIDTH;

	private static final int DRAG_TARGET_SQUARE_START_X = BOARD_START_X - (int)(SQUARE_WIDTH/2.0);
	private static final int DRAG_TARGET_SQUARE_START_Y = BOARD_START_Y - (int)(SQUARE_HEIGHT/2.0);

	private Image imgBackground;
	private JLabel lblGameState;
	private JLabel lblWidth;
	private JLabel lblHeight;
	
	//Buttons + Spinners
	private JSpinner m_numberSpinnerWidth;
	private JSpinner m_numberSpinnerHeight;
	private JButton btnChangeGameState;
	
	private ChessGame chessGame;
	private List<GuiPiece> guiPieces = new ArrayList<GuiPiece>();

	public ChessGui() {
		this.setLayout(null);

		// background
		URL urlBackgroundImg = getClass().getResource("/ch03/img/bo.png");
		this.imgBackground = new ImageIcon(urlBackgroundImg).getImage();
		
		// create chess game
		this.chessGame = new ChessGame();
		
		//wrap game pieces into their graphical representation
		for (Piece piece : this.chessGame.getPieces()) {
			createAndAddGuiPiece(piece);
		}
		

		// add listeners to enable drag and drop
		//
		PiecesDragAndDropListener listener = new PiecesDragAndDropListener(this.guiPieces,
				this);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);

		// button to change game state
		this.btnChangeGameState = new JButton("add");
		btnChangeGameState.addActionListener(new ChangeGameStateButtonActionListener(this, this.chessGame));
		btnChangeGameState.setBounds(100, 100, 80, 30);
		this.add(btnChangeGameState);

		// label to display game state
		this.lblGameState = new JLabel("test");
		lblGameState.setBounds(130, 30, 80, 30);
		lblGameState.setForeground(Color.WHITE);
		this.add(lblGameState);
		
		

		
		

		// label to display height
		this.lblHeight = new JLabel("Height:");
		lblHeight.setBounds(50, 167, 80, 30);
		lblHeight.setForeground(Color.WHITE);
		this.add(lblHeight);
		
		//spinner to choose height	    			
		SpinnerNumberModel m_numberSpinnerModelWidth = new SpinnerNumberModel(1, 1, 8, 1);
	    this.m_numberSpinnerHeight = new JSpinner(m_numberSpinnerModelWidth);
	    m_numberSpinnerHeight.setBounds(90,170,50,25);
	    m_numberSpinnerHeight.setEditor(new JSpinner.DefaultEditor(m_numberSpinnerHeight));
	    this.add(m_numberSpinnerHeight);		
		
	    // label to display Width
		this.lblWidth = new JLabel("Width:");
		lblWidth.setBounds(150, 167, 80, 30);
		lblWidth.setForeground(Color.WHITE);
		this.add(lblWidth);		
		
		//spinner to choose height
		SpinnerNumberModel m_numberSpinnerModelHeight = new SpinnerNumberModel(1, 1, 8, 1);	
	    this.m_numberSpinnerWidth = new JSpinner(m_numberSpinnerModelHeight);
	    m_numberSpinnerWidth.setBounds(192,170,50,25);
	    m_numberSpinnerWidth.setEditor(new JSpinner.DefaultEditor(m_numberSpinnerWidth));
	    this.add(m_numberSpinnerWidth);
		
		
		
		
		
		
		

		// create application frame and set visible
		//
		JFrame f = new JFrame();
		f.setSize(80, 80);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);
		f.setSize(imgBackground.getWidth(null), imgBackground.getHeight(null));
	}
	
	public List<GuiPiece> getGuiPieces(){return this.guiPieces;}

	/**
	 * @return textual description of current game state
	 */

	/**
	 * create a game piece
	 * 
	 * @param color color constant
	 * @param type type constant
	 * @param x x position of upper left corner
	 * @param y y position of upper left corner
	 */
	public void createAndAddGuiPiece(Piece piece) {

		GuiPiece guiPiece = new GuiPiece(piece, PIECE_WIDTH, PIECE_HEIGHT);
		
		piece.setWidth(PIECE_WIDTH/SQUARE_WIDTH);
		piece.setHeight(PIECE_HEIGHT/SQUARE_HEIGHT);
		
		this.guiPieces.add(guiPiece);
	}
	public void createAndAddCustomGuiPiece(Piece piece) {

		
		for (int i = guiPieces.size()-1; i >= 0; i--) {

			if(guiPieces.get(i).getX()  == BOARD_START_X - START_CUSTOM_COLUMN * SQUARE_WIDTH) //wenn custom existiert
			{
				guiPieces.remove( i );
			}
		}
		
		
		GuiPiece guiPiece = new GuiPiece(piece, (Integer) this.m_numberSpinnerWidth.getValue()*SQUARE_WIDTH, (Integer) this.m_numberSpinnerHeight.getValue()*SQUARE_HEIGHT);
		
		piece.setWidth( (Integer) this.m_numberSpinnerWidth.getValue());
		piece.setHeight( (Integer) this.m_numberSpinnerHeight.getValue());
		System.out.println(piece.getHeight());
		System.out.println(piece.getColumn());
		
		this.guiPieces.add(guiPiece);
	}



	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(this.imgBackground, 0, 0, null);
		
		g.setColor(Color.BLACK);
		g.drawRect(40, 90, 210, 120);
		
		
		//for (int i = 0; i < 50; i++) {
		//	for (int j = 0; j < 30; j++)
		//	{
		//		g.setColor(Color.WHITE);
		//		g.fillRect(BOARD_START_X + SQUARE_WIDTH*i, BOARD_START_Y + SQUARE_HEIGHT*j, SQUARE_WIDTH, SQUARE_HEIGHT);
		//		g.setColor(Color.BLACK);
		//		g.drawRect(BOARD_START_X + SQUARE_WIDTH*i, BOARD_START_Y + SQUARE_HEIGHT*j, SQUARE_WIDTH, SQUARE_HEIGHT);	
		//	}
		//}

		
		for (GuiPiece piece : this.guiPieces) {
			//g.drawImage(piece.getImage(), piece.getX(), piece.getY(), null);
			g.setColor(piece.getColor());
			g.fillRect(piece.getX(), piece.getY(), piece.getWidth(), piece.getHeight());
			g.setColor(Color.RED);
			g.drawRect(piece.getX(), piece.getY(), piece.getWidth(), piece.getHeight());

			
			

			
			
			String myString = "TEST";
			
			if (myString.length() > 5)
			{
				myString = "ID";
			}
			
			FontMetrics fm = g.getFontMetrics();
			int textCenterX = fm.stringWidth(myString) / 2;
			int textCenterY = fm.getAscent()/2;
			
			
			
			g.drawString(myString, piece.getX()+piece.getWidth()/2-textCenterX, piece.getY()+piece.getHeight()/2+textCenterY);
		}
	}

	public static void main(String[] args) {
		new ChessGui();
	}


	/**
	 * convert logical column into x coordinate
	 * @param column
	 * @return x coordinate for column
	 */
	public static int convertColumnToX(int column){
		return BOARD_START_X + SQUARE_WIDTH * column;
	}
	
	/**
	 * convert logical row into y coordinate
	 * @param row
	 * @return y coordinate for row
	 */
	public static int convertRowToY(int row){
		return BOARD_START_Y + SQUARE_HEIGHT * row;
	}
	
	/**
	 * convert x coordinate into logical column
	 * @param x
	 * @return logical column for x coordinate
	 */
	public static int convertXToColumn(int x){
		System.out.println(x);
		System.out.println(DRAG_TARGET_SQUARE_START_X);
		return (x - DRAG_TARGET_SQUARE_START_X)/SQUARE_WIDTH;
	}
	
	/**
	 * convert y coordinate into logical row
	 * @param y
	 * @return logical row for y coordinate
	 */
	public static int convertYToRow(int y){
		return (y - DRAG_TARGET_SQUARE_START_Y)/SQUARE_HEIGHT;
	}

	/**
	 * change location of given piece, if the location is valid.
	 * If the location is not valid, move the piece back to its original
	 * position.
	 * @param dragPiece
	 * @param x
	 * @param y
	 */
	public void setNewPieceLocation(GuiPiece dragPiece, int x, int y) {
		int targetRow = ChessGui.convertYToRow(y);
		int targetColumn = ChessGui.convertXToColumn(x);

		if( targetRow < Piece.ROW_1 || targetRow > 30-dragPiece.getHeight()/SQUARE_HEIGHT|| targetColumn < Piece.COLUMN_A || targetColumn > 60-dragPiece.getWidth()/SQUARE_WIDTH){
			// reset piece position if move is not valid
			dragPiece.resetToUnderlyingPiecePosition();
		
		}else{
			//change model and update gui piece afterwards
			System.out.println("moving piece to "+targetRow+"/"+targetColumn);
			this.chessGame.movePiece(
					dragPiece.getPiece().getRow(), dragPiece.getPiece().getColumn()
					, targetRow, targetColumn);
			dragPiece.resetToUnderlyingPiecePosition();
		}
	}

}
