package com.example.demo.entity;

public class User implements Comparable<Object>  {
	int age;
	String name;
	
	public User(int age, String name){
		this.age = age;
		this.name = name;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public int compareTo(Object o) {
		User user = (User) o;
		return this.age - user.getAge();
	}
}
