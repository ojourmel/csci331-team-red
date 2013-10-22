package csci331.team.red.clientEngine;

import java.util.HashMap;
import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import csci331.team.red.clientEngine.MainMenuScreen;

/**
 * Main entry point for the game proper.  Handles all shared objects between scenes.
 * @author Lduperron
 */
public class ClientEngine extends Game
{
	// Used for branch logic in our render loop.
	Boolean stillLoading;
	

	// Used for drawing stuffs
	SpriteBatch primarySpriteBatch;

	// Three asset managers.  Loading asset manager is guaranteed to be entirely loaded before the first rendering calls are made.  
	// The other two will be loaded during the loading screen, which is handled in this file.
	AssetManager loadingAssetManager;
	
	// Used for assets that will not be interacted with by the user
	AssetManager gameTextureManager;
	
	// Used for assets that will be interacted with by the user (We need the raw pixmap so we can handle transparency when clicking on them)
	AssetManager gamePixmapManager;
	
	
	HashMap<String, AssetDescriptor<Texture>> preloadTextures;
	HashMap<String, AssetDescriptor<Texture>> Textures;
	HashMap<String, AssetDescriptor<Pixmap>> Pixmaps;
	
	// Used to display the 'loading' screen
	FreeTypeFontGenerator generator;
	BitmapFont loadingFont;
	String loadingstr;
	
	
	// Other scenes in the game.
	MainMenuScreen mainMenuScreen;
	ConnectingScreen connectingScreen;
	HostingScreen hostingScreen;
	DatabaseAgentScreen databaseAgentScreen;
	FieldAgentScreen fieldAgentScreen;
	SettingsScreen settingsScreen;
	
	
	// Font used in the game
	BitmapFont gameFont;
	
	// Styles used in the game
	TextButtonStyle dialogueStyle;
	TextButtonStyle buttonStyle;
	TextButtonStyle radioButtonStyle;
	
	TextFieldStyle inputStyle;

	
	
	
	LabelStyle rawTextStyle;
	
	ScrollPaneStyle scrollPaneStyle;
	
	// Button ninepatch
	MenuNinePatch menuNinePatch;
	NinePatchDrawable ninePatchDrawable;	
		
	@Override
	public void create() 
	{
		// DUN DUN DUN.  Disables compatibility with older (really old) openGL ]
		// implementations.  This setting disables the requirement for loaded images
		// to be a strict power of two.  Anything that supports openGL 2.0 (which should
		// be most things today) shouldn't have a problem.  If we do encounter any hardware
		// that errors out on it, we should set this to true again and then just refactor
		// all our images.  Which shouldn't be that much work to do in bulk, just annoying.
		Texture.setEnforcePotImages(false);
		Tween.setCombinedAttributesLimit(4);
		

		primarySpriteBatch = new SpriteBatch();
		
		stillLoading = true;
		loadingAssetManager = new AssetManager();
		gameTextureManager = new AssetManager();
		gamePixmapManager = new AssetManager();
		preloadTextures = new HashMap<String, AssetDescriptor<Texture>>() ;
		Textures = new HashMap<String, AssetDescriptor<Texture>>() ;
		Pixmaps = new HashMap<String, AssetDescriptor<Pixmap>>() ;
		
		
		
		
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/amiga4ever.ttf"));
		loadingFont = generator.generateFont(25);

		
		
		// Backgrounds...
		Textures.put("level1fieldbg" ,new AssetDescriptor<Texture>("backgrounds/level1fieldagent.jpg" , Texture.class) );
		Textures.put("level1databasebg", new AssetDescriptor<Texture>("backgrounds/level1databaseagent.png" , Texture.class));
		Textures.put("level2fieldbg", new AssetDescriptor<Texture>("backgrounds/level2fieldagent.png" , Texture.class));
		
		// Static Props...
		Textures.put("clipboard", new AssetDescriptor<Texture>("props/clipboardtransparent.png" , Texture.class));

		// UI elements...
		Textures.put("dialogueNinePatchTexture", new AssetDescriptor<Texture>("UI/dialogueBoxNinePatch.png" , Texture.class));

		
		
		// Characters...
		Pixmaps.put("level1thug", new AssetDescriptor<Pixmap>("characters/level1thug.png" , Pixmap.class));
		Pixmaps.put("level1male", new AssetDescriptor<Pixmap>("characters/maleExtra.png" , Pixmap.class));
		Pixmaps.put("level1female", new AssetDescriptor<Pixmap>("characters/femaleExtra.png" , Pixmap.class));

		// Dynamic Props...
		Pixmaps.put("goldenTicket", new  AssetDescriptor<Pixmap>("props/ticket.png" , Pixmap.class));

		
		
		
		
		
		
		
		
		
		
		
		// Put things the loadingAssetManager needs to load here...
		for (AssetDescriptor<Texture> theTexture : preloadTextures.values()) {
			loadingAssetManager.load(theTexture);
		}
		
		
		// Put things the gameTextureManager needs to load here...
		
		for (AssetDescriptor<Texture> theTexture : Textures.values()) {
			gameTextureManager.load(theTexture);
		}
		
		// Put things the gamePixmapManager needs to load here...
		
		for (AssetDescriptor<Pixmap> thePixmap : Pixmaps.values()) {
			gamePixmapManager.load(thePixmap);
		}

		
		
		// Make sure the loadingAssetManager is at least done
		loadingAssetManager.finishLoading();
		
	}

	@Override
	public void dispose() {

	}

	@Override
	public void render() {

		if(stillLoading)
		{

			Gdx.graphics.getGL20().glClearColor( 0, 0, 0, 1 );
			Gdx.graphics.getGL20().glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
			
			if(!gameTextureManager.update())
			{
				
				loadingstr = "Loading Textures - " + gameTextureManager.getProgress()*100;
		        primarySpriteBatch.begin();
		        
		        loadingFont.draw(primarySpriteBatch, loadingstr , Gdx.graphics.getWidth() / 2 - loadingFont.getBounds(loadingstr).width/2, Gdx.graphics.getHeight()/2);
		        
		        primarySpriteBatch.end();
		        
		        return;
			}
				
			if(!gamePixmapManager.update())
			{
				loadingstr = "Loading Pixmaps - " + gamePixmapManager.getProgress()*100;
				
		        primarySpriteBatch.begin();
		        
		        loadingFont.draw(primarySpriteBatch,loadingstr , (Gdx.graphics.getWidth()/2)-loadingFont.getBounds(loadingstr).width/2, Gdx.graphics.getHeight()/2);
		        
		        primarySpriteBatch.end();
		        
				return;
			}

			
			
			// Switch over to calling super's rendering for rendering screens
			stillLoading = false;
			
			// Do any last initializations that we needed to load files to do
			finishInit();
			
			// Go to the main menu.
			switchToNewScreen(ScreenEnumerations.MainMenu);

		}
		else
		{
			super.render();
			
		}
	}
	
	/**
	 * Finishes init on variables that required resources to be fully loaded.
	 */
	private void finishInit()
	{
		
    	menuNinePatch = new MenuNinePatch(gameTextureManager.get(Textures.get("dialogueNinePatchTexture")));
    	ninePatchDrawable = new NinePatchDrawable(MenuNinePatch.getInstance());
    	
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/amiga4ever.ttf"));		
		gameFont = generator.generateFont(25);
		
		dialogueStyle  = new TextButtonStyle();
		dialogueStyle.font = gameFont;
		dialogueStyle.up = ninePatchDrawable;
    	
		dialogueStyle.down = dialogueStyle.up;
		dialogueStyle.over = dialogueStyle.down;
    	
    	buttonStyle =  new TextButtonStyle();
    	buttonStyle.font = gameFont;
    	buttonStyle.up = ninePatchDrawable;
    	
    	buttonStyle.down = buttonStyle.up;
    	buttonStyle.over = buttonStyle.up;
    	buttonStyle.fontColor = Color.WHITE;
    	buttonStyle.downFontColor = Color.LIGHT_GRAY;
    	
    	radioButtonStyle =  new TextButtonStyle();
    	radioButtonStyle.font = gameFont;
    	radioButtonStyle.up = ninePatchDrawable;
    	radioButtonStyle.checkedFontColor = Color.RED;
    	radioButtonStyle.fontColor = Color.WHITE;
    	radioButtonStyle.down = buttonStyle.up;
    	radioButtonStyle.over = buttonStyle.up;
    	

    	inputStyle = new TextFieldStyle();
    	inputStyle.font = gameFont;
    	inputStyle.fontColor = Color.RED;
    	inputStyle.background = ninePatchDrawable;
    	inputStyle.cursor= ninePatchDrawable;
    	
    	rawTextStyle = new LabelStyle();
    	rawTextStyle.font = gameFont;
    	rawTextStyle.fontColor = Color.BLACK;
    	
    	scrollPaneStyle = new ScrollPaneStyle();
    	scrollPaneStyle.background = ninePatchDrawable;
    	scrollPaneStyle.hScroll =  ninePatchDrawable;
    	scrollPaneStyle.hScrollKnob =  ninePatchDrawable;
    	scrollPaneStyle.vScroll =  ninePatchDrawable;
    	scrollPaneStyle.vScrollKnob =  ninePatchDrawable;
    	
    	
		return;
	}
	
	/** 
	 * Given a ScreenEnumeration (which is really just a list of all possible screens)
	 * it ensures it has been generated (lazy init) and then switches to it.
	 * @param newLevel
	 */
	
	public void switchToNewScreen(ScreenEnumerations newLevel)
	{
		switch (newLevel)
		{
		case ClientEngine:
			this.setScreen(null);
			
			
			
		case MainMenu:
			if(mainMenuScreen == null)
			{
				mainMenuScreen = new MainMenuScreen(this);
			}
			this.setScreen(mainMenuScreen);
			break;
			
			
			
			
		case Hosting:
			if(hostingScreen == null)
			{
				hostingScreen = new HostingScreen(this);
			}
			this.setScreen(hostingScreen);
			break;
			
			
			
		case Connecting:
			if(connectingScreen == null)
			{
				connectingScreen = new ConnectingScreen(this);
			}
			this.setScreen(connectingScreen);
			break;
			
			
		case Settings:
			if(settingsScreen == null)
			{
				settingsScreen = new SettingsScreen(this);
			}
			this.setScreen(settingsScreen);
			break;
			
			
		case FieldAgent:
			if(fieldAgentScreen == null)
			{
				fieldAgentScreen = new FieldAgentScreen(this);
			}
			this.setScreen(fieldAgentScreen);
			break;
			
			
			
			
		case DatabaseAgent:
			if(databaseAgentScreen == null)
			{
				databaseAgentScreen = new DatabaseAgentScreen(this);
			}
			this.setScreen(databaseAgentScreen);
			break;
			
			
			default:
				return;
		}
		
		return;
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	

	
	/** 
	 * Entry point for the program.  Sets width/height et al.
	 * @param args
	 */
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		// System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		cfg.title = "Final Project Prototype";
		cfg.useGL20 = true;
		cfg.width = 1024; // 1200
		cfg.height = 768; // 675
		
		
		cfg.vSyncEnabled = false;
		cfg.resizable = false;
		new LwjglApplication(new ClientEngine(), cfg);
	}
}