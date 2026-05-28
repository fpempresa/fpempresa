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
package es.logongas.fpempresa.businessprocess.email.impl;

import es.logongas.fpempresa.businessprocess.email.EmailBusinessProcess;
import es.logongas.fpempresa.config.Config;
import es.logongas.fpempresa.service.kernel.mail.Mail;
import es.logongas.fpempresa.service.kernel.mail.MailKernelService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailBusinessProcessImpl implements EmailBusinessProcess {

    private Class entityType;

    @Autowired
    private MailKernelService mailKernelService;

    @Override
    public void setEntityType(Class entityType) {
        this.entityType = entityType;
    }

    @Override
    public Class getEntityType() {
        return this.entityType;
    }

    @Override
    public void sendEmail(SendEmailArguments sendEmailArguments) throws Exception {
        Mail mail = new Mail();
        mail.setTo(Arrays.asList(sendEmailArguments.to));
        mail.setFrom(Config.getSetting("mail.sender"));
        mail.setSubject(sendEmailArguments.subject);
        mail.setHtmlBody(sendEmailArguments.body);

        mailKernelService.send(mail);
    }

}
