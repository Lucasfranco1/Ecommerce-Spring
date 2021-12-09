/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.service;

import com.ecommerce.model.Orden;
import com.ecommerce.repository.OrdenRepository;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<Orden> findAll() {
        return oR.findAll();
    }
    //formato 0000010
    public String generarNumeroOrden(){
        int num=0;
        String numeroConcatenado="";
        List<Orden>ordenes=findAll();
        
        List<Integer>numeros=new ArrayList<>();
        
        //a las ordenes le voy asignar un número, mientras vamos recorriendo la lista orden, vamos ir parseando a integer los números y añadiendo los números de orden de la lista orden, lo transformamos en integer para ir asegurando el incremento
        ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));
        
        
        if(ordenes.isEmpty()){
            num=1;
        }else{
            num=numeros.stream().max(Integer :: compare).get();//obtenemos el mayor número de la lista
            num++;
            
        }   
        if(num<10){//00000010- 00000100
            numeroConcatenado="000000000"+String.valueOf(num);
        }else if(num<100){
            numeroConcatenado="00000000"+String.valueOf(num);
        }else if(num<1000){
            numeroConcatenado="0000000"+String.valueOf(num);
        }else if(num<10000){
            numeroConcatenado="000000"+String.valueOf(num);
        }
        
        
        return numeroConcatenado;
    }

}
