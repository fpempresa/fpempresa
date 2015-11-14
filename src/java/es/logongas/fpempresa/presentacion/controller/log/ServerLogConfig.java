/**
 * FPempresa Copyright (C) 2015 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.presentacion.controller.log;

/**
 * Configuracion de los niveles del sistema de Log
 * @author logongas
 */
public class ServerLogConfig {

    private String appLevel;
    private String databaseLevel;

    public ServerLogConfig() {
    }

    public ServerLogConfig(String appLevel, String databaseLevel) {
        this.appLevel = appLevel;
        this.databaseLevel = databaseLevel;
    }

    /**
     * @return the appLevel
     */
    public String getAppLevel() {
        return appLevel;
    }

    /**
     * @param appLevel the appLevel to set
     */
    public void setAppLevel(String appLevel) {
        this.appLevel = appLevel;
    }

    /**
     * @return the databaseLevel
     */
    public String getDatabaseLevel() {
        return databaseLevel;
    }

    /**
     * @param databaseLevel the databaseLevel to set
     */
    public void setDatabaseLevel(String databaseLevel) {
        this.databaseLevel = databaseLevel;
    }

}
