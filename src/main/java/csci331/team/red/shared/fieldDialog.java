package csci331.team.red.shared;

import java.util.ArrayList;

import csci331.team.red.clientEngine.DatabaseAgentScreen.DatabaseDialogCallbacks;
import csci331.team.red.clientEngine.FieldAgentScreen.fieldDialogCallbacks;

/**
 * Wrapper class for words said by the players and actors. <br>
 * FIXME: <b>Stub</b>
 * 
 * @author ojourmel , lduperron
 */
public class fieldDialog {
	public static final fieldDialog GENERIC = new fieldDialog("Ha Ha, that was funny" , "Diety");

	private String speaker;
	private String dialogue;
	private fieldDialogCallbacks.callbacks callbackCode;
	
	
	
	public fieldDialog(String words , String speaker) 
	{
		this(words, speaker, null);
	}
	
	public fieldDialog(String words , String speaker , fieldDialogCallbacks.callbacks c) 
	{
		this.dialogue = words;
		this.speaker = speaker;
		this.callbackCode = c;
	}
	
	public static fieldDialog[] returnDialogArray(String[][] strings)
	{
		ArrayList<fieldDialog> temp = new ArrayList<fieldDialog>();
		
		for(int i = 0; i < strings.length; i++)
		{
			fieldDialog tempDialog = new fieldDialog(strings[i][0] , strings [i][1]);
			temp.add(tempDialog);
			
			
		}

		return temp.toArray(new fieldDialog[0]);
		
	}
	
	public static fieldDialog[] returnDialogArray(String[][] strings , fieldDialogCallbacks.callbacks[] callbackArray)
	{
		ArrayList<fieldDialog> temp = new ArrayList<fieldDialog>();
		
		for(int i = 0; i < strings.length; i++)
		{
			fieldDialog tempDialog = new fieldDialog(strings[i][0] , strings [i][1],  callbackArray [i]);
			
			temp.add(tempDialog);
			
			
		}

		return temp.toArray(new fieldDialog[0]);
		
	}
	
	

	public String getDialogue() {
		return dialogue;
	}
	
	public String getSpeaker() {
		return speaker;
	}
	public fieldDialogCallbacks.callbacks getCallbackCode()
	{
		
		return callbackCode;
	}
}
