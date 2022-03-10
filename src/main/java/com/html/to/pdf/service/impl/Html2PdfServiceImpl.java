package com.html.to.pdf.service.impl;

import com.html.to.pdf.service.Html2PdfService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import net.lingala.zip4j.ZipFile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class Html2PdfServiceImpl implements Html2PdfService {
    private static final String NEXT_PAGE = "<p style=\"page-break-after: always;\">&nbsp;</p>";
    private static final String PDF_EXTENTION = ".pdf";
    private static final String PDF_FOLDER = "pdf/";
    private static final String PDF = "pdf";
    private static final String ZIP_EXTENTION = ".zip";
    private static final String ZIP_FOLDER = "zip/";

    private static final String HTML_EXTENTION = ".html";


    @Override
    public byte[] oneToOneConvert(MultipartFile multipartFile) throws IOException {
        String content = new String(multipartFile.getBytes());
        String fileName = convertFileType(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        createPdf(content, PDF_FOLDER + fileName);

        File file = new File(PDF);
        String rarName = ZIP_FOLDER + UUID.randomUUID() + ZIP_EXTENTION;
        new ZipFile(rarName).addFiles(Arrays.asList(Objects.requireNonNull(file.listFiles())));

        byte[] fileContent = Files.readAllBytes(Paths.get(rarName));
        deleteOldFiles(Arrays.asList(Objects.requireNonNull(file.listFiles())));
        return fileContent;
    }

    @Override
    public byte[] manyToManyConvert(List<MultipartFile> htmlFiles) throws IOException {
        for (MultipartFile htmlFile : htmlFiles) {
            String content = new String(htmlFile.getBytes());
            String fileName = convertFileType(Objects.requireNonNull(htmlFile.getOriginalFilename()));
            createPdf(content, PDF_FOLDER + fileName);
        }

        File file = new File(PDF);
        String rarName = ZIP_FOLDER + UUID.randomUUID() + ZIP_EXTENTION;
        new ZipFile(rarName).addFiles(Arrays.asList(Objects.requireNonNull(file.listFiles())));

        byte[] fileContent = Files.readAllBytes(Paths.get(rarName));
        deleteOldFiles(Arrays.asList(Objects.requireNonNull(file.listFiles())));
        return fileContent;
    }

    @Override
    public byte[] manyToOneConvert(List<MultipartFile> htmlFiles) throws IOException {
        if (Objects.isNull(htmlFiles)) {
            return null;
        }
        StringBuilder content = new StringBuilder();
        content.append(new String(htmlFiles.get(0).getBytes()));
        for (int i = 1; i < htmlFiles.size(); i++) {
            content.append(NEXT_PAGE);
            content.append(new String(htmlFiles.get(i).getBytes()));
        }

        String fileName = convertFileType(Objects.requireNonNull(htmlFiles.get(0).getOriginalFilename()));
        createPdf(content.toString(), PDF_FOLDER + fileName);

        File file = new File(PDF);
        String rarName = ZIP_FOLDER + UUID.randomUUID() + ZIP_EXTENTION;
        new ZipFile(rarName).addFiles(Arrays.asList(Objects.requireNonNull(file.listFiles())));

        byte[] fileContent = Files.readAllBytes(Paths.get(rarName));
        deleteOldFiles(Arrays.asList(Objects.requireNonNull(file.listFiles())));
        return fileContent;
    }

    private String convertFileType(String fileName) {
        if (fileName.contains(HTML_EXTENTION)) {
            return fileName.substring(0, fileName.length() - 4) + PDF;
        }
        return fileName + PDF_EXTENTION;
    }

    private void createPdf(String src, String dest) throws IOException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setTagged();
        PageSize pageSize = PageSize.A3;

        pdf.setDefaultPageSize(pageSize);
        ConverterProperties properties = new ConverterProperties();
        MediaDeviceDescription mediaDeviceDescription
                = new MediaDeviceDescription(MediaType.SCREEN);
        mediaDeviceDescription.setWidth(pageSize.getWidth());
        properties.setCreateAcroForm(true);
        properties.setMediaDeviceDescription(mediaDeviceDescription);
        HtmlConverter.convertToPdf(src, pdf, properties);
    }


    private void deleteOldFiles(List<File> filesToRemove) {
        if (CollectionUtils.isEmpty(filesToRemove)) {
            return;
        }
        try {
            filesToRemove.forEach(File::delete);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
