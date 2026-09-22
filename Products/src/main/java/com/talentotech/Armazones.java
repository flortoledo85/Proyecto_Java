package com.talentotech;

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
        public void aplicarDescuento(Double porcentaje) {
            System.out.println("Aplicando " + porcentaje + "%" + " de descuento a " + getName());
        }

        @Override 
        public void generarEtiqueta() {
            System.out.println("Etiqueta : "+ getName() + " - Medida :" + medidas);        
        }

}