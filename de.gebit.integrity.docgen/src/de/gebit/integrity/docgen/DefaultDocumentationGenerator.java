/*******************************************************************************
 * Copyright (c) 2017 Rene Schneider, GEBIT Solutions GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package de.gebit.integrity.docgen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.gebit.integrity.docgen.html.PackageTreeView;
import de.gebit.integrity.docgen.html.PackageView;
import de.gebit.integrity.dsl.PackageDefinition;
import de.gebit.integrity.dsl.SuiteDefinition;
import de.gebit.integrity.modelsource.ModelSourceExplorer;
import de.gebit.integrity.runner.TestModel;
import de.gebit.integrity.utils.ParsedDocumentationComment.ParseException;
import htmlflow.HtmlView;

/**
 * The documentation generator is responsible for parsing Integrity files and generating HTML documentation, similar to
 * JavaDoc.
 *
 * @author Rene Schneider - initial API and implementation
 *
 */
public class DefaultDocumentationGenerator implements DocumentationGenerator {

	/**
	 * The model to parse.
	 */
	private TestModel model;

	/**
	 * The target directory for the HTML result files.
	 */
	private File targetDirectory;

	@Override
	public void initialize(TestModel aModel) {
		model = aModel;
	}

	@Override
	public void run(File aTargetDirectory) throws IOException {
		if (!aTargetDirectory.exists()) {
			if (!aTargetDirectory.mkdirs()) {
				throw new IOException("Failed to create target directory");
			}
		}
		if (!aTargetDirectory.isDirectory()) {
			throw new IOException("Target directory name provided is not a directory");
		}
		targetDirectory = aTargetDirectory;

		ModelSourceExplorer tempModelSourceExplorer = model.getInjector().getInstance(ModelSourceExplorer.class);

		Collection<IntegrityPackage> tempPackages = groupEntitiesByPackage(model);

		// Write out the package tree view document
		System.out.print("Writing package tree...");
		PackageTreeView tempTreeView = new PackageTreeView(tempPackages, false);
		// We also need a second PackageTreeView that is to be embedded into the package pages
		PackageTreeView tempTreeViewEmbedded = new PackageTreeView(tempPackages, true);
		processDocument(new File(targetDirectory, "index.html"), tempTreeView);
		System.out.println("done!");

		// Write all the documents for packages
		File tempPackageSubdir = new File(targetDirectory, "packages");
		tempPackageSubdir.mkdir();
		for (IntegrityPackage tempPackage : tempPackages) {
			System.out.print("Writing doc for package '" + tempPackage.getName() + "'...");
			try {
				processDocument(new File(tempPackageSubdir, tempPackage.getName() + ".html"),
						new PackageView(tempPackage, tempModelSourceExplorer, tempTreeViewEmbedded));
			} catch (ParseException exc) {
				System.out.println("...failed :-( " + exc.getMessage());
			}
			System.out.println("done!");
		}

		System.out.print("Copying static resources...");
		copyResources(targetDirectory);
		System.out.println("done!");

		System.out.println("Finished generating documentation!");
	}

	/**
	 * Parses all entities relevant for documentation, grouping them by the packages that they are located within, and
	 * returns those packages.
	 * 
	 * @param aModel
	 *            the model to search
	 * @return a list of packages with entity info
	 */
	protected Collection<IntegrityPackage> groupEntitiesByPackage(TestModel aModel) {
		Map<String, IntegrityPackage> tempResult = new HashMap<>();

		for (SuiteDefinition tempSuite : aModel.getAllSuites()) {
			String tempPackageName = ((PackageDefinition) tempSuite.eContainer()).getName();
			IntegrityPackage tempPackageInfo = tempResult.get(tempPackageName);
			if (tempPackageInfo == null) {
				tempPackageInfo = new IntegrityPackage(tempPackageName);
				tempResult.put(tempPackageName, tempPackageInfo);
			}
			tempPackageInfo.add(tempSuite);
		}

		return tempResult.values();
	}

	/**
	 * Writes out the result HTML for the provided view.
	 * 
	 * @param aTargetFile
	 *            the file to write into
	 * @param aView
	 *            the view to generate HTML from
	 * @throws IOException
	 */
	protected <T> void processDocument(File aTargetFile, HtmlView<T> aView) throws IOException {
		try (PrintStream tempOut = new PrintStream(new FileOutputStream(aTargetFile))) {
			aView.setPrintStream(tempOut).write();
		}
	}

	/**
	 * Copies all static resource files into the target directory.
	 * 
	 * @param aTargetDir
	 *            the target directory
	 * @throws IOException
	 */
	protected void copyResources(File aTargetDir) throws IOException {
		File tempCssTargetDir = new File(aTargetDir, "resources/css");
		if (!tempCssTargetDir.exists() || !tempCssTargetDir.isDirectory()) {
			if (!tempCssTargetDir.mkdirs()) {
				throw new IOException("Failed to create CSS target directory");
			}
		}

		File tempCss = new File(tempCssTargetDir, "main.css");
		InputStream tempStream = getResourceAsStream("css/main.css");
		Files.copy(tempStream, tempCss.getAbsoluteFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
	}

	/**
	 * Opens a stream to read the given resource from the classpath. Assumes resources are present under a fixed
	 * subdirectory.
	 * 
	 * @param aResourceName
	 *            the name of the resource
	 * @return the resource stream
	 * @throws FileNotFoundException
	 *             if the resource is not found
	 */
	protected InputStream getResourceAsStream(String aResourceName) throws FileNotFoundException {
		InputStream tempStream = getClass().getResourceAsStream("/docgen-resources/" + aResourceName);
		if (tempStream == null) {
			throw new FileNotFoundException("Failed to find docgen resource '" + aResourceName + "' in JAR file");
		}

		return tempStream;
	}

}
