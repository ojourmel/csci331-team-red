package csci331.team.red.client;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class dialgoueWindow extends TextButton {

	TextButton internalReference;
	TextButton Speaker;
	
	public dialgoueWindow(String dialogue, String thespeaker, TextButtonStyle style , Stage parentStage) {
		this(dialogue, thespeaker, style, parentStage,  false , 0);
	}
	
	public dialgoueWindow(String dialogue, String thespeaker, TextButtonStyle style , Stage parentStage, boolean useWordWrap , int extraPadding) {
		super(dialogue, style , useWordWrap , extraPadding);
		
		internalReference = this;
		
		Speaker = new TextButton(thespeaker, style , false , 0);
    	Speaker.setPosition(0, this.getHeight()+5);
    	
    	
    	parentStage.addActor(this);
    	parentStage.addActor(Speaker);
		
    	this.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	dialgoueWindow thisActor = (dialgoueWindow)event.getListenerActor();
    	    	thisActor.Speaker.remove();
    	    	thisActor.remove();
    	    	
    	    };
    		
    	});
		
	}

}
