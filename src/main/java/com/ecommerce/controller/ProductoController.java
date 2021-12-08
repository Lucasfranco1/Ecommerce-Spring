/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.model.Usuario;
import com.ecommerce.service.ProductoService;
import java.util.List;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoService pS;
    
    @GetMapping("")
    public String show(ModelMap model){
        List<Producto>todo=pS.findAll();
        model.addAttribute("productos", todo);
        return "productos/show";
    }
    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }
    
    @PostMapping("/save")
    public String save(Producto producto){
        Usuario u=new Usuario(1, "", "", "", "", "", "", "");
        producto.setUsuario(u);
        pS.save(producto);
        return "redirect:/productos";
    }
}
