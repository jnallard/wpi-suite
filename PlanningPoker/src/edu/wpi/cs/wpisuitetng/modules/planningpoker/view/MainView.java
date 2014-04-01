/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.network.Network;

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

	/** The overview panel */
	private final OverviewPanel overviewPanel;

	/**
	 * Construct the panel.
	 * @param boardModel 
	 */
	public MainView(PlanningPokerModel gamesModel) {
		// Add the board panel to this view

		newGamePanel = new NewGamePanel(gamesModel);
		overviewPanel = new OverviewPanel(gamesModel, this);
		this.addTab("Overview Tab", overviewPanel);
		this.addTab("New Game Tab", newGamePanel);

		// Creates an ActionListener to be used by the timer, that will only run if
		// the user is signed in (network configuration is not null)
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if(Network.getInstance().getDefaultNetworkConfiguration() != null){
						overviewPanel.getGamesFromServer();
						overviewPanel.updateTable();
					}
				}
				catch (RuntimeException runtimeException){
				}
			}
		};

		// Timer will update the table every 5 seconds
		Timer timer = new Timer(5000, actionListener);
		timer.start();

	}

	public OverviewPanel getOverviewPanel(){
		return overviewPanel;
	}
}
