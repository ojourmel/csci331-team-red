package csci331.team.red.clientEngine;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
/**
 * @author Lduperron
 */

public class DialogueWindow extends TextButton {

	TextButton internalReference;
	TextButton Speaker;
	
	DialogueWindow nextWindow;
	
	Enum<?> Callbackcode;
	
	public DialogueWindow(String dialogue, String thespeaker, TextButtonStyle style , Stage parentStage) {
		this(dialogue, thespeaker, style, parentStage,  false , 0 , true, null , null);
	}
	
	public DialogueWindow(String dialogue, String thespeaker, TextButtonStyle style , Stage parentStage , DialogueWindow nextWindow) {
		this(dialogue, thespeaker, style, parentStage,  false , 0 , true,  null , null);
	}
	
	public DialogueWindow(String dialogue, String thespeaker, TextButtonStyle style , Stage parentStage, boolean useWordWrap , int extraPadding , Boolean displayNow , DialogueWindow nextWindow , Enum<?> c) {
		this(dialogue, thespeaker, style , parentStage, useWordWrap , extraPadding , displayNow, nextWindow, c , Gdx.graphics.getHeight());
	}
	
	public DialogueWindow(String dialogue, String thespeaker, TextButtonStyle style , Stage parentStage, boolean useWordWrap , int extraPadding , Boolean displayNow , DialogueWindow nextWindow , Enum<?> c , float yPosition) {
		super(dialogue, style , useWordWrap , extraPadding);
		
		internalReference = this;
		
		if(nextWindow != null)
		{
			this.nextWindow = nextWindow;
			
		}
		if(c != null )
		{
			this.Callbackcode = c;
			
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
    	    	DialogueWindow thisActor = (DialogueWindow)event.getListenerActor();
    	    	
    	    	if(thisActor.nextWindow != null)
    	    	{
    	    		thisActor.getStage().addActor(thisActor.nextWindow);
    	    		thisActor.getStage().addActor(thisActor.nextWindow.Speaker);
    	    	}
    	    	if(thisActor.Callbackcode != null)
    	    	{
    	    		java.lang.reflect.Method m = null;
					try {
						Class<?> c = thisActor.Callbackcode.getClass().getDeclaringClass();
						Class<?>[] cArg = new Class[1];
				        cArg[0] = thisActor.Callbackcode.getClass();
						m = c.getDeclaredMethod("call" , cArg);
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		try {
						m.invoke(null, thisActor.Callbackcode);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    	}
    	    	
    	    	
    	    	thisActor.Speaker.remove();
    	    	thisActor.remove();
    	    	
    	    };
    		
    	});
		
	}

}
