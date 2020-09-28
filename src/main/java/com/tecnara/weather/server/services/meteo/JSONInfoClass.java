package com.tecnara.weather.server.services.meteo;

import java.util.List;

public class JSONInfoClass {
    private Main main;
    private List<Weather> weather;
    private Wind wind;
    private String name;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        //Temperatura,humedad,tiempoPrincipal, descripcion, velocidad del viento , nombre de poblacion
        return "Ciudad: " + this.getName() + "\n" +
                "Temperatura: " + (Float.parseFloat(main.getTemp()) - 273.15) + "\n" +
                "Humedad: " + main.getHumidity() + "\n" +
                "Tiempo principal: " + getWeather().get(0).getMain() + "\n" +
                "Descripcion: " + getWeather().get(0).getDescription() + "\n"+
                "Velocidad del viento: " + wind.getSpeed();
    }
}
