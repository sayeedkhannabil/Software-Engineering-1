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
		checkUseridNotNull(userID);
		//userid should not contain space or be empty
		checkUseridFormat(userID);
		this.userID=userID;
	}

	private void checkUseridNotNull(String userID){
		if (userID==null){
			throw new NullPointerException("userID should not be null.");
		}
	}

	private void checkUseridFormat(String userID) throws IncorrectFormat {
		if (userID.equals("")|| userID.contains(" ")){
			throw new IncorrectFormat("user id should not be empty or contain" +
					" space");
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
		if (this.userID!=null){
			return true;
		}
		else {
			return false;
		}
	}

	//change user id to new user id
	public void changeUserID(String newUserID) throws Exception {
		checkUseridNotNull(newUserID);
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
