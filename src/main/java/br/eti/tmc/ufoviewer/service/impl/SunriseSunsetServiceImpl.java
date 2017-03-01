package br.eti.tmc.ufoviewer.service.impl;

import br.eti.tmc.ufoviewer.entity.SunriseSunsetApi;
import br.eti.tmc.ufoviewer.service.SunriseSunsetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * Created by tesso on 18/01/17.
 */
public class SunriseSunsetServiceImpl implements SunriseSunsetService{

    @Value("${latitude}")
    String latitude;
    @Value("${longitude}")
    String longitude;


    SunriseSunsetApi ssToday;
    SunriseSunsetApi ssTomorrow;




    private static final Logger log = LoggerFactory.getLogger(SunriseSunsetServiceImpl.class);

    public Date getSunset(String param) throws Exception {
        switch (param) {
            case "today":
                return ssToday.getResults().getSunset();
            case "tomorrow":
                return ssTomorrow.getResults().getSunset();
        }
    return null;
    }

    public Date getSunrise(String param) throws Exception {
        switch (param) {
            case "today":
                return ssToday.getResults().getSunrise();
            case "tomorrow":
                return ssTomorrow.getResults().getSunrise();
        }
        return null;
    }

    public SunriseSunsetApi buscaDados(String param){
        log.info("Looking up sunrise/sunset data for "+param);
        String url = "http://api.sunrise-sunset.org/json?lat="+latitude+"&lng="+longitude+"&formatted=0&date="+param;

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, SunriseSunsetApi.class);

    }

    public void atualizaDados() throws Exception{

        ssToday = null;
        ssTomorrow = null;

        ssToday = buscaDados("today");
        ssTomorrow = buscaDados("tomorrow");
        if (ssToday == null || !ssToday.getStatus().equals("OK")) throw new Exception("Erro ao atualizar Dados");
        if (ssTomorrow == null || !ssTomorrow.getStatus().equals("OK")) throw new Exception("Erro ao atualizar Dados");
    }
}
