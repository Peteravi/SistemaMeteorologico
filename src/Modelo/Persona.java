package modelo;

/**
 * Clase Persona.
 * Representa a un observador concreto que responde a cambios en el sujeto observado.
 */
public class Persona implements Observer {
    private String codigo;
    private String nombre;

    public Persona(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    @Override
    public void update(float temperature, String category) {
        System.out.println("Notificación para " + nombre + ": Temperatura actual " + temperature + "°C, Categoría: " + category);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
}
