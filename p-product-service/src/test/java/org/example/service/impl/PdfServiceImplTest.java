package org.example.service.impl;

import org.example.service.IPdfService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PdfServiceImplTest {
    @Autowired
    IPdfService pdfService;

    @Test
    public void test() throws IOException {

    }
}