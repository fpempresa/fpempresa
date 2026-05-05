/**
 * FPempresa Copyright (C) 2026 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.modelo.log;

import es.logongas.ix3.core.annotations.Label;
import java.util.Date;

/**
 *
 * @author logongas
 */
public class LogFile {

    private int idLogFile;

    @Label("Nombre")
    private String nombre;

    @Label("Fecha de creación")
    private Date fechaCreacion;

    @Label("Fecha de modificación")
    private Date fechaModificacion;

    @Label("Tamaño")
    private long size;

    @Label("Contenido")
    private String content;

    public int getIdLogFile() {
        return idLogFile;
    }

    public void setIdLogFile(int idLogFile) {
        this.idLogFile = idLogFile;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
