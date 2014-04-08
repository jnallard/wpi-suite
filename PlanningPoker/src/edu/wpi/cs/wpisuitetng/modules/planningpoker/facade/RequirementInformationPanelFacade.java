/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.facade;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;


import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.UpdateRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.IterationModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.RequirementPanelListener;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.RequirementSelector;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.RequirementSelectorListener;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.ScrollablePanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.ViewMode;

/**
 * @author Code On Bleu
 * @version $Revision: 1.0 $
 */
public class RequirementInformationPanelFacade extends JScrollPane{
	


	private Requirement currentRequirement;
	

	private int storedEstimate;
	private Iteration storedIteration;
	private RequirementStatus storedStatus;
	
	private JTextField boxName;
	private JTextField boxReleaseNum;
	private JTextArea boxDescription;
	private JComboBox<Iteration> boxIteration;
	private JTextField boxChildEstimate;
	private JLabel labelChildEstimate;
	private JTextField boxTotalEstimate;
	private JLabel labelTotalEstimate;
	private final Border defaultBorder = (new JTextField()).getBorder();
	private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);

	private JLabel currentParent;
	private JButton editParent;
	private JButton removeFromParent;
	private JButton chooseParent;
	private JPanel noParentInfoPanel;
	private JComboBox<RequirementType> dropdownType;
	private JComboBox<RequirementStatus> dropdownStatus;
	private JComboBox<RequirementPriority> dropdownPriority;
	private JTextField boxEstimate;

	private RequirementStatus lastValidStatus;
	private boolean fillingFieldsForRequirement = false;
	
	private JLabel errorName;
	private JLabel errorDescription;
	private JLabel errorEstimate;

	/**
	 * Constructs the requirement information panel
	 */
	public RequirementInformationPanelFacade() {
		this.currentRequirement = new Requirement();
		this.setMinimumSize(new Dimension(500,200));
		this.buildLayout();

		clearInfo();
	}

	/**
	 * Builds the layout panel.
	 * 
	
	 */
	@SuppressWarnings("rawtypes")
	private void buildLayout() {
		ScrollablePanel contentPanel = new ScrollablePanel();
		//instantialize everything.
		JLabel labelName = new JLabel("Name *");
		JLabel labelReleaseNum = new JLabel("Release Number");
		JLabel labelDescription = new JLabel("Description *");
		JLabel labelIteration = new JLabel("Iteration");
		labelChildEstimate = new JLabel("Children Estimate");
		labelTotalEstimate = new JLabel("Total Estimate");
		JLabel labelType = new JLabel("Type");
		JLabel labelStatus = new JLabel("Status");
		JLabel labelPriority = new JLabel("Priority");
		JLabel labelEstimate = new JLabel("Estimate");
		JPanel parentInfoPanel = new JPanel();
		boxName = new JTextField();

		boxReleaseNum = (new JTextField());

		JScrollPane descrScroll = new JScrollPane();
		boxDescription = new JTextArea();
		boxDescription.setLineWrap(true);
		boxDescription.setBorder(defaultBorder);
		descrScroll.setViewportView(boxDescription);

		List<Iteration> iterations = IterationModel.getInstance().getIterations();
		Iteration[] iterationArray = new Iteration[iterations.size()];
		for (int i = 0; i < iterations.size(); i++) {
			Iteration iter = iterations.get(i);
			iterationArray[i] = iter;
		}
		boxIteration = (new JComboBox<Iteration>(iterationArray));
		boxIteration.setBackground(Color.WHITE);
		boxIteration.setMaximumSize(new Dimension(150, 25));

		errorName = (new JLabel());
		errorDescription = (new JLabel());

		dropdownType = (new JComboBox<RequirementType>(RequirementType.values()));
		dropdownType.setEditable(false);
		dropdownType.setBackground(Color.WHITE);

		dropdownStatus = (new JComboBox<RequirementStatus>(
				RequirementStatus.values()));
		dropdownStatus.setEditable(false);
		dropdownStatus.setBackground(Color.WHITE);
		// Radio buttons

		dropdownPriority = new JComboBox<RequirementPriority>(RequirementPriority.values());
		dropdownPriority.setEditable(false);
		dropdownPriority.setBackground(Color.WHITE);

		boxEstimate = (new JTextField());
		boxEstimate.setPreferredSize(new Dimension(50, 20));
		boxChildEstimate = (new JTextField());
		boxChildEstimate.setEnabled(false);
		boxTotalEstimate = new JTextField();
		boxTotalEstimate.setEnabled(false);
		errorEstimate = (new JLabel());

		boolean hasChildren = !currentRequirement.getChildren().isEmpty();
		labelChildEstimate.setVisible(hasChildren);
		boxChildEstimate.setVisible(hasChildren);

		labelTotalEstimate.setVisible(hasChildren);
		boxTotalEstimate.setVisible(hasChildren);

		currentParent = new JLabel();
		editParent = new JButton("Edit Parent");
		editParent.setAlignmentX(RIGHT_ALIGNMENT);
		editParent.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentRequirement.getParentID() != -1)
				{
					ViewEventController.getInstance().editRequirement(currentRequirement.getParent());
				}
			}		
		});
		removeFromParent = new JButton("Remove From Parent");
		removeFromParent.setAlignmentX(RIGHT_ALIGNMENT);
		removeFromParent.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				Requirement oldParent = currentRequirement.getParent();
				try {
					currentRequirement.setParentID(-1);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				UpdateRequirementController.getInstance().updateRequirement(currentRequirement);
				ViewEventController.getInstance().refreshEditRequirementPanel(currentRequirement);
				ViewEventController.getInstance().refreshEditRequirementPanel(oldParent);
				ViewEventController.getInstance().getOverviewTree().refresh();
			}	
		});


		contentPanel.add(labelName, "wrap");
		contentPanel.add(boxName, "growx, pushx, shrinkx, span, wrap");

		contentPanel.add(labelDescription, "wrap");
		contentPanel.add(descrScroll, "growx, pushx, shrinkx, span, height 200px, wmin 10, wrap");

		//setup columns.
		JPanel leftColumn = new JPanel();
		JPanel rightColumn = new JPanel();
		leftColumn.add(labelReleaseNum, "left,wrap");
		leftColumn.add(boxReleaseNum, "width 100px, left,wrap");
		leftColumn.add(labelIteration, "left,wrap");
		leftColumn.add(boxIteration, "width 100px, left,wrap");
		leftColumn.add(labelEstimate, "left,wrap");
		leftColumn.add(boxEstimate, "width 50px, left,wrap");
		leftColumn.add(labelChildEstimate, "left,wrap");
		leftColumn.add(boxChildEstimate, "width 50px, left, wrap");
		leftColumn.add(labelTotalEstimate, "left, wrap");
		leftColumn.add(boxTotalEstimate, "width 50px, left");

		rightColumn.add(labelType, "left, wrap");
		rightColumn.add(dropdownType, "left, wrap");
		rightColumn.add(labelStatus, "left, wrap");
		rightColumn.add(dropdownStatus, "left, wrap");
		rightColumn.add(labelPriority, "left, wrap");
		rightColumn.add(dropdownPriority, "left, wrap");
		rightColumn.add(currentParent,"left, wrap");
		rightColumn.add(parentInfoPanel, "left, span, wrap");
		
		contentPanel.add(leftColumn, "left, spany, growy, push");
		contentPanel.add(rightColumn, "right, spany, growy, push");
		
		fireRefresh();
		this.setViewportView(contentPanel);
	}

	/**
	 * Refreshes the information of the requirement.
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.RequirementPanelListener#fireRefresh()
	 */
	public void fireRefresh() {
		repopulateIterationInformation();
		refreshIteration();
		refreshEstimate();
		adjustFieldEnability();
	}
	
	/**
	 * Refresh the estimate if its been changed in the requirement
	 */
	private void refreshEstimate() {
		if(currentRequirement.getEstimate() != storedEstimate)
		{
			storedEstimate = currentRequirement.getEstimate();
			boxEstimate.setText(String.valueOf(storedEstimate));
		}
	}

	/**
	 * Repopulates the iteration information in case new iterations have been created.
	 */
	private void repopulateIterationInformation() {
		Iteration selected = (Iteration)boxIteration.getSelectedItem();
		List<Iteration> iterations = IterationModel.getInstance().getIterations();
		Iteration[] iterationArray = new Iteration[iterations.size()];
		for (int i = 0; i < iterations.size(); i++) {
			Iteration iter = iterations.get(i);
			iterationArray[i] = iter;
		}
		DefaultComboBoxModel<Iteration> aModel = new DefaultComboBoxModel<Iteration>(iterationArray);		
		boxIteration.setModel(aModel);
		boxIteration.setSelectedItem(selected);
	}
	
	/**
	 * Refreshes the selected iteration if its been changed in the requirement
	 */
	private void refreshIteration() {
		Iteration cur = IterationModel.getInstance().getIteration(currentRequirement.getIteration());
		if(cur != storedIteration)
		{
			storedIteration = cur;
			boxIteration.setSelectedItem(storedIteration);
		}
		
		if(storedStatus != currentRequirement.getStatus())
		{
			setStatus();
		}
	}
	



	/**
	 * Fills the fields of the edit requirement panel based on the current
	 * settings of the edited requirement.
	 */
	@SuppressWarnings("unchecked")
	private void fillFieldsForRequirement() {
		fillingFieldsForRequirement = true;
		boxName.setText(currentRequirement.getName());
		boxDescription.setText(currentRequirement.getDescription());
		storedEstimate = currentRequirement.getEstimate();
		boxEstimate.setText(
				String.valueOf(storedEstimate));
		boxReleaseNum.setText(currentRequirement.getRelease());
		storedIteration = IterationModel.getInstance().getIteration(currentRequirement.getIteration());
		boxIteration.setSelectedItem(storedIteration);


		setStatus();

		dropdownType.setSelectedItem(currentRequirement.getType());

		this.dropdownPriority.setSelectedItem(currentRequirement.getPriority());

		// reset the error messages.
		errorEstimate.setText("");
		boxEstimate.setBorder(defaultBorder);
		errorDescription.setText("");
		boxDescription.setBorder(defaultBorder);
		errorName.setText("");
		boxName.setBorder(defaultBorder);
		boxChildEstimate.setText(Integer.toString(currentRequirement.getChildEstimate()));
		boxTotalEstimate.setText(Integer.toString(currentRequirement.getTotalEstimate()));

		fireRefresh();
		fillingFieldsForRequirement = false;
		repaint();
	}

	/**
	 * Sets the status dropdown
	 */
	private void setStatus() {
		if (currentRequirement.getStatus().equals(RequirementStatus.NEW)) {
			dropdownStatus.removeAllItems();
			dropdownStatus.addItem(RequirementStatus.NEW);
			dropdownStatus.addItem(RequirementStatus.DELETED);
		} else if (currentRequirement.getStatus().equals(
				RequirementStatus.INPROGRESS)) {
			dropdownStatus.removeAllItems();
			dropdownStatus.addItem(RequirementStatus.INPROGRESS);
			dropdownStatus.addItem(RequirementStatus.COMPLETE);
		} else if (currentRequirement.getStatus()
				.equals(RequirementStatus.OPEN)) {
			dropdownStatus.removeAllItems();
			dropdownStatus.addItem(RequirementStatus.OPEN);
			dropdownStatus.addItem(RequirementStatus.DELETED);
		} else if (currentRequirement.getStatus().equals(
				RequirementStatus.COMPLETE)
				|| currentRequirement.getStatus().equals(
						RequirementStatus.DELETED)) {
			if (currentRequirement.getIteration().equals("Backlog")) {
				dropdownStatus.removeAllItems();
				dropdownStatus.addItem(RequirementStatus.OPEN);
				dropdownStatus.addItem(RequirementStatus.COMPLETE);
				dropdownStatus.addItem(RequirementStatus.DELETED);
			} else {
				dropdownStatus.removeAllItems();
				dropdownStatus.addItem(RequirementStatus.INPROGRESS);
				dropdownStatus.addItem(RequirementStatus.COMPLETE);
				dropdownStatus.addItem(RequirementStatus.DELETED);
			}
		}
		storedStatus = currentRequirement.getStatus();
		dropdownStatus.setSelectedItem(storedStatus);
		lastValidStatus = currentRequirement.getStatus();		
	}

	/**
	 * Validates the values of the fields in the requirement panel to ensure
	 * they are valid
	 * @param warn whether to warn the user or not
	
	 * @return whether fields are valid. */
	public boolean validateFields(boolean warn) {
		boolean isNameValid;
		boolean isDescriptionValid;
		boolean isEstimateValid;
		

		if (getBoxName().getText().length() >= 100) {
			isNameValid = false;
			getErrorName().setText("No more than 100 chars");
			getBoxName().setBorder(errorBorder);
			getErrorName().setForeground(Color.RED);
		} else if (getBoxName().getText().trim().length() <= 0) {
			isNameValid = false;
			if(warn)
			{
				getErrorName().setText("** Name is REQUIRED");
				getBoxName().setBorder(errorBorder);
				getErrorName().setForeground(Color.RED);
			}
		} else {
			if(warn)
			{
				getErrorName().setText("");
				getBoxName().setBorder(defaultBorder);
			}
			isNameValid = true;

		}
		if (getBoxDescription().getText().trim().length() <= 0) {
			isDescriptionValid = false;
			if(warn)
			{
				getErrorDescription().setText("** Description is REQUIRED");
				getErrorDescription().setForeground(Color.RED);
				getBoxDescription().setBorder(errorBorder);
			}

		} else {
			if(warn)
			{
				getErrorDescription().setText("");
				getBoxDescription().setBorder(defaultBorder);
			}
			isDescriptionValid = true;
		}

		if (getBoxEstimate().getText().trim().length() <= 0) {
			getBoxEstimate().setText("");
			getErrorEstimate().setText("");
			getBoxEstimate().setBorder(defaultBorder);
			isEstimateValid = true;
		} else if (!(isInteger(getBoxEstimate().getText()))) {
			getErrorEstimate()
			.setText("Estimate must be non-negative integer");
			getBoxEstimate().setBorder(errorBorder);
			getBoxEstimate().setBorder((new JTextField()).getBorder());
			getErrorEstimate().setForeground(Color.RED);
			isEstimateValid = false;
		} else if (Integer.parseInt(getBoxEstimate().getText()) < 0) {
			getErrorEstimate()
			.setText("Estimate must be non-negative integer");
			getBoxEstimate().setBorder(errorBorder);
			getErrorEstimate().setForeground(Color.RED);
			isEstimateValid = false;
		} else if (((Integer.parseInt(getBoxEstimate().getText()) == 0) || (getBoxEstimate().getText().trim().length() == 0))
				&& !(getBoxIteration().getSelectedItem().equals(IterationModel.getInstance().getBacklog()))) {
			getErrorEstimate()
			.setText(
					"Cannot have an estimate of 0 and be assigned to an iteration.");
			getBoxEstimate().setBorder(errorBorder);
			getErrorEstimate().setForeground(Color.RED);
			isEstimateValid = false;
		} else {
			getErrorEstimate().setText("");
			getBoxEstimate().setBorder(defaultBorder);
			isEstimateValid = true;
		}
		return isNameValid && isDescriptionValid && isEstimateValid;
	}

	/**
	 * Resets the information back to default
	 */
	public void clearInfo()
	{
		this.fillFieldsForRequirement(); //if editing, revert back to old info

		//no changes have been made, so let the parent know.
		
	}



	/**
	 * Updates the requirement based on whether it is being created or not
	 * @param wasCreated whether the requirement is being created or edited.
	 */
	private void updateRequirement(boolean wasCreated) {
		if(wasCreated) currentRequirement.setId(RequirementModel.getInstance().getNextID());
		currentRequirement.setWasCreated(wasCreated);
		
		// Extract the name, release number, and description from the GUI fields
		String stringName = this.getBoxName().getText();
		String stringReleaseNum = this.getBoxReleaseNum().getText();
		String stringDescription = this.getBoxDescription().getText();
		String stringEstimate = this.getBoxEstimate().getText();
		String stringIteration = (String) this.getBoxIteration().getSelectedItem().toString();

		if (stringIteration.trim().equals(""))
			stringIteration = "Backlog";

		RequirementPriority priority;
		RequirementStatus status = (RequirementStatus) this.getDropdownStatus().getSelectedItem();
		RequirementType type = (RequirementType) getDropdownType()
				.getSelectedItem();

		int estimate = stringEstimate.trim().length() == 0 ? 0 : Integer
				.parseInt(stringEstimate);


		currentRequirement.setName(stringName);
		currentRequirement.setRelease(stringReleaseNum);
		currentRequirement.setDescription(stringDescription);
		currentRequirement.setStatus(status);
		currentRequirement.setPriority((RequirementPriority)dropdownPriority.getSelectedItem());
		currentRequirement.setEstimate(estimate);
		currentRequirement.setIteration(stringIteration);
		currentRequirement.setType(type);
		if(wasCreated)
		{
			// Set the time stamp for the transaction for the creation of the requirement
			currentRequirement.getHistory().setTimestamp(System.currentTimeMillis());
			System.out.println("The Time Stamp is now :" + currentRequirement.getHistory().getTimestamp());
			currentRequirement.getHistory().add("Requirement created");

			RequirementModel.getInstance().addRequirement(currentRequirement);
		}

		UpdateRequirementController.getInstance().updateRequirement(
				currentRequirement);

		ViewEventController.getInstance().refreshTable();
		ViewEventController.getInstance().refreshTree();

		if(currentRequirement.getParentID() != -1)
		{
			ViewEventController.getInstance().refreshEditRequirementPanel(currentRequirement.getParent());
		}
	}
	
	/**
	 * Enables or disables components as needed
	 */
	private void adjustFieldEnability()
	{
		boolean allDisabled = getDropdownStatus().getSelectedItem() == RequirementStatus.DELETED;
		allDisabled |= getDropdownStatus().getSelectedItem() == RequirementStatus.COMPLETE;
		boolean inProgress = getDropdownStatus().getSelectedItem() == RequirementStatus.INPROGRESS;
		boolean validEstimate = false;
		
		boolean allChildrenDeleted = true;
		for(Requirement child : currentRequirement.getChildren())
		{
			allChildrenDeleted &= child.getStatus() == RequirementStatus.DELETED;
		}

		try
		{
			Integer estimate = new Integer(getBoxEstimate().getText().trim());
			
			validEstimate = estimate > 0;
		}
		catch(Exception e)
		{
			validEstimate = false;
		}
		
		this.getBoxName().setEnabled(!allDisabled);
		this.getBoxDescription().setEnabled(!allDisabled);
		this.getBoxEstimate().setEnabled(!inProgress && !allDisabled);
		this.getBoxReleaseNum().setEnabled(!allDisabled);
		this.getDropdownType().setEnabled(!allDisabled);
		this.getBoxIteration().setEnabled(validEstimate && !allDisabled);
		this.dropdownPriority.setEnabled(!allDisabled);
	}

	/**
	
	 * @return Returns whether any field in the panel has been changed */
	public boolean anythingChanged()
	{
		return anythingChangedCreating();
		
	}

	/**
	
	 * @return Returns whether any fields in the panel have been changed  */
	private boolean anythingChangedCreating() {
		// Check if the user has changed the name
		if (!(getBoxName().getText().equals(""))){
			return true;}
		// Check if the user has changed the description
		if (!(getBoxDescription().getText().equals(""))){
			return true;}
		// Check if the user has changed the release number
		if (!(getBoxReleaseNum().getText().equals(""))){
			return true;}
		// Check if the user has changed the iteration number
		if (!getBoxIteration().getSelectedItem().equals(IterationModel.getInstance().getBacklog())){
			return true;}
		// Check if the user has changed the type
		if (!(((RequirementType)getDropdownType().getSelectedItem()) == RequirementType.BLANK)){
			return true;}
		// Check if the user has changed the estimate
		if (!(getBoxEstimate().getText().trim().equals("") || getBoxEstimate().getText().trim().equals("0"))){
			return true;}

		if (dropdownPriority.getSelectedItem() != RequirementPriority.BLANK)
		{
			return true;
		}

		return false;
	}

	/**
	
	 * @return whether any fields have been changed. */
	private boolean anythingChangedEditing() {
		// Check if the user has changed the name
		if (!(getBoxName().getText().equals(currentRequirement.getName()))){
			return true;}
		// Check if the user has changed the description
		if (!(getBoxDescription().getText().equals(currentRequirement.getDescription()))){
			return true;}
		// Check if the user has changed the release number
		if (!(getBoxReleaseNum().getText().equals(currentRequirement.getRelease()))){
			return true;}
		// Check if the user has changed the iteration number
		if (!(getBoxIteration().getSelectedItem().toString().equals(currentRequirement.getIteration()))){
			return true;}
		// Check if the user has changed the type
		if (!(((RequirementType)getDropdownType().getSelectedItem()) == currentRequirement.getType())){
			return true;}
		// Check if the user has changed the status
		if (!(((RequirementStatus)getDropdownStatus().getSelectedItem()) == currentRequirement.getStatus())){
			return true;}
		// Check if the user has changed the estimate
		if (!(getBoxEstimate().getText().trim().equals(String.valueOf(currentRequirement.getEstimate())))){
			return true;}

		RequirementPriority reqPriority = currentRequirement.getPriority();
		
		if (reqPriority != dropdownPriority.getSelectedItem())
		{
			return true;
		}

		return false;
	}

	/**
	 * Returns whether the panel is ready to be removed or not based on if there are changes that haven't been
	 * saved.
	 * 
	
	
	
	 * @return whether the panel can be removed. * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.RequirementPanelListener#readyToRemove() * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.RequirementPanelListener#readyToRemove() * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.RequirementPanelListener#readyToRemove()
	 */
	public boolean readyToRemove()
	{	
		return !anythingChanged();
	}

	


	/**
	 * Checks if the input string is an integer
	 * 
	 * @param input
	 *            the string to test
	
	 * @return true if input is an integer, false otherwise */
	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Returns the error name label
	
	 * @return error name label */
	public JLabel getErrorName() {
		return errorName;
	}

	/**
	
	 * @return the error description label */
	public JLabel getErrorDescription() {
		return errorDescription;
	}

	/**
	 * 
	
	 * @return the error estimate label */
	public JLabel getErrorEstimate() {
		return errorEstimate;
	}

	/**
	 * 
	
	 * @return box name */
	public JTextField getBoxName() {
		return boxName;
	}

	/**
	 * 
	
	 * @return box release num */
	public JTextField getBoxReleaseNum() {
		return boxReleaseNum;
	}

	/**
	 * 
	
	 * @return box description */
	public JTextArea getBoxDescription() {
		return boxDescription;
	}

	/**
	 * 
	
	 * @return box iteration */
	public JComboBox getBoxIteration() {
		return boxIteration;
	}

	/**
	 * 
	
	 * @return box child estimate */
	public JTextField getBoxChildEstimate() {
		return boxChildEstimate;
	}

	/**
	 * 
	
	 * @return box total estimate */
	public JTextField getBoxTotalEstimate() {
		return boxTotalEstimate;
	}

	/**
	 * 
	
	 * @return type dropdown */
	public JComboBox<RequirementType> getDropdownType() {
		return dropdownType;
	}

	/**
	 * 
	
	 * @return status dropdown */
	public JComboBox<RequirementStatus> getDropdownStatus() {
		return dropdownStatus;
	}

	/**
	 * 
	
	 * @return estimate box */
	public JTextField getBoxEstimate() {
		return boxEstimate;
	}


}
