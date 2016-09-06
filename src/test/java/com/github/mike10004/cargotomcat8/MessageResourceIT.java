/*
 * (c) 2016 Novetta
 */
package com.github.mike10004.cargotomcat8;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageResourceIT {
    
    @Test
    public void getServletContextInitParam() throws Exception {
        System.out.println("getServletContextInitParam");
        URL url = new URL("http://localhost:58090/cargo-tomcat8-example/webresources/context/init-param/message");
        Response response = fetch(url);
        System.out.format("fetched %s -> %s%n", url, response);
        assertEquals("status", 200, response.status);
        String actual = new String(response.data, Charsets.US_ASCII);
        System.out.println("myContextParam = " + actual);
        assertEquals("servlet context init param value", "testMessageContent", actual);
    }
    
    private static class Response {
        
        public final int status;
        public final Multimap<String, String> headers;
        public final byte[] data;

        public Response(int status, byte[] data, Multimap<String, String> headers) {
            this.status = status;
            this.data = Preconditions.checkNotNull(data);
            this.headers = ImmutableMultimap.copyOf(headers);
        }
        
        public Response(int status, byte[] data, Map<String, List<String>> headers) {
            this(status, data, toMultimap(headers));
        }
        
        private static Multimap<String, String> toMultimap(Map<String, List<String>> headers) {
            Multimap<String, String> mm = ArrayListMultimap.create();
            for (String key : headers.keySet()) {
                mm.putAll(Strings.nullToEmpty(key), headers.get(key));
            }
            return mm;
        }

        @Override
        public String toString() {
            return "Response{" + "status=" + status + ", headers=" + headers + ", data.size=" + data.length + '}';
        }
        
    }
    
    private static Response fetch(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        int status = conn.getResponseCode();
        boolean success = status / 100 == 2;
        try (InputStream in = conn.getInputStream()) {
            return new Response(status, ByteStreams.toByteArray(in), conn.getHeaderFields());
        } catch (IOException e) {
            if (!success) {
                try (InputStream err = conn.getErrorStream()) {
                    return new Response(status, ByteStreams.toByteArray(err), conn.getHeaderFields());
                } 
            } else {
                throw e;
            }
        } finally {
            conn.disconnect();
        }
    }
}
