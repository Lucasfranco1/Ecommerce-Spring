/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
    
    private String folder="images//";
    
    public String saveImages(MultipartFile file) throws IOException{
        if(!file.isEmpty()){
            //imagen no viene vacía
            //Convertir en bytes
            byte[]bytes=file.getBytes();
            Path path=Paths.get(folder+file.getOriginalFilename());//en ese path quiero que se guarde
            Files.write(path, bytes);
            return file.getOriginalFilename();//con esto retorno el nombre de la imagen que he subido
        }
        //si el usuario no ha subido una imagen, habrá una por default...
        return "default.jpg";
    }
    public void deleteImage(String nombre){
        String ruta="images//";
        File file=new File(ruta+nombre);
        file.delete();
        
    }
    
}

