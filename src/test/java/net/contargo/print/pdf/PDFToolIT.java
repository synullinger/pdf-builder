package net.contargo.print.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.net.URISyntaxException;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.Map;


/**
 * @author  Olle Törnström - toernstroem@synyx.de
 */
public class PDFToolIT {

    private static final Path RESOURCES = FileSystems.getDefault().getPath("src/test/resources");

    @Test
    public void ensureReplacesTextInPDF() throws URISyntaxException, IOException {

        Path source = RESOURCES.resolve("foo.pdf");
        Assert.assertTrue("Missing " + source, source.toFile().exists());

        PDFTextStripper textStripper = new PDFTextStripper();

        PDDocument sourcePdDocument = PDDocument.load(source.toFile());
        String textOnlyBefore = textStripper.getText(sourcePdDocument);

        Assert.assertTrue("Search value `foo` exists before", textOnlyBefore.contains("foo"));
        Assert.assertFalse("Replace value `bar` present before", textOnlyBefore.contains("bar"));

        Map<String, String> texts = new HashMap<>();
        texts.put("foo", "bar");

        RESOURCES.resolve("foo-replaced.pdf");

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PDFTool.fromTemplate(source).withReplacement("foo", "bar").generate().save(out);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        PDDocument targetPdDocument = PDDocument.load(in);
        String textOnlyAfter = textStripper.getText(targetPdDocument);

        Assert.assertFalse("Search value `foo` exists after", textOnlyAfter.contains("foo"));
        Assert.assertTrue("Replace value `bar` is missing", textOnlyAfter.contains("bar"));
    }
}