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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.emersonhernandez.bean.TipoPlato;
import org.emersonhernandez.db.Conexion;

import org.emersonhernandez.main.Principal;


public class TipoPlatoController  implements Initializable{

     private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal; 
    private ObservableList<TipoPlato> listaTipoPlato; 
    @FXML private TextField txtCodigoTipoPlato;
    @FXML private TextField txtDescripcionTipoPlato;
    @FXML private TableView tblTipoPlato;
    @FXML private TableColumn colCodigoTipoPlato;
    @FXML private TableColumn colDescipcionTipoPlato;
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
        tblTipoPlato.setItems(getTipoPlato());
        colCodigoTipoPlato.setCellValueFactory(new PropertyValueFactory<TipoPlato, Integer>("codigoTipoPlato"));
        colDescipcionTipoPlato.setCellValueFactory(new PropertyValueFactory<TipoPlato, String>("descripcionTipoPlato"));
   
    }
    
    
    public ObservableList<TipoPlato> getTipoPlato(){
        ArrayList<TipoPlato> lista = new ArrayList<TipoPlato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarTipoPlatos");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add( new TipoPlato(resultado.getInt("codigoTipoPlato"),
                        resultado.getString("descripcion")));
                       
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTipoPlato = FXCollections.observableList(lista);
    }
    
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
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
                if(tblTipoPlato.getSelectionModel().getSelectedItem() !=null){
                  int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Eliminar TipoPlato", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarTipoPlato (?)");
                            procedimiento.setInt(1,((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
                            listaTipoPlato.remove(tblTipoPlato.getSelectionModel().getSelectedIndex());
                            procedimiento.execute();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    }else{
                        JOptionPane.showMessageDialog(null, "Sleccione un Elemento");
                    
                }
                limpiarControles();
        }
        
    }
     
     public void editar(){
         
            switch (tipoDeOperacion){
                case NINGUNO:
                    if (tblTipoPlato.getSelectionModel().getSelectedItem() != null ){
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
    
     
     
      public void SeleccionarElemento(){
        if(tblTipoPlato.getSelectionModel().getSelectedItem() != null ){
            txtCodigoTipoPlato.setText(String.valueOf(((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato()));
            txtDescripcionTipoPlato.setText(String.valueOf(((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getDescripcionTipoPlato()));
        }else{
            JOptionPane.showMessageDialog(null, "Debe selecciobar un elemento");
        }
    }
    
      public void actualizar(){
            if(txtDescripcionTipoPlato.getText().length() != 0){   
                try{
                  PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarTipoPlato (?, ?)");
                  TipoPlato registro = (TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem();
                  registro.setDescripcionTipoPlato(txtDescripcionTipoPlato.getText());
                  procedimiento.setInt(1, ((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
                  procedimiento.setString(2, registro.getDescripcionTipoPlato());
                  procedimiento.execute();
             }catch(Exception e){
                 e.printStackTrace();
             }
            }else{
                JOptionPane.showMessageDialog(null, "Ingrese correctamente los datos");
            }    
      }
    
    
    public void guardar(){
        if(txtDescripcionTipoPlato.getText().length() != 0){    
            TipoPlato registro = new TipoPlato();
            registro.setDescripcionTipoPlato(txtDescripcionTipoPlato.getText());
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarTipoPlato(?)");
                procedimiento.setString(1, registro.getDescripcionTipoPlato());
                procedimiento.execute();
                listaTipoPlato.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Ingresar correctamente los datos");
        }
    }
    
    public void activarControles(){
        txtCodigoTipoPlato.setEditable(false);
        txtDescripcionTipoPlato.setEditable(true);
       
        
    }     
    public void limpiarControles(){
        txtCodigoTipoPlato.clear();
        txtDescripcionTipoPlato.clear(); 
        tblTipoPlato.getSelectionModel().clearSelection();
    }
    
    public void desactivarControles(){
        txtCodigoTipoPlato.setEditable(false);
        txtDescripcionTipoPlato.setEditable(false);
        
    }

    
    
    public TipoPlatoController() {    
    }

    public TipoPlatoController(Principal escenarioPrincipal) {
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
    public void ventanaPlato(){
        escenarioPrincipal.ventanaPlato();
    }
    
}
