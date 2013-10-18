package csci331.team.red.client;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Quad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import csci331.team.red.client.tweeningFunctions.RaindropTweener;

public class Raindrop extends Actor 
{
       Sprite myself;
       
       Raindrop ref;
       
       static Pixmap alphaMap = null;
       static Texture alphaTexture = null;
       static TweenManager tweenmanager = null;
       
       Vector2 localCords = null;
       
       public Raindrop ()
       {
    	   
    	   this(null , null , null);
    	   

			Tween.to(this, RaindropTweener.POSITION_XY, 2.0f)
			    .target(this.getX(), -100).ease(Quad.IN)
			    .start(tweenmanager);

			
    	   
       }
       
       public Raindrop (Pixmap incomingPixmap , Texture incomingTexture , TweenManager incomingTweener) 
       {
    	   if(alphaMap == null)
    	   {
    		   
    		   alphaMap = incomingPixmap;
    		   alphaTexture = incomingTexture;
    		   tweenmanager = incomingTweener;
    		   
    		   
    	   }
    	   
    	   this.myself = new Sprite(alphaTexture);
    	   ref = this;
    	   localCords = new Vector2();
    	   
       //	this.setY();
       	
       	setWidth(myself.getWidth());
       	setHeight(myself.getHeight());
       	this.setBounds(0, 0, getWidth() , getHeight());
       	
       	this.setPosition(MathUtils.random(0, Gdx.graphics.getWidth()-64), Gdx.graphics.getHeight());
       	//);
       	
	    setTouchable(Touchable.enabled);
	    
//	    addListener(new InputListener() 
//	    {
//	       public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
//	       {
//		       System.out.println("touchDown");
//		       return true;  // must return true for touchUp event to occur
//	       }
//	       
//	       public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
//	       {
//	    	   System.out.println("touchUp");
//	       }
//	   });
	
//	   addListener((new DragListener() 
//	   {
//		    public void touchDragged (InputEvent event, float x, float y, int pointer) 
//		    {
//		            // example code below for origin and position
//		    	super.touchDragged(event, x, y, pointer);
//				setOrigin(Gdx.input.getX(), Gdx.input.getY());
//				setPosition(x, y);
//				System.out.println("touchdragged   " + x + ", " + y);
//		    }
//		    
//	}));
       	
//	    DragAndDrop dragAndDrop = new DragAndDrop();
//	    dragAndDrop.addSource(new Source(this));
	    
	    
	    addListener((new DragListener() 
	    { 
	    	public void touchDragged (InputEvent event, float x, float y, int pointer) 
	    	{ 
	    		tweenmanager.killTarget(ref);
	    		
	    		float dx = x-getTouchDownX(); 
	    		float dy = y-getTouchDownY(); 
	    		ref.setPosition(ref.getX() + dx, ref.getY() + dy); 
	    		ref.toFront();
	    	} 
	    }));
       	
       	
       }

       public void draw (SpriteBatch batch, float parentAlpha) {
               Color color = getColor();
               batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
               batch.draw(myself, getX(), getY());
               myself.setBounds(getX(), getY(), getWidth(), getHeight());
       }
       
       public Rectangle getBoundingRectangle()
       {
    	   
    	   
    	   return myself.getBoundingRectangle();
    	   
    	   
       }
       
       @Override
       public Actor hit(float x, float y, boolean touchable) 
       {
           if (touchable && getTouchable() != Touchable.enabled)
           {
           	return null;
           }
           
           else
           {
				if(x >= 0 && x < getWidth() && y >= 0 && y < getHeight())
				{
				    int pixel = alphaMap.getPixel((int)x, (int)(getHeight() - y));
				    
				    if ((pixel & 0x000000ff) != 0) 
				    {
				    	return this;
				    }
					
					
				}
			
				//return x >= 0 && x < getWidth() && y >= 0 && y < getHeight() ? this : null;
           }

           return null;
           
       }
       
}

