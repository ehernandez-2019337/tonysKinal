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
import org.emersonhernandez.bean.Producto;
import org.emersonhernandez.db.Conexion;
import org.emersonhernandez.main.Principal;


public class ProductosController implements Initializable {
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal; 
    private ObservableList<Producto> listaProducto; 
    @FXML private TextField txtCodigoProducto;
    @FXML private TextField txtNombreProducto;
    @FXML private TextField txtCantidad;
    @FXML private TableView tblProductos;
    @FXML private TableColumn colCodigoProducto;
    @FXML private TableColumn colNombreProducto;
    @FXML private TableColumn colCantidad;
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
        tblProductos.setItems(getProducto());
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("codigoProducto"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidadProducto"));
        }
    public ObservableList<Producto> getProducto(){
        ArrayList<Producto> lista = new ArrayList<Producto>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarProductos");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add( new Producto(resultado.getInt("codigoProducto"),
                        resultado.getString("nombreProduto"),
                        resultado.getInt("cantidadProducto")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProducto = FXCollections.observableList(lista);
    }
    
    
    
    public void seleccionarElemento(){
        if(tblProductos.getSelectionModel().getSelectedItem() != null){
        txtCodigoProducto.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        txtNombreProducto.setText(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getNombreProducto());
        txtCantidad.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCantidadProducto()));

         }else{
             JOptionPane.showMessageDialog(null, "Debe Seleccionar un elemento");
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
                 if(tblProductos.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimieto = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarProducto(?)");
                            procedimieto.setInt(1, ((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                            listaProducto.remove(tblProductos.getSelectionModel().getSelectedIndex());
                            procedimieto.execute();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Seleccione un elemento");
                }
                
        }
        }
    
    
    
    
    
    public void editar(){
        switch (tipoDeOperacion){
            case NINGUNO:
                if (tblProductos.getSelectionModel().getSelectedItem() != null ){
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
        if(txtNombreProducto.getText().length()!=0 & txtCantidad.getText().length()!=0){
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarProducto (?, ?, ?)");
                Producto registro = (Producto) tblProductos.getSelectionModel().getSelectedItem();
                registro.setNombreProducto(txtNombreProducto.getText());
                registro.setCantidadProducto(Integer.parseInt(txtCantidad.getText()));
                procedimiento.setInt(1, registro.getCodigoProducto());
                procedimiento.setString(2, registro.getNombreProducto());
                procedimiento.setInt(3, registro.getCantidadProducto());
                procedimiento.execute();
            }catch(Exception e){
                e.printStackTrace();

            }
        }else{
            JOptionPane.showMessageDialog(null, "Ingrese los datos correctamente");
        }
    }     
    
    
    
      public void guardar(){
        if(txtNombreProducto.getText().length()!=0 & txtCantidad.getText().length()!=0){
        Producto registro = new Producto();
         registro.setNombreProducto(txtNombreProducto.getText());
         registro.setCantidadProducto(Integer.parseInt(txtCantidad.getText()));
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarProducto(?,?)");
             procedimiento.setString(1, registro.getNombreProducto());
             procedimiento.setInt(2, registro.getCantidadProducto());
             procedimiento.execute();
             listaProducto.add(registro);

         }catch(Exception e){
             e.printStackTrace();
         }
       }else{
            JOptionPane.showMessageDialog(null, "Ingese los datos correctamente");
        }
      } 
    
    
    
    
    
    
     public void activarControles(){
        txtCodigoProducto.setEditable(false);
        txtNombreProducto.setEditable(true);
        txtCantidad.setEditable(true);         
    }     
    public void limpiarControles(){
        txtCodigoProducto.clear();
        txtNombreProducto.clear(); 
        txtCantidad.clear();
    }
    
    public void desactivarControles(){
        txtCodigoProducto.setEditable(false);
        txtNombreProducto.setEditable(false);
        txtCantidad.setEditable(false);
    }
    
    public ProductosController(){
    }

    public ProductosController(Principal escenarioPrincipal) {
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
    
}
