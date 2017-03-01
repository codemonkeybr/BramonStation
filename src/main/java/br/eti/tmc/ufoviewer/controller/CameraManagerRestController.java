package br.eti.tmc.ufoviewer.controller;

import br.eti.tmc.ufoviewer.entity.EventType;
import br.eti.tmc.ufoviewer.entity.State;
import br.eti.tmc.ufoviewer.service.CameraManagerService;
import br.eti.tmc.ufoviewer.service.SunriseSunsetService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tessomc on 26/01/17.
 */
@RestController
public class CameraManagerRestController {


    @Inject
    private SunriseSunsetService ss;

    @Inject
    private CameraManagerService cms;

    // internal class members
    private Timer timer;

    @Value("${latitude}")
    String latitude;
    @Value("${longitude}")
    String longitude;
    private EventType nextEvent;
    private Date nextSunriseDate;
    private Date nextSunsetDate;


    private State state;


    @RequestMapping(value = "/camera", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> statusTest(@RequestParam(value = "param", required = false) String atributo) {
        try {

            if (!validaControleHabilitado())
                return new ResponseEntity<String>(new String(), HttpStatus.NOT_FOUND);

            switch (atributo) {
                case "on":
                    cms.setLedState(true);
                    break;
                case "off":
                    cms.setLedState(false);

            }

            String retorno = "Status: " + (cms.getLedState() ? "ON" : "OFF");
            return new ResponseEntity<String>(retorno, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("ERROR", HttpStatus.OK);
        }

    }

    @PostConstruct
    public void start() {

        if (!validaControleHabilitado())
            return;

        // create timer, GPIO controller
        timer = new Timer();


        // schedule starting event; apply initial power controller state
        try {
            switch (scheduleNextEvent()) {
                case SunriseToday: {
                    // if the next event is sunrise, then turn power ON
                    //powerController.high();
                    cms.setLedState(true);
                    break;
                }
                case SunsetToday: {
                    // if the next event is sunset, then turn power OFF
                    //powerController.low();
                    cms.setLedState(false);
                    break;
                }
                case SunriseTomorrow: {
                    // if the next event is sunrise, then turn power ON
                    //powerController.high();
                    cms.setLedState(true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/cmd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<State> status(@RequestParam(value = "param", required = false) String atributo) {
        try {

           if (!validaControleHabilitado())
               return new ResponseEntity<>(new State(), HttpStatus.NOT_FOUND);

            if (atributo != null)
                switch (atributo) {
                    case "on":
                        // turn ON power
                        cms.setLedState(true);

                        System.out.println("---------------------------------");
                        System.out.println("[OVERRIDE] POWER STATE ON");
                        System.out.println("---------------------------------");
                        break;
                    case "off":
                        // turn OFF power
                        cms.setLedState(false);

                        System.out.println("---------------------------------");
                        System.out.println("[OVERRIDE] POWER STATE OFF");
                        System.out.println("---------------------------------");
                        break;
                    case "read":
                        // read state from arduino
                        cms.readLedState();

                        System.out.println("---------------------------------");
                        System.out.println("[READ STATE CMD] SENT");
                        System.out.println("---------------------------------");
                        break;
                    case "status":
                        // determine and display current power controller state
                        if (cms.getLedState() == true) {
                            System.out.println("---------------------------------");
                            System.out.println("[STATUS] POWER STATE IS : ON");
                            System.out.println("---------------------------------");
                        } else {
                            System.out.println("---------------------------------");
                            System.out.println("[STATUS] POWER STATE IS : OFF");
                            System.out.println("---------------------------------");
                        }
                        break;
                    case "time":
                        // display current date/time
                        System.out.println("---------------------------------");
                        System.out.println("[CURRENT TIME] ");
                        System.out.println(new Date());
                        System.out.println("---------------------------------");
                        break;
                    case "sunrise":
                        // display sunrise date/time
                        System.out.println("---------------------------------");
                        System.out.println("[NEXT SUNRISE] ");
                        System.out.println(" @ " + nextSunriseDate);
                        System.out.println("---------------------------------");
                        break;
                    case "sunset":
                        // display sunset date/time
                        System.out.println("---------------------------------");
                        System.out.println("[NEXT SUNSET] ");
                        System.out.println(" @ " + nextSunsetDate);
                        System.out.println("---------------------------------");
                        break;
                    case "coords":
                        System.out.println("---------------------------------");
                        System.out.println("[LONGITUDE] = " + longitude);
                        System.out.println("[LATITUDE]  = " + latitude);
                        System.out.println("---------------------------------");
                        break;
                    case "next":
                        switch (nextEvent) {
                            case SunriseToday: {
                                System.out.println("-----------------------------------");
                                System.out.println("[NEXT EVENT] SUNRISE TODAY ");
                                System.out.println("  @ " + nextSunriseDate);
                                System.out.println("-----------------------------------");
                                break;
                            }
                            case SunsetToday: {
                                System.out.println("-----------------------------------");
                                System.out.println("[NEXT EVENT] SUNSET TODAY");
                                System.out.println("  @ " + nextSunsetDate);
                                System.out.println("-----------------------------------");
                                break;
                            }
                            case SunriseTomorrow: {
                                System.out.println("-----------------------------------");
                                System.out.println("[NEXT EVENT] SUNRISE TOMORROW");
                                System.out.println("  @ " + nextSunriseDate);
                                System.out.println("-----------------------------------");
                                break;
                            }
                        }
                }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new State(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        updateState();
        return new ResponseEntity<>(state, HttpStatus.OK);
    }

    private boolean validaControleHabilitado() {
        if(!cms.getControlEnabled()) {
            System.out.println("---------------------------------");
            System.out.println("[CAMERA] CONTROL NOT ENABLED");
            System.out.println("---------------------------------");
            return false;
        }
        return true;
    }

    private void updateState() {

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss zzz");
        System.out.println();
        state = new State();
        state.setNextEvent(nextEvent);
        state.setNextSunrise(sdf1.format(nextSunriseDate));
        state.setNextSunset(sdf1.format(nextSunsetDate));
        state.setPowered(cms.getLedState());
        state.setCurrentTime(sdf1.format(new Date()));
    }

    private synchronized EventType scheduleNextEvent() throws Exception {

        // get sunrise and sunset time for today
        Date today = new Date();
        ss.atualizaDados();
        Date today_sunrise = ss.getSunrise("today");
        Date today_sunset = ss.getSunset("today");

        // get sunrise and sunset time for tomorrow

        Date tomorrow_sunrise = ss.getSunrise("tomorrow");
        Date tomorrow_sunset = ss.getSunset("tomorrow");

        // determine if sunrise or sunset is the next event
        if (today.after(today_sunset)) {
            // get tomorrow's date time
            System.out.println("-----------------------------------");
            System.out.println("[SCHEDULED] NEXT EVENT: SUNRISE    ");
            System.out.println("  @ " + tomorrow_sunrise);
            System.out.println("-----------------------------------");

            // schedule tomorrow's sunrise as next event
            timer.schedule(new SunriseTask(), tomorrow_sunrise);

            // set cache next sunrise and sunset variables
            nextSunriseDate = tomorrow_sunrise;
            nextSunsetDate = tomorrow_sunset;

            // return next event
            nextEvent = EventType.SunriseTomorrow;
            return nextEvent;
        } else if (today.after(today_sunrise)) {
            System.out.println("-----------------------------------");
            System.out.println("[SCHEDULED] NEXT EVENT: SUNSET     ");
            System.out.println("  @ " + today_sunset);
            System.out.println("-----------------------------------");

            // schedule sunset as next event
            timer.schedule(new SunsetTask(), today_sunset);

            // set cache next sunrise and sunset variables
            nextSunriseDate = tomorrow_sunrise;
            nextSunsetDate = today_sunset;

            // return next event
            nextEvent = EventType.SunsetToday;
            return nextEvent;
        } else {
            System.out.println("-----------------------------------");
            System.out.println("[SCHEDULED] NEXT EVENT: SUNRISE    ");
            System.out.println("  @ " + today_sunrise);
            System.out.println("-----------------------------------");

            // schedule sunrise as next event
            timer.schedule(new SunriseTask(), today_sunrise);

            // set cache next sunrise and sunset variables
            nextSunriseDate = today_sunrise;
            nextSunsetDate = today_sunset;

            // return next event
            nextEvent = EventType.SunriseToday;
            return nextEvent;
        }
    }


    /**
     * This class is invoked as a callback at sunrise time and it
     * turns on the attached power controller to the Raspberry Pi
     * using the Pi4J API.
     *
     * @author Robert Savage
     */
    private class SunriseTask extends TimerTask {
        @Override
        public void run() {
            try {
                // turn OFF power
                cms.setLedState(false);

                System.out.println("-----------------------------------");
                System.out.println("[SUNRISE] POWER HAS BEEN TURNED OFF");
                System.out.println("-----------------------------------");

                // schedule next event

                scheduleNextEvent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This class is invoked as a callback at sunset time and it
     * turns off the attached power controller to the Raspberry Pi
     * using the Pi4J API.
     *
     * @author Robert Savage
     */
    private class SunsetTask extends TimerTask {
        @Override
        public void run() {
            try {
                // turn ON power
                cms.setLedState(true);

                System.out.println("-----------------------------------");
                System.out.println("[SUNSET]  POWER HAS BEEN TURNED ON");
                System.out.println("-----------------------------------");

                // schedule next event

                scheduleNextEvent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
