package csci331.team.red.clientEngine;

import java.util.HashMap;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Quad;

import com.badlogic.gdx.Application;
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
	
	InputMultiplexer multiplexer;
	
	TextButton passiveState;
	TextButton aggressiveState;
	ButtonGroup states;
	
	
	TextButton allowButton;
	TextButton detainButton;
	
	
	// Used for controlling stuff moving around.  Going to be moved into the game.
	TweenManager tweenManager;
	

	public FieldAgentScreen(ClientEngine parent)
	{
		// Sets up links to our parent
		parentEngine = parent;
		batch = parentEngine.primarySpriteBatch;
		
		
		
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorTweener());
		
		// Loads the background image
		backgroundImage = parentEngine.gameTextureManager.get(parentEngine.level1fieldbg);
		
		// Loads other textures
		clipBoard = parentEngine.gameTextureManager.get(parentEngine.clipboard);
		
		characters = new HashMap<String, Pixmap>();
		
		characters.put("thug", parentEngine.gamePixmapManager.get(parentEngine.level1thug));
		
		
		
		// Sets up the camera
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    	    
	    // Initializes a stage to hold our buttons
	    papersStage = new Stage();
	    uiStage = new Stage();
	    backgroundStage = new Stage();
	    charactersStage = new Stage();
	    
	    // Sets up an input multiplexer to handle our input to the buttons
	    multiplexer = new InputMultiplexer();
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

	    uiStage.addActor(detainButton);
	    
	    allowButton = new TextButton("Allow" , parentEngine.buttonStyle);
	    allowButton.setPosition(Gdx.graphics.getWidth()-allowButton.getWidth(), detainButton.getHeight());

	    uiStage.addActor(allowButton);
	    

	  
	    TransparentActor thug = new TransparentActor(characters.get("thug") , tweenManager);
	    
	    TransparentActor ticket = new TransparentActor(parentEngine.gamePixmapManager.get(parentEngine.goldenTicket) , tweenManager);
	    ticket.setDragable();
	    ticket.scale(-1);
	    ticket.setPosition(300, 400);
	    Tween.to(ticket, ActorTweener.ZOOM , 1.0f).target(200 ,100  ,  1 , 1).ease(Quad.IN).delay(6).start(tweenManager);
	    
	    
	    papersStage.addActor(ticket);
	    
	    charactersStage.addActor(thug);
	    Tween.to(thug,ActorTweener.POSITION_XY, 1.0f).target(300, 300).ease(Quad.IN).start(tweenManager);
	    
	   // Tween.to(thug,ActorTweener.SCALE_XY, 3.0f).target(2 , 2).ease(Quad.IN).start(tweenManager);
	    
	    //Tween.to(thug,ActorTweener.ZOOM, 3.0f).target(thug.getX()-thug.getWidth()/2 ,thug.getY()-thug.getHeight()/2  ,  2 , 2).ease(Quad.IN).delay(2).start(tweenManager);
	    
	    Tween.to(thug,ActorTweener.ZOOM, 3.0f).target(300-thug.getWidth()/2 ,(300-thug.getHeight()/2)-100  ,  2 , 2).ease(Quad.IN).delay(2).start(tweenManager);
	    
	    
	    
	    
	    
	    

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
		batch.draw(clipBoard, 0, 0, Gdx.graphics.getWidth(), 500);
		batch.draw(clipBoard, 900, 0, Gdx.graphics.getWidth()-700, Gdx.graphics.getHeight()+200);
		
		batch.end();
		

		
		backgroundStage.act();
		backgroundStage.draw();
		
		papersStage.act();
		papersStage.draw();
		
		uiStage.act();
		uiStage.draw();
		

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

}
