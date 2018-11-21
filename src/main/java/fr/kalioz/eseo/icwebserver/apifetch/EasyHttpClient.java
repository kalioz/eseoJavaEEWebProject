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

public class EasyHttpClient {
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
            e.printStackTrace();
            return null;
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpRequestBase request = new HttpGet(uri);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("status code != 200 - aborting :" + response.getStatusLine().getStatusCode() + " // " + uri);
                return null;
            }
            StringWriter writer = new StringWriter();
            IOUtils.copy(response.getEntity().getContent(), writer, "utf-8");
            String output = writer.toString();

            return output;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
