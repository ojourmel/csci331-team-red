package csci331.team.red.client;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import csci331.team.red.shared.Message;

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
				
			centralEngine.network.send(Message.RESUME);
				
		}
		
		else
		{
			centralEngine.network.send(Message.PAUSE);
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