/*
 * FPempresa Copyright (C) 2023 Lorenzo González
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
package es.logongas.fpempresa.service.kernel.captcha.impl;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Random;
import es.logongas.fpempresa.service.kernel.captcha.CaptchaKernelService;
import es.logongas.fpempresa.util.ImageUtil;

/**
 *
 * @author logongas
 */
public class CaptchaKernelServiceImpl implements CaptchaKernelService {

    private final int minFontSize=20;
    private final int maxFontSize=30;
    
    private final int widthImage=250;
    private final int heightImage=45;
    
    private final int minAcceptedWordLength=5;
    private final int maxAcceptedWordLength=5;
    
    private final String acceptedChars="ABCEFGHJKLMNPRSTUVWXYZ23456789";

    
    private final WordToImage wordToImage;
    private final WordGenerator wordGenerator;

    
    public CaptchaKernelServiceImpl() {

        FontGenerator fontGenerator = buildFontGenerator(minFontSize, maxFontSize);
        BackgroundGenerator backgroundGenerator = buildBackgroundGenerator(widthImage, heightImage);
        TextPaster textPaster = buildTextPaster(minAcceptedWordLength, maxAcceptedWordLength);
        
        wordToImage = buildWordToImage(fontGenerator, backgroundGenerator, textPaster);
        wordGenerator=buildWordGenerator(acceptedChars);
    }

    @Override
    public String getWord() {
        Integer wordLength = getWordLength();

        String word = wordGenerator.getWord(wordLength);

        return word;
    }

    @Override
    public byte[] getImage(String word) {
        BufferedImage image = wordToImage.getImage(word);

        return ImageUtil.getByteArrayFromBufferedImage(image);
    }

    
    
    private int getWordLength() {
        Random myRandom = new SecureRandom();

        int range = wordToImage.getMaxAcceptedWordLength() - wordToImage.getMinAcceptedWordLength();

        int wordLength;
        if (range == 0) {
            wordLength = wordToImage.getMinAcceptedWordLength();
        } else if (range>0) {
            int randomRange = myRandom.nextInt(range + 1);
            wordLength = randomRange + wordToImage.getMinAcceptedWordLength();
        } else {
            throw new RuntimeException("El rango no puede ser negativo:"+range);
        }

        return wordLength;
    }

    private WordToImage buildWordToImage(FontGenerator fontGenerator, BackgroundGenerator backgroundGenerator, TextPaster textPaster) {
        return new ComposedWordToImage(fontGenerator, backgroundGenerator, textPaster);
    }

    private TextPaster buildTextPaster(int minAcceptedWordLength, int maxAcceptedWordLength) {
        RandomRangeColorGenerator randomRangeColorGenerator = new RandomRangeColorGenerator(new int[]{0, 255}, new int[]{20, 100}, new int[]{20, 100});
        TextPaster textPaster = new RandomTextPaster(minAcceptedWordLength, maxAcceptedWordLength, randomRangeColorGenerator, true);

        return textPaster;
    }

    private BackgroundGenerator buildBackgroundGenerator(int widthImage, int heightImage) {
        BackgroundGenerator backgroundGenerator = new UniColorBackgroundGenerator(widthImage, heightImage, new Color(252, 252, 253));

        return backgroundGenerator;
    }

    private FontGenerator buildFontGenerator(int minFontSize, int maxFontSize) {
        Font[] fontsList = new Font[]{new Font("Helvetica", Font.TYPE1_FONT, 10), new Font("Arial", 0, 14), new Font("Vardana", 0, 17),};
        FontGenerator fontGenerator = new RandomFontGenerator(minFontSize, maxFontSize, fontsList);

        return fontGenerator;
    }

    private WordGenerator buildWordGenerator(String acceptedChars) {
        return new RandomWordGenerator(acceptedChars);
    }
    
    


}
