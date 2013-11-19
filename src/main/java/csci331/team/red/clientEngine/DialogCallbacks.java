package csci331.team.red.clientEngine;

import csci331.team.red.shared.Document;
import csci331.team.red.shared.Person;

interface Callback
{

}

enum FieldCallback implements Callback
{
	approachFirstPerson, giveID, approachSecondPerson,

}

enum DBCallback implements Callback
{

	startAlerts, stopAlerts, MaryTestAlert,
}

interface DialogCallback<T extends Callback>
{
	void call(Callback c);
}

class DatabaseDialogeCallback<T extends Callback> implements
		DialogCallback<Callback>
{
	protected DatabaseAgentScreen entity;

	protected DatabaseDialogeCallback(DatabaseAgentScreen entity)
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

class FieldDialogeCallback<T extends Callback> implements
		DialogCallback<Callback>
{
	protected FieldAgentScreen entity;

	protected FieldDialogeCallback(FieldAgentScreen entity)
	{
		this.entity = entity;
	}

	@Override
	public void call(Callback cg)
	{
		FieldCallback c = (FieldCallback) cg;
		switch (c)
		{
			case approachFirstPerson:
				
				entity.displayNewPerson(new Person());
				break;
			case approachSecondPerson:
				entity.displayNewPerson(new Person());
				break;

			case giveID:
				entity.produceDocument(new Document(Document.Type.GoldenTicket , new String[]{"Mary Test"}));

			default:
				break;

		}
	}
}

// class Car extends Vehicle<FieldCallback>
// {
// public Car(FieldCallback color)
// {
// super(color);
// }
// }
//
// class RacingCar extends Vehicle<DBCallback>
// {
// public RacingCar(DBCallback color)
// {
// super(color);
// }
// }
