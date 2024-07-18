/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emersonhernandez.bean;

import java.util.Date;


public class Servicios {
    private int codigoServicio;
    private Date fechaDeServicio;
    private String tipoDeServicio;
    private String lugarServicio;
    private String telefonoDeContacto;
    private String hora;
    private String minutos;
    private String horaervicio;
    
    private int codigoEmpresa;

    public Servicios() {
    }

    public Servicios(int codigoServicio, Date fechaDeServicio, String tipoDeServicio, String lugarServicio, String telefonoDeContacto, String horaervicio, int codigoEmpresa) {
        this.codigoServicio = codigoServicio;
        this.fechaDeServicio = fechaDeServicio;
        this.tipoDeServicio = tipoDeServicio;
        this.lugarServicio = lugarServicio;
        this.telefonoDeContacto = telefonoDeContacto;
        this.horaervicio = horaervicio;
        this.codigoEmpresa = codigoEmpresa;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public Date getFechaDeServicio() {
        return fechaDeServicio;
    }

    public void setFechaDeServicio(Date fechaDeServicio) {
        this.fechaDeServicio = fechaDeServicio;
    }

    public String getTipoDeServicio() {
        return tipoDeServicio;
    }

    public void setTipoDeServicio(String tipoDeServicio) {
        this.tipoDeServicio = tipoDeServicio;
    }

    public String getLugarServicio() {
        return lugarServicio;
    }

    public void setLugarServicio(String lugarServicio) {
        this.lugarServicio = lugarServicio;
    }

    public String getTelefonoDeContacto() {
        return telefonoDeContacto;
    }

    public void setTelefonoDeContacto(String telefonoDeContacto) {
        this.telefonoDeContacto = telefonoDeContacto;
    }

    
    

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMinutos() {
        return minutos;
    }

    public void setMinutos(String minutos) {
        this.minutos = minutos;
    }

    public String getHoraervicio() {
        return horaervicio;
    }

    public void setHoraervicio(String horaervicio) {
        this.horaervicio = horaervicio;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

   
    
}
