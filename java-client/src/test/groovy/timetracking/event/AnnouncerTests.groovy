package timetracking.event;

import org.junit.*;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import org.junit.annotations.*;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
class AnnouncerTests {
  private class Event1Args extends Object{
  }

  private class Event2Args extends Object{
  }

  private interface IMyEventListener extends EventListener {
    public void onEvent1(Event1Args args);

    public void onEvent2(Event2Args args);
  }

  @Test
  void testAnnouncer(){
    def announcer = Announcer.to(IMyEventListener.class);

    final def checkMap = [:]

    def listener = [
    onEvent1: { args ->
      checkMap[args.getClass()] = args;
      },
    onEvent2: { args ->
      checkMap[args.getClass()] = args;
      },

    ] as IMyEventListener

    Assert.assertEquals(checkMap.size(), 0);
    announcer.announce().onEvent1(new Event1Args());
    announcer.announce().onEvent2(new Event2Args());
    Assert.assertEquals(checkMap.size(), 0);

    announcer.addListener(listener);
    announcer.announce().onEvent1(new Event1Args());
    Assert.assertEquals(checkMap.size(), 1);
    Assert.assertTrue(checkMap[Event1Args.class] instanceof Event1Args);
    announcer.announce().onEvent2(new Event2Args());
    Assert.assertEquals(checkMap.size(), 2);
    Assert.assertTrue(checkMap[Event2Args.class] instanceof Event2Args);
    announcer.removeListener(listener);

    checkMap.clear();
    announcer.announce().onEvent1(new Event1Args());
    announcer.announce().onEvent2(new Event2Args());
    Assert.assertEquals(checkMap.size(), 0);
    
  }
}