package com.tools.decoder.io;

import com.tools.decoder.decode.ClassDetector;
import com.tools.decoder.decompile.CfrDecompiler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResultWriter {

    private static final String CLASS_FILE = "payload.class";

    public void write(byte[] data, String filename) throws IOException {
        Path file = Path.of(filename);
        Files.write(file, data);
        System.out.println("[+] Save: " + file.toAbsolutePath());

        if (ClassDetector.isJavaClass(data)) {
            writeJavaClass(data);
        } else {
            printPlainText(data);
        }
    }

    private void writeJavaClass(byte[] data) throws IOException {
        Path classFile = Path.of(CLASS_FILE);
        Files.write(classFile, data);
        System.out.println("[+] Detected Java Class");
        System.out.println("[+] Class saved:" + classFile.toAbsolutePath());
        System.out.println("[+] CFR OUTPUT:" + CfrDecompiler.decompile(classFile).toAbsolutePath());
    }

    private void printPlainText(byte[] data) {
        System.out.println("\n===== OUTPUT =====\n");
        System.out.println(new String(data, StandardCharsets.UTF_8));
    }
}