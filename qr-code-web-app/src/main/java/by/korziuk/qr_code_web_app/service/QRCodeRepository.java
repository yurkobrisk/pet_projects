package by.korziuk.qr_code_web_app.service;

import by.korziuk.qr_code_web_app.domain.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QRCodeRepository extends JpaRepository<QRCode, Long> {

    QRCode findByDescription(String description);

    boolean existsByDescription(String description);
}
