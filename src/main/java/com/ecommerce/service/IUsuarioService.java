/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.service;
import java.util.Optional;
import com.ecommerce.model.Usuario;


public interface IUsuarioService {
   Optional<Usuario> findById(Integer id);
   Usuario save(Usuario usuario);
   Optional<Usuario>findByEmail(String mail);
}
