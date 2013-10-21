package csci331.team.red.core;

public class Main {
	public static void main(String[] args) {
		ServerEngine server = new ServerEngine();

		server.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		server.onPlayerDisconnect();
		
		
	}
}
