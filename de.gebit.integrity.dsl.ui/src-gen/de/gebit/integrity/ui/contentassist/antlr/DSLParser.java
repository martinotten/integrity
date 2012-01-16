/*
* generated by Xtext
*/
package de.gebit.integrity.ui.contentassist.antlr;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

import com.google.inject.Inject;

import de.gebit.integrity.services.DSLGrammarAccess;

public class DSLParser extends AbstractContentAssistParser {
	
	@Inject
	private DSLGrammarAccess grammarAccess;
	
	private Map<AbstractElement, String> nameMappings;
	
	@Override
	protected de.gebit.integrity.ui.contentassist.antlr.internal.InternalDSLParser createParser() {
		de.gebit.integrity.ui.contentassist.antlr.internal.InternalDSLParser result = new de.gebit.integrity.ui.contentassist.antlr.internal.InternalDSLParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}
	
	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getStatementAccess().getAlternatives(), "rule__Statement__Alternatives");
					put(grammarAccess.getPackageStatementAccess().getAlternatives(), "rule__PackageStatement__Alternatives");
					put(grammarAccess.getSuiteStatementAccess().getAlternatives(), "rule__SuiteStatement__Alternatives");
					put(grammarAccess.getSuiteStatementWithResultAccess().getAlternatives(), "rule__SuiteStatementWithResult__Alternatives");
					put(grammarAccess.getTableTestAccess().getAlternatives_5(), "rule__TableTest__Alternatives_5");
					put(grammarAccess.getTableTestRowAccess().getAlternatives_2(), "rule__TableTestRow__Alternatives_2");
					put(grammarAccess.getResultNameAccess().getAlternatives(), "rule__ResultName__Alternatives");
					put(grammarAccess.getParameterNameAccess().getAlternatives(), "rule__ParameterName__Alternatives");
					put(grammarAccess.getValueOrEnumValueAccess().getAlternatives(), "rule__ValueOrEnumValue__Alternatives");
					put(grammarAccess.getValueAccess().getAlternatives(), "rule__Value__Alternatives");
					put(grammarAccess.getPackageDefinitionAccess().getGroup(), "rule__PackageDefinition__Group__0");
					put(grammarAccess.getImportAccess().getGroup(), "rule__Import__Group__0");
					put(grammarAccess.getForkDefinitionAccess().getGroup(), "rule__ForkDefinition__Group__0");
					put(grammarAccess.getTestDefinitionAccess().getGroup(), "rule__TestDefinition__Group__0");
					put(grammarAccess.getCallDefinitionAccess().getGroup(), "rule__CallDefinition__Group__0");
					put(grammarAccess.getSuiteDefinitionAccess().getGroup(), "rule__SuiteDefinition__Group__0");
					put(grammarAccess.getSuiteDefinitionAccess().getGroup_2(), "rule__SuiteDefinition__Group_2__0");
					put(grammarAccess.getSuiteDefinitionAccess().getGroup_3(), "rule__SuiteDefinition__Group_3__0");
					put(grammarAccess.getSuiteDefinitionAccess().getGroup_7(), "rule__SuiteDefinition__Group_7__0");
					put(grammarAccess.getVariableDefinitionAccess().getGroup(), "rule__VariableDefinition__Group__0");
					put(grammarAccess.getVariableDefinitionAccess().getGroup_2(), "rule__VariableDefinition__Group_2__0");
					put(grammarAccess.getConstantDefinitionAccess().getGroup(), "rule__ConstantDefinition__Group__0");
					put(grammarAccess.getTestAccess().getGroup(), "rule__Test__Group__0");
					put(grammarAccess.getTestAccess().getGroup_4(), "rule__Test__Group_4__0");
					put(grammarAccess.getTableTestAccess().getGroup(), "rule__TableTest__Group__0");
					put(grammarAccess.getTableTestAccess().getGroup_5_0(), "rule__TableTest__Group_5_0__0");
					put(grammarAccess.getTableTestRowAccess().getGroup(), "rule__TableTestRow__Group__0");
					put(grammarAccess.getTableTestRowAccess().getGroup_2_0(), "rule__TableTestRow__Group_2_0__0");
					put(grammarAccess.getParameterTableHeaderAccess().getGroup(), "rule__ParameterTableHeader__Group__0");
					put(grammarAccess.getResultTableHeaderAccess().getGroup(), "rule__ResultTableHeader__Group__0");
					put(grammarAccess.getParameterTableValueAccess().getGroup(), "rule__ParameterTableValue__Group__0");
					put(grammarAccess.getNamedResultAccess().getGroup(), "rule__NamedResult__Group__0");
					put(grammarAccess.getCallAccess().getGroup(), "rule__Call__Group__0");
					put(grammarAccess.getCallAccess().getGroup_4(), "rule__Call__Group_4__0");
					put(grammarAccess.getNamedCallResultAccess().getGroup(), "rule__NamedCallResult__Group__0");
					put(grammarAccess.getSuiteAccess().getGroup(), "rule__Suite__Group__0");
					put(grammarAccess.getSuiteAccess().getGroup_3(), "rule__Suite__Group_3__0");
					put(grammarAccess.getSuiteParameterAccess().getGroup(), "rule__SuiteParameter__Group__0");
					put(grammarAccess.getParameterAccess().getGroup(), "rule__Parameter__Group__0");
					put(grammarAccess.getArbitraryParameterOrResultNameAccess().getGroup(), "rule__ArbitraryParameterOrResultName__Group__0");
					put(grammarAccess.getMethodReferenceAccess().getGroup(), "rule__MethodReference__Group__0");
					put(grammarAccess.getQualifiedNameAccess().getGroup(), "rule__QualifiedName__Group__0");
					put(grammarAccess.getQualifiedNameAccess().getGroup_1(), "rule__QualifiedName__Group_1__0");
					put(grammarAccess.getQualifiedJavaClassNameAccess().getGroup(), "rule__QualifiedJavaClassName__Group__0");
					put(grammarAccess.getQualifiedNameWithWildcardAccess().getGroup(), "rule__QualifiedNameWithWildcard__Group__0");
					put(grammarAccess.getModelAccess().getStatementsAssignment(), "rule__Model__StatementsAssignment");
					put(grammarAccess.getVisibleSingleLineCommentAccess().getContentAssignment(), "rule__VisibleSingleLineComment__ContentAssignment");
					put(grammarAccess.getVisibleMultiLineCommentAccess().getContentAssignment(), "rule__VisibleMultiLineComment__ContentAssignment");
					put(grammarAccess.getPackageDefinitionAccess().getNameAssignment_1(), "rule__PackageDefinition__NameAssignment_1");
					put(grammarAccess.getPackageDefinitionAccess().getStatementsAssignment_3(), "rule__PackageDefinition__StatementsAssignment_3");
					put(grammarAccess.getImportAccess().getImportedNamespaceAssignment_1(), "rule__Import__ImportedNamespaceAssignment_1");
					put(grammarAccess.getForkDefinitionAccess().getNameAssignment_1(), "rule__ForkDefinition__NameAssignment_1");
					put(grammarAccess.getForkDefinitionAccess().getDescriptionAssignment_2(), "rule__ForkDefinition__DescriptionAssignment_2");
					put(grammarAccess.getTestDefinitionAccess().getNameAssignment_1(), "rule__TestDefinition__NameAssignment_1");
					put(grammarAccess.getTestDefinitionAccess().getFixtureMethodAssignment_3(), "rule__TestDefinition__FixtureMethodAssignment_3");
					put(grammarAccess.getCallDefinitionAccess().getNameAssignment_1(), "rule__CallDefinition__NameAssignment_1");
					put(grammarAccess.getCallDefinitionAccess().getFixtureMethodAssignment_3(), "rule__CallDefinition__FixtureMethodAssignment_3");
					put(grammarAccess.getSuiteDefinitionAccess().getNameAssignment_1(), "rule__SuiteDefinition__NameAssignment_1");
					put(grammarAccess.getSuiteDefinitionAccess().getParametersAssignment_2_1(), "rule__SuiteDefinition__ParametersAssignment_2_1");
					put(grammarAccess.getSuiteDefinitionAccess().getDependenciesAssignment_3_1(), "rule__SuiteDefinition__DependenciesAssignment_3_1");
					put(grammarAccess.getSuiteDefinitionAccess().getStatementsAssignment_5(), "rule__SuiteDefinition__StatementsAssignment_5");
					put(grammarAccess.getSuiteDefinitionAccess().getFinalizersAssignment_7_1(), "rule__SuiteDefinition__FinalizersAssignment_7_1");
					put(grammarAccess.getVariableDefinitionAccess().getNameAssignment_1(), "rule__VariableDefinition__NameAssignment_1");
					put(grammarAccess.getVariableDefinitionAccess().getInitialValueAssignment_2_1(), "rule__VariableDefinition__InitialValueAssignment_2_1");
					put(grammarAccess.getConstantDefinitionAccess().getNameAssignment_1(), "rule__ConstantDefinition__NameAssignment_1");
					put(grammarAccess.getConstantDefinitionAccess().getValueAssignment_2(), "rule__ConstantDefinition__ValueAssignment_2");
					put(grammarAccess.getVariableEntityAccess().getNameAssignment(), "rule__VariableEntity__NameAssignment");
					put(grammarAccess.getTestAccess().getDefinitionAssignment_1(), "rule__Test__DefinitionAssignment_1");
					put(grammarAccess.getTestAccess().getParametersAssignment_2(), "rule__Test__ParametersAssignment_2");
					put(grammarAccess.getTestAccess().getResultsAssignment_3(), "rule__Test__ResultsAssignment_3");
					put(grammarAccess.getTestAccess().getResultAssignment_4_1(), "rule__Test__ResultAssignment_4_1");
					put(grammarAccess.getTableTestAccess().getDefinitionAssignment_1(), "rule__TableTest__DefinitionAssignment_1");
					put(grammarAccess.getTableTestAccess().getParametersAssignment_2(), "rule__TableTest__ParametersAssignment_2");
					put(grammarAccess.getTableTestAccess().getParameterHeadersAssignment_3(), "rule__TableTest__ParameterHeadersAssignment_3");
					put(grammarAccess.getTableTestAccess().getResultHeadersAssignment_4(), "rule__TableTest__ResultHeadersAssignment_4");
					put(grammarAccess.getTableTestAccess().getRowsAssignment_6(), "rule__TableTest__RowsAssignment_6");
					put(grammarAccess.getTableTestRowAccess().getValuesAssignment_1(), "rule__TableTestRow__ValuesAssignment_1");
					put(grammarAccess.getTableTestRowAccess().getResultAssignment_2_0_2(), "rule__TableTestRow__ResultAssignment_2_0_2");
					put(grammarAccess.getParameterTableHeaderAccess().getNameAssignment_1(), "rule__ParameterTableHeader__NameAssignment_1");
					put(grammarAccess.getResultTableHeaderAccess().getNameAssignment_1(), "rule__ResultTableHeader__NameAssignment_1");
					put(grammarAccess.getParameterTableValueAccess().getValueAssignment_1(), "rule__ParameterTableValue__ValueAssignment_1");
					put(grammarAccess.getNamedResultAccess().getNameAssignment_0(), "rule__NamedResult__NameAssignment_0");
					put(grammarAccess.getNamedResultAccess().getValueAssignment_2(), "rule__NamedResult__ValueAssignment_2");
					put(grammarAccess.getFixedResultNameAccess().getFieldAssignment(), "rule__FixedResultName__FieldAssignment");
					put(grammarAccess.getCallAccess().getDefinitionAssignment_1(), "rule__Call__DefinitionAssignment_1");
					put(grammarAccess.getCallAccess().getParametersAssignment_2(), "rule__Call__ParametersAssignment_2");
					put(grammarAccess.getCallAccess().getResultsAssignment_3(), "rule__Call__ResultsAssignment_3");
					put(grammarAccess.getCallAccess().getResultAssignment_4_1(), "rule__Call__ResultAssignment_4_1");
					put(grammarAccess.getNamedCallResultAccess().getNameAssignment_0(), "rule__NamedCallResult__NameAssignment_0");
					put(grammarAccess.getNamedCallResultAccess().getTargetAssignment_2(), "rule__NamedCallResult__TargetAssignment_2");
					put(grammarAccess.getSuiteAccess().getDefinitionAssignment_1(), "rule__Suite__DefinitionAssignment_1");
					put(grammarAccess.getSuiteAccess().getParametersAssignment_2(), "rule__Suite__ParametersAssignment_2");
					put(grammarAccess.getSuiteAccess().getForkAssignment_3_1(), "rule__Suite__ForkAssignment_3_1");
					put(grammarAccess.getSuiteParameterAccess().getNameAssignment_0(), "rule__SuiteParameter__NameAssignment_0");
					put(grammarAccess.getSuiteParameterAccess().getValueAssignment_2(), "rule__SuiteParameter__ValueAssignment_2");
					put(grammarAccess.getParameterAccess().getNameAssignment_0(), "rule__Parameter__NameAssignment_0");
					put(grammarAccess.getParameterAccess().getValueAssignment_2(), "rule__Parameter__ValueAssignment_2");
					put(grammarAccess.getFixedParameterNameAccess().getAnnotationAssignment(), "rule__FixedParameterName__AnnotationAssignment");
					put(grammarAccess.getArbitraryParameterOrResultNameAccess().getIdentifierAssignment_1(), "rule__ArbitraryParameterOrResultName__IdentifierAssignment_1");
					put(grammarAccess.getIntegerValueAccess().getIntegerValueAssignment(), "rule__IntegerValue__IntegerValueAssignment");
					put(grammarAccess.getDecimalValueAccess().getDecimalValueAssignment(), "rule__DecimalValue__DecimalValueAssignment");
					put(grammarAccess.getStringValueAccess().getStringValueAssignment(), "rule__StringValue__StringValueAssignment");
					put(grammarAccess.getVariableAccess().getNameAssignment(), "rule__Variable__NameAssignment");
					put(grammarAccess.getEnumValueAccess().getEnumValueAssignment(), "rule__EnumValue__EnumValueAssignment");
					put(grammarAccess.getMethodReferenceAccess().getTypeAssignment_0(), "rule__MethodReference__TypeAssignment_0");
					put(grammarAccess.getMethodReferenceAccess().getMethodAssignment_2(), "rule__MethodReference__MethodAssignment_2");
				}
			};
		}
		return nameMappings.get(element);
	}
	
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			de.gebit.integrity.ui.contentassist.antlr.internal.InternalDSLParser typedParser = (de.gebit.integrity.ui.contentassist.antlr.internal.InternalDSLParser) parser;
			typedParser.entryRuleModel();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}
	
	public DSLGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(DSLGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
