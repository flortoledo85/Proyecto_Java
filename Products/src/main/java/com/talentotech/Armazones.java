package com.talentotech;

public class Armazones extends Producto implements Vendible, Etiquetable{
        public Double medidas;

        public Armazones (String name, Double price, int stock, Double medidas){
            super(name, price, stock);
            this.medidas = medidas;
        }


        public Double getMedidas() {
            return medidas;
        }

        @Override 

        public String getCategoria(){
            return "armazones";
        }
        
        @Override 
        public void aplicarDescuento(Double porcentaje) {
            System.out.println("Aplicando " + porcentaje + "%" + " de descuento a " + getName());
        }

        @Override 
        public void generarEtiqueta() {
            System.out.println("Etiqueta : "+ getName() + " - Medida :" + medidas);        
        }

}