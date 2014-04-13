package timetracking.config;

import java.util.Properties;
import java.util.Map;
import java.util.Iterator;

import java.io.IOException;
import java.io.InputStream;

import timetracking.Main;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Config extends Properties{
    private static final Logger log = LoggerFactory.getLogger(Config.class);

    public Config() throws IOException{
        this(Main.class.getResourceAsStream("/timetracking.properties"));
    }

    public Config(final InputStream resourceStream) throws IOException{
        super();
        
        load(resourceStream);
        
        overrideDefault();
    }

    /**
     * Goes through System.env and System.properties
     * and looks for variables from loaded config
     * If found, overrides default value.
     */
    private final void overrideDefault(){
        Iterator<Map.Entry<Object, Object>> iter = entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<Object, Object> entry = iter.next();
            final String key = entry.getKey().toString();
            if(System.getenv(key) != null)
                entry.setValue(System.getenv(key));
            else if(System.getProperty(key) != null)
                entry.setValue(System.getProperty(key));
        }
    }
}
