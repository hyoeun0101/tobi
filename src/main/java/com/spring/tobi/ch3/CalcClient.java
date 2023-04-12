package com.spring.tobi.ch3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CalcClient {
    private final CalcTemplate calcTemplate;

    public Integer calcSum1(String filepath) throws FileNotFoundException, IOException{
        // 콜백 생성
        BufferedReaderCallback sumCallback = new BufferedReaderCallback() {
                @Override
                public Integer calculateWithReader(BufferedReader br) throws IOException {
                    int sum = 0;
                    String line = null;
                    while((line = br.readLine()) != null) {
                        sum += Integer.valueOf(line);
                    }
                    return sum;
                }
            };
        // 템플릿 호출.
        return calcTemplate.fileReadTemplate(filepath, sumCallback);
    }
    
    public Integer calcMultiply1(String filepath) {
        BufferedReaderCallback multiplyCallback = new BufferedReaderCallback() {
            @Override
            public Integer calculateWithReader(BufferedReader br) throws IOException {
                Integer result = 1;
                String line = null;
                while((line = br.readLine()) != null) {
                    result *= Integer.valueOf(line);
                }
                return result;
            }
        };
        return calcTemplate.fileReadTemplate(filepath, multiplyCallback);
    }



    public Integer calcSum2(String filepath) throws FileNotFoundException, IOException{
        // 콜백 생성
        LineCallback<Integer> sumCallback = new LineCallback<Integer>() {

            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value + Integer.valueOf(line);

            }
            
        };
        // 템플릿 호출.
        return calcTemplate.lineReadTemplate(filepath, sumCallback, 0);
    }

    public Integer calcMultiply2(String filepath) throws FileNotFoundException, IOException{
        // 콜백 생성
        LineCallback<Integer> multiplyCallback = new LineCallback<Integer>() {

            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value * Integer.valueOf(line);
            }
            
        };
        // 템플릿 호출.
        return calcTemplate.lineReadTemplate(filepath, multiplyCallback, 1);
    }

    public String concatenate (String filepath) throws IOException {
        LineCallback<String> concatenateCallback = new LineCallback<String>() {
            @Override
            public String doSomethingWithLine(String line, String value) {
                return value + line;
            }
        };
        return calcTemplate.lineReadTemplate(filepath, concatenateCallback, "");
    }

    

}
