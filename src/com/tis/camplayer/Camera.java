package com.tis.camplayer;

/**
 * Created by Asan on 06.03.2017.
 */

class Camera {
	private String name,protocol, login, password, ip_addr, port, stream_addr;

	Camera(){
		name = "";
		protocol = "rtsp";
		login = "";
		password = "";
		ip_addr = "";
		port = "554";
		stream_addr = "";
	}

	String getAddress() {
		return protocol + "://" + login + "@" + password + ":" + ip_addr + ":" + port + "@" + stream_addr;
	}

	void setAddress(String protocol, String login, String password, String ip_addr, String port, String stream_addr){
		this.protocol = protocol;
		this.login = login;
		this.password = password;
		this.ip_addr = ip_addr;
		this.port = port;
		this.stream_addr = stream_addr;
	}

	String getName() { return name; }

	void setName(String name) { this.name = name; }

	String getLogin() { return login; }

	String getPassword() { return password; }

	String getIP_addr() { return ip_addr; }

	String getStream_addr() { return stream_addr; }

	@Override
	public String toString() {
		return name;
	}
}
