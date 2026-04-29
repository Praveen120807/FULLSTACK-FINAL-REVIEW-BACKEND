package com.klef.fsad.sdp.entity;
import java.sql.Blob;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "certificate_details")
public class CertificateDetails 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String certName;

    @Column(nullable = false, length = 100)
    private String orgName;

    @Column(nullable = false)
    private String issueDate;

    @Column(nullable = false)
    private String expiryDate;

    @Column(length = 200)
    private String certificateUrl;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime addedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"password"}) 
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCertificateUrl() {
        return certificateUrl;
    }

    public void setCertificateUrl(String certificateUrl) {
        this.certificateUrl = certificateUrl;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @Lob
    private Blob fileData;

    private String fileName;

    @Override
	public String toString() {
		return "CertificateDetails [id=" + id + ", certName=" + certName + ", orgName=" + orgName + ", issueDate="
				+ issueDate + ", expiryDate=" + expiryDate + ", certificateUrl=" + certificateUrl + ", addedAt="
				+ addedAt + ", user=" + user + ", fileData=" + fileData + ", fileName=" + fileName + "]";
	}

	public Blob getFileData() {
		return fileData;
	}

	public void setFileData(Blob fileData) {
		this.fileData = fileData;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}