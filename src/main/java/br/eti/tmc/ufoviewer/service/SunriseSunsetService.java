package br.eti.tmc.ufoviewer.service;

import java.util.Date;

/**
 * Created by tessomc on 26/01/17.
 */
public interface SunriseSunsetService {
    void atualizaDados() throws Exception;

    Date getSunrise(String today) throws Exception;

    Date getSunset(String today) throws Exception;
}
