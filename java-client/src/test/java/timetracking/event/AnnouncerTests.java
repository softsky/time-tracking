package timetracking.event;

import org.junit.*;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.*;

import java.util.EventListener;

class Event1Args extends Object{
}

class Event2Args extends Object{
}


@RunWith(JUnit4.class)
public class AnnouncerTests {
    private interface IMyEventListener extends EventListener {
        public void onEvent1(Event1Args args);

        public void onEvent2(Event2Args args);
    }

    public AnnouncerTests(){
    }

    @Test
    public void testAnnouncer(){
        Announcer<IMyEventListener> announcer = Announcer.to(IMyEventListener.class);

        final Map<Class, Object> checkMap = new HashMap<Class, Object>();

        IMyEventListener listener = new IMyEventListener(){
                public void onEvent1(Event1Args args){
                    checkMap.put(Event1Args.class, args);
                }
                public void onEvent2(Event2Args args){
                    checkMap.put(Event2Args.class,args);
                }
            };

        Assert.assertEquals(checkMap.size(), 0);
        announcer.announce().onEvent1(new Event1Args());
        announcer.announce().onEvent2(new Event2Args());
        Assert.assertEquals(checkMap.size(), 0);

        announcer.addListener(listener);
        announcer.announce().onEvent1(new Event1Args());
        Assert.assertEquals(checkMap.size(), 1);
        Assert.assertNotNull(checkMap.get(Event1Args.class));
        announcer.announce().onEvent2(new Event2Args());
        Assert.assertEquals(checkMap.size(), 2);
        Assert.assertNotNull(checkMap.get(Event2Args.class));
        announcer.removeListener(listener);

        checkMap.clear();
        announcer.announce().onEvent1(new Event1Args());
        announcer.announce().onEvent2(new Event2Args());
        Assert.assertEquals(checkMap.size(), 0);
    
    }
}
