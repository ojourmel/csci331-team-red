package csci331.team.red.client;


interface Callback
{

}

enum FieldCallback implements Callback
{
	giveDocuments, approachPerson,

}

enum DBCallback implements Callback
{

	startAlerts, stopAlerts, MaryTestAlert,
}

interface DialogueCallback<T extends Callback>
{
	void call(Callback c);
}

class DatabaseDialogueCallback<T extends Callback> implements
		DialogueCallback<Callback>
{
	protected DatabaseAgentScreen entity;

	protected DatabaseDialogueCallback(DatabaseAgentScreen entity)
	{
		this.entity = entity;
	}

	@Override
	public void call(Callback cg)
	{
		DBCallback c = (DBCallback) cg;
		switch (c)
		{
			case MaryTestAlert:

				entity.addAlert("Mary Test Spotted - Looks Very Suspicous, Old Woman Reports");
				entity.changeAlertStage(false);
				break;
			case startAlerts:
				entity.changeAlertStage(true);
				break;
			case stopAlerts:
				entity.changeAlertStage(false);
				break;
			default:
				break;
		}
	}
}

class FieldDialogueCallback<T extends Callback> implements
		DialogueCallback<Callback>
{
	protected FieldAgentScreen entity;

	protected FieldDialogueCallback(FieldAgentScreen entity)
	{
		this.entity = entity;
	}

	@Override
	public void call(Callback cg)
	{
		
		FieldCallback c = (FieldCallback) cg;
		switch (c)
		{
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
