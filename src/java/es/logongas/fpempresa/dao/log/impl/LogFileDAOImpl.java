/**
 * FPempresa Copyright (C) 2026 Lorenzo González
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
package es.logongas.fpempresa.dao.log.impl;

import es.logongas.fpempresa.dao.log.LogFileDAO;
import es.logongas.fpempresa.modelo.log.LogFile;
import es.logongas.ix3.dao.DataSession;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author logongas
 */
public class LogFileDAOImpl implements LogFileDAO {
    
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("txt", "log");

    @Override
    public List<LogFile> search(DataSession dataSession) {
        List<File> files = getSortedFiles();
        List<LogFile> result = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            result.add(fileToLogFile(i + 1, files.get(i)));
        }
        return result;
    }

    @Override
    public LogFile read(DataSession dataSession, int idLogFile) {
        List<File> files = getSortedFiles();
        if (idLogFile < 1 || idLogFile > files.size()) {
            return null;
        }
        File file = files.get(idLogFile - 1);
        LogFile logFile = fileToLogFile(idLogFile, file);
        try {
            logFile.setContent(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8));
        } catch (IOException ex) {
            logFile.setContent("Error al leer el fichero: " + ex.getMessage());
        }
        return logFile;
    }

    
    
    /********************* Utilidades de ficheros *********************/
    
    private File getLogsDirectory() {
        String catalinaBase = System.getProperty("catalina.base");
        if (catalinaBase == null) {
            catalinaBase = System.getProperty("catalina.home");
        }
        return new File(catalinaBase, "logs");
    }


    private boolean isAllowedFile(File file) {
        if (!file.isFile()) {
            return false;
        }
        String nombre = file.getName();
        int lastDot = nombre.lastIndexOf('.');
        if (lastDot < 0) {
            return false;
        }
        String extension = nombre.substring(lastDot + 1).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(extension);
    }

    private List<File> getSortedFiles() {
        File logsDir = getLogsDirectory();
        File[] files = logsDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return isAllowedFile(file);
            }
        });
        if (files == null) {
            return new ArrayList<File>();
        }
        List<File> fileList = new ArrayList<File>(Arrays.asList(files));
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });
        return fileList;
    }

    private LogFile fileToLogFile(int idLogFile, File file) {
        LogFile logFile = new LogFile();
        logFile.setIdLogFile(idLogFile);
        logFile.setNombre(file.getName());
        logFile.setSize(file.length());
        logFile.setFechaModificacion(new Date(file.lastModified()));
        try {
            BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            logFile.setFechaCreacion(new Date(attrs.creationTime().toMillis()));
        } catch (IOException ex) {
            logFile.setFechaCreacion(new Date(file.lastModified()));
        }
        return logFile;
    }

    
    
    
}
