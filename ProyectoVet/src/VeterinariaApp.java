import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class VeterinariaApp extends JFrame {
    
    // Estructuras Globales
    ArbolPacientes arbolPacientes = new ArbolPacientes();
    TablaHashMedicos hashMedicos = new TablaHashMedicos();
    PilaNavegacion historialNavegacion = new PilaNavegacion();
    
    // Panel Principal (CardLayout)
    JPanel mainPanel;
    CardLayout cardLayout;

    public VeterinariaApp() {
        setTitle("Sistema de Registro Veterinario");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cargar datos persistentes
        Persistencia.cargarDatos(arbolPacientes, hashMedicos);

        // Configuración de Layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Crear Vistas
        mainPanel.add(crearMenuPrincipal(), "MENU");
        mainPanel.add(crearFormularioPaciente(null), "FORM_PACIENTE");
        mainPanel.add(crearFormularioMedico(null), "FORM_MEDICO");
        mainPanel.add(crearVistaPacientes(), "LISTA_PACIENTES");
        mainPanel.add(crearVistaMedicos(), "LISTA_MEDICOS");

        // Agregar al Frame
        add(mainPanel);
        
        // Listener de cierre para guardar datos
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Persistencia.guardarDatos(arbolPacientes, hashMedicos);
            }
        });
    }

    // --- VISTA: MENU PRINCIPAL ---
    private JPanel crearMenuPrincipal() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JButton btnRegPac = new JButton("Registrar Paciente");
        JButton btnRegMed = new JButton("Registrar Médico");
        JButton btnVerPac = new JButton("Ver Pacientes");
        JButton btnVerMed = new JButton("Ver Médicos");

        btnRegPac.addActionListener(e -> mostrarVista("FORM_PACIENTE"));
        btnRegMed.addActionListener(e -> mostrarVista("FORM_MEDICO"));
        btnVerPac.addActionListener(e -> {
            mainPanel.add(crearVistaPacientes(), "LISTA_PACIENTES"); // Recargar vista
            mostrarVista("LISTA_PACIENTES");
        });
        btnVerMed.addActionListener(e -> {
            mainPanel.add(crearVistaMedicos(), "LISTA_MEDICOS"); // Recargar vista
            mostrarVista("LISTA_MEDICOS");
        });

        panel.add(btnRegPac);
        panel.add(btnRegMed);
        panel.add(btnVerPac);
        panel.add(btnVerMed);
        return panel;
    }

    // --- VISTA: FORMULARIO PACIENTE ---
    private JPanel crearFormularioPaciente(Paciente editar) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(8, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Datos del Paciente y Tutor"));

        JTextField txtNombreP = new JTextField(editar != null ? editar.nombre : "");
        JTextField txtRutaP = new JTextField(editar != null ? editar.rutaFotoPaciente : "ruta/foto_paciente.jpg");
        JTextField txtNombreT = new JTextField(editar != null ? editar.nombreTutor : "");
        JTextField txtRutaT = new JTextField(editar != null ? editar.rutaFotoTutor : "ruta/foto_tutor.jpg");
        JTextField txtCiT = new JTextField(editar != null ? editar.ciTutor : "");
        JTextField txtDom = new JTextField(editar != null ? editar.domicilio : "");
        JTextField txtFecha = new JTextField(editar != null ? editar.fechaNacimiento.toString() : "YYYY-MM-DD");
        JTextField txtRaza = new JTextField(editar != null ? editar.raza : "");

        form.add(new JLabel("Nombre Paciente:")); form.add(txtNombreP);
        form.add(new JLabel("Ruta Foto Paciente:")); form.add(txtRutaP);
        form.add(new JLabel("Nombre Tutor:")); form.add(txtNombreT);
        form.add(new JLabel("Ruta Foto Tutor:")); form.add(txtRutaT);
        form.add(new JLabel("CI Tutor:")); form.add(txtCiT);
        form.add(new JLabel("Domicilio:")); form.add(txtDom);
        form.add(new JLabel("Fecha Nac (YYYY-MM-DD):")); form.add(txtFecha);
        form.add(new JLabel("Raza:")); form.add(txtRaza);

        JButton btnGuardar = new JButton(editar == null ? "Registrar" : "Guardar Cambios");
        btnGuardar.addActionListener(e -> {
            try {
                if (editar == null) {
                    Paciente nuevo = new Paciente(
                        txtNombreP.getText(), txtRutaP.getText(), txtNombreT.getText(), txtRutaT.getText(),
                        txtCiT.getText(), txtDom.getText(), LocalDate.parse(txtFecha.getText()), txtRaza.getText()
                    );
                    arbolPacientes.insertar(nuevo);
                } else {
                    // Edición simple (en memoria los objetos son referencias, así que actualizar campos funciona)
                    editar.nombre = txtNombreP.getText();
                    editar.domicilio = txtDom.getText();
                    // ... actualizar resto
                }
                JOptionPane.showMessageDialog(this, "Guardado con éxito");
                mostrarVista("MENU");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en datos: " + ex.getMessage());
            }
        });
        
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> mostrarVista("MENU"));

        JPanel botones = new JPanel();
        botones.add(btnVolver);
        botones.add(btnGuardar);

        panel.add(form, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    // --- VISTA: LISTA PACIENTES (CREDENCIALES) ---
    private JPanel crearVistaPacientes() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Cabecera: Búsqueda
        JPanel header = new JPanel(new FlowLayout());
        JTextField txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar ID/Nombre");
        JButton btnVolver = new JButton("Menu");
        header.add(new JLabel("Buscador:"));
        header.add(txtBuscar);
        header.add(btnBuscar);
        header.add(btnVolver);
        
        btnVolver.addActionListener(e -> mostrarVista("MENU"));

        // Contenido: Lista Scrollable
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(content);

        // Lógica de llenado
        Runnable llenarContent = () -> {
            content.removeAll();
            java.util.List<Paciente> lista = new java.util.ArrayList<>();
            arbolPacientes.llenarLista(lista);
            
            // Filtro de búsqueda
            String query = txtBuscar.getText().toLowerCase();
            if (!query.isEmpty()) {
                lista.removeIf(p -> !p.nombre.toLowerCase().contains(query) && !p.id.toLowerCase().contains(query));
            }
            
            // Ordenamiento (Requisito)
            Ordenador.ordenarPorNombre(lista);

            for (Paciente p : lista) {
                content.add(crearCredencialPaciente(p));
                content.add(Box.createRigidArea(new Dimension(0, 10)));
            }
            content.revalidate();
            content.repaint();
        };

        btnBuscar.addActionListener(e -> llenarContent.run());
        llenarContent.run(); // Carga inicial

        panel.add(header, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearCredencialPaciente(Paciente p) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setMaximumSize(new Dimension(800, 150));

        // Info Básica
        JPanel info = new JPanel(new GridLayout(3, 1));
        info.add(new JLabel("Nombre: " + p.nombre));
        info.add(new JLabel("ID: " + p.id));
        info.add(new JLabel("Raza: " + p.raza));

        // Botones
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnInfo = new JButton("Información");
        JButton btnEditar = new JButton("Editar");
        JButton btnHistorial = new JButton(p.historial.entradas.size == 0 ? "Crear historial" : "Ver historial");

        btnInfo.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "Detalles:\nTutor: " + p.nombreTutor + "\nEdad: " + p.edad + " años\nDomicilio: " + p.domicilio));
        
        btnEditar.addActionListener(e -> {
            mainPanel.add(crearFormularioPaciente(p), "EDIT_PACIENTE");
            mostrarVista("EDIT_PACIENTE");
        });

        btnHistorial.addActionListener(e -> {
            mainPanel.add(crearVistaHistorial(p), "HISTORIAL");
            mostrarVista("HISTORIAL");
        });

        acciones.add(btnInfo);
        acciones.add(btnEditar);
        acciones.add(btnHistorial);
        
        // Usamos el método ayudante, pasamos la ruta y el tamaño (100x100)
        JLabel foto = cargarImagen(p.rutaFotoPaciente, 100, 100);

        card.add(foto, BorderLayout.WEST);
        card.add(info, BorderLayout.CENTER);
        card.add(acciones, BorderLayout.SOUTH);

        return card;
    }

    // --- VISTA: HISTORIAL ---
    private JPanel crearVistaHistorial(Paciente p) {
        JPanel panel = new JPanel(new BorderLayout());

        // Cabecera Requerida
        JPanel header = new JPanel(new GridLayout(2, 2));
        header.setBorder(new TitledBorder("Datos Generales"));
        header.add(new JLabel("Paciente: " + p.nombre));
        header.add(new JLabel("Tutor: " + p.nombreTutor));
        header.add(new JLabel("ID: " + p.id));
        header.add(new JLabel("Contacto: " + p.ciTutor)); // Usando CI como contacto por simplicidad
        
        // Selección de Médico para el historial
        JComboBox<String> comboMedicos = new JComboBox<>();
        for(Medico m : hashMedicos.obtenerTodos()) {
            comboMedicos.addItem(m.nombre);
        }
        header.add(new JLabel("Médico Tratante:"));
        header.add(comboMedicos);

        // Contenido (Lista de detalles)
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> listGui = new JList<>(listModel);
        
        Nodo<DetalleHistorial> actual = p.historial.entradas.cabeza;
        while(actual != null) {
            listModel.addElement(actual.dato.fecha + ": " + actual.dato.detalle);
            actual = actual.siguiente;
        }

        // Pie (Adicionar y Guardar)
        JPanel footer = new JPanel(new FlowLayout());
        JTextField txtDetalle = new JTextField(30);
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnGuardar = new JButton("Guardar (Volver)");

        btnAdicionar.addActionListener(e -> {
            if(!txtDetalle.getText().isEmpty()) {
                DetalleHistorial nuevo = new DetalleHistorial(txtDetalle.getText());
                p.historial.entradas.agregar(nuevo);
                listModel.addElement(nuevo.fecha + ": " + nuevo.detalle);
                txtDetalle.setText("");
            }
        });

        btnGuardar.addActionListener(e -> {
            Persistencia.guardarDatos(arbolPacientes, hashMedicos); // Guardado inmediato
            mostrarVista("LISTA_PACIENTES");
        });

        footer.add(new JLabel("Detalle:"));
        footer.add(txtDetalle);
        footer.add(btnAdicionar);
        footer.add(btnGuardar);

        panel.add(header, BorderLayout.NORTH);
        panel.add(new JScrollPane(listGui), BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }

    // --- VISTA: FORMULARIO MÉDICO ---
    private JPanel crearFormularioMedico(Medico editar) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        
        JTextField txtNom = new JTextField(editar != null ? editar.nombre : "");
        JTextField txtCI = new JTextField(editar != null ? editar.ci : "");
        JTextField txtFecha = new JTextField(editar != null ? editar.fechaNacimiento : "YYYY-MM-DD");
        JTextField txtMat = new JTextField(editar != null ? editar.matricula : "");
        JTextField txtEsp = new JTextField(editar != null ? editar.especialidad : "");
        JTextField txtFoto = new JTextField(editar != null ? editar.rutaFoto : "");

        form.add(new JLabel("Nombre:")); form.add(txtNom);
        form.add(new JLabel("CI:")); form.add(txtCI);
        form.add(new JLabel("Nacimiento:")); form.add(txtFecha);
        form.add(new JLabel("Matrícula:")); form.add(txtMat);
        form.add(new JLabel("Especialidad:")); form.add(txtEsp);
        form.add(new JLabel("Ruta Foto:")); form.add(txtFoto);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            Medico m = new Medico(txtNom.getText(), txtCI.getText(), txtFecha.getText(), txtMat.getText(), txtEsp.getText(), txtFoto.getText());
            hashMedicos.insertar(m);
            JOptionPane.showMessageDialog(this, "Médico Registrado");
            mostrarVista("MENU");
        });

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> mostrarVista("MENU"));
        
        JPanel bot = new JPanel(); bot.add(btnVolver); bot.add(btnGuardar);
        panel.add(form, BorderLayout.CENTER);
        panel.add(bot, BorderLayout.SOUTH);
        return panel;
    }

    // --- VISTA: LISTA MÉDICOS ---
    private JPanel crearVistaMedicos() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JPanel header = new JPanel();
        JTextField txtSearch = new JTextField(15);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnVolver = new JButton("Volver");
        header.add(new JLabel("Buscar Médico:")); header.add(txtSearch); header.add(btnBuscar); header.add(btnVolver);
        btnVolver.addActionListener(e -> mostrarVista("MENU"));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        Runnable cargar = () -> {
            content.removeAll();
            java.util.List<Medico> lista = hashMedicos.obtenerTodos();
            String q = txtSearch.getText().toLowerCase();
            
            for(Medico m : lista) {
                if(q.isEmpty() || m.nombre.toLowerCase().contains(q) || m.ci.contains(q)) {
                    JPanel card = new JPanel(new BorderLayout());
                    card.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    card.setMaximumSize(new Dimension(600, 100));
                    
                    JPanel datos = new JPanel(new GridLayout(2, 1));
                    datos.add(new JLabel(m.nombre + " - " + m.especialidad));
                    datos.add(new JLabel("CI: " + m.ci));
                    
                    JPanel btns = new JPanel();
                 // --- CÓDIGO CORREGIDO PARA LOS BOTONES DE MÉDICOS ---

                    
                    //btns.add(new JButton("Información"));
                    //btns.add(new JButton("Editar"));
                    JButton btnInfo = new JButton("Información");
                    JButton btnEditar = new JButton("Editar");

                    // Acción del botón INFORMACIÓN
                    btnInfo.addActionListener(ev -> JOptionPane.showMessageDialog(this, 
                        "Datos del Médico:\n" +
                        "Nombre: " + m.nombre + "\n" +
                        "CI: " + m.ci + "\n" +
                        "Matrícula: " + m.matricula + "\n" +
                        "Especialidad: " + m.especialidad + "\n" +
                        "Fecha Nac: " + m.fechaNacimiento
                    ));

                    // Acción del botón EDITAR
                    btnEditar.addActionListener(ev -> {
                        // Reutilizamos el formulario de médico pasándole el objeto 'm' actual
                        mainPanel.add(crearFormularioMedico(m), "EDIT_MEDICO");
                        mostrarVista("EDIT_MEDICO");
                    });

                    btns.add(btnInfo);
                    btns.add(btnEditar);

                    // --------------------------------------------------------
                    
                    
                    // Cargamos la foto del médico un poco más pequeña (80x80)
                    
                    card.add(cargarImagen(m.rutaFoto, 80, 80), BorderLayout.WEST);
                    card.add(datos, BorderLayout.CENTER);
                    card.add(btns, BorderLayout.SOUTH);
                    
                    content.add(card);
                    content.add(Box.createRigidArea(new Dimension(0,10)));
                }
            }
            content.revalidate(); content.repaint();
        };
        
        btnBuscar.addActionListener(e -> cargar.run());
        cargar.run();

        panel.add(header, BorderLayout.NORTH);
        panel.add(new JScrollPane(content), BorderLayout.CENTER);
        return panel;
    }

    private void mostrarVista(String nombreVista) {
        cardLayout.show(mainPanel, nombreVista);
    }
    
 // --- MÉTODO AUXILIAR PARA CARGAR FOTOS ---
    private JLabel cargarImagen(String ruta, int ancho, int alto) {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(ancho, alto));
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        if (ruta == null || ruta.isEmpty()) {
            label.setText("Sin Ruta");
            return label;
        }

        File f = new File(ruta);
        if (f.exists() && !f.isDirectory()) {
            try {
                ImageIcon icon = new ImageIcon(ruta);
                Image img = icon.getImage();
                // Redimensionar suavemente para ajustar al tamaño deseado
                Image newImg = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(newImg));
            } catch (Exception e) {
                label.setText("Error");
            }
        } else {
            label.setText("No encontrada");
        }
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VeterinariaApp().setVisible(true));
    }
}