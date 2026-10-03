package com.talentotech;

import com.talentotech.excepciones.EntradaInvalidaExcepcion;

public class Armazones extends Producto implements Vendible, Etiquetable{
        private Medidas medidas;

        public Armazones (String name, Double price, int stock, Medidas medidas){
            super(name, price, stock);
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
                Double nuevo = Vendible.calcularDescuento(this.getPrice(), porcentaje);
                setPrice(nuevo);
                return nuevo;
            }
        }

        @Override 
        public void generarEtiqueta() {
            System.out.println("Etiqueta : "+ getName() + " - Medida :" + medidas);        
        }

}