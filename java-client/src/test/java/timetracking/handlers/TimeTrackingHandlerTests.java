package timetracking.handlers;

import org.junit.*;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TimeTrackingHandlerTests {
    public TimeTrackingHandlerTests(){
    }
    @Before
    public void setUp(){
    }

    @After
    public void tearDown(){
    }

    @Test
    public void testNormalizeString(){
        Assert.assertEquals("-Display0", TimeTrackingHandlers.normalizeString("\\Display0"));
    }

}
