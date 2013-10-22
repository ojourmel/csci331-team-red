package csci331.team.red.clientEngine;

import java.util.HashMap;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Quad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import csci331.team.red.shared.Dialog;
/**
 * @author Lduperron
 */


public class FieldAgentScreen implements Screen 
{
	ClientEngine parentEngine;
	
	Texture backgroundImage;
	Texture clipBoard;
	
	HashMap<String , Pixmap> characters;
	
	
	private OrthographicCamera camera;
	
	SpriteBatch batch;

	Stage uiStage;
	Stage papersStage;
	Stage charactersStage;
	Stage backgroundStage;
	Stage dialogueStage;
	
	InputMultiplexer multiplexer;
	
	TextButton passiveState;
	TextButton aggressiveState;
	ButtonGroup states;
	
	
	TextButton allowButton;
	TextButton detainButton;
	
	TransparentActor currentPerson;
	TransparentActor ticket;
	
	// Used for controlling stuff moving around.  Going to be moved into the game.
	TweenManager tweenManager;
	

	public FieldAgentScreen(ClientEngine parent)
	{
		fieldDialogCallbacks.entity = this;
		
		// Sets up links to our parent
		parentEngine = parent;
		batch = parentEngine.primarySpriteBatch;
		
		
		
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorTweener());
		
		// Loads the background image
		backgroundImage = parentEngine.gameTextureManager.get(parentEngine.Textures.get("level1fieldbg"));
		
		// Loads other textures
		clipBoard = parentEngine.gameTextureManager.get(parentEngine.Textures.get("clipboard"));
		
		characters = new HashMap<String, Pixmap>();
		
		characters.put("thug", parentEngine.gamePixmapManager.get(parentEngine.Pixmaps.get("level1thug")));
		characters.put("female", parentEngine.gamePixmapManager.get(parentEngine.Pixmaps.get("level1female")));
		characters.put("male", parentEngine.gamePixmapManager.get(parentEngine.Pixmaps.get("level1male")));
		
		// Sets up the camera
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    	    
	    // Initializes a stage to hold our buttons
	    papersStage = new Stage();
	    uiStage = new Stage();
	    backgroundStage = new Stage();
	    charactersStage = new Stage();
	    dialogueStage = new Stage();
	    
	    // Sets up an input multiplexer to handle our input to the buttons
	    multiplexer = new InputMultiplexer();
	    multiplexer.addProcessor(dialogueStage);
	    multiplexer.addProcessor(uiStage);
	    multiplexer.addProcessor(papersStage);
	    multiplexer.addProcessor(backgroundStage);
	    multiplexer.addProcessor(charactersStage);
	    
	    
	    
	    
	    
	    
	    
	    states = new ButtonGroup();
	    
	    
	    passiveState = new TextButton("Passive" , parentEngine.radioButtonStyle);
	    passiveState.setPosition(Gdx.graphics.getWidth()-passiveState.getWidth(), Gdx.graphics.getHeight()-passiveState.getHeight());
	    states.add(passiveState);
	    uiStage.addActor(passiveState);
	    
	    
	    aggressiveState = new TextButton("Aggressive" , parentEngine.radioButtonStyle);
	    aggressiveState.setPosition(Gdx.graphics.getWidth()-aggressiveState.getWidth(), passiveState.getY()-aggressiveState.getHeight());
	    states.add(aggressiveState);
	    uiStage.addActor(aggressiveState);
	    
	    
	    
	    
	    
	    
	    
	  
	    
	    
	    
	    
	    detainButton = new TextButton("Detain!" , parentEngine.buttonStyle);
	    detainButton.setPosition(Gdx.graphics.getWidth()-detainButton.getWidth(), 0);
	    detainButton.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    		    String[][] strarr = {
    		    		{"Oh dear, sweetie... it seems you've forgotten your cuffs" , "Ominious Voice"},
    		    		{"...and your gun...", "Ominious Voice"},
    		    		{"...and your badge.", "Ominious Voice"},
    		    		{"You can go this time, citizen." , "You"},
    		    		
    		    };
    		    fieldDialogCallbacks.callbacks[] callarr = {null, null, null , null};
    		    Dialog[] d = Dialog.returnDialogArray(strarr , callarr);
    		    displayDialogue(d);
    	    	
    	    };
    		
    	});
	    
	    

	    uiStage.addActor(detainButton);
	    
	    allowButton = new TextButton("Allow" , parentEngine.buttonStyle);
	    allowButton.setPosition(Gdx.graphics.getWidth()-allowButton.getWidth(), detainButton.getHeight());
	    allowButton.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    		    String[][] strarr = {
    		    		{"Get out of my sight." , "You"},
    		    		{"Well.  That was uneventful." , "Ominious Voice"},
    		    		{"You should approach him.  He looks suspicious.", "Ominious Voice"},
    		    		{"420 YOLO SWAG MOFO" , "Thug"},
    		    		{"Oh dear.  Quick, switch to AGGRESSIVE and use your ULTRATASER 9000 X-TREME EDITION" , "Ominious Voice"},
    		    		{"...Will you back off, old lady?  I've got this." , "You"},
    		    		{"...Aren't you Harold's mom?" , "You"},
    		    		{"I'm just here making sure everything goes okay for him." , "Harold's Mom"},
    		    		{"..." , "You"},
    		    		 
    		    		
    		    };
    		    fieldDialogCallbacks.callbacks[] callarr = {null, null, fieldDialogCallbacks.callbacks.approachSecondPerson , null , null , null , null , null , null ,null ,null ,null ,null};
    		    Dialog[] d = Dialog.returnDialogArray(strarr , callarr);
    		    displayDialogue(d);
    	    	
    		    
    		    
    		    tweenManager.killTarget(currentPerson);
    		    tweenManager.killTarget(ticket);
    		    Tween.to(currentPerson,ActorTweener.POSITION_XY, 1.0f).target(-500, -500).ease(Quad.IN).start(tweenManager);
    		    Tween.to(ticket,ActorTweener.POSITION_XY, 1.0f).target(-500, ticket.getY()).ease(Quad.IN).start(tweenManager);
    		    
    		    
    		    
    		    
    		    
    		   // Tween.call(nextPerson).delay(3.0f).start(tweenManager);
    		    
    	    };
    		
    	});
	    uiStage.addActor(allowButton);
	    
	    
	    
	    String[][] strarr = {
	    		{"Well then... (Click to continue)" , "Ominious Voice"},
	    		{"Welcome to your first day on the job.", "Ominious Voice"},
	    		{"Why don't we approach someone?", "Ominious Voice"},
	    		{"Hey, you!  Stop!", "You"},
	    		{"...What?", "Girl"},
	    		{"...Turn around", "You"},
	    		{"...No.", "Girl"},
	    		{"...Well, let's see your ID." , "You"},
	    		{"Here." , "Girl"},
	    		{"You should call your partner and ask him if the infomation you've recieved is correct." , "Ominious Voice"},
	    		{"But I can tell you it is this time.  You should let her go." , "Ominious Voice"},
	    		
	    };
	    fieldDialogCallbacks.callbacks[] callarr = {null, null,fieldDialogCallbacks.callbacks.approachFirstPerson, null,null, null,null, fieldDialogCallbacks.callbacks.giveID, null,null, null , null , null, null};
	    Dialog[] d = Dialog.returnDialogArray(strarr , callarr);
	    displayDialogue(d);

	    
	    
	    

	    TextButton BackButton =  new TextButton("Back" , parentEngine.buttonStyle);
	    uiStage.addActor(BackButton);
	    BackButton.toFront();
	    BackButton.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	parentEngine.switchToNewScreen(ScreenEnumerations.MainMenu);
    	    	
    	    };
    		
    	});
	    
	    
	}
	
	private void displayNewPerson(final int currentdude) // This is just bad and hardcoded.  The server will provide a 'person' in the future.
	{
		

		
		if(currentdude == 1)
		{
		    currentPerson = new TransparentActor(characters.get("female") , tweenManager);
		    
		   
		    charactersStage.addActor(currentPerson);
		    Tween.to(currentPerson,ActorTweener.POSITION_XY, 1.0f).target(300, 300).ease(Quad.IN).start(tweenManager);
		
		   // Tween.to(thug,ActorTweener.SCALE_XY, 3.0f).target(2 , 2).ease(Quad.IN).start(tweenManager);
		    
		    //Tween.to(thug,ActorTweener.ZOOM, 3.0f).target(thug.getX()-thug.getWidth()/2 ,thug.getY()-thug.getHeight()/2  ,  2 , 2).ease(Quad.IN).delay(2).start(tweenManager);
		    
		    Tween.to(currentPerson,ActorTweener.ZOOM, 3.0f).target(300-currentPerson.getWidth()/2 ,(300-currentPerson.getHeight()/2)-200  ,  2 , 2).ease(Quad.IN).delay(2).start(tweenManager);
		}
	    
		else if(currentdude == 2)
		{
			
			currentPerson = new TransparentActor(characters.get("thug") , tweenManager);
		    
			
			
			
		    ticket = new TransparentActor(parentEngine.gamePixmapManager.get(parentEngine.Pixmaps.get("goldenTicket")) , tweenManager);
		    ticket.setDragable();
		    ticket.scale(-1);
		    ticket.setPosition(300, 400);
		    Tween.to(ticket, ActorTweener.ZOOM , 1.0f).target(200 ,100  ,  1 , 1).ease(Quad.IN).delay(6).start(tweenManager);
		    
		    
		    papersStage.addActor(ticket);
		    
		    charactersStage.addActor(currentPerson);
		    Tween.to(currentPerson,ActorTweener.POSITION_XY, 1.0f).target(300, 300).ease(Quad.IN).start(tweenManager);
		    
		   // Tween.to(thug,ActorTweener.SCALE_XY, 3.0f).target(2 , 2).ease(Quad.IN).start(tweenManager);
		    
		    //Tween.to(thug,ActorTweener.ZOOM, 3.0f).target(thug.getX()-thug.getWidth()/2 ,thug.getY()-thug.getHeight()/2  ,  2 , 2).ease(Quad.IN).delay(2).start(tweenManager);
		    
		    Tween.to(currentPerson,ActorTweener.ZOOM, 3.0f).target(300-currentPerson.getWidth()/2 ,(300-currentPerson.getHeight()/2)-200  ,  2 , 2).ease(Quad.IN).delay(2).start(tweenManager);
	
			
			
		}
		
		
	}
	
	
	@Override
	public void render(float delta) 
	{
		
		
		Gdx.graphics.getGL20().glClearColor( 0, 0, 0, 1 );
		Gdx.graphics.getGL20().glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

		
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
	    
		
		tweenManager.update(Gdx.graphics.getDeltaTime());
		
		batch.begin();
	    
		batch.disableBlending();

		batch.draw(backgroundImage , 0 ,0 , Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
	    
		batch.enableBlending();
		
		batch.end();
		
		
		charactersStage.act();
		charactersStage.draw();
		
		batch.begin();
		
		// TODO:  Turn these into actors
		batch.draw(clipBoard, 0, 0, Gdx.graphics.getWidth()-100, 500);
		
		batch.end();
		

		
		backgroundStage.act();
		backgroundStage.draw();
		
		papersStage.act();
		papersStage.draw();
		
		uiStage.act();
		uiStage.draw();
		
		dialogueStage.act();
		dialogueStage.draw();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		
		Gdx.input.setInputProcessor(multiplexer);
		

	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	
	// TODO:  Move this into a level if we even have dialog.
	// This is static because... I wanted to enum and couldn't figure out how to enum without it being static.
	// =<
	public static class fieldDialogCallbacks
	{
		static FieldAgentScreen entity;
		
		public static enum callbacks
		{
			approachFirstPerson,
			giveID,
			approachSecondPerson
		}
		
		public static void setEntity(FieldAgentScreen screen)
		{
			
			entity = screen;
		}
		
		public static void call(callbacks c)
		{
			switch(c)
			{
			case approachFirstPerson:
				entity.displayNewPerson(1);
				break;
			case approachSecondPerson:
				entity.displayNewPerson(2);
				break;
				
			case giveID:
				entity.produceDocument(1);
			
			default:
				break;
			
			
			}
			
		
			
			
		}

			
			
	}
	
	public void produceDocument(int documentNumber)
	{
		
		 ticket = new TransparentActor(parentEngine.gamePixmapManager.get(parentEngine.Pixmaps.get("goldenTicket")) , tweenManager);
		    ticket.setDragable();
		    ticket.scale(-1);
		    ticket.setPosition(300, 400);
		    Tween.to(ticket, ActorTweener.ZOOM , 1.0f).target(200 ,100  ,  1 , 1).ease(Quad.IN).start(tweenManager);
		    
		    
		    papersStage.addActor(ticket);
		    
		
	}
	
	// TODO:  Move dialogue functions into somewhere shared between the two agent stages.  Maybe.
	public void displayDialogue(String speaker, String Dialogue)
	{
		Dialog[] temp = new Dialog[1];
		temp[0] = new Dialog(Dialogue, speaker);
		displayDialogue(temp);
	}
	
	public void displayDialogue(Dialog[] dialogueArray)
	{
		dialogueStage.clear();
		
		if(dialogueArray.length == 0)
		{
			return;
		}
		
		DialogueWindow iteratorOld = null;
		if(dialogueArray.length > 1)
		{
			iteratorOld = new DialogueWindow(dialogueArray[dialogueArray.length-1].getDialogue(), dialogueArray[dialogueArray.length-1].getSpeaker(), parentEngine.dialogueStyle , dialogueStage, true , 20 , false, null , dialogueArray[dialogueArray.length-1].getCallbackCode());
			
			
			for(int i = dialogueArray.length-2; i > 0 ; i--)
			{
				DialogueWindow iteratorNew = new DialogueWindow (dialogueArray[i].getDialogue(), dialogueArray[i].getSpeaker(), parentEngine.dialogueStyle , dialogueStage, true , 20 , false, iteratorOld ,dialogueArray[i].getCallbackCode());
				iteratorOld = iteratorNew;
			}
		}
		new DialogueWindow(dialogueArray[0].getDialogue(), dialogueArray[0].getSpeaker(), parentEngine.dialogueStyle , dialogueStage, true , 20 , true, iteratorOld , dialogueArray[0].getCallbackCode());
		
	}
		
	

}
