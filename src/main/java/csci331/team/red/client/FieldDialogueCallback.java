package csci331.team.red.client;

import csci331.team.red.shared.callbacks.FieldCallback;

public class FieldDialogueCallback<T extends Callback> implements
		DialogueCallback<Callback> {
	protected FieldAgentScreen entity;

	public FieldDialogueCallback(FieldAgentScreen entity) {
		this.entity = entity;
	}

	@Override
	public void call(Callback cg) {

		FieldCallback c = (FieldCallback) cg;
		switch (c) {
		case approachPerson:

			entity.displayNewPerson(entity.currentIncident.getActor());
			break;

		case giveDocuments:
			if(entity.currentIncident.getIncidentDocuments() != null)
			{
				entity.produceDocuments(entity.currentIncident.getIncidentDocuments());
			}

			break;
		case endGame:
			entity.parentEngine.LeaveGame();
			break;
		default:
			break;

		}
	}
}
