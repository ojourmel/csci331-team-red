package csci331.team.red.clientEngine;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class DatabaseAgentScreen implements Screen 
{
	ClientEngine parentEngine;
	
	Texture backgroundImage;
	
	private OrthographicCamera camera;
	
	SpriteBatch batch;
	
	Stage computerStage;
	TextField computerTextBox;

	
	ScrollPane computerTextScroller;
	Table computerTextLabelContainingTable;
	Table computerTextScrollingTable;
	
	
	
	ScrollPane alertTextScroller;
	Table alertTextLabelContainingTable;
	Table alertTextScrollingTable;
	
	
	Stage alertsStage;
	
	
	
	Stage backgroundStage;
	
	InputMultiplexer multiplexer;
	

	public DatabaseAgentScreen(ClientEngine parent)
	{
		// Sets up links to our parent
		parentEngine = parent;
		batch = parentEngine.primarySpriteBatch;
		
		
		// Loads the background image
		backgroundImage = parentEngine.gameTextureManager.get(parentEngine.level1databasebg);
		
		// Sets up the camera
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    	    
	    // Initializes a stage to hold our buttons
	    computerStage = new Stage();
	    alertsStage = new Stage();
	    backgroundStage = new Stage();
	    // Sets up an input multiplexer to handle our input to the buttons
	    
	    multiplexer = new InputMultiplexer();
	    multiplexer.addProcessor(computerStage);
	    multiplexer.addProcessor(alertsStage);
	    multiplexer.addProcessor(backgroundStage);
	    
	    computerTextBox = new TextField("" , parentEngine.inputStyle);
	    computerTextBox.setPosition(10, 100);
	    computerTextBox.setWidth(350);
	    
    	
	    computerTextBox.setTextFieldListener(new TextFieldListener() 
	    {
            public void keyTyped (TextField textField, char key) {
            	
            //	System.out.println(key);
            	
                if(key == '\n' || key == '\r')
                {
                	
                	Label newCommandLabel = new Label(textField.getText() , parentEngine.rawTextStyle);
                	textField.setText("");
                	
                	
                	computerTextLabelContainingTable.row();
            	    computerTextLabelContainingTable.add(newCommandLabel).left();
            	    
                	
                	//textField.getStage().unfocus(textField);
                	
                	
                	
                }
                
            }
        });
	    
	    
	    
	    
	    
	    
	    computerStage.addActor(computerTextBox);

	    computerTextLabelContainingTable = new Table();

	    computerTextLabelContainingTable.align(Align.bottom | Align.left);

	    computerTextScroller = new ScrollPane(computerTextLabelContainingTable);
	//    computerTextScroller.setScrollingDisabled(true,false);
	    computerTextScroller.setStyle(parentEngine.scrollPaneStyle);

	    computerTextScrollingTable = new Table();
	    computerTextScrollingTable.setBounds(10, 150, 350, 325);
	    computerTextScrollingTable.add(computerTextScroller).fill().expand();
	    
	    
	    computerStage.addActor(computerTextScrollingTable);

	    
	    
	    alertTextLabelContainingTable = new Table();

	    alertTextLabelContainingTable.align(Align.bottom | Align.left);

	    alertTextScroller = new ScrollPane(alertTextLabelContainingTable);
	//    computerTextScroller.setScrollingDisabled(true,false);
	    alertTextScroller.setStyle(parentEngine.scrollPaneStyle);
	   // alertTextScroller.

	    alertTextScrollingTable = new Table();
	    alertTextScrollingTable.setBounds(575, 0, 340, 520);
	    
	    alertTextScrollingTable.add(alertTextScroller).fill().expand();
	   
	    
	    alertsStage.addActor(alertTextScrollingTable);

	    
	    
	    
	    
	    backgroundStage.addListener(new ClickListener() {
	    	
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	computerStage.unfocusAll();
    	    };
	    	
	    	
	    });
	    
	    addAlert("test");
	    addAlert("testtttt");
	    addAlert("te44sttttt fre");
	    addAlert("why fre");
	    
	    displayDialogue("Ominious Voice" , "Well then...");
	    
	    
	    

	    TextButton BackButton =  new TextButton("Back" , parentEngine.buttonStyle);
	    backgroundStage.addActor(BackButton);
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
	    
		
		batch.begin();
	    
		batch.disableBlending();

		batch.draw(backgroundImage , 0 ,0 , Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
	    
		batch.enableBlending();
		
		batch.end();
		
		computerStage.act();
		alertsStage.act();
		backgroundStage.act();
		
		computerStage.draw();
		alertsStage.draw();
		backgroundStage.draw();
	}

	public void addAlert(String alertText)
	{
		
    	Label newCommandLabel = new Label("VUI ALERT: " + alertText , parentEngine.rawTextStyle);
    	newCommandLabel.setWrap(true);
    	float alertScreenLength = alertTextScrollingTable.getWidth();

    	alertTextLabelContainingTable.row();
    	alertTextLabelContainingTable.add(newCommandLabel).left().minWidth(alertScreenLength-20).fill();

	}
	
	public void displayDialogue(String speaker, String Dialogue)
	{
		
		new dialgoueWindow(Dialogue, speaker, parentEngine.dialogueStyle , backgroundStage, true , 20);

		
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
