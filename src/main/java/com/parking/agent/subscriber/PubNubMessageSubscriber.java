package com.parking.agent.subscriber;


import com.ning.http.client.Cookie;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.parking.agent.rest.RestClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class PubNubMessageSubscriber {

    private Pubnub pubnub;
    private static final String PARKING_CHANNEL = "parking_channel";
    private String baseURL = null;
    private Cookie apiKey = null;
    private JSONParser parser = new JSONParser();
    private Map<String, LocationListener> listenerMap = new HashMap<>();

    public PubNubMessageSubscriber(String host,
                             String port,
                             String publishKey,
                             String subscribeKey,
                             String key,
                             String endpoint) {
        baseURL = "http://" + host + ":" + port + endpoint;
        System.out.println("The base url is " + baseURL);
        pubnub = new Pubnub(publishKey, subscribeKey);
        pubnub.setCacheBusting(false);
        apiKey = new Cookie(host, "x-CentraSite-APIKey", key, "", 999, false);
        subscribe();
    }

    private void subscribe() {
        try {

            pubnub.subscribe(PARKING_CHANNEL, new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    try {
                        String locationName = updateLocationInfo((String) message);
                        System.out.println("Location " + locationName + " updated as " + message);
                        startListenerIfNecessary(locationName);
                    } catch (ParseException | URISyntaxException e) {
                        System.err.println(e.getMessage());
                    }
                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    if (error.errorCode == PubnubError.PNERR_TIMEOUT)
                        pubnub.disconnectAndResubscribe();
                }
            });

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Pubnub getPubnub() {
        return pubnub;
    }

    private void startListenerIfNecessary(String locationName) {
        if (!listenerMap.containsKey(locationName)) {
            System.out.println("Listener added for " + locationName);
            listenerMap.put(locationName, new LocationListener(locationName, this));
        }
    }

    private String updateLocationInfo(String message) throws ParseException, URISyntaxException {
        JSONObject jsonObject = (JSONObject) parser.parse(message);
        String locationName = (String) jsonObject.get("name");
        URI url = new URI(baseURL + "/" + locationName);
        RestClient.CLIENT.post(url, message, apiKey);
        return locationName;
    }

    public boolean shutdownLocation(String locationName) {
        System.out.println("Shutdown called fro the location " + locationName);
        String shutDownUrl = "http://mcvst02.eur.ad.sag:8080/parkingmgmt/locations" + "/" + locationName + "/_shutdown";
        System.out.println("Shutdown url is " + shutDownUrl);
        URI url = null;
        try {
            url = new URI(shutDownUrl);
        } catch (URISyntaxException e) {
            return false;
        }
        RestClient.Response response = null;
        try {
            response = RestClient.CLIENT.put(url, apiKey);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return response != null && response.getStatusCode().equals(RestClient.StatusCode.OK);
    }

    public boolean activateLocation(String locationName) {
        System.out.println("Activation called fro the location " + locationName);
        String activateUrl = "http://mcvst02.eur.ad.sag:8080/parkingmgmt/locations" + "/" + locationName + "/_activate";
        System.out.println("Activate url is " + activateUrl);
        URI url = null;
        try {
            url = new URI(activateUrl);
        } catch (URISyntaxException e) {
            return false;
        }
        RestClient.Response response = null;
        try {
            response = RestClient.CLIENT.put(url, apiKey);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return response != null && response.getStatusCode().equals(RestClient.StatusCode.OK);
    }
}

