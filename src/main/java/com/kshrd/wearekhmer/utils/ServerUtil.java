package com.kshrd.wearekhmer.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerUtil {

    @Value("${app.image.serverName}")
    private String imageServerName;



    public String getImageServerName() {
        return imageServerName;
    }
}
