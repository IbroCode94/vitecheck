package com.vitmedics.vitcheck;

import com.vitmedics.vitcheck.utils.AesUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class AesUtilTests {

    @Test
    void givenString_whenEncrypt_thenSuccess()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

        String input = "baeldung";
        SecretKey key = AesUtils.generateKey(256);
        IvParameterSpec ivParameterSpec = AesUtils.generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";
        String cipherText = AesUtils.encrypt(algorithm, input, key, ivParameterSpec);
        String plainText = AesUtils.decrypt(algorithm, cipherText, key, ivParameterSpec);
        Assertions.assertEquals(input, plainText);
    }

    @Test
    void iv_encodes_and_decodes()
        throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
                BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

            String ivString = AesUtils.generateIvString();
            IvParameterSpec ivParameterSpec = AesUtils.ivSpecFromString(ivString);

            String input = "baeldung";
            String keyStr = AesUtils.generateKeyString(256);
            SecretKey key = AesUtils.keyFromString(keyStr);
            String algorithm = "AES/CBC/PKCS5Padding";
            String cipherText = AesUtils.encrypt(algorithm, input, key, ivParameterSpec);
            String plainText = AesUtils.decrypt(algorithm, cipherText, key, ivParameterSpec);
            Assertions.assertEquals(input, plainText);
    }

    /**
     * Use this test to generate an iv and key value to be used for encrypt/decrypt operations
     * @throws NoSuchAlgorithmException
     */
    @Test
    void generates_iv_and_key_strings()
            throws NoSuchAlgorithmException {

        String ivString = AesUtils.generateIvString();
        String keyStr = AesUtils.generateKeyString(256);

        Assertions.assertNotNull(ivString);
        Assertions.assertNotNull(keyStr);

    }

    /**
     * Use this test to validate iv and key string inputs that encryption/decryption works correctly.
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     */
    @Test
    void iv_encodes_and_decodes_with_static_iv_and_key()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

        String ivString ="feB62n31VV/7o1lIGn4O0Q==";
        IvParameterSpec ivParameterSpec = AesUtils.ivSpecFromString(ivString);

        String input = "znKWzZYt2";
        String keyStr =  "HLyA1ZaklP8wYHUrZXEKGQZwVXFopGvxY3hYV0SnhP4=";
        SecretKey key = AesUtils.keyFromString(keyStr);
        String cipherText = AesUtils.encrypt(AesUtils.AES_ALGORITHM, input, key, ivParameterSpec);
        String plainText = AesUtils.decrypt(AesUtils.AES_ALGORITHM, cipherText, key, ivParameterSpec);
        Assertions.assertEquals(input, plainText);
    }
}
