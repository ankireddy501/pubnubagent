package com.parking.agent.subscriber;

import com.pubnub.api.Callback;
import com.pubnub.api.PubnubException;
import org.json.JSONArray;

/**
 * Created by VST on 06-07-2016.
 */
public class LocationListener extends Thread {

    private boolean flag = true;
    private String messageSent = null;
    private String messageReceived = null;
    private String locationName = null;
    private boolean isActive = true;
    private PubNubMessageSubscriber subscriber = null;

    public LocationListener(String locationName, PubNubMessageSubscriber subscriber) {
        this.locationName = locationName;
        this.subscriber = subscriber;
        this.start();
    }

    @Override
    public void run() {
        try {
            subscriber.getPubnub().subscribe(locationName + "_response", new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    messageReceived = (String) message;
                }
            });
        } catch (PubnubException e) {
            e.printStackTrace();
        }

        while (flag) {
            messageSent = randomAlphaNumeric(5);

            subscriber.getPubnub().publish(locationName, messageSent, new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    System.out.println("Message is sent to the queue");
                }
            });

            System.out.println("Random message sent to " + locationName + " is " + messageSent);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                flag = false;
            }

            int counter = 0;
            boolean stillDinReceive = true;
            while (counter < 3 && stillDinReceive) {
                System.out.println(counter + " th iteration.");
                System.out.println("Random message received from " + locationName + " is " + messageReceived);
                if (!messageSent.equals(messageReceived)) {
                    if (counter == 2) {
                        if (isActive && subscriber.shutdownLocation(locationName)) {
                            isActive = false;
                        }
                    }
                } else {
                    stillDinReceive = false;
                    if (!isActive && subscriber.activateLocation(locationName)) {
                        isActive = true;
                    }
                }
                counter ++;
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    flag = false;
                }
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                flag = false;
            }

            messageSent = null;
            messageReceived = null;
        }
    }

    private String randomAlphaNumeric(int count) {
        final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

}
