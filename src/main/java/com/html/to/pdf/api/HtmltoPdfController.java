package com.html.to.pdf.api;

import com.html.to.pdf.service.Html2PdfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class HtmltoPdfController {

    private final Html2PdfService html2PdfService;

    public HtmltoPdfController(Html2PdfService html2PdfService) {
        this.html2PdfService = html2PdfService;
    }

    @PostMapping(value = "/multi", produces = "application/zip")
    @ResponseBody
    public ResponseEntity<byte[]> uploadMultiFile(@RequestParam("html") List<MultipartFile> htmlFiles) throws IOException {
        return new ResponseEntity<>(html2PdfService.manyToManyConvert(htmlFiles), HttpStatus.OK);
    }

    @PostMapping(value = "/single", produces = "application/zip")
    @ResponseBody
    public ResponseEntity<byte[]> uploadFile(@RequestParam("html") MultipartFile htmlFile) throws IOException {
        return new ResponseEntity<>(html2PdfService.oneToOneConvert(htmlFile), HttpStatus.OK);
    }


    @PostMapping(value = "/merge", produces = "application/zip")
    @ResponseBody
    public ResponseEntity<byte[]> mergeFiles(@RequestParam("html") List<MultipartFile> htmlFile) throws IOException {
        return new ResponseEntity<>(html2PdfService.manyToOneConvert(htmlFile), HttpStatus.OK);
    }
}
