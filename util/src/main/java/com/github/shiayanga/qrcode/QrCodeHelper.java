package com.github.shiayanga.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @author LiYang
 */
public class QrCodeHelper {
    private static final MatrixToImageConfig DEFAULT_CONFIG = new MatrixToImageConfig();

    /**
     * 生成图片
     *
     * @param matrix
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        return toBufferedImage(matrix, DEFAULT_CONFIG);
    }

    /**
     * 生成图片
     *
     * @param matrix
     * @param config
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix, MatrixToImageConfig config) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, config.getBufferedImageColorModel());
        int onColor = config.getPixelOnColor();
        int offColor = config.getPixelOffColor();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? onColor : offColor);
            }
        }

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.drawString("邀请码：XVYTAD", matrix.getWidth() / 2 - 35, matrix.getHeight() - 20);
        return image;
    }

    /**
     * 写出图片到流
     *
     * @param matrix
     * @param format
     * @param outputStream
     * @throws IOException
     */
    public static void writeToStream(BitMatrix matrix, String format, OutputStream outputStream) throws IOException {
        writeToStream(matrix, format, outputStream, DEFAULT_CONFIG);
    }

    /**
     * 写出图片到流
     *
     * @param matrix
     * @param format
     * @param outputStream
     * @param config
     * @throws IOException
     */
    public static void writeToStream(BitMatrix matrix, String format, OutputStream outputStream, MatrixToImageConfig config) throws IOException {
        BufferedImage image = toBufferedImage(matrix, config);
        if (!ImageIO.write(image, format, outputStream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    public static void generate(OutputStream outputStream) throws WriterException, IOException {
        String content = "http://www.baidu.com";
        int width = 500;
        int height = 500;
        String format = "jpg";
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        hints.put(EncodeHintType.MARGIN, 4);
        BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

        writeToStream(matrix, format, outputStream);
    }
}
