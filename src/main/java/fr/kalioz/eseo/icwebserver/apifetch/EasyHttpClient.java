package fr.kalioz.eseo.icwebserver.apifetch;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EasyHttpClient {
    private static final Logger logger = Logger.getLogger(EasyHttpClient.class.getName());

    private EasyHttpClient() {
    }

    public static String doRequest(String url, Map<String, String> params) {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("https").setHost("geo.api.gouv.fr").setPath(url);

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.setParameter(entry.getKey(), entry.getValue());
            }
        }

        URI uri;
        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            logger.log(Level.SEVERE, e.toString(), e);
            return null;
        }

        CloseableHttpResponse response = null;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            logger.log(Level.FINE, "GET {0}.", uri);
            HttpRequestBase request = new HttpGet(uri);
            response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != 200) {
                logger.log(Level.FINE, "status code != 200 ({0}.) - aborting.", response.getStatusLine().getStatusCode());
                return null;
            }
            StringWriter writer = new StringWriter();
            IOUtils.copy(response.getEntity().getContent(), writer, "utf-8");
            return writer.toString();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString(), e);
        } finally {
            if (response != null)
                try {
                    response.close();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, e.toString(), e);
                }
        }
        return null;
    }
}
