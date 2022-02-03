package com.farinia.desafioTest.controller;

import com.farinia.desafioTest.handle.ApiRestException;
import com.farinia.desafioTest.model.Productos;
import com.farinia.desafioTest.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    ProductoService service;

    @GetMapping("/productos/ej")
    public String getEjemplo() {
        return "ejemplo";
    }

    @GetMapping("/productos")
    public List<Productos> getProductos() {
        return service.findAll();
    }

    @GetMapping("/productos/{id}")
    public Productos getProductosbyId(@PathVariable Long id) throws ApiRestException {
        return service.findById(id);
    }

    @PostMapping("/productos")
    public Productos createProductos(@RequestBody Productos productos) throws ApiRestException {
        return service.create(productos);
    }

    @PutMapping("/productos")
    public void updateProductos(@RequestBody Productos productos) throws ApiRestException {
        service.update(productos);
    }

    @DeleteMapping("/productos/{id}")
    public void deleteProductos(@PathVariable Long id) throws ApiRestException {
        service.delete(id);
    }
}
