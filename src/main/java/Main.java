import org.benf.cfr.reader.api.CfrDriver;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;


public class Main {


    private static final String AES_ALGORITHM =
            "AES/ECB/PKCS5Padding";


    private static final String OUTPUT_REQUEST =
            "request_decrypted.txt";


    private static final String OUTPUT_RESPONSE =
            "response_decrypted.txt";


    private static String pass;

    private static String key;



    public static void main(String[] args) {


        try (Scanner scanner = new Scanner(System.in)) {


            init(scanner);


            printMenu();


            int choice =
                    Integer.parseInt(scanner.nextLine());



            switch (choice) {


                case 1 ->
                        decryptRequest(scanner);


                case 2 ->
                        decryptResponse(scanner);


                default ->
                        System.out.println(
                                "[-] Wrong chosen!"
                        );

            }


        } catch (Exception e) {

            System.err.println(
                    "[-] Decrypt failed: "
                            + e.getMessage()
            );

            e.printStackTrace();

        }

    }





    private static void init(Scanner scanner) {


        System.out.print("pass: ");

        pass = scanner.nextLine();


        System.out.print("key: ");

        key = scanner.nextLine();

    }





    private static void printMenu() {


        System.out.println("""
                
                ===============================
                 Godzilla ekp WebShell Decoder
                ===============================

                1. Decode Request Data
                2. Decode Response Data

                """);

        System.out.print(
                "Choose: "
        );

    }

    private static void decryptRequest(
            Scanner scanner
    ) throws Exception {


        System.out.println(
                "pass:"
        );


        String input =
                scanner.nextLine();



        input =
                URLDecoder.decode(
                        input,
                        StandardCharsets.UTF_8
                );



        byte[] firstDecode =
                Base64.getDecoder()
                        .decode(input);



        byte[] secondDecode =
                Base64.getDecoder()
                        .decode(firstDecode);



        byte[] plaintext =
                decryptAES(
                        secondDecode
                );



        plaintext =
                ungzip(
                        plaintext
                );



        saveResult(
                plaintext,
                OUTPUT_REQUEST
        );

    }





    /**
     * Decode Response Data
     *
     * JSP:
     *
     * Base64(
     *      AES(result)
     * )
     *
     */
    private static void decryptResponse(
            Scanner scanner
    ) throws Exception {


        System.out.println(
                "Crypted Data:"
        );


        String input =
                scanner.nextLine();



        byte[] encrypted =
                Base64.getDecoder()
                        .decode(input);



        byte[] plaintext =
                decryptAES(
                        encrypted
                );



        plaintext =
                ungzip(
                        plaintext
                );



        saveResult(
                plaintext,
                OUTPUT_RESPONSE
        );

    }





    /**
     * AES Decrypt
     */
    private static byte[] decryptAES(
            byte[] data
    ) throws Exception {


        Cipher cipher =
                Cipher.getInstance(
                        AES_ALGORITHM
                );


        SecretKeySpec secKey =
                new SecretKeySpec(
                        key.getBytes(
                                StandardCharsets.UTF_8
                        ),
                        "AES"
                );



        cipher.init(
                Cipher.DECRYPT_MODE,
                secKey
        );



        return cipher.doFinal(data);

    }





    /**
     * GZIP
     */
    private static byte[] ungzip(
            byte[] data
    ) throws Exception {


        try (
                GZIPInputStream gzip =
                        new GZIPInputStream(
                                new ByteArrayInputStream(data)
                        );

                ByteArrayOutputStream output =
                        new ByteArrayOutputStream()
        ) {


            byte[] buffer =
                    new byte[4096];


            int length;


            while (
                    (length = gzip.read(buffer))
                            != -1
            ) {

                output.write(
                        buffer,
                        0,
                        length
                );

            }


            return output.toByteArray();

        }

    }





    /**
     * Save data
     */
    private static void saveResult(
            byte[] data,
            String filename
    ) throws Exception {


        Path file =
                Path.of(filename);



        Files.write(
                file,
                data
        );



        System.out.println(
                "[+] Save: "
                        +
                        file.toAbsolutePath()
        );



	if (isJavaClass(data)) {

	    Path classFile =
		    Path.of("payload.class");
	    Files.write(
		    classFile,
		    data
	    );

	    System.out.println(
		    "[+] Detected Java Class"
	    );


	    System.out.println(
		    "[+] Class saved:"
			    +
			    classFile.toAbsolutePath()
	    );


	    decompile(classFile);

        } else {


            System.out.println(
                    "\n===== OUTPUT =====\n"
            );


            System.out.println(
                    new String(
                            data,
                            StandardCharsets.UTF_8
                    )
            );

        }

    }

    private static boolean isJavaClass(
            byte[] data
    ) {


        return data.length >= 4
                &&
                data[0] == (byte) 0xca
                &&
                data[1] == (byte) 0xfe
                &&
                data[2] == (byte) 0xba
                &&
                data[3] == (byte) 0xbe;

    }





    /**
     * CFR desassemble
     */
    private static void decompile(
            Path classFile
    ) throws Exception {


        Map<String, String> options =
                new HashMap<>();


        options.put(
                "outputdir",
                "decompile"
        );



        CfrDriver driver =
                new CfrDriver.Builder()
                        .withOptions(options)
                        .build();



        driver.analyse(
                List.of(
                        classFile.toString()
                )
        );



        System.out.println(
                "[+] CFR OUTPUT:"
                        +
                        Path.of("decompile")
                                .toAbsolutePath()
        );

    }


}
