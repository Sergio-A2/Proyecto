package javafx_archetype_simple.Proyecto;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Interfaz extends Application {

    // Variable temporal 
    private String inputActual = "";
    private Label pantallaDisplay;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20px;");

        // Título simple
        Label titulo = new Label("Interfaz");
        
        // Pantalla donde aparecen los números 
        pantallaDisplay = new Label("---");
        pantallaDisplay.setStyle("-fx-font-size: 24px; -fx-border-color: black; -fx-padding: 10px; -fx-min-width: 150px; -fx-alignment: center;");

        // Panel de botones numéricos
        FlowPane teclado = new FlowPane();
        teclado.setAlignment(Pos.CENTER);
        teclado.setHgap(10);
        teclado.setVgap(10);
        teclado.setMaxWidth(250);

        for (int i = 0; i <= 9; i++) {
            Button btn = new Button(String.valueOf(i));
            btn.setPrefSize(50, 50); // Botones cuadrados grandes
            
            int numero = i;
            btn.setOnAction(e -> {
                if (inputActual.length() < 4) {
                    inputActual += numero;
                    pantallaDisplay.setText(inputActual);
                } else {
                    System.out.println("Max 4 digitos alcanzado.");
                }
            });
            
            teclado.getChildren().add(btn);
        }

        // Botones de acción 
        HBox acciones = new HBox(15);
        acciones.setAlignment(Pos.CENTER);

        Button btnBorrar = new Button("Borrar");
        btnBorrar.setOnAction(e -> {
            inputActual = "";
            pantallaDisplay.setText("---");
        });

        Button btnJugar = new Button("Jugar / Probar");
        btnJugar.setStyle("-fx-background-color: lightgreen;");
        btnJugar.setOnAction(e -> {
            // Aquí es donde falta conectar la lógica del arbol y la cola
            System.out.println("No hay arbol");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Funciona.");
            alert.show();
        });

        acciones.getChildren().addAll(btnBorrar, btnJugar);

        // Agregar todo a la ventana
        root.getChildren().addAll(titulo, pantallaDisplay, teclado, acciones);

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Prueba");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}