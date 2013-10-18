package csci331.team.red.client;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import csci331.team.red.core.mainGameScreen;

public class KeyboardHandler implements InputProcessor  {

	mainGameScreen parent;
	
	public KeyboardHandler(mainGameScreen s)
	{
		
		parent = s;
		
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
		if(keycode == Keys.A)
		{
			dialgoueWindow button = new dialgoueWindow("They're all around me!", "Bucket:", parent.style , parent.secondStage, true , 0);
			
		}
		
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
