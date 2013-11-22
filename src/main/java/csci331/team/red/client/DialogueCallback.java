package csci331.team.red.client;

public interface DialogueCallback<T extends Callback> {
	void call(Callback c);
}
