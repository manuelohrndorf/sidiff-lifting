package org.eclipse.emf.refactor.examples.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.emf.refactor.examples.services.SimpleWebModelingLanguageGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalSimpleWebModelingLanguageParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'webmodel'", "'{'", "'}'", "'data {'", "'entity'", "'att'", "':'", "'ref'", "'hypertext {'", "'start page is'", "'static page'", "'link to page'", "'index page'", "'shows entity'", "'data page'", "'Boolean'", "'Email'", "'Float'", "'Integer'", "'String'"
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
    public String getGrammarFileName() { return "../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g"; }



     	private SimpleWebModelingLanguageGrammarAccess grammarAccess;
     	
        public InternalSimpleWebModelingLanguageParser(TokenStream input, SimpleWebModelingLanguageGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "WebModel";	
       	}
       	
       	@Override
       	protected SimpleWebModelingLanguageGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleWebModel"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:68:1: entryRuleWebModel returns [EObject current=null] : iv_ruleWebModel= ruleWebModel EOF ;
    public final EObject entryRuleWebModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWebModel = null;


        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:69:2: (iv_ruleWebModel= ruleWebModel EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:70:2: iv_ruleWebModel= ruleWebModel EOF
            {
             newCompositeNode(grammarAccess.getWebModelRule()); 
            pushFollow(FOLLOW_ruleWebModel_in_entryRuleWebModel75);
            iv_ruleWebModel=ruleWebModel();

            state._fsp--;

             current =iv_ruleWebModel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleWebModel85); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWebModel"


    // $ANTLR start "ruleWebModel"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:77:1: ruleWebModel returns [EObject current=null] : (otherlv_0= 'webmodel' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_dataLayer_3_0= ruleDataLayer ) ) ( (lv_hypertextLayer_4_0= ruleHypertextLayer ) ) otherlv_5= '}' ) ;
    public final EObject ruleWebModel() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject lv_dataLayer_3_0 = null;

        EObject lv_hypertextLayer_4_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:80:28: ( (otherlv_0= 'webmodel' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_dataLayer_3_0= ruleDataLayer ) ) ( (lv_hypertextLayer_4_0= ruleHypertextLayer ) ) otherlv_5= '}' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:81:1: (otherlv_0= 'webmodel' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_dataLayer_3_0= ruleDataLayer ) ) ( (lv_hypertextLayer_4_0= ruleHypertextLayer ) ) otherlv_5= '}' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:81:1: (otherlv_0= 'webmodel' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_dataLayer_3_0= ruleDataLayer ) ) ( (lv_hypertextLayer_4_0= ruleHypertextLayer ) ) otherlv_5= '}' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:81:3: otherlv_0= 'webmodel' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_dataLayer_3_0= ruleDataLayer ) ) ( (lv_hypertextLayer_4_0= ruleHypertextLayer ) ) otherlv_5= '}'
            {
            otherlv_0=(Token)match(input,11,FOLLOW_11_in_ruleWebModel122); 

                	newLeafNode(otherlv_0, grammarAccess.getWebModelAccess().getWebmodelKeyword_0());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:85:1: ( (lv_name_1_0= RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:86:1: (lv_name_1_0= RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:86:1: (lv_name_1_0= RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:87:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleWebModel139); 

            			newLeafNode(lv_name_1_0, grammarAccess.getWebModelAccess().getNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getWebModelRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ID");
            	    

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleWebModel156); 

                	newLeafNode(otherlv_2, grammarAccess.getWebModelAccess().getLeftCurlyBracketKeyword_2());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:107:1: ( (lv_dataLayer_3_0= ruleDataLayer ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:108:1: (lv_dataLayer_3_0= ruleDataLayer )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:108:1: (lv_dataLayer_3_0= ruleDataLayer )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:109:3: lv_dataLayer_3_0= ruleDataLayer
            {
             
            	        newCompositeNode(grammarAccess.getWebModelAccess().getDataLayerDataLayerParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleDataLayer_in_ruleWebModel177);
            lv_dataLayer_3_0=ruleDataLayer();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getWebModelRule());
            	        }
                   		set(
                   			current, 
                   			"dataLayer",
                    		lv_dataLayer_3_0, 
                    		"DataLayer");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:125:2: ( (lv_hypertextLayer_4_0= ruleHypertextLayer ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:126:1: (lv_hypertextLayer_4_0= ruleHypertextLayer )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:126:1: (lv_hypertextLayer_4_0= ruleHypertextLayer )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:127:3: lv_hypertextLayer_4_0= ruleHypertextLayer
            {
             
            	        newCompositeNode(grammarAccess.getWebModelAccess().getHypertextLayerHypertextLayerParserRuleCall_4_0()); 
            	    
            pushFollow(FOLLOW_ruleHypertextLayer_in_ruleWebModel198);
            lv_hypertextLayer_4_0=ruleHypertextLayer();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getWebModelRule());
            	        }
                   		set(
                   			current, 
                   			"hypertextLayer",
                    		lv_hypertextLayer_4_0, 
                    		"HypertextLayer");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_5=(Token)match(input,13,FOLLOW_13_in_ruleWebModel210); 

                	newLeafNode(otherlv_5, grammarAccess.getWebModelAccess().getRightCurlyBracketKeyword_5());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWebModel"


    // $ANTLR start "entryRuleDataLayer"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:155:1: entryRuleDataLayer returns [EObject current=null] : iv_ruleDataLayer= ruleDataLayer EOF ;
    public final EObject entryRuleDataLayer() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDataLayer = null;


        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:156:2: (iv_ruleDataLayer= ruleDataLayer EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:157:2: iv_ruleDataLayer= ruleDataLayer EOF
            {
             newCompositeNode(grammarAccess.getDataLayerRule()); 
            pushFollow(FOLLOW_ruleDataLayer_in_entryRuleDataLayer246);
            iv_ruleDataLayer=ruleDataLayer();

            state._fsp--;

             current =iv_ruleDataLayer; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDataLayer256); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDataLayer"


    // $ANTLR start "ruleDataLayer"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:164:1: ruleDataLayer returns [EObject current=null] : (otherlv_0= 'data {' () ( (lv_entities_2_0= ruleEntity ) )* otherlv_3= '}' ) ;
    public final EObject ruleDataLayer() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_3=null;
        EObject lv_entities_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:167:28: ( (otherlv_0= 'data {' () ( (lv_entities_2_0= ruleEntity ) )* otherlv_3= '}' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:168:1: (otherlv_0= 'data {' () ( (lv_entities_2_0= ruleEntity ) )* otherlv_3= '}' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:168:1: (otherlv_0= 'data {' () ( (lv_entities_2_0= ruleEntity ) )* otherlv_3= '}' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:168:3: otherlv_0= 'data {' () ( (lv_entities_2_0= ruleEntity ) )* otherlv_3= '}'
            {
            otherlv_0=(Token)match(input,14,FOLLOW_14_in_ruleDataLayer293); 

                	newLeafNode(otherlv_0, grammarAccess.getDataLayerAccess().getDataKeyword_0());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:172:1: ()
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:173:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getDataLayerAccess().getDataLayerAction_1(),
                        current);
                

            }

            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:178:2: ( (lv_entities_2_0= ruleEntity ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==15) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:179:1: (lv_entities_2_0= ruleEntity )
            	    {
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:179:1: (lv_entities_2_0= ruleEntity )
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:180:3: lv_entities_2_0= ruleEntity
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getDataLayerAccess().getEntitiesEntityParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleEntity_in_ruleDataLayer323);
            	    lv_entities_2_0=ruleEntity();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getDataLayerRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"entities",
            	            		lv_entities_2_0, 
            	            		"Entity");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            otherlv_3=(Token)match(input,13,FOLLOW_13_in_ruleDataLayer336); 

                	newLeafNode(otherlv_3, grammarAccess.getDataLayerAccess().getRightCurlyBracketKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDataLayer"


    // $ANTLR start "entryRuleEntity"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:208:1: entryRuleEntity returns [EObject current=null] : iv_ruleEntity= ruleEntity EOF ;
    public final EObject entryRuleEntity() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEntity = null;


        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:209:2: (iv_ruleEntity= ruleEntity EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:210:2: iv_ruleEntity= ruleEntity EOF
            {
             newCompositeNode(grammarAccess.getEntityRule()); 
            pushFollow(FOLLOW_ruleEntity_in_entryRuleEntity372);
            iv_ruleEntity=ruleEntity();

            state._fsp--;

             current =iv_ruleEntity; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEntity382); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEntity"


    // $ANTLR start "ruleEntity"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:217:1: ruleEntity returns [EObject current=null] : (otherlv_0= 'entity' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_attributes_3_0= ruleAttribute ) )* ( (lv_references_4_0= ruleReference ) )* otherlv_5= '}' ) ;
    public final EObject ruleEntity() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject lv_attributes_3_0 = null;

        EObject lv_references_4_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:220:28: ( (otherlv_0= 'entity' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_attributes_3_0= ruleAttribute ) )* ( (lv_references_4_0= ruleReference ) )* otherlv_5= '}' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:221:1: (otherlv_0= 'entity' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_attributes_3_0= ruleAttribute ) )* ( (lv_references_4_0= ruleReference ) )* otherlv_5= '}' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:221:1: (otherlv_0= 'entity' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_attributes_3_0= ruleAttribute ) )* ( (lv_references_4_0= ruleReference ) )* otherlv_5= '}' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:221:3: otherlv_0= 'entity' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_attributes_3_0= ruleAttribute ) )* ( (lv_references_4_0= ruleReference ) )* otherlv_5= '}'
            {
            otherlv_0=(Token)match(input,15,FOLLOW_15_in_ruleEntity419); 

                	newLeafNode(otherlv_0, grammarAccess.getEntityAccess().getEntityKeyword_0());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:225:1: ( (lv_name_1_0= RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:226:1: (lv_name_1_0= RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:226:1: (lv_name_1_0= RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:227:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleEntity436); 

            			newLeafNode(lv_name_1_0, grammarAccess.getEntityAccess().getNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getEntityRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ID");
            	    

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleEntity453); 

                	newLeafNode(otherlv_2, grammarAccess.getEntityAccess().getLeftCurlyBracketKeyword_2());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:247:1: ( (lv_attributes_3_0= ruleAttribute ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==16) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:248:1: (lv_attributes_3_0= ruleAttribute )
            	    {
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:248:1: (lv_attributes_3_0= ruleAttribute )
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:249:3: lv_attributes_3_0= ruleAttribute
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getEntityAccess().getAttributesAttributeParserRuleCall_3_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleAttribute_in_ruleEntity474);
            	    lv_attributes_3_0=ruleAttribute();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getEntityRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"attributes",
            	            		lv_attributes_3_0, 
            	            		"Attribute");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:265:3: ( (lv_references_4_0= ruleReference ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==18) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:266:1: (lv_references_4_0= ruleReference )
            	    {
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:266:1: (lv_references_4_0= ruleReference )
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:267:3: lv_references_4_0= ruleReference
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getEntityAccess().getReferencesReferenceParserRuleCall_4_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleReference_in_ruleEntity496);
            	    lv_references_4_0=ruleReference();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getEntityRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"references",
            	            		lv_references_4_0, 
            	            		"Reference");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            otherlv_5=(Token)match(input,13,FOLLOW_13_in_ruleEntity509); 

                	newLeafNode(otherlv_5, grammarAccess.getEntityAccess().getRightCurlyBracketKeyword_5());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEntity"


    // $ANTLR start "entryRuleAttribute"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:295:1: entryRuleAttribute returns [EObject current=null] : iv_ruleAttribute= ruleAttribute EOF ;
    public final EObject entryRuleAttribute() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAttribute = null;


        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:296:2: (iv_ruleAttribute= ruleAttribute EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:297:2: iv_ruleAttribute= ruleAttribute EOF
            {
             newCompositeNode(grammarAccess.getAttributeRule()); 
            pushFollow(FOLLOW_ruleAttribute_in_entryRuleAttribute545);
            iv_ruleAttribute=ruleAttribute();

            state._fsp--;

             current =iv_ruleAttribute; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAttribute555); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAttribute"


    // $ANTLR start "ruleAttribute"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:304:1: ruleAttribute returns [EObject current=null] : (otherlv_0= 'att' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_type_3_0= ruleSimpleType ) ) ) ;
    public final EObject ruleAttribute() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Enumerator lv_type_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:307:28: ( (otherlv_0= 'att' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_type_3_0= ruleSimpleType ) ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:308:1: (otherlv_0= 'att' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_type_3_0= ruleSimpleType ) ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:308:1: (otherlv_0= 'att' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_type_3_0= ruleSimpleType ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:308:3: otherlv_0= 'att' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_type_3_0= ruleSimpleType ) )
            {
            otherlv_0=(Token)match(input,16,FOLLOW_16_in_ruleAttribute592); 

                	newLeafNode(otherlv_0, grammarAccess.getAttributeAccess().getAttKeyword_0());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:312:1: ( (lv_name_1_0= RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:313:1: (lv_name_1_0= RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:313:1: (lv_name_1_0= RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:314:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleAttribute609); 

            			newLeafNode(lv_name_1_0, grammarAccess.getAttributeAccess().getNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getAttributeRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ID");
            	    

            }


            }

            otherlv_2=(Token)match(input,17,FOLLOW_17_in_ruleAttribute626); 

                	newLeafNode(otherlv_2, grammarAccess.getAttributeAccess().getColonKeyword_2());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:334:1: ( (lv_type_3_0= ruleSimpleType ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:335:1: (lv_type_3_0= ruleSimpleType )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:335:1: (lv_type_3_0= ruleSimpleType )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:336:3: lv_type_3_0= ruleSimpleType
            {
             
            	        newCompositeNode(grammarAccess.getAttributeAccess().getTypeSimpleTypeEnumRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleSimpleType_in_ruleAttribute647);
            lv_type_3_0=ruleSimpleType();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getAttributeRule());
            	        }
                   		set(
                   			current, 
                   			"type",
                    		lv_type_3_0, 
                    		"SimpleType");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAttribute"


    // $ANTLR start "entryRuleReference"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:360:1: entryRuleReference returns [EObject current=null] : iv_ruleReference= ruleReference EOF ;
    public final EObject entryRuleReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReference = null;


        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:361:2: (iv_ruleReference= ruleReference EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:362:2: iv_ruleReference= ruleReference EOF
            {
             newCompositeNode(grammarAccess.getReferenceRule()); 
            pushFollow(FOLLOW_ruleReference_in_entryRuleReference683);
            iv_ruleReference=ruleReference();

            state._fsp--;

             current =iv_ruleReference; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleReference693); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleReference"


    // $ANTLR start "ruleReference"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:369:1: ruleReference returns [EObject current=null] : (otherlv_0= 'ref' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ) ;
    public final EObject ruleReference() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;

         enterRule(); 
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:372:28: ( (otherlv_0= 'ref' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:373:1: (otherlv_0= 'ref' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:373:1: (otherlv_0= 'ref' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:373:3: otherlv_0= 'ref' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) )
            {
            otherlv_0=(Token)match(input,18,FOLLOW_18_in_ruleReference730); 

                	newLeafNode(otherlv_0, grammarAccess.getReferenceAccess().getRefKeyword_0());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:377:1: ( (lv_name_1_0= RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:378:1: (lv_name_1_0= RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:378:1: (lv_name_1_0= RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:379:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleReference747); 

            			newLeafNode(lv_name_1_0, grammarAccess.getReferenceAccess().getNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getReferenceRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ID");
            	    

            }


            }

            otherlv_2=(Token)match(input,17,FOLLOW_17_in_ruleReference764); 

                	newLeafNode(otherlv_2, grammarAccess.getReferenceAccess().getColonKeyword_2());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:399:1: ( (otherlv_3= RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:400:1: (otherlv_3= RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:400:1: (otherlv_3= RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:401:3: otherlv_3= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getReferenceRule());
            	        }
                    
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleReference784); 

            		newLeafNode(otherlv_3, grammarAccess.getReferenceAccess().getTypeEntityCrossReference_3_0()); 
            	

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleReference"


    // $ANTLR start "entryRuleHypertextLayer"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:420:1: entryRuleHypertextLayer returns [EObject current=null] : iv_ruleHypertextLayer= ruleHypertextLayer EOF ;
    public final EObject entryRuleHypertextLayer() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHypertextLayer = null;


        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:421:2: (iv_ruleHypertextLayer= ruleHypertextLayer EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:422:2: iv_ruleHypertextLayer= ruleHypertextLayer EOF
            {
             newCompositeNode(grammarAccess.getHypertextLayerRule()); 
            pushFollow(FOLLOW_ruleHypertextLayer_in_entryRuleHypertextLayer820);
            iv_ruleHypertextLayer=ruleHypertextLayer();

            state._fsp--;

             current =iv_ruleHypertextLayer; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleHypertextLayer830); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleHypertextLayer"


    // $ANTLR start "ruleHypertextLayer"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:429:1: ruleHypertextLayer returns [EObject current=null] : (otherlv_0= 'hypertext {' ( (lv_pages_1_0= rulePage ) )+ otherlv_2= 'start page is' ( (otherlv_3= RULE_ID ) ) otherlv_4= '}' ) ;
    public final EObject ruleHypertextLayer() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        EObject lv_pages_1_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:432:28: ( (otherlv_0= 'hypertext {' ( (lv_pages_1_0= rulePage ) )+ otherlv_2= 'start page is' ( (otherlv_3= RULE_ID ) ) otherlv_4= '}' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:433:1: (otherlv_0= 'hypertext {' ( (lv_pages_1_0= rulePage ) )+ otherlv_2= 'start page is' ( (otherlv_3= RULE_ID ) ) otherlv_4= '}' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:433:1: (otherlv_0= 'hypertext {' ( (lv_pages_1_0= rulePage ) )+ otherlv_2= 'start page is' ( (otherlv_3= RULE_ID ) ) otherlv_4= '}' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:433:3: otherlv_0= 'hypertext {' ( (lv_pages_1_0= rulePage ) )+ otherlv_2= 'start page is' ( (otherlv_3= RULE_ID ) ) otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,19,FOLLOW_19_in_ruleHypertextLayer867); 

                	newLeafNode(otherlv_0, grammarAccess.getHypertextLayerAccess().getHypertextKeyword_0());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:437:1: ( (lv_pages_1_0= rulePage ) )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==21||LA4_0==23||LA4_0==25) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:438:1: (lv_pages_1_0= rulePage )
            	    {
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:438:1: (lv_pages_1_0= rulePage )
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:439:3: lv_pages_1_0= rulePage
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getHypertextLayerAccess().getPagesPageParserRuleCall_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePage_in_ruleHypertextLayer888);
            	    lv_pages_1_0=rulePage();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getHypertextLayerRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"pages",
            	            		lv_pages_1_0, 
            	            		"Page");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);

            otherlv_2=(Token)match(input,20,FOLLOW_20_in_ruleHypertextLayer901); 

                	newLeafNode(otherlv_2, grammarAccess.getHypertextLayerAccess().getStartPageIsKeyword_2());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:459:1: ( (otherlv_3= RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:460:1: (otherlv_3= RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:460:1: (otherlv_3= RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:461:3: otherlv_3= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getHypertextLayerRule());
            	        }
                    
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleHypertextLayer921); 

            		newLeafNode(otherlv_3, grammarAccess.getHypertextLayerAccess().getStartPageStaticPageCrossReference_3_0()); 
            	

            }


            }

            otherlv_4=(Token)match(input,13,FOLLOW_13_in_ruleHypertextLayer933); 

                	newLeafNode(otherlv_4, grammarAccess.getHypertextLayerAccess().getRightCurlyBracketKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleHypertextLayer"


    // $ANTLR start "entryRulePage"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:484:1: entryRulePage returns [EObject current=null] : iv_rulePage= rulePage EOF ;
    public final EObject entryRulePage() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePage = null;


        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:485:2: (iv_rulePage= rulePage EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:486:2: iv_rulePage= rulePage EOF
            {
             newCompositeNode(grammarAccess.getPageRule()); 
            pushFollow(FOLLOW_rulePage_in_entryRulePage969);
            iv_rulePage=rulePage();

            state._fsp--;

             current =iv_rulePage; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePage979); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePage"


    // $ANTLR start "rulePage"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:493:1: rulePage returns [EObject current=null] : (this_StaticPage_0= ruleStaticPage | this_DynamicPage_1= ruleDynamicPage ) ;
    public final EObject rulePage() throws RecognitionException {
        EObject current = null;

        EObject this_StaticPage_0 = null;

        EObject this_DynamicPage_1 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:496:28: ( (this_StaticPage_0= ruleStaticPage | this_DynamicPage_1= ruleDynamicPage ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:497:1: (this_StaticPage_0= ruleStaticPage | this_DynamicPage_1= ruleDynamicPage )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:497:1: (this_StaticPage_0= ruleStaticPage | this_DynamicPage_1= ruleDynamicPage )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==21) ) {
                alt5=1;
            }
            else if ( (LA5_0==23||LA5_0==25) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:498:5: this_StaticPage_0= ruleStaticPage
                    {
                     
                            newCompositeNode(grammarAccess.getPageAccess().getStaticPageParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleStaticPage_in_rulePage1026);
                    this_StaticPage_0=ruleStaticPage();

                    state._fsp--;

                     
                            current = this_StaticPage_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:508:5: this_DynamicPage_1= ruleDynamicPage
                    {
                     
                            newCompositeNode(grammarAccess.getPageAccess().getDynamicPageParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleDynamicPage_in_rulePage1053);
                    this_DynamicPage_1=ruleDynamicPage();

                    state._fsp--;

                     
                            current = this_DynamicPage_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePage"


    // $ANTLR start "entryRuleStaticPage"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:524:1: entryRuleStaticPage returns [EObject current=null] : iv_ruleStaticPage= ruleStaticPage EOF ;
    public final EObject entryRuleStaticPage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStaticPage = null;


        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:525:2: (iv_ruleStaticPage= ruleStaticPage EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:526:2: iv_ruleStaticPage= ruleStaticPage EOF
            {
             newCompositeNode(grammarAccess.getStaticPageRule()); 
            pushFollow(FOLLOW_ruleStaticPage_in_entryRuleStaticPage1088);
            iv_ruleStaticPage=ruleStaticPage();

            state._fsp--;

             current =iv_ruleStaticPage; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleStaticPage1098); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStaticPage"


    // $ANTLR start "ruleStaticPage"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:533:1: ruleStaticPage returns [EObject current=null] : (otherlv_0= 'static page' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_links_3_0= ruleLink ) )* otherlv_4= '}' ) ;
    public final EObject ruleStaticPage() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_links_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:536:28: ( (otherlv_0= 'static page' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_links_3_0= ruleLink ) )* otherlv_4= '}' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:537:1: (otherlv_0= 'static page' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_links_3_0= ruleLink ) )* otherlv_4= '}' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:537:1: (otherlv_0= 'static page' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_links_3_0= ruleLink ) )* otherlv_4= '}' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:537:3: otherlv_0= 'static page' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_links_3_0= ruleLink ) )* otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,21,FOLLOW_21_in_ruleStaticPage1135); 

                	newLeafNode(otherlv_0, grammarAccess.getStaticPageAccess().getStaticPageKeyword_0());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:541:1: ( (lv_name_1_0= RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:542:1: (lv_name_1_0= RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:542:1: (lv_name_1_0= RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:543:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleStaticPage1152); 

            			newLeafNode(lv_name_1_0, grammarAccess.getStaticPageAccess().getNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getStaticPageRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ID");
            	    

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleStaticPage1169); 

                	newLeafNode(otherlv_2, grammarAccess.getStaticPageAccess().getLeftCurlyBracketKeyword_2());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:563:1: ( (lv_links_3_0= ruleLink ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==22) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:564:1: (lv_links_3_0= ruleLink )
            	    {
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:564:1: (lv_links_3_0= ruleLink )
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:565:3: lv_links_3_0= ruleLink
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getStaticPageAccess().getLinksLinkParserRuleCall_3_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLink_in_ruleStaticPage1190);
            	    lv_links_3_0=ruleLink();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getStaticPageRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"links",
            	            		lv_links_3_0, 
            	            		"Link");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            otherlv_4=(Token)match(input,13,FOLLOW_13_in_ruleStaticPage1203); 

                	newLeafNode(otherlv_4, grammarAccess.getStaticPageAccess().getRightCurlyBracketKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStaticPage"


    // $ANTLR start "entryRuleLink"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:593:1: entryRuleLink returns [EObject current=null] : iv_ruleLink= ruleLink EOF ;
    public final EObject entryRuleLink() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLink = null;


        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:594:2: (iv_ruleLink= ruleLink EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:595:2: iv_ruleLink= ruleLink EOF
            {
             newCompositeNode(grammarAccess.getLinkRule()); 
            pushFollow(FOLLOW_ruleLink_in_entryRuleLink1239);
            iv_ruleLink=ruleLink();

            state._fsp--;

             current =iv_ruleLink; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLink1249); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLink"


    // $ANTLR start "ruleLink"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:602:1: ruleLink returns [EObject current=null] : (otherlv_0= 'link to page' ( (otherlv_1= RULE_ID ) ) ) ;
    public final EObject ruleLink() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;

         enterRule(); 
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:605:28: ( (otherlv_0= 'link to page' ( (otherlv_1= RULE_ID ) ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:606:1: (otherlv_0= 'link to page' ( (otherlv_1= RULE_ID ) ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:606:1: (otherlv_0= 'link to page' ( (otherlv_1= RULE_ID ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:606:3: otherlv_0= 'link to page' ( (otherlv_1= RULE_ID ) )
            {
            otherlv_0=(Token)match(input,22,FOLLOW_22_in_ruleLink1286); 

                	newLeafNode(otherlv_0, grammarAccess.getLinkAccess().getLinkToPageKeyword_0());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:610:1: ( (otherlv_1= RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:611:1: (otherlv_1= RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:611:1: (otherlv_1= RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:612:3: otherlv_1= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getLinkRule());
            	        }
                    
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLink1306); 

            		newLeafNode(otherlv_1, grammarAccess.getLinkAccess().getTargetPageCrossReference_1_0()); 
            	

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLink"


    // $ANTLR start "entryRuleDynamicPage"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:631:1: entryRuleDynamicPage returns [EObject current=null] : iv_ruleDynamicPage= ruleDynamicPage EOF ;
    public final EObject entryRuleDynamicPage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDynamicPage = null;


        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:632:2: (iv_ruleDynamicPage= ruleDynamicPage EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:633:2: iv_ruleDynamicPage= ruleDynamicPage EOF
            {
             newCompositeNode(grammarAccess.getDynamicPageRule()); 
            pushFollow(FOLLOW_ruleDynamicPage_in_entryRuleDynamicPage1342);
            iv_ruleDynamicPage=ruleDynamicPage();

            state._fsp--;

             current =iv_ruleDynamicPage; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDynamicPage1352); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDynamicPage"


    // $ANTLR start "ruleDynamicPage"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:640:1: ruleDynamicPage returns [EObject current=null] : (this_IndexPage_0= ruleIndexPage | this_DataPage_1= ruleDataPage ) ;
    public final EObject ruleDynamicPage() throws RecognitionException {
        EObject current = null;

        EObject this_IndexPage_0 = null;

        EObject this_DataPage_1 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:643:28: ( (this_IndexPage_0= ruleIndexPage | this_DataPage_1= ruleDataPage ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:644:1: (this_IndexPage_0= ruleIndexPage | this_DataPage_1= ruleDataPage )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:644:1: (this_IndexPage_0= ruleIndexPage | this_DataPage_1= ruleDataPage )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==23) ) {
                alt7=1;
            }
            else if ( (LA7_0==25) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:645:5: this_IndexPage_0= ruleIndexPage
                    {
                     
                            newCompositeNode(grammarAccess.getDynamicPageAccess().getIndexPageParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleIndexPage_in_ruleDynamicPage1399);
                    this_IndexPage_0=ruleIndexPage();

                    state._fsp--;

                     
                            current = this_IndexPage_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:655:5: this_DataPage_1= ruleDataPage
                    {
                     
                            newCompositeNode(grammarAccess.getDynamicPageAccess().getDataPageParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleDataPage_in_ruleDynamicPage1426);
                    this_DataPage_1=ruleDataPage();

                    state._fsp--;

                     
                            current = this_DataPage_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDynamicPage"


    // $ANTLR start "entryRuleIndexPage"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:671:1: entryRuleIndexPage returns [EObject current=null] : iv_ruleIndexPage= ruleIndexPage EOF ;
    public final EObject entryRuleIndexPage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIndexPage = null;


        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:672:2: (iv_ruleIndexPage= ruleIndexPage EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:673:2: iv_ruleIndexPage= ruleIndexPage EOF
            {
             newCompositeNode(grammarAccess.getIndexPageRule()); 
            pushFollow(FOLLOW_ruleIndexPage_in_entryRuleIndexPage1461);
            iv_ruleIndexPage=ruleIndexPage();

            state._fsp--;

             current =iv_ruleIndexPage; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleIndexPage1471); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIndexPage"


    // $ANTLR start "ruleIndexPage"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:680:1: ruleIndexPage returns [EObject current=null] : (otherlv_0= 'index page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' ) ;
    public final EObject ruleIndexPage() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_links_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:683:28: ( (otherlv_0= 'index page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:684:1: (otherlv_0= 'index page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:684:1: (otherlv_0= 'index page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:684:3: otherlv_0= 'index page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,23,FOLLOW_23_in_ruleIndexPage1508); 

                	newLeafNode(otherlv_0, grammarAccess.getIndexPageAccess().getIndexPageKeyword_0());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:688:1: ( (lv_name_1_0= RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:689:1: (lv_name_1_0= RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:689:1: (lv_name_1_0= RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:690:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleIndexPage1525); 

            			newLeafNode(lv_name_1_0, grammarAccess.getIndexPageAccess().getNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getIndexPageRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ID");
            	    

            }


            }

            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:706:2: (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==24) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:706:4: otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,24,FOLLOW_24_in_ruleIndexPage1543); 

                        	newLeafNode(otherlv_2, grammarAccess.getIndexPageAccess().getShowsEntityKeyword_2_0());
                        
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:710:1: ( (otherlv_3= RULE_ID ) )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:711:1: (otherlv_3= RULE_ID )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:711:1: (otherlv_3= RULE_ID )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:712:3: otherlv_3= RULE_ID
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getIndexPageRule());
                    	        }
                            
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleIndexPage1563); 

                    		newLeafNode(otherlv_3, grammarAccess.getIndexPageAccess().getEntityEntityCrossReference_2_1_0()); 
                    	

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,12,FOLLOW_12_in_ruleIndexPage1577); 

                	newLeafNode(otherlv_4, grammarAccess.getIndexPageAccess().getLeftCurlyBracketKeyword_3());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:727:1: ( (lv_links_5_0= ruleLink ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==22) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:728:1: (lv_links_5_0= ruleLink )
            	    {
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:728:1: (lv_links_5_0= ruleLink )
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:729:3: lv_links_5_0= ruleLink
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getIndexPageAccess().getLinksLinkParserRuleCall_4_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLink_in_ruleIndexPage1598);
            	    lv_links_5_0=ruleLink();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getIndexPageRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"links",
            	            		lv_links_5_0, 
            	            		"Link");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            otherlv_6=(Token)match(input,13,FOLLOW_13_in_ruleIndexPage1611); 

                	newLeafNode(otherlv_6, grammarAccess.getIndexPageAccess().getRightCurlyBracketKeyword_5());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIndexPage"


    // $ANTLR start "entryRuleDataPage"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:757:1: entryRuleDataPage returns [EObject current=null] : iv_ruleDataPage= ruleDataPage EOF ;
    public final EObject entryRuleDataPage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDataPage = null;


        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:758:2: (iv_ruleDataPage= ruleDataPage EOF )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:759:2: iv_ruleDataPage= ruleDataPage EOF
            {
             newCompositeNode(grammarAccess.getDataPageRule()); 
            pushFollow(FOLLOW_ruleDataPage_in_entryRuleDataPage1647);
            iv_ruleDataPage=ruleDataPage();

            state._fsp--;

             current =iv_ruleDataPage; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDataPage1657); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDataPage"


    // $ANTLR start "ruleDataPage"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:766:1: ruleDataPage returns [EObject current=null] : (otherlv_0= 'data page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' ) ;
    public final EObject ruleDataPage() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_links_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:769:28: ( (otherlv_0= 'data page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:770:1: (otherlv_0= 'data page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:770:1: (otherlv_0= 'data page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:770:3: otherlv_0= 'data page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,25,FOLLOW_25_in_ruleDataPage1694); 

                	newLeafNode(otherlv_0, grammarAccess.getDataPageAccess().getDataPageKeyword_0());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:774:1: ( (lv_name_1_0= RULE_ID ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:775:1: (lv_name_1_0= RULE_ID )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:775:1: (lv_name_1_0= RULE_ID )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:776:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleDataPage1711); 

            			newLeafNode(lv_name_1_0, grammarAccess.getDataPageAccess().getNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getDataPageRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ID");
            	    

            }


            }

            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:792:2: (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==24) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:792:4: otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,24,FOLLOW_24_in_ruleDataPage1729); 

                        	newLeafNode(otherlv_2, grammarAccess.getDataPageAccess().getShowsEntityKeyword_2_0());
                        
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:796:1: ( (otherlv_3= RULE_ID ) )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:797:1: (otherlv_3= RULE_ID )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:797:1: (otherlv_3= RULE_ID )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:798:3: otherlv_3= RULE_ID
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getDataPageRule());
                    	        }
                            
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleDataPage1749); 

                    		newLeafNode(otherlv_3, grammarAccess.getDataPageAccess().getEntityEntityCrossReference_2_1_0()); 
                    	

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,12,FOLLOW_12_in_ruleDataPage1763); 

                	newLeafNode(otherlv_4, grammarAccess.getDataPageAccess().getLeftCurlyBracketKeyword_3());
                
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:813:1: ( (lv_links_5_0= ruleLink ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==22) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:814:1: (lv_links_5_0= ruleLink )
            	    {
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:814:1: (lv_links_5_0= ruleLink )
            	    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:815:3: lv_links_5_0= ruleLink
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getDataPageAccess().getLinksLinkParserRuleCall_4_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLink_in_ruleDataPage1784);
            	    lv_links_5_0=ruleLink();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getDataPageRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"links",
            	            		lv_links_5_0, 
            	            		"Link");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            otherlv_6=(Token)match(input,13,FOLLOW_13_in_ruleDataPage1797); 

                	newLeafNode(otherlv_6, grammarAccess.getDataPageAccess().getRightCurlyBracketKeyword_5());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDataPage"


    // $ANTLR start "ruleSimpleType"
    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:843:1: ruleSimpleType returns [Enumerator current=null] : ( (enumLiteral_0= 'Boolean' ) | (enumLiteral_1= 'Email' ) | (enumLiteral_2= 'Float' ) | (enumLiteral_3= 'Integer' ) | (enumLiteral_4= 'String' ) ) ;
    public final Enumerator ruleSimpleType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;

         enterRule(); 
        try {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:845:28: ( ( (enumLiteral_0= 'Boolean' ) | (enumLiteral_1= 'Email' ) | (enumLiteral_2= 'Float' ) | (enumLiteral_3= 'Integer' ) | (enumLiteral_4= 'String' ) ) )
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:846:1: ( (enumLiteral_0= 'Boolean' ) | (enumLiteral_1= 'Email' ) | (enumLiteral_2= 'Float' ) | (enumLiteral_3= 'Integer' ) | (enumLiteral_4= 'String' ) )
            {
            // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:846:1: ( (enumLiteral_0= 'Boolean' ) | (enumLiteral_1= 'Email' ) | (enumLiteral_2= 'Float' ) | (enumLiteral_3= 'Integer' ) | (enumLiteral_4= 'String' ) )
            int alt12=5;
            switch ( input.LA(1) ) {
            case 26:
                {
                alt12=1;
                }
                break;
            case 27:
                {
                alt12=2;
                }
                break;
            case 28:
                {
                alt12=3;
                }
                break;
            case 29:
                {
                alt12=4;
                }
                break;
            case 30:
                {
                alt12=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:846:2: (enumLiteral_0= 'Boolean' )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:846:2: (enumLiteral_0= 'Boolean' )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:846:4: enumLiteral_0= 'Boolean'
                    {
                    enumLiteral_0=(Token)match(input,26,FOLLOW_26_in_ruleSimpleType1847); 

                            current = grammarAccess.getSimpleTypeAccess().getBooleanEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getSimpleTypeAccess().getBooleanEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:852:6: (enumLiteral_1= 'Email' )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:852:6: (enumLiteral_1= 'Email' )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:852:8: enumLiteral_1= 'Email'
                    {
                    enumLiteral_1=(Token)match(input,27,FOLLOW_27_in_ruleSimpleType1864); 

                            current = grammarAccess.getSimpleTypeAccess().getEmailEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getSimpleTypeAccess().getEmailEnumLiteralDeclaration_1()); 
                        

                    }


                    }
                    break;
                case 3 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:858:6: (enumLiteral_2= 'Float' )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:858:6: (enumLiteral_2= 'Float' )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:858:8: enumLiteral_2= 'Float'
                    {
                    enumLiteral_2=(Token)match(input,28,FOLLOW_28_in_ruleSimpleType1881); 

                            current = grammarAccess.getSimpleTypeAccess().getFloatEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getSimpleTypeAccess().getFloatEnumLiteralDeclaration_2()); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:864:6: (enumLiteral_3= 'Integer' )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:864:6: (enumLiteral_3= 'Integer' )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:864:8: enumLiteral_3= 'Integer'
                    {
                    enumLiteral_3=(Token)match(input,29,FOLLOW_29_in_ruleSimpleType1898); 

                            current = grammarAccess.getSimpleTypeAccess().getIntegerEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_3, grammarAccess.getSimpleTypeAccess().getIntegerEnumLiteralDeclaration_3()); 
                        

                    }


                    }
                    break;
                case 5 :
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:870:6: (enumLiteral_4= 'String' )
                    {
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:870:6: (enumLiteral_4= 'String' )
                    // ../org.eclipse.emf.refactor.examples.swml.xtext/src-gen/org/eclipse/emf/refactor/examples/parser/antlr/internal/InternalSimpleWebModelingLanguage.g:870:8: enumLiteral_4= 'String'
                    {
                    enumLiteral_4=(Token)match(input,30,FOLLOW_30_in_ruleSimpleType1915); 

                            current = grammarAccess.getSimpleTypeAccess().getStringEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_4, grammarAccess.getSimpleTypeAccess().getStringEnumLiteralDeclaration_4()); 
                        

                    }


                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSimpleType"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleWebModel_in_entryRuleWebModel75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleWebModel85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_ruleWebModel122 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleWebModel139 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleWebModel156 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ruleDataLayer_in_ruleWebModel177 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ruleHypertextLayer_in_ruleWebModel198 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleWebModel210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDataLayer_in_entryRuleDataLayer246 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDataLayer256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_ruleDataLayer293 = new BitSet(new long[]{0x000000000000A000L});
    public static final BitSet FOLLOW_ruleEntity_in_ruleDataLayer323 = new BitSet(new long[]{0x000000000000A000L});
    public static final BitSet FOLLOW_13_in_ruleDataLayer336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEntity_in_entryRuleEntity372 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEntity382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_ruleEntity419 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleEntity436 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleEntity453 = new BitSet(new long[]{0x0000000000052000L});
    public static final BitSet FOLLOW_ruleAttribute_in_ruleEntity474 = new BitSet(new long[]{0x0000000000052000L});
    public static final BitSet FOLLOW_ruleReference_in_ruleEntity496 = new BitSet(new long[]{0x0000000000042000L});
    public static final BitSet FOLLOW_13_in_ruleEntity509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAttribute_in_entryRuleAttribute545 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAttribute555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleAttribute592 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleAttribute609 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ruleAttribute626 = new BitSet(new long[]{0x000000007C000000L});
    public static final BitSet FOLLOW_ruleSimpleType_in_ruleAttribute647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleReference_in_entryRuleReference683 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleReference693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_ruleReference730 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleReference747 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ruleReference764 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleReference784 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleHypertextLayer_in_entryRuleHypertextLayer820 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleHypertextLayer830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_ruleHypertextLayer867 = new BitSet(new long[]{0x0000000002A00000L});
    public static final BitSet FOLLOW_rulePage_in_ruleHypertextLayer888 = new BitSet(new long[]{0x0000000002B00000L});
    public static final BitSet FOLLOW_20_in_ruleHypertextLayer901 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleHypertextLayer921 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleHypertextLayer933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePage_in_entryRulePage969 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePage979 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStaticPage_in_rulePage1026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDynamicPage_in_rulePage1053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStaticPage_in_entryRuleStaticPage1088 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleStaticPage1098 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_ruleStaticPage1135 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleStaticPage1152 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleStaticPage1169 = new BitSet(new long[]{0x0000000000402000L});
    public static final BitSet FOLLOW_ruleLink_in_ruleStaticPage1190 = new BitSet(new long[]{0x0000000000402000L});
    public static final BitSet FOLLOW_13_in_ruleStaticPage1203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLink_in_entryRuleLink1239 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLink1249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_ruleLink1286 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLink1306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDynamicPage_in_entryRuleDynamicPage1342 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDynamicPage1352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleIndexPage_in_ruleDynamicPage1399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDataPage_in_ruleDynamicPage1426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleIndexPage_in_entryRuleIndexPage1461 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleIndexPage1471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleIndexPage1508 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleIndexPage1525 = new BitSet(new long[]{0x0000000001001000L});
    public static final BitSet FOLLOW_24_in_ruleIndexPage1543 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleIndexPage1563 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleIndexPage1577 = new BitSet(new long[]{0x0000000000402000L});
    public static final BitSet FOLLOW_ruleLink_in_ruleIndexPage1598 = new BitSet(new long[]{0x0000000000402000L});
    public static final BitSet FOLLOW_13_in_ruleIndexPage1611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDataPage_in_entryRuleDataPage1647 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDataPage1657 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_ruleDataPage1694 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleDataPage1711 = new BitSet(new long[]{0x0000000001001000L});
    public static final BitSet FOLLOW_24_in_ruleDataPage1729 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleDataPage1749 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleDataPage1763 = new BitSet(new long[]{0x0000000000402000L});
    public static final BitSet FOLLOW_ruleLink_in_ruleDataPage1784 = new BitSet(new long[]{0x0000000000402000L});
    public static final BitSet FOLLOW_13_in_ruleDataPage1797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleSimpleType1847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_ruleSimpleType1864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_ruleSimpleType1881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_ruleSimpleType1898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_ruleSimpleType1915 = new BitSet(new long[]{0x0000000000000002L});

}