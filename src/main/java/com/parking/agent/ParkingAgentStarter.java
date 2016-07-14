package com.parking.agent;

import com.parking.agent.subscriber.PubNubMessageSubscriber;

/**
 * Created by VST on 06-07-2016.
 */
public class ParkingAgentStarter {
    /**
     * @param args Arguments.
     */
    public static void main(String[] args)throws Exception {
        for (int i=0; i < args.length; i++) {
            System.out.println(i + " th element is " + args[i]);
        }
        PubNubMessageSubscriber subscriber =
                new PubNubMessageSubscriber(args[0], args[1], args[2], args[3], args[4], args[5]);
    }
}
