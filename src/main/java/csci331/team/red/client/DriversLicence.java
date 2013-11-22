package csci331.team.red.client;

import javax.management.RuntimeErrorException;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import csci331.team.red.shared.Face;

public class DriversLicence extends TransparentActor
{
	
    static BitmapFont font;
    String DriverName;
    String DateOfBirth;
    Texture LicenceFace;
    
    private static final int pictureX = 25;
    private static final int pictureY = 160;
	
    private static final int DriverNameX = 200;
    private static final int DriverNameY = 275;
    
    private static final int DateOfBirthX = 200;
    private static final int DateOfBirthY = 250;
    
	public DriversLicence(Pixmap incomingPixmap , TweenManager manager , String[] TextFields , Face face)
	{
		super(incomingPixmap, manager);
		
		// All drivers licences use the same font, and it's made static to prevent from having to regenerate copies.
		// Could maybe be generated during first init, but it's fine here for now.
		if(font == null)
		{
			font = ClientEngine.generator.generateFont(20);
		}
	    font.setColor(0,0,0 , 1);  
	    if(TextFields.length == 2)
	    {
		    DriverName = "Name: " + TextFields[0];
		    DateOfBirth = "DoB:  " + TextFields[1];
	    }
	    else
	    {
	    	throw new RuntimeErrorException(null);
	    	
	    }
	    LicenceFace = new Texture(ClientEngine.gamePixmapManager.get(ClientEngine.PersonFaces.get(face)));
	    
	}
	
    public void draw (SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        
        float y = getScaleY();
        
        font.setScale(getScaleX(), getScaleY());
        font.draw(batch , DriverName,
        		getX() +  DriverNameX * getScaleX(),
        		getY() + DriverNameY* getScaleY());
        
        font.draw(batch , DateOfBirth,
        		getX() +  DateOfBirthX * getScaleX(),
        		getY() + DateOfBirthY* getScaleY());
        
        batch.draw(LicenceFace, getX() + pictureX* getScaleX(), getY() + pictureY * getScaleY(), 125 * getScaleX(), 125 * getScaleY());
        
      //  batch.draw(texture, getX(), getY());
}
	
}
