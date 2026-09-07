package com.tools.decoder;

import com.tools.decoder.cli.DecoderCli;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            new DecoderCli(scanner).run();
        }
    }
}