package csci331.team.red.clientEngine;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class UIControlHandler implements InputProcessor {

	ClientEngine centralEngine;
	
	public UIControlHandler(ClientEngine s)
	{
		
		centralEngine = s;
		
	}
	
	@Override
	public boolean keyDown(int keycode) {

	if(keycode == Keys.ESCAPE)
	{       
		if(centralEngine.getScreen() == centralEngine.pauseScreen)
		{
				
			centralEngine.UnpauseGame();
				
		}
		
		else
		{
			centralEngine.PauseGame();
		}
		 
	}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}