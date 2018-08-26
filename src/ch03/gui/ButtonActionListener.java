package ch03.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch03.logic.LagerLogic;


public class ButtonActionListener implements ActionListener {

	private LagerGui lagerGui;
	private LagerLogic lagerLogic;

	public ButtonActionListener(LagerGui lagerGui, LagerLogic lagerLogic) {
		this.lagerGui = lagerGui;
		this.lagerLogic = lagerLogic;

	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		if(lagerGui.isAddButton(evt))
		{	
			int i = 0;
			int j = 0;
			boolean placeFound = false;
			
			int newPieceWidth = lagerGui.getSpinnerWidth();
			int newPieceHeight = lagerGui.getSpinnerHeight();
			
			loopstart:
			for(i = 0; i < LagerGui.BOARD_WIDTH; i++)
			{
				if (i + newPieceWidth > LagerGui.BOARD_WIDTH)
				{
					continue;
				}
				
				for(j = 0; j < LagerGui.BOARD_HEIGHT; j++)
				{
					if (j + newPieceHeight > LagerGui.BOARD_HEIGHT)
					{
						continue;
					}
					if(!this.lagerLogic.placePiece(j,i,newPieceWidth,newPieceHeight))
					{
						System.out.println("gesetzt auf " + i + "/" + j);
						placeFound = true;
						break loopstart;
					}	
				}
			}
			
			if(placeFound)
			{
				
				this.lagerGui.createAndAddCustomGuiPiece(this.lagerLogic.createAndAddPiece(0, j, i));
				this.lagerGui.repaint();
			}
			//else meldung
		}
		
		else if(lagerGui.isUploadButton(evt))
		{
			//Upload
		}
	}
}
