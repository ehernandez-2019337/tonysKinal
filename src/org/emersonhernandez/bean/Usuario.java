
package org.emersonhernandez.bean;

/**
 *
 * @author informatica
 */
public class Usuario {
    private String nombreUsuario;
    private String apellidoUsiuario;
    private String usuarioLogin;
    private String contrasena;

    public Usuario() {
    }

    public Usuario(String nombreUsuario, String apellidoUsiuario, String usuarioLogin, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsiuario = apellidoUsiuario;
        this.usuarioLogin = usuarioLogin;
        this.contrasena = contrasena;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsiuario() {
        return apellidoUsiuario;
    }

    public void setApellidoUsiuario(String apellidoUsiuario) {
        this.apellidoUsiuario = apellidoUsiuario;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

   
    
}
