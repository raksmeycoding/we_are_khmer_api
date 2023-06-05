package com.kshrd.wearekhmer.oneSignal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.net.http.HttpClient;

@Data
@Builder
@AllArgsConstructor
public class Onesignal {


    private final String appId = "afc89b92-a380-4797-9c77-d9829ccb8f98";


    private final String restAPIKey = "NjM0ZTAzY2YtMmIzYS00MTQxLTkwMjAtYzg0YjQzNmI5NGE5";

}
