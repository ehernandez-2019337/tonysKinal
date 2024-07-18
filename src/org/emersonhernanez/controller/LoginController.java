
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
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import org.emersonhernandez.bean.Login;
import org.emersonhernandez.bean.Usuario;
import org.emersonhernandez.db.Conexion;
import org.emersonhernandez.main.Principal;


public class LoginController implements Initializable {
    private Principal escenarioPrincipal;
    private ObservableList<Usuario> listaUsuario;
    @FXML private TextField txtUser;
    @FXML private TextField txtPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public ObservableList<Usuario> getUsuario(){
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarUsuario");
                ResultSet resultado = procedimiento.executeQuery();
                    while(resultado.next()){
                        lista.add( new Usuario(resultado.getString("nombreUsuario"),
                        resultado.getString("apellidoUsuario"),
                        resultado.getString("usuarioLogin"),
                        resultado.getString("contrasena")));
                    }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaUsuario = FXCollections.observableArrayList(lista);
    }
    
    @FXML 
    private void secion(){
        Login login = new Login();
        int x = 0;
        boolean bandera = false;
        login.setUsuarioMaster(txtUser.getText());
        login.setPasswordLogin(txtPassword.getText());
        while(x < getUsuario().size()){
            String user = getUsuario().get(x).getUsuarioLogin();
            String pass = getUsuario().get(x).getContrasena();
            if(user.equals(login.getUsuarioMaster()) && pass.equals(login.getPasswordLogin())){
            JOptionPane.showMessageDialog(null, "Secion Iniciada\n" +getUsuario().get(x).getNombreUsuario() 
            + " " + getUsuario().get(x).getApellidoUsiuario() + " \n " + "Bienvenido");
            
            escenarioPrincipal.menuPrincipal();
            x = getUsuario().size();
            bandera = true;
        }
            x++;
        }
        if (bandera == false )
            JOptionPane.showMessageDialog(null, "Usuario o contrase;a incorrectos");
    }
    

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    public void ventanaUsuario(){
        escenarioPrincipal.ventanaUsuario();
    }
   
    
}
