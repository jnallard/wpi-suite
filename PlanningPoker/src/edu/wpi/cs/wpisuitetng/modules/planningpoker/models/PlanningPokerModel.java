/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.network.Network;


/**
 * This is a model for the planning poker module. This model
 * contains a list of games. It extends AbstractListModel so that
 * it can provide the model data to the JList component in the BoardPanel.
 *
 * @author Robert, yyan
 * @version Mar 25, 2014
 */
public class PlanningPokerModel extends AbstractListModel<Game> {

	// the list of all games this user could access
	private List<Game> games;
	//the next available ID number for the game to be added
	private int nextID;

	//the static object allow the planning poker model to become a singleton
	private static PlanningPokerModel instance;

	public PlanningPokerModel() {
		games = new ArrayList<Game>();
		nextID = 1;

//		// Creates an ActionListener to be used by the timer to update requirements every few seconds
//		ActionListener actionListener = new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				try{
//					if(Network.getInstance().getDefaultNetworkConfiguration() != null){
//						games = getGames();
//					}
//				}
//
//				catch(RuntimeException exception){
//				}
//			}
//		};
//		// Timer will update the requirements every 2 seconds
//		Timer timer = new Timer(2000, actionListener);
//		timer.start();
	}

	/**
	 * Adds a game to the list
	 *
	 * @param newGame the new game to be added to the list
	 */
	public void AddGame(Game newGame) {
		newGame.setId(nextID++);
		games.add(newGame);
		// TODO: controller.getInstance().refreshTable()/addRequirement
	}


	/**
	 * Return the game with the given id
	 * 
	 * @param id of the game wanted
	 * 
	 * @return Game which has the given id, or null if no game matches this id
	 * 
	 */
	public Game getGame(int id){
		Game temp = null;
		for(int i = 0;i < games.size();i++){
			temp = games.get(i);
			if(temp.getID()==id){
				break;
			}
		}
		return temp;
	}


	/**
	 * Return all games stored in this model
	 * @return all games in list
	 */
	public List<Game> getAllGames(){
		return games;
	}



	/**
	 * Returns the length of the list of games
	 * 
	 * @return length of games list
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return games.size();
	}

	public List<Game> getGames()
	{
		return games;
	}

	/**
	 * Returns the game at the index. This
	 * implementation indexes in the reverse
	 * order.
	 * @param index the index in the list
	 * 
	 * @return the desired game
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Game getElementAt(int index) {
		return games.get(games.size() - 1 - index);
	}

	public static PlanningPokerModel getInstance() {
		if(instance == null)
		{
			instance = new PlanningPokerModel();
		}

		return instance;
	}


	/**
	 * Adds each game in the database to the PlanningPokerModel
	 * @param games is the array of games that the database currently holds
	 */
	public void addAllGames(Game[] games) {
		for(Game game: games)
		{
			this.AddGame(game);
		}
	}

	/**
	 * Removes all elements from the PlanningPokerModel
	 */
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<Game> iterator = games.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));		
		this.setId(0);
	}

	/**
	 * Set nextID to the passed value
	 * @param i the value to set nextID to
	 */
	private void setId(int i) {
		nextID = i;
	}

}
