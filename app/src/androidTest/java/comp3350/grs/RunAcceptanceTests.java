package comp3350.grs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.grs.acceptance.UserAccountTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({UserAccountTest.class

})
public class RunAcceptanceTests
{
    public RunAcceptanceTests()
    {
        System.out.println("Acceptance tests");
    }
}
