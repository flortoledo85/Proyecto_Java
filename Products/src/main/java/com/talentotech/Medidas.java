package com.talentotech;

import java.util.LinkedHashMap;
import java.util.Map;

public class Medidas {
    private Double anchoFrente;
    private Double largoPatilla;
    private Double anchoCristal;
    private Double altoCristal;

    public Medidas (Double anchoFrente, Double largoPatilla, Double anchoCristal, Double altoCristal) {
        this.anchoFrente = anchoFrente;
        this.largoPatilla = largoPatilla;
        this.altoCristal = altoCristal;
        this.anchoCristal = anchoCristal;
        }
        
        public Double getAnchoFrente() {
            return anchoFrente;
        }

        public Double getLargoPatilla() {
            return largoPatilla;
        }

        public Double getAnchoCristal() {
            return anchoCristal;
        }

        public Double getAltoCristal() {
            return altoCristal;
        }

        /*Metodos para setear los parametros, de momento no será necesario ya que no
        se modifican con el tiempo*/

        /*public void setAnchoFrente(Double anchoFrente) {
            this.anchoFrente = anchoFrente;
        }

        public void setLargoPatilla(Double largoPatilla) {
            this.largoPatilla = largoPatilla;
        }

        public void setAnchoCristal(Double anchoCristal) {
            this.anchoCristal = anchoCristal;
        }

        public void setAltoCristal(Double altoCristal) {
            this.altoCristal = altoCristal;
        }*/

        //Metodo para mostrar todas las medidas al mostrar el stock
        @Override
        public String toString() {
            return "Ancho frente: " + anchoFrente + "mm, Largo patilla: " + largoPatilla + "mm, " +
           "Plantilla: " + anchoCristal + "x" + altoCristal + "mm";
        }
        
        /*A futuro */
        // public Map<String, Double> getMedidas() {
        //     Map<String, Double> medidas = new LinkedHashMap<>();
        //     medidas.put("Ancho del Frente", anchoFrente);
        //     medidas.put("Largo de Patilla", largoPatilla);
        //     medidas.put("Ancho del Cristal", anchoCristal);
        //     medidas.put("Alto del Cristal", altoCristal);

        //     return medidas;
        // }
}
