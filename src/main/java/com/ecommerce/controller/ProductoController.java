/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.model.Usuario;
import com.ecommerce.service.ProductoService;
import com.ecommerce.service.UploadFileService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService pS;

    @Autowired
    private UploadFileService upload;

    @GetMapping("")
    public String show(ModelMap model) {
        List<Producto> todo = pS.findAll();
        model.addAttribute("productos", todo);
        return "productos/show";
    }

    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        Usuario u = new Usuario(1, "", "", "", "", "", "", "");
        producto.setUsuario(u);

        //imagen
        if (producto.getId() == null) {//cuando se crea un producto
            String nombreImagen = upload.saveImages(file);
            producto.setImagen(nombreImagen);
        }

        pS.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, ModelMap model) {
        Producto pr = new Producto();
        Optional<Producto> respuesta = pS.get(id);
        pr = respuesta.get();
        model.addAttribute("productos", pr);
        return "productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {       
        Producto p = new Producto();
            p = pS.get(producto.getId()).get();
        if (file.isEmpty()) {
            //modifica y se cargue la misma img, no la cambiamos
            
            producto.setImagen(p.getImagen());
        } else {
            //Cuando se edita la  imagen tmb           
            if (!p.getImagen().equals("default.jpg")) {
                upload.deleteImage(p.getImagen());

            }

            //si quiero cambiar la imagen también
            String nombreImagen = upload.saveImages(file);
            producto.setImagen(nombreImagen);
        }
        producto.setUsuario(p.getUsuario());
        pS.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        //eliminamos imagen
        Producto p = new Producto();
        p = pS.get(id).get();
        //eliminamos la imagen cuando no sea la de por defecto
        if (!p.getImagen().equals("default.jpg")) {
            upload.deleteImage(p.getImagen());

        }
        pS.delete(id);
        return "redirect:/productos";
    }

}
