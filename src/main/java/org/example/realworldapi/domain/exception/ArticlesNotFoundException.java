package org.example.realworldapi.domain.exception;

public class ArticlesNotFoundException extends BusinessException {

	public ArticlesNotFoundException() {
		super(5, "articles not found");
	}
}
