package csci331.team.red.core;

import java.util.HashMap;
import java.util.Iterator;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import csci331.team.red.client.KeyboardHandler;
import csci331.team.red.client.MenuNinePatch;
import csci331.team.red.client.Raindrop;
import csci331.team.red.client.dialgoueWindow;
import csci331.team.red.client.tweeningFunctions.RaindropTweener;

public class mainGameScreen implements Screen
{
	public final ClientEngine theParent;
	private OrthographicCamera camera;
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	
	private Texture backgroundImage;
	private Texture foregroundImage;
	
	private Rectangle bucket;
	private Array<Rectangle> raindrops;
	private long lastDropTime;
	Vector3 touchPos;
	
	int dropsGathered;
	public Stage stage;
	public Stage secondStage;
	public Stage uiStage;
	final Skin skin = new Skin();
	
	FPSLogger logger;
	Image droptest;
	
	
	HashMap<Keys, Boolean> keyPressHelpers = new HashMap<Keys, Boolean> ();
	
	MenuNinePatch nine;
	
	BitmapFont font12;
	BitmapFont font25;
	BitmapFont font35;
	
	TextField textfield;
	
	public TextButtonStyle style;
	
	
	public mainGameScreen(final ClientEngine gam) 
	{
		this.theParent = gam;
		
		theParent.manager = new AssetManager();
		theParent.pixmapManager = new AssetManager();
		
		touchPos = new Vector3();
		
		raindrops = new Array<Rectangle>();
		spawnRaindrop();

		
		theParent.manager.load("bucket.png", Texture.class);
		theParent.manager.load("droplet.png", Texture.class);
		theParent.pixmapManager.load("droplet.png" , Pixmap.class);
		theParent.manager.load("drop.wav", Sound.class);
		theParent.manager.load("rainfall.mp3", Music.class);
		theParent.manager.load("nova.jpg", Texture.class);
		theParent.manager.load("windowHall.png", Texture.class);
		
		theParent.manager.finishLoading();
		theParent.pixmapManager.finishLoading();
		
		rainMusic = theParent.manager.get("rainfall.mp3" , Music.class);
		dropImage = theParent.manager.get("droplet.png" , Texture.class);
		bucketImage = theParent.manager.get("bucket.png" , Texture.class);
		dropSound = theParent.manager.get("drop.wav" , Sound.class);
		
		backgroundImage = theParent.manager.get("nova.jpg", Texture.class);
		foregroundImage = theParent.manager.get("windowHall.png", Texture.class);
		
		rainMusic.setLooping(true);
	  //  rainMusic.play();
		
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    
	    bucket = new Rectangle();
	    bucket.x = 800 / 2 - 64 / 2;
	    bucket.y = 20;
	    bucket.width = 64;
	    bucket.height = 64;
		
		
		
		logger = new FPSLogger();
		
		
		stage = new Stage();
		secondStage = new Stage();
		uiStage = new Stage();
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		KeyboardHandler menu = new KeyboardHandler(this);
		
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(secondStage);
		multiplexer.addProcessor(menu);
		Gdx.input.setInputProcessor(multiplexer);
  
        
    	skin.add("test", new Texture("droplet.png"));
        Image droptest = new Image(skin, "test");
        skin.add("default", new LabelStyle(new BitmapFont(), Color.WHITE));

        
    	//stage.addActor(droptest);
        Tween.registerAccessor(Raindrop.class, new RaindropTweener());
    	
    	Raindrop theDrop = new Raindrop(theParent.pixmapManager.get("droplet.png" , Pixmap.class) , theParent.manager.get("droplet.png" , Texture.class) , theParent.Tweenmanager);


    	
    	stage.addActor(theDrop);
    	stage.addActor(new Raindrop());
    	
    	// slow =<  but easy =>
    	// TODO:  Replace this with a pregenerated font package
    	// ...Also replace it with a distance field font.
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Alternity.ttf"));
    	font12 = generator.generateFont(12); // font size 12 pixels
    	font25 = generator.generateFont(25); // font size 25 pixels
    	font35 = generator.generateFont(35); // font size 25 pixels
    	generator.dispose(); // don't forget to dispose to avoid memory leaks!
    	

    	nine = MenuNinePatch.getInstance();
    	
    	// Create a new TextButtonStyle
    	style = new TextButtonStyle();
    	style.font = font25;
    	style.up = new NinePatchDrawable(nine);
    	
    	style.down = style.up;
    	style.over = style.down;
    	
    	// Instantiate the Button itself.

    //	button.setPosition(0, 0);
    	

    	
    	//secondStage.addActor(button);
    	
    	TextFieldStyle textStyle = new TextFieldStyle();
    	textStyle.font = font25;
    	textStyle.fontColor = Color.RED;
    	textStyle.background = new NinePatchDrawable(nine);
    	textStyle.cursor= new NinePatchDrawable(nine);
    	textfield = new TextField("test" , textStyle);
    	
    	textfield.setPosition(500,  500);
    	
    	textfield.setTextFieldListener(new TextFieldListener() {
            public void keyTyped (TextField textField, char key) {
            	
            //	System.out.println(key);
            	
                if(key == '\n' || key == '\r')
                {
                	textField.setText("");
                	textField.getStage().unfocus(textField);
                	
                }
                
            }
        });
    	
    	secondStage.addActor(textfield);
	}
	
	
	
	
	
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    camera.update();
	    
	    logger.log();
	    
	    

	    if(Gdx.input.isTouched()) {
	        
	        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	        camera.unproject(touchPos);
	        bucket.x = touchPos.x - 64 / 2;
	     }
	    
	    
	    
	    if(Gdx.input.isKeyPressed(Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
	    if(Gdx.input.isKeyPressed(Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
	    
//	    if(Gdx.input.isKeyPressed(Keys.D))
//	    {
//	    	
//	    	
//	    	dialgoueWindow button = new dialgoueWindow("They're all around me!", "Bucket:", style , secondStage, true , 20);
//	    	
//	    }
//	    
	    
//	    if(Gdx.input.isKeyPressed(Keys.ENTER))
//	    {
//	    	
//	    	
//	    	dialgoueWindow button = new dialgoueWindow("They're all around me!", "Bucket:", style , secondStage, true , 20);
//	    	
//	    }
//	    
	    
	    
	    
	    if(bucket.x < 0) bucket.x = 0;
	    if(bucket.x > Gdx.graphics.getWidth() - 64) bucket.x = Gdx.graphics.getWidth() - 64;
	    
	    if(TimeUtils.nanoTime() - lastDropTime > 1000000000)
	    {
	    	stage.addActor(new Raindrop());
	    	lastDropTime = TimeUtils.nanoTime();
	    	
	    }
	    	
	    
	    Iterator<Actor> iter = stage.getActors().iterator();
	    while(iter.hasNext()) {
	    	
    	
	       Raindrop raindrop = (Raindrop) iter.next();
	       
	    	if(raindrop.getBoundingRectangle().overlaps(bucket)) {
		    	dropsGathered++;
	//	        dropSound.play();
		        iter.remove();
		     }
	       
	       
	       if(raindrop.getY() + 64 < 0) iter.remove();
	    }
	    
	    
	    
	    
	    theParent.batch.setProjectionMatrix(camera.combined);
	    
	    theParent.batch.begin();
	    
	    	//theParent.batch.draw(backgroundImage , Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
	    	theParent.batch.disableBlending();

	    	theParent.batch.draw(backgroundImage , 0 ,0);
	    
	    	theParent.batch.enableBlending();
	    	
			theParent.font.draw(theParent.batch, "Drops Collected: " + dropsGathered, 0, 480);
			
			theParent.batch.draw(bucketImage, bucket.x, bucket.y);
			
		//	nine.draw(theParent.batch, 0, 110, Gdx.graphics.getWidth()/4, 50);
		//	font35.draw(theParent.batch, "Bucket:", 10, 110+50-10);
		//	nine.draw(theParent.batch, 0, 0, Gdx.graphics.getWidth(), 100);
		//	font25.draw(theParent.batch, "THEY'RE ALL AROUND ME!!!", 10, 100-20);
			
			theParent.batch.end();
		
		
		
        stage.act(Gdx.graphics.getDeltaTime());
        theParent.Tweenmanager.update((float) (Gdx.graphics.getDeltaTime()*.5));
     //   droptest.setBounds(50, 125, 100, 100);
        
        stage.draw();
        
        
        secondStage.act();
        secondStage.draw();
        
        theParent.batch.begin();
    //    theParent.batch.draw(foregroundImage , 0 ,0 , Gdx.graphics.getWidth() , Gdx.graphics.getHeight());

        theParent.batch.end();

		
//	    	for(Rectangle raindrop: raindrops) {
//	    		 theParent.batch.draw(dropImage, raindrop.x, raindrop.y);
//	    		    
//	    		    
//	    		 }
    	

	}

	@Override
	public void resize(int width, int height)
	{
		stage.setViewport(width, height, true);


	}

	@Override
	public void show()
	{
		//rainMusic.play();

	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub

	}

	
	
	 private void spawnRaindrop() {
		    Rectangle raindrop = new Rectangle();
		    raindrop.x = 4;
		    raindrop.y = Gdx.graphics.getHeight();
		    raindrop.width = 64;
		    raindrop.height = 64;
		    raindrops.add(raindrop);
		    lastDropTime = TimeUtils.nanoTime();
		    
		 }

}


