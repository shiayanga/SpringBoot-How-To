package com.github.shiayanga.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
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
        return image;
    }

    /**
     * 生成图片
     *
     * @param matrix
     * @param config
     * @return
     */
    public static BufferedImage toBufferedImageWithLogo(BitMatrix matrix, MatrixToImageConfig config) throws Exception {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int logH = (int) (height*0.2);
        int logW = (int) (width*0.2);

        BufferedImage image = toBufferedImage(matrix, config);
        String url = "http://192.168.0.134:21001/love-chat-image/春风交友图标96.png";
        BufferedImage poster = ImageIO.read(new URL(url));

        int x = (width - logW) / 2;
        int y = (height - logH) / 2;

        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = resultImage.createGraphics();

        graphics.drawImage(image,0,0,null);
        graphics.drawImage(poster,x,y,logW,logH,null);
        Shape shape = new RoundRectangle2D.Float(x, y, logW, logH, 30, 30);
        graphics.setStroke(new BasicStroke(3f));
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.draw(shape);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Source Han Sans CN",Font.PLAIN,30));
        graphics.drawString("我的邀请码:XVYTAD", matrix.getWidth() / 2 - 90, matrix.getHeight() - 15);
        graphics.dispose();

        return resultImage;
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

    /**
     * 写出图片到流
     *
     * @param matrix
     * @param format
     * @param outputStream
     * @param config
     * @throws IOException
     */
    public static void writeToStreamWithLogo(BitMatrix matrix, String format, OutputStream outputStream, MatrixToImageConfig config) throws Exception {
        BufferedImage image = toBufferedImageWithLogo(matrix, config);
        if (!ImageIO.write(image, format, outputStream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }


    public static void generate(OutputStream outputStream) throws Exception {
        String content = "http://www.baidu.com";
        int width = 500;
        int height = 500;
        String format = "png";
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        // 指定字符集
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        // 指定边缘的宽度
        hints.put(EncodeHintType.MARGIN, 3);
        // 指定纠错级别。纠错级别越高，可以修正的错误就越多，但二维码的密度也越大。可选值包括L，M，Q和H，表示从7%到30%的纠错能力
        hints.put(EncodeHintType.ERROR_CORRECTION, "H");

        BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

        writeToStreamWithLogo(matrix, format, outputStream,DEFAULT_CONFIG);
    }
}
