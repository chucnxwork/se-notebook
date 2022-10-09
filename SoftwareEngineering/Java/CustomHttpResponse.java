/**
 * 
 */
package com.chucnx.socketcs.utils;

public class CustomHttpResponse {
	private byte[] body;
	private String contentType;
	public byte[] getBody() {
		return body;
	}
	public void setBody(byte[] body) {
		this.body = body;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
