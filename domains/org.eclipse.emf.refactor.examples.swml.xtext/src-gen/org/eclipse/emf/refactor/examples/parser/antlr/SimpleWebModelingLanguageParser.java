/*
 * generated by Xtext 2.14.0-SNAPSHOT
 */
package org.eclipse.emf.refactor.examples.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.emf.refactor.examples.parser.antlr.internal.InternalSimpleWebModelingLanguageParser;
import org.eclipse.emf.refactor.examples.services.SimpleWebModelingLanguageGrammarAccess;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class SimpleWebModelingLanguageParser extends AbstractAntlrParser {

	@Inject
	private SimpleWebModelingLanguageGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalSimpleWebModelingLanguageParser createParser(XtextTokenStream stream) {
		return new InternalSimpleWebModelingLanguageParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "WebModel";
	}

	public SimpleWebModelingLanguageGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(SimpleWebModelingLanguageGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
