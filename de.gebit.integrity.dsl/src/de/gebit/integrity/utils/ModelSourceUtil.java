/*******************************************************************************
 * Copyright (c) 2013 Rene Schneider, GEBIT Solutions GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package de.gebit.integrity.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

/**
 * This utility class contains helper methods for various tasks of determining information about the Integrity model
 * source files.
 * 
 * @author Rene Schneider - initial API and implementation
 * 
 */
public final class ModelSourceUtil {

	private ModelSourceUtil() {
		// private constructor
	}

	/**
	 * Finds the path to the source file in which the given {@link EObject} is defined.
	 * 
	 * @param anObject
	 *            the object
	 * @return the path or null if not determinable
	 */
	public static String getSourceFilePathForEObject(EObject anObject) {
		ICompositeNode tempNode = NodeModelUtils.getNode(anObject);
		if (tempNode != null) {
			return getSourceFilePathForINode(tempNode);
		}

		return null;
	}

	/**
	 * Finds the path to the source file in which the given {@link INode} is defined.
	 * 
	 * @param aNode
	 *            the node
	 * @return the path
	 */
	public static String getSourceFilePathForINode(INode aNode) {
		String tempResource = aNode.getRootNode().getSemanticElement().eResource().getURI().toString();
		String tempUndesiredPrefix = "platform:/resource/";
		if (tempResource.startsWith(tempUndesiredPrefix)) {
			return tempResource.substring(tempUndesiredPrefix.length());
		} else {
			return tempResource;
		}
	}

}
