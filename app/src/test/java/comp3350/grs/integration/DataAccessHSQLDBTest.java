package comp3350.grs.integration;


import org.junit.Test;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.persistence.DataAccessI;
import comp3350.grs.persistence.DataAccessITest;

public class DataAccessHSQLDBTest {
    private static String dbName = Main.testDbName;

    @Test
    public void testDataAccess()
    {
        DataAccessI dataAccess;

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test DataAccess (using default DB)");

        // Use the following two statements to run with the real database
        Services.createDataAccess(dbName);
        dataAccess = Services.getDataAccess();

        DataAccessITest.dataAccessTest(dataAccess);

        Services.closeDataAccess();

        System.out.println("Finished Integration test DataAccess (using default DB)");
    }

}
