package com.talentotech.optica_backend.model;

import com.talentotech.optica_backend.exceptions.EntradaInvalidaExcepcion;

public class Armazones extends Producto implements Vendible, Etiquetable{
        private Medidas medidas;

        public Armazones (String nombre, Double precio, int stock, Medidas medidas){
            super(nombre, precio, stock);
            this.medidas = medidas;
        }


        public Medidas getMedidas() {
            return medidas;
        }

        @Override 

        public String getCategoria(){
            return "armazones";
        }
        
        @Override 
        public Double aplicarDescuento(Double porcentaje) {
            if (porcentaje < 0 || porcentaje > 100) {
                throw new EntradaInvalidaExcepcion("Ingrese un porcentaje valido.");
            }else {
                Double nuevo = Vendible.calcularDescuento(this.getPrecio(), porcentaje);
                setPrecio(nuevo);
                return nuevo;
            }
        }

        @Override 
        public void generarEtiqueta() {
            System.out.println("Etiqueta : "+ getNombre() + " - Medida :" + medidas);        
        }

}