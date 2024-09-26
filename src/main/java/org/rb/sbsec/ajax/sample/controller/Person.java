package org.rb.sbsec.ajax.sample.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {
	String name;
	Integer age;
	
	public Person(String name, Integer age) {
		this.name = name;
		this.age  = age;
	}
}
