/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emersonhernandez.bean;


public class ServiciosHasPlatos {
    private int servicioCodigoServicio;
    private int codigoPlato;
    private int codigoServicio;

    public ServiciosHasPlatos() {
    }

    public ServiciosHasPlatos(int servicioCodigoServicio, int codigoPlato, int codigoServicio) {
        this.servicioCodigoServicio = servicioCodigoServicio;
        this.codigoPlato = codigoPlato;
        this.codigoServicio = codigoServicio;
    }

    public int getServicioCodigoServicio() {
        return servicioCodigoServicio;
    }

    public void setServicioCodigoServicio(int servicioCodigoServicio) {
        this.servicioCodigoServicio = servicioCodigoServicio;
    }

    public int getCodigoPlato() {
        return codigoPlato;
    }

    public void setCodigoPlato(int codigoPlato) {
        this.codigoPlato = codigoPlato;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }
    
    
    
}
