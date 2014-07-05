import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import example.EmbeddedTomcat;
import example.guice.CounterService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CounterServiceTest {
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
    public void testCounter() throws InterruptedException {
        Client client = ClientBuilder.newClient();
        
        WebTarget path = client.target(embeddedTomcat.getBaseURI())
                        .path("api/counter");
        String result = path
                        .request(MediaType.TEXT_PLAIN_TYPE)
                        .get(String.class);

        Integer.parseInt(result);
    }
}
