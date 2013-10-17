package csci331.team.red.core;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import csci331.team.red.client.Raindrop;

public class mainGameScreen implements Screen
{
	final ClientEngine theParent;
	private OrthographicCamera camera;
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	
	private Texture backgroundImage;
	
	private Rectangle bucket;
	private Array<Rectangle> raindrops;
	private long lastDropTime;
	Vector3 touchPos;
	
	int dropsGathered;
	Stage stage;
	final Skin skin = new Skin();
	
	FPSLogger logger;
	Image droptest;
	
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
		
		theParent.manager.finishLoading();
		theParent.pixmapManager.finishLoading();
		
		rainMusic = theParent.manager.get("rainfall.mp3" , Music.class);
		dropImage = theParent.manager.get("droplet.png" , Texture.class);
		bucketImage = theParent.manager.get("bucket.png" , Texture.class);
		dropSound = theParent.manager.get("drop.wav" , Sound.class);
		
		backgroundImage = theParent.manager.get("nova.jpg", Texture.class);
		
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
        Gdx.input.setInputProcessor(stage);
        
        
        
    	skin.add("test", new Texture("droplet.png"));
        Image droptest = new Image(skin, "test");
        skin.add("default", new LabelStyle(new BitmapFont(), Color.WHITE));

        
    	//stage.addActor(droptest);
    	
    	
    	Raindrop theDrop = new Raindrop(theParent.pixmapManager.get("droplet.png" , Pixmap.class));

    	Raindrop theDrop2 = new Raindrop(theParent.pixmapManager.get("droplet.png" , Pixmap.class));
   	
    	
    	stage.addActor(theDrop);
    	stage.addActor(theDrop2);
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

	    if(Gdx.input.isKeyPressed(Keys.SPACE))
	    {
	    	
	    	theParent.setScreen(theParent.title);
	    	
	    }
	    
	    if(bucket.x < 0) bucket.x = 0;
	    if(bucket.x > Gdx.graphics.getWidth() - 64) bucket.x = Gdx.graphics.getWidth() - 64;
	    
	    if(TimeUtils.nanoTime() - lastDropTime > 1) spawnRaindrop();
	    
	    Iterator<Rectangle> iter = raindrops.iterator();
	    while(iter.hasNext()) {
	       Rectangle raindrop = iter.next();
	       raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
	       if(raindrop.y + 64 < 0) iter.remove();
	    }
	    
	    
	    
	    
	    
	    theParent.batch.setProjectionMatrix(camera.combined);
	    
	    theParent.batch.begin();
	    
	    	//theParent.batch.draw(backgroundImage , Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
	    	theParent.batch.disableBlending();

	    	theParent.batch.draw(backgroundImage , 0 ,0);
	    
	    	theParent.batch.enableBlending();
	    	
			theParent.font.draw(theParent.batch, "Drops Collected: " + dropsGathered, 0, 480);
			
			theParent.batch.draw(bucketImage, bucket.x, bucket.y);
			
			
		theParent.batch.end();
		
        stage.act(Gdx.graphics.getDeltaTime());

     //   droptest.setBounds(50, 125, 100, 100);
        
        stage.draw();

		
//	    	for(Rectangle raindrop: raindrops) {
//	    		 theParent.batch.draw(dropImage, raindrop.x, raindrop.y);
//	    		    
//	    		    if(raindrop.overlaps(bucket)) {
//	    		    	dropsGathered++;
//	    	//	        dropSound.play();
//	    		        iter.remove();
//	    		     }
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


