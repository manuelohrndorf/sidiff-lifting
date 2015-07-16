rule WebModel2WebModel
	match left : Left!WebModel
	with right : Right!WebModel {
	
	compare {
		return true;
	}
}

rule DataLayer2DataLayer
	match left : Left!DataLayer
	with right : Right!DataLayer {
	
	compare {
		return true;
	}
}

rule Entity2Entity
	match left : Left!Entity
	with right : Right!Entity {
	
	compare {
		return left.name = right.name;
	}
}

rule Attribute2Attribute
	match left : Left!Attribute
	with right : Right!Attribute {
	
	compare {
		return left.name = right.name and
			left.eContainer.matches(right.eContainer);
	}
}

rule Reference2Reference
	match left : Left!Reference
	with right : Right!Reference {
	
	compare {
		return left.name = right.name and
			left.eContainer.matches(right.eContainer);
	}
}

rule HypertextLayer2HypertextLayer
	match left : Left!HypertextLayer
	with right : Right!HypertextLayer {
	
	compare {
		return true;
	}
}

rule Page2Page
	match left : Left!Page
	with right : Right!Page {
	
	compare {
		return left.name = right.name;
	}
}

rule DynamicPage2DynamicPage
	match left : Left!DynamicPage
	with right : Right!DynamicPage {
	
	compare {
		return left.name = right.name;
	}
}

rule IndexPage2IndexPage
	match left : Left!IndexPage
	with right : Right!IndexPage {
	
	compare {
		return left.name = right.name;
	}
}

rule DataPage2DataPage
	match left : Left!DataPage
	with right : Right!DataPage {
	
	compare {
		return left.name = right.name;
	}
}

rule StaticPage2StaticPage
	match left : Left!StaticPage
	with right : Right!StaticPage {
	
	compare {
		return left.name = right.name;
	}
}

rule Link2Link
	match left : Left!Link
	with right : Right!Link {
	
	compare {
		return left.srcMatches(right) and left.tgtMatches(right);
	}
}

operation Link srcMatches(other : Link) : Boolean {
	return self.eContainer.name = other.eContainer.name;
}

operation Link tgtMatches(other : Link) : Boolean {
	return self.target.name = other.target.name;
}

