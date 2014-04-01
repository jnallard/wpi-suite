/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Team Codon Bleu
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;

/**
 * This panel fills the main content area of the tab for this module. It
 * contains one inner JPanel, the BoardPanel.
 * 
 * @author Joshua Allard
 *
 */
@SuppressWarnings("serial")
public class MainView extends JTabbedPane {

	/** The panel containing the new game creator */
	private final NewGamePanel newGamePanel;
	private Boolean newGamePanelVisible = false;
	
	/** The overview panel */
	private final OverviewPanel overviewPanel;
	
	/**
	 * Construct the panel.
	 * @param boardModel 
	 */
	public MainView(PlanningPokerModel gamesModel) {
		// Add the board panel to this view
		newGamePanel = new NewGamePanel(gamesModel);
		newGamePanel.setBounds(0,0,500,500);
		overviewPanel = new OverviewPanel(gamesModel);
		overviewPanel.setBounds(0, 0, 500, 500);
		this.addTab("Overview Tab", overviewPanel);
	}
	
	/**
	 * Adds a new game panel if it is not
	 * already there
	 */
	public void createNewGameTab()
	{
		if(!newGamePanelVisible)
		{
			this.addTab("New Game Tab", newGamePanel);
			this.newGamePanelVisible = true;
		}
	}
}
