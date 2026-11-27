package com.talentotech.optica_backend.model;
import java.math.BigDecimal;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public class Medidas {
    @Column(name = "anchoFrente")
    private Double anchoFrente;
    @Column(name = "largoPatilla")
    private Double largoPatilla;
    @Column(name = "ancho_cristal")
    private Double anchoCristal;
    @Column(name = "alto_cristal")
    private Double altoCristal;


    public Medidas (Double anchoFrente, Double largoPatilla, Double anchoCristal, Double altoCristal) {
        this.anchoFrente = anchoFrente;
        this.largoPatilla = largoPatilla;
        this.altoCristal = altoCristal;
        this.anchoCristal = anchoCristal;
        }
        
        protected Medidas(){}

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
