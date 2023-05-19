/*
 * FPempresa Copyright (C) 2023 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.modelo.captcha;

import java.util.Date;

/**
 *
 * @author logongas
 */
public class Captcha {

    private int idCaptcha;
    private String keyCaptcha;
    private Date fecha;

    /**
     * @return the idCaptcha
     */
    public int getIdCaptcha() {
        return idCaptcha;
    }

    /**
     * @param idCaptcha the idCaptcha to set
     */
    public void setIdCaptcha(int idCaptcha) {
        this.idCaptcha = idCaptcha;
    }

    /**
     * @return the keyCaptcha
     */
    public String getKeyCaptcha() {
        return keyCaptcha;
    }

    /**
     * @param keyCaptcha the keyCaptcha to set
     */
    public void setKeyCaptcha(String keyCaptcha) {
        this.keyCaptcha = keyCaptcha;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
    
}
