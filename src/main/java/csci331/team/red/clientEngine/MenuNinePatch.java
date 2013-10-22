package csci331.team.red.clientEngine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

/**
 * Menu Nine Patch.  Based off of code found on the Internet, this is 
 * designed just to wrap the nine-patch caller with default values of 
 * all '8's
 * @author Lduperron
 */
public class MenuNinePatch extends NinePatch {
        private static MenuNinePatch instance;
        
        MenuNinePatch(Texture t)
        {
        	
        	super(t, 8, 8, 8, 8);
        	instance = this;
        }
        
        public void init(Texture t)
        {
        	
        	instance = new MenuNinePatch(t);
        }
        
        public static MenuNinePatch getInstance(){
                return instance;
        }
}