/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Mock Request class for testing
 *
 * @author Chris
 * @version Mar 22, 2014
 */
public class MockRequest extends Request{

	protected boolean sent = false;

	/**
	 * Constructor for MockRequest.
	 * @param networkConfiguration NetworkConfiguration
	 * @param path String
	 * @param requestMethod HttpMethod
	 */
	public MockRequest(NetworkConfiguration networkConfiguration, String path, HttpMethod requestMethod) {
		super(networkConfiguration, path, requestMethod);
	}

	/**
	 * Method send.
	
	 * @throws IllegalStateException */
	@Override
	public void send() throws IllegalStateException {
		// don't actually send
		sent = true;
	}
	
	/**
	 * Method isSent.
	
	 * @return boolean */
	public boolean isSent() {
		return sent;
	}

}
