package com.tools.decoder.decode;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class DecodeService {

    private final AesDecryptor aes;

    public DecodeService(String key) {
        this.aes = new AesDecryptor(key);
    }

    public byte[] decodeRequest(String encoded) throws IOException, GeneralSecurityException {
        String decoded = Codec.urlDecode(encoded);
        byte[] firstDecode = Codec.base64Decode(decoded);
        byte[] secondDecode = Codec.base64Decode(firstDecode);
        return GzipUtil.ungzip(aes.decrypt(secondDecode));
    }

    public byte[] decodeResponse(String encrypted) throws IOException, GeneralSecurityException {
        return GzipUtil.ungzip(aes.decrypt(Codec.base64Decode(encrypted)));
    }
}