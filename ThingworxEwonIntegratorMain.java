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
import com.ewon.test.*;
import java.util.Date;

//import com.test.crooks.ExtTestClass;

/**
 * Main application for testing the eWON JVM/CLDC implementation
 */
public class TestMain {

    static boolean wait(int i)
    {
       return i>0;
    }

    public static void TestTagControl()
    {
        System.out.println("Test: com.ewon.ewonitf.TagControl");

        TestTagControl.readWriteTest();
    }

    /**
     * Experiment the com.ewon.ewonitf.SysControlBlock object.
     * Executes tests in the TestSysControlBlock object.
     */
    public static void TestSysControlBlock()
    {
        System.out.println("Test: com.ewon.ewonitf.SysControlBlock");

        TestSysControlBlock.createOrUpdateTag();
        TestSysControlBlock.readWriteSysBlock();
        TestSysControlBlock.createOrUpdateUser();
    }

    /**
     * Experiment the com.ewon.ewonitf.IOManager object.
     */
    public static void TestIOManager()
    {
        System.out.println("Test: com.ewon.ewonitf.IOManager");

        TestIOManager.buttonAndLedTest();
        TestIOManager.readWriteTagTest();
    }

    /**
     * Experiment the com.ewon.ewonitf.DefaultEventHandler object.
     */
    public static void TestDefaultEventHandler()
    {
    TestDefaultEventHandler testDEH;

       System.out.println("Test: com.ewon.ewonitf.DefaultEventHandler");

       testDEH = new TestDefaultEventHandler();
       //Warning: this function never returns
       testDEH.eventHandlerTest(/*UseThread:true or UseThread:false*/ false);
    }

    /**
     * Experiment the com.ewon.ewonitf.DefaultEventHandler object.
     */
    public static void TestTagDefaultEventHandler()
    {
    TestDefaultEventHandler testDEH;

       System.out.println("Test: com.ewon.ewonitf.DefaultEventHandler");

       testDEH = new TestDefaultEventHandler();
       //Warning: this function never returns
       testDEH.eventHandlerTagTest(/*UseThread:true or UseThread:false*/ false);
    }

    /**
     * Experiment the com.ewon.ewonitf.Loader object.
     * Experiment the com.ewon.ewonitf.Exporter object.
     */
    public static void TestLoaderExporter()
    {
    TestLoaderExporter  testLoader;

       testLoader = new TestLoaderExporter();

       System.out.println("Test: com.ewon.ewonitf.Loader");
       //these 2 tests do exactly the same but using different functions
       testLoader.loaderTest();
       testLoader.loaderLoadFromTest();

       System.out.println("Test: com.ewon.ewonitf.Exporter");
       //these 2 tests do exactly the same but using different functions
       testLoader.exporterTest();
       testLoader.exporterExportFromTest();
    }

    /**
     * Experiment the com.ewon.ewonitf.ModemManager object.
     */
    public static void TestModemManager()
    {
       System.out.println("Test: com.ewon.ewonitf.ModemManager");

       TestModemManager.pollModemInfo();

       TestModemManager.receiveSmsTst();

       TestModemManager.resetModemTest();
    }

    /**
     * Experiment the com.ewon.ewonitf.ModemManager object.
     */
    public static void TestScheduledActionManager()
    {
       System.out.println("Test: com.ewon.ewonitf.ScheduledActionManager");

       // Create scheduled actions
       TestScheduledActionManager.getHttpTest();
       TestScheduledActionManager.ntpSyncTest();
       TestScheduledActionManager.sendMailTest();

       // Read the current list of actions.
       TestScheduledActionManager.readSamTest();
       TestScheduledActionManager.readClearSamLogTest();
    }

    /**
     * Experiment the com.ewon.ewonitf.RuntimeControl object.
     */
    public static void TestRuntimeControl()
    {
       System.out.println("Test: com.ewon.ewonitf.RuntimeControl");

       //This will restart the program, so next function never called
       TestRuntimeControl.restartProgramTest();

       //This function will reboot the eWON.
       TestRuntimeControl.watchdogTest();
    }

    /**
     * Experiment the com.ewon.ewonitf.EventManager and EwonSystem object.
     */
    public static void TestEventAndSystem()
    {
       System.out.println("Test: com.ewon.ewonitf.EventManager");
       TestEventAndSystem.eventTest();
       System.out.println("Test: com.ewon.ewonitf.EwonSystem");
       TestEventAndSystem.ewonSystemTest();
    }

    /**
     * Experiment the com.ewon.ewonitf.NetManager object.
     */
    public static void TestNetManager()
    {
       System.out.println("Test: com.ewon.ewonitf.NetManager");
       TestNetManager.t2mCnxTest();
       TestNetManager.pppAndTfTest();
       TestNetManager.switchPortTest();
       TestNetManager.interfaceInfoTest();
    }

    /**
     * Experiment the javax.microedition.io.CommConnection object.
     */
    public static void TestComm()
    {
       System.out.println("Test: javax.microedition.io.CommConnection");
       TestComm.echoTest();
       TestComm.echoWithReuseOpenTest();
    }

    /**
     * Experiment the javax.microedition.io.CommConnection object.
     */
    public static void TestFileConnection()
    {
       System.out.println("Test: javax.microedition.io.FileConnection");

       TestFileconnection testFC = new TestFileconnection();
       testFC.createFileTest();
       testFC.listRootTest();
       testFC.readDirTest();
       testFC.readFileTest();
    }

    public  static void TestLoadComConfig()
    {
        // Init default Configuration
          try {
            // Setup the default configuration for config.txt
            String abbConfig = "/usr/config.abb";
            Loader loaderConfig = new Loader("/config.txt");
            loaderConfig.LoadFrom("file:///" + abbConfig);
            loaderConfig.close();
            // Setup the default configuration for comcfg.txt
            String abbComConfig = "/usr/comcfg.abb";
            Loader loaderComConfig = new Loader("/comcfg.txt");
            loaderComConfig.LoadFrom("file:///" + abbComConfig);
            loaderComConfig.close();
          } catch (Exception ex) {
             System.out.println("Error: "+ex.toString());
          }
    }

    /**
     * The main entry point
     * @param args Arguments passed to the program
     */
    public static void main(String[] args) {
        TestSocket         testSocket;

        System.out.println("Start program");

        System.out.println("ewonitf Version: "+System.getProperty("ewonitf.version"));

        TestIOServer();


        TestDefaultEventHandler();

        System.out.println("End program");
        Runtime.getRuntime().exit(0);

        TestTagDefaultEventHandler();
        TestTagControl();

        System.out.println("End program");
        Runtime.getRuntime().exit(0);


        TestModemManager();

        TestComm();


        TestLoadComConfig();

        TestFileConnection();

        //**This test is long and requires you to interact with the eWON front button.
        //TestIOManager();
//        TestLoaderExporter();
        TestModemManager();
        TestScheduledActionManager();
        TestRuntimeControl();
        TestEventAndSystem();
//        testSocket =new TestSocket();
//        testSocket.TestSocket6();

        TestNetManager();
//        TestComm();
//        TestFileConnection();
        TestDefaultEventHandler();

        System.out.println("End program");
        Runtime.getRuntime().exit(0);
    }

   private static void TestIOServer() {
      TestIOServer  testIOServer;

      System.out.println("Test: com.ewon.ewonitf.IOServer");

      testIOServer = new TestIOServer();
      try {
         testIOServer.registerServer();
      } catch (Exception ex) {
         System.out.println("Test: com.ewon.ewonitf.Loader");
      }

   }

    /** Creates a new instance of the TestMain application */
    public TestMain()
    {
    }

}
