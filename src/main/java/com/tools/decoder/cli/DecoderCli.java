package com.tools.decoder.cli;

import com.tools.decoder.decode.DecodeService;
import com.tools.decoder.io.ResultWriter;

import java.util.Scanner;

public class DecoderCli {

    private static final String REQUEST_OUTPUT = "request_decrypted.txt";
    private static final String RESPONSE_OUTPUT = "response_decrypted.txt";

    private final Scanner scanner;
    private String pass;
    private String key;

    public DecoderCli(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        try {
            init();
            printMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            DecodeService service = new DecodeService(key);
            ResultWriter writer = new ResultWriter();

            switch (choice) {
                case 1 -> {
                    System.out.println("pass:");
                    writer.write(service.decodeRequest(scanner.nextLine()), REQUEST_OUTPUT);
                }
                case 2 -> {
                    System.out.println("Crypted Data:");
                    writer.write(service.decodeResponse(scanner.nextLine()), RESPONSE_OUTPUT);
                }
                default -> System.out.println("[-] Wrong chosen!");
            }
        } catch (Exception e) {
            System.err.println("[-] Decrypt failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void init() {
        System.out.print("pass: ");
        pass = scanner.nextLine();

        System.out.print("key: ");
        key = scanner.nextLine();
    }

    private void printMenu() {
        System.out.println("""
                
                ===============================
                 Godzilla ekp WebShell Decoder
                ===============================

                1. Decode Request Data
                2. Decode Response Data

                """);
        System.out.print("Choose: ");
    }
}