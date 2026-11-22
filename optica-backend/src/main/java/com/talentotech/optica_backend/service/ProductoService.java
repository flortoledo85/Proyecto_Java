package com.talentotech.optica_backend;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Locale;
import com.talentotech.optica_backend.exceptions.*;
import org.springframework.stereotype.Service;
import com.talentotech.optica_backend.model.*;
import com.talentotech.optica_backend.repository.ProductoRepository;

@Service

public class ProductoService {
    private final ProductoRepository repository;
    private List<Producto> productos = new ArrayList<>();

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public Producto agregarArmazon(String nombre, Double precio, int stock, Medidas medidas){
  
        Producto nuevoArmazon = new Armazones(nombre, precio, stock, medidas);
        productos.add(nuevoArmazon);
        return nuevoArmazon;
    }

    public List<Producto> listarProductos(){
        return List.copyOf(productos);
    }

    public Producto guardar(Producto producto) {
        productos.add(producto);
        return producto;
}

    public Producto buscarPorID(Integer idBuscado) {
        for (Producto producto : productos) {
            if (producto.getId().equals(idBuscado)){
                return producto;
            }
        }
        throw new ProductoNoEncontradoExcepcion("Id wasn't find it: " + idBuscado);
    }

    public List<Producto> buscarPorNombre(String nombreBuscado) {
        List<Producto> encontrados = new ArrayList<>();
        if (nombreBuscado == null || nombreBuscado.isEmpty()) {
            return encontrados;
        }
         String criterio = nombreBuscado.toLowerCase(Locale.ROOT);
        for (Producto producto : productos) {
            if (producto.getNombre().toLowerCase(Locale.ROOT).contains(criterio)) {
                encontrados.add(producto);
            }
        }
        return encontrados;
    }

    public void eliminarProducto(Integer id) {
        productos.remove(buscarPorID(id));
    }

    public Producto actualizarProducto(Integer id, Double precio, Integer stock) {
        Producto producto = buscarPorID(id);

        if (precio != null && (precio < 0. || Double.isNaN(precio))){
            throw new EntradaInvalidaExcepcion("The price is not valid");
        }
        
        if (stock != null && stock < 0) {
            throw new EntradaInvalidaExcepcion("Stock number is not valid");
        }

        if (precio != null) {
            producto.setPrecio(precio);
        }

        if (stock != null) {
            producto.setStock(stock);
        }
        return producto;
    }

    public void cargarProductosPrueba() {
        agregarArmazon("RayBan Aviator", 45000.0, 10, new Medidas(140.0, 145.0, 50.0, 35.0));
        agregarArmazon("Seven", 15000.0, 20, new Medidas(138.0, 142.0, 48.0, 34.0));
        agregarArmazon("Sol Bordo", 25000.0, 15, new Medidas(140.0, 145.0, 53.0, 48.0));
    }

    public List<Producto> getProductos() {
        return repository.findAll();
    }
}
