package comp3350.grs;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.grs.integration.BusinessPersistenceSeamTest;
import comp3350.grs.integration.DataAccessHSQLDBTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataAccessHSQLDBTest.class, BusinessPersistenceSeamTest.class,
})

public class RunIntegrationTests {
}
