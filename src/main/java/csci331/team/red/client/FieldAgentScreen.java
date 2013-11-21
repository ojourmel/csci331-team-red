package csci331.team.red.client;

import java.util.HashMap;
import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Quad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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

import csci331.team.red.shared.Character;
import csci331.team.red.shared.Decision;
import csci331.team.red.shared.Dialogue;
import csci331.team.red.shared.Document;
import csci331.team.red.shared.Incident;
import csci331.team.red.shared.Message;
import csci331.team.red.shared.Posture;
/**
 * Screen for the field agent
 * @author Lduperron
 */


public class FieldAgentScreen implements Screen 
{
	ClientEngine parentEngine;
	
	Texture backgroundImage;
	Texture clipBoard;
	
	Music BackgroundMusic;
	
	HashMap<String , Pixmap> characters;
	
	
	private OrthographicCamera camera;
	
	SpriteBatch batch;

	Stage uiStage;
	Stage papersStage;
	Stage charactersStage;
	Stage backgroundStage;
	Stage dialogueStage;
	
	InputMultiplexer multiplexer;
	
	TextButton passivePostureButton;
	TextButton aggressivePostureButton;
	ButtonGroup postureButtonGroup;
	
	
	TextButton allowButton;
	TextButton detainButton;
	
	TransparentActor currentPerson;
	TransparentActor ticket;
	
	// Used for controlling stuff moving around.  Going to be moved into the game.
	TweenManager tweenManager;
	
	FieldDialogueCallback<Callback> callbackObject;
	Incident currentIncident;
	

	public FieldAgentScreen(ClientEngine parent)
	{
		callbackObject = new FieldDialogueCallback<Callback>(this);
		
		// Sets up links to our parent
		parentEngine = parent;
		batch = parentEngine.primarySpriteBatch;
		
		
		
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorTweener());
		
		
		
		// Loads the background image
		backgroundImage = parentEngine.gameTextureManager.get(parentEngine.Backgrounds.get(parentEngine.currentLevel.getInteractive()));
		
		// and the background music
		BackgroundMusic = parentEngine.gameMusicManager.get(parentEngine.BackgroundMusic.get(parentEngine.currentLevel.getSoundTrack()));
		BackgroundMusic.setLooping(true);
		BackgroundMusic.play();
		
		
		// Loads other textures
		clipBoard = parentEngine.gameTextureManager.get(parentEngine.Textures.get("clipboard"));
		
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
	    multiplexer.addProcessor(parentEngine.uiControlHandler);
	    multiplexer.addProcessor(dialogueStage);
	    multiplexer.addProcessor(uiStage);
	    multiplexer.addProcessor(papersStage);
	    multiplexer.addProcessor(backgroundStage);
	    multiplexer.addProcessor(charactersStage);
	    
	    
	    
	    
	    
	    
	    
	    postureButtonGroup = new ButtonGroup();
	    
	    
	    passivePostureButton = new TextButton("Passive" , parentEngine.radioButtonStyle);
	    passivePostureButton.setPosition(Gdx.graphics.getWidth()-passivePostureButton.getWidth(), Gdx.graphics.getHeight()-passivePostureButton.getHeight());
	    postureButtonGroup.add(passivePostureButton);
	    
	    passivePostureButton.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    		    parentEngine.network.send(Message.ONPOSTURECHANGE, Posture.PASSIVE);
    	    	
    	    };
    		
    	});
	    
	    
	    uiStage.addActor(passivePostureButton);
	    
	    
	    aggressivePostureButton = new TextButton("Aggressive" , parentEngine.radioButtonStyle);
	    aggressivePostureButton.setPosition(Gdx.graphics.getWidth()-aggressivePostureButton.getWidth(), passivePostureButton.getY()-aggressivePostureButton.getHeight());
	    postureButtonGroup.add(aggressivePostureButton);
	    
	    aggressivePostureButton.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	parentEngine.network.send(Message.ONPOSTURECHANGE, Posture.AGGRESSIVE);
    	    	
    	    };
    		
    	});
	    
	    uiStage.addActor(aggressivePostureButton);
	    
	    
	    
	    
	    
	    
	    
	  
	    
	    
	    
	    
	    detainButton = new TextButton("Detain!" , parentEngine.buttonStyle);
	    detainButton.setPosition(Gdx.graphics.getWidth()-detainButton.getWidth(), 0);
	    detainButton.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
//    		    String[][] strarr = {
//    		    		{"Oh dear, sweetie... it seems you've forgotten your cuffs" , "Ominious Voice"},
//    		    		{"...and your gun...", "Ominious Voice"},
//    		    		{"...and your badge.", "Ominious Voice"},
//    		    		{"You can go this time, citizen." , "You"},
//    		    		
//    		    };
//    		    Dialogue[] d = Dialogue.returnDialogArray(strarr);
//    		    displayDialogue(d);
    	    	
    	    	
    	    	
    	    	parentEngine.network.send(Message.ONDECISIONEVENT, Decision.DETAIN);
    	    	detainPerson();
    	    };
    		
    	});
	    
	    

	    uiStage.addActor(detainButton);
	    
	    allowButton = new TextButton("Allow" , parentEngine.buttonStyle);
	    allowButton.setPosition(Gdx.graphics.getWidth()-allowButton.getWidth(), detainButton.getHeight());
	    allowButton.addListener(new ClickListener() {
    		
    		// TODO: Part of Tutorial Code. To be removed
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
//    		    String[][] strarr = {
//    		    		{"Get out of my sight." , "You"},
//    		    		{"Well.  That was uneventful." , "Ominious Voice"},
//    		    		{"You should approach him.  He looks suspicious.", "Ominious Voice"},
//    		    		{"420 YOLO SWAG MOFO" , "Thug"},
//    		    		{"Oh dear.  Quick, switch to AGGRESSIVE and use your ULTRATASER 9000 X-TREME EDITION" , "Ominious Voice"},
//    		    		{"...Will you back off, old lady?  I've got this." , "You"},
//    		    		{"...Aren't you Harold's mom?" , "You"},
//    		    		{"I'm just here making sure everything goes okay for him." , "Harold's Mom"},
//    		    		{"..." , "You"},
//    		    		 
//    		    		
//    		    };
//    		    //FieldCallback[] callarr = {null, null, FieldCallback.approachPerson};
//    		    //Dialogue[] d = Dialogue.returnDialogArray(strarr , callarr);
//    		    Dialogue[] d = Dialogue.returnDialogArray(strarr);
//    		    displayDialogue(d);
    	    	
    	    	parentEngine.network.send(Message.ONDECISIONEVENT, Decision.ALLOW);
    	    	allowPerson();

    		    
    		    
    		    
    		    
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
	    FieldCallback[] callarr = {null, null,FieldCallback.approachPerson, null,null, null,null, FieldCallback.giveDocuments, null,null, null , null , null, null};
	    //Dialogue[] d = Dialogue.returnDialogArray(strarr , callarr);
	    Dialogue[] d = Dialogue.returnDialogArray(strarr);
	    
	    displayDialogue(d);

	    
	    
	    

//	    TextButton BackButton =  new TextButton("Back" , parentEngine.buttonStyle);
//	    uiStage.addActor(BackButton);
//	    BackButton.toFront();
//	    BackButton.addListener(new ClickListener() {
//    		
//    		
//    	    @Override
//    	    public void clicked(InputEvent event, float x, float y) 
//    	    {
//    	    	parentEngine.switchToNewScreen(ScreenEnumerations.MainMenu);
//    	    	
//    	    };
//    		
//    	});
	    
	    
	}
	
	void displayNewPerson(Character person)
	{

		currentPerson = new TransparentActor(parentEngine.gamePixmapManager.get(parentEngine.PersonPictures.get(person.getAvatar())) , tweenManager);
		    
		   
		charactersStage.addActor(currentPerson);
		Tween.to(currentPerson,ActorTweener.POSITION_XY, 1.0f).target(300, 300).ease(Quad.IN).start(tweenManager);
		Tween.to(currentPerson,ActorTweener.ZOOM, 3.0f).target(300-currentPerson.getWidth()/2 ,(300-currentPerson.getHeight()/2)-200  ,  2 , 2).ease(Quad.IN).delay(2).start(tweenManager);
		
	  
			
		
		
		
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
	}

	@Override
	public void show() {
		
		Gdx.input.setInputProcessor(multiplexer);
		BackgroundMusic.play();

	}

	@Override
	public void hide() {
		
		Gdx.input.setInputProcessor(null);
		BackgroundMusic.pause();

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		parentEngine.fieldAgentScreen = null;
	}
	

	
	public void produceDocuments(List<Document> documents)
	{
		for(Document document : documents){
			switch(document.DocumentType)
			{
				case DriversLicence:
					ticket = new DriversLicence(parentEngine.gamePixmapManager.get(parentEngine.Documents.get(document.DocumentType)) , tweenManager, document.TextFields);
					break;
				case GoldenTicket:
					ticket = new DriversLicence(parentEngine.gamePixmapManager.get(parentEngine.Documents.get(document.DocumentType)) , tweenManager, document.TextFields);
					break;
				default:
					break;
			}
		}
		
		
		ticket.setDragable();
		ticket.scale(-1);
		ticket.setPosition(300, 400);
		Tween.to(ticket, ActorTweener.ZOOM , 1.0f).target(200 ,100  ,  1 , 1).ease(Quad.IN).start(tweenManager);
		    
		papersStage.addActor(ticket);

	
	}
	
	public void displayDialogue(String speaker, String Dialogue)
	{
		Dialogue[] temp = new Dialogue[1];
		temp[0] = new Dialogue(Dialogue, speaker);
		displayDialogue(temp);
	}
	
	public void displayDialogue(Dialogue[] dialogueArray)
	{
		dialogueStage.clear();
		
		if(dialogueArray.length == 0)
		{
			return;
		}
		
		DialogueWindow iteratorOld = null;
		if(dialogueArray.length > 1)
		{
			iteratorOld = new DialogueWindow(dialogueArray[dialogueArray.length-1].getDialogue(), dialogueArray[dialogueArray.length-1].getSpeaker(), parentEngine.dialogueStyle , dialogueStage, true , 20 , false, null , dialogueArray[dialogueArray.length-1].getCallbackCode(), callbackObject);
			
			
			for(int i = dialogueArray.length-2; i > 0 ; i--)
			{
				DialogueWindow iteratorNew = new DialogueWindow (dialogueArray[i].getDialogue(), dialogueArray[i].getSpeaker(), parentEngine.dialogueStyle , dialogueStage, true , 20 , false, iteratorOld ,dialogueArray[i].getCallbackCode(), callbackObject);
				iteratorOld = iteratorNew;
			}
		}
		new DialogueWindow(dialogueArray[0].getDialogue(), dialogueArray[0].getSpeaker(), parentEngine.dialogueStyle , dialogueStage, true , 20 , true, iteratorOld , dialogueArray[0].getCallbackCode(), callbackObject);
		
	}
	
	public void allowPerson()
	{
		
	    tweenManager.killTarget(currentPerson);
	    tweenManager.killTarget(ticket);
	    Tween.to(currentPerson,ActorTweener.POSITION_XY, 1.0f).target(-500, -500).ease(Quad.IN).start(tweenManager);
	    if(ticket!=null)
	    {
	    	Tween.to(ticket,ActorTweener.POSITION_XY, 1.0f).target(-500, ticket.getY()).ease(Quad.IN).start(tweenManager);
	    }
	    
		
	}
	
	public void detainPerson()
	{
		
	    tweenManager.killTarget(currentPerson);
	    tweenManager.killTarget(ticket);
	    Tween.to(currentPerson,ActorTweener.POSITION_XY, 1.0f).target(-500, -500).ease(Quad.IN).start(tweenManager);
	    if(ticket!=null)
	    {
	    	Tween.to(ticket,ActorTweener.POSITION_XY, 1.0f).target(-500, ticket.getY()).ease(Quad.IN).start(tweenManager);
	    }
	    
		
	}
}
