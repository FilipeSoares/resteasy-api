package br.com.app.restful.model;

import java.io.Serializable;

public class Address implements Serializable, ModelEntity {
	
	private Long id;
	
	private String street;
	
	private String city;
	
	private String state;
	
	private Long zip;

	@Override
	public Long getId() {
		return this.id;
	}

}
