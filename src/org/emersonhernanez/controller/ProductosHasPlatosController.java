
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.emersonhernandez.bean.Plato;
import org.emersonhernandez.bean.Producto;
import org.emersonhernandez.bean.ProductosHasPlato;
import org.emersonhernandez.db.Conexion;
import org.emersonhernandez.main.Principal;


public class ProductosHasPlatosController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones{ GUARDAR, ELIMINAR, ACTUALIZAR,  NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<ProductosHasPlato> listaProductosHasPlato;
    private ObservableList<Plato> listaPlato;
    private ObservableList<Producto> listaProducto; 



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        
    }
    
    @FXML private TextField txtProductosCodigoProductos;
    @FXML private ComboBox cmbCodigoPlato;
    @FXML private ComboBox cmbCodigoProducto;
    @FXML private TableView tblProductosHasPlatos;
    @FXML private TableColumn colProductosCodigoProducto;
    @FXML private TableColumn colCodigoPlato;
    @FXML private TableColumn colCodigoProducto;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
      public void cargarDatos(){
        tblProductosHasPlatos.setItems(getProductosHasPlato());
        cmbCodigoPlato.setItems(getPlato());
        cmbCodigoProducto.setItems(getProducto());
        colProductosCodigoProducto.setCellValueFactory(new PropertyValueFactory<ProductosHasPlato, Integer> ("ProductosCodigoProducto"));
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<ProductosHasPlato, Integer>("codigoPlato"));
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<ProductosHasPlato, Integer>("codigoProducto"));
        

    }
    
    public ObservableList<ProductosHasPlato> getProductosHasPlato(){
        ArrayList<ProductosHasPlato> lista = new ArrayList<ProductosHasPlato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarProductosHasPlatos()");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new ProductosHasPlato (resultado.getInt("productos_CodigoProducto"),
                        resultado.getInt("codigoPlato"),
                        resultado.getInt("codigoProducto")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return listaProductosHasPlato = FXCollections.observableArrayList(lista);
    }
    
    
    public ObservableList<Producto> getProducto(){
        ArrayList<Producto> lista = new ArrayList<Producto>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarProductos");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add( new Producto(resultado.getInt("codigoProducto"),
                        resultado.getString("nombreProduto"),
                        resultado.getInt("CantidadProducto")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProducto = FXCollections.observableList(lista);
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
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
    
     public void seleccionarElemento(){
        if(tblProductosHasPlatos.getSelectionModel().getSelectedItem() != null){
        txtProductosCodigoProductos.setText(String.valueOf(((ProductosHasPlato)tblProductosHasPlatos.getSelectionModel().getSelectedItem()).getProductosCodigoProducto()));
        cmbCodigoPlato.getSelectionModel().select(buscarPlato(((ProductosHasPlato)tblProductosHasPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
        cmbCodigoProducto.getSelectionModel().select(buscarPlato(((ProductosHasPlato)tblProductosHasPlatos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        }else{
                JOptionPane.showMessageDialog(null, "Seleccione un elemento");
                } 
        
    }
     
     public  Plato buscarPlato(int codigoPlato){
        Plato resultado = null;
        
        try{
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarPlato (?)");
          procedimiento.setInt(1, codigoPlato);
          ResultSet registro = procedimiento.executeQuery();
          while(registro.next()){
                resultado = new Plato(registro.getInt("codigoPlato"),
                registro.getInt("cantidadPlato"),
                registro.getString("nombrePlato"),
                registro.getString("descripcionPlato"),
                registro.getDouble("precioPlato"),
                registro.getInt("codigoTipoPlato"));
                
              
          }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
     
      public  Producto buscarProducto(int codigoProducto){
        Producto resultado = null;
        
        try{
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarProducto (?)");
          procedimiento.setInt(1, codigoProducto);
          ResultSet registro = procedimiento.executeQuery();
          while(registro.next()){
                resultado = new Producto(registro.getInt("codigoProducto"),
                registro.getString("nombreProducto"),
                registro.getInt("cantidadProducto"));
                
              
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
       
            if(tblProductosHasPlatos.getSelectionModel().getSelectedItem() != null){
                int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Eliminar Producto Has Plato", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_NO_OPTION){
                    try{
                        PreparedStatement procedimieto = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarProductoHasPlato(?)");
                        procedimieto.setInt(1, ((ProductosHasPlato)tblProductosHasPlatos.getSelectionModel().getSelectedItem()).getProductosCodigoProducto());
                        listaProductosHasPlato.remove(tblProductosHasPlatos.getSelectionModel().getSelectedIndex());
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
     
     
    
    public void guardar(){
        
            ProductosHasPlato registro = new ProductosHasPlato();
            registro.setProductosCodigoProducto(Integer.parseInt(txtProductosCodigoProductos.getText()));
            registro.setCodigoPlato(((Plato)cmbCodigoPlato.getSelectionModel().getSelectedItem()).getCodigoPlato());
            registro.setCodigoProducto(((Producto)cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());

        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarProductosHasPlatos(?,?,?)");
            procedimiento.setInt(1, registro.getProductosCodigoProducto());
            procedimiento.setInt(2, registro.getCodigoPlato());
            procedimiento.setInt(3, registro.getCodigoProducto());
            procedimiento.execute();
            listaProductosHasPlato.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
        cargarDatos(); 
            }
        
        
    
    
    
    
    public void activarControles(){
        txtProductosCodigoProductos.setEditable(true);
        cmbCodigoProducto.setDisable(false);
        cmbCodigoPlato.setDisable(false);
        
    }  
          
     
    public void desactivarControles(){
        txtProductosCodigoProductos.setDisable(true);
        cmbCodigoProducto.setDisable(true);
        cmbCodigoPlato.setDisable(true);

    }
    public void limpiarControles(){
        txtProductosCodigoProductos.clear();
        cmbCodigoProducto.setValue(null);
        cmbCodigoPlato.setValue(null);
       tblProductosHasPlatos.getSelectionModel().clearSelection();

    }
    
    
    

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
