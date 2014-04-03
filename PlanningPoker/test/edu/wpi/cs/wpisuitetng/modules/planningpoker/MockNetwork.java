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

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Mock Network class for testing
 *
 * @author Chris
 * @version Mar 22, 2014
 */
public class MockNetwork extends Network{

		
		protected MockRequest lastRequestMade = null;
		
		/**
		 * Method makeRequest.
		 * @param path String
		 * @param requestMethod HttpMethod
		
		 * @return Request */
		@Override
		public Request makeRequest(String path, HttpMethod requestMethod) {
			if (requestMethod == null) {
				throw new NullPointerException("requestMethod may not be null");
			}
			
			lastRequestMade = new MockRequest(defaultNetworkConfiguration, path, requestMethod); 
			
			return lastRequestMade;
		}
		
		/**
		 * Method getLastRequestMade.
		
		 * @return MockRequest */
		public MockRequest getLastRequestMade() {
			return lastRequestMade;
		}
}
