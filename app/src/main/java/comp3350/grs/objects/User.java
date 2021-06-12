package comp3350.grs.objects;
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

	public User(String userID)
	{
		//userid should not contain space or be empty
		if (userID.equals("")|| userID.contains(" ")){
			this.userID=null;
		}
		else{
			this.userID=userID;
		}


	}


	public String getUserID()
	{
		return userID;
	}

	public void changeUserID(String newID) throws Exception {
		this.userID=newID;
	}

	public String toString()
	{
		return "userID:" + userID;
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
