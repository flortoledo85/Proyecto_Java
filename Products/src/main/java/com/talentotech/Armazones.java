package com.talentotech;

public class Armazones extends Producto {
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
}