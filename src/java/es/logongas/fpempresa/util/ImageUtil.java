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

import java.awt.Color;
import java.awt.Graphics2D;
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
        BufferedImage bufferedImage=readImage(rawImage);

        if (canWriteImageToJPEG(bufferedImage)) {
            return true;
        } else {
            BufferedImage bufferedImageWhiteBackground=getBufferedImageToWhiteBackground(bufferedImage);

            if (canWriteImageToJPEG(bufferedImageWhiteBackground)) {
                return true;
            } else {
                return false;
            }
        }
    }


    /**
     * Esta funciona está para ser usada desde JaperReports, ya que desde ahí no podemos capturar el error y hacer un log
     * @param rawImage
     * @param msgFail
     * @return
     */
    public static Image getImageLogFail(byte[] rawImage,String msgFail) {
        Image image;

        BufferedImage bufferedImage=readImage(rawImage);


        if (canWriteImageToJPEG(bufferedImage)) {
            image=bufferedImage;
        } else {
            BufferedImage bufferedImageWhiteBackground=getBufferedImageToWhiteBackground(bufferedImage);

            if (canWriteImageToJPEG(bufferedImageWhiteBackground)) {
                image=bufferedImageWhiteBackground;
            } else {
                log.warn("Fail Image to JPEG-msgFail="+msgFail);
                image=bufferedImageWhiteBackground;
            }
        }

        return image;
    }

    
    private static BufferedImage readImage(byte[] rawImage) {
        try {
        
            BufferedImage bufferedImage=ImageIO.read(new ByteArrayInputStream(rawImage));
        
            return bufferedImage;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private static boolean canWriteImageToJPEG(BufferedImage image) {
        try {
        
            ImageIO.write(image,"jpeg",new NullOutputStream());
        
            return true;
        } catch (Exception ex) {
            return false;
        }
    }    
    
    private static BufferedImage getBufferedImageToWhiteBackground(BufferedImage bufferedImage) {
        try {
        
            BufferedImage bufferedImageWhite = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = bufferedImageWhite.createGraphics();
            graphics2D.setColor(Color.WHITE); 
            graphics2D.fillRect(0, 0, bufferedImageWhite.getWidth(), bufferedImageWhite.getHeight());
            graphics2D.drawImage(bufferedImage, 0, 0, bufferedImageWhite.getWidth(), bufferedImageWhite.getHeight(), null);
            graphics2D.dispose();
        
            return bufferedImageWhite;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }    
    
}
