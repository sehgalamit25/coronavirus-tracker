package com.sehgal.coronavirustracker.services;

import com.sehgal.coronavirustracker.models.Locationstats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    //"https://github.com/CSSEGISandData/COVID-19/blob/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
  //  private static String VIRUS_DATA_URL = "https://github.com/CSSEGISandData/COVID-19/blob/master/archived_data/archived_time_series/time_series_2019-ncov-Confirmed.csv";
    List<Locationstats> locationstatsList=new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<Locationstats> reflist=new ArrayList<>();
       /* HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
       StringReader csvBodyReader=new StringReader(response.body());*/

      //  System.out.println(response.body());
        //https://commons.apache.org/proper/commons-csv/user-guide.html
        Reader in = new FileReader("covid-global.csv");
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        for (CSVRecord record : records) {
            Locationstats locationstats= Locationstats.builder()
                    .state(record.get("Province/State"))
                    .country(record.get("Country/Region"))
                    .latestTotalCases(Integer.valueOf(record.get(record.size()-1)))
                    .build();
            System.out.println(locationstats);
            reflist.add(locationstats);

        }
        locationstatsList=reflist;


    }
}
