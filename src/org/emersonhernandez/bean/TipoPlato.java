/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emersonhernandez.bean;


public class TipoPlato {
   private int codigoTipoPlato;
   private String descripcionTipoPlato;
   
   public TipoPlato(){
   }

    public TipoPlato(int codigoTipoPlato, String descripcionTipoPlato) {
        this.codigoTipoPlato = codigoTipoPlato;
        this.descripcionTipoPlato = descripcionTipoPlato;
    }

    public int getCodigoTipoPlato() {
        return codigoTipoPlato;
    }

    public void setCodigoTipoPlato(int codigoTipoPlato) {
        this.codigoTipoPlato = codigoTipoPlato;
    }

    public String getDescripcionTipoPlato() {
        return descripcionTipoPlato;
    }

    public void setDescripcionTipoPlato(String descripcionTipoPlato) {
        this.descripcionTipoPlato = descripcionTipoPlato;
    }
   
   @Override
    public String toString() {
        return  codigoTipoPlato + ". " + descripcionTipoPlato ;
    }
   
}
