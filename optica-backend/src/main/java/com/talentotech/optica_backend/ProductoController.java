package com.talentotech.optica_backend;

import java.util.Arrays;

@RestController
@RequestMapping("/productos")

public class ProductoController {
    @GetMapping
    public List<Producto> listarProducto() {
        return Arrays.asList(new Producto("name", 10000.0, 100));
    }
}
