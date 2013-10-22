package csci331.team.red.shared;

/**
 * Java implementation of a single database generated person.
 * 
 */
public class Person {
	
	public final int fraud;
	// 25% chance of this person being a fraud.
	private static final double FRAUD_CHANCE = 0.25;
	public Person() {
		if(Math.random()<FRAUD_CHANCE){
			fraud = 1;
		}else{
			fraud = 0;
		}
	}
}
