/**
 *   FPempresa
 *   Copyright (C) 2026  Lorenzo González
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
package es.logongas.fpempresa.businessprocess.email;

import es.logongas.ix3.businessprocess.BusinessProcess;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;

public interface EmailBusinessProcess extends BusinessProcess {

    void sendEmail(SendEmailArguments sendEmailArguments) throws Exception;

    public class SendEmailArguments extends BusinessProcess.BusinessProcessArguments {

        final public String to;
        final public String subject;
        final public String body;

        public SendEmailArguments(Principal principal, DataSession dataSession, String to, String subject, String body) {
            super(principal, dataSession);
            this.to = to;
            this.subject = subject;
            this.body = body;
        }

    }

}
