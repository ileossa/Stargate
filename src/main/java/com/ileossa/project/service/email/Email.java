package com.ileossa.project.service.email;

import java.util.List;


/**
 * Created by v.lafosse on 27/07/2017.
 */
public class Email {

	private String from;
	private List<String> to;
	private List<String> cc;
	private String subject;
	private String message;
	private boolean isHtml;

	public Email() {
	}

	public Email(String from, List<String> to, List<String> cc, String subject, String message, Boolean isHtml) {
		this.from = from;
		this.to = to;
		this.cc = cc;
		this.subject = subject;
		this.message = message;
		this.isHtml = isHtml;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public List<String> getCc() {
		return cc;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isHtml() {
		return isHtml;
	}

	public void setHtml(boolean html) {
		isHtml = html;
	}
}
