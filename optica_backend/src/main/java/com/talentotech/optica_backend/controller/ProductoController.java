package com.talentotech.optica_backend;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import com.talentotech.optica_backend.model.*;
import com.talentotech.optica_backend.exceptions.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(
            @RequestParam(name = "nombre", required = false) String nombre) {
        if (nombre == null) {
            return ResponseEntity.ok(productoService.getProductos());
        }
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.buscarPorID(id));
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody DatosAlta datos) {
        DatosMedidas m = datos.medidas();
        Producto producto;
        if (m == null) {
            producto = new Producto(datos.nombre(), datos.precio(), datos.stock());
        } else {
            if (m.anchoFrente() == null || m.largoPatilla() == null || m.anchoCristal() == null
                    || m.altoCristal() == null) {
                throw new EntradaInvalidaExcepcion("Completá las cuatro medidas del armazón.");
            }
            if (datos.stock() == null || datos.stock() < 0) {
                throw new EntradaInvalidaExcepcion("Stock inválido");
            }
            Medidas medidas = new Medidas(m.anchoFrente(), m.largoPatilla(), m.anchoCristal(), m.altoCristal());
            producto = new Armazones(datos.nombre(), datos.precio(), datos.stock(), medidas);
        }
        Categoria categoria = productoService.buscarCategoriaPorId(datos.categoriaId());

        producto.setCategoria(categoria);
        producto.setDescripcion(datos.descripcion());
        producto.setRutaImagen(datos.rutaImagen());

        return ResponseEntity.ok(productoService.guardar(producto));
    }

    public record DatosAlta(
            String nombre,
            String descripcion,
            BigDecimal precio,
            Integer stock,
            Integer categoriaId,
            String rutaImagen,
            DatosMedidas medidas) {
    }

    public record DatosMedidas(Double anchoFrente, Double largoPatilla, Double anchoCristal, Double altoCristal) {
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @RequestBody DatosActualizacion datos) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, datos.nombre(), datos.descripcion(),
                datos.precio(), datos.stock(), datos.categoriaId(), datos.rutaImagen()));
    }

    public record DatosActualizacion(String nombre, String descripcion, BigDecimal precio, Integer stock,
            Integer categoriaId, String rutaImagen) {
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}