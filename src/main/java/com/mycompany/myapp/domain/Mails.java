package com.mycompany.myapp.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Mails {
	//@XmlElement List<String> mails;
	@XmlElement String[] mails;
	@XmlElement String object;
	@XmlElement String content;

	public String getObject() {
		return object;
	}
	
	public String getContent() {
		return content;
	}
	
	public String[] getMails() {
		return mails;
	}
}
