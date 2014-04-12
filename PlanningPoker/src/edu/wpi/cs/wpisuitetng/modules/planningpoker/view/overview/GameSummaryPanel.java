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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame.EndGameManuallyController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame.UpdateGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.CreateGameInfoPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.NewGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.PlayGamePanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * GameSummaryPanel is a class which displays the summary of a game that can be
 * chosen to edit and/or play by a user
 * 
 * @author Team Code On Bleu
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GameSummaryPanel extends JPanel {

	//private final CreateGameInfoPanel createGameInfoPanel;
	JLabel titleLabel;
	JLabel deadlineLabel;
	JLabel descriptionLabel;
	JButton playGameBtn;
	JButton editGameBtn;
	JButton endGameBtn;
	JTextArea requirementsList;
	Game game;
	JScrollPane scrollPane;
	JPanel emptyPanel1, emptyPanel2;

	public GameSummaryPanel() { 

		this.setLayout(new GridBagLayout());

		final GridBagConstraints constraints = new GridBagConstraints();

		titleLabel = new JLabel("Title");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipady = 10;
		//titleLabel.setBounds(6, 6, 295, 56);
		add(titleLabel, constraints);

		deadlineLabel = new JLabel("Deadline");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridx = 3;
		constraints.gridy = 0;
		//deadlineLabel.setBounds(313, 6, 131, 56);
		add(deadlineLabel, constraints);

		descriptionLabel = new JLabel("Description");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 6;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridx = 0;
		constraints.gridy = 1;
		//descriptionLabel.setBounds(6, 74, 438, 56);
		add(descriptionLabel, constraints);

		playGameBtn = new JButton("Play Game");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridx = 6;
		constraints.gridy = 3;
		constraints.ipady = 0;
		//playGameBtn.setBounds(327, 265, 117, 29);
		add(playGameBtn, constraints);
		playGameBtn.setEnabled(true);
		playGameBtn.setEnabled(false);

		playGameBtn.addActionListener(new ActionListener () {

			public void actionPerformed(ActionEvent e)
			{
				final MainViewTabController mvt = MainViewTabController.getInstance();
				mvt.playGameTab(game);
			}

		});

		endGameBtn = new JButton("End Game");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.ipady = 0;
		add(endGameBtn, constraints);
		endGameBtn.setVisible(false);
		endGameBtn.setEnabled(false);


		//EndGameManuallyController endGameManuallyController;

		// Maps End Game button to AddGameController class
		//endGameBtn.addActionListener(new EndGameManuallyController(createGameInfoPanel, endGameManuallyController.getUpdatedGame(), endGameManuallyController.isEndingGame()));


		editGameBtn = new JButton("Edit Game");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridx = 5;
		constraints.gridy = 3;
		//editGameBtn.setBounds(184, 265, 117, 29);
		add(editGameBtn, constraints);
		editGameBtn.setEnabled(false);

		editGameBtn.addActionListener(new ActionListener () {

			public void actionPerformed(ActionEvent e)
			{
				final MainViewTabController mvt = MainViewTabController.getInstance();
				mvt.createGameTab(game);
			}

		});

		requirementsList = new JTextArea("Requirements");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 6;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.gridx = 0;
		constraints.gridy = 2;
		//requirementsList.setBounds(6, 147, 438, 106);
		scrollPane = new JScrollPane(requirementsList);
		add(scrollPane, constraints);
		requirementsList.setEditable(false);

		emptyPanel1 = new JPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = .5;
		constraints.weighty = 0;
		constraints.gridx = 2;
		constraints.gridy = 3;
		add(emptyPanel1, constraints);

		emptyPanel2 = new JPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = .5;
		constraints.weighty = 0;
		constraints.gridx = 3;
		constraints.gridy = 3;
		add(emptyPanel2, constraints);
	}

	/** Function which takes a game as a parameter and
	 *  updates the summary of the game that is displayed
	 * 
	 * @param gme the game to update the panel information with
	 */
	public void updateSummary(Game gme)
	{
		game = gme;
		titleLabel.setText("Game Name: " + game.getName());
		deadlineLabel.setText("Deadline: " + game.getEnd().toString());
		descriptionLabel.setText("Description: " + game.getDescription());
		final String appendedReqs = GameSummaryPanel.getRequirementNames(game);
		requirementsList.setText(appendedReqs);

		// Controls whether the buttons are enabled/disabled and visible/invisible.
		// The buttons start, edit, or end the game.
		// playGameBtn, editGameBtn, and endGameBtn
		// If the user is the game creator.
		if(game.getGameCreator().equals(ConfigManager.getConfig().getUserName())) {
			// If the game is a draft.
			if(game.getStatus().equals(GameStatus.DRAFT)) {
				playGameBtn.setVisible(true);
				playGameBtn.setEnabled(false);
				editGameBtn.setVisible(true);
				editGameBtn.setEnabled(true);
				endGameBtn.setVisible(true);
				endGameBtn.setEnabled(false);
			}
			// If the game is in progress.
			else if(game.getStatus().equals(GameStatus.IN_PROGRESS)) {
				playGameBtn.setVisible(true);
				playGameBtn.setEnabled(true);
				editGameBtn.setVisible(true);
				editGameBtn.setEnabled(false);
				endGameBtn.setVisible(true);
				endGameBtn.setEnabled(true);
			}
			// If the game is ended.
			else {
				playGameBtn.setVisible(true);
				playGameBtn.setEnabled(false);
				editGameBtn.setVisible(true);
				editGameBtn.setEnabled(false);
				endGameBtn.setVisible(true);
				endGameBtn.setEnabled(false);
			}
		}
		// If the user is not the game creator.
		else {
			// Users cannot see the drafts of other users.
			// If the game is in progress.
			if(game.getStatus().equals(GameStatus.IN_PROGRESS)) {
				playGameBtn.setVisible(true);
				playGameBtn.setEnabled(true);
				editGameBtn.setVisible(false);
				editGameBtn.setEnabled(false);
				endGameBtn.setVisible(false);
				endGameBtn.setEnabled(false);
			}
			// If the game is ended.
			else {
				playGameBtn.setVisible(true);
				playGameBtn.setEnabled(false);
				editGameBtn.setVisible(false);
				editGameBtn.setEnabled(false);
				endGameBtn.setVisible(false);
				endGameBtn.setEnabled(false);
			}
		}
	}


	/** Function which returns a string with all requirement names
	 * for the given game appended (with new lines) into ones
	 * string
	 * 
	 * @param game holds the requirements that will be returned
	 * @return String of appended requirement names for the game
	 */

	private static String getRequirementNames(Game game) {
		String temp = "";

		RequirementManagerFacade.getInstance();
		final List<Requirement> reqs = RequirementManagerFacade.getInstance().getPreStoredRequirements();
		for(Requirement r : reqs)
		{
			if(game.getRequirements().contains(r.getId())){
				temp += r.getName() + "\n";
			}
		}
		return temp;
	}
}