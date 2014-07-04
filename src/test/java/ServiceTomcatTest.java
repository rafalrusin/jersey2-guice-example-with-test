import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import example.EmbeddedTomcat;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import example.guice.Service;

public class ServiceTomcatTest {

    private static EmbeddedTomcat embeddedTomcat;
    

    @BeforeClass
    public static void beforeClass() throws Exception {
        embeddedTomcat= new EmbeddedTomcat();
        embeddedTomcat.start();
    }
    
    @AfterClass
    public static void afterClass() throws Exception {
        embeddedTomcat.stop();
    }

    @Test
    public void givenRunningJettyInstance_whenGetMyresource_thenServiceStringReturned() throws InterruptedException {
        Client client = ClientBuilder.newClient();
        
        System.out.println(embeddedTomcat.getBaseURI());
        
        WebTarget path = client.target(embeddedTomcat.getBaseURI())
                        .path("api/myresource");
        String entity = path
                        .request(MediaType.TEXT_PLAIN_TYPE)
                        .get(String.class);

        assertEquals(Service.SERVICE_STRING, entity);

//        Thread.sleep(1000000);
        
    }

}
