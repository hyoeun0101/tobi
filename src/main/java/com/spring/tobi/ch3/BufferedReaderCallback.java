package com.spring.tobi.ch3;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallback {
    Integer calculateWithReader(BufferedReader br) throws IOException;
}