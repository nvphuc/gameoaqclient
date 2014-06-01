package oaq.connector;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;

public class Connector {
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public boolean connect() {
		try {
			socket = new Socket("localhost", 9998);
			oos = new ObjectOutputStream(this.socket.getOutputStream());
			ois = new ObjectInputStream(this.socket.getInputStream());
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean disconnect() {
		try {
			oos.close();
			ois.close();
			socket.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean sendMessage(String message) {
		try {
			oos.writeObject(message);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean sendImage(String pathImage) {
		try {
			FileInputStream inFromHardDisk = new FileInputStream(pathImage);
			int size = inFromHardDisk.available();
			byte[] arrByteOfSentFile = new byte[size];
			inFromHardDisk.read(arrByteOfSentFile, 0, size);
			oos.writeObject(arrByteOfSentFile);
			inFromHardDisk.close();
			return true;
		} catch (IOException e) {
			return false;
		} 
	}

	public String receiveMessage() {
		String message = "";
		try {
			message = (String) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
		}
		return message;
	}
	
	public ImageIcon receiveImage(){		
		try {		
			ImageIcon image = (ImageIcon) ois.readObject();
			return image;
		} catch (IOException | ClassNotFoundException e) {
			return null;
		} 	
	}
	
	public InforTable receiveInforTable(){		
		try {		
			InforTable infor = (InforTable) ois.readObject();
			return infor;
		} catch (IOException | ClassNotFoundException e) {
			return null;
		} 	
	}
}
