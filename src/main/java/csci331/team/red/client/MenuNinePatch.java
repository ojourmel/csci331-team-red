package csci331.team.red.client;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

/**
 * Menu Nine Patch. This is designed just to wrap the nine-patch caller with default values of 
 * all '8's
 * @author Lduperron
 */

/**
 * 
 * CSCI331T LD Pattern:  Singleton
 * The MenuNinePatch is a singleton entity that provides the same instance of a MenuNinePatch after being initialized (or provides the default
 * texture if it's called before being initialized).
 * 
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
        
        public static MenuNinePatch getInstance()
        {
        	if(instance == null)
        	{
        		instance = new MenuNinePatch(ClientEngine.gameTextureManager.get(ClientEngine.Textures
        				.get("dialogueNinePatchTexture")));
        	}
        	
                return instance;
        }
}