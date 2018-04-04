package es.logongas.fpempresa.service.hojacalculo;

import java.util.List;

/**
 *
 * @author logongas
 */
public interface HojaCalculoService {

    /**
     * Obtiene una hoja de calculo
     *
     * @param rows Los datos a mostrar
     * @param properties Cada una de las propiedades de cada objeto a mostrar
     * separadas por comas
     * @return La hoja de cálculo
     */
    byte[] getHojaCalculo(List<? extends Object> dataRows, String properties, String labels);

}
