/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.service;

import com.ecommerce.model.Producto;
import com.ecommerce.repository.ProductoRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository pR;
    
    @Override
    public Producto save(Producto producto) {
       return pR.save(producto);
    }

    @Override
    public Optional<Producto> get(Integer id) {
       return pR.findById(id);
    }

    @Override
    public void update(Producto producto) {
         pR.save(producto);
    }

    @Override
    public void delete(Integer id) {
        pR.deleteById(id);
    }
    
}
