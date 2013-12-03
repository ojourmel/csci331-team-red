package csci331.team.red.client;

import java.io.IOException;
import java.util.HashMap;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
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

import csci331.team.red.network.NetClient;
import csci331.team.red.server.ServerEngine;
import csci331.team.red.shared.Alert;
import csci331.team.red.shared.Background;
import csci331.team.red.shared.Dialogue;
import csci331.team.red.shared.Document;
import csci331.team.red.shared.Face;
import csci331.team.red.shared.Incident;
import csci331.team.red.shared.Level;
import csci331.team.red.shared.Message;
import csci331.team.red.shared.PersonPicture;
import csci331.team.red.shared.Result;
import csci331.team.red.shared.Role;
import csci331.team.red.shared.SoundTrack;

/**
 * Main entry point for the game proper. Handles all shared objects between
 * scenes.
 * 
 * @author Lduperron
 */


/**
 * 
 * CSCI331T LD Interface
 * The ClientEngine is the main interface for the clientside / graphics portion of the game to interface 
 * with the server.  It exposes the public methods necessary for the server to control the game client via
 * the network.
 */

public class ClientEngine extends Game {
	// Server used for hosting games
	ServerEngine server;

	// Used to connect to the server
	static NetClient network;

	// Used for branch logic in our render loop.
	Boolean stillLoading;

	// Used for drawing stuffs
	static SpriteBatch primarySpriteBatch;

	// Three asset managers. Loading asset manager is guaranteed to be entirely
	// loaded before the first rendering calls are made.
	// The other two will be loaded during the loading screen, which is handled
	// in this file.
	static AssetManager loadingAssetManager;

	// Used for assets that will not be interacted with by the user
	static AssetManager gameTextureManager;

	// Used for assets that will be interacted with by the user (We need the raw
	// pixmap so we can handle transparency when clicking on them)
	static AssetManager gamePixmapManager;

	// Used for both background music
	static AssetManager gameMusicManager;

	static HashMap<String, AssetDescriptor<Texture>> preloadTextures;

	static HashMap<String, AssetDescriptor<Texture>> Textures;
	static HashMap<Background, AssetDescriptor<Texture>> Backgrounds;

	static HashMap<String, AssetDescriptor<Pixmap>> Pixmaps;
	static HashMap<PersonPicture, AssetDescriptor<Pixmap>> PersonAvatars;
	static HashMap<Face, AssetDescriptor<Pixmap>> PersonFaces;
	static HashMap<Document.Type, AssetDescriptor<Pixmap>> Documents;

	static HashMap<SoundTrack, AssetDescriptor<Music>> BackgroundMusic;

	// Used to display the 'loading' screen
	static FreeTypeFontGenerator generator;
	BitmapFont loadingFont;
	String loadingstr;

	// Other scenes in the game.
	MainMenuScreen mainMenuScreen;
	ConnectingScreen connectingScreen;
	HostingScreen hostingScreen;
	DatabaseAgentScreen databaseAgentScreen;
	FieldAgentScreen fieldAgentScreen;
	SettingsScreen settingsScreen;
	PauseScreen pauseScreen;
	ErrorScreen errorScreen;

	// Handles first-chance keyboard presses
	UIControlHandler uiControlHandler = new UIControlHandler(this);

	// Font used in the game
	static BitmapFont gameFont;

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

	// Stuff used between the two screens
	// Where we were, used to know where to go back to.
	Screen currentScreen;
	Screen previousScreen;

	// The current level
	Level currentLevel;

	String errorText = "No Error Reported";

	@Override
	public void create() {
		// DUN DUN DUN. Disables compatibility with older (really old) openGL ]
		// implementations. This setting disables the requirement for loaded
		// images
		// to be a strict power of two. Anything that supports openGL 2.0 (which
		// should
		// be most things today) shouldn't have a problem. If we do encounter
		// any hardware
		// that errors out on it, we should set this to true again and then just
		// refactor
		// all our images. Which shouldn't be that much work to do in bulk, just
		// annoying.
		Texture.setEnforcePotImages(false);
		Tween.setCombinedAttributesLimit(4);

		
		
		
		primarySpriteBatch = new SpriteBatch();

		stillLoading = true;
		loadingAssetManager = new AssetManager();
		gameTextureManager = new AssetManager();
		gamePixmapManager = new AssetManager();
		gameMusicManager = new AssetManager();

		preloadTextures = new HashMap<String, AssetDescriptor<Texture>>();
		Textures = new HashMap<String, AssetDescriptor<Texture>>();
		Backgrounds = new HashMap<Background, AssetDescriptor<Texture>>();

		Pixmaps = new HashMap<String, AssetDescriptor<Pixmap>>();
		PersonAvatars = new HashMap<PersonPicture, AssetDescriptor<Pixmap>>();
		Documents = new HashMap<Document.Type, AssetDescriptor<Pixmap>>();
		PersonFaces = new HashMap<Face, AssetDescriptor<Pixmap>>();

		BackgroundMusic = new HashMap<SoundTrack, AssetDescriptor<Music>>();

		generator = new FreeTypeFontGenerator(
				Gdx.files.internal("fonts/amiga4ever.ttf"));
		loadingFont = generator.generateFont(25);

		// Backgrounds...
		Backgrounds.put(Background.LEVEL1FIELDBG, new AssetDescriptor<Texture>(
				"backgrounds/level1fieldagent.jpg", Texture.class));
		Backgrounds.put(Background.LEVEL1DATABASEBG,
				new AssetDescriptor<Texture>(
						"backgrounds/level1databaseagent.png", Texture.class));
		Backgrounds.put(Background.MENUSCREEN, new AssetDescriptor<Texture>(
				"backgrounds/level2fieldagent.png", Texture.class));
		Backgrounds.put(Background.WAITING, new AssetDescriptor<Texture>(
				"backgrounds/PauseScreen.jpg", Texture.class));
		Backgrounds.put(Background.ERROR, new AssetDescriptor<Texture>(
				"backgrounds/TechnicalDifficultyBars.jpg", Texture.class));

		// Static Props...
		Textures.put("clipboard", new AssetDescriptor<Texture>(
				"props/clipboardtransparent.png", Texture.class));

		// UI elements...
		Textures.put("dialogueNinePatchTexture", new AssetDescriptor<Texture>(
				"UI/dialogueBoxNinePatch.png", Texture.class));

		// Characters...

		PersonAvatars.put(PersonPicture.THUG1, new AssetDescriptor<Pixmap>(
				"characters/level1thug.png", Pixmap.class));

		PersonAvatars.put(PersonPicture.INTROFEMALE0,
				new AssetDescriptor<Pixmap>("characters/campusfemale0.png",
						Pixmap.class));
		PersonFaces.put(Face.INTROFEMALE0, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusfemale0face.png", Pixmap.class));

		// All Females
		PersonAvatars.put(PersonPicture.FEMALE1, new AssetDescriptor<Pixmap>(
				"characters/campusfemale1.png", Pixmap.class));
		PersonFaces.put(Face.FEMALE1, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusfemale1face.png", Pixmap.class));

		PersonAvatars.put(PersonPicture.FEMALE2, new AssetDescriptor<Pixmap>(
				"characters/campusfemale2.png", Pixmap.class));
		PersonFaces.put(Face.FEMALE2, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusfemale2face.png", Pixmap.class));

		PersonAvatars.put(PersonPicture.FEMALE3, new AssetDescriptor<Pixmap>(
				"characters/campusfemale3.png", Pixmap.class));
		PersonFaces.put(Face.FEMALE3, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusfemale3face.png", Pixmap.class));

		PersonAvatars.put(PersonPicture.FEMALE4, new AssetDescriptor<Pixmap>(
				"characters/campusfemale4.png", Pixmap.class));
		PersonFaces.put(Face.FEMALE4, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusfemale4face.png", Pixmap.class));

		PersonAvatars.put(PersonPicture.FEMALE5, new AssetDescriptor<Pixmap>(
				"characters/campusfemale5.png", Pixmap.class));
		PersonFaces.put(Face.FEMALE5, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusfemale5face.png", Pixmap.class));

		PersonAvatars.put(PersonPicture.FEMALE6, new AssetDescriptor<Pixmap>(
				"characters/campusfemale6.png", Pixmap.class));
		PersonFaces.put(Face.FEMALE6, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusfemale6face.png", Pixmap.class));

		PersonAvatars.put(PersonPicture.FEMALE7, new AssetDescriptor<Pixmap>(
				"characters/campusfemale7.png", Pixmap.class));
		PersonFaces.put(Face.FEMALE7, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusfemale7face.png", Pixmap.class));

		PersonAvatars.put(PersonPicture.FEMALE8, new AssetDescriptor<Pixmap>(
				"characters/campusfemale8.png", Pixmap.class));
		PersonFaces.put(Face.FEMALE8, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusfemale8face.png", Pixmap.class));

		PersonAvatars.put(PersonPicture.FEMALE9, new AssetDescriptor<Pixmap>(
				"characters/campusfemale9.png", Pixmap.class));
		PersonFaces.put(Face.FEMALE9, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusfemale9face.png", Pixmap.class));

		// All Males
		PersonAvatars.put(PersonPicture.MALE1, new AssetDescriptor<Pixmap>(
				"characters/campusmale1.png", Pixmap.class));
		PersonFaces.put(Face.MALE1, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusmale1face.png", Pixmap.class));

		PersonAvatars.put(PersonPicture.MALE2, new AssetDescriptor<Pixmap>(
				"characters/campusmale2.png", Pixmap.class));
		PersonFaces.put(Face.MALE2, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusmale2face.png", Pixmap.class));

		PersonAvatars.put(PersonPicture.MALE3, new AssetDescriptor<Pixmap>(
				"characters/campusmale3.png", Pixmap.class));
		PersonFaces.put(Face.MALE3, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusmale3face.png", Pixmap.class));

		PersonAvatars.put(PersonPicture.MALE4, new AssetDescriptor<Pixmap>(
				"characters/campusmale4.png", Pixmap.class));
		PersonFaces.put(Face.MALE4, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusmale4face.png", Pixmap.class));

		PersonAvatars.put(PersonPicture.MALE5, new AssetDescriptor<Pixmap>(
				"characters/campusmale5.png", Pixmap.class));
		PersonFaces.put(Face.MALE5, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusmale5face.png", Pixmap.class));

		PersonAvatars.put(PersonPicture.MALE6, new AssetDescriptor<Pixmap>(
				"characters/campusmale6.png", Pixmap.class));
		PersonFaces.put(Face.MALE6, new AssetDescriptor<Pixmap>(
				"characterPortraits/campusmale6face.png", Pixmap.class));

		// Dynamic Props...
		Documents.put(Document.Type.GoldenTicket, new AssetDescriptor<Pixmap>(
				"props/ticket.png", Pixmap.class));
		Documents.put(Document.Type.DriversLicence,
				new AssetDescriptor<Pixmap>("props/BlankID.png", Pixmap.class));

		// Music...
		BackgroundMusic.put(SoundTrack.SONG, new AssetDescriptor<Music>(
				"music/UnreasonableBehaviour.mp3", Music.class));

		// Put things the loadingAssetManager needs to load here...
		for (AssetDescriptor<Texture> theTexture : preloadTextures.values()) {
			loadingAssetManager.load(theTexture);
		}

		// Put things the gameTextureManager needs to load here...

		for (AssetDescriptor<Texture> theTexture : Textures.values()) {
			gameTextureManager.load(theTexture);
		}

		for (AssetDescriptor<Texture> theTexture : Backgrounds.values()) {
			gameTextureManager.load(theTexture);
		}

		// Put things the gamePixmapManager needs to load here...

		for (AssetDescriptor<Pixmap> thePixmap : Pixmaps.values()) {
			gamePixmapManager.load(thePixmap);
		}

		for (AssetDescriptor<Pixmap> thePixmap : PersonAvatars.values()) {
			gamePixmapManager.load(thePixmap);
		}

		for (AssetDescriptor<Pixmap> thePixmap : PersonFaces.values()) {
			gamePixmapManager.load(thePixmap);
		}

		for (AssetDescriptor<Pixmap> thePixmap : Documents.values()) {
			gamePixmapManager.load(thePixmap);
		}

		// Put things the gameMusicManager needs to load here...

		for (AssetDescriptor<Music> theMusic : BackgroundMusic.values()) {
			gameMusicManager.load(theMusic);
		}

		// Make sure the loadingAssetManager is at least done
		loadingAssetManager.finishLoading();

	}

	@Override
	public void dispose() {

	}

	
	
	
	@Override
	public void render() {

		if (stillLoading) {

			Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
			Gdx.graphics.getGL20().glClear(
					GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

			if (!gameTextureManager.update()) {

				loadingstr = "Loading Textures - "
						+ gameTextureManager.getProgress() * 100;
				primarySpriteBatch.begin();

				loadingFont.draw(
						primarySpriteBatch,
						loadingstr,
						Gdx.graphics.getWidth() / 2
								- loadingFont.getBounds(loadingstr).width / 2,
						Gdx.graphics.getHeight() / 2);

				primarySpriteBatch.end();

				return;
			}

			if (!gamePixmapManager.update()) {
				loadingstr = "Loading Pixmaps - "
						+ gamePixmapManager.getProgress() * 100;

				primarySpriteBatch.begin();

				loadingFont.draw(
						primarySpriteBatch,
						loadingstr,
						(Gdx.graphics.getWidth() / 2)
								- loadingFont.getBounds(loadingstr).width / 2,
						Gdx.graphics.getHeight() / 2);

				primarySpriteBatch.end();

				return;
			}

			if (!gameMusicManager.update()) {
				loadingstr = "Loading Music - "
						+ gameMusicManager.getProgress() * 100;

				primarySpriteBatch.begin();

				loadingFont.draw(
						primarySpriteBatch,
						loadingstr,
						(Gdx.graphics.getWidth() / 2)
								- loadingFont.getBounds(loadingstr).width / 2,
						Gdx.graphics.getHeight() / 2);

				primarySpriteBatch.end();

				return;
			}

			// Switch over to calling super's rendering for rendering screens
			stillLoading = false;

			// Do any last initializations that we needed to load files to do
			finishInit();

			// Go to the main menu.
			switchToNewScreen(ScreenEnumerations.MainMenu);

		} else {
			super.render();

		}
	}

	/**
	 * Finishes init on variables that required resources to be fully loaded.
	 */
	private void finishInit() {

		menuNinePatch = new MenuNinePatch(gameTextureManager.get(Textures
				.get("dialogueNinePatchTexture")));
		ninePatchDrawable = new NinePatchDrawable(MenuNinePatch.getInstance());

		generator = new FreeTypeFontGenerator(
				Gdx.files.internal("fonts/amiga4ever.ttf"));
		gameFont = generator.generateFont(25);

		dialogueStyle = new TextButtonStyle();
		dialogueStyle.font = gameFont;
		dialogueStyle.up = ninePatchDrawable;

		dialogueStyle.down = dialogueStyle.up;
		dialogueStyle.over = dialogueStyle.down;

		buttonStyle = new TextButtonStyle();
		buttonStyle.font = gameFont;
		buttonStyle.up = ninePatchDrawable;

		buttonStyle.down = buttonStyle.up;
		buttonStyle.over = buttonStyle.up;
		buttonStyle.fontColor = Color.WHITE;
		buttonStyle.downFontColor = Color.LIGHT_GRAY;

		radioButtonStyle = new TextButtonStyle();
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
		inputStyle.cursor = ninePatchDrawable;

		rawTextStyle = new LabelStyle();
		rawTextStyle.font = gameFont;
		rawTextStyle.fontColor = Color.BLACK;

		scrollPaneStyle = new ScrollPaneStyle();
		scrollPaneStyle.background = ninePatchDrawable;
		scrollPaneStyle.hScroll = ninePatchDrawable;
		scrollPaneStyle.hScrollKnob = ninePatchDrawable;
		scrollPaneStyle.vScroll = ninePatchDrawable;
		scrollPaneStyle.vScrollKnob = ninePatchDrawable;

		currentLevel = Level.getWait();

		return;
	}


/**
 * Pauses the game.  Takes nothing, used by the network
 */
	public void PauseGame() {

		previousScreen = getScreen();
		switchToNewScreen(ScreenEnumerations.PauseScreen);

	}

	/**
	 * Unpauses the game.  Takes nothing, used by the network
	 */
	public void UnpauseGame() {
		setScreen(previousScreen);
	}

	/**
	 * Joins a game, given an IP address
	 */
	protected void JoinGame(String desiredConnectTarget) {
		try {
			network = new NetClient(this, desiredConnectTarget);
			network.send(Message.CONNECT);
		} catch (IOException e) {
			// throw new RuntimeException(e);
			errorText = "Failed to connect to host:\r\n " + e.getMessage();
			switchToNewScreen(ScreenEnumerations.ErrorScreen);
		}

	}

	/**
	 * Leaves the current game, taking us back to the main menu.  Takes nothing, used by the network
	 */
	public void LeaveGame() {
		switchToNewScreen(ScreenEnumerations.MainMenu);
		if (network != null) {
			network.kill();
			network = null;
		}
	}

	/**
	 * Called when the server disconects the client by force (Error, etc)
	 */
	public void DisconnectByServer() {
		errorText = "Disconnected by remote host";
		switchToNewScreen(ScreenEnumerations.ErrorScreen);

	}

	protected void HostGame() {
		server = new ServerEngine();

		server.start();

		JoinGame("localhost");
	}

	public void StopHosting() {
		server.kill();

	}
	/**
	 * Switches a client to a new role
	 */
	public void SetRole(Role role) {
		switch (role) {
		case DATABASE:
			switchToNewScreen(ScreenEnumerations.DatabaseAgent);
			break;

		case FIELDAGENT:
			switchToNewScreen(ScreenEnumerations.FieldAgent);
			break;

		default:
			break;

		}

	}
	/**
	 * Initializes the level (but does not start it yet)
	 */
	public void setLevel(Level level) {

		currentLevel = level;

	}

	/**
	 * Clears the previous incident (if one exists) and sets the current incident to the provided one.
	 * Sets potential alerts that are provided.
	 */
	public void startIncident(Incident incident) {
		if (fieldAgentScreen != null) {
			fieldAgentScreen.papersStage.clear();
			fieldAgentScreen.charactersStage.clear();

			fieldAgentScreen.currentIncident = incident;
			fieldAgentScreen.displayNewPerson(incident.getActor());

		}
		if (databaseAgentScreen != null) {
			databaseAgentScreen.potentialAlerts.clear();
			databaseAgentScreen.potentialAlerts.addAll(incident.getAlerts()
					.toArray(new Alert[0]));

		}

	}
	/**
	 * Adds a specific, non-random alert
	 */
	public void addAlert(Alert alert) {
		if (databaseAgentScreen != null) {
			databaseAgentScreen.addAlert(alert);
		}

	}
	/**
	 * Displays a result on the computer screen
	 */
	public void DatabaseQueryResult(Result result) {
		if (databaseAgentScreen != null) {
			databaseAgentScreen.displayComputerResponse(result.getResultText());
		}

	}
	/**
	 * Displays a dialogue for both roles
	 */
	public void DisplayDialouge(Dialogue[] dialogue) {
		if (databaseAgentScreen != null) {
			databaseAgentScreen.displayDialogue(dialogue);
		}
		if (fieldAgentScreen != null) {
			fieldAgentScreen.displayDialogue(dialogue);
		}
	}
	
	
	/**
	 * Given a ScreenEnumeration (which is really just a list of all possible
	 * screens) it ensures it has been generated (lazy init) and then switches
	 * to it.
	 * 
	 * @param newLevel
	 */

	public void switchToNewScreen(ScreenEnumerations newLevel) {
		switch (newLevel) {
		case ClientEngine:
			this.setScreen(null);

		case MainMenu:
			if (mainMenuScreen == null) {
				mainMenuScreen = new MainMenuScreen(this);
			}
			this.setScreen(mainMenuScreen);
			break;

		case Hosting:
			if (hostingScreen == null) {
				hostingScreen = new HostingScreen(this);
			}
			this.setScreen(hostingScreen);
			break;

		case Connecting:
			if (connectingScreen == null) {
				connectingScreen = new ConnectingScreen(this);
			}
			this.setScreen(connectingScreen);
			break;

		case Settings:
			if (settingsScreen == null) {
				settingsScreen = new SettingsScreen(this);
			}
			this.setScreen(settingsScreen);
			break;

		case FieldAgent:
			if (fieldAgentScreen == null) {
				fieldAgentScreen = new FieldAgentScreen(this);
			}
			this.setScreen(fieldAgentScreen);
			break;

		case DatabaseAgent:
			if (databaseAgentScreen == null) {
				databaseAgentScreen = new DatabaseAgentScreen(this);
			}
			this.setScreen(databaseAgentScreen);
			break;

		case PauseScreen:
			if (pauseScreen == null) {
				pauseScreen = new PauseScreen(this);
			}
			this.setScreen(pauseScreen);
			break;

		case ErrorScreen:
			if (errorScreen == null) {
				errorScreen = new ErrorScreen(this);
			}
			this.setScreen(errorScreen);
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
	 * Entry point for the program. Sets width/height et al.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		// System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		cfg.title = "Final Project Beta";
		cfg.useGL20 = true;
		cfg.width = 1024; // 1200
		cfg.height = 768; // 675

		cfg.vSyncEnabled = false;
		cfg.resizable = false;
		new LwjglApplication(new ClientEngine(), cfg);
	}
}