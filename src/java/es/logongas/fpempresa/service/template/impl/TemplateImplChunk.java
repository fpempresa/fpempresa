/**
 * FPempresa Copyright (C) 2019 Lorenzo González
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
package es.logongas.fpempresa.service.template.impl;

import com.x5.template.Chunk;
import com.x5.template.Theme;
import com.x5.template.ThemeConfig;
import es.logongas.fpempresa.service.template.Template;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author logongas
 */
public class TemplateImplChunk implements Template{
    private final Chunk chunk;

    public TemplateImplChunk(String templateText) {
        
        Map<String,String> params=new HashMap<>();
        params.put(ThemeConfig.FILTERS, "es.logongas.fpempresa.service.template.impl.ChunkDateFilter");
        ThemeConfig themeConfig=new ThemeConfig(params);
        Theme theme = new Theme(themeConfig);
        chunk = theme.makeChunk();
        chunk.append(templateText);
      
        
    }

    
    
    @Override
    public String render(Map<String, Object> models) {
        chunk.clear();
        chunk.putAll(models);
        chunk.set("now", new Date());
        
        return chunk.toString();  
    }
}
