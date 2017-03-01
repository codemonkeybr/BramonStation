package br.eti.tmc.ufoviewer.entity;

import com.fasterxml.jackson.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sunrise",
        "sunset",
        "solar_noon",
        "day_length",
        "civil_twilight_begin",
        "civil_twilight_end",
        "nautical_twilight_begin",
        "nautical_twilight_end",
        "astronomical_twilight_begin",
        "astronomical_twilight_end"
})
public class Results {

    @JsonProperty("sunrise")
    private Date sunrise;
    @JsonProperty("sunset")
    private Date sunset;
    @JsonProperty("solar_noon")
    private Date solarNoon;
    @JsonProperty("day_length")
    private Integer dayLength;
    @JsonProperty("civil_twilight_begin")
    private Date civilTwilightBegin;
    @JsonProperty("civil_twilight_end")
    private Date civilTwilightEnd;
    @JsonProperty("nautical_twilight_begin")
    private Date nauticalTwilightBegin;
    @JsonProperty("nautical_twilight_end")
    private Date nauticalTwilightEnd;
    @JsonProperty("astronomical_twilight_begin")
    private Date astronomicalTwilightBegin;
    @JsonProperty("astronomical_twilight_end")
    private Date astronomicalTwilightEnd;
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sunrise")
    public Date getSunrise() {
        return sunrise;
    }

    @JsonProperty("sunrise")
    public void setSunrise(Date sunrise) {
        this.sunrise = sunrise;
    }

    @JsonProperty("sunset")
    public Date getSunset() {
        return sunset;
    }

    @JsonProperty("sunset")
    public void setSunset(Date sunset) {
        this.sunset = sunset;
    }

    @JsonProperty("solar_noon")
    public Date getSolarNoon() {
        return solarNoon;
    }

    @JsonProperty("solar_noon")
    public void setSolarNoon(Date solarNoon) {
        this.solarNoon = solarNoon;
    }

    @JsonProperty("day_length")
    public Integer getDayLength() {
        return dayLength;
    }

    @JsonProperty("day_length")
    public void setDayLength(Integer dayLength) {
        this.dayLength = dayLength;
    }

    @JsonProperty("civil_twilight_begin")
    public Date getCivilTwilightBegin() {
        return civilTwilightBegin;
    }

    @JsonProperty("civil_twilight_begin")
    public void setCivilTwilightBegin(Date civilTwilightBegin) {
        this.civilTwilightBegin = civilTwilightBegin;
    }

    @JsonProperty("civil_twilight_end")
    public Date getCivilTwilightEnd() {
        return civilTwilightEnd;
    }

    @JsonProperty("civil_twilight_end")
    public void setCivilTwilightEnd(Date civilTwilightEnd) {
        this.civilTwilightEnd = civilTwilightEnd;
    }

    @JsonProperty("nautical_twilight_begin")
    public Date getNauticalTwilightBegin() {
        return nauticalTwilightBegin;
    }

    @JsonProperty("nautical_twilight_begin")
    public void setNauticalTwilightBegin(Date nauticalTwilightBegin) {
        this.nauticalTwilightBegin = nauticalTwilightBegin;
    }

    @JsonProperty("nautical_twilight_end")
    public Date getNauticalTwilightEnd() {
        return nauticalTwilightEnd;
    }

    @JsonProperty("nautical_twilight_end")
    public void setNauticalTwilightEnd(Date nauticalTwilightEnd) {
        this.nauticalTwilightEnd = nauticalTwilightEnd;
    }

    @JsonProperty("astronomical_twilight_begin")
    public Date getAstronomicalTwilightBegin() {
        return astronomicalTwilightBegin;
    }

    @JsonProperty("astronomical_twilight_begin")
    public void setAstronomicalTwilightBegin(Date astronomicalTwilightBegin) {
        this.astronomicalTwilightBegin = astronomicalTwilightBegin;
    }

    @JsonProperty("astronomical_twilight_end")
    public Date getAstronomicalTwilightEnd() {
        return astronomicalTwilightEnd;
    }

    @JsonProperty("astronomical_twilight_end")
    public void setAstronomicalTwilightEnd(Date astronomicalTwilightEnd) {
        this.astronomicalTwilightEnd = astronomicalTwilightEnd;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Results{" +
                "sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", solarNoon='" + solarNoon + '\'' +
                ", dayLength=" + dayLength +
                ", civilTwilightBegin='" + civilTwilightBegin + '\'' +
                ", civilTwilightEnd='" + civilTwilightEnd + '\'' +
                ", nauticalTwilightBegin='" + nauticalTwilightBegin + '\'' +
                ", nauticalTwilightEnd='" + nauticalTwilightEnd + '\'' +
                ", astronomicalTwilightBegin='" + astronomicalTwilightBegin + '\'' +
                ", astronomicalTwilightEnd='" + astronomicalTwilightEnd + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}