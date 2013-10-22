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


/**
 * @author Lduperron
 */


public class SettingsScreen implements Screen 
{
	ClientEngine parentEngine;
	
	Texture backgroundImage;
	
	private OrthographicCamera camera;
	
	SpriteBatch batch;
	
	Stage settingsStage;
	
	InputMultiplexer multiplexer;
	

	public SettingsScreen(ClientEngine parent)
	{
		// Sets up links to our parent
		parentEngine = parent;
		batch = parentEngine.primarySpriteBatch;
		
		
		// Loads the background image
		backgroundImage = parentEngine.gameTextureManager.get(parentEngine.Textures.get("level2fieldbg"));
		
		// Sets up the camera
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    	    
	    // Initializes a stage to hold our buttons
	    settingsStage = new Stage();
	    
	    // Sets up an input multiplexer to handle our input to the buttons
	    multiplexer = new InputMultiplexer();
	    multiplexer.addProcessor(settingsStage);
	    
	    
	    // Creates our buttons
	    TextButton SettingsLabel = new TextButton("No settings lol" , parentEngine.buttonStyle);
	    settingsStage.addActor(SettingsLabel);
  

	    TextButton BackButton =  new TextButton("Back" , parentEngine.buttonStyle);
	    settingsStage.addActor(BackButton);
	    BackButton.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	parentEngine.switchToNewScreen(ScreenEnumerations.MainMenu);
    	    	
    	    };
    		
    	});
	    
	    // Sets the position of the buttons
	    SettingsLabel.setPosition(Gdx.graphics.getWidth()/2-SettingsLabel.getWidth()/2 , (Gdx.graphics.getHeight()/5 *4  )   );
	    BackButton.setPosition(Gdx.graphics.getWidth()/2-BackButton.getWidth()/2 , (Gdx.graphics.getHeight() / (settingsStage.getActors().size+1)/5  )   );

	    
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

		batch.draw(backgroundImage , 0 ,0);
	    
		batch.enableBlending();
		
		batch.end();
		
		settingsStage.act();
		settingsStage.draw();
		

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
