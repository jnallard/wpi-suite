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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.JPlaceholderTextField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.ChangeDeadline;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CloseNewGameTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;

import javax.swing.JCheckBox;
import javax.swing.border.Border;




/**
 * This class is a JPanel. It contains all the fields needed to 
 * create a new game
 * 
 * @author Code On Bleu
 *
 */
@SuppressWarnings({"serial"})
public class CreateGameInfoPanel extends JPanel {
	private MainViewTabController mainViewTabController;
	private NewGamePanel parent;
	
	private JLabel lblName;
	private JTextField gameNameText;
	private JTextArea description;
	private JLabel lblDeadline;
	private JDatePickerImpl datePicker;
	private JLabel lblTime;
	private JComboBox hourSelector;
	private JComboBox minuteSelector;
	private JRadioButton rdbtnAm;
	private JRadioButton rdbtnPm;
	private ButtonGroup AMPMSelection;
	private JLabel lblDeck;
	private JComboBox deck;
	private JButton btnNewButton;
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnStart;
	private JCheckBox chckbxDeadline;
	private JLabel lblMessage;
	private Game editingGame;
	


	public CreateGameInfoPanel(NewGamePanel parent) {
		this.mainViewTabController = MainViewTabController.getInstance();
		this.parent = parent;
		setBounds(5,5,307,393);
		setLayout(null);
		
		
		//Adds the fields and button to the main panel.
		gameNameText = new JTextField();
		gameNameText.setBounds(119, 56, 130, 23);
		add(gameNameText);
		
		description = new JTextArea();
		description.setLineWrap(true);
		description.setBounds(35, 284, 233, 76);
		//description.
		add(description);

		
		JLabel lblNewLabel = new JLabel("Game Information");
		lblNewLabel.setBounds(46, 11, 210, 33);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		add(lblNewLabel);
		

		
		lblName = new JLabel("Name:");
		lblName.setBounds(35, 58, 86, 14);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(lblName);
		
		
		lblDeadline = new JLabel("Deadline:");
		lblDeadline.setBounds(35, 126, 86, 20);
		lblDeadline.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(lblDeadline);
		
		
		
		// creates a date picker and sets its position
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		datePicker.setBounds(119, 126, 130, 30);
		add(datePicker);
		
		
		
		String[] hours = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		hourSelector = new JComboBox(hours);
		hourSelector.setBounds(119, 157, 52, 20);
		hourSelector.setFont(new Font("Tahoma", Font.PLAIN, 13));
	
		add(hourSelector);
		
		String[] minutes = {"00","15","30","45"};

		minuteSelector = new JComboBox(minutes);
		minuteSelector.setBounds(196, 157, 53, 20);
		minuteSelector.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(minuteSelector);
		
		lblTime = new JLabel("Time:");
		lblTime.setBounds(35, 157, 86, 20);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(lblTime);
		
		
		
		AMPMSelection = new ButtonGroup();
		
		rdbtnAm = new JRadioButton("AM");
		rdbtnAm.setBounds(119, 188, 46, 23);
		rdbtnAm.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnAm.setSelected(true);
		
		rdbtnPm = new JRadioButton("PM");
		rdbtnPm.setBounds(163, 188, 46, 23);
		rdbtnPm.setFont(new Font("Tahoma", Font.PLAIN, 13));


		AMPMSelection.add(rdbtnAm);
		AMPMSelection.add(rdbtnPm);
		
		add(rdbtnAm);
		add(rdbtnPm);
		
		lblDeck = new JLabel("Deck:");
		lblDeck.setBounds(35, 222, 86, 20);
		lblDeck.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(lblDeck);
		
		String[] decks = {"default"};
		deck = new JComboBox(decks);
		deck.setBounds(119, 222, 130, 20);
		deck.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(deck);
		
		btnNewButton = new JButton("Add new deck");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(119, 250, 130, 23);
		//add(btnNewButton);
		

		chckbxDeadline = new JCheckBox("Deadline?");
		chckbxDeadline.setBounds(36, 95, 129, 23);
		chckbxDeadline.addActionListener(new ChangeDeadline(this));
		chckbxDeadline.setSelected(true);
		add(chckbxDeadline);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblDescription.setBounds(35, 264, 136, 15);
		add(lblDescription);
		
		this.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e){
				resizeDescription();
			}
			public void componentHidden(ComponentEvent e){
				resizeDescription();
			}
			public void componentShown(ComponentEvent e){
				resizeDescription();
			}
			public void componentMoved(ComponentEvent e){
				resizeDescription();
			}
		});

		


	}

	

	public CreateGameInfoPanel(NewGamePanel parent, Game passedInGame) {
		this.mainViewTabController = MainViewTabController.getInstance();
		this.parent = parent;
		this.editingGame = passedInGame;
		setBounds(5,5,307,393);
		setLayout(null);
		
		
		//Adds the fields and button to the main panel.
		gameNameText = new JTextField();
		gameNameText.setBounds(119, 56, 130, 23);
		gameNameText.setText(editingGame.getName());
		add(gameNameText);
		
		description = new JTextArea();
		description.setLineWrap(true);
		description.setBounds(35, 284, 233, 76);
		description.setText(editingGame.getDescription());
		//description.
		add(description);

		
		JLabel lblNewLabel = new JLabel("Game Information");
		lblNewLabel.setBounds(46, 11, 210, 33);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		add(lblNewLabel);
		

		
		lblName = new JLabel("Name:");
		lblName.setBounds(35, 58, 86, 14);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(lblName);
		
		
		lblDeadline = new JLabel("Deadline:");
		lblDeadline.setBounds(35, 126, 86, 20);
		lblDeadline.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(lblDeadline);
		
		
		
		// creates a date picker and sets its position
		UtilDateModel model = new UtilDateModel();
		Calendar tempCalendar = new GregorianCalendar();
		tempCalendar.setTime(editingGame.getEnd());
		model.setDate(tempCalendar.get(Calendar.YEAR), tempCalendar.get(Calendar.MONTH), tempCalendar.get(Calendar.DATE));
		model.setSelected(true);
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		datePicker.setBounds(119, 126, 130, 30);
		add(datePicker);
		
		
		
		String[] hours = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		hourSelector = new JComboBox(hours);
		hourSelector.setBounds(119, 157, 52, 20);
		hourSelector.setFont(new Font("Tahoma", Font.PLAIN, 13));
		hourSelector.setSelectedItem(getHourStringFromCalendar(tempCalendar.get(Calendar.HOUR)));
	
		add(hourSelector);
		
		String[] minutes = {"00","15","30","45"};

		minuteSelector = new JComboBox(minutes);
		minuteSelector.setBounds(196, 157, 53, 20);
		minuteSelector.setFont(new Font("Tahoma", Font.PLAIN, 13));
		minuteSelector.setSelectedItem(getMinuteStringFromCalendar(tempCalendar.get(Calendar.MINUTE)));
		add(minuteSelector);
		
		lblTime = new JLabel("Time:");
		lblTime.setBounds(35, 157, 86, 20);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(lblTime);
		
		
		
		AMPMSelection = new ButtonGroup();
		
		rdbtnAm = new JRadioButton("AM");
		rdbtnAm.setBounds(119, 188, 46, 23);
		rdbtnAm.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		rdbtnPm = new JRadioButton("PM");
		rdbtnPm.setBounds(163, 188, 46, 23);
		rdbtnPm.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		if(tempCalendar.get(Calendar.HOUR) >= 12){
			rdbtnAm.setSelected(false);
			rdbtnPm.setSelected(true);
		}
		else{
			rdbtnAm.setSelected(true);
			rdbtnPm.setSelected(false);
		}


		AMPMSelection.add(rdbtnAm);
		AMPMSelection.add(rdbtnPm);
		
		add(rdbtnAm);
		add(rdbtnPm);
		
		lblDeck = new JLabel("Deck:");
		lblDeck.setBounds(35, 222, 86, 20);
		lblDeck.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(lblDeck);
		
		String[] decks = {"default"};
		deck = new JComboBox(decks);
		deck.setBounds(119, 222, 130, 20);
		deck.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(deck);
		
		btnNewButton = new JButton("Add new deck");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(119, 250, 130, 23);
		//add(btnNewButton);
		

		chckbxDeadline = new JCheckBox("Deadline?");
		chckbxDeadline.setBounds(36, 95, 129, 23);
		//chckbxDeadline.setSelected(editingGame.isHasDeadline());
		add(chckbxDeadline);
		ForceEnableOrDisableDeadline(editingGame.isHasDeadline());
		
		chckbxDeadline.addActionListener(new ChangeDeadline(this));
		
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblDescription.setBounds(35, 264, 136, 15);
		add(lblDescription);
		
		this.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e){
				resizeDescription();
			}
			public void componentHidden(ComponentEvent e){
				resizeDescription();
			}
			public void componentShown(ComponentEvent e){
				resizeDescription();
			}
			public void componentMoved(ComponentEvent e){
				resizeDescription();
			}
		});

		


	}
	




	private String getMinuteStringFromCalendar(int minute) {
		System.out.println("Minute:" + minute);
		if(minute < 15){
			return "00";
		}
		else if(minute < 30){
			return "15";
		}
		else if(minute < 45){
			return "30";
		}
		else if(minute < 60){
			return "45";
		}
		return "00";
	}


	private String getHourStringFromCalendar(int hour) {
		System.out.println("Hour:" + hour);
		hour = hour % 12;
		System.out.println("Hour % 12:" + hour);
		if(hour == 0){
			return "12";
		}
		else if(hour == 1){
			return "01";
		}
		else if(hour == 2){
			return "02";
		}
		else if(hour == 3){
			return "03";
		}
		else if(hour == 4){
			return "04";
		}
		else if(hour == 5){
			return "05";
		}
		else if(hour == 6){
			return "06";
		}
		else if(hour == 7){
			return "07";
		}
		else if(hour == 8){
			return "08";
		}
		else if(hour == 9){
			return "09";
		}
		else if(hour == 10){
			return "10";
		}
		else if(hour == 11){
			return "11";
		}
		
		return "12";
	}


	protected void resizeDescription() {
		description.setSize(description.getWidth(), this.getHeight() - 300);
		
	}




	/**
	 * Checks to see if the fields for the game are selected properly
	 * '<html>error</html>' format needed to allow word wrap in the error label
	 * @return check  - true if the fields are selected properly, otherwise false
	 */
	public boolean checkFields() {
		if (gameNameText.getText().trim().isEmpty()){
				reportError("<html>*Error: Please choose a name!</html>");
				return false;
		}
		if(chckbxDeadline.isSelected()){
			if(datePicker.getModel().getValue() == null){
				reportError("<html>*Error: Please choose a date or turn off the deadline.</html>");
				return false;
			}
			if(getDeadline().compareTo(new Date()) <= 0){
				reportError("<html>*Error: The deadline must not be in the past.</html>");
				return false;
			}
		}
		if (parent.getGameRequirements().size() == 0){
			reportError("<html>*Error: Pick at least one requirement.</html>");
			return false;
		}
		return true;
	}
	


	/**
	 * Will switch the deadline fields to enabled or disabled based on the Deadline checkbox
	 */
	public void EnableOrDisableDeadline() {
		if (chckbxDeadline.isSelected()){
			datePicker.setEnabled(true);
			hourSelector.setEnabled(true);
			minuteSelector.setEnabled(true);
			rdbtnPm.setEnabled(true);
			rdbtnAm.setEnabled(true);
		}
		else {
			datePicker.setEnabled(false);
			hourSelector.setEnabled(false);
			minuteSelector.setEnabled(false);
			rdbtnPm.setEnabled(false);
			rdbtnAm.setEnabled(false);
		}
		
	}
	
	private void ForceEnableOrDisableDeadline(boolean hasDeadline) {
		if (hasDeadline){
			chckbxDeadline.setSelected(true);
			datePicker.setEnabled(true);
			hourSelector.setEnabled(true);
			minuteSelector.setEnabled(true);
			rdbtnPm.setEnabled(true);
			rdbtnAm.setEnabled(true);
		}
		else {
			chckbxDeadline.setSelected(false);
			datePicker.setEnabled(false);
			hourSelector.setEnabled(false);
			minuteSelector.setEnabled(false);
			rdbtnPm.setEnabled(false);
			rdbtnAm.setEnabled(false);
		}
	}
	
	/**Fills the text box with a red warning based on the error Message
	 * 
	 * @param error the message to be printed, should be in <html>text</html> format
	 */
	public void reportError(String error) {
		parent.reportError(error);
	}
	
	/**Fills the text box with a green message based on the input
	 * 
	 * @param message the message to be printed, should be in <html>text</html> format
	 */
	public void reportMessage(String message) {
		parent.reportMessage(message);
	}
	

	/**
	 * Sends the signal to Mainview to close the NewgameTab
	 */
	public void closeNewGameTab() {
		this.mainViewTabController.closeTab(this.parent);
	}

	/**
	 * Fills in the game object with the necessary data
	 * @return newGame
	 */
	public Game getGameObject() {
		if(chckbxDeadline.isSelected()){
			Game newGame = new Game(getGameName(), new Date(), getDeadline());
			newGame.setRequirements(parent.getGameRequirements());
			newGame.setDescription(description.getText());
			newGame.setHasDeadline(true);
			return newGame;
		}
		else{
			Game newGame = new Game(getGameName(), new Date(), new Date());
			newGame.setRequirements(parent.getGameRequirements());
			newGame.setDescription(description.getText());
			newGame.setHasDeadline(false);
			return newGame;
		}
	}
	
	private String getGameName() {
		return gameNameText.getText();
	}

	/**
	 * returns deadline value including calendar date and time
	 * @return Date
	 */
	public Date getDeadline() {
		Date selectedDate = (Date) datePicker.getModel().getValue();
		Calendar tempCalendar = new GregorianCalendar();
		tempCalendar.setTime(selectedDate);
		tempCalendar.set(tempCalendar.get(Calendar.YEAR),
							tempCalendar.get(Calendar.MONTH),
							tempCalendar.get(Calendar.DATE),
							getHour(),
							getMinute(),
							0);
		return tempCalendar.getTime();
	}
	
	/**
	 * gets hour value from hour selector; converts to 24 hour format
	 * @return hourInt
	 */
	public int getHour() {
		String hourString = (String) hourSelector.getSelectedItem();
		int hourInt = Integer.parseInt(hourString);
		if (rdbtnPm.isSelected()) {
			hourInt = hourInt + 12;
		}
		return hourInt;
	}
	
	/**
	 * gets minute value from minute selector
	 * @return minuteInt
	 */
	public int getMinute() {
		String minuteString = (String) minuteSelector.getSelectedItem();
		int minuteInt = Integer.parseInt(minuteString);
		return minuteInt;
	}
}
