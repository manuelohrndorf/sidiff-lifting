package org.eclipse.emf.refactor.examples.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.eclipse.emf.refactor.examples.services.SimpleWebModelingLanguageGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalSimpleWebModelingLanguageParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'Boolean'", "'Email'", "'Float'", "'Integer'", "'String'", "'webmodel'", "'{'", "'}'", "'data {'", "'entity'", "'att'", "':'", "'ref'", "'hypertext {'", "'start page is'", "'static page'", "'link to page'", "'index page'", "'shows entity'", "'data page'"
    };
    public static final int RULE_STRING=6;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int RULE_ID=4;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=5;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators


        public InternalSimpleWebModelingLanguageParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalSimpleWebModelingLanguageParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalSimpleWebModelingLanguageParser.tokenNames; }
    public String getGrammarFileName() { return "InternalSimpleWebModelingLanguage.g"; }


    	private SimpleWebModelingLanguageGrammarAccess grammarAccess;

    	public void setGrammarAccess(SimpleWebModelingLanguageGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRuleWebModel"
    // InternalSimpleWebModelingLanguage.g:53:1: entryRuleWebModel : ruleWebModel EOF ;
    public final void entryRuleWebModel() throws RecognitionException {
        try {
            // InternalSimpleWebModelingLanguage.g:54:1: ( ruleWebModel EOF )
            // InternalSimpleWebModelingLanguage.g:55:1: ruleWebModel EOF
            {
             before(grammarAccess.getWebModelRule()); 
            pushFollow(FOLLOW_1);
            ruleWebModel();

            state._fsp--;

             after(grammarAccess.getWebModelRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleWebModel"


    // $ANTLR start "ruleWebModel"
    // InternalSimpleWebModelingLanguage.g:62:1: ruleWebModel : ( ( rule__WebModel__Group__0 ) ) ;
    public final void ruleWebModel() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:66:2: ( ( ( rule__WebModel__Group__0 ) ) )
            // InternalSimpleWebModelingLanguage.g:67:2: ( ( rule__WebModel__Group__0 ) )
            {
            // InternalSimpleWebModelingLanguage.g:67:2: ( ( rule__WebModel__Group__0 ) )
            // InternalSimpleWebModelingLanguage.g:68:3: ( rule__WebModel__Group__0 )
            {
             before(grammarAccess.getWebModelAccess().getGroup()); 
            // InternalSimpleWebModelingLanguage.g:69:3: ( rule__WebModel__Group__0 )
            // InternalSimpleWebModelingLanguage.g:69:4: rule__WebModel__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__WebModel__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getWebModelAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleWebModel"


    // $ANTLR start "entryRuleDataLayer"
    // InternalSimpleWebModelingLanguage.g:78:1: entryRuleDataLayer : ruleDataLayer EOF ;
    public final void entryRuleDataLayer() throws RecognitionException {
        try {
            // InternalSimpleWebModelingLanguage.g:79:1: ( ruleDataLayer EOF )
            // InternalSimpleWebModelingLanguage.g:80:1: ruleDataLayer EOF
            {
             before(grammarAccess.getDataLayerRule()); 
            pushFollow(FOLLOW_1);
            ruleDataLayer();

            state._fsp--;

             after(grammarAccess.getDataLayerRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDataLayer"


    // $ANTLR start "ruleDataLayer"
    // InternalSimpleWebModelingLanguage.g:87:1: ruleDataLayer : ( ( rule__DataLayer__Group__0 ) ) ;
    public final void ruleDataLayer() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:91:2: ( ( ( rule__DataLayer__Group__0 ) ) )
            // InternalSimpleWebModelingLanguage.g:92:2: ( ( rule__DataLayer__Group__0 ) )
            {
            // InternalSimpleWebModelingLanguage.g:92:2: ( ( rule__DataLayer__Group__0 ) )
            // InternalSimpleWebModelingLanguage.g:93:3: ( rule__DataLayer__Group__0 )
            {
             before(grammarAccess.getDataLayerAccess().getGroup()); 
            // InternalSimpleWebModelingLanguage.g:94:3: ( rule__DataLayer__Group__0 )
            // InternalSimpleWebModelingLanguage.g:94:4: rule__DataLayer__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__DataLayer__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getDataLayerAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDataLayer"


    // $ANTLR start "entryRuleEntity"
    // InternalSimpleWebModelingLanguage.g:103:1: entryRuleEntity : ruleEntity EOF ;
    public final void entryRuleEntity() throws RecognitionException {
        try {
            // InternalSimpleWebModelingLanguage.g:104:1: ( ruleEntity EOF )
            // InternalSimpleWebModelingLanguage.g:105:1: ruleEntity EOF
            {
             before(grammarAccess.getEntityRule()); 
            pushFollow(FOLLOW_1);
            ruleEntity();

            state._fsp--;

             after(grammarAccess.getEntityRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleEntity"


    // $ANTLR start "ruleEntity"
    // InternalSimpleWebModelingLanguage.g:112:1: ruleEntity : ( ( rule__Entity__Group__0 ) ) ;
    public final void ruleEntity() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:116:2: ( ( ( rule__Entity__Group__0 ) ) )
            // InternalSimpleWebModelingLanguage.g:117:2: ( ( rule__Entity__Group__0 ) )
            {
            // InternalSimpleWebModelingLanguage.g:117:2: ( ( rule__Entity__Group__0 ) )
            // InternalSimpleWebModelingLanguage.g:118:3: ( rule__Entity__Group__0 )
            {
             before(grammarAccess.getEntityAccess().getGroup()); 
            // InternalSimpleWebModelingLanguage.g:119:3: ( rule__Entity__Group__0 )
            // InternalSimpleWebModelingLanguage.g:119:4: rule__Entity__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Entity__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getEntityAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleEntity"


    // $ANTLR start "entryRuleAttribute"
    // InternalSimpleWebModelingLanguage.g:128:1: entryRuleAttribute : ruleAttribute EOF ;
    public final void entryRuleAttribute() throws RecognitionException {
        try {
            // InternalSimpleWebModelingLanguage.g:129:1: ( ruleAttribute EOF )
            // InternalSimpleWebModelingLanguage.g:130:1: ruleAttribute EOF
            {
             before(grammarAccess.getAttributeRule()); 
            pushFollow(FOLLOW_1);
            ruleAttribute();

            state._fsp--;

             after(grammarAccess.getAttributeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAttribute"


    // $ANTLR start "ruleAttribute"
    // InternalSimpleWebModelingLanguage.g:137:1: ruleAttribute : ( ( rule__Attribute__Group__0 ) ) ;
    public final void ruleAttribute() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:141:2: ( ( ( rule__Attribute__Group__0 ) ) )
            // InternalSimpleWebModelingLanguage.g:142:2: ( ( rule__Attribute__Group__0 ) )
            {
            // InternalSimpleWebModelingLanguage.g:142:2: ( ( rule__Attribute__Group__0 ) )
            // InternalSimpleWebModelingLanguage.g:143:3: ( rule__Attribute__Group__0 )
            {
             before(grammarAccess.getAttributeAccess().getGroup()); 
            // InternalSimpleWebModelingLanguage.g:144:3: ( rule__Attribute__Group__0 )
            // InternalSimpleWebModelingLanguage.g:144:4: rule__Attribute__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Attribute__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getAttributeAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAttribute"


    // $ANTLR start "entryRuleReference"
    // InternalSimpleWebModelingLanguage.g:153:1: entryRuleReference : ruleReference EOF ;
    public final void entryRuleReference() throws RecognitionException {
        try {
            // InternalSimpleWebModelingLanguage.g:154:1: ( ruleReference EOF )
            // InternalSimpleWebModelingLanguage.g:155:1: ruleReference EOF
            {
             before(grammarAccess.getReferenceRule()); 
            pushFollow(FOLLOW_1);
            ruleReference();

            state._fsp--;

             after(grammarAccess.getReferenceRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleReference"


    // $ANTLR start "ruleReference"
    // InternalSimpleWebModelingLanguage.g:162:1: ruleReference : ( ( rule__Reference__Group__0 ) ) ;
    public final void ruleReference() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:166:2: ( ( ( rule__Reference__Group__0 ) ) )
            // InternalSimpleWebModelingLanguage.g:167:2: ( ( rule__Reference__Group__0 ) )
            {
            // InternalSimpleWebModelingLanguage.g:167:2: ( ( rule__Reference__Group__0 ) )
            // InternalSimpleWebModelingLanguage.g:168:3: ( rule__Reference__Group__0 )
            {
             before(grammarAccess.getReferenceAccess().getGroup()); 
            // InternalSimpleWebModelingLanguage.g:169:3: ( rule__Reference__Group__0 )
            // InternalSimpleWebModelingLanguage.g:169:4: rule__Reference__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Reference__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getReferenceAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleReference"


    // $ANTLR start "entryRuleHypertextLayer"
    // InternalSimpleWebModelingLanguage.g:178:1: entryRuleHypertextLayer : ruleHypertextLayer EOF ;
    public final void entryRuleHypertextLayer() throws RecognitionException {
        try {
            // InternalSimpleWebModelingLanguage.g:179:1: ( ruleHypertextLayer EOF )
            // InternalSimpleWebModelingLanguage.g:180:1: ruleHypertextLayer EOF
            {
             before(grammarAccess.getHypertextLayerRule()); 
            pushFollow(FOLLOW_1);
            ruleHypertextLayer();

            state._fsp--;

             after(grammarAccess.getHypertextLayerRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleHypertextLayer"


    // $ANTLR start "ruleHypertextLayer"
    // InternalSimpleWebModelingLanguage.g:187:1: ruleHypertextLayer : ( ( rule__HypertextLayer__Group__0 ) ) ;
    public final void ruleHypertextLayer() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:191:2: ( ( ( rule__HypertextLayer__Group__0 ) ) )
            // InternalSimpleWebModelingLanguage.g:192:2: ( ( rule__HypertextLayer__Group__0 ) )
            {
            // InternalSimpleWebModelingLanguage.g:192:2: ( ( rule__HypertextLayer__Group__0 ) )
            // InternalSimpleWebModelingLanguage.g:193:3: ( rule__HypertextLayer__Group__0 )
            {
             before(grammarAccess.getHypertextLayerAccess().getGroup()); 
            // InternalSimpleWebModelingLanguage.g:194:3: ( rule__HypertextLayer__Group__0 )
            // InternalSimpleWebModelingLanguage.g:194:4: rule__HypertextLayer__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__HypertextLayer__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getHypertextLayerAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleHypertextLayer"


    // $ANTLR start "entryRulePage"
    // InternalSimpleWebModelingLanguage.g:203:1: entryRulePage : rulePage EOF ;
    public final void entryRulePage() throws RecognitionException {
        try {
            // InternalSimpleWebModelingLanguage.g:204:1: ( rulePage EOF )
            // InternalSimpleWebModelingLanguage.g:205:1: rulePage EOF
            {
             before(grammarAccess.getPageRule()); 
            pushFollow(FOLLOW_1);
            rulePage();

            state._fsp--;

             after(grammarAccess.getPageRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePage"


    // $ANTLR start "rulePage"
    // InternalSimpleWebModelingLanguage.g:212:1: rulePage : ( ( rule__Page__Alternatives ) ) ;
    public final void rulePage() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:216:2: ( ( ( rule__Page__Alternatives ) ) )
            // InternalSimpleWebModelingLanguage.g:217:2: ( ( rule__Page__Alternatives ) )
            {
            // InternalSimpleWebModelingLanguage.g:217:2: ( ( rule__Page__Alternatives ) )
            // InternalSimpleWebModelingLanguage.g:218:3: ( rule__Page__Alternatives )
            {
             before(grammarAccess.getPageAccess().getAlternatives()); 
            // InternalSimpleWebModelingLanguage.g:219:3: ( rule__Page__Alternatives )
            // InternalSimpleWebModelingLanguage.g:219:4: rule__Page__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Page__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getPageAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePage"


    // $ANTLR start "entryRuleStaticPage"
    // InternalSimpleWebModelingLanguage.g:228:1: entryRuleStaticPage : ruleStaticPage EOF ;
    public final void entryRuleStaticPage() throws RecognitionException {
        try {
            // InternalSimpleWebModelingLanguage.g:229:1: ( ruleStaticPage EOF )
            // InternalSimpleWebModelingLanguage.g:230:1: ruleStaticPage EOF
            {
             before(grammarAccess.getStaticPageRule()); 
            pushFollow(FOLLOW_1);
            ruleStaticPage();

            state._fsp--;

             after(grammarAccess.getStaticPageRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleStaticPage"


    // $ANTLR start "ruleStaticPage"
    // InternalSimpleWebModelingLanguage.g:237:1: ruleStaticPage : ( ( rule__StaticPage__Group__0 ) ) ;
    public final void ruleStaticPage() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:241:2: ( ( ( rule__StaticPage__Group__0 ) ) )
            // InternalSimpleWebModelingLanguage.g:242:2: ( ( rule__StaticPage__Group__0 ) )
            {
            // InternalSimpleWebModelingLanguage.g:242:2: ( ( rule__StaticPage__Group__0 ) )
            // InternalSimpleWebModelingLanguage.g:243:3: ( rule__StaticPage__Group__0 )
            {
             before(grammarAccess.getStaticPageAccess().getGroup()); 
            // InternalSimpleWebModelingLanguage.g:244:3: ( rule__StaticPage__Group__0 )
            // InternalSimpleWebModelingLanguage.g:244:4: rule__StaticPage__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__StaticPage__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getStaticPageAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleStaticPage"


    // $ANTLR start "entryRuleLink"
    // InternalSimpleWebModelingLanguage.g:253:1: entryRuleLink : ruleLink EOF ;
    public final void entryRuleLink() throws RecognitionException {
        try {
            // InternalSimpleWebModelingLanguage.g:254:1: ( ruleLink EOF )
            // InternalSimpleWebModelingLanguage.g:255:1: ruleLink EOF
            {
             before(grammarAccess.getLinkRule()); 
            pushFollow(FOLLOW_1);
            ruleLink();

            state._fsp--;

             after(grammarAccess.getLinkRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLink"


    // $ANTLR start "ruleLink"
    // InternalSimpleWebModelingLanguage.g:262:1: ruleLink : ( ( rule__Link__Group__0 ) ) ;
    public final void ruleLink() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:266:2: ( ( ( rule__Link__Group__0 ) ) )
            // InternalSimpleWebModelingLanguage.g:267:2: ( ( rule__Link__Group__0 ) )
            {
            // InternalSimpleWebModelingLanguage.g:267:2: ( ( rule__Link__Group__0 ) )
            // InternalSimpleWebModelingLanguage.g:268:3: ( rule__Link__Group__0 )
            {
             before(grammarAccess.getLinkAccess().getGroup()); 
            // InternalSimpleWebModelingLanguage.g:269:3: ( rule__Link__Group__0 )
            // InternalSimpleWebModelingLanguage.g:269:4: rule__Link__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Link__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getLinkAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLink"


    // $ANTLR start "entryRuleDynamicPage"
    // InternalSimpleWebModelingLanguage.g:278:1: entryRuleDynamicPage : ruleDynamicPage EOF ;
    public final void entryRuleDynamicPage() throws RecognitionException {
        try {
            // InternalSimpleWebModelingLanguage.g:279:1: ( ruleDynamicPage EOF )
            // InternalSimpleWebModelingLanguage.g:280:1: ruleDynamicPage EOF
            {
             before(grammarAccess.getDynamicPageRule()); 
            pushFollow(FOLLOW_1);
            ruleDynamicPage();

            state._fsp--;

             after(grammarAccess.getDynamicPageRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDynamicPage"


    // $ANTLR start "ruleDynamicPage"
    // InternalSimpleWebModelingLanguage.g:287:1: ruleDynamicPage : ( ( rule__DynamicPage__Alternatives ) ) ;
    public final void ruleDynamicPage() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:291:2: ( ( ( rule__DynamicPage__Alternatives ) ) )
            // InternalSimpleWebModelingLanguage.g:292:2: ( ( rule__DynamicPage__Alternatives ) )
            {
            // InternalSimpleWebModelingLanguage.g:292:2: ( ( rule__DynamicPage__Alternatives ) )
            // InternalSimpleWebModelingLanguage.g:293:3: ( rule__DynamicPage__Alternatives )
            {
             before(grammarAccess.getDynamicPageAccess().getAlternatives()); 
            // InternalSimpleWebModelingLanguage.g:294:3: ( rule__DynamicPage__Alternatives )
            // InternalSimpleWebModelingLanguage.g:294:4: rule__DynamicPage__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__DynamicPage__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getDynamicPageAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDynamicPage"


    // $ANTLR start "entryRuleIndexPage"
    // InternalSimpleWebModelingLanguage.g:303:1: entryRuleIndexPage : ruleIndexPage EOF ;
    public final void entryRuleIndexPage() throws RecognitionException {
        try {
            // InternalSimpleWebModelingLanguage.g:304:1: ( ruleIndexPage EOF )
            // InternalSimpleWebModelingLanguage.g:305:1: ruleIndexPage EOF
            {
             before(grammarAccess.getIndexPageRule()); 
            pushFollow(FOLLOW_1);
            ruleIndexPage();

            state._fsp--;

             after(grammarAccess.getIndexPageRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleIndexPage"


    // $ANTLR start "ruleIndexPage"
    // InternalSimpleWebModelingLanguage.g:312:1: ruleIndexPage : ( ( rule__IndexPage__Group__0 ) ) ;
    public final void ruleIndexPage() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:316:2: ( ( ( rule__IndexPage__Group__0 ) ) )
            // InternalSimpleWebModelingLanguage.g:317:2: ( ( rule__IndexPage__Group__0 ) )
            {
            // InternalSimpleWebModelingLanguage.g:317:2: ( ( rule__IndexPage__Group__0 ) )
            // InternalSimpleWebModelingLanguage.g:318:3: ( rule__IndexPage__Group__0 )
            {
             before(grammarAccess.getIndexPageAccess().getGroup()); 
            // InternalSimpleWebModelingLanguage.g:319:3: ( rule__IndexPage__Group__0 )
            // InternalSimpleWebModelingLanguage.g:319:4: rule__IndexPage__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__IndexPage__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getIndexPageAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleIndexPage"


    // $ANTLR start "entryRuleDataPage"
    // InternalSimpleWebModelingLanguage.g:328:1: entryRuleDataPage : ruleDataPage EOF ;
    public final void entryRuleDataPage() throws RecognitionException {
        try {
            // InternalSimpleWebModelingLanguage.g:329:1: ( ruleDataPage EOF )
            // InternalSimpleWebModelingLanguage.g:330:1: ruleDataPage EOF
            {
             before(grammarAccess.getDataPageRule()); 
            pushFollow(FOLLOW_1);
            ruleDataPage();

            state._fsp--;

             after(grammarAccess.getDataPageRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDataPage"


    // $ANTLR start "ruleDataPage"
    // InternalSimpleWebModelingLanguage.g:337:1: ruleDataPage : ( ( rule__DataPage__Group__0 ) ) ;
    public final void ruleDataPage() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:341:2: ( ( ( rule__DataPage__Group__0 ) ) )
            // InternalSimpleWebModelingLanguage.g:342:2: ( ( rule__DataPage__Group__0 ) )
            {
            // InternalSimpleWebModelingLanguage.g:342:2: ( ( rule__DataPage__Group__0 ) )
            // InternalSimpleWebModelingLanguage.g:343:3: ( rule__DataPage__Group__0 )
            {
             before(grammarAccess.getDataPageAccess().getGroup()); 
            // InternalSimpleWebModelingLanguage.g:344:3: ( rule__DataPage__Group__0 )
            // InternalSimpleWebModelingLanguage.g:344:4: rule__DataPage__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__DataPage__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getDataPageAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDataPage"


    // $ANTLR start "ruleSimpleType"
    // InternalSimpleWebModelingLanguage.g:353:1: ruleSimpleType : ( ( rule__SimpleType__Alternatives ) ) ;
    public final void ruleSimpleType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:357:1: ( ( ( rule__SimpleType__Alternatives ) ) )
            // InternalSimpleWebModelingLanguage.g:358:2: ( ( rule__SimpleType__Alternatives ) )
            {
            // InternalSimpleWebModelingLanguage.g:358:2: ( ( rule__SimpleType__Alternatives ) )
            // InternalSimpleWebModelingLanguage.g:359:3: ( rule__SimpleType__Alternatives )
            {
             before(grammarAccess.getSimpleTypeAccess().getAlternatives()); 
            // InternalSimpleWebModelingLanguage.g:360:3: ( rule__SimpleType__Alternatives )
            // InternalSimpleWebModelingLanguage.g:360:4: rule__SimpleType__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__SimpleType__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getSimpleTypeAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSimpleType"


    // $ANTLR start "rule__Page__Alternatives"
    // InternalSimpleWebModelingLanguage.g:368:1: rule__Page__Alternatives : ( ( ruleStaticPage ) | ( ruleDynamicPage ) );
    public final void rule__Page__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:372:1: ( ( ruleStaticPage ) | ( ruleDynamicPage ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==26) ) {
                alt1=1;
            }
            else if ( (LA1_0==28||LA1_0==30) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalSimpleWebModelingLanguage.g:373:2: ( ruleStaticPage )
                    {
                    // InternalSimpleWebModelingLanguage.g:373:2: ( ruleStaticPage )
                    // InternalSimpleWebModelingLanguage.g:374:3: ruleStaticPage
                    {
                     before(grammarAccess.getPageAccess().getStaticPageParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleStaticPage();

                    state._fsp--;

                     after(grammarAccess.getPageAccess().getStaticPageParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSimpleWebModelingLanguage.g:379:2: ( ruleDynamicPage )
                    {
                    // InternalSimpleWebModelingLanguage.g:379:2: ( ruleDynamicPage )
                    // InternalSimpleWebModelingLanguage.g:380:3: ruleDynamicPage
                    {
                     before(grammarAccess.getPageAccess().getDynamicPageParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleDynamicPage();

                    state._fsp--;

                     after(grammarAccess.getPageAccess().getDynamicPageParserRuleCall_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Page__Alternatives"


    // $ANTLR start "rule__DynamicPage__Alternatives"
    // InternalSimpleWebModelingLanguage.g:389:1: rule__DynamicPage__Alternatives : ( ( ruleIndexPage ) | ( ruleDataPage ) );
    public final void rule__DynamicPage__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:393:1: ( ( ruleIndexPage ) | ( ruleDataPage ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==28) ) {
                alt2=1;
            }
            else if ( (LA2_0==30) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalSimpleWebModelingLanguage.g:394:2: ( ruleIndexPage )
                    {
                    // InternalSimpleWebModelingLanguage.g:394:2: ( ruleIndexPage )
                    // InternalSimpleWebModelingLanguage.g:395:3: ruleIndexPage
                    {
                     before(grammarAccess.getDynamicPageAccess().getIndexPageParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleIndexPage();

                    state._fsp--;

                     after(grammarAccess.getDynamicPageAccess().getIndexPageParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSimpleWebModelingLanguage.g:400:2: ( ruleDataPage )
                    {
                    // InternalSimpleWebModelingLanguage.g:400:2: ( ruleDataPage )
                    // InternalSimpleWebModelingLanguage.g:401:3: ruleDataPage
                    {
                     before(grammarAccess.getDynamicPageAccess().getDataPageParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleDataPage();

                    state._fsp--;

                     after(grammarAccess.getDynamicPageAccess().getDataPageParserRuleCall_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DynamicPage__Alternatives"


    // $ANTLR start "rule__SimpleType__Alternatives"
    // InternalSimpleWebModelingLanguage.g:410:1: rule__SimpleType__Alternatives : ( ( ( 'Boolean' ) ) | ( ( 'Email' ) ) | ( ( 'Float' ) ) | ( ( 'Integer' ) ) | ( ( 'String' ) ) );
    public final void rule__SimpleType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:414:1: ( ( ( 'Boolean' ) ) | ( ( 'Email' ) ) | ( ( 'Float' ) ) | ( ( 'Integer' ) ) | ( ( 'String' ) ) )
            int alt3=5;
            switch ( input.LA(1) ) {
            case 11:
                {
                alt3=1;
                }
                break;
            case 12:
                {
                alt3=2;
                }
                break;
            case 13:
                {
                alt3=3;
                }
                break;
            case 14:
                {
                alt3=4;
                }
                break;
            case 15:
                {
                alt3=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // InternalSimpleWebModelingLanguage.g:415:2: ( ( 'Boolean' ) )
                    {
                    // InternalSimpleWebModelingLanguage.g:415:2: ( ( 'Boolean' ) )
                    // InternalSimpleWebModelingLanguage.g:416:3: ( 'Boolean' )
                    {
                     before(grammarAccess.getSimpleTypeAccess().getBooleanEnumLiteralDeclaration_0()); 
                    // InternalSimpleWebModelingLanguage.g:417:3: ( 'Boolean' )
                    // InternalSimpleWebModelingLanguage.g:417:4: 'Boolean'
                    {
                    match(input,11,FOLLOW_2); 

                    }

                     after(grammarAccess.getSimpleTypeAccess().getBooleanEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSimpleWebModelingLanguage.g:421:2: ( ( 'Email' ) )
                    {
                    // InternalSimpleWebModelingLanguage.g:421:2: ( ( 'Email' ) )
                    // InternalSimpleWebModelingLanguage.g:422:3: ( 'Email' )
                    {
                     before(grammarAccess.getSimpleTypeAccess().getEmailEnumLiteralDeclaration_1()); 
                    // InternalSimpleWebModelingLanguage.g:423:3: ( 'Email' )
                    // InternalSimpleWebModelingLanguage.g:423:4: 'Email'
                    {
                    match(input,12,FOLLOW_2); 

                    }

                     after(grammarAccess.getSimpleTypeAccess().getEmailEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalSimpleWebModelingLanguage.g:427:2: ( ( 'Float' ) )
                    {
                    // InternalSimpleWebModelingLanguage.g:427:2: ( ( 'Float' ) )
                    // InternalSimpleWebModelingLanguage.g:428:3: ( 'Float' )
                    {
                     before(grammarAccess.getSimpleTypeAccess().getFloatEnumLiteralDeclaration_2()); 
                    // InternalSimpleWebModelingLanguage.g:429:3: ( 'Float' )
                    // InternalSimpleWebModelingLanguage.g:429:4: 'Float'
                    {
                    match(input,13,FOLLOW_2); 

                    }

                     after(grammarAccess.getSimpleTypeAccess().getFloatEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalSimpleWebModelingLanguage.g:433:2: ( ( 'Integer' ) )
                    {
                    // InternalSimpleWebModelingLanguage.g:433:2: ( ( 'Integer' ) )
                    // InternalSimpleWebModelingLanguage.g:434:3: ( 'Integer' )
                    {
                     before(grammarAccess.getSimpleTypeAccess().getIntegerEnumLiteralDeclaration_3()); 
                    // InternalSimpleWebModelingLanguage.g:435:3: ( 'Integer' )
                    // InternalSimpleWebModelingLanguage.g:435:4: 'Integer'
                    {
                    match(input,14,FOLLOW_2); 

                    }

                     after(grammarAccess.getSimpleTypeAccess().getIntegerEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalSimpleWebModelingLanguage.g:439:2: ( ( 'String' ) )
                    {
                    // InternalSimpleWebModelingLanguage.g:439:2: ( ( 'String' ) )
                    // InternalSimpleWebModelingLanguage.g:440:3: ( 'String' )
                    {
                     before(grammarAccess.getSimpleTypeAccess().getStringEnumLiteralDeclaration_4()); 
                    // InternalSimpleWebModelingLanguage.g:441:3: ( 'String' )
                    // InternalSimpleWebModelingLanguage.g:441:4: 'String'
                    {
                    match(input,15,FOLLOW_2); 

                    }

                     after(grammarAccess.getSimpleTypeAccess().getStringEnumLiteralDeclaration_4()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleType__Alternatives"


    // $ANTLR start "rule__WebModel__Group__0"
    // InternalSimpleWebModelingLanguage.g:449:1: rule__WebModel__Group__0 : rule__WebModel__Group__0__Impl rule__WebModel__Group__1 ;
    public final void rule__WebModel__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:453:1: ( rule__WebModel__Group__0__Impl rule__WebModel__Group__1 )
            // InternalSimpleWebModelingLanguage.g:454:2: rule__WebModel__Group__0__Impl rule__WebModel__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__WebModel__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__WebModel__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WebModel__Group__0"


    // $ANTLR start "rule__WebModel__Group__0__Impl"
    // InternalSimpleWebModelingLanguage.g:461:1: rule__WebModel__Group__0__Impl : ( 'webmodel' ) ;
    public final void rule__WebModel__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:465:1: ( ( 'webmodel' ) )
            // InternalSimpleWebModelingLanguage.g:466:1: ( 'webmodel' )
            {
            // InternalSimpleWebModelingLanguage.g:466:1: ( 'webmodel' )
            // InternalSimpleWebModelingLanguage.g:467:2: 'webmodel'
            {
             before(grammarAccess.getWebModelAccess().getWebmodelKeyword_0()); 
            match(input,16,FOLLOW_2); 
             after(grammarAccess.getWebModelAccess().getWebmodelKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WebModel__Group__0__Impl"


    // $ANTLR start "rule__WebModel__Group__1"
    // InternalSimpleWebModelingLanguage.g:476:1: rule__WebModel__Group__1 : rule__WebModel__Group__1__Impl rule__WebModel__Group__2 ;
    public final void rule__WebModel__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:480:1: ( rule__WebModel__Group__1__Impl rule__WebModel__Group__2 )
            // InternalSimpleWebModelingLanguage.g:481:2: rule__WebModel__Group__1__Impl rule__WebModel__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__WebModel__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__WebModel__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WebModel__Group__1"


    // $ANTLR start "rule__WebModel__Group__1__Impl"
    // InternalSimpleWebModelingLanguage.g:488:1: rule__WebModel__Group__1__Impl : ( ( rule__WebModel__NameAssignment_1 ) ) ;
    public final void rule__WebModel__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:492:1: ( ( ( rule__WebModel__NameAssignment_1 ) ) )
            // InternalSimpleWebModelingLanguage.g:493:1: ( ( rule__WebModel__NameAssignment_1 ) )
            {
            // InternalSimpleWebModelingLanguage.g:493:1: ( ( rule__WebModel__NameAssignment_1 ) )
            // InternalSimpleWebModelingLanguage.g:494:2: ( rule__WebModel__NameAssignment_1 )
            {
             before(grammarAccess.getWebModelAccess().getNameAssignment_1()); 
            // InternalSimpleWebModelingLanguage.g:495:2: ( rule__WebModel__NameAssignment_1 )
            // InternalSimpleWebModelingLanguage.g:495:3: rule__WebModel__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__WebModel__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getWebModelAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WebModel__Group__1__Impl"


    // $ANTLR start "rule__WebModel__Group__2"
    // InternalSimpleWebModelingLanguage.g:503:1: rule__WebModel__Group__2 : rule__WebModel__Group__2__Impl rule__WebModel__Group__3 ;
    public final void rule__WebModel__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:507:1: ( rule__WebModel__Group__2__Impl rule__WebModel__Group__3 )
            // InternalSimpleWebModelingLanguage.g:508:2: rule__WebModel__Group__2__Impl rule__WebModel__Group__3
            {
            pushFollow(FOLLOW_5);
            rule__WebModel__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__WebModel__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WebModel__Group__2"


    // $ANTLR start "rule__WebModel__Group__2__Impl"
    // InternalSimpleWebModelingLanguage.g:515:1: rule__WebModel__Group__2__Impl : ( '{' ) ;
    public final void rule__WebModel__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:519:1: ( ( '{' ) )
            // InternalSimpleWebModelingLanguage.g:520:1: ( '{' )
            {
            // InternalSimpleWebModelingLanguage.g:520:1: ( '{' )
            // InternalSimpleWebModelingLanguage.g:521:2: '{'
            {
             before(grammarAccess.getWebModelAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getWebModelAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WebModel__Group__2__Impl"


    // $ANTLR start "rule__WebModel__Group__3"
    // InternalSimpleWebModelingLanguage.g:530:1: rule__WebModel__Group__3 : rule__WebModel__Group__3__Impl rule__WebModel__Group__4 ;
    public final void rule__WebModel__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:534:1: ( rule__WebModel__Group__3__Impl rule__WebModel__Group__4 )
            // InternalSimpleWebModelingLanguage.g:535:2: rule__WebModel__Group__3__Impl rule__WebModel__Group__4
            {
            pushFollow(FOLLOW_6);
            rule__WebModel__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__WebModel__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WebModel__Group__3"


    // $ANTLR start "rule__WebModel__Group__3__Impl"
    // InternalSimpleWebModelingLanguage.g:542:1: rule__WebModel__Group__3__Impl : ( ( rule__WebModel__DataLayerAssignment_3 ) ) ;
    public final void rule__WebModel__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:546:1: ( ( ( rule__WebModel__DataLayerAssignment_3 ) ) )
            // InternalSimpleWebModelingLanguage.g:547:1: ( ( rule__WebModel__DataLayerAssignment_3 ) )
            {
            // InternalSimpleWebModelingLanguage.g:547:1: ( ( rule__WebModel__DataLayerAssignment_3 ) )
            // InternalSimpleWebModelingLanguage.g:548:2: ( rule__WebModel__DataLayerAssignment_3 )
            {
             before(grammarAccess.getWebModelAccess().getDataLayerAssignment_3()); 
            // InternalSimpleWebModelingLanguage.g:549:2: ( rule__WebModel__DataLayerAssignment_3 )
            // InternalSimpleWebModelingLanguage.g:549:3: rule__WebModel__DataLayerAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__WebModel__DataLayerAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getWebModelAccess().getDataLayerAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WebModel__Group__3__Impl"


    // $ANTLR start "rule__WebModel__Group__4"
    // InternalSimpleWebModelingLanguage.g:557:1: rule__WebModel__Group__4 : rule__WebModel__Group__4__Impl rule__WebModel__Group__5 ;
    public final void rule__WebModel__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:561:1: ( rule__WebModel__Group__4__Impl rule__WebModel__Group__5 )
            // InternalSimpleWebModelingLanguage.g:562:2: rule__WebModel__Group__4__Impl rule__WebModel__Group__5
            {
            pushFollow(FOLLOW_7);
            rule__WebModel__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__WebModel__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WebModel__Group__4"


    // $ANTLR start "rule__WebModel__Group__4__Impl"
    // InternalSimpleWebModelingLanguage.g:569:1: rule__WebModel__Group__4__Impl : ( ( rule__WebModel__HypertextLayerAssignment_4 ) ) ;
    public final void rule__WebModel__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:573:1: ( ( ( rule__WebModel__HypertextLayerAssignment_4 ) ) )
            // InternalSimpleWebModelingLanguage.g:574:1: ( ( rule__WebModel__HypertextLayerAssignment_4 ) )
            {
            // InternalSimpleWebModelingLanguage.g:574:1: ( ( rule__WebModel__HypertextLayerAssignment_4 ) )
            // InternalSimpleWebModelingLanguage.g:575:2: ( rule__WebModel__HypertextLayerAssignment_4 )
            {
             before(grammarAccess.getWebModelAccess().getHypertextLayerAssignment_4()); 
            // InternalSimpleWebModelingLanguage.g:576:2: ( rule__WebModel__HypertextLayerAssignment_4 )
            // InternalSimpleWebModelingLanguage.g:576:3: rule__WebModel__HypertextLayerAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__WebModel__HypertextLayerAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getWebModelAccess().getHypertextLayerAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WebModel__Group__4__Impl"


    // $ANTLR start "rule__WebModel__Group__5"
    // InternalSimpleWebModelingLanguage.g:584:1: rule__WebModel__Group__5 : rule__WebModel__Group__5__Impl ;
    public final void rule__WebModel__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:588:1: ( rule__WebModel__Group__5__Impl )
            // InternalSimpleWebModelingLanguage.g:589:2: rule__WebModel__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__WebModel__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WebModel__Group__5"


    // $ANTLR start "rule__WebModel__Group__5__Impl"
    // InternalSimpleWebModelingLanguage.g:595:1: rule__WebModel__Group__5__Impl : ( '}' ) ;
    public final void rule__WebModel__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:599:1: ( ( '}' ) )
            // InternalSimpleWebModelingLanguage.g:600:1: ( '}' )
            {
            // InternalSimpleWebModelingLanguage.g:600:1: ( '}' )
            // InternalSimpleWebModelingLanguage.g:601:2: '}'
            {
             before(grammarAccess.getWebModelAccess().getRightCurlyBracketKeyword_5()); 
            match(input,18,FOLLOW_2); 
             after(grammarAccess.getWebModelAccess().getRightCurlyBracketKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WebModel__Group__5__Impl"


    // $ANTLR start "rule__DataLayer__Group__0"
    // InternalSimpleWebModelingLanguage.g:611:1: rule__DataLayer__Group__0 : rule__DataLayer__Group__0__Impl rule__DataLayer__Group__1 ;
    public final void rule__DataLayer__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:615:1: ( rule__DataLayer__Group__0__Impl rule__DataLayer__Group__1 )
            // InternalSimpleWebModelingLanguage.g:616:2: rule__DataLayer__Group__0__Impl rule__DataLayer__Group__1
            {
            pushFollow(FOLLOW_8);
            rule__DataLayer__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DataLayer__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataLayer__Group__0"


    // $ANTLR start "rule__DataLayer__Group__0__Impl"
    // InternalSimpleWebModelingLanguage.g:623:1: rule__DataLayer__Group__0__Impl : ( 'data {' ) ;
    public final void rule__DataLayer__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:627:1: ( ( 'data {' ) )
            // InternalSimpleWebModelingLanguage.g:628:1: ( 'data {' )
            {
            // InternalSimpleWebModelingLanguage.g:628:1: ( 'data {' )
            // InternalSimpleWebModelingLanguage.g:629:2: 'data {'
            {
             before(grammarAccess.getDataLayerAccess().getDataKeyword_0()); 
            match(input,19,FOLLOW_2); 
             after(grammarAccess.getDataLayerAccess().getDataKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataLayer__Group__0__Impl"


    // $ANTLR start "rule__DataLayer__Group__1"
    // InternalSimpleWebModelingLanguage.g:638:1: rule__DataLayer__Group__1 : rule__DataLayer__Group__1__Impl rule__DataLayer__Group__2 ;
    public final void rule__DataLayer__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:642:1: ( rule__DataLayer__Group__1__Impl rule__DataLayer__Group__2 )
            // InternalSimpleWebModelingLanguage.g:643:2: rule__DataLayer__Group__1__Impl rule__DataLayer__Group__2
            {
            pushFollow(FOLLOW_8);
            rule__DataLayer__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DataLayer__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataLayer__Group__1"


    // $ANTLR start "rule__DataLayer__Group__1__Impl"
    // InternalSimpleWebModelingLanguage.g:650:1: rule__DataLayer__Group__1__Impl : ( () ) ;
    public final void rule__DataLayer__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:654:1: ( ( () ) )
            // InternalSimpleWebModelingLanguage.g:655:1: ( () )
            {
            // InternalSimpleWebModelingLanguage.g:655:1: ( () )
            // InternalSimpleWebModelingLanguage.g:656:2: ()
            {
             before(grammarAccess.getDataLayerAccess().getDataLayerAction_1()); 
            // InternalSimpleWebModelingLanguage.g:657:2: ()
            // InternalSimpleWebModelingLanguage.g:657:3: 
            {
            }

             after(grammarAccess.getDataLayerAccess().getDataLayerAction_1()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataLayer__Group__1__Impl"


    // $ANTLR start "rule__DataLayer__Group__2"
    // InternalSimpleWebModelingLanguage.g:665:1: rule__DataLayer__Group__2 : rule__DataLayer__Group__2__Impl rule__DataLayer__Group__3 ;
    public final void rule__DataLayer__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:669:1: ( rule__DataLayer__Group__2__Impl rule__DataLayer__Group__3 )
            // InternalSimpleWebModelingLanguage.g:670:2: rule__DataLayer__Group__2__Impl rule__DataLayer__Group__3
            {
            pushFollow(FOLLOW_8);
            rule__DataLayer__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DataLayer__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataLayer__Group__2"


    // $ANTLR start "rule__DataLayer__Group__2__Impl"
    // InternalSimpleWebModelingLanguage.g:677:1: rule__DataLayer__Group__2__Impl : ( ( rule__DataLayer__EntitiesAssignment_2 )* ) ;
    public final void rule__DataLayer__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:681:1: ( ( ( rule__DataLayer__EntitiesAssignment_2 )* ) )
            // InternalSimpleWebModelingLanguage.g:682:1: ( ( rule__DataLayer__EntitiesAssignment_2 )* )
            {
            // InternalSimpleWebModelingLanguage.g:682:1: ( ( rule__DataLayer__EntitiesAssignment_2 )* )
            // InternalSimpleWebModelingLanguage.g:683:2: ( rule__DataLayer__EntitiesAssignment_2 )*
            {
             before(grammarAccess.getDataLayerAccess().getEntitiesAssignment_2()); 
            // InternalSimpleWebModelingLanguage.g:684:2: ( rule__DataLayer__EntitiesAssignment_2 )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==20) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalSimpleWebModelingLanguage.g:684:3: rule__DataLayer__EntitiesAssignment_2
            	    {
            	    pushFollow(FOLLOW_9);
            	    rule__DataLayer__EntitiesAssignment_2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

             after(grammarAccess.getDataLayerAccess().getEntitiesAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataLayer__Group__2__Impl"


    // $ANTLR start "rule__DataLayer__Group__3"
    // InternalSimpleWebModelingLanguage.g:692:1: rule__DataLayer__Group__3 : rule__DataLayer__Group__3__Impl ;
    public final void rule__DataLayer__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:696:1: ( rule__DataLayer__Group__3__Impl )
            // InternalSimpleWebModelingLanguage.g:697:2: rule__DataLayer__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DataLayer__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataLayer__Group__3"


    // $ANTLR start "rule__DataLayer__Group__3__Impl"
    // InternalSimpleWebModelingLanguage.g:703:1: rule__DataLayer__Group__3__Impl : ( '}' ) ;
    public final void rule__DataLayer__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:707:1: ( ( '}' ) )
            // InternalSimpleWebModelingLanguage.g:708:1: ( '}' )
            {
            // InternalSimpleWebModelingLanguage.g:708:1: ( '}' )
            // InternalSimpleWebModelingLanguage.g:709:2: '}'
            {
             before(grammarAccess.getDataLayerAccess().getRightCurlyBracketKeyword_3()); 
            match(input,18,FOLLOW_2); 
             after(grammarAccess.getDataLayerAccess().getRightCurlyBracketKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataLayer__Group__3__Impl"


    // $ANTLR start "rule__Entity__Group__0"
    // InternalSimpleWebModelingLanguage.g:719:1: rule__Entity__Group__0 : rule__Entity__Group__0__Impl rule__Entity__Group__1 ;
    public final void rule__Entity__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:723:1: ( rule__Entity__Group__0__Impl rule__Entity__Group__1 )
            // InternalSimpleWebModelingLanguage.g:724:2: rule__Entity__Group__0__Impl rule__Entity__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__Entity__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Entity__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Entity__Group__0"


    // $ANTLR start "rule__Entity__Group__0__Impl"
    // InternalSimpleWebModelingLanguage.g:731:1: rule__Entity__Group__0__Impl : ( 'entity' ) ;
    public final void rule__Entity__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:735:1: ( ( 'entity' ) )
            // InternalSimpleWebModelingLanguage.g:736:1: ( 'entity' )
            {
            // InternalSimpleWebModelingLanguage.g:736:1: ( 'entity' )
            // InternalSimpleWebModelingLanguage.g:737:2: 'entity'
            {
             before(grammarAccess.getEntityAccess().getEntityKeyword_0()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getEntityAccess().getEntityKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Entity__Group__0__Impl"


    // $ANTLR start "rule__Entity__Group__1"
    // InternalSimpleWebModelingLanguage.g:746:1: rule__Entity__Group__1 : rule__Entity__Group__1__Impl rule__Entity__Group__2 ;
    public final void rule__Entity__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:750:1: ( rule__Entity__Group__1__Impl rule__Entity__Group__2 )
            // InternalSimpleWebModelingLanguage.g:751:2: rule__Entity__Group__1__Impl rule__Entity__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__Entity__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Entity__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Entity__Group__1"


    // $ANTLR start "rule__Entity__Group__1__Impl"
    // InternalSimpleWebModelingLanguage.g:758:1: rule__Entity__Group__1__Impl : ( ( rule__Entity__NameAssignment_1 ) ) ;
    public final void rule__Entity__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:762:1: ( ( ( rule__Entity__NameAssignment_1 ) ) )
            // InternalSimpleWebModelingLanguage.g:763:1: ( ( rule__Entity__NameAssignment_1 ) )
            {
            // InternalSimpleWebModelingLanguage.g:763:1: ( ( rule__Entity__NameAssignment_1 ) )
            // InternalSimpleWebModelingLanguage.g:764:2: ( rule__Entity__NameAssignment_1 )
            {
             before(grammarAccess.getEntityAccess().getNameAssignment_1()); 
            // InternalSimpleWebModelingLanguage.g:765:2: ( rule__Entity__NameAssignment_1 )
            // InternalSimpleWebModelingLanguage.g:765:3: rule__Entity__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Entity__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getEntityAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Entity__Group__1__Impl"


    // $ANTLR start "rule__Entity__Group__2"
    // InternalSimpleWebModelingLanguage.g:773:1: rule__Entity__Group__2 : rule__Entity__Group__2__Impl rule__Entity__Group__3 ;
    public final void rule__Entity__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:777:1: ( rule__Entity__Group__2__Impl rule__Entity__Group__3 )
            // InternalSimpleWebModelingLanguage.g:778:2: rule__Entity__Group__2__Impl rule__Entity__Group__3
            {
            pushFollow(FOLLOW_10);
            rule__Entity__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Entity__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Entity__Group__2"


    // $ANTLR start "rule__Entity__Group__2__Impl"
    // InternalSimpleWebModelingLanguage.g:785:1: rule__Entity__Group__2__Impl : ( '{' ) ;
    public final void rule__Entity__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:789:1: ( ( '{' ) )
            // InternalSimpleWebModelingLanguage.g:790:1: ( '{' )
            {
            // InternalSimpleWebModelingLanguage.g:790:1: ( '{' )
            // InternalSimpleWebModelingLanguage.g:791:2: '{'
            {
             before(grammarAccess.getEntityAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getEntityAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Entity__Group__2__Impl"


    // $ANTLR start "rule__Entity__Group__3"
    // InternalSimpleWebModelingLanguage.g:800:1: rule__Entity__Group__3 : rule__Entity__Group__3__Impl rule__Entity__Group__4 ;
    public final void rule__Entity__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:804:1: ( rule__Entity__Group__3__Impl rule__Entity__Group__4 )
            // InternalSimpleWebModelingLanguage.g:805:2: rule__Entity__Group__3__Impl rule__Entity__Group__4
            {
            pushFollow(FOLLOW_10);
            rule__Entity__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Entity__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Entity__Group__3"


    // $ANTLR start "rule__Entity__Group__3__Impl"
    // InternalSimpleWebModelingLanguage.g:812:1: rule__Entity__Group__3__Impl : ( ( rule__Entity__AttributesAssignment_3 )* ) ;
    public final void rule__Entity__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:816:1: ( ( ( rule__Entity__AttributesAssignment_3 )* ) )
            // InternalSimpleWebModelingLanguage.g:817:1: ( ( rule__Entity__AttributesAssignment_3 )* )
            {
            // InternalSimpleWebModelingLanguage.g:817:1: ( ( rule__Entity__AttributesAssignment_3 )* )
            // InternalSimpleWebModelingLanguage.g:818:2: ( rule__Entity__AttributesAssignment_3 )*
            {
             before(grammarAccess.getEntityAccess().getAttributesAssignment_3()); 
            // InternalSimpleWebModelingLanguage.g:819:2: ( rule__Entity__AttributesAssignment_3 )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==21) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalSimpleWebModelingLanguage.g:819:3: rule__Entity__AttributesAssignment_3
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__Entity__AttributesAssignment_3();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

             after(grammarAccess.getEntityAccess().getAttributesAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Entity__Group__3__Impl"


    // $ANTLR start "rule__Entity__Group__4"
    // InternalSimpleWebModelingLanguage.g:827:1: rule__Entity__Group__4 : rule__Entity__Group__4__Impl rule__Entity__Group__5 ;
    public final void rule__Entity__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:831:1: ( rule__Entity__Group__4__Impl rule__Entity__Group__5 )
            // InternalSimpleWebModelingLanguage.g:832:2: rule__Entity__Group__4__Impl rule__Entity__Group__5
            {
            pushFollow(FOLLOW_10);
            rule__Entity__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Entity__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Entity__Group__4"


    // $ANTLR start "rule__Entity__Group__4__Impl"
    // InternalSimpleWebModelingLanguage.g:839:1: rule__Entity__Group__4__Impl : ( ( rule__Entity__ReferencesAssignment_4 )* ) ;
    public final void rule__Entity__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:843:1: ( ( ( rule__Entity__ReferencesAssignment_4 )* ) )
            // InternalSimpleWebModelingLanguage.g:844:1: ( ( rule__Entity__ReferencesAssignment_4 )* )
            {
            // InternalSimpleWebModelingLanguage.g:844:1: ( ( rule__Entity__ReferencesAssignment_4 )* )
            // InternalSimpleWebModelingLanguage.g:845:2: ( rule__Entity__ReferencesAssignment_4 )*
            {
             before(grammarAccess.getEntityAccess().getReferencesAssignment_4()); 
            // InternalSimpleWebModelingLanguage.g:846:2: ( rule__Entity__ReferencesAssignment_4 )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==23) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalSimpleWebModelingLanguage.g:846:3: rule__Entity__ReferencesAssignment_4
            	    {
            	    pushFollow(FOLLOW_12);
            	    rule__Entity__ReferencesAssignment_4();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

             after(grammarAccess.getEntityAccess().getReferencesAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Entity__Group__4__Impl"


    // $ANTLR start "rule__Entity__Group__5"
    // InternalSimpleWebModelingLanguage.g:854:1: rule__Entity__Group__5 : rule__Entity__Group__5__Impl ;
    public final void rule__Entity__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:858:1: ( rule__Entity__Group__5__Impl )
            // InternalSimpleWebModelingLanguage.g:859:2: rule__Entity__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Entity__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Entity__Group__5"


    // $ANTLR start "rule__Entity__Group__5__Impl"
    // InternalSimpleWebModelingLanguage.g:865:1: rule__Entity__Group__5__Impl : ( '}' ) ;
    public final void rule__Entity__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:869:1: ( ( '}' ) )
            // InternalSimpleWebModelingLanguage.g:870:1: ( '}' )
            {
            // InternalSimpleWebModelingLanguage.g:870:1: ( '}' )
            // InternalSimpleWebModelingLanguage.g:871:2: '}'
            {
             before(grammarAccess.getEntityAccess().getRightCurlyBracketKeyword_5()); 
            match(input,18,FOLLOW_2); 
             after(grammarAccess.getEntityAccess().getRightCurlyBracketKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Entity__Group__5__Impl"


    // $ANTLR start "rule__Attribute__Group__0"
    // InternalSimpleWebModelingLanguage.g:881:1: rule__Attribute__Group__0 : rule__Attribute__Group__0__Impl rule__Attribute__Group__1 ;
    public final void rule__Attribute__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:885:1: ( rule__Attribute__Group__0__Impl rule__Attribute__Group__1 )
            // InternalSimpleWebModelingLanguage.g:886:2: rule__Attribute__Group__0__Impl rule__Attribute__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__Attribute__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Attribute__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Attribute__Group__0"


    // $ANTLR start "rule__Attribute__Group__0__Impl"
    // InternalSimpleWebModelingLanguage.g:893:1: rule__Attribute__Group__0__Impl : ( 'att' ) ;
    public final void rule__Attribute__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:897:1: ( ( 'att' ) )
            // InternalSimpleWebModelingLanguage.g:898:1: ( 'att' )
            {
            // InternalSimpleWebModelingLanguage.g:898:1: ( 'att' )
            // InternalSimpleWebModelingLanguage.g:899:2: 'att'
            {
             before(grammarAccess.getAttributeAccess().getAttKeyword_0()); 
            match(input,21,FOLLOW_2); 
             after(grammarAccess.getAttributeAccess().getAttKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Attribute__Group__0__Impl"


    // $ANTLR start "rule__Attribute__Group__1"
    // InternalSimpleWebModelingLanguage.g:908:1: rule__Attribute__Group__1 : rule__Attribute__Group__1__Impl rule__Attribute__Group__2 ;
    public final void rule__Attribute__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:912:1: ( rule__Attribute__Group__1__Impl rule__Attribute__Group__2 )
            // InternalSimpleWebModelingLanguage.g:913:2: rule__Attribute__Group__1__Impl rule__Attribute__Group__2
            {
            pushFollow(FOLLOW_13);
            rule__Attribute__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Attribute__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Attribute__Group__1"


    // $ANTLR start "rule__Attribute__Group__1__Impl"
    // InternalSimpleWebModelingLanguage.g:920:1: rule__Attribute__Group__1__Impl : ( ( rule__Attribute__NameAssignment_1 ) ) ;
    public final void rule__Attribute__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:924:1: ( ( ( rule__Attribute__NameAssignment_1 ) ) )
            // InternalSimpleWebModelingLanguage.g:925:1: ( ( rule__Attribute__NameAssignment_1 ) )
            {
            // InternalSimpleWebModelingLanguage.g:925:1: ( ( rule__Attribute__NameAssignment_1 ) )
            // InternalSimpleWebModelingLanguage.g:926:2: ( rule__Attribute__NameAssignment_1 )
            {
             before(grammarAccess.getAttributeAccess().getNameAssignment_1()); 
            // InternalSimpleWebModelingLanguage.g:927:2: ( rule__Attribute__NameAssignment_1 )
            // InternalSimpleWebModelingLanguage.g:927:3: rule__Attribute__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Attribute__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getAttributeAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Attribute__Group__1__Impl"


    // $ANTLR start "rule__Attribute__Group__2"
    // InternalSimpleWebModelingLanguage.g:935:1: rule__Attribute__Group__2 : rule__Attribute__Group__2__Impl rule__Attribute__Group__3 ;
    public final void rule__Attribute__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:939:1: ( rule__Attribute__Group__2__Impl rule__Attribute__Group__3 )
            // InternalSimpleWebModelingLanguage.g:940:2: rule__Attribute__Group__2__Impl rule__Attribute__Group__3
            {
            pushFollow(FOLLOW_14);
            rule__Attribute__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Attribute__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Attribute__Group__2"


    // $ANTLR start "rule__Attribute__Group__2__Impl"
    // InternalSimpleWebModelingLanguage.g:947:1: rule__Attribute__Group__2__Impl : ( ':' ) ;
    public final void rule__Attribute__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:951:1: ( ( ':' ) )
            // InternalSimpleWebModelingLanguage.g:952:1: ( ':' )
            {
            // InternalSimpleWebModelingLanguage.g:952:1: ( ':' )
            // InternalSimpleWebModelingLanguage.g:953:2: ':'
            {
             before(grammarAccess.getAttributeAccess().getColonKeyword_2()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getAttributeAccess().getColonKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Attribute__Group__2__Impl"


    // $ANTLR start "rule__Attribute__Group__3"
    // InternalSimpleWebModelingLanguage.g:962:1: rule__Attribute__Group__3 : rule__Attribute__Group__3__Impl ;
    public final void rule__Attribute__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:966:1: ( rule__Attribute__Group__3__Impl )
            // InternalSimpleWebModelingLanguage.g:967:2: rule__Attribute__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Attribute__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Attribute__Group__3"


    // $ANTLR start "rule__Attribute__Group__3__Impl"
    // InternalSimpleWebModelingLanguage.g:973:1: rule__Attribute__Group__3__Impl : ( ( rule__Attribute__TypeAssignment_3 ) ) ;
    public final void rule__Attribute__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:977:1: ( ( ( rule__Attribute__TypeAssignment_3 ) ) )
            // InternalSimpleWebModelingLanguage.g:978:1: ( ( rule__Attribute__TypeAssignment_3 ) )
            {
            // InternalSimpleWebModelingLanguage.g:978:1: ( ( rule__Attribute__TypeAssignment_3 ) )
            // InternalSimpleWebModelingLanguage.g:979:2: ( rule__Attribute__TypeAssignment_3 )
            {
             before(grammarAccess.getAttributeAccess().getTypeAssignment_3()); 
            // InternalSimpleWebModelingLanguage.g:980:2: ( rule__Attribute__TypeAssignment_3 )
            // InternalSimpleWebModelingLanguage.g:980:3: rule__Attribute__TypeAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Attribute__TypeAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getAttributeAccess().getTypeAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Attribute__Group__3__Impl"


    // $ANTLR start "rule__Reference__Group__0"
    // InternalSimpleWebModelingLanguage.g:989:1: rule__Reference__Group__0 : rule__Reference__Group__0__Impl rule__Reference__Group__1 ;
    public final void rule__Reference__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:993:1: ( rule__Reference__Group__0__Impl rule__Reference__Group__1 )
            // InternalSimpleWebModelingLanguage.g:994:2: rule__Reference__Group__0__Impl rule__Reference__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__Reference__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Reference__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Reference__Group__0"


    // $ANTLR start "rule__Reference__Group__0__Impl"
    // InternalSimpleWebModelingLanguage.g:1001:1: rule__Reference__Group__0__Impl : ( 'ref' ) ;
    public final void rule__Reference__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1005:1: ( ( 'ref' ) )
            // InternalSimpleWebModelingLanguage.g:1006:1: ( 'ref' )
            {
            // InternalSimpleWebModelingLanguage.g:1006:1: ( 'ref' )
            // InternalSimpleWebModelingLanguage.g:1007:2: 'ref'
            {
             before(grammarAccess.getReferenceAccess().getRefKeyword_0()); 
            match(input,23,FOLLOW_2); 
             after(grammarAccess.getReferenceAccess().getRefKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Reference__Group__0__Impl"


    // $ANTLR start "rule__Reference__Group__1"
    // InternalSimpleWebModelingLanguage.g:1016:1: rule__Reference__Group__1 : rule__Reference__Group__1__Impl rule__Reference__Group__2 ;
    public final void rule__Reference__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1020:1: ( rule__Reference__Group__1__Impl rule__Reference__Group__2 )
            // InternalSimpleWebModelingLanguage.g:1021:2: rule__Reference__Group__1__Impl rule__Reference__Group__2
            {
            pushFollow(FOLLOW_13);
            rule__Reference__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Reference__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Reference__Group__1"


    // $ANTLR start "rule__Reference__Group__1__Impl"
    // InternalSimpleWebModelingLanguage.g:1028:1: rule__Reference__Group__1__Impl : ( ( rule__Reference__NameAssignment_1 ) ) ;
    public final void rule__Reference__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1032:1: ( ( ( rule__Reference__NameAssignment_1 ) ) )
            // InternalSimpleWebModelingLanguage.g:1033:1: ( ( rule__Reference__NameAssignment_1 ) )
            {
            // InternalSimpleWebModelingLanguage.g:1033:1: ( ( rule__Reference__NameAssignment_1 ) )
            // InternalSimpleWebModelingLanguage.g:1034:2: ( rule__Reference__NameAssignment_1 )
            {
             before(grammarAccess.getReferenceAccess().getNameAssignment_1()); 
            // InternalSimpleWebModelingLanguage.g:1035:2: ( rule__Reference__NameAssignment_1 )
            // InternalSimpleWebModelingLanguage.g:1035:3: rule__Reference__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Reference__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getReferenceAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Reference__Group__1__Impl"


    // $ANTLR start "rule__Reference__Group__2"
    // InternalSimpleWebModelingLanguage.g:1043:1: rule__Reference__Group__2 : rule__Reference__Group__2__Impl rule__Reference__Group__3 ;
    public final void rule__Reference__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1047:1: ( rule__Reference__Group__2__Impl rule__Reference__Group__3 )
            // InternalSimpleWebModelingLanguage.g:1048:2: rule__Reference__Group__2__Impl rule__Reference__Group__3
            {
            pushFollow(FOLLOW_3);
            rule__Reference__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Reference__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Reference__Group__2"


    // $ANTLR start "rule__Reference__Group__2__Impl"
    // InternalSimpleWebModelingLanguage.g:1055:1: rule__Reference__Group__2__Impl : ( ':' ) ;
    public final void rule__Reference__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1059:1: ( ( ':' ) )
            // InternalSimpleWebModelingLanguage.g:1060:1: ( ':' )
            {
            // InternalSimpleWebModelingLanguage.g:1060:1: ( ':' )
            // InternalSimpleWebModelingLanguage.g:1061:2: ':'
            {
             before(grammarAccess.getReferenceAccess().getColonKeyword_2()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getReferenceAccess().getColonKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Reference__Group__2__Impl"


    // $ANTLR start "rule__Reference__Group__3"
    // InternalSimpleWebModelingLanguage.g:1070:1: rule__Reference__Group__3 : rule__Reference__Group__3__Impl ;
    public final void rule__Reference__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1074:1: ( rule__Reference__Group__3__Impl )
            // InternalSimpleWebModelingLanguage.g:1075:2: rule__Reference__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Reference__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Reference__Group__3"


    // $ANTLR start "rule__Reference__Group__3__Impl"
    // InternalSimpleWebModelingLanguage.g:1081:1: rule__Reference__Group__3__Impl : ( ( rule__Reference__TypeAssignment_3 ) ) ;
    public final void rule__Reference__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1085:1: ( ( ( rule__Reference__TypeAssignment_3 ) ) )
            // InternalSimpleWebModelingLanguage.g:1086:1: ( ( rule__Reference__TypeAssignment_3 ) )
            {
            // InternalSimpleWebModelingLanguage.g:1086:1: ( ( rule__Reference__TypeAssignment_3 ) )
            // InternalSimpleWebModelingLanguage.g:1087:2: ( rule__Reference__TypeAssignment_3 )
            {
             before(grammarAccess.getReferenceAccess().getTypeAssignment_3()); 
            // InternalSimpleWebModelingLanguage.g:1088:2: ( rule__Reference__TypeAssignment_3 )
            // InternalSimpleWebModelingLanguage.g:1088:3: rule__Reference__TypeAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Reference__TypeAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getReferenceAccess().getTypeAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Reference__Group__3__Impl"


    // $ANTLR start "rule__HypertextLayer__Group__0"
    // InternalSimpleWebModelingLanguage.g:1097:1: rule__HypertextLayer__Group__0 : rule__HypertextLayer__Group__0__Impl rule__HypertextLayer__Group__1 ;
    public final void rule__HypertextLayer__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1101:1: ( rule__HypertextLayer__Group__0__Impl rule__HypertextLayer__Group__1 )
            // InternalSimpleWebModelingLanguage.g:1102:2: rule__HypertextLayer__Group__0__Impl rule__HypertextLayer__Group__1
            {
            pushFollow(FOLLOW_15);
            rule__HypertextLayer__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__HypertextLayer__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HypertextLayer__Group__0"


    // $ANTLR start "rule__HypertextLayer__Group__0__Impl"
    // InternalSimpleWebModelingLanguage.g:1109:1: rule__HypertextLayer__Group__0__Impl : ( 'hypertext {' ) ;
    public final void rule__HypertextLayer__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1113:1: ( ( 'hypertext {' ) )
            // InternalSimpleWebModelingLanguage.g:1114:1: ( 'hypertext {' )
            {
            // InternalSimpleWebModelingLanguage.g:1114:1: ( 'hypertext {' )
            // InternalSimpleWebModelingLanguage.g:1115:2: 'hypertext {'
            {
             before(grammarAccess.getHypertextLayerAccess().getHypertextKeyword_0()); 
            match(input,24,FOLLOW_2); 
             after(grammarAccess.getHypertextLayerAccess().getHypertextKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HypertextLayer__Group__0__Impl"


    // $ANTLR start "rule__HypertextLayer__Group__1"
    // InternalSimpleWebModelingLanguage.g:1124:1: rule__HypertextLayer__Group__1 : rule__HypertextLayer__Group__1__Impl rule__HypertextLayer__Group__2 ;
    public final void rule__HypertextLayer__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1128:1: ( rule__HypertextLayer__Group__1__Impl rule__HypertextLayer__Group__2 )
            // InternalSimpleWebModelingLanguage.g:1129:2: rule__HypertextLayer__Group__1__Impl rule__HypertextLayer__Group__2
            {
            pushFollow(FOLLOW_16);
            rule__HypertextLayer__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__HypertextLayer__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HypertextLayer__Group__1"


    // $ANTLR start "rule__HypertextLayer__Group__1__Impl"
    // InternalSimpleWebModelingLanguage.g:1136:1: rule__HypertextLayer__Group__1__Impl : ( ( ( rule__HypertextLayer__PagesAssignment_1 ) ) ( ( rule__HypertextLayer__PagesAssignment_1 )* ) ) ;
    public final void rule__HypertextLayer__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1140:1: ( ( ( ( rule__HypertextLayer__PagesAssignment_1 ) ) ( ( rule__HypertextLayer__PagesAssignment_1 )* ) ) )
            // InternalSimpleWebModelingLanguage.g:1141:1: ( ( ( rule__HypertextLayer__PagesAssignment_1 ) ) ( ( rule__HypertextLayer__PagesAssignment_1 )* ) )
            {
            // InternalSimpleWebModelingLanguage.g:1141:1: ( ( ( rule__HypertextLayer__PagesAssignment_1 ) ) ( ( rule__HypertextLayer__PagesAssignment_1 )* ) )
            // InternalSimpleWebModelingLanguage.g:1142:2: ( ( rule__HypertextLayer__PagesAssignment_1 ) ) ( ( rule__HypertextLayer__PagesAssignment_1 )* )
            {
            // InternalSimpleWebModelingLanguage.g:1142:2: ( ( rule__HypertextLayer__PagesAssignment_1 ) )
            // InternalSimpleWebModelingLanguage.g:1143:3: ( rule__HypertextLayer__PagesAssignment_1 )
            {
             before(grammarAccess.getHypertextLayerAccess().getPagesAssignment_1()); 
            // InternalSimpleWebModelingLanguage.g:1144:3: ( rule__HypertextLayer__PagesAssignment_1 )
            // InternalSimpleWebModelingLanguage.g:1144:4: rule__HypertextLayer__PagesAssignment_1
            {
            pushFollow(FOLLOW_17);
            rule__HypertextLayer__PagesAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getHypertextLayerAccess().getPagesAssignment_1()); 

            }

            // InternalSimpleWebModelingLanguage.g:1147:2: ( ( rule__HypertextLayer__PagesAssignment_1 )* )
            // InternalSimpleWebModelingLanguage.g:1148:3: ( rule__HypertextLayer__PagesAssignment_1 )*
            {
             before(grammarAccess.getHypertextLayerAccess().getPagesAssignment_1()); 
            // InternalSimpleWebModelingLanguage.g:1149:3: ( rule__HypertextLayer__PagesAssignment_1 )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==26||LA7_0==28||LA7_0==30) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalSimpleWebModelingLanguage.g:1149:4: rule__HypertextLayer__PagesAssignment_1
            	    {
            	    pushFollow(FOLLOW_17);
            	    rule__HypertextLayer__PagesAssignment_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

             after(grammarAccess.getHypertextLayerAccess().getPagesAssignment_1()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HypertextLayer__Group__1__Impl"


    // $ANTLR start "rule__HypertextLayer__Group__2"
    // InternalSimpleWebModelingLanguage.g:1158:1: rule__HypertextLayer__Group__2 : rule__HypertextLayer__Group__2__Impl rule__HypertextLayer__Group__3 ;
    public final void rule__HypertextLayer__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1162:1: ( rule__HypertextLayer__Group__2__Impl rule__HypertextLayer__Group__3 )
            // InternalSimpleWebModelingLanguage.g:1163:2: rule__HypertextLayer__Group__2__Impl rule__HypertextLayer__Group__3
            {
            pushFollow(FOLLOW_3);
            rule__HypertextLayer__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__HypertextLayer__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HypertextLayer__Group__2"


    // $ANTLR start "rule__HypertextLayer__Group__2__Impl"
    // InternalSimpleWebModelingLanguage.g:1170:1: rule__HypertextLayer__Group__2__Impl : ( 'start page is' ) ;
    public final void rule__HypertextLayer__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1174:1: ( ( 'start page is' ) )
            // InternalSimpleWebModelingLanguage.g:1175:1: ( 'start page is' )
            {
            // InternalSimpleWebModelingLanguage.g:1175:1: ( 'start page is' )
            // InternalSimpleWebModelingLanguage.g:1176:2: 'start page is'
            {
             before(grammarAccess.getHypertextLayerAccess().getStartPageIsKeyword_2()); 
            match(input,25,FOLLOW_2); 
             after(grammarAccess.getHypertextLayerAccess().getStartPageIsKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HypertextLayer__Group__2__Impl"


    // $ANTLR start "rule__HypertextLayer__Group__3"
    // InternalSimpleWebModelingLanguage.g:1185:1: rule__HypertextLayer__Group__3 : rule__HypertextLayer__Group__3__Impl rule__HypertextLayer__Group__4 ;
    public final void rule__HypertextLayer__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1189:1: ( rule__HypertextLayer__Group__3__Impl rule__HypertextLayer__Group__4 )
            // InternalSimpleWebModelingLanguage.g:1190:2: rule__HypertextLayer__Group__3__Impl rule__HypertextLayer__Group__4
            {
            pushFollow(FOLLOW_7);
            rule__HypertextLayer__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__HypertextLayer__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HypertextLayer__Group__3"


    // $ANTLR start "rule__HypertextLayer__Group__3__Impl"
    // InternalSimpleWebModelingLanguage.g:1197:1: rule__HypertextLayer__Group__3__Impl : ( ( rule__HypertextLayer__StartPageAssignment_3 ) ) ;
    public final void rule__HypertextLayer__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1201:1: ( ( ( rule__HypertextLayer__StartPageAssignment_3 ) ) )
            // InternalSimpleWebModelingLanguage.g:1202:1: ( ( rule__HypertextLayer__StartPageAssignment_3 ) )
            {
            // InternalSimpleWebModelingLanguage.g:1202:1: ( ( rule__HypertextLayer__StartPageAssignment_3 ) )
            // InternalSimpleWebModelingLanguage.g:1203:2: ( rule__HypertextLayer__StartPageAssignment_3 )
            {
             before(grammarAccess.getHypertextLayerAccess().getStartPageAssignment_3()); 
            // InternalSimpleWebModelingLanguage.g:1204:2: ( rule__HypertextLayer__StartPageAssignment_3 )
            // InternalSimpleWebModelingLanguage.g:1204:3: rule__HypertextLayer__StartPageAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__HypertextLayer__StartPageAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getHypertextLayerAccess().getStartPageAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HypertextLayer__Group__3__Impl"


    // $ANTLR start "rule__HypertextLayer__Group__4"
    // InternalSimpleWebModelingLanguage.g:1212:1: rule__HypertextLayer__Group__4 : rule__HypertextLayer__Group__4__Impl ;
    public final void rule__HypertextLayer__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1216:1: ( rule__HypertextLayer__Group__4__Impl )
            // InternalSimpleWebModelingLanguage.g:1217:2: rule__HypertextLayer__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__HypertextLayer__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HypertextLayer__Group__4"


    // $ANTLR start "rule__HypertextLayer__Group__4__Impl"
    // InternalSimpleWebModelingLanguage.g:1223:1: rule__HypertextLayer__Group__4__Impl : ( '}' ) ;
    public final void rule__HypertextLayer__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1227:1: ( ( '}' ) )
            // InternalSimpleWebModelingLanguage.g:1228:1: ( '}' )
            {
            // InternalSimpleWebModelingLanguage.g:1228:1: ( '}' )
            // InternalSimpleWebModelingLanguage.g:1229:2: '}'
            {
             before(grammarAccess.getHypertextLayerAccess().getRightCurlyBracketKeyword_4()); 
            match(input,18,FOLLOW_2); 
             after(grammarAccess.getHypertextLayerAccess().getRightCurlyBracketKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HypertextLayer__Group__4__Impl"


    // $ANTLR start "rule__StaticPage__Group__0"
    // InternalSimpleWebModelingLanguage.g:1239:1: rule__StaticPage__Group__0 : rule__StaticPage__Group__0__Impl rule__StaticPage__Group__1 ;
    public final void rule__StaticPage__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1243:1: ( rule__StaticPage__Group__0__Impl rule__StaticPage__Group__1 )
            // InternalSimpleWebModelingLanguage.g:1244:2: rule__StaticPage__Group__0__Impl rule__StaticPage__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__StaticPage__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__StaticPage__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StaticPage__Group__0"


    // $ANTLR start "rule__StaticPage__Group__0__Impl"
    // InternalSimpleWebModelingLanguage.g:1251:1: rule__StaticPage__Group__0__Impl : ( 'static page' ) ;
    public final void rule__StaticPage__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1255:1: ( ( 'static page' ) )
            // InternalSimpleWebModelingLanguage.g:1256:1: ( 'static page' )
            {
            // InternalSimpleWebModelingLanguage.g:1256:1: ( 'static page' )
            // InternalSimpleWebModelingLanguage.g:1257:2: 'static page'
            {
             before(grammarAccess.getStaticPageAccess().getStaticPageKeyword_0()); 
            match(input,26,FOLLOW_2); 
             after(grammarAccess.getStaticPageAccess().getStaticPageKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StaticPage__Group__0__Impl"


    // $ANTLR start "rule__StaticPage__Group__1"
    // InternalSimpleWebModelingLanguage.g:1266:1: rule__StaticPage__Group__1 : rule__StaticPage__Group__1__Impl rule__StaticPage__Group__2 ;
    public final void rule__StaticPage__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1270:1: ( rule__StaticPage__Group__1__Impl rule__StaticPage__Group__2 )
            // InternalSimpleWebModelingLanguage.g:1271:2: rule__StaticPage__Group__1__Impl rule__StaticPage__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__StaticPage__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__StaticPage__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StaticPage__Group__1"


    // $ANTLR start "rule__StaticPage__Group__1__Impl"
    // InternalSimpleWebModelingLanguage.g:1278:1: rule__StaticPage__Group__1__Impl : ( ( rule__StaticPage__NameAssignment_1 ) ) ;
    public final void rule__StaticPage__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1282:1: ( ( ( rule__StaticPage__NameAssignment_1 ) ) )
            // InternalSimpleWebModelingLanguage.g:1283:1: ( ( rule__StaticPage__NameAssignment_1 ) )
            {
            // InternalSimpleWebModelingLanguage.g:1283:1: ( ( rule__StaticPage__NameAssignment_1 ) )
            // InternalSimpleWebModelingLanguage.g:1284:2: ( rule__StaticPage__NameAssignment_1 )
            {
             before(grammarAccess.getStaticPageAccess().getNameAssignment_1()); 
            // InternalSimpleWebModelingLanguage.g:1285:2: ( rule__StaticPage__NameAssignment_1 )
            // InternalSimpleWebModelingLanguage.g:1285:3: rule__StaticPage__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__StaticPage__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getStaticPageAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StaticPage__Group__1__Impl"


    // $ANTLR start "rule__StaticPage__Group__2"
    // InternalSimpleWebModelingLanguage.g:1293:1: rule__StaticPage__Group__2 : rule__StaticPage__Group__2__Impl rule__StaticPage__Group__3 ;
    public final void rule__StaticPage__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1297:1: ( rule__StaticPage__Group__2__Impl rule__StaticPage__Group__3 )
            // InternalSimpleWebModelingLanguage.g:1298:2: rule__StaticPage__Group__2__Impl rule__StaticPage__Group__3
            {
            pushFollow(FOLLOW_18);
            rule__StaticPage__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__StaticPage__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StaticPage__Group__2"


    // $ANTLR start "rule__StaticPage__Group__2__Impl"
    // InternalSimpleWebModelingLanguage.g:1305:1: rule__StaticPage__Group__2__Impl : ( '{' ) ;
    public final void rule__StaticPage__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1309:1: ( ( '{' ) )
            // InternalSimpleWebModelingLanguage.g:1310:1: ( '{' )
            {
            // InternalSimpleWebModelingLanguage.g:1310:1: ( '{' )
            // InternalSimpleWebModelingLanguage.g:1311:2: '{'
            {
             before(grammarAccess.getStaticPageAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getStaticPageAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StaticPage__Group__2__Impl"


    // $ANTLR start "rule__StaticPage__Group__3"
    // InternalSimpleWebModelingLanguage.g:1320:1: rule__StaticPage__Group__3 : rule__StaticPage__Group__3__Impl rule__StaticPage__Group__4 ;
    public final void rule__StaticPage__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1324:1: ( rule__StaticPage__Group__3__Impl rule__StaticPage__Group__4 )
            // InternalSimpleWebModelingLanguage.g:1325:2: rule__StaticPage__Group__3__Impl rule__StaticPage__Group__4
            {
            pushFollow(FOLLOW_18);
            rule__StaticPage__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__StaticPage__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StaticPage__Group__3"


    // $ANTLR start "rule__StaticPage__Group__3__Impl"
    // InternalSimpleWebModelingLanguage.g:1332:1: rule__StaticPage__Group__3__Impl : ( ( rule__StaticPage__LinksAssignment_3 )* ) ;
    public final void rule__StaticPage__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1336:1: ( ( ( rule__StaticPage__LinksAssignment_3 )* ) )
            // InternalSimpleWebModelingLanguage.g:1337:1: ( ( rule__StaticPage__LinksAssignment_3 )* )
            {
            // InternalSimpleWebModelingLanguage.g:1337:1: ( ( rule__StaticPage__LinksAssignment_3 )* )
            // InternalSimpleWebModelingLanguage.g:1338:2: ( rule__StaticPage__LinksAssignment_3 )*
            {
             before(grammarAccess.getStaticPageAccess().getLinksAssignment_3()); 
            // InternalSimpleWebModelingLanguage.g:1339:2: ( rule__StaticPage__LinksAssignment_3 )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==27) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalSimpleWebModelingLanguage.g:1339:3: rule__StaticPage__LinksAssignment_3
            	    {
            	    pushFollow(FOLLOW_19);
            	    rule__StaticPage__LinksAssignment_3();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

             after(grammarAccess.getStaticPageAccess().getLinksAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StaticPage__Group__3__Impl"


    // $ANTLR start "rule__StaticPage__Group__4"
    // InternalSimpleWebModelingLanguage.g:1347:1: rule__StaticPage__Group__4 : rule__StaticPage__Group__4__Impl ;
    public final void rule__StaticPage__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1351:1: ( rule__StaticPage__Group__4__Impl )
            // InternalSimpleWebModelingLanguage.g:1352:2: rule__StaticPage__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__StaticPage__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StaticPage__Group__4"


    // $ANTLR start "rule__StaticPage__Group__4__Impl"
    // InternalSimpleWebModelingLanguage.g:1358:1: rule__StaticPage__Group__4__Impl : ( '}' ) ;
    public final void rule__StaticPage__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1362:1: ( ( '}' ) )
            // InternalSimpleWebModelingLanguage.g:1363:1: ( '}' )
            {
            // InternalSimpleWebModelingLanguage.g:1363:1: ( '}' )
            // InternalSimpleWebModelingLanguage.g:1364:2: '}'
            {
             before(grammarAccess.getStaticPageAccess().getRightCurlyBracketKeyword_4()); 
            match(input,18,FOLLOW_2); 
             after(grammarAccess.getStaticPageAccess().getRightCurlyBracketKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StaticPage__Group__4__Impl"


    // $ANTLR start "rule__Link__Group__0"
    // InternalSimpleWebModelingLanguage.g:1374:1: rule__Link__Group__0 : rule__Link__Group__0__Impl rule__Link__Group__1 ;
    public final void rule__Link__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1378:1: ( rule__Link__Group__0__Impl rule__Link__Group__1 )
            // InternalSimpleWebModelingLanguage.g:1379:2: rule__Link__Group__0__Impl rule__Link__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__Link__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Link__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group__0"


    // $ANTLR start "rule__Link__Group__0__Impl"
    // InternalSimpleWebModelingLanguage.g:1386:1: rule__Link__Group__0__Impl : ( 'link to page' ) ;
    public final void rule__Link__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1390:1: ( ( 'link to page' ) )
            // InternalSimpleWebModelingLanguage.g:1391:1: ( 'link to page' )
            {
            // InternalSimpleWebModelingLanguage.g:1391:1: ( 'link to page' )
            // InternalSimpleWebModelingLanguage.g:1392:2: 'link to page'
            {
             before(grammarAccess.getLinkAccess().getLinkToPageKeyword_0()); 
            match(input,27,FOLLOW_2); 
             after(grammarAccess.getLinkAccess().getLinkToPageKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group__0__Impl"


    // $ANTLR start "rule__Link__Group__1"
    // InternalSimpleWebModelingLanguage.g:1401:1: rule__Link__Group__1 : rule__Link__Group__1__Impl ;
    public final void rule__Link__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1405:1: ( rule__Link__Group__1__Impl )
            // InternalSimpleWebModelingLanguage.g:1406:2: rule__Link__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Link__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group__1"


    // $ANTLR start "rule__Link__Group__1__Impl"
    // InternalSimpleWebModelingLanguage.g:1412:1: rule__Link__Group__1__Impl : ( ( rule__Link__TargetAssignment_1 ) ) ;
    public final void rule__Link__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1416:1: ( ( ( rule__Link__TargetAssignment_1 ) ) )
            // InternalSimpleWebModelingLanguage.g:1417:1: ( ( rule__Link__TargetAssignment_1 ) )
            {
            // InternalSimpleWebModelingLanguage.g:1417:1: ( ( rule__Link__TargetAssignment_1 ) )
            // InternalSimpleWebModelingLanguage.g:1418:2: ( rule__Link__TargetAssignment_1 )
            {
             before(grammarAccess.getLinkAccess().getTargetAssignment_1()); 
            // InternalSimpleWebModelingLanguage.g:1419:2: ( rule__Link__TargetAssignment_1 )
            // InternalSimpleWebModelingLanguage.g:1419:3: rule__Link__TargetAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Link__TargetAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getLinkAccess().getTargetAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group__1__Impl"


    // $ANTLR start "rule__IndexPage__Group__0"
    // InternalSimpleWebModelingLanguage.g:1428:1: rule__IndexPage__Group__0 : rule__IndexPage__Group__0__Impl rule__IndexPage__Group__1 ;
    public final void rule__IndexPage__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1432:1: ( rule__IndexPage__Group__0__Impl rule__IndexPage__Group__1 )
            // InternalSimpleWebModelingLanguage.g:1433:2: rule__IndexPage__Group__0__Impl rule__IndexPage__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__IndexPage__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__IndexPage__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group__0"


    // $ANTLR start "rule__IndexPage__Group__0__Impl"
    // InternalSimpleWebModelingLanguage.g:1440:1: rule__IndexPage__Group__0__Impl : ( 'index page' ) ;
    public final void rule__IndexPage__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1444:1: ( ( 'index page' ) )
            // InternalSimpleWebModelingLanguage.g:1445:1: ( 'index page' )
            {
            // InternalSimpleWebModelingLanguage.g:1445:1: ( 'index page' )
            // InternalSimpleWebModelingLanguage.g:1446:2: 'index page'
            {
             before(grammarAccess.getIndexPageAccess().getIndexPageKeyword_0()); 
            match(input,28,FOLLOW_2); 
             after(grammarAccess.getIndexPageAccess().getIndexPageKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group__0__Impl"


    // $ANTLR start "rule__IndexPage__Group__1"
    // InternalSimpleWebModelingLanguage.g:1455:1: rule__IndexPage__Group__1 : rule__IndexPage__Group__1__Impl rule__IndexPage__Group__2 ;
    public final void rule__IndexPage__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1459:1: ( rule__IndexPage__Group__1__Impl rule__IndexPage__Group__2 )
            // InternalSimpleWebModelingLanguage.g:1460:2: rule__IndexPage__Group__1__Impl rule__IndexPage__Group__2
            {
            pushFollow(FOLLOW_20);
            rule__IndexPage__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__IndexPage__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group__1"


    // $ANTLR start "rule__IndexPage__Group__1__Impl"
    // InternalSimpleWebModelingLanguage.g:1467:1: rule__IndexPage__Group__1__Impl : ( ( rule__IndexPage__NameAssignment_1 ) ) ;
    public final void rule__IndexPage__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1471:1: ( ( ( rule__IndexPage__NameAssignment_1 ) ) )
            // InternalSimpleWebModelingLanguage.g:1472:1: ( ( rule__IndexPage__NameAssignment_1 ) )
            {
            // InternalSimpleWebModelingLanguage.g:1472:1: ( ( rule__IndexPage__NameAssignment_1 ) )
            // InternalSimpleWebModelingLanguage.g:1473:2: ( rule__IndexPage__NameAssignment_1 )
            {
             before(grammarAccess.getIndexPageAccess().getNameAssignment_1()); 
            // InternalSimpleWebModelingLanguage.g:1474:2: ( rule__IndexPage__NameAssignment_1 )
            // InternalSimpleWebModelingLanguage.g:1474:3: rule__IndexPage__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__IndexPage__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getIndexPageAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group__1__Impl"


    // $ANTLR start "rule__IndexPage__Group__2"
    // InternalSimpleWebModelingLanguage.g:1482:1: rule__IndexPage__Group__2 : rule__IndexPage__Group__2__Impl rule__IndexPage__Group__3 ;
    public final void rule__IndexPage__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1486:1: ( rule__IndexPage__Group__2__Impl rule__IndexPage__Group__3 )
            // InternalSimpleWebModelingLanguage.g:1487:2: rule__IndexPage__Group__2__Impl rule__IndexPage__Group__3
            {
            pushFollow(FOLLOW_20);
            rule__IndexPage__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__IndexPage__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group__2"


    // $ANTLR start "rule__IndexPage__Group__2__Impl"
    // InternalSimpleWebModelingLanguage.g:1494:1: rule__IndexPage__Group__2__Impl : ( ( rule__IndexPage__Group_2__0 )? ) ;
    public final void rule__IndexPage__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1498:1: ( ( ( rule__IndexPage__Group_2__0 )? ) )
            // InternalSimpleWebModelingLanguage.g:1499:1: ( ( rule__IndexPage__Group_2__0 )? )
            {
            // InternalSimpleWebModelingLanguage.g:1499:1: ( ( rule__IndexPage__Group_2__0 )? )
            // InternalSimpleWebModelingLanguage.g:1500:2: ( rule__IndexPage__Group_2__0 )?
            {
             before(grammarAccess.getIndexPageAccess().getGroup_2()); 
            // InternalSimpleWebModelingLanguage.g:1501:2: ( rule__IndexPage__Group_2__0 )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==29) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalSimpleWebModelingLanguage.g:1501:3: rule__IndexPage__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__IndexPage__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getIndexPageAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group__2__Impl"


    // $ANTLR start "rule__IndexPage__Group__3"
    // InternalSimpleWebModelingLanguage.g:1509:1: rule__IndexPage__Group__3 : rule__IndexPage__Group__3__Impl rule__IndexPage__Group__4 ;
    public final void rule__IndexPage__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1513:1: ( rule__IndexPage__Group__3__Impl rule__IndexPage__Group__4 )
            // InternalSimpleWebModelingLanguage.g:1514:2: rule__IndexPage__Group__3__Impl rule__IndexPage__Group__4
            {
            pushFollow(FOLLOW_18);
            rule__IndexPage__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__IndexPage__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group__3"


    // $ANTLR start "rule__IndexPage__Group__3__Impl"
    // InternalSimpleWebModelingLanguage.g:1521:1: rule__IndexPage__Group__3__Impl : ( '{' ) ;
    public final void rule__IndexPage__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1525:1: ( ( '{' ) )
            // InternalSimpleWebModelingLanguage.g:1526:1: ( '{' )
            {
            // InternalSimpleWebModelingLanguage.g:1526:1: ( '{' )
            // InternalSimpleWebModelingLanguage.g:1527:2: '{'
            {
             before(grammarAccess.getIndexPageAccess().getLeftCurlyBracketKeyword_3()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getIndexPageAccess().getLeftCurlyBracketKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group__3__Impl"


    // $ANTLR start "rule__IndexPage__Group__4"
    // InternalSimpleWebModelingLanguage.g:1536:1: rule__IndexPage__Group__4 : rule__IndexPage__Group__4__Impl rule__IndexPage__Group__5 ;
    public final void rule__IndexPage__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1540:1: ( rule__IndexPage__Group__4__Impl rule__IndexPage__Group__5 )
            // InternalSimpleWebModelingLanguage.g:1541:2: rule__IndexPage__Group__4__Impl rule__IndexPage__Group__5
            {
            pushFollow(FOLLOW_18);
            rule__IndexPage__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__IndexPage__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group__4"


    // $ANTLR start "rule__IndexPage__Group__4__Impl"
    // InternalSimpleWebModelingLanguage.g:1548:1: rule__IndexPage__Group__4__Impl : ( ( rule__IndexPage__LinksAssignment_4 )* ) ;
    public final void rule__IndexPage__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1552:1: ( ( ( rule__IndexPage__LinksAssignment_4 )* ) )
            // InternalSimpleWebModelingLanguage.g:1553:1: ( ( rule__IndexPage__LinksAssignment_4 )* )
            {
            // InternalSimpleWebModelingLanguage.g:1553:1: ( ( rule__IndexPage__LinksAssignment_4 )* )
            // InternalSimpleWebModelingLanguage.g:1554:2: ( rule__IndexPage__LinksAssignment_4 )*
            {
             before(grammarAccess.getIndexPageAccess().getLinksAssignment_4()); 
            // InternalSimpleWebModelingLanguage.g:1555:2: ( rule__IndexPage__LinksAssignment_4 )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==27) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalSimpleWebModelingLanguage.g:1555:3: rule__IndexPage__LinksAssignment_4
            	    {
            	    pushFollow(FOLLOW_19);
            	    rule__IndexPage__LinksAssignment_4();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

             after(grammarAccess.getIndexPageAccess().getLinksAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group__4__Impl"


    // $ANTLR start "rule__IndexPage__Group__5"
    // InternalSimpleWebModelingLanguage.g:1563:1: rule__IndexPage__Group__5 : rule__IndexPage__Group__5__Impl ;
    public final void rule__IndexPage__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1567:1: ( rule__IndexPage__Group__5__Impl )
            // InternalSimpleWebModelingLanguage.g:1568:2: rule__IndexPage__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__IndexPage__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group__5"


    // $ANTLR start "rule__IndexPage__Group__5__Impl"
    // InternalSimpleWebModelingLanguage.g:1574:1: rule__IndexPage__Group__5__Impl : ( '}' ) ;
    public final void rule__IndexPage__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1578:1: ( ( '}' ) )
            // InternalSimpleWebModelingLanguage.g:1579:1: ( '}' )
            {
            // InternalSimpleWebModelingLanguage.g:1579:1: ( '}' )
            // InternalSimpleWebModelingLanguage.g:1580:2: '}'
            {
             before(grammarAccess.getIndexPageAccess().getRightCurlyBracketKeyword_5()); 
            match(input,18,FOLLOW_2); 
             after(grammarAccess.getIndexPageAccess().getRightCurlyBracketKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group__5__Impl"


    // $ANTLR start "rule__IndexPage__Group_2__0"
    // InternalSimpleWebModelingLanguage.g:1590:1: rule__IndexPage__Group_2__0 : rule__IndexPage__Group_2__0__Impl rule__IndexPage__Group_2__1 ;
    public final void rule__IndexPage__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1594:1: ( rule__IndexPage__Group_2__0__Impl rule__IndexPage__Group_2__1 )
            // InternalSimpleWebModelingLanguage.g:1595:2: rule__IndexPage__Group_2__0__Impl rule__IndexPage__Group_2__1
            {
            pushFollow(FOLLOW_3);
            rule__IndexPage__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__IndexPage__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group_2__0"


    // $ANTLR start "rule__IndexPage__Group_2__0__Impl"
    // InternalSimpleWebModelingLanguage.g:1602:1: rule__IndexPage__Group_2__0__Impl : ( 'shows entity' ) ;
    public final void rule__IndexPage__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1606:1: ( ( 'shows entity' ) )
            // InternalSimpleWebModelingLanguage.g:1607:1: ( 'shows entity' )
            {
            // InternalSimpleWebModelingLanguage.g:1607:1: ( 'shows entity' )
            // InternalSimpleWebModelingLanguage.g:1608:2: 'shows entity'
            {
             before(grammarAccess.getIndexPageAccess().getShowsEntityKeyword_2_0()); 
            match(input,29,FOLLOW_2); 
             after(grammarAccess.getIndexPageAccess().getShowsEntityKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group_2__0__Impl"


    // $ANTLR start "rule__IndexPage__Group_2__1"
    // InternalSimpleWebModelingLanguage.g:1617:1: rule__IndexPage__Group_2__1 : rule__IndexPage__Group_2__1__Impl ;
    public final void rule__IndexPage__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1621:1: ( rule__IndexPage__Group_2__1__Impl )
            // InternalSimpleWebModelingLanguage.g:1622:2: rule__IndexPage__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__IndexPage__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group_2__1"


    // $ANTLR start "rule__IndexPage__Group_2__1__Impl"
    // InternalSimpleWebModelingLanguage.g:1628:1: rule__IndexPage__Group_2__1__Impl : ( ( rule__IndexPage__EntityAssignment_2_1 ) ) ;
    public final void rule__IndexPage__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1632:1: ( ( ( rule__IndexPage__EntityAssignment_2_1 ) ) )
            // InternalSimpleWebModelingLanguage.g:1633:1: ( ( rule__IndexPage__EntityAssignment_2_1 ) )
            {
            // InternalSimpleWebModelingLanguage.g:1633:1: ( ( rule__IndexPage__EntityAssignment_2_1 ) )
            // InternalSimpleWebModelingLanguage.g:1634:2: ( rule__IndexPage__EntityAssignment_2_1 )
            {
             before(grammarAccess.getIndexPageAccess().getEntityAssignment_2_1()); 
            // InternalSimpleWebModelingLanguage.g:1635:2: ( rule__IndexPage__EntityAssignment_2_1 )
            // InternalSimpleWebModelingLanguage.g:1635:3: rule__IndexPage__EntityAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__IndexPage__EntityAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getIndexPageAccess().getEntityAssignment_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__Group_2__1__Impl"


    // $ANTLR start "rule__DataPage__Group__0"
    // InternalSimpleWebModelingLanguage.g:1644:1: rule__DataPage__Group__0 : rule__DataPage__Group__0__Impl rule__DataPage__Group__1 ;
    public final void rule__DataPage__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1648:1: ( rule__DataPage__Group__0__Impl rule__DataPage__Group__1 )
            // InternalSimpleWebModelingLanguage.g:1649:2: rule__DataPage__Group__0__Impl rule__DataPage__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__DataPage__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DataPage__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group__0"


    // $ANTLR start "rule__DataPage__Group__0__Impl"
    // InternalSimpleWebModelingLanguage.g:1656:1: rule__DataPage__Group__0__Impl : ( 'data page' ) ;
    public final void rule__DataPage__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1660:1: ( ( 'data page' ) )
            // InternalSimpleWebModelingLanguage.g:1661:1: ( 'data page' )
            {
            // InternalSimpleWebModelingLanguage.g:1661:1: ( 'data page' )
            // InternalSimpleWebModelingLanguage.g:1662:2: 'data page'
            {
             before(grammarAccess.getDataPageAccess().getDataPageKeyword_0()); 
            match(input,30,FOLLOW_2); 
             after(grammarAccess.getDataPageAccess().getDataPageKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group__0__Impl"


    // $ANTLR start "rule__DataPage__Group__1"
    // InternalSimpleWebModelingLanguage.g:1671:1: rule__DataPage__Group__1 : rule__DataPage__Group__1__Impl rule__DataPage__Group__2 ;
    public final void rule__DataPage__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1675:1: ( rule__DataPage__Group__1__Impl rule__DataPage__Group__2 )
            // InternalSimpleWebModelingLanguage.g:1676:2: rule__DataPage__Group__1__Impl rule__DataPage__Group__2
            {
            pushFollow(FOLLOW_20);
            rule__DataPage__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DataPage__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group__1"


    // $ANTLR start "rule__DataPage__Group__1__Impl"
    // InternalSimpleWebModelingLanguage.g:1683:1: rule__DataPage__Group__1__Impl : ( ( rule__DataPage__NameAssignment_1 ) ) ;
    public final void rule__DataPage__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1687:1: ( ( ( rule__DataPage__NameAssignment_1 ) ) )
            // InternalSimpleWebModelingLanguage.g:1688:1: ( ( rule__DataPage__NameAssignment_1 ) )
            {
            // InternalSimpleWebModelingLanguage.g:1688:1: ( ( rule__DataPage__NameAssignment_1 ) )
            // InternalSimpleWebModelingLanguage.g:1689:2: ( rule__DataPage__NameAssignment_1 )
            {
             before(grammarAccess.getDataPageAccess().getNameAssignment_1()); 
            // InternalSimpleWebModelingLanguage.g:1690:2: ( rule__DataPage__NameAssignment_1 )
            // InternalSimpleWebModelingLanguage.g:1690:3: rule__DataPage__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__DataPage__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getDataPageAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group__1__Impl"


    // $ANTLR start "rule__DataPage__Group__2"
    // InternalSimpleWebModelingLanguage.g:1698:1: rule__DataPage__Group__2 : rule__DataPage__Group__2__Impl rule__DataPage__Group__3 ;
    public final void rule__DataPage__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1702:1: ( rule__DataPage__Group__2__Impl rule__DataPage__Group__3 )
            // InternalSimpleWebModelingLanguage.g:1703:2: rule__DataPage__Group__2__Impl rule__DataPage__Group__3
            {
            pushFollow(FOLLOW_20);
            rule__DataPage__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DataPage__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group__2"


    // $ANTLR start "rule__DataPage__Group__2__Impl"
    // InternalSimpleWebModelingLanguage.g:1710:1: rule__DataPage__Group__2__Impl : ( ( rule__DataPage__Group_2__0 )? ) ;
    public final void rule__DataPage__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1714:1: ( ( ( rule__DataPage__Group_2__0 )? ) )
            // InternalSimpleWebModelingLanguage.g:1715:1: ( ( rule__DataPage__Group_2__0 )? )
            {
            // InternalSimpleWebModelingLanguage.g:1715:1: ( ( rule__DataPage__Group_2__0 )? )
            // InternalSimpleWebModelingLanguage.g:1716:2: ( rule__DataPage__Group_2__0 )?
            {
             before(grammarAccess.getDataPageAccess().getGroup_2()); 
            // InternalSimpleWebModelingLanguage.g:1717:2: ( rule__DataPage__Group_2__0 )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==29) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalSimpleWebModelingLanguage.g:1717:3: rule__DataPage__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DataPage__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDataPageAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group__2__Impl"


    // $ANTLR start "rule__DataPage__Group__3"
    // InternalSimpleWebModelingLanguage.g:1725:1: rule__DataPage__Group__3 : rule__DataPage__Group__3__Impl rule__DataPage__Group__4 ;
    public final void rule__DataPage__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1729:1: ( rule__DataPage__Group__3__Impl rule__DataPage__Group__4 )
            // InternalSimpleWebModelingLanguage.g:1730:2: rule__DataPage__Group__3__Impl rule__DataPage__Group__4
            {
            pushFollow(FOLLOW_18);
            rule__DataPage__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DataPage__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group__3"


    // $ANTLR start "rule__DataPage__Group__3__Impl"
    // InternalSimpleWebModelingLanguage.g:1737:1: rule__DataPage__Group__3__Impl : ( '{' ) ;
    public final void rule__DataPage__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1741:1: ( ( '{' ) )
            // InternalSimpleWebModelingLanguage.g:1742:1: ( '{' )
            {
            // InternalSimpleWebModelingLanguage.g:1742:1: ( '{' )
            // InternalSimpleWebModelingLanguage.g:1743:2: '{'
            {
             before(grammarAccess.getDataPageAccess().getLeftCurlyBracketKeyword_3()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getDataPageAccess().getLeftCurlyBracketKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group__3__Impl"


    // $ANTLR start "rule__DataPage__Group__4"
    // InternalSimpleWebModelingLanguage.g:1752:1: rule__DataPage__Group__4 : rule__DataPage__Group__4__Impl rule__DataPage__Group__5 ;
    public final void rule__DataPage__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1756:1: ( rule__DataPage__Group__4__Impl rule__DataPage__Group__5 )
            // InternalSimpleWebModelingLanguage.g:1757:2: rule__DataPage__Group__4__Impl rule__DataPage__Group__5
            {
            pushFollow(FOLLOW_18);
            rule__DataPage__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DataPage__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group__4"


    // $ANTLR start "rule__DataPage__Group__4__Impl"
    // InternalSimpleWebModelingLanguage.g:1764:1: rule__DataPage__Group__4__Impl : ( ( rule__DataPage__LinksAssignment_4 )* ) ;
    public final void rule__DataPage__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1768:1: ( ( ( rule__DataPage__LinksAssignment_4 )* ) )
            // InternalSimpleWebModelingLanguage.g:1769:1: ( ( rule__DataPage__LinksAssignment_4 )* )
            {
            // InternalSimpleWebModelingLanguage.g:1769:1: ( ( rule__DataPage__LinksAssignment_4 )* )
            // InternalSimpleWebModelingLanguage.g:1770:2: ( rule__DataPage__LinksAssignment_4 )*
            {
             before(grammarAccess.getDataPageAccess().getLinksAssignment_4()); 
            // InternalSimpleWebModelingLanguage.g:1771:2: ( rule__DataPage__LinksAssignment_4 )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==27) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalSimpleWebModelingLanguage.g:1771:3: rule__DataPage__LinksAssignment_4
            	    {
            	    pushFollow(FOLLOW_19);
            	    rule__DataPage__LinksAssignment_4();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

             after(grammarAccess.getDataPageAccess().getLinksAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group__4__Impl"


    // $ANTLR start "rule__DataPage__Group__5"
    // InternalSimpleWebModelingLanguage.g:1779:1: rule__DataPage__Group__5 : rule__DataPage__Group__5__Impl ;
    public final void rule__DataPage__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1783:1: ( rule__DataPage__Group__5__Impl )
            // InternalSimpleWebModelingLanguage.g:1784:2: rule__DataPage__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DataPage__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group__5"


    // $ANTLR start "rule__DataPage__Group__5__Impl"
    // InternalSimpleWebModelingLanguage.g:1790:1: rule__DataPage__Group__5__Impl : ( '}' ) ;
    public final void rule__DataPage__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1794:1: ( ( '}' ) )
            // InternalSimpleWebModelingLanguage.g:1795:1: ( '}' )
            {
            // InternalSimpleWebModelingLanguage.g:1795:1: ( '}' )
            // InternalSimpleWebModelingLanguage.g:1796:2: '}'
            {
             before(grammarAccess.getDataPageAccess().getRightCurlyBracketKeyword_5()); 
            match(input,18,FOLLOW_2); 
             after(grammarAccess.getDataPageAccess().getRightCurlyBracketKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group__5__Impl"


    // $ANTLR start "rule__DataPage__Group_2__0"
    // InternalSimpleWebModelingLanguage.g:1806:1: rule__DataPage__Group_2__0 : rule__DataPage__Group_2__0__Impl rule__DataPage__Group_2__1 ;
    public final void rule__DataPage__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1810:1: ( rule__DataPage__Group_2__0__Impl rule__DataPage__Group_2__1 )
            // InternalSimpleWebModelingLanguage.g:1811:2: rule__DataPage__Group_2__0__Impl rule__DataPage__Group_2__1
            {
            pushFollow(FOLLOW_3);
            rule__DataPage__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DataPage__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group_2__0"


    // $ANTLR start "rule__DataPage__Group_2__0__Impl"
    // InternalSimpleWebModelingLanguage.g:1818:1: rule__DataPage__Group_2__0__Impl : ( 'shows entity' ) ;
    public final void rule__DataPage__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1822:1: ( ( 'shows entity' ) )
            // InternalSimpleWebModelingLanguage.g:1823:1: ( 'shows entity' )
            {
            // InternalSimpleWebModelingLanguage.g:1823:1: ( 'shows entity' )
            // InternalSimpleWebModelingLanguage.g:1824:2: 'shows entity'
            {
             before(grammarAccess.getDataPageAccess().getShowsEntityKeyword_2_0()); 
            match(input,29,FOLLOW_2); 
             after(grammarAccess.getDataPageAccess().getShowsEntityKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group_2__0__Impl"


    // $ANTLR start "rule__DataPage__Group_2__1"
    // InternalSimpleWebModelingLanguage.g:1833:1: rule__DataPage__Group_2__1 : rule__DataPage__Group_2__1__Impl ;
    public final void rule__DataPage__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1837:1: ( rule__DataPage__Group_2__1__Impl )
            // InternalSimpleWebModelingLanguage.g:1838:2: rule__DataPage__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DataPage__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group_2__1"


    // $ANTLR start "rule__DataPage__Group_2__1__Impl"
    // InternalSimpleWebModelingLanguage.g:1844:1: rule__DataPage__Group_2__1__Impl : ( ( rule__DataPage__EntityAssignment_2_1 ) ) ;
    public final void rule__DataPage__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1848:1: ( ( ( rule__DataPage__EntityAssignment_2_1 ) ) )
            // InternalSimpleWebModelingLanguage.g:1849:1: ( ( rule__DataPage__EntityAssignment_2_1 ) )
            {
            // InternalSimpleWebModelingLanguage.g:1849:1: ( ( rule__DataPage__EntityAssignment_2_1 ) )
            // InternalSimpleWebModelingLanguage.g:1850:2: ( rule__DataPage__EntityAssignment_2_1 )
            {
             before(grammarAccess.getDataPageAccess().getEntityAssignment_2_1()); 
            // InternalSimpleWebModelingLanguage.g:1851:2: ( rule__DataPage__EntityAssignment_2_1 )
            // InternalSimpleWebModelingLanguage.g:1851:3: rule__DataPage__EntityAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__DataPage__EntityAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getDataPageAccess().getEntityAssignment_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__Group_2__1__Impl"


    // $ANTLR start "rule__WebModel__NameAssignment_1"
    // InternalSimpleWebModelingLanguage.g:1860:1: rule__WebModel__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__WebModel__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1864:1: ( ( RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:1865:2: ( RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:1865:2: ( RULE_ID )
            // InternalSimpleWebModelingLanguage.g:1866:3: RULE_ID
            {
             before(grammarAccess.getWebModelAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getWebModelAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WebModel__NameAssignment_1"


    // $ANTLR start "rule__WebModel__DataLayerAssignment_3"
    // InternalSimpleWebModelingLanguage.g:1875:1: rule__WebModel__DataLayerAssignment_3 : ( ruleDataLayer ) ;
    public final void rule__WebModel__DataLayerAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1879:1: ( ( ruleDataLayer ) )
            // InternalSimpleWebModelingLanguage.g:1880:2: ( ruleDataLayer )
            {
            // InternalSimpleWebModelingLanguage.g:1880:2: ( ruleDataLayer )
            // InternalSimpleWebModelingLanguage.g:1881:3: ruleDataLayer
            {
             before(grammarAccess.getWebModelAccess().getDataLayerDataLayerParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleDataLayer();

            state._fsp--;

             after(grammarAccess.getWebModelAccess().getDataLayerDataLayerParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WebModel__DataLayerAssignment_3"


    // $ANTLR start "rule__WebModel__HypertextLayerAssignment_4"
    // InternalSimpleWebModelingLanguage.g:1890:1: rule__WebModel__HypertextLayerAssignment_4 : ( ruleHypertextLayer ) ;
    public final void rule__WebModel__HypertextLayerAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1894:1: ( ( ruleHypertextLayer ) )
            // InternalSimpleWebModelingLanguage.g:1895:2: ( ruleHypertextLayer )
            {
            // InternalSimpleWebModelingLanguage.g:1895:2: ( ruleHypertextLayer )
            // InternalSimpleWebModelingLanguage.g:1896:3: ruleHypertextLayer
            {
             before(grammarAccess.getWebModelAccess().getHypertextLayerHypertextLayerParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleHypertextLayer();

            state._fsp--;

             after(grammarAccess.getWebModelAccess().getHypertextLayerHypertextLayerParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WebModel__HypertextLayerAssignment_4"


    // $ANTLR start "rule__DataLayer__EntitiesAssignment_2"
    // InternalSimpleWebModelingLanguage.g:1905:1: rule__DataLayer__EntitiesAssignment_2 : ( ruleEntity ) ;
    public final void rule__DataLayer__EntitiesAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1909:1: ( ( ruleEntity ) )
            // InternalSimpleWebModelingLanguage.g:1910:2: ( ruleEntity )
            {
            // InternalSimpleWebModelingLanguage.g:1910:2: ( ruleEntity )
            // InternalSimpleWebModelingLanguage.g:1911:3: ruleEntity
            {
             before(grammarAccess.getDataLayerAccess().getEntitiesEntityParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleEntity();

            state._fsp--;

             after(grammarAccess.getDataLayerAccess().getEntitiesEntityParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataLayer__EntitiesAssignment_2"


    // $ANTLR start "rule__Entity__NameAssignment_1"
    // InternalSimpleWebModelingLanguage.g:1920:1: rule__Entity__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__Entity__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1924:1: ( ( RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:1925:2: ( RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:1925:2: ( RULE_ID )
            // InternalSimpleWebModelingLanguage.g:1926:3: RULE_ID
            {
             before(grammarAccess.getEntityAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getEntityAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Entity__NameAssignment_1"


    // $ANTLR start "rule__Entity__AttributesAssignment_3"
    // InternalSimpleWebModelingLanguage.g:1935:1: rule__Entity__AttributesAssignment_3 : ( ruleAttribute ) ;
    public final void rule__Entity__AttributesAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1939:1: ( ( ruleAttribute ) )
            // InternalSimpleWebModelingLanguage.g:1940:2: ( ruleAttribute )
            {
            // InternalSimpleWebModelingLanguage.g:1940:2: ( ruleAttribute )
            // InternalSimpleWebModelingLanguage.g:1941:3: ruleAttribute
            {
             before(grammarAccess.getEntityAccess().getAttributesAttributeParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleAttribute();

            state._fsp--;

             after(grammarAccess.getEntityAccess().getAttributesAttributeParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Entity__AttributesAssignment_3"


    // $ANTLR start "rule__Entity__ReferencesAssignment_4"
    // InternalSimpleWebModelingLanguage.g:1950:1: rule__Entity__ReferencesAssignment_4 : ( ruleReference ) ;
    public final void rule__Entity__ReferencesAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1954:1: ( ( ruleReference ) )
            // InternalSimpleWebModelingLanguage.g:1955:2: ( ruleReference )
            {
            // InternalSimpleWebModelingLanguage.g:1955:2: ( ruleReference )
            // InternalSimpleWebModelingLanguage.g:1956:3: ruleReference
            {
             before(grammarAccess.getEntityAccess().getReferencesReferenceParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleReference();

            state._fsp--;

             after(grammarAccess.getEntityAccess().getReferencesReferenceParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Entity__ReferencesAssignment_4"


    // $ANTLR start "rule__Attribute__NameAssignment_1"
    // InternalSimpleWebModelingLanguage.g:1965:1: rule__Attribute__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__Attribute__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1969:1: ( ( RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:1970:2: ( RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:1970:2: ( RULE_ID )
            // InternalSimpleWebModelingLanguage.g:1971:3: RULE_ID
            {
             before(grammarAccess.getAttributeAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getAttributeAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Attribute__NameAssignment_1"


    // $ANTLR start "rule__Attribute__TypeAssignment_3"
    // InternalSimpleWebModelingLanguage.g:1980:1: rule__Attribute__TypeAssignment_3 : ( ruleSimpleType ) ;
    public final void rule__Attribute__TypeAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1984:1: ( ( ruleSimpleType ) )
            // InternalSimpleWebModelingLanguage.g:1985:2: ( ruleSimpleType )
            {
            // InternalSimpleWebModelingLanguage.g:1985:2: ( ruleSimpleType )
            // InternalSimpleWebModelingLanguage.g:1986:3: ruleSimpleType
            {
             before(grammarAccess.getAttributeAccess().getTypeSimpleTypeEnumRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleSimpleType();

            state._fsp--;

             after(grammarAccess.getAttributeAccess().getTypeSimpleTypeEnumRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Attribute__TypeAssignment_3"


    // $ANTLR start "rule__Reference__NameAssignment_1"
    // InternalSimpleWebModelingLanguage.g:1995:1: rule__Reference__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__Reference__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:1999:1: ( ( RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:2000:2: ( RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:2000:2: ( RULE_ID )
            // InternalSimpleWebModelingLanguage.g:2001:3: RULE_ID
            {
             before(grammarAccess.getReferenceAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getReferenceAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Reference__NameAssignment_1"


    // $ANTLR start "rule__Reference__TypeAssignment_3"
    // InternalSimpleWebModelingLanguage.g:2010:1: rule__Reference__TypeAssignment_3 : ( ( RULE_ID ) ) ;
    public final void rule__Reference__TypeAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:2014:1: ( ( ( RULE_ID ) ) )
            // InternalSimpleWebModelingLanguage.g:2015:2: ( ( RULE_ID ) )
            {
            // InternalSimpleWebModelingLanguage.g:2015:2: ( ( RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:2016:3: ( RULE_ID )
            {
             before(grammarAccess.getReferenceAccess().getTypeEntityCrossReference_3_0()); 
            // InternalSimpleWebModelingLanguage.g:2017:3: ( RULE_ID )
            // InternalSimpleWebModelingLanguage.g:2018:4: RULE_ID
            {
             before(grammarAccess.getReferenceAccess().getTypeEntityIDTerminalRuleCall_3_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getReferenceAccess().getTypeEntityIDTerminalRuleCall_3_0_1()); 

            }

             after(grammarAccess.getReferenceAccess().getTypeEntityCrossReference_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Reference__TypeAssignment_3"


    // $ANTLR start "rule__HypertextLayer__PagesAssignment_1"
    // InternalSimpleWebModelingLanguage.g:2029:1: rule__HypertextLayer__PagesAssignment_1 : ( rulePage ) ;
    public final void rule__HypertextLayer__PagesAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:2033:1: ( ( rulePage ) )
            // InternalSimpleWebModelingLanguage.g:2034:2: ( rulePage )
            {
            // InternalSimpleWebModelingLanguage.g:2034:2: ( rulePage )
            // InternalSimpleWebModelingLanguage.g:2035:3: rulePage
            {
             before(grammarAccess.getHypertextLayerAccess().getPagesPageParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            rulePage();

            state._fsp--;

             after(grammarAccess.getHypertextLayerAccess().getPagesPageParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HypertextLayer__PagesAssignment_1"


    // $ANTLR start "rule__HypertextLayer__StartPageAssignment_3"
    // InternalSimpleWebModelingLanguage.g:2044:1: rule__HypertextLayer__StartPageAssignment_3 : ( ( RULE_ID ) ) ;
    public final void rule__HypertextLayer__StartPageAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:2048:1: ( ( ( RULE_ID ) ) )
            // InternalSimpleWebModelingLanguage.g:2049:2: ( ( RULE_ID ) )
            {
            // InternalSimpleWebModelingLanguage.g:2049:2: ( ( RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:2050:3: ( RULE_ID )
            {
             before(grammarAccess.getHypertextLayerAccess().getStartPageStaticPageCrossReference_3_0()); 
            // InternalSimpleWebModelingLanguage.g:2051:3: ( RULE_ID )
            // InternalSimpleWebModelingLanguage.g:2052:4: RULE_ID
            {
             before(grammarAccess.getHypertextLayerAccess().getStartPageStaticPageIDTerminalRuleCall_3_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getHypertextLayerAccess().getStartPageStaticPageIDTerminalRuleCall_3_0_1()); 

            }

             after(grammarAccess.getHypertextLayerAccess().getStartPageStaticPageCrossReference_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HypertextLayer__StartPageAssignment_3"


    // $ANTLR start "rule__StaticPage__NameAssignment_1"
    // InternalSimpleWebModelingLanguage.g:2063:1: rule__StaticPage__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__StaticPage__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:2067:1: ( ( RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:2068:2: ( RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:2068:2: ( RULE_ID )
            // InternalSimpleWebModelingLanguage.g:2069:3: RULE_ID
            {
             before(grammarAccess.getStaticPageAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getStaticPageAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StaticPage__NameAssignment_1"


    // $ANTLR start "rule__StaticPage__LinksAssignment_3"
    // InternalSimpleWebModelingLanguage.g:2078:1: rule__StaticPage__LinksAssignment_3 : ( ruleLink ) ;
    public final void rule__StaticPage__LinksAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:2082:1: ( ( ruleLink ) )
            // InternalSimpleWebModelingLanguage.g:2083:2: ( ruleLink )
            {
            // InternalSimpleWebModelingLanguage.g:2083:2: ( ruleLink )
            // InternalSimpleWebModelingLanguage.g:2084:3: ruleLink
            {
             before(grammarAccess.getStaticPageAccess().getLinksLinkParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleLink();

            state._fsp--;

             after(grammarAccess.getStaticPageAccess().getLinksLinkParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StaticPage__LinksAssignment_3"


    // $ANTLR start "rule__Link__TargetAssignment_1"
    // InternalSimpleWebModelingLanguage.g:2093:1: rule__Link__TargetAssignment_1 : ( ( RULE_ID ) ) ;
    public final void rule__Link__TargetAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:2097:1: ( ( ( RULE_ID ) ) )
            // InternalSimpleWebModelingLanguage.g:2098:2: ( ( RULE_ID ) )
            {
            // InternalSimpleWebModelingLanguage.g:2098:2: ( ( RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:2099:3: ( RULE_ID )
            {
             before(grammarAccess.getLinkAccess().getTargetPageCrossReference_1_0()); 
            // InternalSimpleWebModelingLanguage.g:2100:3: ( RULE_ID )
            // InternalSimpleWebModelingLanguage.g:2101:4: RULE_ID
            {
             before(grammarAccess.getLinkAccess().getTargetPageIDTerminalRuleCall_1_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getLinkAccess().getTargetPageIDTerminalRuleCall_1_0_1()); 

            }

             after(grammarAccess.getLinkAccess().getTargetPageCrossReference_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__TargetAssignment_1"


    // $ANTLR start "rule__IndexPage__NameAssignment_1"
    // InternalSimpleWebModelingLanguage.g:2112:1: rule__IndexPage__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__IndexPage__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:2116:1: ( ( RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:2117:2: ( RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:2117:2: ( RULE_ID )
            // InternalSimpleWebModelingLanguage.g:2118:3: RULE_ID
            {
             before(grammarAccess.getIndexPageAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getIndexPageAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__NameAssignment_1"


    // $ANTLR start "rule__IndexPage__EntityAssignment_2_1"
    // InternalSimpleWebModelingLanguage.g:2127:1: rule__IndexPage__EntityAssignment_2_1 : ( ( RULE_ID ) ) ;
    public final void rule__IndexPage__EntityAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:2131:1: ( ( ( RULE_ID ) ) )
            // InternalSimpleWebModelingLanguage.g:2132:2: ( ( RULE_ID ) )
            {
            // InternalSimpleWebModelingLanguage.g:2132:2: ( ( RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:2133:3: ( RULE_ID )
            {
             before(grammarAccess.getIndexPageAccess().getEntityEntityCrossReference_2_1_0()); 
            // InternalSimpleWebModelingLanguage.g:2134:3: ( RULE_ID )
            // InternalSimpleWebModelingLanguage.g:2135:4: RULE_ID
            {
             before(grammarAccess.getIndexPageAccess().getEntityEntityIDTerminalRuleCall_2_1_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getIndexPageAccess().getEntityEntityIDTerminalRuleCall_2_1_0_1()); 

            }

             after(grammarAccess.getIndexPageAccess().getEntityEntityCrossReference_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__EntityAssignment_2_1"


    // $ANTLR start "rule__IndexPage__LinksAssignment_4"
    // InternalSimpleWebModelingLanguage.g:2146:1: rule__IndexPage__LinksAssignment_4 : ( ruleLink ) ;
    public final void rule__IndexPage__LinksAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:2150:1: ( ( ruleLink ) )
            // InternalSimpleWebModelingLanguage.g:2151:2: ( ruleLink )
            {
            // InternalSimpleWebModelingLanguage.g:2151:2: ( ruleLink )
            // InternalSimpleWebModelingLanguage.g:2152:3: ruleLink
            {
             before(grammarAccess.getIndexPageAccess().getLinksLinkParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleLink();

            state._fsp--;

             after(grammarAccess.getIndexPageAccess().getLinksLinkParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IndexPage__LinksAssignment_4"


    // $ANTLR start "rule__DataPage__NameAssignment_1"
    // InternalSimpleWebModelingLanguage.g:2161:1: rule__DataPage__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__DataPage__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:2165:1: ( ( RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:2166:2: ( RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:2166:2: ( RULE_ID )
            // InternalSimpleWebModelingLanguage.g:2167:3: RULE_ID
            {
             before(grammarAccess.getDataPageAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getDataPageAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__NameAssignment_1"


    // $ANTLR start "rule__DataPage__EntityAssignment_2_1"
    // InternalSimpleWebModelingLanguage.g:2176:1: rule__DataPage__EntityAssignment_2_1 : ( ( RULE_ID ) ) ;
    public final void rule__DataPage__EntityAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:2180:1: ( ( ( RULE_ID ) ) )
            // InternalSimpleWebModelingLanguage.g:2181:2: ( ( RULE_ID ) )
            {
            // InternalSimpleWebModelingLanguage.g:2181:2: ( ( RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:2182:3: ( RULE_ID )
            {
             before(grammarAccess.getDataPageAccess().getEntityEntityCrossReference_2_1_0()); 
            // InternalSimpleWebModelingLanguage.g:2183:3: ( RULE_ID )
            // InternalSimpleWebModelingLanguage.g:2184:4: RULE_ID
            {
             before(grammarAccess.getDataPageAccess().getEntityEntityIDTerminalRuleCall_2_1_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getDataPageAccess().getEntityEntityIDTerminalRuleCall_2_1_0_1()); 

            }

             after(grammarAccess.getDataPageAccess().getEntityEntityCrossReference_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__EntityAssignment_2_1"


    // $ANTLR start "rule__DataPage__LinksAssignment_4"
    // InternalSimpleWebModelingLanguage.g:2195:1: rule__DataPage__LinksAssignment_4 : ( ruleLink ) ;
    public final void rule__DataPage__LinksAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSimpleWebModelingLanguage.g:2199:1: ( ( ruleLink ) )
            // InternalSimpleWebModelingLanguage.g:2200:2: ( ruleLink )
            {
            // InternalSimpleWebModelingLanguage.g:2200:2: ( ruleLink )
            // InternalSimpleWebModelingLanguage.g:2201:3: ruleLink
            {
             before(grammarAccess.getDataPageAccess().getLinksLinkParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleLink();

            state._fsp--;

             after(grammarAccess.getDataPageAccess().getLinksLinkParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DataPage__LinksAssignment_4"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000A40000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x000000000000F800L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000054000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000054000002L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000008040000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000020020000L});

}