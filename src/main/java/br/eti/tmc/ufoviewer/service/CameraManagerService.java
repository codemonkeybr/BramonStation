package br.eti.tmc.ufoviewer.service;

/**
 * Created by tessomc on 26/01/17.
 */
public interface CameraManagerService {
    boolean getLedState();

    void setLedState(boolean b) throws Exception;

    void readLedState() throws Exception;

    boolean getControlEnabled();
}
