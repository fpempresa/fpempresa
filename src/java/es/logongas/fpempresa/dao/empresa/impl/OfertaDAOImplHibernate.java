/**
 * FPempresa Copyright (C) 2015 Lorenzo González
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
package es.logongas.fpempresa.dao.empresa.impl;

import es.logongas.fpempresa.dao.empresa.OfertaDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.impl.GenericDAOImplHibernate;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author logongas
 */
public class OfertaDAOImplHibernate extends GenericDAOImplHibernate<Oferta, Integer> implements OfertaDAO {

    @Override
    public List<Oferta> getOfertasUsuarioTitulado(DataSession dataSession, Usuario usuario, Provincia provincia, Date fechaInicio, Date fechaFin) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT oferta.*\n"
                + "FROM\n"
                + "   oferta   \n"
                + "		INNER JOIN  ofertaciclo   ON oferta.idOferta=ofertaciclo.idOferta \n"
                + "		INNER JOIN  municipio as municipiooferta ON oferta.idMunicipio=municipiooferta.idMunicipio\n"
                + "		INNER JOIN  empresa           ON oferta.idEmpresa=empresa.idEmpresa,\n"
                + "   usuario \n"
                + "		INNER JOIN  formacionacademica ON usuario.idTitulado=formacionacademica.idTitulado \n"
                + "		INNER JOIN  titulado                     ON usuario.idTitulado=titulado.idTitulado  \n"
                + "		INNER JOIN  municipio as municipiotitulado           ON titulado.idMunicipio=municipiotitulado.idMunicipio\n"
                + "WHERE\n"
                + "   oferta.cerrada <> 1 AND\n"
                + "   usuario.idIdentity=? AND\n"
                + "   empresa.idCentro IS NULL AND\n"
                + "   ofertaciclo.idCiclo=formacionacademica.idCiclo AND\n");

        if (provincia != null) {
            sb.append("   municipiooferta.idProvincia=? AND\n");
        }
        if (fechaInicio != null) {
            sb.append("   oferta.fecha>=? AND\n");
        }
        if (fechaFin != null) {
            sb.append("   oferta.fecha<=? AND\n");
        }

        sb.append("   not exists (SELECT * FROM  candidato WHERE candidato.idIdentity=usuario.idIdentity AND candidato.idOferta=oferta.idOferta)\n");

        sb.append(" UNION DISTINCT \n");

        sb.append("SELECT oferta.*\n"
                + "FROM\n"
                + "   oferta   \n"
                + "		INNER JOIN  ofertaciclo   ON oferta.idOferta=ofertaciclo.idOferta \n"
                + "		INNER JOIN  municipio as municipiooferta ON oferta.idMunicipio=municipiooferta.idMunicipio\n"
                + "		INNER JOIN  empresa           ON oferta.idEmpresa=empresa.idEmpresa,\n"
                + "   usuario \n"
                + "		INNER JOIN  formacionacademica ON usuario.idTitulado=formacionacademica.idTitulado   \n"
                + "WHERE\n"
                + "   oferta.cerrada <> 1 AND\n"
                + "   usuario.idIdentity=? AND\n"
                + "   empresa.idCentro = formacionacademica.idCentro AND\n"
                + "   ofertaciclo.idCiclo=formacionacademica.idCiclo AND\n");
        if (provincia != null) {
            sb.append("   municipiooferta.idProvincia=? AND\n");
        }
        if (fechaInicio != null) {
            sb.append("   oferta.fecha>=? AND\n");
        }
        if (fechaFin != null) {
            sb.append("   oferta.fecha<=? AND\n");
        }

        sb.append("   not exists (SELECT * FROM candidato WHERE candidato.idIdentity=usuario.idIdentity AND candidato.idOferta=oferta.idOferta)");

        SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
        sqlQuery.addEntity(Oferta.class);
        
        int paramNumber=0;
        
        sqlQuery.setInteger(paramNumber++, usuario.getIdIdentity());
        if (provincia != null) {
            sqlQuery.setInteger(paramNumber++, provincia.getIdProvincia());
        }
        if (fechaInicio != null) {
            sqlQuery.setDate(paramNumber++, fechaInicio);
        }
        if (fechaFin != null) {
            sqlQuery.setDate(paramNumber++, fechaFin);
        }        
        sqlQuery.setInteger(paramNumber++, usuario.getIdIdentity());
        if (provincia != null) {
            sqlQuery.setInteger(paramNumber++, provincia.getIdProvincia());
        }
        if (fechaInicio != null) {
            sqlQuery.setDate(paramNumber++, fechaInicio);
        }
        if (fechaFin != null) {
            sqlQuery.setDate(paramNumber++, fechaFin);
        } 

        return (List<Oferta>) sqlQuery.list();
    }

    @Override
    public List<Oferta> getOfertasInscritoUsuarioTitulado(DataSession dataSession, Usuario usuario, Provincia provincia, Date fechaInicio, Date fechaFin) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT candidato.oferta FROM  Candidato  as candidato WHERE candidato.usuario.idIdentity=?");

        if (provincia!=null) {
            sb.append(" AND candidato.oferta.municipio.provincia.idProvincia=? ");
        }
        if (fechaInicio!=null) {
            sb.append(" AND candidato.oferta.fecha>=? ");
        }
        if (fechaFin!=null) {
            sb.append(" AND candidato.oferta.fecha<=? ");
        }
        
        Query query = session.createQuery(sb.toString());
        int paramNumber=0;
        query.setInteger(paramNumber++, usuario.getIdIdentity());
        if (provincia != null) {
            query.setInteger(paramNumber++, provincia.getIdProvincia());
        }
        if (fechaInicio != null) {
            query.setDate(paramNumber++, fechaInicio);
        }
        if (fechaFin != null) {
            query.setDate(paramNumber++, fechaFin);
        } 
        
        return (List<Oferta>) query.list();
    }

    @Override
    public List<Oferta> getOfertasEmpresasCentro(DataSession dataSession, Centro centro) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String hql = "SELECT oferta FROM oferta WHERE oferta.empresa.centro.idCentro=?";

        Query query = session.createQuery(hql);
        query.setInteger(0, centro.getIdCentro());

        return (List<Oferta>) query.list();
    }

    @Override
    public List<Oferta> getOfertasEmpresa(DataSession dataSession, Empresa empresa) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String hql = "SELECT oferta FROM oferta WHERE oferta.empresa.idEmpresa=?";

        Query query = session.createQuery(hql);
        query.setInteger(0, empresa.getIdEmpresa());

        return (List<Oferta>) query.list();
    }

}
