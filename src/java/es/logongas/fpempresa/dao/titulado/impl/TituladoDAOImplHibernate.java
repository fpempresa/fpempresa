/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
