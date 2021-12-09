/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.controller;

import com.ecommerce.model.DetalleOrden;
import com.ecommerce.model.Orden;
import com.ecommerce.model.Producto;
import com.ecommerce.model.Usuario;
import com.ecommerce.service.IDetalleOrdenService;
import com.ecommerce.service.IOrdenService;
import com.ecommerce.service.IUsuarioService;
import com.ecommerce.service.ProductoService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ProductoService pS;
    @Autowired
    private IUsuarioService uS;
    
    @Autowired
    private IOrdenService oS;

    @Autowired
    private IDetalleOrdenService doS;
    
    
    //Para guardar los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
    //Guardará los datos de la orden        
    Orden orden = new Orden();

    @GetMapping("/")
    public String homeUser(ModelMap model) {

        model.addAttribute("productos", pS.findAll());
        return "usuario/home";
    }

    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id, ModelMap model) {
        Producto p = new Producto();
        Optional<Producto> respuesta = pS.get(id);
        p = respuesta.get();
        model.addAttribute("productos", p);
        return "usuario/productoHome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, ModelMap modelo) {
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Optional<Producto> respuesta = pS.get(id);
        System.out.println("Producto:" + pS.get(id));
        System.out.println("Producto:" + cantidad);
        producto = respuesta.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        detalleOrden.setProducto(producto);

        //validar que le producto no se añada 2 veces
        Integer idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);

        if (!ingresado) {
            detalles.add(detalleOrden);
        }

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        modelo.addAttribute("cart", detalles);
        modelo.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    //Quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Integer id, ModelMap model) {
        //lista nueva de productos
        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

        for (DetalleOrden detalleOrden : detalles) {
            if (detalleOrden.getProducto().getId() != id) {
                //Va a añadir el que es diferente
                ordenesNueva.add(detalleOrden);
            }

        }
        //poner la nueva lista con los productos restantes
        detalles = ordenesNueva;

        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }
    
    @GetMapping("/getCart")
    public String getCart(ModelMap model){
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        return "usuario/carrito";
    }
    
    @GetMapping("/order")    
    public String order(ModelMap model){
        Usuario usuario=uS.findById(1).get();
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);
      return "usuario/resumenorden";
    }
    //guardar la orden
    @GetMapping("/saveOrder")
    public String saveOrder(){
        Date fechaCreacion=new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(oS.generarNumeroOrden());
        
        //usuario
        Usuario usuario=uS.findById(1).get();
          
        orden.setUsuario(usuario);
        oS.save(orden);
        
        //guardar detalles
        for (DetalleOrden dt : detalles) {
            dt.setOrden(orden);
            doS.save(dt);
        }
        
        
        ///limpiar lista y orden
        orden=new Orden();
        detalles.clear();                        
        
        return "redirect:/";
    }
    
    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre, ModelMap model){
        System.out.println("Nombre del producto: "+ nombre);
        
        //una función lambda, filtrará el producto... Y lo convertirá en una lista...
        List<Producto>productos=pS.findAll().stream().filter(p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("productos", productos);
        
        return "usuario/home";
    }
    
}
