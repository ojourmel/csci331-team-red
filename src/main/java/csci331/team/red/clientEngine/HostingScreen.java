package csci331.team.red.clientEngine;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
/**
 * Screen for hosting a game.  Will start a server and then connect to it, and then wait for another to connect.
 * Server will be killed if you go 'back'
 * @author Lduperron
 */


public class HostingScreen implements Screen 
{
	ClientEngine parentEngine;
	
	Texture backgroundImage;
	
	private OrthographicCamera camera;
	
	SpriteBatch batch;
	
	Stage hostingStage;
	
	InputMultiplexer multiplexer;
	

	public HostingScreen(ClientEngine parent)
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
	    hostingStage = new Stage();
	    
	    // Sets up an input multiplexer to handle our input to the buttons
	    multiplexer = new InputMultiplexer();
	    multiplexer.addProcessor(hostingStage);
	    
	    
	    // Creates our buttons
	    TextButton HostingLabel = new TextButton("Stay a while... and listen." , parentEngine.buttonStyle);
	    hostingStage.addActor(HostingLabel);

	    InetAddress localhost = null;
	    try {
	    	localhost  =  java.net.InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    TextButton HostnameLabel =  new TextButton("Hostname: " + localhost.getHostName() , parentEngine.buttonStyle);
	    hostingStage.addActor(HostnameLabel);
	    

	    TextButton BackButton =  new TextButton("Stop Hosting" , parentEngine.buttonStyle);
	    hostingStage.addActor(BackButton);
	    BackButton.addListener(new ClickListener() 
	    {
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	parentEngine.StopHosting();
    	    	parentEngine.switchToNewScreen(ScreenEnumerations.MainMenu);
    	    	
    	    };
    		
    	});
	    
    		

	    
	    // Sets the position of the buttons
	    HostingLabel.setPosition(Gdx.graphics.getWidth()/2-HostingLabel.getWidth()/2 , (Gdx.graphics.getHeight() / (hostingStage.getActors().size+1)*3  )   );
	    HostnameLabel.setPosition(Gdx.graphics.getWidth()/2-HostnameLabel.getWidth()/2 , (Gdx.graphics.getHeight() / (hostingStage.getActors().size+1)*2  )   );
	    BackButton.setPosition(Gdx.graphics.getWidth()/2-BackButton.getWidth()/2 , (Gdx.graphics.getHeight() / (hostingStage.getActors().size+1)*1  )   );
	    
	    parentEngine.HostGame();
	    
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
		
		hostingStage.act();
		hostingStage.draw();
		

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
