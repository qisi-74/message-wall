package com.qisi.open.listener.client;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author : qisi
 * @date: 2023/2/21
 * @description: 飞书的客户端
 */
@Slf4j
@Service
public class LarkClient {
    @Value("${lark.encryptKey}")
    private String encryptKey;
    @Value("${lark.verificationToken}")
    private String verificationToken;

    @PostConstruct
    public void LarkClient(){
        log.info(encryptKey);
        log.info(verificationToken);
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public String getVerificationToken() {
        return verificationToken;
    }
    public String calculateSignature(String timestamp, String nonce, String bodyString) throws NoSuchAlgorithmException {
        StringBuilder content = new StringBuilder();
        content.append(timestamp).append(nonce).append(encryptKey).append(bodyString);
        MessageDigest alg = MessageDigest.getInstance("SHA-256");
        String sign = Hex.encodeHexString(alg.digest(content.toString().getBytes()));
        return sign;
    }
    public String decrypt(String base64) throws Exception {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            // won't happen
        }
        byte[] keyBs = digest.digest(encryptKey.getBytes(StandardCharsets.UTF_8));
        byte[] decode = Base64.getDecoder().decode(base64);
        Cipher cipher = Cipher.getInstance("AES/CBC/NOPADDING");
        byte[] iv = new byte[16];
        System.arraycopy(decode, 0, iv, 0, 16);
        byte[] data = new byte[decode.length - 16];
        System.arraycopy(decode, 16, data, 0, data.length);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBs, "AES"), new IvParameterSpec(iv));
        byte[] r = cipher.doFinal(data);
        if (r.length > 0) {
            int p = r.length - 1;
            for (; p >= 0 && r[p] <= 16; p--) {
            }
            if (p != r.length - 1) {
                byte[] rr = new byte[p + 1];
                System.arraycopy(r, 0, rr, 0, p + 1);
                r = rr;
            }
        }
        return new String(r, StandardCharsets.UTF_8);
    }
}
