/*
 * HelloWorld.java
 *
 * Created on 13 juillet 2006, 19:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import com.ewon.ewonitf.Loader;
import com.ewon.ewonitf.WebProducer;
import com.ewon.ewonitf.IOManager;
import com.ewon.test.*;
import java.util.Date;

//import com.test.crooks.ExtTestClass;

/**
 * Main application for testing the eWON JVM/CLDC implementation
 */
public class ThingworxEwonIntegratorMain {


    /**
     * The main entry point
     * @param args Arguments passed to the program
     */
    public static void main(String[] args) {

        // Example of reading a tag
        TagValue = IOManager.readTag("MyTag");
        System.out.println("Tag Value"+ Float.toString(TagValue));

        // Example of writing a tag
        //IOManager.writeTag("MyTag", (float)123.0);

        RestClient client = new RestClient();

        // !!! Edit these !!!:
        String url = "https://xxxxx/Thingworx/Things/TestThing/Services/UpdatePropertyValues";
        String data = "{\"values\":{\"created\":1440412002249,\"description\":\"\",\"name\":\"Infotable\",\"dataShape\":{\"fieldDefinitions\":{\"name\":{\"name\":\"name\",\"aspects\":{\"isPrimaryKey\":true},\"description\":\"Property name\",\"baseType\":\"STRING\",\"ordinal\":0},\"time\":{\"name\":\"time\",\"aspects\":{},\"description\":\"time\",\"baseType\":\"DATETIME\",\"ordinal\":0},\"value\":{\"name\":\"value\",\"aspects\":{},\"description\":\"value\",\"baseType\":\"VARIANT\",\"ordinal\":0},\"quality\":{\"name\":\"quality\",\"aspects\":{},\"description\":\"quality\",\"baseType\":\"STRING\",\"ordinal\":0}},\"name\":\"NamedVTQ\",\"description\":\"Property name, value, time, quality, state\"},\"rows\":[{\"name\":\"prop1\",\"time\":\"2015-08-24T10:26:51.000Z\",\"value\":\"1234\"},{\"name\":\"prop2\",\"time\":\"2015-08-24T10:29:01.000Z\",\"value\":\"testing122234\"}]}}";

        String response = client.post(url, data);

        // TODO: Now you can inspect the response and do something with it

        // If scheduling is done on the eWon configuration side, this shouldn't be used
        System.out.println("End program");
        Runtime.getRuntime().exit(0);
    }

    /** Creates a new instance of the TestMain application */
    public ThingworxEwonIntegratorMain()
    {
    }

}
