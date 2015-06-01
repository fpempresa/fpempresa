/**
 *   FPempresa
 *   Copyright (C) 2015  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.dao.centro.impl;

import es.logongas.fpempresa.dao.centro.CertificadoTituloDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.centro.CertificadoTitulo;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.ix3.dao.impl.GenericDAOImplHibernate;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author logongas
 */
public class CertificadoTituloDAOImplHibernate extends GenericDAOImplHibernate<CertificadoTitulo, Integer> implements CertificadoTituloDAO {

    @Override
    public CertificadoTitulo getCertificadoTituloByCentroCicloAnyo(Centro centro, Ciclo ciclo, int anyo) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT certificadoTitulo FROM CertificadoTitulo certificadoTitulo  WHERE certificadoTitulo.centro.idCentro=? AND certificadoTitulo.ciclo.idCiclo=? AND certificadoTitulo.anyo=?");
        query.setInteger(0, centro.getIdCentro());
        query.setInteger(1, ciclo.getIdCiclo());
        query.setInteger(2, anyo);
        CertificadoTitulo certificadoTitulo = (CertificadoTitulo) query.uniqueResult();

        return certificadoTitulo;

    }

}
