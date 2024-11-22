package modelo;

/**
 * Interfaz Observer.
 * Representa a los observadores que serán notificados por el sujeto cuando ocurra un cambio.
 */
public interface Observer {
    /**
     * Método que será llamado cuando el sujeto notifique a los observadores.
     * 
     * @param temperature La nueva temperatura registrada.
     * @param category    La categoría correspondiente a la temperatura.
     */
    void update(float temperature, String category);
}
