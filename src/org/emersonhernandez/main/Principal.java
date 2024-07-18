/*
Emerson Hernández
2019337
IN5AM
Fecha Creación: 28-03-23
Fecha Modificación: 04-04-23
Fecha Modificación: 05-04-23
Fecha Modificación: 08-04-23
Fecha Modificación: 04-04-23
Fecha Modificación: 19-04-23
Fecha Modificación: 20-04-23
Fecha Modificación: 26-04-23
Fecha Modificación: 27-04-23
Fecha Modificación: 30-04-23
Fecha Modificación: 02-05-23
Fecha Modificación: 03-05-23
Fecha Modificación: 05-05-23
Fecha Modificación: 09-05-23
Fecha Modificación: 10-05-23
 */
package org.emersonhernandez.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.emersonhernanez.controller.EmpleadosController;
import org.emersonhernanez.controller.EmpresasController;
import org.emersonhernanez.controller.LoginController;
import org.emersonhernanez.controller.MenuPrincipalController;
import org.emersonhernanez.controller.PlatoController;
import org.emersonhernanez.controller.PresupuestoController;
import org.emersonhernanez.controller.ProductosController;
import org.emersonhernanez.controller.ProductosHasPlatosController;
import org.emersonhernanez.controller.ProgramadorController;
import org.emersonhernanez.controller.ServiciosController;
import org.emersonhernanez.controller.ServiciosHasPlatosController;
import org.emersonhernanez.controller.TipoEmpleadoController;
import org.emersonhernanez.controller.TipoPlatoController;
import org.emersonhernanez.controller.UsuarioController;

public class Principal extends Application {
    private final String PAQUTE_VISTA = "/org/emersonhernandez/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Tony's Kinal 2019337");
        escenarioPrincipal.getIcons().add(new Image ("/org/emersonhernandez/image/LogoTonys.png"));
       // Parent root = FXMLLoader.load(getClass().getResource("/org/emersonhernandez/view/MenuPrincipalView.fxml"));
       // Scene escena = new Scene(root);
        // escenarioPrincipal.setScene(escena);
        menuPrincipal();
        escenarioPrincipal.show();
        
    }
    
    @FXML 
    public void menuPrincipal(){
        try{
            MenuPrincipalController menu = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",520,500);
            menu.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProgramador (){
        try{
          ProgramadorController programador = (ProgramadorController)cambiarEscena("ProgramadorView.fxml",402,335);  
          programador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void ventanaEmpresas(){
        try{
            EmpresasController empresas = (EmpresasController) cambiarEscena("EmpresasView.fxml",913,602);
            empresas.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTipoEmpleado(){
        try{
            TipoEmpleadoController tipoEmpleado = (TipoEmpleadoController) cambiarEscena ("TipoEmpleadoView.fxml",862,602);
            tipoEmpleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
            
    }
    
    public void ventanaTipoPlato(){
        try{
            TipoPlatoController tipoPlato = (TipoPlatoController)cambiarEscena("TipoPlatoView.fxml",862,602);
            tipoPlato.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProducto(){
        try{
            ProductosController productos = (ProductosController) cambiarEscena ("ProductosView.fxml", 913,602);
            productos.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void ventanaPresupuesto(){
        try{
            PresupuestoController presupuesto = (PresupuestoController) cambiarEscena ("PresupuestosView.fxml",913,602);
            presupuesto.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaPlato(){
        try{
            PlatoController plato = (PlatoController) cambiarEscena ("PlatosView.fxml",1191,602);
            plato.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpleado(){
        try{
           EmpleadosController empleado = (EmpleadosController) cambiarEscena ("EmpleadosView.fxml", 1453,602);
           empleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaServicios(){
        try{
            ServiciosController servicio = (ServiciosController) cambiarEscena ("ServiciosView.fxml", 1192, 602);
            servicio.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    public void Login(){
        try{
            LoginController login = (LoginController) cambiarEscena ("LoginView.fxml", 700, 500);
            login.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaUsuario(){
        try{
            UsuarioController usuario = (UsuarioController) cambiarEscena ("UsuariosView.fxml", 700, 500);
           usuario.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProductosHasPlatos(){
        try{
            ProductosHasPlatosController php = (ProductosHasPlatosController) cambiarEscena ("ProductosHasPlatosView.fxml", 913,602);
            php.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaServiciosHasPlatos(){
        try{
            ServiciosHasPlatosController shp = (ServiciosHasPlatosController) cambiarEscena ("ServiciosHasPlatosView.fxml",913,602);
            shp.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws IOException{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUTE_VISTA +fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUTE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
    
}
