package example;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;

/**
 * Created by joker on 7/4/2014.
 */
public class Main {
    public static void main(String[] args) throws ServletException, LifecycleException, InterruptedException, IOException {
        new EmbeddedTomcat().start();
        System.in.read();
    }
}
