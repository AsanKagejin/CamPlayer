package com.tis.camplayer;

import javax.swing.*;
import java.io.*;
import java.util.Vector;

/**
 * Created by Asan on 06.03.2017.
 */

class ProgramState implements Serializable {
	int cols, rows;
	Vector<Camera> cams;

	private ProgramState(){
		cols = 1;
		rows = 1;
		cams = new Vector<>();
	}

	static ProgramState loadState(){
		ProgramState temp = new ProgramState();
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("state.dat"));
			temp = (ProgramState) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return temp;
	}

	void saveState() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("state.dat"));
			oos.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void setCams(ListModel<Camera> cameras){
		cams.clear();
		for(int i = 0; i < cameras.getSize(); i++)
			cams.add(cameras.getElementAt(i));
	}

	void setSize(int cols, int rows){
		this.cols = cols;
		this.rows = rows;
	}
}
