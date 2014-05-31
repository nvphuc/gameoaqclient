package oaq.connector;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;

public class Connector {
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public void connect() {
		try {
			socket = new Socket("localhost", 9998);
			oos = new ObjectOutputStream(this.socket.getOutputStream());
			ois = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
		}
	}

	public void disconnect() {
		try {
			oos.close();
			ois.close();
			socket.close();
		} catch (IOException e) {
		}
	}

	public void sendMessage(String message) {
		try {
			oos.writeObject(message);
		} catch (IOException e) {
		}
	}

	public void sendImage(ImageIcon image) {
		try {			
			oos.writeObject(image);
		} catch (IOException e) {
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
