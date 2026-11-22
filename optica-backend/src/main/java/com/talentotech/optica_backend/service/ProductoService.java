package com.talentotech.optica_backend;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Locale;
import com.talentotech.optica_backend.exceptions.*;
import org.springframework.stereotype.Service;
import com.talentotech.optica_backend.model.Producto;
import com.talentotech.optica_backend.model.Categoria;
import com.talentotech.optica_backend.repository.CategoriaRepository;
import com.talentotech.optica_backend.repository.ProductoRepository;
import com.talentotech.optica_backend.model.Armazones;
import com.talentotech.optica_backend.model.Medidas;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoRepository repository;
    private final CategoriaRepository categoriaRepository;
    private List<Producto> productos = new ArrayList<>();

    public ProductoService(ProductoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    public Producto agregarArmazon(String nombre, BigDecimal precio, int stock, Medidas medidas){
        Producto nuevoArmazon = new Armazones(nombre, precio, stock, medidas);
        productos.add(nuevoArmazon);
        return nuevoArmazon;
    }

    public List<Producto> listarProductos(){
        return List.copyOf(productos);
    }

    public Producto guardar(Producto p) {
        // p.setId(0);
        p.setCategoria(obtenerCategoria(p));
        return repository.save(p);
    }

    public Producto buscarPorID(Integer idBuscado) {
        Optional<Producto> resultado = repository.findById(idBuscado);
        if (resultado.isEmpty()){
                throw new ProductoNoEncontradoExcepcion("Id wasn't find it: " + idBuscado);
            }
        return resultado.get();
    }

    public List<Producto> buscarPorNombre(String nombreBuscado) {
        if (nombreBuscado == null || nombreBuscado.isBlank()) {
            return List.of();
        }
        return repository.findByNombreContainingIgnoreCase(nombreBuscado.strip());
    }

    public void eliminarProducto(Integer id) {
        Producto p = buscarPorID(id);
        repository.delete(p);
    }

    public Producto actualizarProducto(Integer id, BigDecimal precio, Integer stock) {
        Producto p = buscarPorID(id);

        if (precio != null) {
            p.setPrecio(precio);
        }

        if (stock != null) {
            p.setStock(stock);
        }

        return repository.save(p);
    }
    // public void cargarProductosPrueba() {
    //     agregarArmazon("RayBan Aviator", 45000.0, 10, new Medidas(140.0, 145.0, 50.0, 35.0));
    //     agregarArmazon("Seven", 15000.0, 20, new Medidas(138.0, 142.0, 48.0, 34.0));
    //     agregarArmazon("Sol Bordo", 25000.0, 15, new Medidas(140.0, 145.0, 53.0, 48.0));
    // }


    public List<Producto> getProductos() {
        return repository.findAll();
    }

    public List<Producto> listarPorCategoria(String categoria) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : repository.findAll()) {
            if (p.getCategoria().getNombre().equalsIgnoreCase(categoria)){
                resultado.add(p);
            }
        }
        return resultado;
    }

    private Categoria obtenerCategoria(Producto p){
        if (p.getCategoria() == null) {
            throw new EntradaInvalidaExcepcion("Categoria is invalid");
        }
        int idCategoria = p.getCategoria().getId();
        Optional<Categoria> resultado = categoriaRepository.findById(idCategoria);
        if (resultado.isEmpty()) {
            throw new EntradaInvalidaExcepcion("This Categoria doesn't exist" + idCategoria);
        }
        return resultado.get();
    }
}
