package com.farinia.desafioTest.service;

import com.farinia.desafioTest.handle.ApiRestException;
import com.farinia.desafioTest.model.Productos;

import java.util.ArrayList;

public interface ProductoServiceInterface {
    ArrayList<Productos> findAll();
    Productos create(Productos productos) throws ApiRestException;
    Productos findById(Long id) throws ApiRestException;
    void update(Productos productos) throws ApiRestException;
    void delete(Long id) throws ApiRestException;
}
