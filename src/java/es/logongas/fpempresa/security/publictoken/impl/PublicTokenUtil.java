/*
 * FPempresa Copyright (C) 2021 Lorenzo González
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
package es.logongas.fpempresa.security.publictoken.impl;

import es.logongas.ix3.web.security.jwt.Jws;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author logongas
 */
public class PublicTokenUtil {
    private static final String PAYLOAD_PARTS_SEPARATOR="|";
    
    
    static public String createUserPublicToken(List<String> data,byte[] secretKey,Task task,Jws jws) {
        StringBuilder payload=new StringBuilder();
        
        payload.append(task.name());
        
        if (data!=null) {
            for(String singleData:data) {
                
                if ((singleData!=null) && (singleData.contains(PAYLOAD_PARTS_SEPARATOR))) {
                    throw new RuntimeException("El dato contiene el separador" );
                }
                
                payload.append(PAYLOAD_PARTS_SEPARATOR);
                payload.append(singleData);
            }
            
        }
        
        return jws.getJwsCompactSerialization(payload.toString(), secretKey);
    }
    
    static public List<String> getDataFromUserPublicToken(String UserPublicToken,Task task,Jws jws)  {
        String payload = jws.getUnverifiedPayloadFromJwsCompactSerialization(UserPublicToken);
        
        String[] arrDatas=payload.split("\\"+PAYLOAD_PARTS_SEPARATOR);
        
        if (arrDatas.length<=0) {
            throw new RuntimeException("No hay tarea:"+arrDatas[0]);
        }
        
        if (arrDatas[0].equals(task.name())==false) {
            throw new RuntimeException("La tarea no es válida:"+arrDatas[0]);
        }
        
        List data=new ArrayList<>();
        
        for(int i=1;i<arrDatas.length;i++) {
            data.add(arrDatas[i]);
        }
        
        return data;
    }
    
    static public boolean verifyUserPublicToken(String userPublicToken,byte[] secretKey,Jws jws,int maxAgeMinutes) {
        return jws.verifyJwsCompactSerialization(userPublicToken, secretKey, maxAgeMinutes);
    }    
    
}
