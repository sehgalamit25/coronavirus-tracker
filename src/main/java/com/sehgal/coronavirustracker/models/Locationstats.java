package com.sehgal.coronavirustracker.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Locationstats {
    private String state;
    private String country;
    private Integer latestTotalCases;

}
