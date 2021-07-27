package comp3350.grs.application;


import comp3350.grs.persistence.DataAccessI;
import comp3350.grs.persistence.DataAccessObject;

public class Services
{
	private static DataAccessI dataAccessService = null;

	public static DataAccessI createDataAccess(String dbName)
	{
		if (dataAccessService == null)
		{
			dataAccessService = new DataAccessObject(dbName);
			dataAccessService.open(Main.getDBPathName(dbName));
		}
		return dataAccessService;
	}

	public static DataAccessI createDataAccess(DataAccessI alternateDataAccessService)
	{
		if (dataAccessService == null)
		{
			dataAccessService = alternateDataAccessService;
			dataAccessService.open(Main.getDBPathName(alternateDataAccessService.getDbName()));
		}
		return dataAccessService;
	}

	public static DataAccessI getDataAccess()
	{
		if (dataAccessService == null)
		{
			System.out.println("Connection to data access has not been established.");
			System.exit(1);
		}
		return dataAccessService;
	}

	public static void closeDataAccess()
	{
		if (dataAccessService != null)
		{
			dataAccessService.close();
		}
		dataAccessService = null;
	}
}
