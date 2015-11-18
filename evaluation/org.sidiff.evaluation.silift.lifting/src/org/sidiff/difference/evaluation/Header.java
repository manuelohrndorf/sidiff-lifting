package org.sidiff.difference.evaluation;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author cpietsch
 *
 */
public class Header {

	private String name;
	
	private List<Header> subHeaders;

	public Header(String name) {
		this.name = name;
		this.subHeaders = new ArrayList<Header>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Header> getSubHeaders() {
		return subHeaders;
	}
	
	public Header getSubHeader(String name){
		for(Header subHeader : subHeaders){
			if(subHeader.getName().equals(name)){
				return subHeader;
			}
		}
		for(Header subHeader : subHeaders){
			Header subsubHeader = subHeader.getSubHeader(name);
			if(subsubHeader != null){
				return subsubHeader;
			}
		}
		return null;
	}
	
	
}
