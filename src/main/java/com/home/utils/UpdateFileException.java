package com.home.utils;

public class UpdateFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UpdateFileException(Throwable e){
		super("An Error occured when trying to update the files" + e.getMessage());
	}
	
	public UpdateFileException(String text){
		super(text);
	}
}
