/**
 * FPempresa Copyright (C) 2020 Lorenzo González
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



package es.logongas.fpempresa.dao.titulado.impl;

import es.logongas.fpempresa.dao.titulado.TituladoDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.impl.GenericDAOImplHibernate;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author GnommoStudios
 */
public class TituladoDAOImplHibernate extends GenericDAOImplHibernate<Titulado, Integer> implements TituladoDAO {

    @Override
    public List<Titulado> getTituladosSuscritosPorProvinciaOfertaYCiclosOferta(DataSession dataSession, Oferta oferta) {

        Session session = (Session) dataSession.getDataBaseSessionImpl();

        StringBuilder stringBuilder = new StringBuilder();

        String query = ""
            +" SELECT "
            +"	DISTINCT 	titulado.* "
            +" FROM "
            +"	titulado "
            +"		INNER JOIN tituladoprovincianotificacion ON titulado.idTitulado=tituladoprovincianotificacion.idTitulado "
            +"		INNER JOIN formacionacademica  ON titulado.idTitulado=formacionacademica.idTitulado "
            +" WHERE "
            +"	titulado.notificarPorEmail = 1 AND "
            +"	tituladoprovincianotificacion.idProvincia=? AND "
            +"	formacionacademica.idCiclo IN ( "
            +"		SELECT idCiclo "
            +"		FROM ofertaciclo "
            +"		WHERE ofertaciclo.idOferta = ? "
            +"	) "
            +"	AND tipoFormacionAcademica='CICLO_FORMATIVO' ";                


        Centro centro = oferta.getEmpresa().getCentro();
        if (centro != null) {
            query += "	AND formacionacademica.idCentro=? ";
        }

        stringBuilder.append(query);

        SQLQuery sqlQuery = session.createSQLQuery(stringBuilder.toString());
        sqlQuery.addEntity(Titulado.class);
        sqlQuery.setInteger(0, oferta.getMunicipio().getProvincia().getIdProvincia());
        sqlQuery.setInteger(1, oferta.getIdOferta());

        if (centro != null) {
            sqlQuery.setInteger(2, centro.getIdCentro());
        }

        return (List<Titulado>) sqlQuery.list();
    }

}
