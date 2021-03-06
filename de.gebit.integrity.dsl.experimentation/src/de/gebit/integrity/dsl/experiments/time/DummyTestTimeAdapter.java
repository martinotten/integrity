/*******************************************************************************
 * Copyright (c) 2017 Rene Schneider, GEBIT Solutions GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package de.gebit.integrity.dsl.experiments.time;

/**
 * Dummy Test Time Adapter.
 *
 * @author Rene Schneider - initial API and implementation
 *
 */
public class DummyTestTimeAdapter {

	public void setInternalState(long aRealtimeOffset, long aRealtimeDecouplingTime, double aProgressionFactor) {
		ExperimentalTimeAndDateSingleton.getInstance().setInternalState(aRealtimeOffset, aRealtimeDecouplingTime,
				aProgressionFactor);
	}

}
