package com.polixis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class IP4Parser {

    public static void main(String[] args) throws IOException {
        Instant start = Instant.now();
        long count = uniqueCount("test.txt");
        Instant end = Instant.now();
        System.out.printf("Number of distinct IP4 addresses: %d%n", count);
        System.out.printf("Total time in milliseconds: %d%n%n",
                Duration.between(start, end).toMillis());
    }

    public static long uniqueCount(String path) throws IOException {
        return Files.lines(Paths.get(path)).distinct().count();
    }

    /*
    Another approach was to read the file by buffer chunks, 32MB for example.
    Since IP4 addresses are composed of four dotted-decimal parts of 0-255 value we will need 4 byte arrays of 256 size.
    We call those arrays as histogram. In each read chunk of file we'll lookup '\n', extract IP address, split into 4
    bytes and for each byte value we'll update histogram's array value of that index to 1. And if prior to updating of
    histogram arrays there was at least on zero value in some array, incrementing distinct addresses counter.
    Some pseudo code:

    byte[][] histogram = new byte[4][256];
        String extractedIp = "192.168.0.1"; // This we got from the buffer
        String[] parts = extractedIp.split(".");
        byte[] currentAddr = new byte[4];
        for(int i = 0; i < parts.length; ++i) {
            currentAddr[i] = Byte.parseByte(parts[i]);
        }

        boolean absent = false;
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            if(histogram[i][currentAddr[i]] == 0) {
                absent = true;
            }
            histogram[i][currentAddr[i]] = 1;
        }
        if(absent) {
            ++counter;
        }
     */
}