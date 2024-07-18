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
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import  javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.emersonhernandez.bean.Empresa;
import org.emersonhernandez.bean.Presupuesto;
import org.emersonhernandez.db.Conexion;
import org.emersonhernandez.main.Principal;
import org.emersonhernandez.report.GenerarReporte;

public class PresupuestoController implements Initializable {
    private enum operaciones{ GUARDAR, ELIMINAR, ACTUALIZAR,  NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Presupuesto> listaPresupuesto;
    private ObservableList<Empresa> listaEmpresa;
    private DatePicker fecha;
    @FXML private TextField txtCodigoPresupuesto;
    @FXML private TextField txtCantidadPresupuesto;
    @FXML private ComboBox cmbCodigoEmpresa;
    @FXML private GridPane grpFecha;
    @FXML private TableView tblPresupuestos;
    @FXML private TableColumn colCodigoPresupuesto;
    @FXML private TableColumn colFechaSolicitud;
    @FXML private TableColumn colCantidadPresupuesto;
    @FXML private TableColumn colCodigoEmpresa;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    private Principal escenarioPrincipal;
    
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
        tblPresupuestos.setItems(getPresupuesto());
        cmbCodigoEmpresa.setItems(getEmpresa());
        colCodigoPresupuesto.setCellValueFactory(new PropertyValueFactory<Presupuesto, Integer> ("codigoPresupuesto"));
        colFechaSolicitud.setCellValueFactory(new PropertyValueFactory<Presupuesto, Date> ("fechaSolicitud"));
        colCantidadPresupuesto.setCellValueFactory(new PropertyValueFactory<Presupuesto, Double>("cantidadPresupuesto"));
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Presupuesto, Integer>("codigoEmpresa"));
        

    }
    
    public ObservableList<Presupuesto> getPresupuesto(){
        ArrayList<Presupuesto> lista = new ArrayList<Presupuesto>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarPresupuestos()");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Presupuesto (resultado.getInt("codigoPresupuesto"),
                        resultado.getDate("fechaSolicitud"),
                        resultado.getDouble("cantidadPresupuesto"),
                        resultado.getInt("codigoEmpresa")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return listaPresupuesto = FXCollections.observableArrayList(lista);
    }
    
       public ObservableList<Empresa> getEmpresa(){
        ArrayList<Empresa> lista = new ArrayList<Empresa>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarEmpresas");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add( new Empresa(resultado.getInt("codigoEmpresa"),
                        resultado.getString("nombreEmpresa"),
                        resultado.getString("direccion"),
                        resultado.getString("telefono")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmpresa = FXCollections.observableList(lista);
    }
       
    public void seleccionarElemento(){
        if(tblPresupuestos.getSelectionModel().getSelectedItem() != null){
        txtCodigoPresupuesto.setText(String.valueOf(((Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem()).getCodigoPresupuesto()));
        fecha.selectedDateProperty().set(((Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem()).getFechaSolicitud());
        txtCantidadPresupuesto.setText(String.valueOf(((Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem()).getCantidadPresupuesto()));
        cmbCodigoEmpresa.getSelectionModel().select(buscarEmpresa(((Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
        }else{
                JOptionPane.showMessageDialog(null, "Seleccione un elemento");
                } 
        
    }
    
    
    public  Empresa buscarEmpresa(int codigoEmpresa){
        Empresa resultado = null;
        
        try{
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarEmpresa (?)");
          procedimiento.setInt(1, codigoEmpresa);
          ResultSet registro = procedimiento.executeQuery();
          while(registro.next()){
                resultado = new Empresa(registro.getInt("codigoEmpresa"),
                registro.getString("nombreEmpresa"),
                registro.getString("direccion"),
                registro.getString("telefono"));
                
              
          }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
             
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnReporte.setDisable(true);
                btnEditar.setDisable(true);
                imgNuevo.setImage(new Image ("/org/emersonhernandez/image/guardar.png"));
                imgEliminar.setImage(new Image ("/org/emersonhernandez/image/cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                
            break;
            
            case GUARDAR:
                guardar();
                
                limpiarControles();
                
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                imgNuevo.setImage(new Image ("/org/emersonhernandez/image/Agregar.png"));
                imgEliminar.setImage(new Image ("/org/emersonhernandez/image/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                limpiarControles();  
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image ("/org/emersonhernandez/image/Agregar.png"));
                imgEliminar.setImage(new Image ("/org/emersonhernandez/image/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                
            break;
        default:
       
            if(tblPresupuestos.getSelectionModel().getSelectedItem() != null){
                int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Eliminar Presupuesto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_NO_OPTION){
                    try{
                        PreparedStatement procedimieto = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarPresupuesto(?)");
                        procedimieto.setInt(1, ((Presupuesto)tblPresupuestos.getSelectionModel().getSelectedItem()).getCodigoPresupuesto());
                        listaPresupuesto.remove(tblPresupuestos.getSelectionModel().getSelectedIndex());
                        procedimieto.execute();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }   
            }else{
                JOptionPane.showMessageDialog(null, "Seleccione un elemento");
            }
            limpiarControles();     
        } 
    }
    
    
     public void editar(){
        
        switch (tipoDeOperacion){
            case NINGUNO:
                if (tblPresupuestos.getSelectionModel().getSelectedItem() != null ){
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Guardar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image ("/org/emersonhernandez/image/guardar.png"));
                    imgReporte.setImage(new Image ("/org/emersonhernandez/image/cancelar.png"));
                    activarControles();
                    cmbCodigoEmpresa.setDisable(true);
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Sleccione un elemento");
                }
                break;
                
            case ACTUALIZAR: 
                    actualizar();
                    limpiarControles();
                    desactivarControles();
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    imgEditar.setImage(new Image ("/org/emersonhernandez/image/Editar.png"));
                    imgReporte.setImage(new Image ("/org/emersonhernandez/image/Reporte.png"));
                    tipoDeOperacion = operaciones.NINGUNO;  
                    cargarDatos();
                break;
        }
        
    }  
     
      
    public void reporte(){
        switch(tipoDeOperacion)  {  
            case ACTUALIZAR: 
                    limpiarControles();
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    imgEditar.setImage(new Image ("/org/emersonhernandez/image/Editar.png"));
                    imgReporte.setImage(new Image ("/org/emersonhernandez/image/Reporte.png"));
                    tipoDeOperacion = operaciones.NINGUNO;  
            break;
            
            default: 
                    imprimirReporte();
                    break;
        }
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        int codEmpresa = Integer.valueOf(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
        parametros.put("codEmpresa", codEmpresa);
        GenerarReporte.mostrarReporte("ReporteGeneral.jasper", "Reporte General", parametros);
        
    }
    
    
    public void actualizar(){
        
            if(txtCantidadPresupuesto.getText().isEmpty() || fecha.getSelectedDate() == null){
                JOptionPane.showMessageDialog(null, "Ingrese los datos de forma correcta");
           }else {
                 try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarPresupuesto (?, ?, ?, ?)");
                Presupuesto registro = (Presupuesto) tblPresupuestos.getSelectionModel().getSelectedItem();
                registro.setFechaSolicitud(fecha.getSelectedDate());
                registro.setCantidadPresupuesto(Double.parseDouble(txtCantidadPresupuesto.getText()));


                procedimiento.setInt(1, registro.getCodigoPresupuesto());
                procedimiento.setDate(2, new java.sql.Date(registro.getFechaSolicitud().getTime()));
                procedimiento.setDouble(3, registro.getCantidadPresupuesto());
                procedimiento.setInt(4, registro.getCodigoEmpresa());
                procedimiento.execute();

                procedimiento.execute();
                }catch(Exception e){
                    e.printStackTrace();

                }

            }
        
    }
        
    
    
    public void guardar(){
        if(txtCantidadPresupuesto.getText().isEmpty() || fecha.getSelectedDate() == null){
            JOptionPane.showMessageDialog(null, "Ingrese los datos de forma correcta");
        }else {
            Presupuesto registro = new Presupuesto();
            registro.setFechaSolicitud(fecha.getSelectedDate());
            registro.setCantidadPresupuesto(Double.parseDouble(txtCantidadPresupuesto.getText()));
            registro.setCodigoEmpresa(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());

        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarPresupuesto(?,?,?)");
            procedimiento.setDate(1, new java.sql.Date(registro.getFechaSolicitud().getTime()));
            procedimiento.setDouble(2, registro.getCantidadPresupuesto());
            procedimiento.setInt(3, registro.getCodigoEmpresa());
            procedimiento.execute();
            listaPresupuesto.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
        cargarDatos(); 
            }
        
        
    }  
       
       
     public void activarControles(){
        txtCodigoPresupuesto.setEditable(false);
        txtCantidadPresupuesto.setEditable(true);
        cmbCodigoEmpresa.setDisable(false);
        fecha.setDisable(false);
        
    }  
          
     
    public void desactivarControles(){
        txtCodigoPresupuesto.setEditable(false);
        txtCantidadPresupuesto.setEditable(false);
        cmbCodigoEmpresa.setDisable(true);
        fecha.setDisable(false);

    }
    public void limpiarControles(){
        txtCodigoPresupuesto.clear();
        txtCantidadPresupuesto.clear();
        cmbCodigoEmpresa.setValue(null);
        fecha.setSelectedDate(null);
       tblPresupuestos.getSelectionModel().clearSelection();

    }
    
    public PresupuestoController() {
    }
    public PresupuestoController(Principal escenarioPrincipal) {
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
