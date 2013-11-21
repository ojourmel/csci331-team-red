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

			entity.displayNewPerson(entity.currentIncident.actor);
			break;

		case giveDocuments:
			entity.produceDocuments(entity.currentIncident.incidentDocuments);

		default:
			break;

		}
	}
}
