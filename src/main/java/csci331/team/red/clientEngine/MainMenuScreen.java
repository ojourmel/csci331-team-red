package csci331.team.red.clientEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import csci331.team.red.shared.Background;
import csci331.team.red.shared.Level;
/**
 * Main menu screen. 
 * @author Lduperron
 */


public class MainMenuScreen implements Screen 
{
	ClientEngine parentEngine;
	
	Texture backgroundImage;
	
	private OrthographicCamera camera;
	
	SpriteBatch batch;
	
	Stage mainMenuStage;
	
	InputMultiplexer multiplexer;
	

	public MainMenuScreen(ClientEngine parent)
	{
		// Sets up links to our parent
		parentEngine = parent;
		batch = parentEngine.primarySpriteBatch;
		
		
		// Loads the background image
		backgroundImage = parentEngine.gameTextureManager.get(parentEngine.Backgrounds.get(Background.MENUSCREEN));
		
		// Sets up the camera
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    	    
	    // Initializes a stage to hold our buttons
	    mainMenuStage = new Stage();
	    
	    // Sets up an input multiplexer to handle our input to the buttons
	    multiplexer = new InputMultiplexer();
	    multiplexer.addProcessor(mainMenuStage);
	    
	    
	    // Creates our buttons
	    TextButton ConnectButton = new TextButton("Connect" , parentEngine.buttonStyle);
	    mainMenuStage.addActor(ConnectButton);
	    ConnectButton.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	parentEngine.switchToNewScreen(ScreenEnumerations.Connecting);
    	    	
    	    };
    		
    	});
	    
	    
	    
	    TextButton ListenButton =  new TextButton("Host" , parentEngine.buttonStyle);
	    mainMenuStage.addActor(ListenButton);
	    ListenButton.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	parentEngine.switchToNewScreen(ScreenEnumerations.Hosting);
    	    	
    	    };
    		
    	});
	    
	    TextButton TestLevelOneFieldButton =  new TextButton("Test Level One (Field Agent)" , parentEngine.buttonStyle);
	    mainMenuStage.addActor(TestLevelOneFieldButton);
	    TestLevelOneFieldButton.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	parentEngine.setLevel(Level.getCampus());
    	    	parentEngine.switchToNewScreen(ScreenEnumerations.FieldAgent);
    	    	
    	    };
    		
    	});
	    
	    
	    
	    TextButton TestLevelOneDBButton =  new TextButton("Test Level One (DB Agent)" , parentEngine.buttonStyle);
	    mainMenuStage.addActor(TestLevelOneDBButton);
	    TestLevelOneDBButton.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	parentEngine.setLevel(Level.getCampus());
    	    	parentEngine.switchToNewScreen(ScreenEnumerations.DatabaseAgent);
    	    	
    	    };
    		
    	});
	    
	    TextButton SettingsButton =  new TextButton("Settings" , parentEngine.buttonStyle);
	    mainMenuStage.addActor(SettingsButton);
	    SettingsButton.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	parentEngine.switchToNewScreen(ScreenEnumerations.Settings);
    	    	
    	    };
    		
    	});
	    
	    TextButton QuitButton =  new TextButton("Quit" , parentEngine.buttonStyle);
	    mainMenuStage.addActor(QuitButton);
	    QuitButton.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	Gdx.app.exit();
    	    	
    	    };
    		
    	});
	    
	    // Sets the position of the buttons
	    ConnectButton.setPosition(Gdx.graphics.getWidth()/2-ConnectButton.getWidth()/2 , (Gdx.graphics.getHeight() / (mainMenuStage.getActors().size+1)*6  )   );
	    ListenButton.setPosition(Gdx.graphics.getWidth()/2-ListenButton.getWidth()/2 , (Gdx.graphics.getHeight() / (mainMenuStage.getActors().size+1)*5  )   );
	    TestLevelOneFieldButton.setPosition(Gdx.graphics.getWidth()/2-TestLevelOneFieldButton.getWidth()/2 , (Gdx.graphics.getHeight() / (mainMenuStage.getActors().size+1)*4  )   );
	    TestLevelOneDBButton.setPosition(Gdx.graphics.getWidth()/2-TestLevelOneDBButton.getWidth()/2 , (Gdx.graphics.getHeight() / (mainMenuStage.getActors().size+1)*3  )   );
	    SettingsButton.setPosition(Gdx.graphics.getWidth()/2-SettingsButton.getWidth()/2 , (Gdx.graphics.getHeight() / (mainMenuStage.getActors().size+1)*2  )   );
	    QuitButton.setPosition(Gdx.graphics.getWidth()/2-QuitButton.getWidth()/2 , (Gdx.graphics.getHeight() / (mainMenuStage.getActors().size+1)*1  )   );
	    
	    
	}
	
	
	@Override
	public void render(float delta) 
	{
		
		
		Gdx.graphics.getGL20().glClearColor( 0, 0, 0, 1 );
		Gdx.graphics.getGL20().glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

		
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
	    
		
		batch.begin();
	    
		batch.disableBlending();

		batch.draw(backgroundImage , 0 ,0 , Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    
		batch.enableBlending();
		
		batch.end();
		
		mainMenuStage.act();
		mainMenuStage.draw();
		

	}

	@Override
	public void resize(int width, int height) {

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

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}
