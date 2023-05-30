package connection;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class ConnectionToServer {
    public static void main(String[] args) {
        int port = 8080; // Puerto en el que se ejecutará el servidor
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();

        ResourceConfig config = new ResourceConfig();
        config.packages("connection");

        GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
    }
}