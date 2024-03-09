import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JComboBox;

public class RechercheServeur implements Runnable
{
	private static int port = 11111;

	private String serv;
	private ArrayList<String> serverList;

	public RechercheServeur(String serv, ArrayList<String> serverList)
	{
		this.serv = serv;
		this.serverList = serverList;
	}

	@Override
	public void run()
	{
		try
		{
			Socket socket = new Socket(this.serv, port);
			this.serverList.add(this.serv);
			socket.close();
		} catch (Exception e) {}
	}
}
