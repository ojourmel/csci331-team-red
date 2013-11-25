package csci331.team.red.client;

import static csci331.team.red.dao.CharacterDAO.ADDRESS;
import static csci331.team.red.dao.CharacterDAO.CITY;
import static csci331.team.red.dao.CharacterDAO.COUNTRY;
import static csci331.team.red.dao.CharacterDAO.DOB;
import static csci331.team.red.dao.CharacterDAO.DRIVERSID;
import static csci331.team.red.dao.CharacterDAO.FIRSTNAME;
import static csci331.team.red.dao.CharacterDAO.GENDER;
import static csci331.team.red.dao.CharacterDAO.LASTNAME;
import static csci331.team.red.dao.CharacterDAO.POSTAL;
import static csci331.team.red.dao.CharacterDAO.REGION;

import java.util.HashMap;

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
    String Address;
    String Address2;
    String City;
    String Postal;
    String Gender;
    String DriversID;
    String Country;
    
    
    private static final int pictureX = 25;
    private static final int pictureY = 160;
	
    private static final int DriverNameX = 190;
    private static final int DriverNameY = 275;
    
    private static final int DateOfBirthX = 190;
    private static final int DateOfBirthY = 250;
    
    private static final int AddressX = 190;
    private static final int AddressY = 225;
    
    private static final int Address2X = 350;
    private static final int Address2Y = 200;
    
    private static final int CityX = 190;
    private static final int CityY = 175;
    
    private static final int PostalX = 190;
    private static final int PostalY = 150;
    
    
    private static final int GenderX = 190;
    private static final int GenderY = 125;
    
    private static final int DriversIDX = 190;
    private static final int DriversIDY = 100;
    
    private static final int CountryX = 190;
    private static final int CountryY = 75;
    
	public DriversLicence(Pixmap incomingPixmap , TweenManager manager , HashMap<String, String> characterAttributes , Face face)
	{
		super(incomingPixmap, manager);
		
		// All drivers licences use the same font, and it's made static to prevent from having to regenerate copies.
		// Could maybe be generated during first init, but it's fine here for now.
		if(font == null)
		{
			font = ClientEngine.generator.generateFont(20);
		}
	    font.setColor(0,0,0 , 1);  

		    DriverName = "Name: " + characterAttributes.get(FIRSTNAME) + " " + characterAttributes.get(LASTNAME);
		    DateOfBirth = "DoB:  " + characterAttributes.get(DOB);
		    Address = "Address: " + characterAttributes.get(ADDRESS).substring( 0 ,Math.min(characterAttributes.get(ADDRESS).length(), 13));
		    Address2 = characterAttributes.get(ADDRESS).substring(Math.min(characterAttributes.get(ADDRESS).length(), 13) ,
		    																Math.min(characterAttributes.get(ADDRESS).length(), 30));
		    City = "City: " + characterAttributes.get(CITY) + "," + characterAttributes.get(REGION);
		    Postal = "Postal: " + characterAttributes.get(POSTAL);
		    Gender = "Gender: " + characterAttributes.get(GENDER);
		    DriversID = "Drivers ID #: " + characterAttributes.get(DRIVERSID);
		    Country = "Country: " + characterAttributes.get(COUNTRY);
		
		    
	    LicenceFace = new Texture(ClientEngine.gamePixmapManager.get(ClientEngine.PersonFaces.get(face)));
	    
	}
	
    public void draw (SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        
        font.setScale(getScaleX(), getScaleY());
        font.draw(batch , DriverName,
        		getX() +  DriverNameX * getScaleX(),
        		getY() + DriverNameY* getScaleY());
        
        font.draw(batch , DateOfBirth,
        		getX() +  DateOfBirthX * getScaleX(),
        		getY() + DateOfBirthY* getScaleY());
        
        font.draw(batch , Gender,
        		getX() +  GenderX * getScaleX(),
        		getY() + GenderY* getScaleY());
        
        font.draw(batch , Address,
        		getX() +  AddressX * getScaleX(),
        		getY() + AddressY* getScaleY());
        
        font.draw(batch , Address2,
        		getX() +  Address2X * getScaleX(),
        		getY() + Address2Y* getScaleY());
        
        font.draw(batch , City,
        		getX() +  CityX * getScaleX(),
        		getY() + CityY* getScaleY() , 0, Math.max(City.length(), 22));
        
        
        font.draw(batch , Postal,
        		getX() +  PostalX * getScaleX(),
        		getY() + PostalY* getScaleY());
        
        font.draw(batch , DriversID,
        		getX() +  DriversIDX * getScaleX(),
        		getY() + DriversIDY* getScaleY());
        
        font.draw(batch , Country,
        		getX() +  CountryX * getScaleX(),
        		getY() + CountryY* getScaleY());
        
        batch.draw(LicenceFace, getX() + pictureX* getScaleX(), getY() + pictureY * getScaleY(), 125 * getScaleX(), 125 * getScaleY());
        
      //  batch.draw(texture, getX(), getY());
}
	
}
