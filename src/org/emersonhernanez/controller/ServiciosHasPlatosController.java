
package org.emersonhernanez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.emersonhernandez.bean.Plato;
import org.emersonhernandez.bean.ServiciosHasPlatos;
import org.emersonhernandez.main.Principal;


public class ServiciosHasPlatosController implements Initializable {
    private Principal escenarioPrincipal;
    @FXML private TextField txtServicioCodigoServicio;
    @FXML private ComboBox cmbCodigoPlato;
    @FXML private ComboBox cmbCodigoServicio;
    @FXML private TableView tblProductosHasPlatos;
    @FXML private TableColumn colServicioCodigoServicio;
    @FXML private TableColumn colCodigoPlato;
    @FXML private TableColumn colCodigoServicio;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    private enum operaciones{ GUARDAR, ELIMINAR, ACTUALIZAR,  NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<ServiciosHasPlatos> listaProductosHasPlato;
    private ObservableList<Plato> listaPlato;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
