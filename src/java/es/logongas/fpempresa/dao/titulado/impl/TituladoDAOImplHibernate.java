/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.dao.titulado.impl;

import es.logongas.fpempresa.dao.titulado.TituladoDAO;
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

        stringBuilder.append(""
                + "SELECT * FROM titulado\n"
                + "NATURAL JOIN tituladoprovincianotificacion\n"
                + "NATURAL JOIN formacionacademica\n"
                + "WHERE titulado.notificarPorEmail = 1\n"
                + "AND tituladoprovincianotificacion.idProvincia = (\n"
                + "	SELECT idProvincia\n"
                + "	FROM municipio\n"
                + "	INNER JOIN oferta\n"
                + "	ON municipio.idMunicipio = oferta.idMunicipio\n"
                + "	WHERE oferta.idOferta = ?\n"
                + "	)\n"
                + "AND formacionacademica.idCiclo IN (\n"
                + "	SELECT idCiclo\n"
                + "	FROM ofertaciclo\n"
                + "	WHERE ofertaciclo.idOferta = ?\n"
                + "	)");

        SQLQuery sqlQuery = session.createSQLQuery(stringBuilder.toString());
        sqlQuery.addEntity(Titulado.class);
        sqlQuery.setInteger(0, oferta.getIdOferta());
        sqlQuery.setInteger(1, oferta.getIdOferta());

        return (List<Titulado>) sqlQuery.list();
    }

}
