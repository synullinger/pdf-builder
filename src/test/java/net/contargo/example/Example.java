package net.contargo.example;

import net.contargo.print.pdf.BuildablePDF;
import net.contargo.print.pdf.PDFBuilder;
import net.contargo.print.pdf.QRSpec;
import net.contargo.print.pdf.RenderException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.FileSystems;
import java.nio.file.Path;


/**
 * This class is used only to create a simple example that can be copy-pasted into the Javadoc comment as a
 * `pre`-section.
 *
 * @author  Olle Törnström - toernstroem@synyx.de
 * @author  Slaven Travar - slaven.travar@pta.de
 */
public class Example {

    {
        String name = "", email = "", code = "", longText = "";
        int maximumCharactersPerLine = 0;

        Path docs = FileSystems.getDefault().getPath("documents");
        Path template = docs.resolve("template.pdf");
        Path result = docs.resolve("result.pdf");

        try {
            PDFBuilder.fromTemplate(template)
                .withReplacement("@name@", name)
                .withReplacement("@email@", email)
                .withMultiLineReplacement(longText, maximumCharactersPerLine, BuildablePDF.MultiLineTextFillMode.BOTTOM,
                        "@line1@", "@line2@", "@line3@")
                .withQRCode(QRSpec.fromCode(code).withPosition(20, 50).withSize(145))
                .build()
                .save(result);
        } catch (IOException | RenderException e) {
            // Handle failure...
        }
    }

    {
        String name = "", email = "", code = "";

        ByteArrayOutputStream result = new ByteArrayOutputStream();

        try(InputStream template = this.getClass().getResourceAsStream("documents/template.pdf")) {
            PDFBuilder.fromTemplate(template)
                .withReplacement("@name@", name)
                .withReplacement("@email@", email)
                .withQRCode(QRSpec.fromCode(code).withPosition(20, 50).withSize(145))
                .build()
                .save(result);
        } catch (IOException | RenderException e) {
            // Handle failure...
        }
    }
}
