package timetracking.config;

import java.util.Properties;
import java.util.Map;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Config extends Properties{

    private final String fileName;
    public Config(final String fileName) throws IOException{
        super();
        this.fileName = fileName;

        load(new FileInputStream(new File(fileName)));
        
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
