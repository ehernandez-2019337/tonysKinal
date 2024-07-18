/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emersonhernanez.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.emersonhernandez.bean.Empresa;
import org.emersonhernandez.bean.Servicios;
import org.emersonhernandez.db.Conexion;
import org.emersonhernandez.main.Principal;


public class ServiciosController implements Initializable  {
    private Principal escenarioPrincipal;
    @FXML private TextField txtCodigoServicio;
    @FXML private TextField txtTipoServicio;
    @FXML private TextField txtHora;
    @FXML private TextField txtMinutos;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtLugarServicio;
    @FXML private ComboBox cmbCodigoEmpresa;
    @FXML private GridPane grpFecha;
    @FXML private TableView tblServicios;
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colFechaServicio;
    @FXML private TableColumn colTipoServicio;
    @FXML private TableColumn colCodigoEmpresa;
    @FXML private TableColumn colHoraServicio;
    @FXML private TableColumn colTelefonoContacto;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportet;
    private enum operaciones{ GUARDAR, ELIMINAR, ACTUALIZAR,  NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Servicios> listaServicio;
    private ObservableList<Empresa> listaEmpresa;
    private DatePicker fecha;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       fecha = new DatePicker(Locale.ENGLISH);
            fecha.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            fecha.getCalendarView().todayButtonTextProperty().set("Today");
            fecha.getCalendarView().setShowWeeks(true);
            fecha.getStylesheets().add("/org/emersonhernandez/resource/TonysKinal.css");
            grpFecha.add(fecha, 3, 0);
            
            cargarDatos();
        
    }
    
    
    
     public void cargarDatos(){
        tblServicios.setItems(getServicios());
        //cmbCodigoEmpresa.setItems(getEmpresa());
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<Servicios, Integer> ("codigoServicio"));
        colFechaServicio.setCellValueFactory(new PropertyValueFactory<Servicios, Date> ("fechaDeServicio"));
        colTipoServicio.setCellValueFactory(new PropertyValueFactory<Servicios, String> ("tipoDeServicio"));
        colHoraServicio.setCellValueFactory(new PropertyValueFactory<Servicios, String> ("horaervicio"));
        colTelefonoContacto.setCellValueFactory(new PropertyValueFactory<Servicios, String> ("telefonoDeContacto"));
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Servicios, Integer>("codigoEmpresa"));
        

    }
    
    public ObservableList<Servicios> getServicios(){
        ArrayList<Servicios> lista = new ArrayList<Servicios>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarServicios()");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Servicios (resultado.getInt("codigoServicio"),
                        resultado.getDate("fechaDeServicio"),
                        resultado.getString("tipoServicio"),
                        resultado.getString("lugarServicio"),
                        resultado.getString("telefonoContacto"),
                        resultado.getString("horaervicio"),
                        resultado.getInt("codigoEmpresa")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return listaServicio = FXCollections.observableArrayList(lista);
    }

    public ServiciosController() {
    }

    public ServiciosController(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaEmpresas(){
        escenarioPrincipal.ventanaEmpresas();
    }
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    
}
