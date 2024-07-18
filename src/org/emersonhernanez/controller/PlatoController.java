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
import org.emersonhernandez.bean.Plato;
import org.emersonhernandez.bean.TipoPlato;
import org.emersonhernandez.db.Conexion;
import org.emersonhernandez.main.Principal;


public class PlatoController implements Initializable{
    private Principal escenarioPrincipal;
    private ObservableList<Plato> listaPlato;
    private ObservableList<TipoPlato> listaTipoPlato;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    @FXML private TextField txtCodigoPlato;
    @FXML private TextField txtCantidadPlato;
    @FXML private TextField txtNombrePlato;
    @FXML private TextField txtDescripcionPlato;
    @FXML private TextField txtPrecioPlato;
    @FXML private ComboBox cmbCodigoTipoPlato;
    @FXML private TableView tblPlatos;
    @FXML private TableColumn colCodigoPlato;
    @FXML private TableColumn colCantidadPlato;
    @FXML private TableColumn colNombrePlato;
    @FXML private TableColumn colDescripcionPlato;
    @FXML private TableColumn colPrecioPlato;
    @FXML private TableColumn colCodigoTipoPlato;
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
        tblPlatos.setItems(getPlato());
        cmbCodigoTipoPlato.setItems(getTipoPlato());
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<Plato, Integer>("codigoPlato"));
        colCantidadPlato.setCellValueFactory(new PropertyValueFactory<Plato, Integer>("cantidadPlato"));
        colNombrePlato.setCellValueFactory(new PropertyValueFactory<Plato, String>("nombrePlato"));
        colDescripcionPlato.setCellValueFactory(new PropertyValueFactory<Plato, String>("descripcionPlato"));   
        colPrecioPlato.setCellValueFactory(new PropertyValueFactory<Plato, Double>("precioPlato"));
        colCodigoTipoPlato.setCellValueFactory(new PropertyValueFactory<Plato, Integer>("codigoTipoPlato"));
        
    }
    
    public ObservableList<Plato> getPlato(){
        ArrayList<Plato> lista = new ArrayList<Plato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarPlatos");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add( new Plato(resultado.getInt("codigoPlato"),
                        resultado.getInt("cantidadPlato"),
                        resultado.getString("nombrePlato"),
                        resultado.getString("descripcionPlato"),
                        resultado.getDouble("precioPlato"),
                        resultado.getInt("codigoTipoPlato")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaPlato = FXCollections.observableList(lista);
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
    
    public void seleccionarElemento(){
        if(tblPlatos.getSelectionModel().getSelectedItem() != null){
        txtCodigoPlato.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
        txtCantidadPlato.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCantidadPlato()));
        txtNombrePlato.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getNombrePlato()));
        txtDescripcionPlato.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getDescripcionPlato()));
        txtPrecioPlato.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getPrecioPlato())); 
        cmbCodigoTipoPlato.getSelectionModel().select(buscarTipoPlato(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoTipoPlato()));
        }else{
                JOptionPane.showMessageDialog(null, "Seleccione un elemento");
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
    
     public  TipoPlato buscarTipoPlato(int codigoTipoPlato){
        TipoPlato resultado = null;
        
        try{
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarTipoPlato (?)");
          procedimiento.setInt(1, codigoTipoPlato);
          ResultSet registro = procedimiento.executeQuery();
          while(registro.next()){
                resultado = new TipoPlato(registro.getInt("codigoTipoPlato"),
                registro.getString("descripcion"));   
          }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
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
       
            if(tblPlatos.getSelectionModel().getSelectedItem() != null){
                int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Eliminar Plato", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_NO_OPTION){
                    try{
                        PreparedStatement procedimieto = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarPlato(?)");
                        procedimieto.setInt(1, ((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato());
                        listaPlato.remove(tblPlatos.getSelectionModel().getSelectedIndex());
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
                if (tblPlatos.getSelectionModel().getSelectedItem() != null ){
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Guardar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image ("/org/emersonhernandez/image/guardar.png"));
                    imgReporte.setImage(new Image ("/org/emersonhernandez/image/cancelar.png"));
                    activarControles();
                    cmbCodigoTipoPlato.setDisable(true);
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
     
    public void actualizar(){
        try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarPlato (?, ?, ?, ?, ?, ?)");
                Plato registro = (Plato) tblPlatos.getSelectionModel().getSelectedItem();
                registro.setCantidadPlato(Integer.parseInt(txtCantidadPlato.getText()));
                registro.setNombrePlato(txtNombrePlato.getText());
                registro.setDescripcionPlato(txtDescripcionPlato.getText());
                registro.setPrecioPlato(Double.parseDouble(txtPrecioPlato.getText()));

                procedimiento.setInt(1, registro.getCodigoPlato());
                procedimiento.setInt(2, registro.getCantidadPlato());
                procedimiento.setString(3, registro.getNombrePlato());
                procedimiento.setString(4, registro.getDescripcionPlato());
                procedimiento.setDouble(5, registro.getPrecioPlato());
                procedimiento.setInt(6, registro.getCodigoTipoPlato());
                procedimiento.execute();

                procedimiento.execute();
                }catch(Exception e){
                    e.printStackTrace();

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
    
    public void guardar(){
        Plato registro = new Plato();
            registro.setCantidadPlato(Integer.parseInt(txtCantidadPlato.getText()));
            registro.setNombrePlato(txtNombrePlato.getText());
            registro.setDescripcionPlato(txtDescripcionPlato.getText());
            registro.setPrecioPlato(Integer.parseInt(txtPrecioPlato.getText()));
            registro.setCodigoTipoPlato(((TipoPlato)cmbCodigoTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarPlato(?, ?, ?, ?, ?)");
                procedimiento.setInt(1, registro.getCantidadPlato());
                procedimiento.setString(2, registro.getNombrePlato());
                procedimiento.setString(3, registro.getDescripcionPlato());
                procedimiento.setDouble(4, registro.getPrecioPlato());
                procedimiento.setInt(5, registro.getCodigoTipoPlato());
                procedimiento.execute();
                listaPlato.add(registro);

            }catch(Exception e){
                e.printStackTrace();
            }
    }
    
    
    
    
     public void activarControles(){
        txtCodigoPlato.setEditable(false);
        txtCantidadPlato.setEditable(true);
        txtNombrePlato.setEditable(true);
        txtDescripcionPlato.setEditable(true);
        txtPrecioPlato.setEditable(true); 
        
    }     
    public void limpiarControles(){
        txtCodigoPlato.clear();
        txtCantidadPlato.clear();
        txtNombrePlato.clear();
        txtDescripcionPlato.clear();
        txtPrecioPlato.clear(); 
        tblPlatos.getSelectionModel().clearSelection();
    }
    
    public void desactivarControles(){
        txtCodigoPlato.setEditable(false);
        txtCantidadPlato.setEditable(false);
        txtNombrePlato.setEditable(false);
        txtDescripcionPlato.setEditable(false);
        txtPrecioPlato.setEditable(false);  
    }
    
    public PlatoController() {
    }

    public PlatoController(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    public void ventanaTipoPlato(){
        escenarioPrincipal.ventanaTipoPlato();
    }
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
}
