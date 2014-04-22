/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.playgame.PlayGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.results.ViewResultsController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;

/**
 * This class is used to create a requirements tree which will be displayed in the view results panel
 * 
 * @author Codon Bleu
 * @version 1.0
 */
public class ListEstimatedRequirementsPanel extends JScrollPane
implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	private JTree tree;
	private final Game game;
	private PlayGameController playGameController;
	private ViewResultsController controller;
	private JButton sendSelectedRequirement;
	/**
	 * Constructs the panel
	 * @param game Taken in to get all requirements for the game
	 */
	public ListEstimatedRequirementsPanel(final Game game, ViewResultsController controller) {

		this.game = game;
		this.controller = controller;
		this.setViewportView(tree);
		this.refresh();  

		//Create the nodes.
		this.addComponentListener(new ComponentListener()
		{

			@Override
			public void componentResized(ComponentEvent e) {

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentShown(ComponentEvent e) {
				refresh();
			}

			@Override
			public void componentHidden(ComponentEvent e) {

			}
		});
	}

	/** Required by TreeSelectionListener interface. */
	public void valueChanged(TreeSelectionEvent e) {
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				tree.getLastSelectedPathComponent();
		
		if (node == null) return;
		
		if(node.isRoot()){
			refresh();
		}
		final Object nodeInfo = node.getUserObject();
		if(node.isLeaf()){
			if(nodeInfo instanceof Requirement){
				final Requirement req = (Requirement)nodeInfo;
				controller.updateResultsInfo(req.getId());
			}
		}
	}


 /**
  * This method is used to refresh the requirements tree
  */
	public void refresh(){

		final DefaultMutableTreeNode top =
				new DefaultMutableTreeNode("Requirements"); //makes a starting node
		final List<Requirement> requirements =
				RequirementManagerFacade.getInstance().getPreStoredRequirements();
		DefaultMutableTreeNode reqNode = null;

		DefaultMutableTreeNode notSelectedCategory = null;
		DefaultMutableTreeNode selectedCategory = null;
		DefaultMutableTreeNode sentCategory = null;
		
		
		notSelectedCategory = new DefaultMutableTreeNode("Estimate not selected");
		selectedCategory = new DefaultMutableTreeNode("Estimate selected");
		sentCategory = new DefaultMutableTreeNode("Estimate sent");
		
		String user = ConfigManager.getInstance().getConfig().getUserName();
		for(Requirement req: requirements){

			// add new node to requirement tree
			if(game.getRequirements().contains(req.getId())){
				for(Estimate e : game.getEstimates())
				{
					if(e.getReqID() == req.getId()){
						if(!e.estimationHasBeenSent() && e.getFinalEstimate() == 0){
							reqNode = new DefaultMutableTreeNode(req);
							notSelectedCategory.add(reqNode);
						}
						else if(!e.estimationHasBeenSent() && e.getFinalEstimate() != 0){
							System.out.println("---------add element to selected");
							reqNode = new DefaultMutableTreeNode(req);
							selectedCategory.add(reqNode);
						}
						else{ // estimation is sent
							reqNode = new DefaultMutableTreeNode(req);
							sentCategory.add(reqNode);
							System.out.println("-----------add element to sent");
						}
					}
				}
			
			}
		}
		
		
		

		top.add(notSelectedCategory);
		top.add(selectedCategory);
		top.add(sentCategory);

		tree = new JTree(top); //create the tree with the top node as the top
		for(int i = 0; i < tree.getRowCount(); i++) tree.expandRow(i);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
						//tell it that it can only select one thing at a time
		tree.setToggleClickCount(0);

		tree.setCellRenderer(new CustomTreeCellRenderer());
						//set to custom cell renderer so that icons make sense
		tree.addTreeSelectionListener(this);
		
		

		tree.setDragEnabled(true);
		tree.setDropMode(DropMode.ON);

		this.setViewportView(tree); //make panel display the tree

		System.out.println("finished refreshing the tree");
	}
	
	public ArrayList<Estimate> getSelectedEstimates(){
		ArrayList<Estimate> estimates = new ArrayList<Estimate>();
		for(Estimate e: game.getEstimates()){
			if(!e.estimationHasBeenSent() && e.getFinalEstimate() != 0){
				estimates.add(e);
				
			}
		}
		return estimates;
	}
	
	
}