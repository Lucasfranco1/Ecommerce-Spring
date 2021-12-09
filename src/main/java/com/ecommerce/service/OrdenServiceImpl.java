/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.service;

import com.ecommerce.model.Orden;
import com.ecommerce.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceImpl implements IOrdenService {

    @Autowired
    private OrdenRepository oR;

    @Override
    public Orden save(Orden orden) {
        return oR.save(orden);
    }

}
