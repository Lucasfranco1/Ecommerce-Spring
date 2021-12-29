/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.repository;

import com.ecommerce.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT a FROM Usuario a WHERE a.alias= :nombre")
    public Usuario buscarPorAlias(@Param("nombre") String nombre);
    
    @Query("SELECT c FROM Usuario c WHERE c.username = :username")
    public Usuario buscarPorMail(@Param("username") String mail);
    
    @Query("SELECT c FROM Usuario c WHERE c.id = :id")
    public List<Usuario> buscarPorId(@Param("id") String id);
}
