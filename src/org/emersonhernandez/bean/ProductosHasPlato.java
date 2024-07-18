
package org.emersonhernandez.bean;


public class ProductosHasPlato {
    private int ProductosCodigoProducto;
    private int codigoPlato;
    private int codigoProducto;
    
   

    public ProductosHasPlato() {
    }

    public ProductosHasPlato(int ProductosCodigoProducto, int codigoPlato, int codigoProducto) {
        this.ProductosCodigoProducto = ProductosCodigoProducto;
        this.codigoPlato = codigoPlato;
        this.codigoProducto = codigoProducto;
    }

    public int getProductosCodigoProducto() {
        return ProductosCodigoProducto;
    }

    public void setProductosCodigoProducto(int ProductosCodigoProducto) {
        this.ProductosCodigoProducto = ProductosCodigoProducto;
    }

    public int getCodigoPlato() {
        return codigoPlato;
    }

    public void setCodigoPlato(int codigoPlato) {
        this.codigoPlato = codigoPlato;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
    
    
}
