package by.korziuk.qr_code_web_app.service;

import by.korziuk.qr_code_web_app.domain.QRCode;
import by.korziuk.qr_code_web_app.repository.QRCodeRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QRCodeServiceImpl implements IQRCodeService{

    @Autowired
    QRCodeRepository repository;

    @Override
    public boolean existsByDescription(String description) {
        return repository.existsByDescription(description);
    }

    @Override
    public QRCode findByDescription(String description) {
        return repository.findByDescription(description);
    }

    @Override
    public QRCode saveQRCode(QRCode qrCode) {
        return repository.save(qrCode);
    }

    @Override
    public void deleteQRCodeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateQRCode(QRCode qrCode) {
        repository.save(qrCode);
    }

    public String createImageQR(String content){
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300);
        } catch (WriterException e) {
            throw new RuntimeException("Image creation error: " + e);
        }

        // Make the BufferedImage that are to hold the QRCode
        int matrixWidth = bitMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // Paint and save the image using the ByteMatrix
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
        } catch (IOException e) {
            throw new RuntimeException("Image write to OutputStream has error: " + e);
        }
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
