/*******************************************************************************
 * Copyright (c) 2017 Rene Schneider, GEBIT Solutions GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package de.gebit.integrity.docgen.html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringJoiner;

import com.google.inject.Inject;

import de.gebit.integrity.docgen.IntegrityPackage;
import de.gebit.integrity.dsl.ConstantDefinition;
import de.gebit.integrity.dsl.SuiteDefinition;
import de.gebit.integrity.dsl.SuiteParameterDefinition;
import de.gebit.integrity.dsl.ValueOrEnumValueOrOperation;
import de.gebit.integrity.dsl.ValueOrEnumValueOrOperationCollection;
import de.gebit.integrity.dsl.VariantDefinition;
import de.gebit.integrity.dsl.VariantValue;
import de.gebit.integrity.modelsource.ModelSourceExplorer;
import de.gebit.integrity.operations.UnexecutableException;
import de.gebit.integrity.parameter.conversion.ConversionContext;
import de.gebit.integrity.parameter.conversion.UnresolvableVariable;
import de.gebit.integrity.parameter.conversion.UnresolvableVariableHandling;
import de.gebit.integrity.parameter.conversion.ValueConverter;
import de.gebit.integrity.parameter.resolving.ParameterResolver;
import de.gebit.integrity.runner.TestModel;
import de.gebit.integrity.utils.IntegrityDSLUtil;
import de.gebit.integrity.utils.ParsedDocumentationComment;
import de.gebit.integrity.utils.ParsedDocumentationComment.ParseException;
import htmlflow.HtmlWriterComposite;
import htmlflow.elements.HtmlBody;
import htmlflow.elements.HtmlDiv;
import htmlflow.elements.HtmlP;
import htmlflow.elements.HtmlTable;
import htmlflow.elements.HtmlTd;
import htmlflow.elements.HtmlTr;

/**
 * HTML view of an Integrity package, containing a number of suites.
 *
 * @author Rene Schneider - initial API and implementation
 *
 */
public class PackageView extends IntegrityHtmlView<Entry<String, Collection<SuiteDefinition>>> {

	/**
	 * The {@link ValueConverter}.
	 */
	@Inject
	protected ValueConverter valueConverter;

	/**
	 * The {@link ParameterResolver}.
	 */
	@Inject
	protected ParameterResolver parameterResolver;

	/**
	 * The {@link ModelSourceExplorer}.
	 */
	@Inject
	protected ModelSourceExplorer modelSourceExplorer;

	/**
	 * This holds the div that contains the main content of the package view.
	 */
	protected HtmlDiv<?> mainContent;

	/**
	 * Constructor.
	 * 
	 * @param anEntry
	 * @throws ParseException
	 */
	public PackageView(IntegrityPackage aPackage, TestModel aModel, PackageTreeView aTreeView) throws ParseException {
		aModel.getInjector().injectMembers(this);

		head().linkCss("../resources/css/main.css").title("Package " + aPackage.getName());
		head().scriptLink("../resources/js/mark.js");
		head().scriptLink("../resources/js/package.js");

		HtmlBody<?> tempBody = body();

		// The content for the tree view is basically just copied over
		HtmlDiv<?> tempTreeContainerDiv = tempBody.div().idAttr("treecontainer");
		tempTreeContainerDiv.addChild(aTreeView.getTreeRootElement());

		// This generates the actual content (documentation of the package)
		mainContent = tempBody.div().idAttr("maincontainer");
		mainContent.div().classAttr("title").text("Package " + aPackage.getName());
		mainContent.hr();

		processConstants(aPackage, mainContent);
		processSuites(aPackage, mainContent);
	}

	@Override
	protected HtmlWriterComposite<?, ?> getTextOnlyEntryNode() {
		return mainContent;
	}

	/**
	 * Processes all suites in a package.
	 * 
	 * @param anEntry
	 * @param aModelSourceExplorer
	 * @param aMainContainerDiv
	 * @throws ParseException
	 */
	protected void processSuites(IntegrityPackage aPackage, HtmlDiv<?> aMainContainerDiv) throws ParseException {
		if (aPackage.getSuites().size() == 0) {
			return;
		}

		HtmlDiv<?> tempMainDiv = aMainContainerDiv.div().classAttr("suites");
		tempMainDiv.div().classAttr("suitesummary")
				.text("This package defines " + aPackage.getSuites().size() + " suites");

		for (SuiteDefinition tempSuite : aPackage.getSuites()) {
			HtmlDiv<?> tempSuiteDiv = tempMainDiv.div().classAttr("suite");
			HtmlDiv<?> tempSuiteHeaderDiv = tempSuiteDiv.div().classAttr("suiteheader");
			tempSuiteHeaderDiv.div().classAttr("suitename").text(tempSuite.getName());
			String tempSuiteTitle = IntegrityDSLUtil.getSuiteTitle(tempSuite);
			if (tempSuiteTitle != null) {
				tempSuiteHeaderDiv.div().classAttr("suitetitle").text(tempSuiteTitle);
			}
			tempSuiteHeaderDiv.div().classAttr("fullsuitename code")
					.text(IntegrityDSLUtil.getQualifiedSuiteName(tempSuite));

			boolean tempSuiteHasDetails = (tempSuite.getDocumentation() != null);

			if (tempSuiteHasDetails) {
				HtmlDiv<?> tempSuiteDetailsDiv = tempSuiteDiv.div().classAttr("suitedetails");
				if (tempSuite.getDocumentation() != null) {
					ParsedDocumentationComment tempParsedComment = new ParsedDocumentationComment(
							tempSuite.getDocumentation(),
							modelSourceExplorer.determineSourceInformation(tempSuite.getDocumentation()));
					tempSuiteDetailsDiv.div().classAttr("suitedescription")
							.text(tempParsedComment.getDocumentationText());

					if (tempSuite.getParameters().size() > 0) {
						HtmlDiv<?> tempSuiteParamsDiv = tempSuiteDetailsDiv.div().classAttr("suiteparams");
						tempSuiteParamsDiv.div().classAttr("detailstitle").text("Parameters");
						HtmlTable<?> tempParamTable = tempSuiteParamsDiv.table();

						for (SuiteParameterDefinition tempParameter : tempSuite.getParameters()) {
							String tempParamDoc = tempParsedComment.getParameterDocumentationTexts()
									.get(tempParameter.getName().getName());
							HtmlTr<?> tempRow = tempParamTable.tr();
							tempRow.td().classAttr(CSSClasses.CODE).text(tempParameter.getName().getName());
							tempRow.td().text(tempParamDoc != null ? tempParamDoc : "");
						}
					}
				}
			}
		}
	}

	/**
	 * Processes all constants in a package.
	 * 
	 * @param anEntry
	 * @param aModelSourceExplorer
	 * @param aMainContainerDiv
	 * @throws ParseException
	 */
	protected void processConstants(IntegrityPackage aPackage, HtmlDiv<?> aMainContainerDiv) throws ParseException {
		if (aPackage.getConstants().size() == 0) {
			return;
		}

		HtmlDiv<?> tempMainDiv = aMainContainerDiv.div().classAttr("constants");
		tempMainDiv.div().classAttr("constantsummary")
				.text("This package defines " + aPackage.getConstants().size() + " constants");

		HtmlTable<?> tempTable = tempMainDiv.table();
		HtmlTr<?> tempHeaderRow = tempTable.tr();
		tempHeaderRow.th().text("Type");
		tempHeaderRow.th().text("Name");
		tempHeaderRow.th().text("Value");
		tempHeaderRow.th().text("Description");

		for (ConstantDefinition tempConstant : aPackage.getConstants()) {
			HtmlTr<?> tempRow = tempTable.tr();

			String tempConstantDescription = "";
			if (tempConstant.getDocumentation() != null) {
				ParsedDocumentationComment tempParsedComment = new ParsedDocumentationComment(
						tempConstant.getDocumentation(),
						modelSourceExplorer.determineSourceInformation(tempConstant.getDocumentation()));
				tempConstantDescription = tempParsedComment.getDocumentationText();
			}

			StringJoiner tempTypes = new StringJoiner(" ");
			if (tempConstant.getPrivate() != null) {
				tempTypes.add(tempConstant.getPrivate());
			}
			if (tempConstant.getParameterized() != null) {
				tempTypes.add(tempConstant.getParameterized());
			}

			tempRow.td().text(tempTypes.toString());
			tempRow.td().classAttr(CSSClasses.CODE).text(tempConstant.getName().getName());
			HtmlTd<?> tempValueContainer = tempRow.td();

			if (tempConstant.getVariantValues().size() == 0) {
				// No variants case - this is easy!
				resolveValue(tempConstant.getValue()).forEach((anElement) -> {
					tempValueContainer.addChild(anElement);
				});
			} else {
				HtmlTable<?> tempVariantTable = new HtmlTable<>();
				if (tempConstant.getValue() != null) {
					HtmlTr<?> tempVariantRow = tempVariantTable.tr();
					tempVariantRow.td().text("no variant:");
					HtmlTd<?> tempVariantValueContainer = tempVariantRow.td();
					resolveValue(tempConstant.getValue()).forEach((anElement) -> {
						tempVariantValueContainer.addChild(anElement);
					});
				}

				for (VariantValue tempVariantValue : tempConstant.getVariantValues()) {
					HtmlTr<?> tempVariantRow = tempVariantTable.tr();
					StringJoiner tempJoiner = new StringJoiner(", ");
					for (VariantDefinition tempVariant : tempVariantValue.getNames()) {
						tempJoiner.add(IntegrityDSLUtil.getQualifiedVariantName(tempVariant));
					}
					tempVariantRow.td().text("in " + tempJoiner.toString() + ":");
					HtmlTd<?> tempVariantValueContainer = tempVariantRow.td();
					resolveValue(tempVariantValue.getValue()).forEach((anElement) -> {
						tempVariantValueContainer.addChild(anElement);
					});
				}

				tempValueContainer.addChild(tempVariantTable);
			}

			tempRow.td().text(tempConstantDescription);
		}
	}

	/**
	 * Resolves a {@link ValueOrEnumValueOrOperationCollection} suitable for inclusion in the HTML page.
	 * 
	 * @param aValueCollection
	 * @return
	 */
	protected List<HtmlP<?>> resolveValue(ValueOrEnumValueOrOperationCollection aValueCollection) {
		if (aValueCollection instanceof ValueOrEnumValueOrOperationCollection) {
			if (aValueCollection.getMoreValues().size() > 0) {
				List<HtmlP<?>> tempElements = new ArrayList<HtmlP<?>>(aValueCollection.getMoreValues().size() + 1);
				for (ValueOrEnumValueOrOperation tempSingleValue : IntegrityDSLUtil
						.getAllValuesFromCollection(aValueCollection)) {
					tempElements.add(resolveSingleValue(tempSingleValue));
				}
				return tempElements;
			} else {
				return Collections.singletonList(resolveSingleValue(aValueCollection.getValue()));
			}
		} else {
			return Collections.singletonList(resolveSingleValue((ValueOrEnumValueOrOperation) aValueCollection));
		}
	}

	/**
	 * Resolves a single value to its actual value includable in an HTML page.
	 * 
	 * @param aValue
	 * @return
	 */
	protected HtmlP<?> resolveSingleValue(ValueOrEnumValueOrOperation aValue) {
		Object tempResult;
		try {
			tempResult = parameterResolver.resolveStatically(aValue, null);
		} catch (ClassNotFoundException | InstantiationException | UnexecutableException exc) {
			tempResult = UnresolvableVariable.getInstance();
		}

		HtmlP<?> tempP = new HtmlP<>();
		tempP.text(valueConverter.convertValueToString(tempResult, false, new ConversionContext()
				.withUnresolvableVariableHandlingPolicy(UnresolvableVariableHandling.RESOLVE_TO_UNRESOLVABLE_OBJECT))
				.toString());
		return tempP;
	}

}