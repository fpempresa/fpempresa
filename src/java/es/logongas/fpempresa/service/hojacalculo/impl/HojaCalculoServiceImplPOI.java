/**
 *   FPempresa
 *   Copyright (C) 2020  Lorenzo González
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
package es.logongas.fpempresa.service.hojacalculo.impl;

import java.io.InputStream;
import java.util.List;
import es.logongas.fpempresa.service.hojacalculo.HojaCalculoService;
import es.logongas.ix3.core.conversion.Conversion;
import es.logongas.ix3.util.ReflectionUtil;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class HojaCalculoServiceImplPOI implements HojaCalculoService {

    @Override
    public byte[] getHojaCalculo(List<? extends Object> dataRows, String properties, String labels) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet();

            setDataToSheet(sheet, dataRows, properties.split(","), labels.split(","), 0);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] hojaCalculo = outputStream.toByteArray();
            workbook.close();

            return hojaCalculo;

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private int setDataToSheet(XSSFSheet sheet, List<? extends Object> dataRows, String[] properties, String[] labels, int rowNum) {

        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.getDefault());
        
        Row row = sheet.createRow(rowNum++);
        int colNum = 0;
        for (String label : labels) {
            Cell cell = row.createCell(colNum++);
            cell.setCellValue(label);
        }

        for (Object dataRow : dataRows) {
            row = sheet.createRow(rowNum++);
            colNum = 0;
            for (String property : properties) {
                Cell cell = row.createCell(colNum++);
                Object value = ReflectionUtil.getValueFromBean(dataRow, property);
                if (value == null) {
                    cell.setCellValue("");
                } else {
                    if (value instanceof Date) {
                        cell.setCellValue(dateFormat.format((Date)value));
                    } else {
                        cell.setCellValue(value.toString());
                    }
                }
            }
        }

        return rowNum;
    }

}
