package com.example.demo.service;

public interface Car {
	public void honk();
	
	public default int getFuelAmount() {
		return 40;
	} 
}
