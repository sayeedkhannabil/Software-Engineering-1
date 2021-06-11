package comp3350.grs.objects;

public abstract class User
{
	private String userID;

	public User(){
		userID=null;
	}

	public User(String userID)
	{
		if (userID.contains(" ")){
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
	
	public boolean equals(Object object)
	{
		boolean result;
		User user;
		
		result = false;
		
		if (this.userID!=null&& object instanceof User)
		{
			user = (User) object;
			if (user.userID.equals(this.userID))
			{
				result = true;
			}
		}
		return result;
	}
}
