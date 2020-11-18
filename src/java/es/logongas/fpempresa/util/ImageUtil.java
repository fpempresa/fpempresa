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
package es.logongas.fpempresa.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import org.apache.commons.io.output.NullOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utilidades para el tratamiento de imagenes.
 *
 * @author logongas
 */
public class ImageUtil {

    static final Logger log = LogManager.getLogger(ImageUtil.class);

    public static boolean isValid(byte[] rawImage) {
        try {
            BufferedImage image = getImage(rawImage);
            
            ImageIO.write(image,"jpeg",new NullOutputStream());
            
            if (image == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public static BufferedImage getImage(byte[] rawImage) {

        try {
            return ImageIO.read(new ByteArrayInputStream(rawImage));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Esta funciona está para ser usada desde JaperReports, ya que desde ahí no podemos capturar el error y hacer un log
     * @param rawImage
     * @param msgFail
     * @return
     */
    public static Image getImageLogFail(byte[] rawImage,String msgFail) {


            BufferedImage image = getImage(rawImage);
            
            if (image == null) {
                log.warn("getImage Fail-Is null-msgFail=" + msgFail);
                return null;
            }
            
            try {
                ImageIO.write(image,"jpeg",new NullOutputStream());
            } catch (Exception ex) {
                log.warn("getImage Fail-Exception=" + ex.getMessage() + "-msgFail="+msgFail);
            }
            return image;
     
    }
}
