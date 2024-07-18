
package org.emersonhernanez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.emersonhernandez.main.Principal;


public class MenuPrincipalController implements Initializable{
    private Principal escenarioPrincipal;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador();
    }

    public void ventanaEmpresas(){
        escenarioPrincipal.ventanaEmpresas();
    }
    public void ventanaTipoEmpleado(){
       escenarioPrincipal.ventanaTipoEmpleado();
    }
    public void ventanaTipoPlato(){
        escenarioPrincipal.ventanaTipoPlato();
    }
    public void ventanaProducto(){
        escenarioPrincipal.ventanaProducto();
    }
    public void Login(){
        escenarioPrincipal.Login();
    }
    public void ventanaUsuario(){
        escenarioPrincipal.ventanaUsuario();
    }
    public void ventanaProductosHasPlatos(){
        escenarioPrincipal.ventanaProductosHasPlatos();
    }
    public void ventanaServiciosHasPlatos(){
        escenarioPrincipal.ventanaServiciosHasPlatos();
    }
    
}
