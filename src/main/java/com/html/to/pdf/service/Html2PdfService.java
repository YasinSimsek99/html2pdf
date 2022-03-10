package com.html.to.pdf.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface Html2PdfService {

    byte[] oneToOneConvert(MultipartFile multipartFile) throws IOException;

    byte[] manyToManyConvert(List<MultipartFile> htmlFiles) throws IOException;

    byte[] manyToOneConvert(List<MultipartFile> htmlFiles) throws IOException;

}
