package csci331.team.red.client;

import com.badlogic.gdx.scenes.scene2d.Actor;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * @author Lduperron
 */

public class ActorTweener implements TweenAccessor<Actor> {

	// The following lines define the different possible tween types.
	// It's up to you to define what you need :-)

	public static final int POSITION_X = 1;
	public static final int POSITION_Y = 2;
	public static final int POSITION_XY = 3;
	public static final int SCALE_XY = 4;
	public static final int ZOOM = 5;

	// TweenAccessor implementation

	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case POSITION_X:
			returnValues[0] = target.getX();
			return 1;
		case POSITION_Y:
			returnValues[0] = target.getY();
			return 1;
		case POSITION_XY:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;

		case SCALE_XY:
			returnValues[0] = target.getScaleX();
			returnValues[1] = target.getScaleY();
			return 2;

		case ZOOM:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			returnValues[2] = target.getScaleX();
			returnValues[3] = target.getScaleY();
			return 4;

		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Actor target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case POSITION_X:
			target.setX(newValues[0]);
			break;
		case POSITION_Y:
			target.setY(newValues[0]);
			break;
		case POSITION_XY:
			target.setX(newValues[0]);
			target.setY(newValues[1]);
			break;
		case SCALE_XY:
			target.setScaleX(newValues[0]);
			target.setScaleY(newValues[1]);
			break;

		case ZOOM:
			target.setX(newValues[0]);
			target.setY(newValues[1]);
			target.setScaleX(newValues[2]);
			target.setScaleY(newValues[3]);
			break;

		default:
			assert false;
			break;
		}
	}
}