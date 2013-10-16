package csci331.team.red.core;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class mainGameScreen implements Screen
{
	final ClientEngine theParent;
	
	private OrthographicCamera camera;
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	
	private Rectangle bucket;
	private Array<Rectangle> raindrops;
	private long lastDropTime;
	Vector3 touchPos;
	
	int dropsGathered;
	
	public mainGameScreen(final ClientEngine gam) 
	{
		this.theParent = gam;
		
		theParent.manager = new AssetManager();
		
		touchPos = new Vector3();
		
		raindrops = new Array<Rectangle>();
		spawnRaindrop();

		
		theParent.manager.load("bucket.png", Texture.class);
		theParent.manager.load("droplet.png", Texture.class);
		theParent.manager.load("drop.wav", Sound.class);
		theParent.manager.load("rainfall.mp3", Music.class);
		
		theParent.manager.finishLoading();
		
		rainMusic = theParent.manager.get("rainfall.mp3" , Music.class);
		dropImage = theParent.manager.get("droplet.png" , Texture.class);
		bucketImage = theParent.manager.get("bucket.png" , Texture.class);
		dropSound = theParent.manager.get("drop.wav" , Sound.class);
		
		rainMusic.setLooping(true);
	    rainMusic.play();
		
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    
	    bucket = new Rectangle();
	    bucket.x = 800 / 2 - 64 / 2;
	    bucket.y = 20;
	    bucket.width = 64;
	    bucket.height = 64;
		
		
		
		
		
		
	}
	
	
	
	
	
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    camera.update();
	    

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
		theParent.font.draw(theParent.batch, "Drops Collected: " + dropsGathered, 0, 480);
	    theParent.batch.draw(bucketImage, bucket.x, bucket.y);
	    	 for(Rectangle raindrop: raindrops) {
	    		 theParent.batch.draw(dropImage, raindrop.x, raindrop.y);
	    		    
	    		    if(raindrop.overlaps(bucket)) {
	    		    	dropsGathered++;
	    		        dropSound.play();
	    		        iter.remove();
	    		     }
	    		 }
	    	 theParent.batch.end();

	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void show()
	{
		rainMusic.play();

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
		    raindrop.x = MathUtils.random(0, Gdx.graphics.getWidth()-64);
		    raindrop.y = Gdx.graphics.getHeight();
		    raindrop.width = 64;
		    raindrop.height = 64;
		    raindrops.add(raindrop);
		    lastDropTime = TimeUtils.nanoTime();
		 }

	 
	 
	 
	 
	 
	
	
}
