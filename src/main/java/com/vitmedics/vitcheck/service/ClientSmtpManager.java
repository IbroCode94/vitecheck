package com.vitmedics.vitcheck.service;

import com.vitmedics.vitcheck.model.entities.client.ClientSmtp;
import com.vitmedics.vitcheck.repository.client.ClientSmtpRepository;
import com.vitmedics.vitcheck.utils.AesUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class ClientSmtpManager {

    private static final Logger log = LogManager.getLogger(ClientSmtpManager.class);
    private String smtpEncryptionKey;
    private String smtpEncryptionIV;

    private ClientSmtp smtp;

    private ClientSmtpManager(ClientSmtp smtp, String smtpEncryptionKey, String smtpEncryptionIV) {
        this.smtp = smtp;
        this.smtpEncryptionKey = smtpEncryptionKey;
        this.smtpEncryptionIV = smtpEncryptionIV;
    }

    static ClientSmtpManager create(UUID clientId, String smtpEncryptionKey, String smtpEncryptionIV) {
        final ClientSmtpRepository clientSmtpRepository = VitcheckContext.getBean(ClientSmtpRepository.class);
        final ClientSmtp smtp = clientSmtpRepository.findByClientId(clientId).orElseThrow(() -> new IllegalArgumentException("Client SMTP details not found for clientId: " + clientId));
        return new ClientSmtpManager(smtp, smtpEncryptionKey, smtpEncryptionIV);
    }

    public String getUsername() {
        return smtp.getUsername();
    }

    public String getPassword() {
        final String encryptedPwd = smtp.getPwd();

        try {
            return AesUtils.decrypt(AesUtils.AES_ALGORITHM, encryptedPwd, AesUtils.keyFromString(smtpEncryptionKey), AesUtils.ivSpecFromString(smtpEncryptionIV));
        } catch (NoSuchPaddingException e) {
            log.error(e);
        } catch (NoSuchAlgorithmException e) {
            log.error(e);
        } catch (InvalidAlgorithmParameterException e) {
            log.error(e);
        } catch (InvalidKeyException e) {
            log.error(e);
        } catch (BadPaddingException e) {
            log.error(e);
        } catch (IllegalBlockSizeException e) {
            log.error(e);
        }

        return null;
    }

    public String getSenderAddress() {
        return smtp.getSenderAddress();
    }

    public String getSmtpHost() {
        return smtp.getSmtpHost();
    }

    public int getSmtpPort() {
        return smtp.getSmtpPort();
    }
}
