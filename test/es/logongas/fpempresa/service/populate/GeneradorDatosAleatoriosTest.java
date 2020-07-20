/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.populate;

import es.logongas.fpempresa.util.validators.CIFNIFValidator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author logongas
 */
public class GeneradorDatosAleatoriosTest {
    
    public GeneradorDatosAleatoriosTest() {
    }

    @Test
    public void test_generador_cif() {
         
        
        String cif=GeneradorDatosAleatorios.getCif();
        CIFNIFValidator cifnifValidator = new CIFNIFValidator(cif);       
        
        boolean success=cifnifValidator.isValid();
        
        if (success==false) {
            throw new RuntimeException("error de validacion:" + cif);
        }

    } 
    
    @Test
    public void test_generador_nif() {
      
        
        String nif=GeneradorDatosAleatorios.getNif();

        CIFNIFValidator cifnifValidator = new CIFNIFValidator(nif);
        
        boolean success=cifnifValidator.isValid();
        
        if (success==false) {
            throw new RuntimeException("error de validacion:" + nif);
        }

    }  
    
}
