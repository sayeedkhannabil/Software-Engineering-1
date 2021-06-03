package comp3350.grs.objects;

public class User
{
	private String userID;

	public User(){
		userID=null;
	}

	public User(String userID)
	{
		this.userID=userID;
	}


	public String getUserID()
	{
		return userID;
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
		
		if (object instanceof User)
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
