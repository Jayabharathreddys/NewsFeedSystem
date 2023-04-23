package com.jaya.newsfeed.utility;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Utility {
public boolean isUUID(String uuid){
    try{
        UUID.fromString(uuid);
        return true;
    }catch (Exception exp){
        return false;
    }
}

public UUID convertStringToUUID(String uuid){
    return isUUID(uuid) ? UUID.fromString(uuid): null;
}

    public static String numberOfMins(LocalDateTime actualTime){

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(actualTime, now);
        long ret = duration.toMinutes();
        String resp = ret+" min(s) ago";

        if(ret > 60) {
            ret = duration.toHours();
            resp = ret+" hour(s) ago";
        }
        if(ret > 24) {
            ret = duration.toDays();
            resp = ret+" day(s) ago";
        }

        return resp;
    }

}
