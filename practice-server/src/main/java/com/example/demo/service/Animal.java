package com.example.demo.service;

public abstract class Animal {
	protected abstract void run(String animal);
	protected void eat(String animal) {
		System.out.println(String.format("%s eat food", animal));
	}
}
