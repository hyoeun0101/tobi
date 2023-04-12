package com.spring.tobi.ch3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CalcTemplate {
    public Integer fileReadTemplate(String filePath, BufferedReaderCallback callback) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            return callback.calculateWithReader(br);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return null;
    }

    // 제네릭 타입으로 변환하기
    public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            T result = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                result = callback.doSomethingWithLine(line, result);
            }
            return result;
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return null;
    }
}
