package com.talentotech;

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

        @Override
        public String toString() {
            return "Ancho frente: " + anchoFrente + "mm, Largo patilla: " + largoPatilla + "mm, " +
           "Plantilla: " + anchoCristal + "x" + altoCristal + "mm";
    }
}
