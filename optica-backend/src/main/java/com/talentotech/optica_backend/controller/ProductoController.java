package com.talentotech.optica_backend;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;
import com.talentotech.optica_backend.model.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.getProductos();
    }

    @GetMapping("/{id}")
    public Producto obtenerProducto(@PathVariable int id) {
        return productoService.buscarPorID(id);
    }

    @PostMapping
    public Producto creear(@RequestBody Producto p) {
        return productoService.guardar(p);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable int id, @RequestBody DatosActualizacion datos) {
        return productoService.actualizarProducto(id, datos.precio, datos.stock);
    }

    public record DatosActualizacion(Double precio, Integer stock) {}

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        productoService.eliminar(id);
    }
}