package ch03.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import ch03.logic.LagerLogic;
import ch03.logic.Piece;

/**
 * all x and y coordinates point to the upper left position of a component all
 * lists are treated as 0 being the bottom and size-1 being the top piece
 * 
 */
public class LagerGui extends JPanel {
	
	private static final long serialVersionUID = 3951307773685425235L;
	
	private static final int BOARD_START_X = 50;
	private static final int BOARD_START_Y = 155;

	private static final int SQUARE_WIDTH = 20;
	private static final int SQUARE_HEIGHT = 20;
	
	private static final int PIECE_WIDTH = 20;
	private static final int PIECE_HEIGHT = 20;
	
	public static final int BOARD_WIDTH = 80;
	public static final int BOARD_HEIGHT = 40;

	private static final int DRAG_TARGET_SQUARE_START_X = BOARD_START_X - (int)(SQUARE_WIDTH/2.0);
	private static final int DRAG_TARGET_SQUARE_START_Y = BOARD_START_Y - (int)(SQUARE_HEIGHT/2.0);


	private JLabel lblWidth;
	private JLabel lblHeight;
	
	private JLabel lblStoreUnit;
	private JLabel lblCarrier;
	
	//Buttons + Spinners
	private JSpinner m_numberSpinnerWidth;
	private JSpinner m_numberSpinnerHeight;
	private JButton btnAdd;
	private JButton btnUpload;
	
	private JComboBox<String> cbStoreUnit;
	private JComboBox<String> cbCarrier;
	
	private LagerLogic lagerLogic;
	private List<GuiPiece> guiPieces = new ArrayList<GuiPiece>();

	public LagerGui() {
		this.setLayout(null);

		this.setBackground(Color.ORANGE);
		
		// create chess game
		this.lagerLogic = new LagerLogic();
		
		//wrap game pieces into their graphical representation
		for (Piece piece : this.lagerLogic.getPieces()) {
			createAndAddGuiPiece(piece);
		}
		

		// add listeners to enable drag and drop
		//
		PiecesDragAndDropListener listener = new PiecesDragAndDropListener(this.guiPieces,
				this);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		
		ActionListener btnActionListener = new ButtonActionListener(this, this.lagerLogic);

		// button to change game state
		this.btnAdd = new JButton("Add");
		btnAdd.addActionListener(btnActionListener);
		btnAdd.setBounds(100, 20, 80, 30);
		this.add(btnAdd);
		
		// button to change game state
		this.btnUpload = new JButton("Upload to TDM");
		btnUpload.addActionListener(btnActionListener);
		btnUpload.setBounds(1000, 20, 150, 30);
		this.add(btnUpload);

		// label to display height
		this.lblHeight = new JLabel("Height:");
		lblHeight.setBounds(150, 67, 80, 30);
		lblHeight.setForeground(Color.BLACK);
		this.add(lblHeight);
		
		//spinner to choose height	    			
		SpinnerNumberModel m_numberSpinnerModelHeight = new SpinnerNumberModel(1, 1, BOARD_HEIGHT, 1);
	    this.m_numberSpinnerHeight = new JSpinner(m_numberSpinnerModelHeight);
	    m_numberSpinnerHeight.setBounds(192,70,50,25);
	    m_numberSpinnerHeight.setEditor(new JSpinner.DefaultEditor(m_numberSpinnerHeight));
	    this.add(m_numberSpinnerHeight);		
		
	    // label to display Width
		this.lblWidth = new JLabel("Width:");
		lblWidth.setBounds(50, 67, 80, 30);
		lblWidth.setForeground(Color.BLACK);
		this.add(lblWidth);		
		
		//spinner to choose height
		SpinnerNumberModel m_numberSpinnerModelWidth = new SpinnerNumberModel(1, 1, BOARD_WIDTH, 1);	
	    this.m_numberSpinnerWidth = new JSpinner(m_numberSpinnerModelWidth);
	    m_numberSpinnerWidth.setBounds(90,70,50,25);
	    m_numberSpinnerWidth.setEditor(new JSpinner.DefaultEditor(m_numberSpinnerWidth));
	    this.add(m_numberSpinnerWidth);

	    // label to display Store Unit
		this.lblStoreUnit = new JLabel("Store Unit:");
		lblStoreUnit.setBounds(435, 19, 80, 30);
		lblStoreUnit.setForeground(Color.BLACK);
		this.add(lblStoreUnit);
		
	    //ComboBox store units
	    String[] storeUnits = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };
	    this.cbStoreUnit = new JComboBox<>(storeUnits);
	    cbStoreUnit.setSelectedIndex(0);
	    cbStoreUnit.addItemListener(new ItemChangeListener());
	    cbStoreUnit.setBounds(500, 20, 150, 30);
	    this.add(cbStoreUnit);
	    
	    // label to display Carrier
		this.lblCarrier = new JLabel("Carrier:");
		lblCarrier.setBounds(450, 69, 80, 30);
		lblCarrier.setForeground(Color.BLACK);
		this.add(lblCarrier);	
		
	    //ComboBox carriers
	    String[] carriers = { "1", "2", "3", "4", "5" };
	    this.cbCarrier = new JComboBox<>(carriers);
	    cbCarrier.setSelectedIndex(0);
	    cbCarrier.addItemListener(new ItemChangeListener());
	    cbCarrier.setBounds(500, 70, 150, 30);
	    this.add(cbCarrier);
				
		
		

		// create application frame and set visible
		//
		JFrame f = new JFrame();
		f.setSize(80, 80);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);

		f.setSize(1800, 1000);
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

			
		GuiPiece guiPiece = new GuiPiece(piece, (Integer) this.m_numberSpinnerWidth.getValue()*SQUARE_WIDTH, (Integer) this.m_numberSpinnerHeight.getValue()*SQUARE_HEIGHT);
		
		piece.setWidth( (Integer) this.m_numberSpinnerWidth.getValue());
		piece.setHeight( (Integer) this.m_numberSpinnerHeight.getValue());
		
		this.guiPieces.add(guiPiece);
	}

	

	


	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawRect(40, 10, 210, 100);
		
		
		for (int i = 0; i < BOARD_WIDTH; i++) {
			for (int j = 0; j < BOARD_HEIGHT; j++)
			{
				g.setColor(Color.WHITE);
				g.fillRect(BOARD_START_X + SQUARE_WIDTH*i, BOARD_START_Y + SQUARE_HEIGHT*j, SQUARE_WIDTH, SQUARE_HEIGHT);
				g.setColor(Color.BLACK);
				g.drawRect(BOARD_START_X + SQUARE_WIDTH*i, BOARD_START_Y + SQUARE_HEIGHT*j, SQUARE_WIDTH, SQUARE_HEIGHT);	
			}
		}

		
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
		new LagerGui();
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
		int targetRow = LagerGui.convertYToRow(y);
		int targetColumn = LagerGui.convertXToColumn(x);

		if( targetRow < Piece.ROW_1 || targetRow > BOARD_HEIGHT-dragPiece.getHeight()/SQUARE_HEIGHT|| targetColumn < Piece.COLUMN_A || targetColumn > BOARD_WIDTH-dragPiece.getWidth()/SQUARE_WIDTH){
			// reset piece position if move is not valid
			dragPiece.resetToUnderlyingPiecePosition();
		
		}else{
			//change model and update gui piece afterwards
			System.out.println("moving piece to "+targetRow+"/"+targetColumn);
			this.lagerLogic.movePiece(
					dragPiece.getPiece().getRow(), dragPiece.getPiece().getColumn()
					, targetRow, targetColumn);
			dragPiece.resetToUnderlyingPiecePosition();
		}
	}
	
	public int getSpinnerWidth()
	{
		return (Integer) this.m_numberSpinnerWidth.getValue();
	}
	
	public int getSpinnerHeight()
	{
		return (Integer) this.m_numberSpinnerHeight.getValue();
	}
	
	boolean isAddButton(ActionEvent evt) {
	    return evt.getSource() == btnAdd;
	}
	
	boolean isUploadButton(ActionEvent evt) {
	    return evt.getSource() == btnUpload;
	}

}
