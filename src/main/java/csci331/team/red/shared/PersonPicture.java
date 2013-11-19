package csci331.team.red.shared;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum PersonPicture
{
	MALE1,
	FEMALE1,
	THUG1;
	
	
	private static final List<PersonPicture> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();
	
	public static PersonPicture randomPerson()  
	{
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
