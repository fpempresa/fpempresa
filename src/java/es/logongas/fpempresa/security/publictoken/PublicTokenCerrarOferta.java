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
package es.logongas.fpempresa.security.publictoken;

import es.logongas.fpempresa.security.publictoken.impl.PublicTokenUtil;
import es.logongas.fpempresa.security.publictoken.impl.Task;
import es.logongas.ix3.web.security.jwt.Jws;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author logongas
 */
public class PublicTokenCerrarOferta {
    private static final Task task=Task.CERRAR_OFERTA;
    private static final int MAX_AGE_PUBLIC_TOKEN_DIAS=90; 
    private static final int maxAgeMinutes=MAX_AGE_PUBLIC_TOKEN_DIAS*24*60;
    
    
    int idOferta;
    Jws jws;
    byte[] secretKey;
    String userPublicToken;
    
    public PublicTokenCerrarOferta(int idOferta,Jws jws,byte[] secretKey) {
        this.idOferta = idOferta;
        this.jws=jws;
        this.secretKey=secretKey;
        this.userPublicToken=null;
    }
    
    public PublicTokenCerrarOferta(String userPublicToken,Jws jws,byte[] secretKey) {
        this.userPublicToken=userPublicToken;
        
        List<String> data=PublicTokenUtil.getDataFromUserPublicToken(userPublicToken, task, jws);
        
        
        this.idOferta = Integer.parseInt(data.get(0));
        this.jws=jws;
        this.secretKey=secretKey;
    }    
    
    
    public boolean isValid() {
        if (userPublicToken==null) {
            throw new RuntimeException("No hay un userPublicToken");
        }
        
        return PublicTokenUtil.verifyUserPublicToken(userPublicToken, secretKey, jws, maxAgeMinutes);
    }
    
    public int getIdOferta() {
        return idOferta;
    }

    @Override
    public String toString() {
        return PublicTokenUtil.createUserPublicToken(getData(), secretKey, task, jws);
    }
    
    private List<String> getData() {
        List<String> data=new ArrayList<>();
        
        data.add(idOferta+"");
        
        return data;
    }
    
}
