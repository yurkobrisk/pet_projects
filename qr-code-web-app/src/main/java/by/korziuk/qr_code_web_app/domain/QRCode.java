package by.korziuk.qr_code_web_app.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "T_QUICK_REFERENCES")
public class QRCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QR_ID")
    private Long id;
    @Column(name = "QR_DESCRIPTION")
    @NotBlank
    private String description;
    @Column(name = "QR_IMAGE", columnDefinition = "blob")
    @NotNull
    private String binaryImage;

    public QRCode() {
    }

    public QRCode(String description, String binaryImage) {
        this.description = description;
        this.binaryImage = binaryImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBinaryImage() {
        return binaryImage;
    }

    public void setBinaryImage(String binaryImage) {
        this.binaryImage = binaryImage;
    }
}
