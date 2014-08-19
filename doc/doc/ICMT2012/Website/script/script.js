		jQuery.noConflict();
		
		jQuery(document).ready(function(){
			jQuery("#TS").treeview({
				collapsed: true,
				animated: "fast",
				control:"#sidetreecontrol",
				persist: "location"
			});
			
			//***************************************************
			//********************** Units **********************
			//***************************************************

			var ajax_load = "<img src='images/load.gif' alt='loading...' />"; 
			
			jQuery("#mainUnit").click(function(){
	    			jQuery("#rule img").html(ajax_load).attr("src", "rules/mainUnit.png");
				jQuery("#description").text(mainUnit);
			});
			
			jQuery("#EnrichmentPhase").click(function(){
	    			jQuery("#rule img").html(ajax_load).attr("src", "rules/EnrichmentPhase.png");
				jQuery("#description").text(EnrichmentPhase);
			});
			
			jQuery("#MatchingPhase").click(function(){
	    			jQuery("#rule img").html(ajax_load).attr("src", "rules/MatchingPhase.png");
				jQuery("#description").text(MatchingPhase);
			});
			
			jQuery("#CreationPhase").click(function(){
	    			jQuery("#rule img").html(ajax_load).attr("src", "rules/CreationPhase.png");
				jQuery("#description").text(CreationPhase);
			});
			
			jQuery("#MirrorPhase").click(function(){
	    			jQuery("#rule img").html(ajax_load).attr("src", "rules/MirrorPhase.png");
				jQuery("#description").text(MirrorPhase);
			});
			
			jQuery("#GroupingPhase").click(function(){
	    			jQuery("#rule img").html(ajax_load).attr("src", "rules/GroupingPhase.png");
				jQuery("#description").text(GroupingPhase);
			});
			
			//***************************************************
			//********************** Rules **********************
			//***************************************************
	    	
	    		jQuery("#EmptyRule01").click(function(){
	    			jQuery("#rule img").html(ajax_load).attr("src", "rules/EmptyRule.png");
				jQuery("#description").text(EmptyRule);
			});
			
			jQuery("#EmptyRule02").click(function(){
	    			jQuery("#rule img").html(ajax_load).attr("src", "rules/EmptyRule.png");
				jQuery("#description").text(EmptyRule);
			});
			
			jQuery("#EmptyRule03").click(function(){
	    			jQuery("#rule img").html(ajax_load).attr("src", "rules/EmptyRule.png");
				jQuery("#description").text(EmptyRule);
			});
			
			jQuery("#EmptyRule04").click(function(){
	    			jQuery("#rule img").html(ajax_load).attr("src", "rules/EmptyRule.png");
				jQuery("#description").text(EmptyRule);
			});
			
			jQuery("#EmptyRule05").click(function(){
	    			jQuery("#rule img").html(ajax_load).attr("src", "rules/EmptyRule.png");
				jQuery("#description").text(EmptyRule);
			});
			
			jQuery("#EmptyRule06").click(function(){
	    			jQuery("#rule img").html(ajax_load).attr("src", "rules/EmptyRule.png");
				jQuery("#description").text(EmptyRule);
			});
			
			jQuery("#EmptyRule07").click(function(){
	    			jQuery("#rule img").html(ajax_load).attr("src", "rules/EmptyRule.png");
				jQuery("#description").text(EmptyRule);
			});
			
			jQuery("#EmptyRule08").click(function(){
	    			jQuery("#rule img").html(ajax_load).attr("src", "rules/EmptyRule.png");
				jQuery("#description").text(EmptyRule);
			});
	    	
	    		jQuery("#CreateImplicitEdge").click(function(){
	    			jQuery("#rule img").html(ajax_load).attr("src", "rules/CreateImplicitEdge.png");
				jQuery("#description").text(CreateImplicitEdge);
			});
			
			jQuery("#MapPreserveEdge").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/MapPreserveEdge.png");
				jQuery("#description").text(MapPreserveEdge);
	    		});
	    	
	    		jQuery("#MatchCorrespondencePattern").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/MatchCorrespondencePattern.png");
				jQuery("#description").text(MatchCorrespondencePattern);
	    		});
	    
	    		jQuery("#MatchAddObjectPattern").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/MatchAddObjectPattern.png");
				jQuery("#description").text(MatchAddObjectPattern);
	    		});
	    	
	    		jQuery("#MatchRemoveObjectPattern").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/MatchRemoveObjectPattern.png");
				jQuery("#description").text(MatchRemoveObjectPattern);
	    		});
	    	
	    		jQuery("#MatchPreservedReferencePattern").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/MatchPreservedReferencePattern.png");
				jQuery("#description").text(MatchPreservedReferencePattern);
	    		});
	    	
	    		jQuery("#MatchAddReferencePattern").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/MatchAddReferencePattern.png");
				jQuery("#description").text(MatchAddReferencePattern);
	    		});

	    		jQuery("#MatchRemoveReferencePattern").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/MatchRemoveReferencePattern.png");
				jQuery("#description").text(MatchRemoveReferencePattern);
	    		});
	    	
	    		jQuery("#MatchAttributeValueChangePattern").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/MatchAttributeValueChangePattern.png");
				jQuery("#description").text(MatchAttributeValueChangePattern);
	    		});
	    	
	    		jQuery("#RemoveDuplicatedEReferenceType").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/RemoveDuplicatedEReferenceType.png");
				jQuery("#description").text(RemoveDuplicatedEReferenceType);
	    		});
	    	
	    		jQuery("#RemoveDuplicatedEAttributeType").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/RemoveDuplicatedEAttributeType.png");
				jQuery("#description").text(RemoveDuplicatedEAttributeType);
	    		})
	    	
	    		jQuery("#CreateCorrespondencePattern").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/CreateCorrespondencePattern.png");
				jQuery("#description").text(CreateCorrespondencePattern);
	    		})

	    		jQuery("#CreateAddObjectPattern").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/CreateAddObjectPattern.png");
				jQuery("#description").text(CreateAddObjectPattern);
	    		})
	    	
	    		jQuery("#CreateRemoveObjectPattern").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/CreateRemoveObjectPattern.png");
				jQuery("#description").text(CreateRemoveObjectPattern);
	    		})

	    		jQuery("#CreatePreservedReferencePattern").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/CreatePreservedReferencePattern.png");
				jQuery("#description").text(CreatePreservedReferencePattern);
	    		})
	    	
	    		jQuery("#CreateAddReferencePattern").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/CreateAddReferencePattern.png");
				jQuery("#description").text(CreateAddReferencePattern);
	    		})
	    	
	    		jQuery("#CreateRemoveReferencePattern").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/CreateRemoveReferencePattern.png");
				jQuery("#description").text(CreateRemoveReferencePattern);
	    		})
	    	
	    		jQuery("#CreateAttributeValueChangePattern").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/CreateAttributeValueChangePattern.png");
				jQuery("#description").text(CreateAttributeValueChangePattern);
	    		})
	    	
	    		jQuery("#MirrorNodesLHStoRHS").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/MirrorNodesLHStoRHS.png");
				jQuery("#description").text(MirrorNodesLHStoRHS);
	    		})
	    	
	    		jQuery("#MirrorAttributeLHStoRHS").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/MirrorAttributeLHStoRHS.png");
				jQuery("#description").text(MirrorAttributeLHStoRHS);
	    		})
	    	
	    		jQuery("#MirrorEdgesLHStoRHS").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/MirrorEdgesLHStoRHS.png");
				jQuery("#description").text(MirrorEdgesLHStoRHS);
	    		})
	    	
	    		jQuery("#CreateChangeSet").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/CreateChangeSet.png");
				jQuery("#description").text(CreateChangeSet);
	    		})
	    	
	    		jQuery("#LinkChangesToChangeSet").click(function (){
				jQuery("#rule img").html(ajax_load).attr("src", "rules/LinkChangesToChangeSet.png");
				jQuery("#description").text(LinkChangesToChangeSet);
	    		})
		});