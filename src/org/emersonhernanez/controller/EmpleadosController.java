/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emersonhernanez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.emersonhernandez.bean.Empleado;
import org.emersonhernandez.bean.TipoEmpleado;
import org.emersonhernandez.db.Conexion;
import org.emersonhernandez.main.Principal;

/**
 *
 * @author emers
 */
public class EmpleadosController implements Initializable {
    private Principal escenarioPrincipal;
    private ObservableList<Empleado> listaEmpleado;
    private ObservableList<TipoEmpleado> listaTipoEmpleado;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtNumeroEmpleado;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtNombres;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtGradoCocinero;
    @FXML private ComboBox cmbCodigoTipoEmpleado;
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colNumeroEmpleado;
    @FXML private TableColumn colApellidos;
    @FXML private TableColumn colNombres;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colTelefono;
    @FXML private TableColumn colGradoCocinero;
    @FXML private TableColumn colCodigoTIpoEmpleado;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    
    }
    
    
     public void cargarDatos(){
        tblEmpleados.setItems(getEmpleado());
        cmbCodigoTipoEmpleado.setItems(getTipoEmpleado());
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoEmpleado"));
        colNumeroEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("numeroEmpleado"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidosEmpleado"));
        colNombres.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombresEmpleado"));   
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccionEmpleado"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefonoContacto"));
        colGradoCocinero.setCellValueFactory(new PropertyValueFactory<Empleado, String>("gradoCocinero"));
        colCodigoTIpoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoTipoEmpleado"));

    }
     
    public ObservableList<Empleado> getEmpleado(){
        ArrayList<Empleado> lista = new ArrayList<Empleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarEmpleados");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add( new Empleado(resultado.getInt("codigoEmpleado"),
                        resultado.getInt("numeroEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("direccionEmpleado"),
                        resultado.getString("telefonoContacto"),
                        resultado.getString("gradoCocinero"),
                        resultado.getInt("codigoTipoEmpleado")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmpleado = FXCollections.observableList(lista);
    }
    

    public ObservableList<TipoEmpleado> getTipoEmpleado(){
        ArrayList<TipoEmpleado> lista = new ArrayList<TipoEmpleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarTipoEmpleados");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add( new TipoEmpleado(resultado.getInt("codigoTipoEmpleado"),
                        resultado.getString("descripcion")));
                       
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTipoEmpleado = FXCollections.observableList(lista);
    }
    
    
    public  TipoEmpleado bucarTipoEmpleado(int codigoTipoEmpleado){
        TipoEmpleado resultado = null;
        
        try{
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarTipoEmpleado (?)");
          procedimiento.setInt(1, codigoTipoEmpleado);
          ResultSet registro = procedimiento.executeQuery();
          while(registro.next()){
                resultado = new TipoEmpleado(registro.getInt("codigoTipoEmpleado"),
                registro.getString("descripcion"));   
          }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
     public void seleccionarElemento(){
        if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
        txtCodigoEmpleado.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNumeroEmpleado.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getNumeroEmpleado()));
        txtApellidos.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado()));
        txtNombres.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado()));
        txtDireccion.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getDireccionEmpleado())); 
        txtTelefono.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoContacto())); 
        txtGradoCocinero.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getGradoCocinero())); 
        cmbCodigoTipoEmpleado.getSelectionModel().select(bucarTipoEmpleado(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado()));
        }else{
                JOptionPane.showMessageDialog(null, "Seleccione un elemento");
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
        }
    }
     
    
     public void editar(){
        
        switch (tipoDeOperacion){
            case NINGUNO:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null ){
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Guardar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image ("/org/emersonhernandez/image/guardar.png"));
                    imgReporte.setImage(new Image ("/org/emersonhernandez/image/cancelar.png"));
                    activarControles();
                    cmbCodigoTipoEmpleado.setDisable(true);
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
       
            if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
                int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_NO_OPTION){
                    try{
                        PreparedStatement procedimieto = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarEmpleado(?)");
                        procedimieto.setInt(1, ((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                        listaEmpleado.remove(tblEmpleados.getSelectionModel().getSelectedIndex());
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
     
     
     public void guardar(){
       
            Empleado registro = new Empleado();
  
            registro.setNumeroEmpleado(Integer.parseInt(txtNumeroEmpleado.getText()));
            registro.setApellidosEmpleado(txtApellidos.getText());
            registro.setNombresEmpleado(txtNombres.getText());
            registro.setDireccionEmpleado(txtDireccion.getText());
            registro.setTelefonoContacto(txtTelefono.getText());
            registro.setGradoCocinero(txtGradoCocinero.getText());
            registro.setCodigoTipoEmpleado(((TipoEmpleado)cmbCodigoTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());

        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarEmpleado(?,?,?,?,?,?,?)");
            procedimiento.setInt(1, registro.getNumeroEmpleado());
            procedimiento.setString(2, registro.getApellidosEmpleado());
            procedimiento.setString(3, registro.getNombresEmpleado());
            procedimiento.setString(4, registro.getDireccionEmpleado() );
            procedimiento.setString(5, registro.getTelefonoContacto());
            procedimiento.setString(6, registro.getGradoCocinero());
            procedimiento.setInt(7, registro.getCodigoTipoEmpleado());
            
            procedimiento.execute();
            listaEmpleado.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
        cargarDatos(); 
      }
     
    
     
      public void actualizar(){
        try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarEmpleado (?, ?, ?, ?, ?, ?,?,?)");
                Empleado registro = (Empleado) tblEmpleados.getSelectionModel().getSelectedItem();
                registro.setNumeroEmpleado(Integer.parseInt(txtNumeroEmpleado.getText()));
                registro.setApellidosEmpleado(txtApellidos.getText());
                registro.setNombresEmpleado(txtNombres.getText());
                registro.setDireccionEmpleado(txtDireccion.getText());
                registro.setTelefonoContacto(txtTelefono.getText());
                registro.setGradoCocinero(txtGradoCocinero.getText());
                

                procedimiento.setInt(1, registro.getCodigoEmpleado());
                procedimiento.setInt(2, registro.getNumeroEmpleado());
                procedimiento.setString(3, registro.getApellidosEmpleado());
                procedimiento.setString(4, registro.getNombresEmpleado());
                procedimiento.setString(5, registro.getDireccionEmpleado());
                procedimiento.setString(6, registro.getTelefonoContacto());
                procedimiento.setString(7, registro.getGradoCocinero());
                procedimiento.setInt(8, registro.getCodigoTipoEmpleado());
                procedimiento.execute();

                procedimiento.execute();
                }catch(Exception e){
                    e.printStackTrace();

                }
    }
        
      
     
     public void activarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNumeroEmpleado.setEditable(true);
        txtApellidos.setEditable(true);
        txtNombres.setEditable(true);
        txtDireccion.setEditable(true); 
        txtTelefono.setEditable(true);
        txtGradoCocinero.setEditable(true);
        
    }     
    public void limpiarControles(){
        txtCodigoEmpleado.clear();
        txtNumeroEmpleado.clear();
        txtApellidos.clear();
        txtNombres.clear();
        txtDireccion.clear(); 
        txtTelefono.clear();
        txtGradoCocinero.clear(); 
        tblEmpleados.getSelectionModel().clearSelection();
    }
    
    public void desactivarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNumeroEmpleado.setEditable(false);
        txtApellidos.setEditable(false);
        txtNombres.setEditable(false);
        txtDireccion.setEditable(false); 
        txtTelefono.setEditable(false);
        txtGradoCocinero.setEditable(false);  
    }
    
    
    public EmpleadosController() {
    }

    public EmpleadosController(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
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
    
    public void ventanaTipoEmpleado(){
        escenarioPrincipal.ventanaTipoEmpleado();
    }
    
}
