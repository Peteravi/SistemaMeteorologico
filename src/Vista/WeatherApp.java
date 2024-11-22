package vista;

import modelo.Persona;
import modelo.WeatherStation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class WeatherApp extends JFrame {
    private WeatherStation weatherStation;
    private HashMap<String, Persona> personas;

    private JTextField txtCodigo, txtNombre;
    private JComboBox<String> comboSuscripcion;
    private JTextArea txtNotificaciones;
    private JLabel lblTemperatura;
    private JSlider sliderTemperatura;
    private JButton btnBuscar;

    public WeatherApp() {
        // Inicializar sistema meteorológico y lista de personas
        weatherStation = new WeatherStation();
        personas = new HashMap<>();

        // Configurar ventana principal
        setTitle("Sistema Meteorológico");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        // Panel para gestionar personas
        JPanel panelGestion = new JPanel(new GridLayout(7, 2, 15, 15));
        panelGestion.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), "Gestión de Personas"));
        panelGestion.setBackground(new Color(255, 255, 255));

        panelGestion.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelGestion.add(txtCodigo);

        panelGestion.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelGestion.add(txtNombre);

        panelGestion.add(new JLabel("Suscripción:"));
        comboSuscripcion = new JComboBox<>(new String[]{"No Suscrito", "Suscrito"});
        panelGestion.add(comboSuscripcion);

        // Botones con bordes redondeados
        JButton btnCrear = new JButton("Crear Persona");
        JButton btnActualizar = new JButton("Actualizar Persona");
        customizeButton(btnCrear);
        customizeButton(btnActualizar);
        
        btnBuscar = new JButton("Buscar Persona");
        customizeButton(btnBuscar);

        panelGestion.add(btnCrear);
        panelGestion.add(btnActualizar);
        panelGestion.add(btnBuscar);

        // Panel para simulador de temperatura
        JPanel panelSimulador = new JPanel(new BorderLayout());
        panelSimulador.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), "Simulador de Temperatura"));
        panelSimulador.setBackground(new Color(255, 255, 255));

        lblTemperatura = new JLabel("Temperatura: 20°C", SwingConstants.CENTER);
        lblTemperatura.setFont(new Font("Arial", Font.BOLD, 16));
        sliderTemperatura = new JSlider(0, 40, 20);
        sliderTemperatura.setMajorTickSpacing(10);
        sliderTemperatura.setPaintTicks(true);
        sliderTemperatura.setPaintLabels(true);

        panelSimulador.add(lblTemperatura, BorderLayout.NORTH);
        panelSimulador.add(sliderTemperatura, BorderLayout.CENTER);

        // Panel para notificaciones
        JPanel panelNotificaciones = new JPanel(new BorderLayout());
        panelNotificaciones.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), "Notificaciones"));
        panelNotificaciones.setBackground(new Color(255, 255, 255));

        txtNotificaciones = new JTextArea();
        txtNotificaciones.setEditable(false);
        txtNotificaciones.setLineWrap(true);
        txtNotificaciones.setBackground(new Color(230, 230, 230));
        txtNotificaciones.setFont(new Font("Arial", Font.PLAIN, 12));
        panelNotificaciones.add(new JScrollPane(txtNotificaciones), BorderLayout.CENTER);

        // Añadir paneles a la ventana
        add(panelGestion, BorderLayout.WEST);
        add(panelSimulador, BorderLayout.CENTER);
        add(panelNotificaciones, BorderLayout.SOUTH);

        // Acción: Crear persona
        btnCrear.addActionListener(e -> crearPersona());

        // Acción: Actualizar persona
        btnActualizar.addActionListener(e -> actualizarPersona());

        // Acción: Buscar persona
        btnBuscar.addActionListener(e -> buscarPersona());

        // Acción: Cambiar temperatura
        sliderTemperatura.addChangeListener(e -> {
            int temperatura = sliderTemperatura.getValue();
            lblTemperatura.setText("Temperatura: " + temperatura + "°C");
            weatherStation.setTemperature(temperatura);
        });
    }

    private void customizeButton(JButton button) {
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180)));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 40));
    }

    private void crearPersona() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        boolean suscrito = comboSuscripcion.getSelectedItem().equals("Suscrito");

        // Validación de campos vacíos
        if (codigo.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar si la persona ya existe
        if (personas.containsKey(codigo)) {
            JOptionPane.showMessageDialog(this, "Ya existe una persona con este código.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear persona y agregarla a la lista
        Persona persona = new Persona(codigo, nombre);
        personas.put(codigo, persona);

        // Registrar a la persona si está suscrita
        if (suscrito) {
            weatherStation.registerObserver(persona);
        }

        // Limpiar campos
        limpiarCampos();

        // Mostrar notificación
        txtNotificaciones.append(String.format("Persona %s (Código: %s) %s. Suscripción: %s\n", nombre, codigo, "creada", suscrito ? "Activa" : "Inactiva"));
    }

    private void actualizarPersona() {
        String codigo = txtCodigo.getText().trim();
        boolean suscrito = comboSuscripcion.getSelectedItem().equals("Suscrito");

        // Validación de campos vacíos
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el código de la persona.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si la persona existe
        if (!personas.containsKey(codigo)) {
            JOptionPane.showMessageDialog(this, "La persona no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener persona y actualizar suscripción
        Persona persona = personas.get(codigo);
        if (suscrito) {
            weatherStation.registerObserver(persona);
        } else {
            weatherStation.removeObserver(persona);
        }

        // Limpiar campos
        limpiarCampos();

        // Mostrar notificación
        txtNotificaciones.append(String.format("Suscripción de %s (Código: %s) %s.\n", persona.getNombre(), persona.getCodigo(), suscrito ? "activada" : "desactivada"));
    }

    private void buscarPersona() {
        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el código de la persona.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!personas.containsKey(codigo)) {
            JOptionPane.showMessageDialog(this, "Persona no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Persona persona = personas.get(codigo);
        JOptionPane.showMessageDialog(this, "Persona encontrada: " + persona.getNombre(), "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        comboSuscripcion.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WeatherApp app = new WeatherApp();
            app.setVisible(true);
        });
    }
}
