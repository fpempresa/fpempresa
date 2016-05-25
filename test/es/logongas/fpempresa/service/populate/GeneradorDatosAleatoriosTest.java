/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.populate;

import com.aeat.valida.Validador;
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
        Validador validadorCIF = new Validador();        
        
        String cif=GeneradorDatosAleatorios.getCif();
        int valor=validadorCIF.checkNif(cif);
        
        if (valor<=0) {
            throw new RuntimeException("error de validacion:" + valor);
        }

    } 
    
    @Test
    public void test_generador_nif() {
        Validador validadorCIF = new Validador();        
        
        String nif=GeneradorDatosAleatorios.getNif();

        int valor=validadorCIF.checkNif(nif);
        
        if (valor<=0) {
            throw new RuntimeException("error de validacion:" + valor);
        }

    }  
    
}
