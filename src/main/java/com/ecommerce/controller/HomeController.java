/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.service.ProductoService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ProductoService pS;
            
            
    @GetMapping("/")
    public String homeUser(ModelMap model){
       
        model.addAttribute("productos", pS.findAll());
        return "usuario/home";
    }
    
    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id, ModelMap model){
        Producto p=new Producto();
        Optional<Producto>respuesta=pS.get(id);
        p=respuesta.get();
        model.addAttribute("productos", p);
      return "usuario/productoHome";  
    }
}
