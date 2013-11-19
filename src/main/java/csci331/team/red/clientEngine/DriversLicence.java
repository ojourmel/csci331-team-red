package csci331.team.red.clientEngine;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DriversLicence extends TransparentActor
{
	
    BitmapFont font;
    String DriverName;

	public DriversLicence(Pixmap incomingPixmap)
	{
		this(incomingPixmap, null , "ERR");

	}
	
	public DriversLicence(Pixmap incomingPixmap , TweenManager manager , String text)
	{
		super(incomingPixmap, manager);
		
		
	    font = new BitmapFont();
	    font.setColor(0,0,0 , 1);  
	    DriverName = text;
	    
	}
	
    public void draw (SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        
        float y = getScaleY();
        
        font.setScale(getScaleX(), getScaleY());
        font.draw(batch,DriverName,getX() ,getY() + 100*y);
        
      //  batch.draw(texture, getX(), getY());
}
	
}
