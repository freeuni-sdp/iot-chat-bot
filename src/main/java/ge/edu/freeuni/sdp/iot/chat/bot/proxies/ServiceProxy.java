package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

/**
 * Created by Nikoloz on 07/10/16.
 */
public class ServiceProxy {

    protected final String uri;
    protected final Client client;

    public ServiceProxy(String uri) {
        this.uri = uri;
        ClientConfig config = new ClientConfig().register(JacksonFeature.class);
        client = ClientBuilder.newClient(config);
    }

    private boolean is404(Response response) {
        return isStatus(response, Response.Status.NOT_FOUND);
    }

    private boolean is201(Response response) {
        return isStatus(response, Response.Status.CREATED);
    }

    private boolean isStatus(Response response, Response.Status status ) {
        return response.getStatus() ==	status.getStatusCode();
    }
}
