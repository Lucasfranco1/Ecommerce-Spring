package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.service.ProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private ProductoService pS;
    
	@GetMapping("")
	public String home(ModelMap model) {
          List<Producto>productos=pS.findAll();
          model.addAttribute("productos", productos);
          return "administrador/home";
	}
        
}
