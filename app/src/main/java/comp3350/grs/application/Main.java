package comp3350.grs.application;



public class Main
{
	public static final String dbName="SC";

	public static void main(String[] args)
	{
		startUp(null);
		shutDown();
		System.out.println("All done");
	}

	public static void startUp(String content)
	{
		Services.createDataAccess(dbName,content);
	}

	public static void shutDown()
	{
		Services.closeDataAccess();
	}
}
