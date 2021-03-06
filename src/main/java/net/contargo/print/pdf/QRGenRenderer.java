package net.contargo.print.pdf;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import net.glxn.qrgen.core.exception.QRGenerationException;
import net.glxn.qrgen.javase.QRCode;


/**
 * A QR-code renderer implementation using the QRGen library (https://github.com/kenglxn/QRGen).
 *
 * @author  Olle Törnström - toernstroem@synyx.de
 * @since  0.1
 */
public class QRGenRenderer implements QRCodeRenderer {

    @Override
    public byte[] render(String code, int size) throws RenderException {

        try {
            return QRCode.from(code)
                .withErrorCorrection(ErrorCorrectionLevel.H)
                .withSize(size, size)
                .stream()
                .toByteArray();
        } catch (QRGenerationException e) {
            throw new RenderException("QR-code render failed.", e);
        }
    }
}
