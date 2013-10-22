package csci331.team.red.clientEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import csci331.team.red.clientEngine.DatabaseAgentScreen.DatabaseDialogCallbacks;
/**
 * @author Lduperron
 */

public class DatabaseDialgoueWindow extends TextButton {

	TextButton internalReference;
	TextButton Speaker;
	
	DatabaseDialgoueWindow nextWindow;
	
	DatabaseDialogCallbacks.callbacks dialogCallbackcode;
	
	public DatabaseDialgoueWindow(String dialogue, String thespeaker, TextButtonStyle style , Stage parentStage) {
		this(dialogue, thespeaker, style, parentStage,  false , 0 , true, null , null);
	}
	
	public DatabaseDialgoueWindow(String dialogue, String thespeaker, TextButtonStyle style , Stage parentStage , DatabaseDialgoueWindow nextWindow) {
		this(dialogue, thespeaker, style, parentStage,  false , 0 , true,  null , null);
	}
	
	public DatabaseDialgoueWindow(String dialogue, String thespeaker, TextButtonStyle style , Stage parentStage, boolean useWordWrap , int extraPadding , Boolean displayNow , DatabaseDialgoueWindow nextWindow , DatabaseDialogCallbacks.callbacks c) {
		this(dialogue, thespeaker, style , parentStage, useWordWrap , extraPadding , displayNow, nextWindow, c , Gdx.graphics.getHeight());
	}
	
	public DatabaseDialgoueWindow(String dialogue, String thespeaker, TextButtonStyle style , Stage parentStage, boolean useWordWrap , int extraPadding , Boolean displayNow , DatabaseDialgoueWindow nextWindow , DatabaseDialogCallbacks.callbacks c , float yPosition) {
		super(dialogue, style , useWordWrap , extraPadding);
		
		internalReference = this;
		
		if(nextWindow != null)
		{
			this.nextWindow = nextWindow;
			
		}
		if(c != null )
		{
			this.dialogCallbackcode = c;
			
		}
		
		this.setPosition(0 , yPosition - this.getHeight());
		
		Speaker = new TextButton(thespeaker, style , false , 0);
    	Speaker.setPosition(0, this.getY()-Speaker.getHeight() - 5);
    	
    	if(displayNow)
    	{
	    	parentStage.addActor(this);
	    	parentStage.addActor(Speaker);
    	}
		
    	this.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	DatabaseDialgoueWindow thisActor = (DatabaseDialgoueWindow)event.getListenerActor();
    	    	
    	    	if(thisActor.nextWindow != null)
    	    	{
    	    		thisActor.getStage().addActor(thisActor.nextWindow);
    	    		thisActor.getStage().addActor(thisActor.nextWindow.Speaker);
    	    	}
    	    	if(thisActor.dialogCallbackcode != null)
    	    	{
    	    		DatabaseDialogCallbacks.call(thisActor.dialogCallbackcode);
    	    	}
    	    	
    	    	
    	    	thisActor.Speaker.remove();
    	    	thisActor.remove();
    	    	
    	    };
    		
    	});
		
	}

}
