/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.service;
import com.ecommerce.controller.exceptions.MyException;
import java.util.Optional;
import com.ecommerce.model.Usuario;
import com.ecommerce.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {
@Autowired
private UsuarioRepository uR;
    @Override
    public Optional<Usuario> findById(Integer id) {
        return uR.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
       return uR.save(usuario);
    }

    @Override
    public Optional<Usuario> findByEmail(String mail) {
       return null;    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario=uR.buscarPorMail(username);
      if(usuario!=null){
          List<GrantedAuthority>permisos=new ArrayList<>();
          GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_"+ usuario.getTipo());
            permisos.add(p1);
         
            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            
            session.setAttribute("usuariosession", usuario); // llave + valor

          User user=new User(usuario.getEmail(), usuario.getPassword(), permisos);
          
          return user;
      }else{
          return null;
      }
    }
    
    /*
    private String nombre;
	private String username;
	private String email;
	private String direccion;
	private String telefono;
	private String tipo;
	private String password;
    */
    
    @Transactional
    public void crearUser(String nombre, String username, String email, String direccion,
        String telefono, String tipo, String password) throws MyException{
        
        validacion(nombre, username, email, direccion, telefono, tipo, password);
        
        Usuario usuario = new Usuario();       
        usuario.setNombre(nombre);
        usuario.setUsername(username);
        usuario.setEmail(email);
        usuario.setDireccion(direccion);
        usuario.setTelefono(telefono);
        usuario.setTipo(tipo);
        String encriptada=new BCryptPasswordEncoder().encode(password);
        usuario.setPassword(encriptada);
        
        uR.save(usuario);
        
    }
    
    @Transactional
    public void modificarUser(Integer id, String nombre, String username, String email, String direccion,
            String telefono, String tipo){
        
    }

    private void validacion(String nombre, String username, String email, String direccion, 
            String telefono, String tipo, String password) throws MyException {
       
        if(nombre==null || nombre.isEmpty()){
            throw new MyException("El nombre no puede ser nulo, ni estar vacío.");
        }
        if(username==null|| username.isEmpty()){
            throw new MyException("El username no puede ser nulo, ni estar vacío.");
        }
        if(email==null|| email.isEmpty()){
            throw new MyException("El email no puede estar nulo ni vacío.");
            
        }
        if(direccion==null|| direccion.isEmpty()){
            throw new MyException("La dirección no puede estar nula ni vacía.");
        }
        if(telefono==null|| telefono.isEmpty()){
            throw new MyException("El teléfono no puede estar nulo, ni vacío.");
        }
        if(tipo==null|| tipo.isEmpty()){
            throw new MyException("El tipo no puede ser nulo ni vacío");
            
        }
        if(password==null|| password.isEmpty()||password.length()<6){
            throw new MyException("La password no puede ser nula, ni venir vacía, y no debe ser inferior a 6 caracteres.");
        }
    }
    
}
