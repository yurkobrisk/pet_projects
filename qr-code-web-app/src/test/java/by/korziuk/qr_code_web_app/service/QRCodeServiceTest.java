package by.korziuk.qr_code_web_app.service;

import com.google.zxing.WriterException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

@ActiveProfiles("test")
@SpringBootTest
class QRCodeServiceTest {

    @Value("${binaryString}")
    String expected;

    QRCodeService qrCodeService = new QRCodeService();

    @Test
    void should_create_qr_image() {
        //Given
        String content = "Washington";
        String result = "";
        //When
        try {
            result = qrCodeService.createImageQR(content);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
        //Then
        MatcherAssert.assertThat(result, Matchers.is(expected));
    }
}