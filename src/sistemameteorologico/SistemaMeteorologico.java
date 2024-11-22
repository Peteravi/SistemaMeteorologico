package sistemameteorologico;

import modelo.Persona;
import modelo.WeatherStation;

public class SistemaMeteorologico {

    public static void main(String[] args) {
        // Crear el sujeto observado (WeatherStation)
        WeatherStation weatherStation = new WeatherStation();

        // Crear observadores (Personas)
        Persona persona1 = new Persona("001", "Juan");
        Persona persona2 = new Persona("002", "María");
        Persona persona3 = new Persona("003", "Luis");

        // Registrar observadores en el sujeto
        weatherStation.registerObserver(persona1);
        weatherStation.registerObserver(persona2);

        // Simular cambios de temperatura y notificar
        System.out.println("Cambiando temperatura a 8°C...");
        weatherStation.setTemperature(8); // Frío

        System.out.println("Cambiando temperatura a 22°C...");
        weatherStation.setTemperature(22); // Cálido

        // Desuscribir a un observador
        weatherStation.removeObserver(persona1);

        System.out.println("Cambiando temperatura a 35°C...");
        weatherStation.setTemperature(35); // Caluroso

        // Registrar otro observador
        weatherStation.registerObserver(persona3);

        System.out.println("Cambiando temperatura a 15°C...");
        weatherStation.setTemperature(15); // Templado
    }
}
