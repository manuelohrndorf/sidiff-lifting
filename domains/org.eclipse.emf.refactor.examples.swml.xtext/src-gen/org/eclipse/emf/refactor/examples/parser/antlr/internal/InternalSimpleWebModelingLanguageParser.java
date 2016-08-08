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
    // InternalSimpleWebModelingLanguage.g:65:1: entryRuleWebModel returns [EObject current=null] : iv_ruleWebModel= ruleWebModel EOF ;
    public final EObject entryRuleWebModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWebModel = null;


        try {
            // InternalSimpleWebModelingLanguage.g:65:49: (iv_ruleWebModel= ruleWebModel EOF )
            // InternalSimpleWebModelingLanguage.g:66:2: iv_ruleWebModel= ruleWebModel EOF
            {
             newCompositeNode(grammarAccess.getWebModelRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleWebModel=ruleWebModel();

            state._fsp--;

             current =iv_ruleWebModel; 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:72:1: ruleWebModel returns [EObject current=null] : (otherlv_0= 'webmodel' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_dataLayer_3_0= ruleDataLayer ) ) ( (lv_hypertextLayer_4_0= ruleHypertextLayer ) ) otherlv_5= '}' ) ;
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
            // InternalSimpleWebModelingLanguage.g:78:2: ( (otherlv_0= 'webmodel' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_dataLayer_3_0= ruleDataLayer ) ) ( (lv_hypertextLayer_4_0= ruleHypertextLayer ) ) otherlv_5= '}' ) )
            // InternalSimpleWebModelingLanguage.g:79:2: (otherlv_0= 'webmodel' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_dataLayer_3_0= ruleDataLayer ) ) ( (lv_hypertextLayer_4_0= ruleHypertextLayer ) ) otherlv_5= '}' )
            {
            // InternalSimpleWebModelingLanguage.g:79:2: (otherlv_0= 'webmodel' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_dataLayer_3_0= ruleDataLayer ) ) ( (lv_hypertextLayer_4_0= ruleHypertextLayer ) ) otherlv_5= '}' )
            // InternalSimpleWebModelingLanguage.g:80:3: otherlv_0= 'webmodel' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_dataLayer_3_0= ruleDataLayer ) ) ( (lv_hypertextLayer_4_0= ruleHypertextLayer ) ) otherlv_5= '}'
            {
            otherlv_0=(Token)match(input,11,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getWebModelAccess().getWebmodelKeyword_0());
            		
            // InternalSimpleWebModelingLanguage.g:84:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:85:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:85:4: (lv_name_1_0= RULE_ID )
            // InternalSimpleWebModelingLanguage.g:86:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getWebModelAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getWebModelRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_5); 

            			newLeafNode(otherlv_2, grammarAccess.getWebModelAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalSimpleWebModelingLanguage.g:106:3: ( (lv_dataLayer_3_0= ruleDataLayer ) )
            // InternalSimpleWebModelingLanguage.g:107:4: (lv_dataLayer_3_0= ruleDataLayer )
            {
            // InternalSimpleWebModelingLanguage.g:107:4: (lv_dataLayer_3_0= ruleDataLayer )
            // InternalSimpleWebModelingLanguage.g:108:5: lv_dataLayer_3_0= ruleDataLayer
            {

            					newCompositeNode(grammarAccess.getWebModelAccess().getDataLayerDataLayerParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_6);
            lv_dataLayer_3_0=ruleDataLayer();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWebModelRule());
            					}
            					set(
            						current,
            						"dataLayer",
            						lv_dataLayer_3_0,
            						"org.eclipse.emf.refactor.examples.SimpleWebModelingLanguage.DataLayer");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSimpleWebModelingLanguage.g:125:3: ( (lv_hypertextLayer_4_0= ruleHypertextLayer ) )
            // InternalSimpleWebModelingLanguage.g:126:4: (lv_hypertextLayer_4_0= ruleHypertextLayer )
            {
            // InternalSimpleWebModelingLanguage.g:126:4: (lv_hypertextLayer_4_0= ruleHypertextLayer )
            // InternalSimpleWebModelingLanguage.g:127:5: lv_hypertextLayer_4_0= ruleHypertextLayer
            {

            					newCompositeNode(grammarAccess.getWebModelAccess().getHypertextLayerHypertextLayerParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_7);
            lv_hypertextLayer_4_0=ruleHypertextLayer();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWebModelRule());
            					}
            					set(
            						current,
            						"hypertextLayer",
            						lv_hypertextLayer_4_0,
            						"org.eclipse.emf.refactor.examples.SimpleWebModelingLanguage.HypertextLayer");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,13,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:152:1: entryRuleDataLayer returns [EObject current=null] : iv_ruleDataLayer= ruleDataLayer EOF ;
    public final EObject entryRuleDataLayer() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDataLayer = null;


        try {
            // InternalSimpleWebModelingLanguage.g:152:50: (iv_ruleDataLayer= ruleDataLayer EOF )
            // InternalSimpleWebModelingLanguage.g:153:2: iv_ruleDataLayer= ruleDataLayer EOF
            {
             newCompositeNode(grammarAccess.getDataLayerRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDataLayer=ruleDataLayer();

            state._fsp--;

             current =iv_ruleDataLayer; 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:159:1: ruleDataLayer returns [EObject current=null] : (otherlv_0= 'data {' () ( (lv_entities_2_0= ruleEntity ) )* otherlv_3= '}' ) ;
    public final EObject ruleDataLayer() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_3=null;
        EObject lv_entities_2_0 = null;



        	enterRule();

        try {
            // InternalSimpleWebModelingLanguage.g:165:2: ( (otherlv_0= 'data {' () ( (lv_entities_2_0= ruleEntity ) )* otherlv_3= '}' ) )
            // InternalSimpleWebModelingLanguage.g:166:2: (otherlv_0= 'data {' () ( (lv_entities_2_0= ruleEntity ) )* otherlv_3= '}' )
            {
            // InternalSimpleWebModelingLanguage.g:166:2: (otherlv_0= 'data {' () ( (lv_entities_2_0= ruleEntity ) )* otherlv_3= '}' )
            // InternalSimpleWebModelingLanguage.g:167:3: otherlv_0= 'data {' () ( (lv_entities_2_0= ruleEntity ) )* otherlv_3= '}'
            {
            otherlv_0=(Token)match(input,14,FOLLOW_8); 

            			newLeafNode(otherlv_0, grammarAccess.getDataLayerAccess().getDataKeyword_0());
            		
            // InternalSimpleWebModelingLanguage.g:171:3: ()
            // InternalSimpleWebModelingLanguage.g:172:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getDataLayerAccess().getDataLayerAction_1(),
            					current);
            			

            }

            // InternalSimpleWebModelingLanguage.g:178:3: ( (lv_entities_2_0= ruleEntity ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==15) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSimpleWebModelingLanguage.g:179:4: (lv_entities_2_0= ruleEntity )
            	    {
            	    // InternalSimpleWebModelingLanguage.g:179:4: (lv_entities_2_0= ruleEntity )
            	    // InternalSimpleWebModelingLanguage.g:180:5: lv_entities_2_0= ruleEntity
            	    {

            	    					newCompositeNode(grammarAccess.getDataLayerAccess().getEntitiesEntityParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_8);
            	    lv_entities_2_0=ruleEntity();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getDataLayerRule());
            	    					}
            	    					add(
            	    						current,
            	    						"entities",
            	    						lv_entities_2_0,
            	    						"org.eclipse.emf.refactor.examples.SimpleWebModelingLanguage.Entity");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            otherlv_3=(Token)match(input,13,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:205:1: entryRuleEntity returns [EObject current=null] : iv_ruleEntity= ruleEntity EOF ;
    public final EObject entryRuleEntity() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEntity = null;


        try {
            // InternalSimpleWebModelingLanguage.g:205:47: (iv_ruleEntity= ruleEntity EOF )
            // InternalSimpleWebModelingLanguage.g:206:2: iv_ruleEntity= ruleEntity EOF
            {
             newCompositeNode(grammarAccess.getEntityRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEntity=ruleEntity();

            state._fsp--;

             current =iv_ruleEntity; 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:212:1: ruleEntity returns [EObject current=null] : (otherlv_0= 'entity' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_attributes_3_0= ruleAttribute ) )* ( (lv_references_4_0= ruleReference ) )* otherlv_5= '}' ) ;
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
            // InternalSimpleWebModelingLanguage.g:218:2: ( (otherlv_0= 'entity' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_attributes_3_0= ruleAttribute ) )* ( (lv_references_4_0= ruleReference ) )* otherlv_5= '}' ) )
            // InternalSimpleWebModelingLanguage.g:219:2: (otherlv_0= 'entity' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_attributes_3_0= ruleAttribute ) )* ( (lv_references_4_0= ruleReference ) )* otherlv_5= '}' )
            {
            // InternalSimpleWebModelingLanguage.g:219:2: (otherlv_0= 'entity' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_attributes_3_0= ruleAttribute ) )* ( (lv_references_4_0= ruleReference ) )* otherlv_5= '}' )
            // InternalSimpleWebModelingLanguage.g:220:3: otherlv_0= 'entity' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_attributes_3_0= ruleAttribute ) )* ( (lv_references_4_0= ruleReference ) )* otherlv_5= '}'
            {
            otherlv_0=(Token)match(input,15,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getEntityAccess().getEntityKeyword_0());
            		
            // InternalSimpleWebModelingLanguage.g:224:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:225:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:225:4: (lv_name_1_0= RULE_ID )
            // InternalSimpleWebModelingLanguage.g:226:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getEntityAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEntityRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_9); 

            			newLeafNode(otherlv_2, grammarAccess.getEntityAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalSimpleWebModelingLanguage.g:246:3: ( (lv_attributes_3_0= ruleAttribute ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==16) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalSimpleWebModelingLanguage.g:247:4: (lv_attributes_3_0= ruleAttribute )
            	    {
            	    // InternalSimpleWebModelingLanguage.g:247:4: (lv_attributes_3_0= ruleAttribute )
            	    // InternalSimpleWebModelingLanguage.g:248:5: lv_attributes_3_0= ruleAttribute
            	    {

            	    					newCompositeNode(grammarAccess.getEntityAccess().getAttributesAttributeParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_9);
            	    lv_attributes_3_0=ruleAttribute();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getEntityRule());
            	    					}
            	    					add(
            	    						current,
            	    						"attributes",
            	    						lv_attributes_3_0,
            	    						"org.eclipse.emf.refactor.examples.SimpleWebModelingLanguage.Attribute");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // InternalSimpleWebModelingLanguage.g:265:3: ( (lv_references_4_0= ruleReference ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==18) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalSimpleWebModelingLanguage.g:266:4: (lv_references_4_0= ruleReference )
            	    {
            	    // InternalSimpleWebModelingLanguage.g:266:4: (lv_references_4_0= ruleReference )
            	    // InternalSimpleWebModelingLanguage.g:267:5: lv_references_4_0= ruleReference
            	    {

            	    					newCompositeNode(grammarAccess.getEntityAccess().getReferencesReferenceParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_10);
            	    lv_references_4_0=ruleReference();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getEntityRule());
            	    					}
            	    					add(
            	    						current,
            	    						"references",
            	    						lv_references_4_0,
            	    						"org.eclipse.emf.refactor.examples.SimpleWebModelingLanguage.Reference");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            otherlv_5=(Token)match(input,13,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:292:1: entryRuleAttribute returns [EObject current=null] : iv_ruleAttribute= ruleAttribute EOF ;
    public final EObject entryRuleAttribute() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAttribute = null;


        try {
            // InternalSimpleWebModelingLanguage.g:292:50: (iv_ruleAttribute= ruleAttribute EOF )
            // InternalSimpleWebModelingLanguage.g:293:2: iv_ruleAttribute= ruleAttribute EOF
            {
             newCompositeNode(grammarAccess.getAttributeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAttribute=ruleAttribute();

            state._fsp--;

             current =iv_ruleAttribute; 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:299:1: ruleAttribute returns [EObject current=null] : (otherlv_0= 'att' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_type_3_0= ruleSimpleType ) ) ) ;
    public final EObject ruleAttribute() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Enumerator lv_type_3_0 = null;



        	enterRule();

        try {
            // InternalSimpleWebModelingLanguage.g:305:2: ( (otherlv_0= 'att' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_type_3_0= ruleSimpleType ) ) ) )
            // InternalSimpleWebModelingLanguage.g:306:2: (otherlv_0= 'att' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_type_3_0= ruleSimpleType ) ) )
            {
            // InternalSimpleWebModelingLanguage.g:306:2: (otherlv_0= 'att' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_type_3_0= ruleSimpleType ) ) )
            // InternalSimpleWebModelingLanguage.g:307:3: otherlv_0= 'att' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_type_3_0= ruleSimpleType ) )
            {
            otherlv_0=(Token)match(input,16,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getAttributeAccess().getAttKeyword_0());
            		
            // InternalSimpleWebModelingLanguage.g:311:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:312:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:312:4: (lv_name_1_0= RULE_ID )
            // InternalSimpleWebModelingLanguage.g:313:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_11); 

            					newLeafNode(lv_name_1_0, grammarAccess.getAttributeAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getAttributeRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,17,FOLLOW_12); 

            			newLeafNode(otherlv_2, grammarAccess.getAttributeAccess().getColonKeyword_2());
            		
            // InternalSimpleWebModelingLanguage.g:333:3: ( (lv_type_3_0= ruleSimpleType ) )
            // InternalSimpleWebModelingLanguage.g:334:4: (lv_type_3_0= ruleSimpleType )
            {
            // InternalSimpleWebModelingLanguage.g:334:4: (lv_type_3_0= ruleSimpleType )
            // InternalSimpleWebModelingLanguage.g:335:5: lv_type_3_0= ruleSimpleType
            {

            					newCompositeNode(grammarAccess.getAttributeAccess().getTypeSimpleTypeEnumRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_type_3_0=ruleSimpleType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAttributeRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_3_0,
            						"org.eclipse.emf.refactor.examples.SimpleWebModelingLanguage.SimpleType");
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
    // InternalSimpleWebModelingLanguage.g:356:1: entryRuleReference returns [EObject current=null] : iv_ruleReference= ruleReference EOF ;
    public final EObject entryRuleReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReference = null;


        try {
            // InternalSimpleWebModelingLanguage.g:356:50: (iv_ruleReference= ruleReference EOF )
            // InternalSimpleWebModelingLanguage.g:357:2: iv_ruleReference= ruleReference EOF
            {
             newCompositeNode(grammarAccess.getReferenceRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleReference=ruleReference();

            state._fsp--;

             current =iv_ruleReference; 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:363:1: ruleReference returns [EObject current=null] : (otherlv_0= 'ref' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ) ;
    public final EObject ruleReference() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSimpleWebModelingLanguage.g:369:2: ( (otherlv_0= 'ref' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) ) )
            // InternalSimpleWebModelingLanguage.g:370:2: (otherlv_0= 'ref' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) )
            {
            // InternalSimpleWebModelingLanguage.g:370:2: (otherlv_0= 'ref' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) ) )
            // InternalSimpleWebModelingLanguage.g:371:3: otherlv_0= 'ref' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (otherlv_3= RULE_ID ) )
            {
            otherlv_0=(Token)match(input,18,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getReferenceAccess().getRefKeyword_0());
            		
            // InternalSimpleWebModelingLanguage.g:375:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:376:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:376:4: (lv_name_1_0= RULE_ID )
            // InternalSimpleWebModelingLanguage.g:377:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_11); 

            					newLeafNode(lv_name_1_0, grammarAccess.getReferenceAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getReferenceRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,17,FOLLOW_3); 

            			newLeafNode(otherlv_2, grammarAccess.getReferenceAccess().getColonKeyword_2());
            		
            // InternalSimpleWebModelingLanguage.g:397:3: ( (otherlv_3= RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:398:4: (otherlv_3= RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:398:4: (otherlv_3= RULE_ID )
            // InternalSimpleWebModelingLanguage.g:399:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getReferenceRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:414:1: entryRuleHypertextLayer returns [EObject current=null] : iv_ruleHypertextLayer= ruleHypertextLayer EOF ;
    public final EObject entryRuleHypertextLayer() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHypertextLayer = null;


        try {
            // InternalSimpleWebModelingLanguage.g:414:55: (iv_ruleHypertextLayer= ruleHypertextLayer EOF )
            // InternalSimpleWebModelingLanguage.g:415:2: iv_ruleHypertextLayer= ruleHypertextLayer EOF
            {
             newCompositeNode(grammarAccess.getHypertextLayerRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleHypertextLayer=ruleHypertextLayer();

            state._fsp--;

             current =iv_ruleHypertextLayer; 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:421:1: ruleHypertextLayer returns [EObject current=null] : (otherlv_0= 'hypertext {' ( (lv_pages_1_0= rulePage ) )+ otherlv_2= 'start page is' ( (otherlv_3= RULE_ID ) ) otherlv_4= '}' ) ;
    public final EObject ruleHypertextLayer() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        EObject lv_pages_1_0 = null;



        	enterRule();

        try {
            // InternalSimpleWebModelingLanguage.g:427:2: ( (otherlv_0= 'hypertext {' ( (lv_pages_1_0= rulePage ) )+ otherlv_2= 'start page is' ( (otherlv_3= RULE_ID ) ) otherlv_4= '}' ) )
            // InternalSimpleWebModelingLanguage.g:428:2: (otherlv_0= 'hypertext {' ( (lv_pages_1_0= rulePage ) )+ otherlv_2= 'start page is' ( (otherlv_3= RULE_ID ) ) otherlv_4= '}' )
            {
            // InternalSimpleWebModelingLanguage.g:428:2: (otherlv_0= 'hypertext {' ( (lv_pages_1_0= rulePage ) )+ otherlv_2= 'start page is' ( (otherlv_3= RULE_ID ) ) otherlv_4= '}' )
            // InternalSimpleWebModelingLanguage.g:429:3: otherlv_0= 'hypertext {' ( (lv_pages_1_0= rulePage ) )+ otherlv_2= 'start page is' ( (otherlv_3= RULE_ID ) ) otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,19,FOLLOW_13); 

            			newLeafNode(otherlv_0, grammarAccess.getHypertextLayerAccess().getHypertextKeyword_0());
            		
            // InternalSimpleWebModelingLanguage.g:433:3: ( (lv_pages_1_0= rulePage ) )+
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
            	    // InternalSimpleWebModelingLanguage.g:434:4: (lv_pages_1_0= rulePage )
            	    {
            	    // InternalSimpleWebModelingLanguage.g:434:4: (lv_pages_1_0= rulePage )
            	    // InternalSimpleWebModelingLanguage.g:435:5: lv_pages_1_0= rulePage
            	    {

            	    					newCompositeNode(grammarAccess.getHypertextLayerAccess().getPagesPageParserRuleCall_1_0());
            	    				
            	    pushFollow(FOLLOW_14);
            	    lv_pages_1_0=rulePage();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getHypertextLayerRule());
            	    					}
            	    					add(
            	    						current,
            	    						"pages",
            	    						lv_pages_1_0,
            	    						"org.eclipse.emf.refactor.examples.SimpleWebModelingLanguage.Page");
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

            otherlv_2=(Token)match(input,20,FOLLOW_3); 

            			newLeafNode(otherlv_2, grammarAccess.getHypertextLayerAccess().getStartPageIsKeyword_2());
            		
            // InternalSimpleWebModelingLanguage.g:456:3: ( (otherlv_3= RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:457:4: (otherlv_3= RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:457:4: (otherlv_3= RULE_ID )
            // InternalSimpleWebModelingLanguage.g:458:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getHypertextLayerRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_7); 

            					newLeafNode(otherlv_3, grammarAccess.getHypertextLayerAccess().getStartPageStaticPageCrossReference_3_0());
            				

            }


            }

            otherlv_4=(Token)match(input,13,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:477:1: entryRulePage returns [EObject current=null] : iv_rulePage= rulePage EOF ;
    public final EObject entryRulePage() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePage = null;


        try {
            // InternalSimpleWebModelingLanguage.g:477:45: (iv_rulePage= rulePage EOF )
            // InternalSimpleWebModelingLanguage.g:478:2: iv_rulePage= rulePage EOF
            {
             newCompositeNode(grammarAccess.getPageRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePage=rulePage();

            state._fsp--;

             current =iv_rulePage; 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:484:1: rulePage returns [EObject current=null] : (this_StaticPage_0= ruleStaticPage | this_DynamicPage_1= ruleDynamicPage ) ;
    public final EObject rulePage() throws RecognitionException {
        EObject current = null;

        EObject this_StaticPage_0 = null;

        EObject this_DynamicPage_1 = null;



        	enterRule();

        try {
            // InternalSimpleWebModelingLanguage.g:490:2: ( (this_StaticPage_0= ruleStaticPage | this_DynamicPage_1= ruleDynamicPage ) )
            // InternalSimpleWebModelingLanguage.g:491:2: (this_StaticPage_0= ruleStaticPage | this_DynamicPage_1= ruleDynamicPage )
            {
            // InternalSimpleWebModelingLanguage.g:491:2: (this_StaticPage_0= ruleStaticPage | this_DynamicPage_1= ruleDynamicPage )
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
                    // InternalSimpleWebModelingLanguage.g:492:3: this_StaticPage_0= ruleStaticPage
                    {

                    			newCompositeNode(grammarAccess.getPageAccess().getStaticPageParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_StaticPage_0=ruleStaticPage();

                    state._fsp--;


                    			current = this_StaticPage_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSimpleWebModelingLanguage.g:501:3: this_DynamicPage_1= ruleDynamicPage
                    {

                    			newCompositeNode(grammarAccess.getPageAccess().getDynamicPageParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
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
    // InternalSimpleWebModelingLanguage.g:513:1: entryRuleStaticPage returns [EObject current=null] : iv_ruleStaticPage= ruleStaticPage EOF ;
    public final EObject entryRuleStaticPage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStaticPage = null;


        try {
            // InternalSimpleWebModelingLanguage.g:513:51: (iv_ruleStaticPage= ruleStaticPage EOF )
            // InternalSimpleWebModelingLanguage.g:514:2: iv_ruleStaticPage= ruleStaticPage EOF
            {
             newCompositeNode(grammarAccess.getStaticPageRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStaticPage=ruleStaticPage();

            state._fsp--;

             current =iv_ruleStaticPage; 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:520:1: ruleStaticPage returns [EObject current=null] : (otherlv_0= 'static page' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_links_3_0= ruleLink ) )* otherlv_4= '}' ) ;
    public final EObject ruleStaticPage() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_links_3_0 = null;



        	enterRule();

        try {
            // InternalSimpleWebModelingLanguage.g:526:2: ( (otherlv_0= 'static page' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_links_3_0= ruleLink ) )* otherlv_4= '}' ) )
            // InternalSimpleWebModelingLanguage.g:527:2: (otherlv_0= 'static page' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_links_3_0= ruleLink ) )* otherlv_4= '}' )
            {
            // InternalSimpleWebModelingLanguage.g:527:2: (otherlv_0= 'static page' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_links_3_0= ruleLink ) )* otherlv_4= '}' )
            // InternalSimpleWebModelingLanguage.g:528:3: otherlv_0= 'static page' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_links_3_0= ruleLink ) )* otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,21,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getStaticPageAccess().getStaticPageKeyword_0());
            		
            // InternalSimpleWebModelingLanguage.g:532:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:533:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:533:4: (lv_name_1_0= RULE_ID )
            // InternalSimpleWebModelingLanguage.g:534:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getStaticPageAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStaticPageRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_15); 

            			newLeafNode(otherlv_2, grammarAccess.getStaticPageAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalSimpleWebModelingLanguage.g:554:3: ( (lv_links_3_0= ruleLink ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==22) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalSimpleWebModelingLanguage.g:555:4: (lv_links_3_0= ruleLink )
            	    {
            	    // InternalSimpleWebModelingLanguage.g:555:4: (lv_links_3_0= ruleLink )
            	    // InternalSimpleWebModelingLanguage.g:556:5: lv_links_3_0= ruleLink
            	    {

            	    					newCompositeNode(grammarAccess.getStaticPageAccess().getLinksLinkParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_links_3_0=ruleLink();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getStaticPageRule());
            	    					}
            	    					add(
            	    						current,
            	    						"links",
            	    						lv_links_3_0,
            	    						"org.eclipse.emf.refactor.examples.SimpleWebModelingLanguage.Link");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            otherlv_4=(Token)match(input,13,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:581:1: entryRuleLink returns [EObject current=null] : iv_ruleLink= ruleLink EOF ;
    public final EObject entryRuleLink() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLink = null;


        try {
            // InternalSimpleWebModelingLanguage.g:581:45: (iv_ruleLink= ruleLink EOF )
            // InternalSimpleWebModelingLanguage.g:582:2: iv_ruleLink= ruleLink EOF
            {
             newCompositeNode(grammarAccess.getLinkRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLink=ruleLink();

            state._fsp--;

             current =iv_ruleLink; 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:588:1: ruleLink returns [EObject current=null] : (otherlv_0= 'link to page' ( (otherlv_1= RULE_ID ) ) ) ;
    public final EObject ruleLink() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalSimpleWebModelingLanguage.g:594:2: ( (otherlv_0= 'link to page' ( (otherlv_1= RULE_ID ) ) ) )
            // InternalSimpleWebModelingLanguage.g:595:2: (otherlv_0= 'link to page' ( (otherlv_1= RULE_ID ) ) )
            {
            // InternalSimpleWebModelingLanguage.g:595:2: (otherlv_0= 'link to page' ( (otherlv_1= RULE_ID ) ) )
            // InternalSimpleWebModelingLanguage.g:596:3: otherlv_0= 'link to page' ( (otherlv_1= RULE_ID ) )
            {
            otherlv_0=(Token)match(input,22,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getLinkAccess().getLinkToPageKeyword_0());
            		
            // InternalSimpleWebModelingLanguage.g:600:3: ( (otherlv_1= RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:601:4: (otherlv_1= RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:601:4: (otherlv_1= RULE_ID )
            // InternalSimpleWebModelingLanguage.g:602:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLinkRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:617:1: entryRuleDynamicPage returns [EObject current=null] : iv_ruleDynamicPage= ruleDynamicPage EOF ;
    public final EObject entryRuleDynamicPage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDynamicPage = null;


        try {
            // InternalSimpleWebModelingLanguage.g:617:52: (iv_ruleDynamicPage= ruleDynamicPage EOF )
            // InternalSimpleWebModelingLanguage.g:618:2: iv_ruleDynamicPage= ruleDynamicPage EOF
            {
             newCompositeNode(grammarAccess.getDynamicPageRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDynamicPage=ruleDynamicPage();

            state._fsp--;

             current =iv_ruleDynamicPage; 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:624:1: ruleDynamicPage returns [EObject current=null] : (this_IndexPage_0= ruleIndexPage | this_DataPage_1= ruleDataPage ) ;
    public final EObject ruleDynamicPage() throws RecognitionException {
        EObject current = null;

        EObject this_IndexPage_0 = null;

        EObject this_DataPage_1 = null;



        	enterRule();

        try {
            // InternalSimpleWebModelingLanguage.g:630:2: ( (this_IndexPage_0= ruleIndexPage | this_DataPage_1= ruleDataPage ) )
            // InternalSimpleWebModelingLanguage.g:631:2: (this_IndexPage_0= ruleIndexPage | this_DataPage_1= ruleDataPage )
            {
            // InternalSimpleWebModelingLanguage.g:631:2: (this_IndexPage_0= ruleIndexPage | this_DataPage_1= ruleDataPage )
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
                    // InternalSimpleWebModelingLanguage.g:632:3: this_IndexPage_0= ruleIndexPage
                    {

                    			newCompositeNode(grammarAccess.getDynamicPageAccess().getIndexPageParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_IndexPage_0=ruleIndexPage();

                    state._fsp--;


                    			current = this_IndexPage_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSimpleWebModelingLanguage.g:641:3: this_DataPage_1= ruleDataPage
                    {

                    			newCompositeNode(grammarAccess.getDynamicPageAccess().getDataPageParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
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
    // InternalSimpleWebModelingLanguage.g:653:1: entryRuleIndexPage returns [EObject current=null] : iv_ruleIndexPage= ruleIndexPage EOF ;
    public final EObject entryRuleIndexPage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIndexPage = null;


        try {
            // InternalSimpleWebModelingLanguage.g:653:50: (iv_ruleIndexPage= ruleIndexPage EOF )
            // InternalSimpleWebModelingLanguage.g:654:2: iv_ruleIndexPage= ruleIndexPage EOF
            {
             newCompositeNode(grammarAccess.getIndexPageRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIndexPage=ruleIndexPage();

            state._fsp--;

             current =iv_ruleIndexPage; 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:660:1: ruleIndexPage returns [EObject current=null] : (otherlv_0= 'index page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' ) ;
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
            // InternalSimpleWebModelingLanguage.g:666:2: ( (otherlv_0= 'index page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' ) )
            // InternalSimpleWebModelingLanguage.g:667:2: (otherlv_0= 'index page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' )
            {
            // InternalSimpleWebModelingLanguage.g:667:2: (otherlv_0= 'index page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' )
            // InternalSimpleWebModelingLanguage.g:668:3: otherlv_0= 'index page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,23,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getIndexPageAccess().getIndexPageKeyword_0());
            		
            // InternalSimpleWebModelingLanguage.g:672:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:673:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:673:4: (lv_name_1_0= RULE_ID )
            // InternalSimpleWebModelingLanguage.g:674:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_16); 

            					newLeafNode(lv_name_1_0, grammarAccess.getIndexPageAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getIndexPageRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            // InternalSimpleWebModelingLanguage.g:690:3: (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==24) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSimpleWebModelingLanguage.g:691:4: otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,24,FOLLOW_3); 

                    				newLeafNode(otherlv_2, grammarAccess.getIndexPageAccess().getShowsEntityKeyword_2_0());
                    			
                    // InternalSimpleWebModelingLanguage.g:695:4: ( (otherlv_3= RULE_ID ) )
                    // InternalSimpleWebModelingLanguage.g:696:5: (otherlv_3= RULE_ID )
                    {
                    // InternalSimpleWebModelingLanguage.g:696:5: (otherlv_3= RULE_ID )
                    // InternalSimpleWebModelingLanguage.g:697:6: otherlv_3= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getIndexPageRule());
                    						}
                    					
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_4); 

                    						newLeafNode(otherlv_3, grammarAccess.getIndexPageAccess().getEntityEntityCrossReference_2_1_0());
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,12,FOLLOW_15); 

            			newLeafNode(otherlv_4, grammarAccess.getIndexPageAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalSimpleWebModelingLanguage.g:713:3: ( (lv_links_5_0= ruleLink ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==22) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalSimpleWebModelingLanguage.g:714:4: (lv_links_5_0= ruleLink )
            	    {
            	    // InternalSimpleWebModelingLanguage.g:714:4: (lv_links_5_0= ruleLink )
            	    // InternalSimpleWebModelingLanguage.g:715:5: lv_links_5_0= ruleLink
            	    {

            	    					newCompositeNode(grammarAccess.getIndexPageAccess().getLinksLinkParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_links_5_0=ruleLink();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getIndexPageRule());
            	    					}
            	    					add(
            	    						current,
            	    						"links",
            	    						lv_links_5_0,
            	    						"org.eclipse.emf.refactor.examples.SimpleWebModelingLanguage.Link");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            otherlv_6=(Token)match(input,13,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:740:1: entryRuleDataPage returns [EObject current=null] : iv_ruleDataPage= ruleDataPage EOF ;
    public final EObject entryRuleDataPage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDataPage = null;


        try {
            // InternalSimpleWebModelingLanguage.g:740:49: (iv_ruleDataPage= ruleDataPage EOF )
            // InternalSimpleWebModelingLanguage.g:741:2: iv_ruleDataPage= ruleDataPage EOF
            {
             newCompositeNode(grammarAccess.getDataPageRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDataPage=ruleDataPage();

            state._fsp--;

             current =iv_ruleDataPage; 
            match(input,EOF,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:747:1: ruleDataPage returns [EObject current=null] : (otherlv_0= 'data page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' ) ;
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
            // InternalSimpleWebModelingLanguage.g:753:2: ( (otherlv_0= 'data page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' ) )
            // InternalSimpleWebModelingLanguage.g:754:2: (otherlv_0= 'data page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' )
            {
            // InternalSimpleWebModelingLanguage.g:754:2: (otherlv_0= 'data page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}' )
            // InternalSimpleWebModelingLanguage.g:755:3: otherlv_0= 'data page' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )? otherlv_4= '{' ( (lv_links_5_0= ruleLink ) )* otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,25,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getDataPageAccess().getDataPageKeyword_0());
            		
            // InternalSimpleWebModelingLanguage.g:759:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalSimpleWebModelingLanguage.g:760:4: (lv_name_1_0= RULE_ID )
            {
            // InternalSimpleWebModelingLanguage.g:760:4: (lv_name_1_0= RULE_ID )
            // InternalSimpleWebModelingLanguage.g:761:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_16); 

            					newLeafNode(lv_name_1_0, grammarAccess.getDataPageAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDataPageRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            // InternalSimpleWebModelingLanguage.g:777:3: (otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==24) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalSimpleWebModelingLanguage.g:778:4: otherlv_2= 'shows entity' ( (otherlv_3= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,24,FOLLOW_3); 

                    				newLeafNode(otherlv_2, grammarAccess.getDataPageAccess().getShowsEntityKeyword_2_0());
                    			
                    // InternalSimpleWebModelingLanguage.g:782:4: ( (otherlv_3= RULE_ID ) )
                    // InternalSimpleWebModelingLanguage.g:783:5: (otherlv_3= RULE_ID )
                    {
                    // InternalSimpleWebModelingLanguage.g:783:5: (otherlv_3= RULE_ID )
                    // InternalSimpleWebModelingLanguage.g:784:6: otherlv_3= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDataPageRule());
                    						}
                    					
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_4); 

                    						newLeafNode(otherlv_3, grammarAccess.getDataPageAccess().getEntityEntityCrossReference_2_1_0());
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,12,FOLLOW_15); 

            			newLeafNode(otherlv_4, grammarAccess.getDataPageAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalSimpleWebModelingLanguage.g:800:3: ( (lv_links_5_0= ruleLink ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==22) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalSimpleWebModelingLanguage.g:801:4: (lv_links_5_0= ruleLink )
            	    {
            	    // InternalSimpleWebModelingLanguage.g:801:4: (lv_links_5_0= ruleLink )
            	    // InternalSimpleWebModelingLanguage.g:802:5: lv_links_5_0= ruleLink
            	    {

            	    					newCompositeNode(grammarAccess.getDataPageAccess().getLinksLinkParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_links_5_0=ruleLink();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getDataPageRule());
            	    					}
            	    					add(
            	    						current,
            	    						"links",
            	    						lv_links_5_0,
            	    						"org.eclipse.emf.refactor.examples.SimpleWebModelingLanguage.Link");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            otherlv_6=(Token)match(input,13,FOLLOW_2); 

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
    // InternalSimpleWebModelingLanguage.g:827:1: ruleSimpleType returns [Enumerator current=null] : ( (enumLiteral_0= 'Boolean' ) | (enumLiteral_1= 'Email' ) | (enumLiteral_2= 'Float' ) | (enumLiteral_3= 'Integer' ) | (enumLiteral_4= 'String' ) ) ;
    public final Enumerator ruleSimpleType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalSimpleWebModelingLanguage.g:833:2: ( ( (enumLiteral_0= 'Boolean' ) | (enumLiteral_1= 'Email' ) | (enumLiteral_2= 'Float' ) | (enumLiteral_3= 'Integer' ) | (enumLiteral_4= 'String' ) ) )
            // InternalSimpleWebModelingLanguage.g:834:2: ( (enumLiteral_0= 'Boolean' ) | (enumLiteral_1= 'Email' ) | (enumLiteral_2= 'Float' ) | (enumLiteral_3= 'Integer' ) | (enumLiteral_4= 'String' ) )
            {
            // InternalSimpleWebModelingLanguage.g:834:2: ( (enumLiteral_0= 'Boolean' ) | (enumLiteral_1= 'Email' ) | (enumLiteral_2= 'Float' ) | (enumLiteral_3= 'Integer' ) | (enumLiteral_4= 'String' ) )
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
                    // InternalSimpleWebModelingLanguage.g:835:3: (enumLiteral_0= 'Boolean' )
                    {
                    // InternalSimpleWebModelingLanguage.g:835:3: (enumLiteral_0= 'Boolean' )
                    // InternalSimpleWebModelingLanguage.g:836:4: enumLiteral_0= 'Boolean'
                    {
                    enumLiteral_0=(Token)match(input,26,FOLLOW_2); 

                    				current = grammarAccess.getSimpleTypeAccess().getBooleanEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getSimpleTypeAccess().getBooleanEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSimpleWebModelingLanguage.g:843:3: (enumLiteral_1= 'Email' )
                    {
                    // InternalSimpleWebModelingLanguage.g:843:3: (enumLiteral_1= 'Email' )
                    // InternalSimpleWebModelingLanguage.g:844:4: enumLiteral_1= 'Email'
                    {
                    enumLiteral_1=(Token)match(input,27,FOLLOW_2); 

                    				current = grammarAccess.getSimpleTypeAccess().getEmailEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getSimpleTypeAccess().getEmailEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalSimpleWebModelingLanguage.g:851:3: (enumLiteral_2= 'Float' )
                    {
                    // InternalSimpleWebModelingLanguage.g:851:3: (enumLiteral_2= 'Float' )
                    // InternalSimpleWebModelingLanguage.g:852:4: enumLiteral_2= 'Float'
                    {
                    enumLiteral_2=(Token)match(input,28,FOLLOW_2); 

                    				current = grammarAccess.getSimpleTypeAccess().getFloatEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getSimpleTypeAccess().getFloatEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalSimpleWebModelingLanguage.g:859:3: (enumLiteral_3= 'Integer' )
                    {
                    // InternalSimpleWebModelingLanguage.g:859:3: (enumLiteral_3= 'Integer' )
                    // InternalSimpleWebModelingLanguage.g:860:4: enumLiteral_3= 'Integer'
                    {
                    enumLiteral_3=(Token)match(input,29,FOLLOW_2); 

                    				current = grammarAccess.getSimpleTypeAccess().getIntegerEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getSimpleTypeAccess().getIntegerEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalSimpleWebModelingLanguage.g:867:3: (enumLiteral_4= 'String' )
                    {
                    // InternalSimpleWebModelingLanguage.g:867:3: (enumLiteral_4= 'String' )
                    // InternalSimpleWebModelingLanguage.g:868:4: enumLiteral_4= 'String'
                    {
                    enumLiteral_4=(Token)match(input,30,FOLLOW_2); 

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


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x000000000000A000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000052000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000042000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x000000007C000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000002A00000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000002B00000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000402000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000001001000L});

}