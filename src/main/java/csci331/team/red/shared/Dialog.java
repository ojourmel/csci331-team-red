package csci331.team.red.shared;

import java.util.ArrayList;

import csci331.team.red.clientEngine.DatabaseAgentScreen.DatabaseDialogCallbacks;

/**
 * Wrapper class for words said by the players and actors. <br>
 * FIXME: <b>Stub</b>
 * 
 * @author ojourmel , lduperron
 */
public class Dialog {
	public static final Dialog GENERIC = new Dialog("Ha Ha, that was funny" , "Diety");

	private String speaker;
	private String dialogue;
	private DatabaseDialogCallbacks.callbacks callbackCode;
	
	
	
	public Dialog(String words , String speaker) 
	{
		this(words, speaker, null);
	}
	
	public Dialog(String words , String speaker , DatabaseDialogCallbacks.callbacks c) 
	{
		this.dialogue = words;
		this.speaker = speaker;
		this.callbackCode = c;
	}
	
	public static Dialog[] returnDialogArray(String[][] strings)
	{
		ArrayList<Dialog> temp = new ArrayList<Dialog>();
		
		for(int i = 0; i < strings.length; i++)
		{
			Dialog tempDialog = new Dialog(strings[i][0] , strings [i][1]);
			temp.add(tempDialog);
			
			
		}

		return temp.toArray(new Dialog[0]);
		
	}
	
	public static Dialog[] returnDialogArray(String[][] strings , DatabaseDialogCallbacks.callbacks[] callbackArray)
	{
		ArrayList<Dialog> temp = new ArrayList<Dialog>();
		
		for(int i = 0; i < strings.length; i++)
		{
			Dialog tempDialog = new Dialog(strings[i][0] , strings [i][1],  callbackArray [i]);
			
			temp.add(tempDialog);
			
			
		}

		return temp.toArray(new Dialog[0]);
		
	}
	
	

	public String getDialogue() {
		return dialogue;
	}
	
	public String getSpeaker() {
		return speaker;
	}
	public DatabaseDialogCallbacks.callbacks getCallbackCode()
	{
		
		return callbackCode;
	}
}
