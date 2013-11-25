package csci331.team.red.client;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import csci331.team.red.server.ServerEngine;
import csci331.team.red.shared.Alert;
import csci331.team.red.shared.Dialogue;
import csci331.team.red.shared.Message;
import csci331.team.red.shared.Query;

/**
 * Scene responsible for the database roll.
 * @author Lduperron
 */

public class DatabaseAgentScreen implements Screen 
{
	ClientEngine parentEngine;
	
	Texture backgroundImage;
	
	private OrthographicCamera camera;
	
	SpriteBatch batch;
	
	Stage computerStage;
	TextField computerTextBox;

	Music BackgroundMusic;
	
	ScrollPane computerTextScroller;
	Table computerTextLabelContainingTable;
	Table computerTextScrollingTable;
	
	
	
	ScrollPane alertTextScroller;
	Table alertTextLabelContainingTable;
	Table alertTextScrollingTable;
	
	
	Stage alertsStage;
	
	Stage dialogueStage;
	
	Stage backgroundStage;
	
	InputMultiplexer multiplexer;
	
	Boolean displayingAlerts;
	DatabaseDialogueCallback<Callback> callbackObject;
	
	
	long lastAlertTime;
	Array<Alert> potentialAlerts = new Array<Alert>();

	public DatabaseAgentScreen(ClientEngine parent)
	{
		
		callbackObject = new DatabaseDialogueCallback<Callback>(this);
		
//		DatabaseDialogCallbacks.setEntity(this);
		
		
		// Sets up links to our parent
		parentEngine = parent;
		batch = ClientEngine.primarySpriteBatch;
		
		
		// Loads the background image
		backgroundImage = ClientEngine.gameTextureManager.get(ClientEngine.Backgrounds.get(parentEngine.currentLevel.getDatabase()));
		
		// and the background music
		BackgroundMusic = ClientEngine.gameMusicManager.get(ClientEngine.BackgroundMusic.get(parentEngine.currentLevel.getSoundTrack()));
		BackgroundMusic.setLooping(true);
		BackgroundMusic.play();
		
		// Sets up the camera
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    	    
	    // Initializes a stage to hold our buttons
	    computerStage = new Stage();
	    alertsStage = new Stage();
	    backgroundStage = new Stage();
	    dialogueStage = new Stage();
	    // Sets up an input multiplexer to handle our input to the buttons
	    
	    multiplexer = new InputMultiplexer();
	    multiplexer.addProcessor(parentEngine.uiControlHandler);
	    multiplexer.addProcessor(dialogueStage);
	    multiplexer.addProcessor(computerStage);
	    multiplexer.addProcessor(alertsStage);
	    multiplexer.addProcessor(backgroundStage);
	    
	    computerTextBox = new TextField("" , parentEngine.inputStyle);
	    //computerTextBox.setPosition(410, 0);
	    //computerTextBox.setWidth(240);
	    DatabaseBoundingBoxes bounds = parentEngine.currentLevel.getBounds();
	    computerTextBox.setBounds(
	    		bounds.ComputerEntryBoundingBox.x,
	    		bounds.ComputerEntryBoundingBox.y,
	    		bounds.ComputerEntryBoundingBox.width,
	    		bounds.ComputerEntryBoundingBox.height);
	    
	    //computerTextBox.setBounds(410, 0, 240, 47);
	    
    	
	    computerTextBox.setTextFieldListener(new TextFieldListener() 
	    {
            public void keyTyped (TextField textField, char key) {
            	
                if(key == '\n' || key == '\r')
                {
                	
                	Label newCommandLabel = new Label(textField.getText() + "\r\n\r\n", parentEngine.rawTextStyle);
                	newCommandLabel.setWrap(true);
                	//String computerResponse = tutorialComputerSearch(textField.getText().trim());
                	ClientEngine.network.send(Message.DBQUERY, new Query(textField.getText().trim()));
                	
                	
                	textField.setText("");
                	
                	
                	
            	    computerTextLabelContainingTable.add(newCommandLabel).left().minWidth(computerTextScrollingTable.getWidth()-20).fill();
            	   


            	    computerTextLabelContainingTable.row();
            	    
            	    computerTextScroller.layout();
            	    computerTextLabelContainingTable.layout();
            	    computerTextScrollingTable.layout();

            	    computerTextScroller.setScrollPercentY(100);
            	    
            	    

                	
                }
                
            }
        });
	    
	    
	    
	    
	    
	    
	    computerStage.addActor(computerTextBox);

	    computerTextLabelContainingTable = new Table();

	    computerTextLabelContainingTable.align(Align.bottom | Align.left);

	    computerTextScroller = new ScrollPane(computerTextLabelContainingTable);
	    computerTextScroller.setStyle(parentEngine.scrollPaneStyle);
	    computerTextScroller.setScrollBarPositions(false, true);
	    

	    computerTextScrollingTable = new Table();
	    computerTextScrollingTable.setBounds(
	    		bounds.ComputerDisplayBoundingBox.x,
	    		bounds.ComputerDisplayBoundingBox.y,
	    		bounds.ComputerDisplayBoundingBox.width,
	    		bounds.ComputerDisplayBoundingBox.height);
	    computerTextScrollingTable.add(computerTextScroller).fill().expand();
	    
	    
	    computerStage.addActor(computerTextScrollingTable);

	    
	    
	    alertTextLabelContainingTable = new Table();

	    alertTextLabelContainingTable.align(Align.bottom | Align.left);

	    alertTextScroller = new ScrollPane(alertTextLabelContainingTable);
	    alertTextScroller.setScrollingDisabled(true,true);


	    alertTextScrollingTable = new Table();
	    alertTextScrollingTable.setBounds(
	    		bounds.AlertDisplayBoundingBox.x,
	    		bounds.AlertDisplayBoundingBox.y,
	    		bounds.AlertDisplayBoundingBox.width,
	    		bounds.AlertDisplayBoundingBox.height);
	    
	    alertTextScrollingTable.add(alertTextScroller).fill().expand();
	   
	    
	    alertsStage.addActor(alertTextScrollingTable);

	    displayingAlerts = false;
	    
	    
	    
	    backgroundStage.addListener(new ClickListener() {
	    	
    		
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) 
    	    {
    	    	
    	    	
    	    	
    	    	computerStage.unfocusAll();
    	    };
	    	
	    	
	    });
	    

//
//	    // used in tutorial dialogue
//	    String[][] strarr = {
//	    		{"Well then... (Click to continue)" , "Ominious Voice"},
//	    		{"Welcome to your first day on the job.", "Ominious Voice"},
//	    		{"Oh no!  The alerts start rolling in!", "Ominious Voice"},
//	    		{"Some of them we can ignore, but some of them we should warn our partner about.  Up to you to decide which is which.", "Ominious Voice"},
//	    		{"Others are suspicious.  Like that 'Mary Test' one.", "Ominious Voice"},
//	    		{"We should do a bit of research on her.\nType 'search prism mary test'\non your phone.", "Ominious Voice"},
//	    		{"I assume you did it, given I'm just a voice.  That's an inconsistancy!  We should warn our partner about that too.", "Ominious Voice"},
//	    		{"Your partner will also be reporting suspicious data to you to look up.  For example..." , "Ominious Voice"},
//	    		{"Hey, can you look up DL '123456789' for me, buddy?" , "Jerk"},
//	    		{"These can occasionally bring up a large amount of infomation, and again, you must decide what is important to relay." , "Ominious Voice"},
//	    		{"I have no more guidance to give you." , "Ominious Voice"},
//	    		{"Mom, get out of my office..." , "You"},
//	    		
//	    };
	    
	    
	    
//	    DBCallback[] callarr = {null, DBCallback.startAlerts, null , DBCallback.MaryTestAlert , null, DBCallback.startAlerts , null , null, null, null, null , null};
//	    Dialogue[] d = Dialogue.returnDialogArray(strarr , callarr);
//	    displayDialogue(d);

//	    TextButton BackButton =  new TextButton("Back" , parentEngine.buttonStyle);
//	    backgroundStage.addActor(BackButton);
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
	    
	    
	    
	    
//	    
//	    lastAlertTime = TimeUtils.millis();
//	    
//	    // This will be handled by the server... hardcoded here now just to test
//	    String[] test = {"Person of interest spotted" , "Person of interest spotted" , "Person of interest spotted" ,
//	    		"Person of interest spotted" , "Person of interest spotted" , "Hostile individual wearing 'thug life' tshirt",
//	    "VUI Alerts System Reset", "Potential threat from person here for 'two weeks'"		
//	    
//	    };
//		potentialAlerts = new Array<String> (test);

	    
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
		
		dialogueStage.act();
		dialogueStage.draw();
		
	    if(TimeUtils.millis() - lastAlertTime > 2000  && displayingAlerts)
	    {
	    	addAlert();
	    	lastAlertTime = TimeUtils.millis();
	    	
	    	
	    }
	}
	

	
	public void addAlert()
	{
	
		addAlert(potentialAlerts.random());
		
		
	}
	
	
	public void addAlert(Alert alert)
	{
		
    	Label newCommandLabel = new Label(alert.alertFrom + " " + alert.alertText , parentEngine.rawTextStyle);
    	newCommandLabel.setWrap(true);
    	float alertScreenLength = alertTextScrollingTable.getWidth();

    	alertTextLabelContainingTable.row();
    	alertTextLabelContainingTable.add(newCommandLabel).left().minWidth(alertScreenLength-20).fill();
    	
	    alertTextScrollingTable.layout();
	    alertTextLabelContainingTable.layout();
	    alertTextScroller.layout();
	    alertTextScroller.setScrollPercentY(100);

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
				DialogueWindow iteratorNew = new DialogueWindow (dialogueArray[i].getDialogue(), dialogueArray[i].getSpeaker(), parentEngine.dialogueStyle , dialogueStage, true , 20 , false, iteratorOld , dialogueArray[i].getCallbackCode(), callbackObject);
				iteratorOld = iteratorNew;
			}
		}
		new DialogueWindow(dialogueArray[0].getDialogue(), dialogueArray[0].getSpeaker(), parentEngine.dialogueStyle , dialogueStage, true , 20 , true, iteratorOld , dialogueArray[0].getCallbackCode(), callbackObject);
		
	}
	
	public void displayComputerResponse(String responseText)
	{
    	Label newResponseLabel = new Label(responseText + "\r\n\r\n" , parentEngine.rawTextStyle);
    	newResponseLabel.setWrap(true);
    	
	    computerTextLabelContainingTable.add(newResponseLabel).left().minWidth(computerTextScrollingTable.getWidth()-20).fill();
  	   

	    computerTextLabelContainingTable.row();
	    
	    computerTextScroller.layout();
	    computerTextLabelContainingTable.layout();
	    computerTextScrollingTable.layout();

	    computerTextScroller.setScrollPercentY(100);
		
	}
	
	public void changeAlertStage(Boolean state)
	{
		displayingAlerts = state;
		
	}
	
	/**
	 * TODO: This will be implemented in {@link ServerEngine}
	 * Currently used for tutorial
	 */
	public String tutorialComputerSearch(String SearchString)
	{
		String[] tokenizedString = SearchString.split("\\s" , 3);
		
		if(tokenizedString.length < 3)
		{
			
			return "Failed to parse command!";
			
		}
	    
		if(!tokenizedString[0].equalsIgnoreCase("search"))
		{
			System.out.println(tokenizedString[0]);
			
			return "Failed to parse command!";
			
			
		}
		
		else if(!(tokenizedString[1].equalsIgnoreCase("prism")))
		{
			return "Failed to acquire hook to search database!";
			
			
		}
		
		else if(!tokenizedString[2].equalsIgnoreCase("Mary Test") && !tokenizedString[2].equalsIgnoreCase("123456789") )
		{
			return "Error:  " + tokenizedString[2] + " does not exist.";
			
			
		}
		
		else
		{
			if(tokenizedString[2].equalsIgnoreCase("Mary Test"))
					{
			
			return "Mary Test --\n" +
					"Age: 22\n" +
					"Status: Deceased";
					}
			else
			{
				return "DL 123456789\n" +
						"Name: John Smythe\n" +
						"Outstanding Arrest Warrants";
						
				
				
				
			}
			
		}
		
		
		
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
		parentEngine.databaseAgentScreen = null;
	}

}
