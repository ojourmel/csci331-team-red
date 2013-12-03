package csci331.team.red.client;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

/**
 * Extention to contain an actor.  Handles transparent backgrounds.
 * @author Lduperron
 */

/**
 * 
 * CSCI331T LD Superclass
 * The TransparentActor is my defined superclass (and is and of itself a subclass of Actor)
 * It does such things such as handle collision detection for all TransparentActors that 
 * handles ignoring clicks on the transparent part.  Subclassed actors will further extend 
 * TransparentActor if it's required for additional utility (such as the documents requring an
 * expanded draw function)
 */

/**
 * 
 * CSCI331T LD Encapsulation
 * The TransparentActor encapsulates its pixmap and texture, meaning that once they are set, they are 
 * no longer publicly accessible.  This is because both of these units are only used internally to the
 * TransparentActor; the texture is generated from the Pixmap only on object creation and the Pixmap is
 * used to calculate collisions.  There is no need for external access. 
 */


public class TransparentActor extends Actor 
{

	Pixmap pixmap;
	Texture texture;
	TweenManager tweenmanager;
	
	Vector2 localCords = null;
	


	public TransparentActor (Pixmap incomingPixmap) 
	{
		this(incomingPixmap, null);
	}
	
	public TransparentActor (Pixmap incomingPixmap, TweenManager incomingTweener) 
	{
		pixmap = incomingPixmap;
		tweenmanager = incomingTweener;
		
		/**
		 * 
		 * CSCI331T LD Dynamic Binding
		 * The texture inside of the transparent actor is dynamically bound at runtime on object creation
		 * This is because it is instantiated with the 'new' keyword 
		 */
		
		texture = new Texture(pixmap);
		
		localCords = new Vector2();
			
		texture = new Texture(pixmap);
				
		setWidth(texture.getWidth());
		setHeight(texture.getHeight());
		this.setBounds(0, 0, getWidth() , getHeight());
				
		setTouchable(Touchable.enabled);
		
		this.addListener((new ClickListener()
		{
			
			public void clicked(InputEvent event, float x, float y)
			{
				event.getTarget().toFront();								
				
			}
			
		}));
	}
	
	public void setDragable ()
	{
		
		this.addListener((new DragListener() 
		{ 
			public void touchDragged (InputEvent event, float x, float y, int pointer) 
		 	{ 
					float dx = x-getTouchDownX(); 
					float dy = y-getTouchDownY(); 
					event.getTarget().setPosition(event.getTarget().getX() + dx, event.getTarget().getY() + dy); 
					event.getTarget().toFront();
			 }
		}));
	}
		
	 

	public void draw (SpriteBatch batch, float parentAlpha) 
	{
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		
		batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), texture.getWidth(), texture.getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), false,  false);
	}
	
	public Rectangle getBoundingRectangle()
	{
		
		return this.getBoundingRectangle();
		
	}
	
	
	/**
	 * 
	 * CSCI331T Collision
	 * The hit method overrides the default Actor's hit method in order to provide pixel-perfect collision detection between the mouse and the actor.
	 * The method first checks to see if the mouse is in the bounding box of the actor, then checks to see if the pixel is transparent.  If it is,
	 * it discards the hit event.
	 */
	
	/**
	 * 
	 * CSCI331T LD Overriding 
	 * The hit method for the TransparentActor is overriden to provide additional functionality;
	 * the original hit method only checked the bounding box, however, the overriden hit method also
	 * includes a check to see if the clicked area is on a transparent area of the actor, and if it is,
	 * to discard the hit.
	 */

	
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
				 int pixel = pixmap.getPixel((int)x, (int)(getHeight() - y));
				 
				 if ((pixel & 0x000000ff) != 0) 
				 {
				 	return this;
				 }
				
				
			}
		 }

		 return null;
	}
}