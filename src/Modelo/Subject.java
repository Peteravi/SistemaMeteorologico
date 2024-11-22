package modelo;

import java.util.List;

/**
 * Interfaz Subject.
 * Representa al sujeto observado que notifica a los observadores sobre cambios en su estado.
 */
public interface Subject {
    /**
     * Registra un nuevo observador.
     * 
     * @param observer El observador a registrar.
     */
    void registerObserver(Observer observer);

    /**
     * Elimina un observador existente.
     * 
     * @param observer El observador a eliminar.
     */
    void removeObserver(Observer observer);

    /**
     * Notifica a todos los observadores sobre un cambio en el estado.
     */
    void notifyObservers();
}
