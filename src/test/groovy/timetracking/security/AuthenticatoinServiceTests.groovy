package timetracking.security

import org.junit.*;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import org.junit.annotations.*;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
class AuthenticationServiceTests {
  def service
  @Before
  void setUp(){
    service = new AuthenticationService('https://softsky.harvestapp.com', 'info@softsky.com.ua', 'secret9000');
  }

  @After
  void tearDown(){
  }

  @Test
  public void testLogin(){
    Assert.assertTrue('Must return true on login', service.login())
  }

  @Ignore
  @Test
  public void testClients(){
    Assert.assertTrue('Must return clients XML', service.clients())
  }

  @Test
  public void testDaily(){
    Assert.assertTrue('Must return daily XML', service.daily())
  }

}