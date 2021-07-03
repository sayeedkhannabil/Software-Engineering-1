package comp3350.grs.objects;

import comp3350.grs.exceptions.IncorrectFormat;

// CLASS: User
//
// Author: Shiqing Guo
//
// REMARKS: domain object of the user who will use this app
//	this is an abstract class
//
//-----------------------------------------
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

	private void checkUseridFormat(String userID) throws IncorrectFormat {
		if (userID!=null){
			if (userID.equals("")|| userID.contains(" ")||userID.length()>20){
				throw new IncorrectFormat("user id should not be empty or contain" +
						" space or more than 20 letters");
			}
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

	public boolean validUser(){
		return this.userID!=null;
	}

	//change user id to new user id
	public void changeUserID(String newUserID) throws IncorrectFormat, Exception {
		checkUseridFormat(newUserID);
		this.userID=newUserID;
	}

	//------------------------------------------------------
	// equals
	//
	// PURPOSE:    compare this user with the user given
	// PARAMETERS:
	//     object: the given user to compare
	// Returns: a boolean to indicate whether this user equals to given user
	//------------------------------------------------------
	public boolean equals(Object object)
	{
		boolean result;
		User user;
		
		result = false;
		
		if (this.userID!=null&& object instanceof User)
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
