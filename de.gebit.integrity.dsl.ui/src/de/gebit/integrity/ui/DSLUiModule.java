/*******************************************************************************
 * Copyright (c) 2013 Rene Schneider, GEBIT Solutions GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
/*
 * generated by Xtext
 */
package de.gebit.integrity.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.formatting.IWhitespaceInformationProvider;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.contentassist.IContentProposalPriorities;
import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher;
import org.eclipse.xtext.ui.editor.doubleClicking.DoubleClickStrategyProvider;
import org.eclipse.xtext.ui.editor.doubleClicking.LexerTokenAndCharacterPairAwareStrategy;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.preferences.LanguageRootPreferencePage;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;

import de.gebit.integrity.ui.contentassist.DSLContentProposalPriorities;
import de.gebit.integrity.ui.contentassist.IntegrityPrefixMatcher;
import de.gebit.integrity.ui.documentation.IntegrityEObjectDocumentationProvider;
import de.gebit.integrity.ui.documentation.IntegrityEObjectHoverProvider;
import de.gebit.integrity.ui.formatting.DSLWhitespaceInformationProvider;
import de.gebit.integrity.ui.highlighting.DSLAntlrTokenToAttributeIdMapper;
import de.gebit.integrity.ui.highlighting.DSLHighlightingConfiguration;
import de.gebit.integrity.ui.highlighting.DSLLexerTokenAndCharacterPairAwareStrategy;
import de.gebit.integrity.ui.highlighting.DSLSemanticHighlightingCalculator;
import de.gebit.integrity.ui.linking.DefaultIntegrityURLResolver;
import de.gebit.integrity.ui.linking.IntegrityURLResolver;
import de.gebit.integrity.ui.preferences.IntegrityRootPreferencePage;
import de.gebit.integrity.ui.search.DefaultIntegritySearch;
import de.gebit.integrity.ui.search.IntegritySearch;

/**
 * Use this class to register components to be used within the IDE.
 * 
 * @author Rene Schneider - initial API and implementation
 */
public class DSLUiModule extends de.gebit.integrity.ui.AbstractDSLUiModule {

	/**
	 * Constructs a new instance.
	 * 
	 * @param aPlugin
	 */
	public DSLUiModule(AbstractUIPlugin aPlugin) {
		super(aPlugin);
	}

	/**
	 * Binds the provider.
	 * 
	 * @return
	 */
	public Class<? extends IEObjectDocumentationProvider> bindIEObjectDocumentationProvider() {
		return IntegrityEObjectDocumentationProvider.class;
	}

	/**
	 * Binds the highlighting configuration.
	 * 
	 * @return
	 */
	public Class<? extends IHighlightingConfiguration> bindHighlightingConfiguration() {
		return DSLHighlightingConfiguration.class;
	}

	/**
	 * Binds the Antlr token to attribute ID mapper.
	 * 
	 * @return
	 */
	public Class<? extends AbstractAntlrTokenToAttributeIdMapper> bindAbstractAntlrTokenToAttributeIdMapper() {
		return DSLAntlrTokenToAttributeIdMapper.class;
	}

	/**
	 * Binds the semantic highlighting calculator.
	 * 
	 * @return
	 */
	public Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator() {
		return DSLSemanticHighlightingCalculator.class;
	}

	/**
	 * Binds the hover provider.
	 * 
	 * @return
	 */
	public Class<? extends IEObjectHoverProvider> bindEObjectHoverProvider() {
		return IntegrityEObjectHoverProvider.class;
	}

	/**
	 * Overrides the editor.
	 * 
	 * @return
	 */
	public Class<? extends XtextEditor> bindXtextEditor() {
		return IntegrityEditor.class;
	}

	/**
	 * Overrides the root language preference page.
	 * 
	 * @return
	 */
	public Class<? extends LanguageRootPreferencePage> bindLanguageRootPreferencePage() {
		return IntegrityRootPreferencePage.class;
	}

	/**
	 * Defines the {@link IntegritySearch} implementation.
	 * 
	 * @return
	 */
	public Class<? extends IntegritySearch> bindIntegritySearch() {
		return DefaultIntegritySearch.class;
	}

	/**
	 * Defines the {@link IntegrityURLResolver} implementation.
	 * 
	 * @return
	 */
	public Class<? extends IntegrityURLResolver> bindIntegrityURLResolver() {
		return DefaultIntegrityURLResolver.class;
	}

	/**
	 * Defines the {@link PrefixMatcher} implementation.
	 * 
	 * @return
	 */
	public Class<? extends PrefixMatcher> bindPrefixMatcher() {
		return IntegrityPrefixMatcher.class;
	}

	@Override
	public Class<? extends IWhitespaceInformationProvider> bindIWhitespaceInformationProvider() {
		return DSLWhitespaceInformationProvider.class;
	}

	/**
	 * Defines the {@link IContentProposalPriorities} implementation.
	 * 
	 * @return
	 */
	public Class<? extends IContentProposalPriorities> bindContentProposalPriorities() {
		return DSLContentProposalPriorities.class;
	}

	/**
	 * Defines our custom {@link DoubleClickStrategyProvider} implementation.
	 * 
	 * @return
	 */
	public Class<? extends LexerTokenAndCharacterPairAwareStrategy> bindLexerTokenAndCharacterPairAwareStrategy() {
		return DSLLexerTokenAndCharacterPairAwareStrategy.class;
	}
}
