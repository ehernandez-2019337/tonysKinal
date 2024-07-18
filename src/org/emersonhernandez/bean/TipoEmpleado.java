/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emersonhernandez.bean;


public class TipoEmpleado {
   private int codigoTipoEmpleado;
   private String descripcionTipoEmpleado;
  
   public TipoEmpleado(){
    }

    public TipoEmpleado(int codigoTipoEmpleado, String descripcionTipoEmpleado) {
        this.codigoTipoEmpleado = codigoTipoEmpleado;
        this.descripcionTipoEmpleado = descripcionTipoEmpleado;
    }

    public int getCodigoTipoEmpleado() {
        return codigoTipoEmpleado;
    }

    public void setCodigoTipoEmpleado(int codigoTipoEmpleado) {
        this.codigoTipoEmpleado = codigoTipoEmpleado;
    }

    public String getDescripcionTipoEmpleado() {
        return descripcionTipoEmpleado;
    }

    public void setDescripcionTipoEmpleado(String descripcionTipoEmpleado) {
        this.descripcionTipoEmpleado = descripcionTipoEmpleado;
    }
    
   
   @Override
    public String toString() {
        return  codigoTipoEmpleado + ". " + descripcionTipoEmpleado ;
    }
   
   
}
