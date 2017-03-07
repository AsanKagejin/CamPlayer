package com.tis.camplayer;

/**
 * Created by Asan on 06.03.2017.
 */

class Camera {
	private String addr, name;

	Camera(String name, String addr){
		this.name = name;
		this.addr = addr;
	}

	String getAddr() {
		return addr;
	}

	void setAddr(String addr) {
		this.addr = addr;
	}

	String getName() { return name; }

	void setName(String name) { this.name = name; }

	@Override
	public String toString() {
		return name;
	}
}
