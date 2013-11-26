package csci331.team.red.client;

import csci331.team.red.shared.Alert;
import csci331.team.red.shared.callbacks.DBCallback;

public class DatabaseDialogueCallback<T extends Callback> implements
		DialogueCallback<Callback> {
	protected DatabaseAgentScreen entity;

	public DatabaseDialogueCallback(DatabaseAgentScreen entity) {
		this.entity = entity;
	}

	@Override
	public void call(Callback cg) {
		DBCallback c = (DBCallback) cg;
		switch (c) {
		case MaryTestAlert:

			entity.addAlert(new Alert("Mary Test Spotted - Looks Very Suspicous, Old Woman Reports" , "VUI ALERT: "));
			entity.changeAlertStage(false);
			break;
		case startAlerts:
			entity.changeAlertStage(true);
			break;
		case stopAlerts:
			entity.changeAlertStage(false);
			break;
		case endGame:
			entity.parentEngine.LeaveGame();
		default:
			break;
		}
	}
}