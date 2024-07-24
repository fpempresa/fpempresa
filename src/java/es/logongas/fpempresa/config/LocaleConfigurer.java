/*
 * FPempresa Copyright (C) 2024 Lorenzo González
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
package es.logongas.fpempresa.config;

import java.util.Locale;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 * @author logongas
 */
public class LocaleConfigurer implements InitializingBean {
    
    private String language;
    private String country;

    static {
        //Locale.setDefault(new Locale("es"));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (language != null) {
            
            if (country!=null) {
                //Locale.setDefault(new Locale(language, country));
            } else {
                //Locale.setDefault(new Locale(language));
            }
            
        }
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
