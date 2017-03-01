package br.eti.tmc.ufoviewer.service;


import br.eti.tmc.ufoviewer.entity.Captura;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by tessomc on 04/12/16.
 */
public interface MotorArquivoService {
    List<Captura> varrePorCapturas(File file);

    List<Captura> filtrarCapturasPor(List<Captura> capturas, Date dataInicio, Date dataFim, Boolean apenasAnalisadas);
    String getCaminho();
}
