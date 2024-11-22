package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase WeatherStation.
 * Representa al sujeto observado que mantiene la temperatura y notifica a los observadores.
 */
public class WeatherStation {
    private float temperature;
    private String category;
    private List<Observer> observers;

    public WeatherStation() {
        this.temperature = 20.0f;
        this.category = "Normal";
        this.observers = new ArrayList<>();
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
        this.category = categorizeTemperature(temperature);
        notifyObservers();
    }

    private String categorizeTemperature(int temperature) {
        if (temperature >= 30) {
            return "Calor";
        } else if (temperature <= 10) {
            return "Frío";
        } else {
            return "Normal";
        }
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature, category);
        }
    }
}
