/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.service;

import com.ecommerce.model.DetalleOrden;
import com.ecommerce.repository.DetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lucas
 */
@Service
public class DetalleOrdenServiceImpl implements IDetalleOrdenService{

    @Autowired
    private DetalleOrdenRepository doR;
    
    @Override
    public DetalleOrden save(DetalleOrden dtorden) {
       return doR.save(dtorden);
    }
    
}
