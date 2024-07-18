/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emersonhernanez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.emersonhernandez.bean.TipoEmpleado;
import org.emersonhernandez.db.Conexion;
import org.emersonhernandez.main.Principal;


public class TipoEmpleadoController implements Initializable {
   private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal; 
    private ObservableList<TipoEmpleado> listaTipoEmpleado; 
    @FXML private TextField txtCodigoTipoEmpleado;
    @FXML private TextField txtDescripcionTipoEmpleado;
    @FXML private TableView tblTipoEmpleado;
    @FXML private TableColumn colCodigoTipoEmpleado;
    @FXML private TableColumn colDescipcionTipoEmpleado;
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
        tblTipoEmpleado.setItems(getTipoEmpleado());
        colCodigoTipoEmpleado.setCellValueFactory(new PropertyValueFactory<TipoEmpleado, Integer>("codigoTipoEmpleado"));
        colDescipcionTipoEmpleado.setCellValueFactory(new PropertyValueFactory<TipoEmpleado, String>("descripcionTipoEmpleado"));
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
    
    public void SeleccionarElemento(){
        if(tblTipoEmpleado.getSelectionModel().getSelectedItem() != null ){
            txtCodigoTipoEmpleado.setText(String.valueOf(((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado()));
            txtDescripcionTipoEmpleado.setText(String.valueOf(((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem()).getDescripcionTipoEmpleado()));
        }else{
            JOptionPane.showMessageDialog(null, "Debe selecciobar un elemento");
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
                if(tblTipoEmpleado.getSelectionModel().getSelectedItem() !=null){
                  int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Eliminar TipoEmpleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarTipoEmpleado (?)");
                            procedimiento.setInt(1,((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
                            listaTipoEmpleado.remove(tblTipoEmpleado.getSelectionModel().getSelectedIndex());
                            procedimiento.execute();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    }else{
                        JOptionPane.showMessageDialog(null, "Sleccione un Elemento");
                    
                } 
        }
        
    }
    
    
    public void editar(){
        
        switch (tipoDeOperacion){
            case NINGUNO:
                if (tblTipoEmpleado.getSelectionModel().getSelectedItem() != null ){
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Guardar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image ("/org/emersonhernandez/image/guardar.png"));
                    imgReporte.setImage(new Image ("/org/emersonhernandez/image/cancelar.png"));
                    activarControles();
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
                            desactivarControles();
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
    
    public void actualizar(){
        if(txtDescripcionTipoEmpleado.getText().length()!= 0){       
            try{
                 PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarTipoEmpleado (?, ?)");
                 TipoEmpleado registro = (TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem();
                 registro.setDescripcionTipoEmpleado(txtDescripcionTipoEmpleado.getText());
                 procedimiento.setInt(1, ((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
                 procedimiento.setString(2, registro.getDescripcionTipoEmpleado());
                 procedimiento.execute();
            }catch(Exception e){
                e.printStackTrace();
            }
        } else{
            JOptionPane.showMessageDialog(null, "Ingrese los datos correctamente");
        }
    }
    
    public void guardar(){
        if(txtDescripcionTipoEmpleado.getText().length()!= 0) {
            TipoEmpleado registro = new TipoEmpleado();
            registro.setDescripcionTipoEmpleado(txtDescripcionTipoEmpleado.getText());
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarTipoEmpleado(?)");
                procedimiento.setString(1, registro.getDescripcionTipoEmpleado());
                procedimiento.execute();
                listaTipoEmpleado.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Ingrese los datos correctamente");
        }
    }
    
    
    public void activarControles(){
        txtCodigoTipoEmpleado.setEditable(false);
        txtDescripcionTipoEmpleado.setEditable(true);
       
        
    }     
    public void limpiarControles(){
        txtCodigoTipoEmpleado.clear();
        txtDescripcionTipoEmpleado.clear(); 
        
    }
    
    public void desactivarControles(){
        txtCodigoTipoEmpleado.setEditable(false);
        txtDescripcionTipoEmpleado.setEditable(false);
        
    }

    
    public TipoEmpleadoController(){
        
    }
    public TipoEmpleadoController(Principal escenarioPrincipal) {
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
    public void ventanaEmpleado(){
        escenarioPrincipal.ventanaEmpleado();
    }
    
    
    
}
