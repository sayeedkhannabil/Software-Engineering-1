package comp3350.grs.objects;

import comp3350.grs.exceptions.IncorrectFormat;


// domain object of the user who will use this app
//	this is an abstract class, have guest and registeredUser as child
public abstract class User
{
	private String userID;

	public User(){
		userID=null;
	}

	public User(String userID) throws IncorrectFormat {
		//userid should not contain space or be empty
		checkUseridFormat(userID);
		this.userID=userID;
	}

	//check the format of user id is correct
	protected void checkUseridFormat(String userID) throws IncorrectFormat {
		final int MAX_LENGTH=20;
		if (userID.length()<=0||userID.length()>MAX_LENGTH){
			throw new IncorrectFormat("length of user id should >0 and <="+MAX_LENGTH);
		}else if (userID.contains(" ")){
			throw new IncorrectFormat("user id should not contain space");
		}
	}

	public String getUserID()
	{
		return userID;
	}


	public String toString()
	{
		return "userID:" + userID;
	}

	//important info is not null
	public boolean validUser(){
		return this.userID!=null;
	}

	//change user id to new user id
	public void changeUserID(String newUserID) throws IncorrectFormat {
		checkUseridFormat(newUserID);
		this.userID=newUserID;
	}


	public boolean equals(Object object)
	{
		boolean result;
		User user;
		result = false;
		
		if (object!=null&& validUser()&& object instanceof User)
		{
			user = (User) object;
			//if user id are the same , they are equal
			if (user.userID.equals(this.userID))
			{
				result = true;
			}
		}
		return result;
	}
}
