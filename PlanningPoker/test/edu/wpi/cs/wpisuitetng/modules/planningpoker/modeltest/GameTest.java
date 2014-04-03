/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.modeltest;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import static org.junit.Assert.*;

import java.util.Date;

/**
 * Game testing class for Planning Poker
 *
 * @author  Code On Bleu
 * @version Mar 23, 2014
 */
public class GameTest {
	
	@Test
	public void autoIncrementingIDTest()
	{
		String game1name = "Game1";
		Date start = new Date();
		Date end = new Date();
		Game game1 = new Game(game1name,start,end);
		assertEquals(game1.getId(), 1);
		
		String game2name = "Game2";
		Game game2 = new Game(game2name,start,end);
		assertEquals(game2.getId(), 2);
	}
}