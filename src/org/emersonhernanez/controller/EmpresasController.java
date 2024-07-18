
package org.emersonhernanez.controller;


import org.emersonhernandez.bean.Empresa;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
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
import org.emersonhernandez.db.Conexion;
import org.emersonhernandez.main.Principal;
import org.emersonhernandez.report.GenerarReporte;


public class EmpresasController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal; 
    private ObservableList<Empresa> listaEmpresa; 
    @FXML private TextField txtCodigoEmpresa;
    @FXML private TextField txtNombreEmpresa;
    @FXML private TextField txtDireccionEmpresa;
    @FXML private TextField txtTelefonoEmpresa;
    @FXML private TableView tblEmpresas;
    @FXML private TableColumn colCodigoEmpresa;
    @FXML private TableColumn colNombreEmpresa;
    @FXML private TableColumn colDireccionEmpresa;
    @FXML private TableColumn colTelefonoEmpresa;
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
        tblEmpresas.setItems(getEmpresa());
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, Integer>("codigoEmpresa"));
        colNombreEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("nombreEmpresa"));
        colDireccionEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("direccionEmpresa"));
        colTelefonoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("telefonoEmpresa"));   
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
    
    public void seleccionarElemento(){
         if(tblEmpresas.getSelectionModel().getSelectedItem() != null){
        txtCodigoEmpresa.setText(String.valueOf(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
        txtNombreEmpresa.setText(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getNombreEmpresa());
        txtDireccionEmpresa.setText(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getDireccionEmpresa());
        txtTelefonoEmpresa.setText(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getTelefonoEmpresa());
         }else{
             JOptionPane.showMessageDialog(null, "Debe Seleccionar un elemento");
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
                if(tblEmpresas.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Eliminar Empresa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimieto = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarEmpresa(?)");
                            procedimieto.setInt(1, ((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
                            listaEmpresa.remove(tblEmpresas.getSelectionModel().getSelectedIndex());
                            procedimieto.execute();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Seleccione un elemento");
                }
                
               limpiarControles(); 
        }
        
    }
        
    public void editar(){
        
        switch (tipoDeOperacion){
            case NINGUNO:
                if (tblEmpresas.getSelectionModel().getSelectedItem() != null ){
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
    
    public void actualizar(){
        
        if(txtNombreEmpresa.getText().length() != 0 && txtDireccionEmpresa.getText().length() != 0 && txtTelefonoEmpresa.getText().length() == 8){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarEmpresa (?, ?, ?, ?)");
            Empresa registro = (Empresa) tblEmpresas.getSelectionModel().getSelectedItem();
            registro.setNombreEmpresa(txtNombreEmpresa.getText());
            registro.setDireccionEmpresa(txtDireccionEmpresa.getText());
            registro.setTelefonoEmpresa(txtTelefonoEmpresa.getText());
            procedimiento.setInt(1, registro.getCodigoEmpresa());
            procedimiento.setString(2, registro.getNombreEmpresa());
            procedimiento.setString(3, registro.getDireccionEmpresa());
            procedimiento.setString(4, registro.getTelefonoEmpresa());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }else {
            JOptionPane.showMessageDialog(null, "Ingrese los datos de forma correcta");
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
        parametros.put("codigoEmpresa", null);
        GenerarReporte.mostrarReporte("ReporteEmpresas.jasper", "Reporte De Empresas", parametros);
    }
   
    
    
    public void guardar(){
        if(  txtNombreEmpresa.getText().length() != 0 && txtDireccionEmpresa.getText().length() != 0 && txtTelefonoEmpresa.getText().length() == 8 )  { 
            Empresa registro = new Empresa();
            registro.setNombreEmpresa(txtNombreEmpresa.getText());
            registro.setDireccionEmpresa(txtDireccionEmpresa.getText());
            registro.setTelefonoEmpresa(txtTelefonoEmpresa.getText());
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarEmpresa(?, ?, ?)");
                procedimiento.setString(1, registro.getNombreEmpresa());
                procedimiento.setString(2, registro.getDireccionEmpresa());
                procedimiento.setString(3, registro.getTelefonoEmpresa());
                procedimiento.execute();
                listaEmpresa.add(registro);

            }catch(Exception e){
                e.printStackTrace();
            }
        }else{ 
            JOptionPane.showMessageDialog(null, "Ingrese los datos de forma correcta");
        }
    }
    
    public void activarControles(){
        txtCodigoEmpresa.setEditable(false);
        txtNombreEmpresa.setEditable(true);
        txtDireccionEmpresa.setEditable(true); 
        txtTelefonoEmpresa.setEditable(true);
        
    }     
    public void limpiarControles(){
        txtCodigoEmpresa.clear();
        txtNombreEmpresa.clear(); 
        txtDireccionEmpresa.clear();
        txtTelefonoEmpresa.clear();
        tblEmpresas.getSelectionModel().clearSelection();
    }
    
    public void desactivarControles(){
        txtCodigoEmpresa.setEditable(false);
        txtNombreEmpresa.setEditable(false);
        txtDireccionEmpresa.setEditable(false);
        txtTelefonoEmpresa.setEditable(false);
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
    public void ventanaPresupuesto(){
        escenarioPrincipal.ventanaPresupuesto();
    }
    public void ventanaServicios(){
        escenarioPrincipal.ventanaServicios();
    }
}
