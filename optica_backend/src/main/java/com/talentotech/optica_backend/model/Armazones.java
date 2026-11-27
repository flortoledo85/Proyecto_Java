package com.talentotech.optica_backend.model;
import java.math.BigDecimal;
import com.talentotech.optica_backend.exceptions.EntradaInvalidaExcepcion;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Embedded;
import jakarta.persistence.PrimaryKeyJoinColumn;


@Entity
@Table(name = "armazones")
@PrimaryKeyJoinColumn(name = "id")

public class Armazones extends Producto implements Vendible, Etiquetable{
        @Embedded
        private Medidas medidas;

        public Armazones (String nombre, BigDecimal precio, int stock, Medidas medidas){
            super(nombre, precio, stock);
            this.medidas = medidas;
        }

        protected Armazones(){}


        public Medidas getMedidas() {
            return medidas;
        }
    
        @Override 
        public BigDecimal aplicarDescuento(BigDecimal porcentaje) {
            if (porcentaje == null || porcentaje.signum() < 0 || porcentaje.compareTo(BigDecimal.valueOf(100)) > 0) {
                throw new EntradaInvalidaExcepcion("Ingrese un porcentaje valido.");
            }else {
                BigDecimal nuevo = Vendible.calcularDescuento(this.getPrecio(), porcentaje);
                setPrecio(nuevo);
                return nuevo;
            }
        }

        @Override 
        public void generarEtiqueta() {
            System.out.println("Etiqueta : "+ getNombre() + " - Medida :" + medidas);        
        }

}