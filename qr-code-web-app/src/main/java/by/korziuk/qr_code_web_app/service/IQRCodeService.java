package by.korziuk.qr_code_web_app.service;

import by.korziuk.qr_code_web_app.domain.QRCode;

public interface IQRCodeService {

    public boolean existsByDescription(String description);
    public QRCode findByDescription(String description);
    public QRCode saveQRCode(QRCode qrCode);
    public void deleteQRCodeById(Long id);
    public void updateQRCode(QRCode qrCode);

    public String createImageQr(String description);
}
