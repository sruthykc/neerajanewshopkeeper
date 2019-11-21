package com.diviso.graeshoppe.service.dto;

public class PdfDTO {
	private String contentType;
	private byte[] pdf;
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getPdf() {
		return pdf;
	}
	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}

}
