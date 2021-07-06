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

	protected void checkUseridFormat(String userID) throws IncorrectFormat {
		if (userID.length()<=0||userID.length()>10){
			throw new IncorrectFormat("length of user id should >0 and <=10");
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

	public boolean validUser(){
		return this.userID!=null;
	}

	//change user id to new user id
	public void changeUserID(String newUserID) throws IncorrectFormat {
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
