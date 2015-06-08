package org.eclipse.emf.refactor.examples.ui.contentassist.antlr.internal; 

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.DFA;
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
    public static final int RULE_ID=4;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int RULE_SL_COMMENT=8;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__30=30;
    public static final int T__19=19;
    public static final int RULE_STRING=6;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int RULE_INT=5;
    public static final int RULE_WS=9;

    // delegates
    // delegators


        public InternalSimpleWebModelingLanguageParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalSimpleWebModelingLanguageParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalSimpleWebModelingLanguageParser.tokenNames; }
    public String getGrammarFileName() { return "../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g"; }


     
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:60:1: entryRuleWebModel : ruleWebModel EOF ;
    public final void entryRuleWebModel() throws RecognitionException {
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:61:1: ( ruleWebModel EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:62:1: ruleWebModel EOF
            {
             before(grammarAccess.getWebModelRule()); 
            pushFollow(FOLLOW_ruleWebModel_in_entryRuleWebModel61);
            ruleWebModel();

            state._fsp--;

             after(grammarAccess.getWebModelRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleWebModel68); 

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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:69:1: ruleWebModel : ( ( rule__WebModel__Group__0 ) ) ;
    public final void ruleWebModel() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:73:2: ( ( ( rule__WebModel__Group__0 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:74:1: ( ( rule__WebModel__Group__0 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:74:1: ( ( rule__WebModel__Group__0 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:75:1: ( rule__WebModel__Group__0 )
            {
             before(grammarAccess.getWebModelAccess().getGroup()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:76:1: ( rule__WebModel__Group__0 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:76:2: rule__WebModel__Group__0
            {
            pushFollow(FOLLOW_rule__WebModel__Group__0_in_ruleWebModel94);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:88:1: entryRuleDataLayer : ruleDataLayer EOF ;
    public final void entryRuleDataLayer() throws RecognitionException {
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:89:1: ( ruleDataLayer EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:90:1: ruleDataLayer EOF
            {
             before(grammarAccess.getDataLayerRule()); 
            pushFollow(FOLLOW_ruleDataLayer_in_entryRuleDataLayer121);
            ruleDataLayer();

            state._fsp--;

             after(grammarAccess.getDataLayerRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDataLayer128); 

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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:97:1: ruleDataLayer : ( ( rule__DataLayer__Group__0 ) ) ;
    public final void ruleDataLayer() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:101:2: ( ( ( rule__DataLayer__Group__0 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:102:1: ( ( rule__DataLayer__Group__0 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:102:1: ( ( rule__DataLayer__Group__0 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:103:1: ( rule__DataLayer__Group__0 )
            {
             before(grammarAccess.getDataLayerAccess().getGroup()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:104:1: ( rule__DataLayer__Group__0 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:104:2: rule__DataLayer__Group__0
            {
            pushFollow(FOLLOW_rule__DataLayer__Group__0_in_ruleDataLayer154);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:116:1: entryRuleEntity : ruleEntity EOF ;
    public final void entryRuleEntity() throws RecognitionException {
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:117:1: ( ruleEntity EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:118:1: ruleEntity EOF
            {
             before(grammarAccess.getEntityRule()); 
            pushFollow(FOLLOW_ruleEntity_in_entryRuleEntity181);
            ruleEntity();

            state._fsp--;

             after(grammarAccess.getEntityRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEntity188); 

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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:125:1: ruleEntity : ( ( rule__Entity__Group__0 ) ) ;
    public final void ruleEntity() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:129:2: ( ( ( rule__Entity__Group__0 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:130:1: ( ( rule__Entity__Group__0 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:130:1: ( ( rule__Entity__Group__0 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:131:1: ( rule__Entity__Group__0 )
            {
             before(grammarAccess.getEntityAccess().getGroup()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:132:1: ( rule__Entity__Group__0 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:132:2: rule__Entity__Group__0
            {
            pushFollow(FOLLOW_rule__Entity__Group__0_in_ruleEntity214);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:144:1: entryRuleAttribute : ruleAttribute EOF ;
    public final void entryRuleAttribute() throws RecognitionException {
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:145:1: ( ruleAttribute EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:146:1: ruleAttribute EOF
            {
             before(grammarAccess.getAttributeRule()); 
            pushFollow(FOLLOW_ruleAttribute_in_entryRuleAttribute241);
            ruleAttribute();

            state._fsp--;

             after(grammarAccess.getAttributeRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAttribute248); 

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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:153:1: ruleAttribute : ( ( rule__Attribute__Group__0 ) ) ;
    public final void ruleAttribute() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:157:2: ( ( ( rule__Attribute__Group__0 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:158:1: ( ( rule__Attribute__Group__0 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:158:1: ( ( rule__Attribute__Group__0 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:159:1: ( rule__Attribute__Group__0 )
            {
             before(grammarAccess.getAttributeAccess().getGroup()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:160:1: ( rule__Attribute__Group__0 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:160:2: rule__Attribute__Group__0
            {
            pushFollow(FOLLOW_rule__Attribute__Group__0_in_ruleAttribute274);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:172:1: entryRuleReference : ruleReference EOF ;
    public final void entryRuleReference() throws RecognitionException {
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:173:1: ( ruleReference EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:174:1: ruleReference EOF
            {
             before(grammarAccess.getReferenceRule()); 
            pushFollow(FOLLOW_ruleReference_in_entryRuleReference301);
            ruleReference();

            state._fsp--;

             after(grammarAccess.getReferenceRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleReference308); 

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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:181:1: ruleReference : ( ( rule__Reference__Group__0 ) ) ;
    public final void ruleReference() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:185:2: ( ( ( rule__Reference__Group__0 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:186:1: ( ( rule__Reference__Group__0 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:186:1: ( ( rule__Reference__Group__0 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:187:1: ( rule__Reference__Group__0 )
            {
             before(grammarAccess.getReferenceAccess().getGroup()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:188:1: ( rule__Reference__Group__0 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:188:2: rule__Reference__Group__0
            {
            pushFollow(FOLLOW_rule__Reference__Group__0_in_ruleReference334);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:200:1: entryRuleHypertextLayer : ruleHypertextLayer EOF ;
    public final void entryRuleHypertextLayer() throws RecognitionException {
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:201:1: ( ruleHypertextLayer EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:202:1: ruleHypertextLayer EOF
            {
             before(grammarAccess.getHypertextLayerRule()); 
            pushFollow(FOLLOW_ruleHypertextLayer_in_entryRuleHypertextLayer361);
            ruleHypertextLayer();

            state._fsp--;

             after(grammarAccess.getHypertextLayerRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleHypertextLayer368); 

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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:209:1: ruleHypertextLayer : ( ( rule__HypertextLayer__Group__0 ) ) ;
    public final void ruleHypertextLayer() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:213:2: ( ( ( rule__HypertextLayer__Group__0 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:214:1: ( ( rule__HypertextLayer__Group__0 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:214:1: ( ( rule__HypertextLayer__Group__0 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:215:1: ( rule__HypertextLayer__Group__0 )
            {
             before(grammarAccess.getHypertextLayerAccess().getGroup()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:216:1: ( rule__HypertextLayer__Group__0 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:216:2: rule__HypertextLayer__Group__0
            {
            pushFollow(FOLLOW_rule__HypertextLayer__Group__0_in_ruleHypertextLayer394);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:228:1: entryRulePage : rulePage EOF ;
    public final void entryRulePage() throws RecognitionException {
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:229:1: ( rulePage EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:230:1: rulePage EOF
            {
             before(grammarAccess.getPageRule()); 
            pushFollow(FOLLOW_rulePage_in_entryRulePage421);
            rulePage();

            state._fsp--;

             after(grammarAccess.getPageRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePage428); 

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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:237:1: rulePage : ( ( rule__Page__Alternatives ) ) ;
    public final void rulePage() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:241:2: ( ( ( rule__Page__Alternatives ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:242:1: ( ( rule__Page__Alternatives ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:242:1: ( ( rule__Page__Alternatives ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:243:1: ( rule__Page__Alternatives )
            {
             before(grammarAccess.getPageAccess().getAlternatives()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:244:1: ( rule__Page__Alternatives )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:244:2: rule__Page__Alternatives
            {
            pushFollow(FOLLOW_rule__Page__Alternatives_in_rulePage454);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:256:1: entryRuleStaticPage : ruleStaticPage EOF ;
    public final void entryRuleStaticPage() throws RecognitionException {
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:257:1: ( ruleStaticPage EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:258:1: ruleStaticPage EOF
            {
             before(grammarAccess.getStaticPageRule()); 
            pushFollow(FOLLOW_ruleStaticPage_in_entryRuleStaticPage481);
            ruleStaticPage();

            state._fsp--;

             after(grammarAccess.getStaticPageRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleStaticPage488); 

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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:265:1: ruleStaticPage : ( ( rule__StaticPage__Group__0 ) ) ;
    public final void ruleStaticPage() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:269:2: ( ( ( rule__StaticPage__Group__0 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:270:1: ( ( rule__StaticPage__Group__0 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:270:1: ( ( rule__StaticPage__Group__0 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:271:1: ( rule__StaticPage__Group__0 )
            {
             before(grammarAccess.getStaticPageAccess().getGroup()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:272:1: ( rule__StaticPage__Group__0 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:272:2: rule__StaticPage__Group__0
            {
            pushFollow(FOLLOW_rule__StaticPage__Group__0_in_ruleStaticPage514);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:284:1: entryRuleLink : ruleLink EOF ;
    public final void entryRuleLink() throws RecognitionException {
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:285:1: ( ruleLink EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:286:1: ruleLink EOF
            {
             before(grammarAccess.getLinkRule()); 
            pushFollow(FOLLOW_ruleLink_in_entryRuleLink541);
            ruleLink();

            state._fsp--;

             after(grammarAccess.getLinkRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLink548); 

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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:293:1: ruleLink : ( ( rule__Link__Group__0 ) ) ;
    public final void ruleLink() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:297:2: ( ( ( rule__Link__Group__0 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:298:1: ( ( rule__Link__Group__0 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:298:1: ( ( rule__Link__Group__0 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:299:1: ( rule__Link__Group__0 )
            {
             before(grammarAccess.getLinkAccess().getGroup()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:300:1: ( rule__Link__Group__0 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:300:2: rule__Link__Group__0
            {
            pushFollow(FOLLOW_rule__Link__Group__0_in_ruleLink574);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:312:1: entryRuleDynamicPage : ruleDynamicPage EOF ;
    public final void entryRuleDynamicPage() throws RecognitionException {
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:313:1: ( ruleDynamicPage EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:314:1: ruleDynamicPage EOF
            {
             before(grammarAccess.getDynamicPageRule()); 
            pushFollow(FOLLOW_ruleDynamicPage_in_entryRuleDynamicPage601);
            ruleDynamicPage();

            state._fsp--;

             after(grammarAccess.getDynamicPageRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDynamicPage608); 

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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:321:1: ruleDynamicPage : ( ( rule__DynamicPage__Alternatives ) ) ;
    public final void ruleDynamicPage() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:325:2: ( ( ( rule__DynamicPage__Alternatives ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:326:1: ( ( rule__DynamicPage__Alternatives ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:326:1: ( ( rule__DynamicPage__Alternatives ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:327:1: ( rule__DynamicPage__Alternatives )
            {
             before(grammarAccess.getDynamicPageAccess().getAlternatives()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:328:1: ( rule__DynamicPage__Alternatives )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:328:2: rule__DynamicPage__Alternatives
            {
            pushFollow(FOLLOW_rule__DynamicPage__Alternatives_in_ruleDynamicPage634);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:340:1: entryRuleIndexPage : ruleIndexPage EOF ;
    public final void entryRuleIndexPage() throws RecognitionException {
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:341:1: ( ruleIndexPage EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:342:1: ruleIndexPage EOF
            {
             before(grammarAccess.getIndexPageRule()); 
            pushFollow(FOLLOW_ruleIndexPage_in_entryRuleIndexPage661);
            ruleIndexPage();

            state._fsp--;

             after(grammarAccess.getIndexPageRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleIndexPage668); 

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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:349:1: ruleIndexPage : ( ( rule__IndexPage__Group__0 ) ) ;
    public final void ruleIndexPage() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:353:2: ( ( ( rule__IndexPage__Group__0 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:354:1: ( ( rule__IndexPage__Group__0 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:354:1: ( ( rule__IndexPage__Group__0 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:355:1: ( rule__IndexPage__Group__0 )
            {
             before(grammarAccess.getIndexPageAccess().getGroup()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:356:1: ( rule__IndexPage__Group__0 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:356:2: rule__IndexPage__Group__0
            {
            pushFollow(FOLLOW_rule__IndexPage__Group__0_in_ruleIndexPage694);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:368:1: entryRuleDataPage : ruleDataPage EOF ;
    public final void entryRuleDataPage() throws RecognitionException {
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:369:1: ( ruleDataPage EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:370:1: ruleDataPage EOF
            {
             before(grammarAccess.getDataPageRule()); 
            pushFollow(FOLLOW_ruleDataPage_in_entryRuleDataPage721);
            ruleDataPage();

            state._fsp--;

             after(grammarAccess.getDataPageRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDataPage728); 

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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:377:1: ruleDataPage : ( ( rule__DataPage__Group__0 ) ) ;
    public final void ruleDataPage() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:381:2: ( ( ( rule__DataPage__Group__0 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:382:1: ( ( rule__DataPage__Group__0 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:382:1: ( ( rule__DataPage__Group__0 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:383:1: ( rule__DataPage__Group__0 )
            {
             before(grammarAccess.getDataPageAccess().getGroup()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:384:1: ( rule__DataPage__Group__0 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:384:2: rule__DataPage__Group__0
            {
            pushFollow(FOLLOW_rule__DataPage__Group__0_in_ruleDataPage754);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:397:1: ruleSimpleType : ( ( rule__SimpleType__Alternatives ) ) ;
    public final void ruleSimpleType() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:401:1: ( ( ( rule__SimpleType__Alternatives ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:402:1: ( ( rule__SimpleType__Alternatives ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:402:1: ( ( rule__SimpleType__Alternatives ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:403:1: ( rule__SimpleType__Alternatives )
            {
             before(grammarAccess.getSimpleTypeAccess().getAlternatives()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:404:1: ( rule__SimpleType__Alternatives )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:404:2: rule__SimpleType__Alternatives
            {
            pushFollow(FOLLOW_rule__SimpleType__Alternatives_in_ruleSimpleType791);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:415:1: rule__Page__Alternatives : ( ( ruleStaticPage ) | ( ruleDynamicPage ) );
    public final void rule__Page__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:419:1: ( ( ruleStaticPage ) | ( ruleDynamicPage ) )
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
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:420:1: ( ruleStaticPage )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:420:1: ( ruleStaticPage )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:421:1: ruleStaticPage
                    {
                     before(grammarAccess.getPageAccess().getStaticPageParserRuleCall_0()); 
                    pushFollow(FOLLOW_ruleStaticPage_in_rule__Page__Alternatives826);
                    ruleStaticPage();

                    state._fsp--;

                     after(grammarAccess.getPageAccess().getStaticPageParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:426:6: ( ruleDynamicPage )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:426:6: ( ruleDynamicPage )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:427:1: ruleDynamicPage
                    {
                     before(grammarAccess.getPageAccess().getDynamicPageParserRuleCall_1()); 
                    pushFollow(FOLLOW_ruleDynamicPage_in_rule__Page__Alternatives843);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:437:1: rule__DynamicPage__Alternatives : ( ( ruleIndexPage ) | ( ruleDataPage ) );
    public final void rule__DynamicPage__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:441:1: ( ( ruleIndexPage ) | ( ruleDataPage ) )
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
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:442:1: ( ruleIndexPage )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:442:1: ( ruleIndexPage )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:443:1: ruleIndexPage
                    {
                     before(grammarAccess.getDynamicPageAccess().getIndexPageParserRuleCall_0()); 
                    pushFollow(FOLLOW_ruleIndexPage_in_rule__DynamicPage__Alternatives875);
                    ruleIndexPage();

                    state._fsp--;

                     after(grammarAccess.getDynamicPageAccess().getIndexPageParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:448:6: ( ruleDataPage )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:448:6: ( ruleDataPage )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:449:1: ruleDataPage
                    {
                     before(grammarAccess.getDynamicPageAccess().getDataPageParserRuleCall_1()); 
                    pushFollow(FOLLOW_ruleDataPage_in_rule__DynamicPage__Alternatives892);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:459:1: rule__SimpleType__Alternatives : ( ( ( 'Boolean' ) ) | ( ( 'Email' ) ) | ( ( 'Float' ) ) | ( ( 'Integer' ) ) | ( ( 'String' ) ) );
    public final void rule__SimpleType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:463:1: ( ( ( 'Boolean' ) ) | ( ( 'Email' ) ) | ( ( 'Float' ) ) | ( ( 'Integer' ) ) | ( ( 'String' ) ) )
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
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:464:1: ( ( 'Boolean' ) )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:464:1: ( ( 'Boolean' ) )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:465:1: ( 'Boolean' )
                    {
                     before(grammarAccess.getSimpleTypeAccess().getBooleanEnumLiteralDeclaration_0()); 
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:466:1: ( 'Boolean' )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:466:3: 'Boolean'
                    {
                    match(input,11,FOLLOW_11_in_rule__SimpleType__Alternatives925); 

                    }

                     after(grammarAccess.getSimpleTypeAccess().getBooleanEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:471:6: ( ( 'Email' ) )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:471:6: ( ( 'Email' ) )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:472:1: ( 'Email' )
                    {
                     before(grammarAccess.getSimpleTypeAccess().getEmailEnumLiteralDeclaration_1()); 
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:473:1: ( 'Email' )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:473:3: 'Email'
                    {
                    match(input,12,FOLLOW_12_in_rule__SimpleType__Alternatives946); 

                    }

                     after(grammarAccess.getSimpleTypeAccess().getEmailEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:478:6: ( ( 'Float' ) )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:478:6: ( ( 'Float' ) )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:479:1: ( 'Float' )
                    {
                     before(grammarAccess.getSimpleTypeAccess().getFloatEnumLiteralDeclaration_2()); 
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:480:1: ( 'Float' )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:480:3: 'Float'
                    {
                    match(input,13,FOLLOW_13_in_rule__SimpleType__Alternatives967); 

                    }

                     after(grammarAccess.getSimpleTypeAccess().getFloatEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:485:6: ( ( 'Integer' ) )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:485:6: ( ( 'Integer' ) )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:486:1: ( 'Integer' )
                    {
                     before(grammarAccess.getSimpleTypeAccess().getIntegerEnumLiteralDeclaration_3()); 
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:487:1: ( 'Integer' )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:487:3: 'Integer'
                    {
                    match(input,14,FOLLOW_14_in_rule__SimpleType__Alternatives988); 

                    }

                     after(grammarAccess.getSimpleTypeAccess().getIntegerEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:492:6: ( ( 'String' ) )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:492:6: ( ( 'String' ) )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:493:1: ( 'String' )
                    {
                     before(grammarAccess.getSimpleTypeAccess().getStringEnumLiteralDeclaration_4()); 
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:494:1: ( 'String' )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:494:3: 'String'
                    {
                    match(input,15,FOLLOW_15_in_rule__SimpleType__Alternatives1009); 

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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:506:1: rule__WebModel__Group__0 : rule__WebModel__Group__0__Impl rule__WebModel__Group__1 ;
    public final void rule__WebModel__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:510:1: ( rule__WebModel__Group__0__Impl rule__WebModel__Group__1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:511:2: rule__WebModel__Group__0__Impl rule__WebModel__Group__1
            {
            pushFollow(FOLLOW_rule__WebModel__Group__0__Impl_in_rule__WebModel__Group__01042);
            rule__WebModel__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__WebModel__Group__1_in_rule__WebModel__Group__01045);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:518:1: rule__WebModel__Group__0__Impl : ( 'webmodel' ) ;
    public final void rule__WebModel__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:522:1: ( ( 'webmodel' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:523:1: ( 'webmodel' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:523:1: ( 'webmodel' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:524:1: 'webmodel'
            {
             before(grammarAccess.getWebModelAccess().getWebmodelKeyword_0()); 
            match(input,16,FOLLOW_16_in_rule__WebModel__Group__0__Impl1073); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:537:1: rule__WebModel__Group__1 : rule__WebModel__Group__1__Impl rule__WebModel__Group__2 ;
    public final void rule__WebModel__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:541:1: ( rule__WebModel__Group__1__Impl rule__WebModel__Group__2 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:542:2: rule__WebModel__Group__1__Impl rule__WebModel__Group__2
            {
            pushFollow(FOLLOW_rule__WebModel__Group__1__Impl_in_rule__WebModel__Group__11104);
            rule__WebModel__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__WebModel__Group__2_in_rule__WebModel__Group__11107);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:549:1: rule__WebModel__Group__1__Impl : ( ( rule__WebModel__NameAssignment_1 ) ) ;
    public final void rule__WebModel__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:553:1: ( ( ( rule__WebModel__NameAssignment_1 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:554:1: ( ( rule__WebModel__NameAssignment_1 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:554:1: ( ( rule__WebModel__NameAssignment_1 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:555:1: ( rule__WebModel__NameAssignment_1 )
            {
             before(grammarAccess.getWebModelAccess().getNameAssignment_1()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:556:1: ( rule__WebModel__NameAssignment_1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:556:2: rule__WebModel__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__WebModel__NameAssignment_1_in_rule__WebModel__Group__1__Impl1134);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:566:1: rule__WebModel__Group__2 : rule__WebModel__Group__2__Impl rule__WebModel__Group__3 ;
    public final void rule__WebModel__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:570:1: ( rule__WebModel__Group__2__Impl rule__WebModel__Group__3 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:571:2: rule__WebModel__Group__2__Impl rule__WebModel__Group__3
            {
            pushFollow(FOLLOW_rule__WebModel__Group__2__Impl_in_rule__WebModel__Group__21164);
            rule__WebModel__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__WebModel__Group__3_in_rule__WebModel__Group__21167);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:578:1: rule__WebModel__Group__2__Impl : ( '{' ) ;
    public final void rule__WebModel__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:582:1: ( ( '{' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:583:1: ( '{' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:583:1: ( '{' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:584:1: '{'
            {
             before(grammarAccess.getWebModelAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,17,FOLLOW_17_in_rule__WebModel__Group__2__Impl1195); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:597:1: rule__WebModel__Group__3 : rule__WebModel__Group__3__Impl rule__WebModel__Group__4 ;
    public final void rule__WebModel__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:601:1: ( rule__WebModel__Group__3__Impl rule__WebModel__Group__4 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:602:2: rule__WebModel__Group__3__Impl rule__WebModel__Group__4
            {
            pushFollow(FOLLOW_rule__WebModel__Group__3__Impl_in_rule__WebModel__Group__31226);
            rule__WebModel__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__WebModel__Group__4_in_rule__WebModel__Group__31229);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:609:1: rule__WebModel__Group__3__Impl : ( ( rule__WebModel__DataLayerAssignment_3 ) ) ;
    public final void rule__WebModel__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:613:1: ( ( ( rule__WebModel__DataLayerAssignment_3 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:614:1: ( ( rule__WebModel__DataLayerAssignment_3 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:614:1: ( ( rule__WebModel__DataLayerAssignment_3 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:615:1: ( rule__WebModel__DataLayerAssignment_3 )
            {
             before(grammarAccess.getWebModelAccess().getDataLayerAssignment_3()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:616:1: ( rule__WebModel__DataLayerAssignment_3 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:616:2: rule__WebModel__DataLayerAssignment_3
            {
            pushFollow(FOLLOW_rule__WebModel__DataLayerAssignment_3_in_rule__WebModel__Group__3__Impl1256);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:626:1: rule__WebModel__Group__4 : rule__WebModel__Group__4__Impl rule__WebModel__Group__5 ;
    public final void rule__WebModel__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:630:1: ( rule__WebModel__Group__4__Impl rule__WebModel__Group__5 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:631:2: rule__WebModel__Group__4__Impl rule__WebModel__Group__5
            {
            pushFollow(FOLLOW_rule__WebModel__Group__4__Impl_in_rule__WebModel__Group__41286);
            rule__WebModel__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__WebModel__Group__5_in_rule__WebModel__Group__41289);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:638:1: rule__WebModel__Group__4__Impl : ( ( rule__WebModel__HypertextLayerAssignment_4 ) ) ;
    public final void rule__WebModel__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:642:1: ( ( ( rule__WebModel__HypertextLayerAssignment_4 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:643:1: ( ( rule__WebModel__HypertextLayerAssignment_4 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:643:1: ( ( rule__WebModel__HypertextLayerAssignment_4 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:644:1: ( rule__WebModel__HypertextLayerAssignment_4 )
            {
             before(grammarAccess.getWebModelAccess().getHypertextLayerAssignment_4()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:645:1: ( rule__WebModel__HypertextLayerAssignment_4 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:645:2: rule__WebModel__HypertextLayerAssignment_4
            {
            pushFollow(FOLLOW_rule__WebModel__HypertextLayerAssignment_4_in_rule__WebModel__Group__4__Impl1316);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:655:1: rule__WebModel__Group__5 : rule__WebModel__Group__5__Impl ;
    public final void rule__WebModel__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:659:1: ( rule__WebModel__Group__5__Impl )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:660:2: rule__WebModel__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__WebModel__Group__5__Impl_in_rule__WebModel__Group__51346);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:666:1: rule__WebModel__Group__5__Impl : ( '}' ) ;
    public final void rule__WebModel__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:670:1: ( ( '}' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:671:1: ( '}' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:671:1: ( '}' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:672:1: '}'
            {
             before(grammarAccess.getWebModelAccess().getRightCurlyBracketKeyword_5()); 
            match(input,18,FOLLOW_18_in_rule__WebModel__Group__5__Impl1374); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:697:1: rule__DataLayer__Group__0 : rule__DataLayer__Group__0__Impl rule__DataLayer__Group__1 ;
    public final void rule__DataLayer__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:701:1: ( rule__DataLayer__Group__0__Impl rule__DataLayer__Group__1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:702:2: rule__DataLayer__Group__0__Impl rule__DataLayer__Group__1
            {
            pushFollow(FOLLOW_rule__DataLayer__Group__0__Impl_in_rule__DataLayer__Group__01417);
            rule__DataLayer__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DataLayer__Group__1_in_rule__DataLayer__Group__01420);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:709:1: rule__DataLayer__Group__0__Impl : ( 'data {' ) ;
    public final void rule__DataLayer__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:713:1: ( ( 'data {' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:714:1: ( 'data {' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:714:1: ( 'data {' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:715:1: 'data {'
            {
             before(grammarAccess.getDataLayerAccess().getDataKeyword_0()); 
            match(input,19,FOLLOW_19_in_rule__DataLayer__Group__0__Impl1448); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:728:1: rule__DataLayer__Group__1 : rule__DataLayer__Group__1__Impl rule__DataLayer__Group__2 ;
    public final void rule__DataLayer__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:732:1: ( rule__DataLayer__Group__1__Impl rule__DataLayer__Group__2 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:733:2: rule__DataLayer__Group__1__Impl rule__DataLayer__Group__2
            {
            pushFollow(FOLLOW_rule__DataLayer__Group__1__Impl_in_rule__DataLayer__Group__11479);
            rule__DataLayer__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DataLayer__Group__2_in_rule__DataLayer__Group__11482);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:740:1: rule__DataLayer__Group__1__Impl : ( () ) ;
    public final void rule__DataLayer__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:744:1: ( ( () ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:745:1: ( () )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:745:1: ( () )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:746:1: ()
            {
             before(grammarAccess.getDataLayerAccess().getDataLayerAction_1()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:747:1: ()
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:749:1: 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:759:1: rule__DataLayer__Group__2 : rule__DataLayer__Group__2__Impl rule__DataLayer__Group__3 ;
    public final void rule__DataLayer__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:763:1: ( rule__DataLayer__Group__2__Impl rule__DataLayer__Group__3 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:764:2: rule__DataLayer__Group__2__Impl rule__DataLayer__Group__3
            {
            pushFollow(FOLLOW_rule__DataLayer__Group__2__Impl_in_rule__DataLayer__Group__21540);
            rule__DataLayer__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DataLayer__Group__3_in_rule__DataLayer__Group__21543);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:771:1: rule__DataLayer__Group__2__Impl : ( ( rule__DataLayer__EntitiesAssignment_2 )* ) ;
    public final void rule__DataLayer__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:775:1: ( ( ( rule__DataLayer__EntitiesAssignment_2 )* ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:776:1: ( ( rule__DataLayer__EntitiesAssignment_2 )* )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:776:1: ( ( rule__DataLayer__EntitiesAssignment_2 )* )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:777:1: ( rule__DataLayer__EntitiesAssignment_2 )*
            {
             before(grammarAccess.getDataLayerAccess().getEntitiesAssignment_2()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:778:1: ( rule__DataLayer__EntitiesAssignment_2 )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==20) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:778:2: rule__DataLayer__EntitiesAssignment_2
            	    {
            	    pushFollow(FOLLOW_rule__DataLayer__EntitiesAssignment_2_in_rule__DataLayer__Group__2__Impl1570);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:788:1: rule__DataLayer__Group__3 : rule__DataLayer__Group__3__Impl ;
    public final void rule__DataLayer__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:792:1: ( rule__DataLayer__Group__3__Impl )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:793:2: rule__DataLayer__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__DataLayer__Group__3__Impl_in_rule__DataLayer__Group__31601);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:799:1: rule__DataLayer__Group__3__Impl : ( '}' ) ;
    public final void rule__DataLayer__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:803:1: ( ( '}' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:804:1: ( '}' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:804:1: ( '}' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:805:1: '}'
            {
             before(grammarAccess.getDataLayerAccess().getRightCurlyBracketKeyword_3()); 
            match(input,18,FOLLOW_18_in_rule__DataLayer__Group__3__Impl1629); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:826:1: rule__Entity__Group__0 : rule__Entity__Group__0__Impl rule__Entity__Group__1 ;
    public final void rule__Entity__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:830:1: ( rule__Entity__Group__0__Impl rule__Entity__Group__1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:831:2: rule__Entity__Group__0__Impl rule__Entity__Group__1
            {
            pushFollow(FOLLOW_rule__Entity__Group__0__Impl_in_rule__Entity__Group__01668);
            rule__Entity__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Entity__Group__1_in_rule__Entity__Group__01671);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:838:1: rule__Entity__Group__0__Impl : ( 'entity' ) ;
    public final void rule__Entity__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:842:1: ( ( 'entity' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:843:1: ( 'entity' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:843:1: ( 'entity' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:844:1: 'entity'
            {
             before(grammarAccess.getEntityAccess().getEntityKeyword_0()); 
            match(input,20,FOLLOW_20_in_rule__Entity__Group__0__Impl1699); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:857:1: rule__Entity__Group__1 : rule__Entity__Group__1__Impl rule__Entity__Group__2 ;
    public final void rule__Entity__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:861:1: ( rule__Entity__Group__1__Impl rule__Entity__Group__2 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:862:2: rule__Entity__Group__1__Impl rule__Entity__Group__2
            {
            pushFollow(FOLLOW_rule__Entity__Group__1__Impl_in_rule__Entity__Group__11730);
            rule__Entity__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Entity__Group__2_in_rule__Entity__Group__11733);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:869:1: rule__Entity__Group__1__Impl : ( ( rule__Entity__NameAssignment_1 ) ) ;
    public final void rule__Entity__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:873:1: ( ( ( rule__Entity__NameAssignment_1 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:874:1: ( ( rule__Entity__NameAssignment_1 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:874:1: ( ( rule__Entity__NameAssignment_1 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:875:1: ( rule__Entity__NameAssignment_1 )
            {
             before(grammarAccess.getEntityAccess().getNameAssignment_1()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:876:1: ( rule__Entity__NameAssignment_1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:876:2: rule__Entity__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__Entity__NameAssignment_1_in_rule__Entity__Group__1__Impl1760);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:886:1: rule__Entity__Group__2 : rule__Entity__Group__2__Impl rule__Entity__Group__3 ;
    public final void rule__Entity__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:890:1: ( rule__Entity__Group__2__Impl rule__Entity__Group__3 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:891:2: rule__Entity__Group__2__Impl rule__Entity__Group__3
            {
            pushFollow(FOLLOW_rule__Entity__Group__2__Impl_in_rule__Entity__Group__21790);
            rule__Entity__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Entity__Group__3_in_rule__Entity__Group__21793);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:898:1: rule__Entity__Group__2__Impl : ( '{' ) ;
    public final void rule__Entity__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:902:1: ( ( '{' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:903:1: ( '{' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:903:1: ( '{' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:904:1: '{'
            {
             before(grammarAccess.getEntityAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,17,FOLLOW_17_in_rule__Entity__Group__2__Impl1821); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:917:1: rule__Entity__Group__3 : rule__Entity__Group__3__Impl rule__Entity__Group__4 ;
    public final void rule__Entity__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:921:1: ( rule__Entity__Group__3__Impl rule__Entity__Group__4 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:922:2: rule__Entity__Group__3__Impl rule__Entity__Group__4
            {
            pushFollow(FOLLOW_rule__Entity__Group__3__Impl_in_rule__Entity__Group__31852);
            rule__Entity__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Entity__Group__4_in_rule__Entity__Group__31855);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:929:1: rule__Entity__Group__3__Impl : ( ( rule__Entity__AttributesAssignment_3 )* ) ;
    public final void rule__Entity__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:933:1: ( ( ( rule__Entity__AttributesAssignment_3 )* ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:934:1: ( ( rule__Entity__AttributesAssignment_3 )* )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:934:1: ( ( rule__Entity__AttributesAssignment_3 )* )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:935:1: ( rule__Entity__AttributesAssignment_3 )*
            {
             before(grammarAccess.getEntityAccess().getAttributesAssignment_3()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:936:1: ( rule__Entity__AttributesAssignment_3 )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==21) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:936:2: rule__Entity__AttributesAssignment_3
            	    {
            	    pushFollow(FOLLOW_rule__Entity__AttributesAssignment_3_in_rule__Entity__Group__3__Impl1882);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:946:1: rule__Entity__Group__4 : rule__Entity__Group__4__Impl rule__Entity__Group__5 ;
    public final void rule__Entity__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:950:1: ( rule__Entity__Group__4__Impl rule__Entity__Group__5 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:951:2: rule__Entity__Group__4__Impl rule__Entity__Group__5
            {
            pushFollow(FOLLOW_rule__Entity__Group__4__Impl_in_rule__Entity__Group__41913);
            rule__Entity__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Entity__Group__5_in_rule__Entity__Group__41916);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:958:1: rule__Entity__Group__4__Impl : ( ( rule__Entity__ReferencesAssignment_4 )* ) ;
    public final void rule__Entity__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:962:1: ( ( ( rule__Entity__ReferencesAssignment_4 )* ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:963:1: ( ( rule__Entity__ReferencesAssignment_4 )* )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:963:1: ( ( rule__Entity__ReferencesAssignment_4 )* )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:964:1: ( rule__Entity__ReferencesAssignment_4 )*
            {
             before(grammarAccess.getEntityAccess().getReferencesAssignment_4()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:965:1: ( rule__Entity__ReferencesAssignment_4 )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==23) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:965:2: rule__Entity__ReferencesAssignment_4
            	    {
            	    pushFollow(FOLLOW_rule__Entity__ReferencesAssignment_4_in_rule__Entity__Group__4__Impl1943);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:975:1: rule__Entity__Group__5 : rule__Entity__Group__5__Impl ;
    public final void rule__Entity__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:979:1: ( rule__Entity__Group__5__Impl )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:980:2: rule__Entity__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__Entity__Group__5__Impl_in_rule__Entity__Group__51974);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:986:1: rule__Entity__Group__5__Impl : ( '}' ) ;
    public final void rule__Entity__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:990:1: ( ( '}' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:991:1: ( '}' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:991:1: ( '}' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:992:1: '}'
            {
             before(grammarAccess.getEntityAccess().getRightCurlyBracketKeyword_5()); 
            match(input,18,FOLLOW_18_in_rule__Entity__Group__5__Impl2002); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1017:1: rule__Attribute__Group__0 : rule__Attribute__Group__0__Impl rule__Attribute__Group__1 ;
    public final void rule__Attribute__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1021:1: ( rule__Attribute__Group__0__Impl rule__Attribute__Group__1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1022:2: rule__Attribute__Group__0__Impl rule__Attribute__Group__1
            {
            pushFollow(FOLLOW_rule__Attribute__Group__0__Impl_in_rule__Attribute__Group__02045);
            rule__Attribute__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Attribute__Group__1_in_rule__Attribute__Group__02048);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1029:1: rule__Attribute__Group__0__Impl : ( 'att' ) ;
    public final void rule__Attribute__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1033:1: ( ( 'att' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1034:1: ( 'att' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1034:1: ( 'att' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1035:1: 'att'
            {
             before(grammarAccess.getAttributeAccess().getAttKeyword_0()); 
            match(input,21,FOLLOW_21_in_rule__Attribute__Group__0__Impl2076); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1048:1: rule__Attribute__Group__1 : rule__Attribute__Group__1__Impl rule__Attribute__Group__2 ;
    public final void rule__Attribute__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1052:1: ( rule__Attribute__Group__1__Impl rule__Attribute__Group__2 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1053:2: rule__Attribute__Group__1__Impl rule__Attribute__Group__2
            {
            pushFollow(FOLLOW_rule__Attribute__Group__1__Impl_in_rule__Attribute__Group__12107);
            rule__Attribute__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Attribute__Group__2_in_rule__Attribute__Group__12110);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1060:1: rule__Attribute__Group__1__Impl : ( ( rule__Attribute__NameAssignment_1 ) ) ;
    public final void rule__Attribute__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1064:1: ( ( ( rule__Attribute__NameAssignment_1 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1065:1: ( ( rule__Attribute__NameAssignment_1 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1065:1: ( ( rule__Attribute__NameAssignment_1 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1066:1: ( rule__Attribute__NameAssignment_1 )
            {
             before(grammarAccess.getAttributeAccess().getNameAssignment_1()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1067:1: ( rule__Attribute__NameAssignment_1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1067:2: rule__Attribute__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__Attribute__NameAssignment_1_in_rule__Attribute__Group__1__Impl2137);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1077:1: rule__Attribute__Group__2 : rule__Attribute__Group__2__Impl rule__Attribute__Group__3 ;
    public final void rule__Attribute__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1081:1: ( rule__Attribute__Group__2__Impl rule__Attribute__Group__3 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1082:2: rule__Attribute__Group__2__Impl rule__Attribute__Group__3
            {
            pushFollow(FOLLOW_rule__Attribute__Group__2__Impl_in_rule__Attribute__Group__22167);
            rule__Attribute__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Attribute__Group__3_in_rule__Attribute__Group__22170);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1089:1: rule__Attribute__Group__2__Impl : ( ':' ) ;
    public final void rule__Attribute__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1093:1: ( ( ':' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1094:1: ( ':' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1094:1: ( ':' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1095:1: ':'
            {
             before(grammarAccess.getAttributeAccess().getColonKeyword_2()); 
            match(input,22,FOLLOW_22_in_rule__Attribute__Group__2__Impl2198); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1108:1: rule__Attribute__Group__3 : rule__Attribute__Group__3__Impl ;
    public final void rule__Attribute__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1112:1: ( rule__Attribute__Group__3__Impl )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1113:2: rule__Attribute__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Attribute__Group__3__Impl_in_rule__Attribute__Group__32229);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1119:1: rule__Attribute__Group__3__Impl : ( ( rule__Attribute__TypeAssignment_3 ) ) ;
    public final void rule__Attribute__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1123:1: ( ( ( rule__Attribute__TypeAssignment_3 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1124:1: ( ( rule__Attribute__TypeAssignment_3 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1124:1: ( ( rule__Attribute__TypeAssignment_3 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1125:1: ( rule__Attribute__TypeAssignment_3 )
            {
             before(grammarAccess.getAttributeAccess().getTypeAssignment_3()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1126:1: ( rule__Attribute__TypeAssignment_3 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1126:2: rule__Attribute__TypeAssignment_3
            {
            pushFollow(FOLLOW_rule__Attribute__TypeAssignment_3_in_rule__Attribute__Group__3__Impl2256);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1144:1: rule__Reference__Group__0 : rule__Reference__Group__0__Impl rule__Reference__Group__1 ;
    public final void rule__Reference__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1148:1: ( rule__Reference__Group__0__Impl rule__Reference__Group__1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1149:2: rule__Reference__Group__0__Impl rule__Reference__Group__1
            {
            pushFollow(FOLLOW_rule__Reference__Group__0__Impl_in_rule__Reference__Group__02294);
            rule__Reference__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Reference__Group__1_in_rule__Reference__Group__02297);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1156:1: rule__Reference__Group__0__Impl : ( 'ref' ) ;
    public final void rule__Reference__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1160:1: ( ( 'ref' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1161:1: ( 'ref' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1161:1: ( 'ref' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1162:1: 'ref'
            {
             before(grammarAccess.getReferenceAccess().getRefKeyword_0()); 
            match(input,23,FOLLOW_23_in_rule__Reference__Group__0__Impl2325); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1175:1: rule__Reference__Group__1 : rule__Reference__Group__1__Impl rule__Reference__Group__2 ;
    public final void rule__Reference__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1179:1: ( rule__Reference__Group__1__Impl rule__Reference__Group__2 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1180:2: rule__Reference__Group__1__Impl rule__Reference__Group__2
            {
            pushFollow(FOLLOW_rule__Reference__Group__1__Impl_in_rule__Reference__Group__12356);
            rule__Reference__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Reference__Group__2_in_rule__Reference__Group__12359);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1187:1: rule__Reference__Group__1__Impl : ( ( rule__Reference__NameAssignment_1 ) ) ;
    public final void rule__Reference__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1191:1: ( ( ( rule__Reference__NameAssignment_1 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1192:1: ( ( rule__Reference__NameAssignment_1 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1192:1: ( ( rule__Reference__NameAssignment_1 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1193:1: ( rule__Reference__NameAssignment_1 )
            {
             before(grammarAccess.getReferenceAccess().getNameAssignment_1()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1194:1: ( rule__Reference__NameAssignment_1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1194:2: rule__Reference__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__Reference__NameAssignment_1_in_rule__Reference__Group__1__Impl2386);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1204:1: rule__Reference__Group__2 : rule__Reference__Group__2__Impl rule__Reference__Group__3 ;
    public final void rule__Reference__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1208:1: ( rule__Reference__Group__2__Impl rule__Reference__Group__3 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1209:2: rule__Reference__Group__2__Impl rule__Reference__Group__3
            {
            pushFollow(FOLLOW_rule__Reference__Group__2__Impl_in_rule__Reference__Group__22416);
            rule__Reference__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Reference__Group__3_in_rule__Reference__Group__22419);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1216:1: rule__Reference__Group__2__Impl : ( ':' ) ;
    public final void rule__Reference__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1220:1: ( ( ':' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1221:1: ( ':' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1221:1: ( ':' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1222:1: ':'
            {
             before(grammarAccess.getReferenceAccess().getColonKeyword_2()); 
            match(input,22,FOLLOW_22_in_rule__Reference__Group__2__Impl2447); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1235:1: rule__Reference__Group__3 : rule__Reference__Group__3__Impl ;
    public final void rule__Reference__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1239:1: ( rule__Reference__Group__3__Impl )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1240:2: rule__Reference__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Reference__Group__3__Impl_in_rule__Reference__Group__32478);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1246:1: rule__Reference__Group__3__Impl : ( ( rule__Reference__TypeAssignment_3 ) ) ;
    public final void rule__Reference__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1250:1: ( ( ( rule__Reference__TypeAssignment_3 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1251:1: ( ( rule__Reference__TypeAssignment_3 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1251:1: ( ( rule__Reference__TypeAssignment_3 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1252:1: ( rule__Reference__TypeAssignment_3 )
            {
             before(grammarAccess.getReferenceAccess().getTypeAssignment_3()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1253:1: ( rule__Reference__TypeAssignment_3 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1253:2: rule__Reference__TypeAssignment_3
            {
            pushFollow(FOLLOW_rule__Reference__TypeAssignment_3_in_rule__Reference__Group__3__Impl2505);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1271:1: rule__HypertextLayer__Group__0 : rule__HypertextLayer__Group__0__Impl rule__HypertextLayer__Group__1 ;
    public final void rule__HypertextLayer__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1275:1: ( rule__HypertextLayer__Group__0__Impl rule__HypertextLayer__Group__1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1276:2: rule__HypertextLayer__Group__0__Impl rule__HypertextLayer__Group__1
            {
            pushFollow(FOLLOW_rule__HypertextLayer__Group__0__Impl_in_rule__HypertextLayer__Group__02543);
            rule__HypertextLayer__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__HypertextLayer__Group__1_in_rule__HypertextLayer__Group__02546);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1283:1: rule__HypertextLayer__Group__0__Impl : ( 'hypertext {' ) ;
    public final void rule__HypertextLayer__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1287:1: ( ( 'hypertext {' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1288:1: ( 'hypertext {' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1288:1: ( 'hypertext {' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1289:1: 'hypertext {'
            {
             before(grammarAccess.getHypertextLayerAccess().getHypertextKeyword_0()); 
            match(input,24,FOLLOW_24_in_rule__HypertextLayer__Group__0__Impl2574); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1302:1: rule__HypertextLayer__Group__1 : rule__HypertextLayer__Group__1__Impl rule__HypertextLayer__Group__2 ;
    public final void rule__HypertextLayer__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1306:1: ( rule__HypertextLayer__Group__1__Impl rule__HypertextLayer__Group__2 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1307:2: rule__HypertextLayer__Group__1__Impl rule__HypertextLayer__Group__2
            {
            pushFollow(FOLLOW_rule__HypertextLayer__Group__1__Impl_in_rule__HypertextLayer__Group__12605);
            rule__HypertextLayer__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__HypertextLayer__Group__2_in_rule__HypertextLayer__Group__12608);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1314:1: rule__HypertextLayer__Group__1__Impl : ( ( ( rule__HypertextLayer__PagesAssignment_1 ) ) ( ( rule__HypertextLayer__PagesAssignment_1 )* ) ) ;
    public final void rule__HypertextLayer__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1318:1: ( ( ( ( rule__HypertextLayer__PagesAssignment_1 ) ) ( ( rule__HypertextLayer__PagesAssignment_1 )* ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1319:1: ( ( ( rule__HypertextLayer__PagesAssignment_1 ) ) ( ( rule__HypertextLayer__PagesAssignment_1 )* ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1319:1: ( ( ( rule__HypertextLayer__PagesAssignment_1 ) ) ( ( rule__HypertextLayer__PagesAssignment_1 )* ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1320:1: ( ( rule__HypertextLayer__PagesAssignment_1 ) ) ( ( rule__HypertextLayer__PagesAssignment_1 )* )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1320:1: ( ( rule__HypertextLayer__PagesAssignment_1 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1321:1: ( rule__HypertextLayer__PagesAssignment_1 )
            {
             before(grammarAccess.getHypertextLayerAccess().getPagesAssignment_1()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1322:1: ( rule__HypertextLayer__PagesAssignment_1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1322:2: rule__HypertextLayer__PagesAssignment_1
            {
            pushFollow(FOLLOW_rule__HypertextLayer__PagesAssignment_1_in_rule__HypertextLayer__Group__1__Impl2637);
            rule__HypertextLayer__PagesAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getHypertextLayerAccess().getPagesAssignment_1()); 

            }

            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1325:1: ( ( rule__HypertextLayer__PagesAssignment_1 )* )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1326:1: ( rule__HypertextLayer__PagesAssignment_1 )*
            {
             before(grammarAccess.getHypertextLayerAccess().getPagesAssignment_1()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1327:1: ( rule__HypertextLayer__PagesAssignment_1 )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==26||LA7_0==28||LA7_0==30) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1327:2: rule__HypertextLayer__PagesAssignment_1
            	    {
            	    pushFollow(FOLLOW_rule__HypertextLayer__PagesAssignment_1_in_rule__HypertextLayer__Group__1__Impl2649);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1338:1: rule__HypertextLayer__Group__2 : rule__HypertextLayer__Group__2__Impl rule__HypertextLayer__Group__3 ;
    public final void rule__HypertextLayer__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1342:1: ( rule__HypertextLayer__Group__2__Impl rule__HypertextLayer__Group__3 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1343:2: rule__HypertextLayer__Group__2__Impl rule__HypertextLayer__Group__3
            {
            pushFollow(FOLLOW_rule__HypertextLayer__Group__2__Impl_in_rule__HypertextLayer__Group__22682);
            rule__HypertextLayer__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__HypertextLayer__Group__3_in_rule__HypertextLayer__Group__22685);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1350:1: rule__HypertextLayer__Group__2__Impl : ( 'start page is' ) ;
    public final void rule__HypertextLayer__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1354:1: ( ( 'start page is' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1355:1: ( 'start page is' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1355:1: ( 'start page is' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1356:1: 'start page is'
            {
             before(grammarAccess.getHypertextLayerAccess().getStartPageIsKeyword_2()); 
            match(input,25,FOLLOW_25_in_rule__HypertextLayer__Group__2__Impl2713); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1369:1: rule__HypertextLayer__Group__3 : rule__HypertextLayer__Group__3__Impl rule__HypertextLayer__Group__4 ;
    public final void rule__HypertextLayer__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1373:1: ( rule__HypertextLayer__Group__3__Impl rule__HypertextLayer__Group__4 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1374:2: rule__HypertextLayer__Group__3__Impl rule__HypertextLayer__Group__4
            {
            pushFollow(FOLLOW_rule__HypertextLayer__Group__3__Impl_in_rule__HypertextLayer__Group__32744);
            rule__HypertextLayer__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__HypertextLayer__Group__4_in_rule__HypertextLayer__Group__32747);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1381:1: rule__HypertextLayer__Group__3__Impl : ( ( rule__HypertextLayer__StartPageAssignment_3 ) ) ;
    public final void rule__HypertextLayer__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1385:1: ( ( ( rule__HypertextLayer__StartPageAssignment_3 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1386:1: ( ( rule__HypertextLayer__StartPageAssignment_3 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1386:1: ( ( rule__HypertextLayer__StartPageAssignment_3 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1387:1: ( rule__HypertextLayer__StartPageAssignment_3 )
            {
             before(grammarAccess.getHypertextLayerAccess().getStartPageAssignment_3()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1388:1: ( rule__HypertextLayer__StartPageAssignment_3 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1388:2: rule__HypertextLayer__StartPageAssignment_3
            {
            pushFollow(FOLLOW_rule__HypertextLayer__StartPageAssignment_3_in_rule__HypertextLayer__Group__3__Impl2774);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1398:1: rule__HypertextLayer__Group__4 : rule__HypertextLayer__Group__4__Impl ;
    public final void rule__HypertextLayer__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1402:1: ( rule__HypertextLayer__Group__4__Impl )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1403:2: rule__HypertextLayer__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__HypertextLayer__Group__4__Impl_in_rule__HypertextLayer__Group__42804);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1409:1: rule__HypertextLayer__Group__4__Impl : ( '}' ) ;
    public final void rule__HypertextLayer__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1413:1: ( ( '}' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1414:1: ( '}' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1414:1: ( '}' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1415:1: '}'
            {
             before(grammarAccess.getHypertextLayerAccess().getRightCurlyBracketKeyword_4()); 
            match(input,18,FOLLOW_18_in_rule__HypertextLayer__Group__4__Impl2832); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1438:1: rule__StaticPage__Group__0 : rule__StaticPage__Group__0__Impl rule__StaticPage__Group__1 ;
    public final void rule__StaticPage__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1442:1: ( rule__StaticPage__Group__0__Impl rule__StaticPage__Group__1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1443:2: rule__StaticPage__Group__0__Impl rule__StaticPage__Group__1
            {
            pushFollow(FOLLOW_rule__StaticPage__Group__0__Impl_in_rule__StaticPage__Group__02873);
            rule__StaticPage__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__StaticPage__Group__1_in_rule__StaticPage__Group__02876);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1450:1: rule__StaticPage__Group__0__Impl : ( 'static page' ) ;
    public final void rule__StaticPage__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1454:1: ( ( 'static page' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1455:1: ( 'static page' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1455:1: ( 'static page' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1456:1: 'static page'
            {
             before(grammarAccess.getStaticPageAccess().getStaticPageKeyword_0()); 
            match(input,26,FOLLOW_26_in_rule__StaticPage__Group__0__Impl2904); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1469:1: rule__StaticPage__Group__1 : rule__StaticPage__Group__1__Impl rule__StaticPage__Group__2 ;
    public final void rule__StaticPage__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1473:1: ( rule__StaticPage__Group__1__Impl rule__StaticPage__Group__2 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1474:2: rule__StaticPage__Group__1__Impl rule__StaticPage__Group__2
            {
            pushFollow(FOLLOW_rule__StaticPage__Group__1__Impl_in_rule__StaticPage__Group__12935);
            rule__StaticPage__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__StaticPage__Group__2_in_rule__StaticPage__Group__12938);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1481:1: rule__StaticPage__Group__1__Impl : ( ( rule__StaticPage__NameAssignment_1 ) ) ;
    public final void rule__StaticPage__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1485:1: ( ( ( rule__StaticPage__NameAssignment_1 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1486:1: ( ( rule__StaticPage__NameAssignment_1 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1486:1: ( ( rule__StaticPage__NameAssignment_1 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1487:1: ( rule__StaticPage__NameAssignment_1 )
            {
             before(grammarAccess.getStaticPageAccess().getNameAssignment_1()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1488:1: ( rule__StaticPage__NameAssignment_1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1488:2: rule__StaticPage__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__StaticPage__NameAssignment_1_in_rule__StaticPage__Group__1__Impl2965);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1498:1: rule__StaticPage__Group__2 : rule__StaticPage__Group__2__Impl rule__StaticPage__Group__3 ;
    public final void rule__StaticPage__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1502:1: ( rule__StaticPage__Group__2__Impl rule__StaticPage__Group__3 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1503:2: rule__StaticPage__Group__2__Impl rule__StaticPage__Group__3
            {
            pushFollow(FOLLOW_rule__StaticPage__Group__2__Impl_in_rule__StaticPage__Group__22995);
            rule__StaticPage__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__StaticPage__Group__3_in_rule__StaticPage__Group__22998);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1510:1: rule__StaticPage__Group__2__Impl : ( '{' ) ;
    public final void rule__StaticPage__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1514:1: ( ( '{' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1515:1: ( '{' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1515:1: ( '{' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1516:1: '{'
            {
             before(grammarAccess.getStaticPageAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,17,FOLLOW_17_in_rule__StaticPage__Group__2__Impl3026); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1529:1: rule__StaticPage__Group__3 : rule__StaticPage__Group__3__Impl rule__StaticPage__Group__4 ;
    public final void rule__StaticPage__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1533:1: ( rule__StaticPage__Group__3__Impl rule__StaticPage__Group__4 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1534:2: rule__StaticPage__Group__3__Impl rule__StaticPage__Group__4
            {
            pushFollow(FOLLOW_rule__StaticPage__Group__3__Impl_in_rule__StaticPage__Group__33057);
            rule__StaticPage__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__StaticPage__Group__4_in_rule__StaticPage__Group__33060);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1541:1: rule__StaticPage__Group__3__Impl : ( ( rule__StaticPage__LinksAssignment_3 )* ) ;
    public final void rule__StaticPage__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1545:1: ( ( ( rule__StaticPage__LinksAssignment_3 )* ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1546:1: ( ( rule__StaticPage__LinksAssignment_3 )* )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1546:1: ( ( rule__StaticPage__LinksAssignment_3 )* )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1547:1: ( rule__StaticPage__LinksAssignment_3 )*
            {
             before(grammarAccess.getStaticPageAccess().getLinksAssignment_3()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1548:1: ( rule__StaticPage__LinksAssignment_3 )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==27) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1548:2: rule__StaticPage__LinksAssignment_3
            	    {
            	    pushFollow(FOLLOW_rule__StaticPage__LinksAssignment_3_in_rule__StaticPage__Group__3__Impl3087);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1558:1: rule__StaticPage__Group__4 : rule__StaticPage__Group__4__Impl ;
    public final void rule__StaticPage__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1562:1: ( rule__StaticPage__Group__4__Impl )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1563:2: rule__StaticPage__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__StaticPage__Group__4__Impl_in_rule__StaticPage__Group__43118);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1569:1: rule__StaticPage__Group__4__Impl : ( '}' ) ;
    public final void rule__StaticPage__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1573:1: ( ( '}' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1574:1: ( '}' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1574:1: ( '}' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1575:1: '}'
            {
             before(grammarAccess.getStaticPageAccess().getRightCurlyBracketKeyword_4()); 
            match(input,18,FOLLOW_18_in_rule__StaticPage__Group__4__Impl3146); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1598:1: rule__Link__Group__0 : rule__Link__Group__0__Impl rule__Link__Group__1 ;
    public final void rule__Link__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1602:1: ( rule__Link__Group__0__Impl rule__Link__Group__1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1603:2: rule__Link__Group__0__Impl rule__Link__Group__1
            {
            pushFollow(FOLLOW_rule__Link__Group__0__Impl_in_rule__Link__Group__03187);
            rule__Link__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Link__Group__1_in_rule__Link__Group__03190);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1610:1: rule__Link__Group__0__Impl : ( 'link to page' ) ;
    public final void rule__Link__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1614:1: ( ( 'link to page' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1615:1: ( 'link to page' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1615:1: ( 'link to page' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1616:1: 'link to page'
            {
             before(grammarAccess.getLinkAccess().getLinkToPageKeyword_0()); 
            match(input,27,FOLLOW_27_in_rule__Link__Group__0__Impl3218); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1629:1: rule__Link__Group__1 : rule__Link__Group__1__Impl ;
    public final void rule__Link__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1633:1: ( rule__Link__Group__1__Impl )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1634:2: rule__Link__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Link__Group__1__Impl_in_rule__Link__Group__13249);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1640:1: rule__Link__Group__1__Impl : ( ( rule__Link__TargetAssignment_1 ) ) ;
    public final void rule__Link__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1644:1: ( ( ( rule__Link__TargetAssignment_1 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1645:1: ( ( rule__Link__TargetAssignment_1 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1645:1: ( ( rule__Link__TargetAssignment_1 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1646:1: ( rule__Link__TargetAssignment_1 )
            {
             before(grammarAccess.getLinkAccess().getTargetAssignment_1()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1647:1: ( rule__Link__TargetAssignment_1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1647:2: rule__Link__TargetAssignment_1
            {
            pushFollow(FOLLOW_rule__Link__TargetAssignment_1_in_rule__Link__Group__1__Impl3276);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1661:1: rule__IndexPage__Group__0 : rule__IndexPage__Group__0__Impl rule__IndexPage__Group__1 ;
    public final void rule__IndexPage__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1665:1: ( rule__IndexPage__Group__0__Impl rule__IndexPage__Group__1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1666:2: rule__IndexPage__Group__0__Impl rule__IndexPage__Group__1
            {
            pushFollow(FOLLOW_rule__IndexPage__Group__0__Impl_in_rule__IndexPage__Group__03310);
            rule__IndexPage__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__IndexPage__Group__1_in_rule__IndexPage__Group__03313);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1673:1: rule__IndexPage__Group__0__Impl : ( 'index page' ) ;
    public final void rule__IndexPage__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1677:1: ( ( 'index page' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1678:1: ( 'index page' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1678:1: ( 'index page' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1679:1: 'index page'
            {
             before(grammarAccess.getIndexPageAccess().getIndexPageKeyword_0()); 
            match(input,28,FOLLOW_28_in_rule__IndexPage__Group__0__Impl3341); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1692:1: rule__IndexPage__Group__1 : rule__IndexPage__Group__1__Impl rule__IndexPage__Group__2 ;
    public final void rule__IndexPage__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1696:1: ( rule__IndexPage__Group__1__Impl rule__IndexPage__Group__2 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1697:2: rule__IndexPage__Group__1__Impl rule__IndexPage__Group__2
            {
            pushFollow(FOLLOW_rule__IndexPage__Group__1__Impl_in_rule__IndexPage__Group__13372);
            rule__IndexPage__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__IndexPage__Group__2_in_rule__IndexPage__Group__13375);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1704:1: rule__IndexPage__Group__1__Impl : ( ( rule__IndexPage__NameAssignment_1 ) ) ;
    public final void rule__IndexPage__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1708:1: ( ( ( rule__IndexPage__NameAssignment_1 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1709:1: ( ( rule__IndexPage__NameAssignment_1 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1709:1: ( ( rule__IndexPage__NameAssignment_1 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1710:1: ( rule__IndexPage__NameAssignment_1 )
            {
             before(grammarAccess.getIndexPageAccess().getNameAssignment_1()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1711:1: ( rule__IndexPage__NameAssignment_1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1711:2: rule__IndexPage__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__IndexPage__NameAssignment_1_in_rule__IndexPage__Group__1__Impl3402);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1721:1: rule__IndexPage__Group__2 : rule__IndexPage__Group__2__Impl rule__IndexPage__Group__3 ;
    public final void rule__IndexPage__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1725:1: ( rule__IndexPage__Group__2__Impl rule__IndexPage__Group__3 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1726:2: rule__IndexPage__Group__2__Impl rule__IndexPage__Group__3
            {
            pushFollow(FOLLOW_rule__IndexPage__Group__2__Impl_in_rule__IndexPage__Group__23432);
            rule__IndexPage__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__IndexPage__Group__3_in_rule__IndexPage__Group__23435);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1733:1: rule__IndexPage__Group__2__Impl : ( ( rule__IndexPage__Group_2__0 )? ) ;
    public final void rule__IndexPage__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1737:1: ( ( ( rule__IndexPage__Group_2__0 )? ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1738:1: ( ( rule__IndexPage__Group_2__0 )? )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1738:1: ( ( rule__IndexPage__Group_2__0 )? )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1739:1: ( rule__IndexPage__Group_2__0 )?
            {
             before(grammarAccess.getIndexPageAccess().getGroup_2()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1740:1: ( rule__IndexPage__Group_2__0 )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==29) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1740:2: rule__IndexPage__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__IndexPage__Group_2__0_in_rule__IndexPage__Group__2__Impl3462);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1750:1: rule__IndexPage__Group__3 : rule__IndexPage__Group__3__Impl rule__IndexPage__Group__4 ;
    public final void rule__IndexPage__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1754:1: ( rule__IndexPage__Group__3__Impl rule__IndexPage__Group__4 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1755:2: rule__IndexPage__Group__3__Impl rule__IndexPage__Group__4
            {
            pushFollow(FOLLOW_rule__IndexPage__Group__3__Impl_in_rule__IndexPage__Group__33493);
            rule__IndexPage__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__IndexPage__Group__4_in_rule__IndexPage__Group__33496);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1762:1: rule__IndexPage__Group__3__Impl : ( '{' ) ;
    public final void rule__IndexPage__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1766:1: ( ( '{' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1767:1: ( '{' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1767:1: ( '{' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1768:1: '{'
            {
             before(grammarAccess.getIndexPageAccess().getLeftCurlyBracketKeyword_3()); 
            match(input,17,FOLLOW_17_in_rule__IndexPage__Group__3__Impl3524); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1781:1: rule__IndexPage__Group__4 : rule__IndexPage__Group__4__Impl rule__IndexPage__Group__5 ;
    public final void rule__IndexPage__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1785:1: ( rule__IndexPage__Group__4__Impl rule__IndexPage__Group__5 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1786:2: rule__IndexPage__Group__4__Impl rule__IndexPage__Group__5
            {
            pushFollow(FOLLOW_rule__IndexPage__Group__4__Impl_in_rule__IndexPage__Group__43555);
            rule__IndexPage__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__IndexPage__Group__5_in_rule__IndexPage__Group__43558);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1793:1: rule__IndexPage__Group__4__Impl : ( ( rule__IndexPage__LinksAssignment_4 )* ) ;
    public final void rule__IndexPage__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1797:1: ( ( ( rule__IndexPage__LinksAssignment_4 )* ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1798:1: ( ( rule__IndexPage__LinksAssignment_4 )* )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1798:1: ( ( rule__IndexPage__LinksAssignment_4 )* )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1799:1: ( rule__IndexPage__LinksAssignment_4 )*
            {
             before(grammarAccess.getIndexPageAccess().getLinksAssignment_4()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1800:1: ( rule__IndexPage__LinksAssignment_4 )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==27) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1800:2: rule__IndexPage__LinksAssignment_4
            	    {
            	    pushFollow(FOLLOW_rule__IndexPage__LinksAssignment_4_in_rule__IndexPage__Group__4__Impl3585);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1810:1: rule__IndexPage__Group__5 : rule__IndexPage__Group__5__Impl ;
    public final void rule__IndexPage__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1814:1: ( rule__IndexPage__Group__5__Impl )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1815:2: rule__IndexPage__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__IndexPage__Group__5__Impl_in_rule__IndexPage__Group__53616);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1821:1: rule__IndexPage__Group__5__Impl : ( '}' ) ;
    public final void rule__IndexPage__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1825:1: ( ( '}' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1826:1: ( '}' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1826:1: ( '}' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1827:1: '}'
            {
             before(grammarAccess.getIndexPageAccess().getRightCurlyBracketKeyword_5()); 
            match(input,18,FOLLOW_18_in_rule__IndexPage__Group__5__Impl3644); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1852:1: rule__IndexPage__Group_2__0 : rule__IndexPage__Group_2__0__Impl rule__IndexPage__Group_2__1 ;
    public final void rule__IndexPage__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1856:1: ( rule__IndexPage__Group_2__0__Impl rule__IndexPage__Group_2__1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1857:2: rule__IndexPage__Group_2__0__Impl rule__IndexPage__Group_2__1
            {
            pushFollow(FOLLOW_rule__IndexPage__Group_2__0__Impl_in_rule__IndexPage__Group_2__03687);
            rule__IndexPage__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__IndexPage__Group_2__1_in_rule__IndexPage__Group_2__03690);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1864:1: rule__IndexPage__Group_2__0__Impl : ( 'shows entity' ) ;
    public final void rule__IndexPage__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1868:1: ( ( 'shows entity' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1869:1: ( 'shows entity' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1869:1: ( 'shows entity' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1870:1: 'shows entity'
            {
             before(grammarAccess.getIndexPageAccess().getShowsEntityKeyword_2_0()); 
            match(input,29,FOLLOW_29_in_rule__IndexPage__Group_2__0__Impl3718); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1883:1: rule__IndexPage__Group_2__1 : rule__IndexPage__Group_2__1__Impl ;
    public final void rule__IndexPage__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1887:1: ( rule__IndexPage__Group_2__1__Impl )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1888:2: rule__IndexPage__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__IndexPage__Group_2__1__Impl_in_rule__IndexPage__Group_2__13749);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1894:1: rule__IndexPage__Group_2__1__Impl : ( ( rule__IndexPage__EntityAssignment_2_1 ) ) ;
    public final void rule__IndexPage__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1898:1: ( ( ( rule__IndexPage__EntityAssignment_2_1 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1899:1: ( ( rule__IndexPage__EntityAssignment_2_1 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1899:1: ( ( rule__IndexPage__EntityAssignment_2_1 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1900:1: ( rule__IndexPage__EntityAssignment_2_1 )
            {
             before(grammarAccess.getIndexPageAccess().getEntityAssignment_2_1()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1901:1: ( rule__IndexPage__EntityAssignment_2_1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1901:2: rule__IndexPage__EntityAssignment_2_1
            {
            pushFollow(FOLLOW_rule__IndexPage__EntityAssignment_2_1_in_rule__IndexPage__Group_2__1__Impl3776);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1915:1: rule__DataPage__Group__0 : rule__DataPage__Group__0__Impl rule__DataPage__Group__1 ;
    public final void rule__DataPage__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1919:1: ( rule__DataPage__Group__0__Impl rule__DataPage__Group__1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1920:2: rule__DataPage__Group__0__Impl rule__DataPage__Group__1
            {
            pushFollow(FOLLOW_rule__DataPage__Group__0__Impl_in_rule__DataPage__Group__03810);
            rule__DataPage__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DataPage__Group__1_in_rule__DataPage__Group__03813);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1927:1: rule__DataPage__Group__0__Impl : ( 'data page' ) ;
    public final void rule__DataPage__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1931:1: ( ( 'data page' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1932:1: ( 'data page' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1932:1: ( 'data page' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1933:1: 'data page'
            {
             before(grammarAccess.getDataPageAccess().getDataPageKeyword_0()); 
            match(input,30,FOLLOW_30_in_rule__DataPage__Group__0__Impl3841); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1946:1: rule__DataPage__Group__1 : rule__DataPage__Group__1__Impl rule__DataPage__Group__2 ;
    public final void rule__DataPage__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1950:1: ( rule__DataPage__Group__1__Impl rule__DataPage__Group__2 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1951:2: rule__DataPage__Group__1__Impl rule__DataPage__Group__2
            {
            pushFollow(FOLLOW_rule__DataPage__Group__1__Impl_in_rule__DataPage__Group__13872);
            rule__DataPage__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DataPage__Group__2_in_rule__DataPage__Group__13875);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1958:1: rule__DataPage__Group__1__Impl : ( ( rule__DataPage__NameAssignment_1 ) ) ;
    public final void rule__DataPage__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1962:1: ( ( ( rule__DataPage__NameAssignment_1 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1963:1: ( ( rule__DataPage__NameAssignment_1 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1963:1: ( ( rule__DataPage__NameAssignment_1 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1964:1: ( rule__DataPage__NameAssignment_1 )
            {
             before(grammarAccess.getDataPageAccess().getNameAssignment_1()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1965:1: ( rule__DataPage__NameAssignment_1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1965:2: rule__DataPage__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__DataPage__NameAssignment_1_in_rule__DataPage__Group__1__Impl3902);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1975:1: rule__DataPage__Group__2 : rule__DataPage__Group__2__Impl rule__DataPage__Group__3 ;
    public final void rule__DataPage__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1979:1: ( rule__DataPage__Group__2__Impl rule__DataPage__Group__3 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1980:2: rule__DataPage__Group__2__Impl rule__DataPage__Group__3
            {
            pushFollow(FOLLOW_rule__DataPage__Group__2__Impl_in_rule__DataPage__Group__23932);
            rule__DataPage__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DataPage__Group__3_in_rule__DataPage__Group__23935);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1987:1: rule__DataPage__Group__2__Impl : ( ( rule__DataPage__Group_2__0 )? ) ;
    public final void rule__DataPage__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1991:1: ( ( ( rule__DataPage__Group_2__0 )? ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1992:1: ( ( rule__DataPage__Group_2__0 )? )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1992:1: ( ( rule__DataPage__Group_2__0 )? )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1993:1: ( rule__DataPage__Group_2__0 )?
            {
             before(grammarAccess.getDataPageAccess().getGroup_2()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1994:1: ( rule__DataPage__Group_2__0 )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==29) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:1994:2: rule__DataPage__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__DataPage__Group_2__0_in_rule__DataPage__Group__2__Impl3962);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2004:1: rule__DataPage__Group__3 : rule__DataPage__Group__3__Impl rule__DataPage__Group__4 ;
    public final void rule__DataPage__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2008:1: ( rule__DataPage__Group__3__Impl rule__DataPage__Group__4 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2009:2: rule__DataPage__Group__3__Impl rule__DataPage__Group__4
            {
            pushFollow(FOLLOW_rule__DataPage__Group__3__Impl_in_rule__DataPage__Group__33993);
            rule__DataPage__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DataPage__Group__4_in_rule__DataPage__Group__33996);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2016:1: rule__DataPage__Group__3__Impl : ( '{' ) ;
    public final void rule__DataPage__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2020:1: ( ( '{' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2021:1: ( '{' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2021:1: ( '{' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2022:1: '{'
            {
             before(grammarAccess.getDataPageAccess().getLeftCurlyBracketKeyword_3()); 
            match(input,17,FOLLOW_17_in_rule__DataPage__Group__3__Impl4024); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2035:1: rule__DataPage__Group__4 : rule__DataPage__Group__4__Impl rule__DataPage__Group__5 ;
    public final void rule__DataPage__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2039:1: ( rule__DataPage__Group__4__Impl rule__DataPage__Group__5 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2040:2: rule__DataPage__Group__4__Impl rule__DataPage__Group__5
            {
            pushFollow(FOLLOW_rule__DataPage__Group__4__Impl_in_rule__DataPage__Group__44055);
            rule__DataPage__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DataPage__Group__5_in_rule__DataPage__Group__44058);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2047:1: rule__DataPage__Group__4__Impl : ( ( rule__DataPage__LinksAssignment_4 )* ) ;
    public final void rule__DataPage__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2051:1: ( ( ( rule__DataPage__LinksAssignment_4 )* ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2052:1: ( ( rule__DataPage__LinksAssignment_4 )* )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2052:1: ( ( rule__DataPage__LinksAssignment_4 )* )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2053:1: ( rule__DataPage__LinksAssignment_4 )*
            {
             before(grammarAccess.getDataPageAccess().getLinksAssignment_4()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2054:1: ( rule__DataPage__LinksAssignment_4 )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==27) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2054:2: rule__DataPage__LinksAssignment_4
            	    {
            	    pushFollow(FOLLOW_rule__DataPage__LinksAssignment_4_in_rule__DataPage__Group__4__Impl4085);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2064:1: rule__DataPage__Group__5 : rule__DataPage__Group__5__Impl ;
    public final void rule__DataPage__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2068:1: ( rule__DataPage__Group__5__Impl )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2069:2: rule__DataPage__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__DataPage__Group__5__Impl_in_rule__DataPage__Group__54116);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2075:1: rule__DataPage__Group__5__Impl : ( '}' ) ;
    public final void rule__DataPage__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2079:1: ( ( '}' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2080:1: ( '}' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2080:1: ( '}' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2081:1: '}'
            {
             before(grammarAccess.getDataPageAccess().getRightCurlyBracketKeyword_5()); 
            match(input,18,FOLLOW_18_in_rule__DataPage__Group__5__Impl4144); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2106:1: rule__DataPage__Group_2__0 : rule__DataPage__Group_2__0__Impl rule__DataPage__Group_2__1 ;
    public final void rule__DataPage__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2110:1: ( rule__DataPage__Group_2__0__Impl rule__DataPage__Group_2__1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2111:2: rule__DataPage__Group_2__0__Impl rule__DataPage__Group_2__1
            {
            pushFollow(FOLLOW_rule__DataPage__Group_2__0__Impl_in_rule__DataPage__Group_2__04187);
            rule__DataPage__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__DataPage__Group_2__1_in_rule__DataPage__Group_2__04190);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2118:1: rule__DataPage__Group_2__0__Impl : ( 'shows entity' ) ;
    public final void rule__DataPage__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2122:1: ( ( 'shows entity' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2123:1: ( 'shows entity' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2123:1: ( 'shows entity' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2124:1: 'shows entity'
            {
             before(grammarAccess.getDataPageAccess().getShowsEntityKeyword_2_0()); 
            match(input,29,FOLLOW_29_in_rule__DataPage__Group_2__0__Impl4218); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2137:1: rule__DataPage__Group_2__1 : rule__DataPage__Group_2__1__Impl ;
    public final void rule__DataPage__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2141:1: ( rule__DataPage__Group_2__1__Impl )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2142:2: rule__DataPage__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__DataPage__Group_2__1__Impl_in_rule__DataPage__Group_2__14249);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2148:1: rule__DataPage__Group_2__1__Impl : ( ( rule__DataPage__EntityAssignment_2_1 ) ) ;
    public final void rule__DataPage__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2152:1: ( ( ( rule__DataPage__EntityAssignment_2_1 ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2153:1: ( ( rule__DataPage__EntityAssignment_2_1 ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2153:1: ( ( rule__DataPage__EntityAssignment_2_1 ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2154:1: ( rule__DataPage__EntityAssignment_2_1 )
            {
             before(grammarAccess.getDataPageAccess().getEntityAssignment_2_1()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2155:1: ( rule__DataPage__EntityAssignment_2_1 )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2155:2: rule__DataPage__EntityAssignment_2_1
            {
            pushFollow(FOLLOW_rule__DataPage__EntityAssignment_2_1_in_rule__DataPage__Group_2__1__Impl4276);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2170:1: rule__WebModel__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__WebModel__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2174:1: ( ( RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2175:1: ( RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2175:1: ( RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2176:1: RULE_ID
            {
             before(grammarAccess.getWebModelAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__WebModel__NameAssignment_14315); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2185:1: rule__WebModel__DataLayerAssignment_3 : ( ruleDataLayer ) ;
    public final void rule__WebModel__DataLayerAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2189:1: ( ( ruleDataLayer ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2190:1: ( ruleDataLayer )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2190:1: ( ruleDataLayer )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2191:1: ruleDataLayer
            {
             before(grammarAccess.getWebModelAccess().getDataLayerDataLayerParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleDataLayer_in_rule__WebModel__DataLayerAssignment_34346);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2200:1: rule__WebModel__HypertextLayerAssignment_4 : ( ruleHypertextLayer ) ;
    public final void rule__WebModel__HypertextLayerAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2204:1: ( ( ruleHypertextLayer ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2205:1: ( ruleHypertextLayer )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2205:1: ( ruleHypertextLayer )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2206:1: ruleHypertextLayer
            {
             before(grammarAccess.getWebModelAccess().getHypertextLayerHypertextLayerParserRuleCall_4_0()); 
            pushFollow(FOLLOW_ruleHypertextLayer_in_rule__WebModel__HypertextLayerAssignment_44377);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2215:1: rule__DataLayer__EntitiesAssignment_2 : ( ruleEntity ) ;
    public final void rule__DataLayer__EntitiesAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2219:1: ( ( ruleEntity ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2220:1: ( ruleEntity )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2220:1: ( ruleEntity )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2221:1: ruleEntity
            {
             before(grammarAccess.getDataLayerAccess().getEntitiesEntityParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleEntity_in_rule__DataLayer__EntitiesAssignment_24408);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2230:1: rule__Entity__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__Entity__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2234:1: ( ( RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2235:1: ( RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2235:1: ( RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2236:1: RULE_ID
            {
             before(grammarAccess.getEntityAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Entity__NameAssignment_14439); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2245:1: rule__Entity__AttributesAssignment_3 : ( ruleAttribute ) ;
    public final void rule__Entity__AttributesAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2249:1: ( ( ruleAttribute ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2250:1: ( ruleAttribute )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2250:1: ( ruleAttribute )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2251:1: ruleAttribute
            {
             before(grammarAccess.getEntityAccess().getAttributesAttributeParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleAttribute_in_rule__Entity__AttributesAssignment_34470);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2260:1: rule__Entity__ReferencesAssignment_4 : ( ruleReference ) ;
    public final void rule__Entity__ReferencesAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2264:1: ( ( ruleReference ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2265:1: ( ruleReference )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2265:1: ( ruleReference )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2266:1: ruleReference
            {
             before(grammarAccess.getEntityAccess().getReferencesReferenceParserRuleCall_4_0()); 
            pushFollow(FOLLOW_ruleReference_in_rule__Entity__ReferencesAssignment_44501);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2275:1: rule__Attribute__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__Attribute__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2279:1: ( ( RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2280:1: ( RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2280:1: ( RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2281:1: RULE_ID
            {
             before(grammarAccess.getAttributeAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Attribute__NameAssignment_14532); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2290:1: rule__Attribute__TypeAssignment_3 : ( ruleSimpleType ) ;
    public final void rule__Attribute__TypeAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2294:1: ( ( ruleSimpleType ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2295:1: ( ruleSimpleType )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2295:1: ( ruleSimpleType )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2296:1: ruleSimpleType
            {
             before(grammarAccess.getAttributeAccess().getTypeSimpleTypeEnumRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleSimpleType_in_rule__Attribute__TypeAssignment_34563);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2305:1: rule__Reference__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__Reference__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2309:1: ( ( RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2310:1: ( RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2310:1: ( RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2311:1: RULE_ID
            {
             before(grammarAccess.getReferenceAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Reference__NameAssignment_14594); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2320:1: rule__Reference__TypeAssignment_3 : ( ( RULE_ID ) ) ;
    public final void rule__Reference__TypeAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2324:1: ( ( ( RULE_ID ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2325:1: ( ( RULE_ID ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2325:1: ( ( RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2326:1: ( RULE_ID )
            {
             before(grammarAccess.getReferenceAccess().getTypeEntityCrossReference_3_0()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2327:1: ( RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2328:1: RULE_ID
            {
             before(grammarAccess.getReferenceAccess().getTypeEntityIDTerminalRuleCall_3_0_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Reference__TypeAssignment_34629); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2339:1: rule__HypertextLayer__PagesAssignment_1 : ( rulePage ) ;
    public final void rule__HypertextLayer__PagesAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2343:1: ( ( rulePage ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2344:1: ( rulePage )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2344:1: ( rulePage )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2345:1: rulePage
            {
             before(grammarAccess.getHypertextLayerAccess().getPagesPageParserRuleCall_1_0()); 
            pushFollow(FOLLOW_rulePage_in_rule__HypertextLayer__PagesAssignment_14664);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2354:1: rule__HypertextLayer__StartPageAssignment_3 : ( ( RULE_ID ) ) ;
    public final void rule__HypertextLayer__StartPageAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2358:1: ( ( ( RULE_ID ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2359:1: ( ( RULE_ID ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2359:1: ( ( RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2360:1: ( RULE_ID )
            {
             before(grammarAccess.getHypertextLayerAccess().getStartPageStaticPageCrossReference_3_0()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2361:1: ( RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2362:1: RULE_ID
            {
             before(grammarAccess.getHypertextLayerAccess().getStartPageStaticPageIDTerminalRuleCall_3_0_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__HypertextLayer__StartPageAssignment_34699); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2373:1: rule__StaticPage__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__StaticPage__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2377:1: ( ( RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2378:1: ( RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2378:1: ( RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2379:1: RULE_ID
            {
             before(grammarAccess.getStaticPageAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__StaticPage__NameAssignment_14734); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2388:1: rule__StaticPage__LinksAssignment_3 : ( ruleLink ) ;
    public final void rule__StaticPage__LinksAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2392:1: ( ( ruleLink ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2393:1: ( ruleLink )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2393:1: ( ruleLink )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2394:1: ruleLink
            {
             before(grammarAccess.getStaticPageAccess().getLinksLinkParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleLink_in_rule__StaticPage__LinksAssignment_34765);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2403:1: rule__Link__TargetAssignment_1 : ( ( RULE_ID ) ) ;
    public final void rule__Link__TargetAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2407:1: ( ( ( RULE_ID ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2408:1: ( ( RULE_ID ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2408:1: ( ( RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2409:1: ( RULE_ID )
            {
             before(grammarAccess.getLinkAccess().getTargetPageCrossReference_1_0()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2410:1: ( RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2411:1: RULE_ID
            {
             before(grammarAccess.getLinkAccess().getTargetPageIDTerminalRuleCall_1_0_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Link__TargetAssignment_14800); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2422:1: rule__IndexPage__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__IndexPage__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2426:1: ( ( RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2427:1: ( RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2427:1: ( RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2428:1: RULE_ID
            {
             before(grammarAccess.getIndexPageAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__IndexPage__NameAssignment_14835); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2437:1: rule__IndexPage__EntityAssignment_2_1 : ( ( RULE_ID ) ) ;
    public final void rule__IndexPage__EntityAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2441:1: ( ( ( RULE_ID ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2442:1: ( ( RULE_ID ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2442:1: ( ( RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2443:1: ( RULE_ID )
            {
             before(grammarAccess.getIndexPageAccess().getEntityEntityCrossReference_2_1_0()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2444:1: ( RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2445:1: RULE_ID
            {
             before(grammarAccess.getIndexPageAccess().getEntityEntityIDTerminalRuleCall_2_1_0_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__IndexPage__EntityAssignment_2_14870); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2456:1: rule__IndexPage__LinksAssignment_4 : ( ruleLink ) ;
    public final void rule__IndexPage__LinksAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2460:1: ( ( ruleLink ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2461:1: ( ruleLink )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2461:1: ( ruleLink )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2462:1: ruleLink
            {
             before(grammarAccess.getIndexPageAccess().getLinksLinkParserRuleCall_4_0()); 
            pushFollow(FOLLOW_ruleLink_in_rule__IndexPage__LinksAssignment_44905);
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2471:1: rule__DataPage__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__DataPage__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2475:1: ( ( RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2476:1: ( RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2476:1: ( RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2477:1: RULE_ID
            {
             before(grammarAccess.getDataPageAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__DataPage__NameAssignment_14936); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2486:1: rule__DataPage__EntityAssignment_2_1 : ( ( RULE_ID ) ) ;
    public final void rule__DataPage__EntityAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2490:1: ( ( ( RULE_ID ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2491:1: ( ( RULE_ID ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2491:1: ( ( RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2492:1: ( RULE_ID )
            {
             before(grammarAccess.getDataPageAccess().getEntityEntityCrossReference_2_1_0()); 
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2493:1: ( RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2494:1: RULE_ID
            {
             before(grammarAccess.getDataPageAccess().getEntityEntityIDTerminalRuleCall_2_1_0_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__DataPage__EntityAssignment_2_14971); 
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
    // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2505:1: rule__DataPage__LinksAssignment_4 : ( ruleLink ) ;
    public final void rule__DataPage__LinksAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2509:1: ( ( ruleLink ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2510:1: ( ruleLink )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2510:1: ( ruleLink )
            // ../org.eclipse.emf.refactor.examples.swml.xtext.ui/src-gen/org/eclipse/emf/refactor/examples/ui/contentassist/antlr/internal/InternalSimpleWebModelingLanguage.g:2511:1: ruleLink
            {
             before(grammarAccess.getDataPageAccess().getLinksLinkParserRuleCall_4_0()); 
            pushFollow(FOLLOW_ruleLink_in_rule__DataPage__LinksAssignment_45006);
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


 

    public static final BitSet FOLLOW_ruleWebModel_in_entryRuleWebModel61 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleWebModel68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__WebModel__Group__0_in_ruleWebModel94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDataLayer_in_entryRuleDataLayer121 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDataLayer128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataLayer__Group__0_in_ruleDataLayer154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEntity_in_entryRuleEntity181 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEntity188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Entity__Group__0_in_ruleEntity214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAttribute_in_entryRuleAttribute241 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAttribute248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Attribute__Group__0_in_ruleAttribute274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleReference_in_entryRuleReference301 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleReference308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Reference__Group__0_in_ruleReference334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleHypertextLayer_in_entryRuleHypertextLayer361 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleHypertextLayer368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__HypertextLayer__Group__0_in_ruleHypertextLayer394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePage_in_entryRulePage421 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePage428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Page__Alternatives_in_rulePage454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStaticPage_in_entryRuleStaticPage481 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleStaticPage488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__StaticPage__Group__0_in_ruleStaticPage514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLink_in_entryRuleLink541 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLink548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Link__Group__0_in_ruleLink574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDynamicPage_in_entryRuleDynamicPage601 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDynamicPage608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DynamicPage__Alternatives_in_ruleDynamicPage634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleIndexPage_in_entryRuleIndexPage661 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleIndexPage668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__IndexPage__Group__0_in_ruleIndexPage694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDataPage_in_entryRuleDataPage721 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDataPage728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataPage__Group__0_in_ruleDataPage754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__SimpleType__Alternatives_in_ruleSimpleType791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStaticPage_in_rule__Page__Alternatives826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDynamicPage_in_rule__Page__Alternatives843 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleIndexPage_in_rule__DynamicPage__Alternatives875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDataPage_in_rule__DynamicPage__Alternatives892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__SimpleType__Alternatives925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__SimpleType__Alternatives946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__SimpleType__Alternatives967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__SimpleType__Alternatives988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__SimpleType__Alternatives1009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__WebModel__Group__0__Impl_in_rule__WebModel__Group__01042 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__WebModel__Group__1_in_rule__WebModel__Group__01045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__WebModel__Group__0__Impl1073 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__WebModel__Group__1__Impl_in_rule__WebModel__Group__11104 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_rule__WebModel__Group__2_in_rule__WebModel__Group__11107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__WebModel__NameAssignment_1_in_rule__WebModel__Group__1__Impl1134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__WebModel__Group__2__Impl_in_rule__WebModel__Group__21164 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__WebModel__Group__3_in_rule__WebModel__Group__21167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__WebModel__Group__2__Impl1195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__WebModel__Group__3__Impl_in_rule__WebModel__Group__31226 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_rule__WebModel__Group__4_in_rule__WebModel__Group__31229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__WebModel__DataLayerAssignment_3_in_rule__WebModel__Group__3__Impl1256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__WebModel__Group__4__Impl_in_rule__WebModel__Group__41286 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_rule__WebModel__Group__5_in_rule__WebModel__Group__41289 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__WebModel__HypertextLayerAssignment_4_in_rule__WebModel__Group__4__Impl1316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__WebModel__Group__5__Impl_in_rule__WebModel__Group__51346 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__WebModel__Group__5__Impl1374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataLayer__Group__0__Impl_in_rule__DataLayer__Group__01417 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_rule__DataLayer__Group__1_in_rule__DataLayer__Group__01420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__DataLayer__Group__0__Impl1448 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataLayer__Group__1__Impl_in_rule__DataLayer__Group__11479 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_rule__DataLayer__Group__2_in_rule__DataLayer__Group__11482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataLayer__Group__2__Impl_in_rule__DataLayer__Group__21540 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_rule__DataLayer__Group__3_in_rule__DataLayer__Group__21543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataLayer__EntitiesAssignment_2_in_rule__DataLayer__Group__2__Impl1570 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_rule__DataLayer__Group__3__Impl_in_rule__DataLayer__Group__31601 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__DataLayer__Group__3__Impl1629 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Entity__Group__0__Impl_in_rule__Entity__Group__01668 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Entity__Group__1_in_rule__Entity__Group__01671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__Entity__Group__0__Impl1699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Entity__Group__1__Impl_in_rule__Entity__Group__11730 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_rule__Entity__Group__2_in_rule__Entity__Group__11733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Entity__NameAssignment_1_in_rule__Entity__Group__1__Impl1760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Entity__Group__2__Impl_in_rule__Entity__Group__21790 = new BitSet(new long[]{0x0000000000A40000L});
    public static final BitSet FOLLOW_rule__Entity__Group__3_in_rule__Entity__Group__21793 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__Entity__Group__2__Impl1821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Entity__Group__3__Impl_in_rule__Entity__Group__31852 = new BitSet(new long[]{0x0000000000A40000L});
    public static final BitSet FOLLOW_rule__Entity__Group__4_in_rule__Entity__Group__31855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Entity__AttributesAssignment_3_in_rule__Entity__Group__3__Impl1882 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_rule__Entity__Group__4__Impl_in_rule__Entity__Group__41913 = new BitSet(new long[]{0x0000000000A40000L});
    public static final BitSet FOLLOW_rule__Entity__Group__5_in_rule__Entity__Group__41916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Entity__ReferencesAssignment_4_in_rule__Entity__Group__4__Impl1943 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_rule__Entity__Group__5__Impl_in_rule__Entity__Group__51974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__Entity__Group__5__Impl2002 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Attribute__Group__0__Impl_in_rule__Attribute__Group__02045 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Attribute__Group__1_in_rule__Attribute__Group__02048 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__Attribute__Group__0__Impl2076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Attribute__Group__1__Impl_in_rule__Attribute__Group__12107 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_rule__Attribute__Group__2_in_rule__Attribute__Group__12110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Attribute__NameAssignment_1_in_rule__Attribute__Group__1__Impl2137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Attribute__Group__2__Impl_in_rule__Attribute__Group__22167 = new BitSet(new long[]{0x000000000000F800L});
    public static final BitSet FOLLOW_rule__Attribute__Group__3_in_rule__Attribute__Group__22170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__Attribute__Group__2__Impl2198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Attribute__Group__3__Impl_in_rule__Attribute__Group__32229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Attribute__TypeAssignment_3_in_rule__Attribute__Group__3__Impl2256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Reference__Group__0__Impl_in_rule__Reference__Group__02294 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Reference__Group__1_in_rule__Reference__Group__02297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__Reference__Group__0__Impl2325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Reference__Group__1__Impl_in_rule__Reference__Group__12356 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_rule__Reference__Group__2_in_rule__Reference__Group__12359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Reference__NameAssignment_1_in_rule__Reference__Group__1__Impl2386 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Reference__Group__2__Impl_in_rule__Reference__Group__22416 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Reference__Group__3_in_rule__Reference__Group__22419 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__Reference__Group__2__Impl2447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Reference__Group__3__Impl_in_rule__Reference__Group__32478 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Reference__TypeAssignment_3_in_rule__Reference__Group__3__Impl2505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__HypertextLayer__Group__0__Impl_in_rule__HypertextLayer__Group__02543 = new BitSet(new long[]{0x0000000054000000L});
    public static final BitSet FOLLOW_rule__HypertextLayer__Group__1_in_rule__HypertextLayer__Group__02546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__HypertextLayer__Group__0__Impl2574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__HypertextLayer__Group__1__Impl_in_rule__HypertextLayer__Group__12605 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__HypertextLayer__Group__2_in_rule__HypertextLayer__Group__12608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__HypertextLayer__PagesAssignment_1_in_rule__HypertextLayer__Group__1__Impl2637 = new BitSet(new long[]{0x0000000054000002L});
    public static final BitSet FOLLOW_rule__HypertextLayer__PagesAssignment_1_in_rule__HypertextLayer__Group__1__Impl2649 = new BitSet(new long[]{0x0000000054000002L});
    public static final BitSet FOLLOW_rule__HypertextLayer__Group__2__Impl_in_rule__HypertextLayer__Group__22682 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__HypertextLayer__Group__3_in_rule__HypertextLayer__Group__22685 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__HypertextLayer__Group__2__Impl2713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__HypertextLayer__Group__3__Impl_in_rule__HypertextLayer__Group__32744 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_rule__HypertextLayer__Group__4_in_rule__HypertextLayer__Group__32747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__HypertextLayer__StartPageAssignment_3_in_rule__HypertextLayer__Group__3__Impl2774 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__HypertextLayer__Group__4__Impl_in_rule__HypertextLayer__Group__42804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__HypertextLayer__Group__4__Impl2832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__StaticPage__Group__0__Impl_in_rule__StaticPage__Group__02873 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__StaticPage__Group__1_in_rule__StaticPage__Group__02876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__StaticPage__Group__0__Impl2904 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__StaticPage__Group__1__Impl_in_rule__StaticPage__Group__12935 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_rule__StaticPage__Group__2_in_rule__StaticPage__Group__12938 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__StaticPage__NameAssignment_1_in_rule__StaticPage__Group__1__Impl2965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__StaticPage__Group__2__Impl_in_rule__StaticPage__Group__22995 = new BitSet(new long[]{0x0000000008040000L});
    public static final BitSet FOLLOW_rule__StaticPage__Group__3_in_rule__StaticPage__Group__22998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__StaticPage__Group__2__Impl3026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__StaticPage__Group__3__Impl_in_rule__StaticPage__Group__33057 = new BitSet(new long[]{0x0000000008040000L});
    public static final BitSet FOLLOW_rule__StaticPage__Group__4_in_rule__StaticPage__Group__33060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__StaticPage__LinksAssignment_3_in_rule__StaticPage__Group__3__Impl3087 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_rule__StaticPage__Group__4__Impl_in_rule__StaticPage__Group__43118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__StaticPage__Group__4__Impl3146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Link__Group__0__Impl_in_rule__Link__Group__03187 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Link__Group__1_in_rule__Link__Group__03190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__Link__Group__0__Impl3218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Link__Group__1__Impl_in_rule__Link__Group__13249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Link__TargetAssignment_1_in_rule__Link__Group__1__Impl3276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__IndexPage__Group__0__Impl_in_rule__IndexPage__Group__03310 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__IndexPage__Group__1_in_rule__IndexPage__Group__03313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__IndexPage__Group__0__Impl3341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__IndexPage__Group__1__Impl_in_rule__IndexPage__Group__13372 = new BitSet(new long[]{0x0000000020020000L});
    public static final BitSet FOLLOW_rule__IndexPage__Group__2_in_rule__IndexPage__Group__13375 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__IndexPage__NameAssignment_1_in_rule__IndexPage__Group__1__Impl3402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__IndexPage__Group__2__Impl_in_rule__IndexPage__Group__23432 = new BitSet(new long[]{0x0000000020020000L});
    public static final BitSet FOLLOW_rule__IndexPage__Group__3_in_rule__IndexPage__Group__23435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__IndexPage__Group_2__0_in_rule__IndexPage__Group__2__Impl3462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__IndexPage__Group__3__Impl_in_rule__IndexPage__Group__33493 = new BitSet(new long[]{0x0000000008040000L});
    public static final BitSet FOLLOW_rule__IndexPage__Group__4_in_rule__IndexPage__Group__33496 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__IndexPage__Group__3__Impl3524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__IndexPage__Group__4__Impl_in_rule__IndexPage__Group__43555 = new BitSet(new long[]{0x0000000008040000L});
    public static final BitSet FOLLOW_rule__IndexPage__Group__5_in_rule__IndexPage__Group__43558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__IndexPage__LinksAssignment_4_in_rule__IndexPage__Group__4__Impl3585 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_rule__IndexPage__Group__5__Impl_in_rule__IndexPage__Group__53616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__IndexPage__Group__5__Impl3644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__IndexPage__Group_2__0__Impl_in_rule__IndexPage__Group_2__03687 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__IndexPage__Group_2__1_in_rule__IndexPage__Group_2__03690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__IndexPage__Group_2__0__Impl3718 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__IndexPage__Group_2__1__Impl_in_rule__IndexPage__Group_2__13749 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__IndexPage__EntityAssignment_2_1_in_rule__IndexPage__Group_2__1__Impl3776 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataPage__Group__0__Impl_in_rule__DataPage__Group__03810 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__DataPage__Group__1_in_rule__DataPage__Group__03813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__DataPage__Group__0__Impl3841 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataPage__Group__1__Impl_in_rule__DataPage__Group__13872 = new BitSet(new long[]{0x0000000020020000L});
    public static final BitSet FOLLOW_rule__DataPage__Group__2_in_rule__DataPage__Group__13875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataPage__NameAssignment_1_in_rule__DataPage__Group__1__Impl3902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataPage__Group__2__Impl_in_rule__DataPage__Group__23932 = new BitSet(new long[]{0x0000000020020000L});
    public static final BitSet FOLLOW_rule__DataPage__Group__3_in_rule__DataPage__Group__23935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataPage__Group_2__0_in_rule__DataPage__Group__2__Impl3962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataPage__Group__3__Impl_in_rule__DataPage__Group__33993 = new BitSet(new long[]{0x0000000008040000L});
    public static final BitSet FOLLOW_rule__DataPage__Group__4_in_rule__DataPage__Group__33996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__DataPage__Group__3__Impl4024 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataPage__Group__4__Impl_in_rule__DataPage__Group__44055 = new BitSet(new long[]{0x0000000008040000L});
    public static final BitSet FOLLOW_rule__DataPage__Group__5_in_rule__DataPage__Group__44058 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataPage__LinksAssignment_4_in_rule__DataPage__Group__4__Impl4085 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_rule__DataPage__Group__5__Impl_in_rule__DataPage__Group__54116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__DataPage__Group__5__Impl4144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataPage__Group_2__0__Impl_in_rule__DataPage__Group_2__04187 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__DataPage__Group_2__1_in_rule__DataPage__Group_2__04190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__DataPage__Group_2__0__Impl4218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataPage__Group_2__1__Impl_in_rule__DataPage__Group_2__14249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__DataPage__EntityAssignment_2_1_in_rule__DataPage__Group_2__1__Impl4276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__WebModel__NameAssignment_14315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDataLayer_in_rule__WebModel__DataLayerAssignment_34346 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleHypertextLayer_in_rule__WebModel__HypertextLayerAssignment_44377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEntity_in_rule__DataLayer__EntitiesAssignment_24408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Entity__NameAssignment_14439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAttribute_in_rule__Entity__AttributesAssignment_34470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleReference_in_rule__Entity__ReferencesAssignment_44501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Attribute__NameAssignment_14532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSimpleType_in_rule__Attribute__TypeAssignment_34563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Reference__NameAssignment_14594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Reference__TypeAssignment_34629 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePage_in_rule__HypertextLayer__PagesAssignment_14664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__HypertextLayer__StartPageAssignment_34699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__StaticPage__NameAssignment_14734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLink_in_rule__StaticPage__LinksAssignment_34765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Link__TargetAssignment_14800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__IndexPage__NameAssignment_14835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__IndexPage__EntityAssignment_2_14870 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLink_in_rule__IndexPage__LinksAssignment_44905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__DataPage__NameAssignment_14936 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__DataPage__EntityAssignment_2_14971 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLink_in_rule__DataPage__LinksAssignment_45006 = new BitSet(new long[]{0x0000000000000002L});

}