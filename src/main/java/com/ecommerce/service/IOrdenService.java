/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.service;

import com.ecommerce.model.Orden;
import java.util.List;

/**
 *
 * @author lucas
 */
public interface IOrdenService {
     public Orden save(Orden orden);
     
     public List<Orden>findAll();
}
