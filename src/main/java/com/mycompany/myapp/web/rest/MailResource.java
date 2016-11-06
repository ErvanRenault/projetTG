package com.mycompany.myapp.web.rest;

import java.net.URISyntaxException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Mails;
import com.mycompany.myapp.service.MailService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class MailResource {

	private final Logger log = LoggerFactory.getLogger(UserResource.class);

	@Inject
	private MailService mailService;

	@CrossOrigin	
	@PostMapping("/mail")
	@Timed
	public void sendMail(@RequestBody Mails mails) throws URISyntaxException {
      System.out.println("bla");
      String[] listMails = mails.getMails();
      String object = mails.getObject();
      String content = mails.getContent();
      for (int i = 0; i < listMails.length; i++) {
    	  mailService.sendEmail(listMails[i], object, content, false, false);
      }
	}
     

}