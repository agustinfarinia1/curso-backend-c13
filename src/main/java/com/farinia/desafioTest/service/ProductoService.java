package com.farinia.desafioTest.service;

import com.farinia.desafioTest.handle.ApiRestException;
import com.farinia.desafioTest.model.Productos;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class ProductoService implements ProductoServiceInterface{

    //Logger logger = LogManager.getLogger(ProductoService.class);
    private final ArrayList<Productos> listaProductos;

    public ProductoService(){
        this.listaProductos = llamadoBd();
    }

    @Override
    public ArrayList<Productos> findAll() {
        return listaProductos;
    }

    @Override
    public Productos create(Productos productos) throws ApiRestException {
        if(productos.getId() <= 0){
            throw new ApiRestException("El identificador del mensaje debe ser mayor a 0");
        }
        //logger.info("Creacion en bd exitosa");
        return productos;
    }

    @Override
    public Productos findById(Long id) throws ApiRestException {
        if(getListaProductos().size() > 0 && id >0) {
            var prodFiltered = getListaProductos().stream()
                    .filter(producto -> producto.getId() == id).findFirst();
            if(prodFiltered.isPresent()){
                return prodFiltered.get();
            }
            else {
                return null;
            }
        }
        return null;
    }

    @Override
    public void update(Productos productoActualizado) throws ApiRestException {
        Productos producto = findById(productoActualizado.getId());
        if(producto != null || productoActualizado.getNombre().equals(null) || productoActualizado.getPrecio() <= 0 || productoActualizado.getStock() <= 0){
            getListaProductos().set(getListaProductos().indexOf(producto),productoActualizado);
        }
    }

    @Override
    public void delete(Long id) throws ApiRestException {
        Productos producto = findById(id);
        if(producto != null){
            getListaProductos().remove(producto);
        }
    }

    public ArrayList<Productos> llamadoBd(){
        ArrayList<Productos> bd = new ArrayList<>();
        bd.add(new Productos(1L,"Fideos",150,10));
        bd.add(new Productos(2L,"Ñoquis",200,11));
        return bd;
    }

    public ArrayList<Productos> getListaProductos(){
        return listaProductos;
    }
}
