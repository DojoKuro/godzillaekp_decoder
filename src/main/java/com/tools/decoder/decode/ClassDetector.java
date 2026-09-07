package com.tools.decoder.decode;

public final class ClassDetector {

    private static final byte[] JAVA_CLASS_MAGIC =
            {(byte) 0xca, (byte) 0xfe, (byte) 0xba, (byte) 0xbe};

    private ClassDetector() {
    }

    public static boolean isJavaClass(byte[] data) {
        if (data.length < JAVA_CLASS_MAGIC.length) {
            return false;
        }
        for (int i = 0; i < JAVA_CLASS_MAGIC.length; i++) {
            if (data[i] != JAVA_CLASS_MAGIC[i]) {
                return false;
            }
        }
        return true;
    }
}