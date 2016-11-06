package com.mycompany.myapp.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Mails {
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
