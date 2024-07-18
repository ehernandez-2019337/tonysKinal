package org.emersonhernandez.bean;


public class Empresa {
   public int codigoEmpresa ;
   public String nombreEmpresa;
   public String direccionEmpresa;
   public String telefonoEmpresa;
   
   public Empresa(){
       
   }

    public Empresa(int codigoEmpresa, String nombreEmpresa, String direccionEmpresa, String telefonoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.direccionEmpresa = direccionEmpresa;
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public void setTelefonoEmpresa(String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    @Override
    public String toString() {
        return  codigoEmpresa + ". " + nombreEmpresa ;
    }

    
   
   
}
