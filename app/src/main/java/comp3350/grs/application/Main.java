package comp3350.grs.application;


import comp3350.grs.persistence.DataAccessI;
import comp3350.grs.persistence.DataAccessObject;

public class Main
{
	public static final String dbName = "MainDB";
	private static String dbPathName = "database/MainDB";

	public static void main(String[] args)
	{
		startUp();

		shutDown();

		System.out.println("All done");
	}

	public static void startUp()
	{
		comp3350.grs.application.Services.createDataAccess(dbName);
	}

	public static void shutDown()
	{
		comp3350.grs.application.Services.closeDataAccess();
	}

	public static String getDBPathName() {
		if (dbPathName == null)
			return dbName;
		else
			return dbPathName;
	}

	public static void setDBPathName(String pathName) {
		System.out.println("Setting DB path to: " + pathName);
		dbPathName = pathName;
	}
}
