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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Handles connecting to other games.
 * @author Lduperron
 */

public class ConnectingScreen implements Screen 
{
	ClientEngine parentEngine;
	
	Texture backgroundImage;
	
	private OrthographicCamera camera;
	
	SpriteBatch batch;
	
	Stage connectingStage;
	
	InputMultiplexer multiplexer;
	

	public ConnectingScreen(ClientEngine parent)
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
	    connectingStage = new Stage();
	    
	    // Sets up an input multiplexer to handle our input to the buttons
	    multiplexer = new InputMultiplexer();
	    multiplexer.addProcessor(connectingStage);
	    
	    
	    // Creates our buttons
	    TextButton ConnectionLabel = new TextButton("Connect to?\nEnter to connect" , parentEngine.buttonStyle);
	    connectingStage.addActor(ConnectionLabel);
	    ConnectionLabel.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	
    	    	
    	    };
    		
    	});
	    
	    TextField connectAddress = new TextField("" , parentEngine.inputStyle);
	    connectAddress.setMessageText("Enter hostname or IP!");
	    connectAddress.setWidth(parentEngine.gameFont.getBounds("Enter hostname or IP!").width +20);
	    connectingStage.addActor(connectAddress);

	    
	  	
	    connectAddress.setTextFieldListener(new TextFieldListener() {
            public void keyTyped (TextField textField, char key) {
            	
            //	System.out.println(key);
            	
            	// Linux or windows!
                if(key == '\n' || key == '\r')
                {
                	textField.setText("");
                	textField.getStage().unfocus(textField);
                	
                }
                
            }
        });
	    
	    
	    TextButton Backbutton =  new TextButton("Back" , parentEngine.buttonStyle);
	    connectingStage.addActor(Backbutton);
	    Backbutton.addListener(new ClickListener() {
    		
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	parentEngine.switchToNewScreen(ScreenEnumerations.MainMenu);
    	    	
    	    };
    		
    	});
	    
	    // Sets the position of the buttons
	    ConnectionLabel.setPosition(Gdx.graphics.getWidth()/2-ConnectionLabel.getWidth()/2 , (Gdx.graphics.getHeight() / (connectingStage.getActors().size+1)*3  )   );
	    
	    connectAddress.setPosition(Gdx.graphics.getWidth()/2-connectAddress.getWidth()/2 , (Gdx.graphics.getHeight() / (connectingStage.getActors().size+1)*2  )   );
	    
	    
	    Backbutton.setPosition(Gdx.graphics.getWidth()/2-Backbutton.getWidth()/2 , (Gdx.graphics.getHeight() / (connectingStage.getActors().size+1)*1  )   );
	    
	    
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
		
		connectingStage.act();
		connectingStage.draw();
		

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
