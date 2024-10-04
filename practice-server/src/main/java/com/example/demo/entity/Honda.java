package com.example.demo.entity;

import com.example.demo.service.Car;

public class Honda implements Car {

	@Override
	public void honk() {
		// TODO Auto-generated method stub
		this.getFuelAmount();
	}
	
	@Override
	public int getFuelAmount() {
		return 10;
	}

}
