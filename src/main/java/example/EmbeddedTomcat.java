package example;

import java.io.File;
import java.net.URI;

import javax.servlet.ServletException;
import javax.ws.rs.core.UriBuilder;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

/**
 * Provides a simple way to start up tomcat running the webapps defined by the using project.
 * 
 */
public class EmbeddedTomcat {

    private Tomcat tomcat;

    public URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/webapp/").port(tomcat.getConnector().getLocalPort()).build();
    }

    public void start() throws ServletException, LifecycleException {
        synchronized (this) {
            if (tomcat == null) {
                tomcat = new Tomcat();
            }
            else {
                return;
            }
        }
        // This tells Tomcat to select an available port it probably wont be 0
        int port = Integer.parseInt(System.getProperty("tomcat.port", "8080"));
        tomcat.setPort(port);
        // This ensures that the temp work folder of Tomcat is created in target which is a safe location
        String basedir = "work_" + port;
        {
            File file = new File(basedir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }
        tomcat.setBaseDir(basedir);
        /*
         * This is the base path used to find webapps given their path when calling addWebapp starting from the basedir,
         * we nullify it so we are also starting from target
         */
        tomcat.getHost().setAppBase("");
        System.out.println(tomcat.getHost().getAppBase());

        // Add AprLifecycleListener - dunno about this but it works
        StandardServer server = (StandardServer) tomcat.getServer();
        AprLifecycleListener listener = new AprLifecycleListener();
        server.addLifecycleListener(listener);

        // Add webapps as found relative to the target path
        tomcat.addWebapp("/webapp", "../src/main/tomcat/webapps/webapp");
        tomcat.start();

        System.out.println(getBaseURI());
    }

    public final void stop() throws LifecycleException {
        if (tomcat.getServer() != null && tomcat.getServer().getState() != LifecycleState.DESTROYED) {
            if (tomcat.getServer().getState() != LifecycleState.STOPPED) {
                tomcat.stop();
            }
            tomcat.destroy();
        }
    }

}
