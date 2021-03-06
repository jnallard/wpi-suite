/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Code On Bleu
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;


/**
 * The panel containing the information for the game
 * @author Codon Bleu
 * @version 1.0
 *
 */
public class GameInfoPanel extends JPanel {
	JLabel titleLabel;
	JLabel numDoneLabel;
	JLabel descriptionLabel;
	
	
	public GameInfoPanel() {
		setLayout(null);
		
		titleLabel = new JLabel("Game Title");
		titleLabel.setBounds(0, 0, 225, 140);
		add(titleLabel);
		
		numDoneLabel = new JLabel("Number Completed/Total Number");
		numDoneLabel.setBounds(0, 160, 225, 140);
		add(numDoneLabel);
		
		descriptionLabel = new JLabel("Description");
		descriptionLabel.setBounds(235, 11, 205, 278);
		add(descriptionLabel);
	}
	
	/**
	 * Update the panel with the information of the game passed
	 * @param game to get information from
	 */
	public void updatePanel(Game game)
	{
		titleLabel.setText(game.getName());
		descriptionLabel.setText(game.getDescription());
		numDoneLabel.setText(GameInfoPanel.numDone(game));
	}

	private static String numDone(Game game) {
		final String temp;
		int count = 0;
		
		for(Estimate e: game.getEstimates())
		{
			ConfigManager.getInstance();
			if(e.hasMadeAnEstimation(ConfigManager.getConfig().getUserName())) {
				count++;
			}
		}
		
		temp = count + "/" + game.getEstimates().size();
		
		return temp;
	}
}
