import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import org.jbarcode.JBarcode;
import org.jbarcode.JBarcodeFactory;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.util.ImageUtil;

import com.onbarcode.barcode.AbstractBarcode;
import com.onbarcode.barcode.BarcodeFactory;
import com.onbarcode.barcode.IBarcode;
import com.onbarcode.barcode.QRCode;


public class TestJbarCode {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String origin = "?name=印勇&mobile=15151877621";
		String base32 = Base32.encode(origin.getBytes("utf-8"));
		System.out.println("base32 = " + base32);
		String decoded = new String(Base32.decode(base32), "utf-8");
		System.out.println("base32 decoded = " + decoded);
		
		QRCode barcode = new QRCode();
		
		/*
		   QRCode Valid data char set:
		        numeric data (digits 0 - 9);
		        alphanumeric data (digits 0 - 9; upper case letters A -Z; nine other characters: space, $ % * + - . / : );
		        byte data (default: ISO/IEC 8859-1);
		        Kanji characters
		*/
		barcode.setData(base32);
		barcode.setDataMode(QRCode.M_AUTO);
		barcode.setVersion(10);
		barcode.setEcl(QRCode.ECL_M);
		
		//  Set the processTilde property to true, if you want use the tilde character "~" 
		//  to specify special characters in the input data. Default is false.
		//
		//  1) 1-byte character: ~0dd/~1dd/~2dd (character value from 000 ~ 255); ASCII character '~' is presented by ~126
		//         Strings from "~256" to "~299" are unused
		//       modified to FS, GS, RS and US respectively.
		//  2) 2-byte character (Unicode): ~6ddddd (character value from 00000 ~ 65535)
		//         Strings from "~665536" to "~699999" are unused
		//  3) for GS1 AI Code: 
		//         ~ai2: AI with 2 digits
		//         ~ai3: AI with 3 digits
		//         ~ai4: AI with 4 digits
		//         ~ai5: AI with 5 digits
		//         ~ai6: AI with 6 digits
		//         ~ai7: AI with 7 digits
		//  4) ECI: ~7dddddd (valid value of dddddd from 000000 to 999999)
		//  5) SJIS: from ~9ddddd (Shift JIS 0x8140 ~ 0x9FFC and 0xE040 ~ 0xEBBF)
		barcode.setProcessTilde(false);
		
		// unit of measure for X, Y, LeftMargin, RightMargin, TopMargin, BottomMargin
		barcode.setUom(IBarcode.UOM_PIXEL);
		// barcode module width in pixel
		barcode.setX(3f);
		
		barcode.setLeftMargin(100f);
		barcode.setRightMargin(0f);
		barcode.setTopMargin(0f);
		barcode.setBottomMargin(0f);
		// barcode image resolution in dpi
		barcode.setResolution(72);
		
		/*
		  to save into gif file, file name end with .gif
		  to save into jpeg file, file name end with .jpg
		  to save into png file, file name end with .png
		  to save into eps file, call method drawBarcode2EPS and file name end with .eps
		*/
		barcode.drawBarcode(".\\qrcode.png");
	}
	static void saveToJPEG(BufferedImage paramBufferedImage, String paramString) {  
        saveToFile(paramBufferedImage, paramString, "jpeg");  
    }  

    static void saveToFile(BufferedImage paramBufferedImage, String paramString1, String paramString2) {  
        try {  
            FileOutputStream localFileOutputStream = new FileOutputStream("./" + paramString1);  
            ImageUtil.encodeAndWrite(paramBufferedImage, paramString2, localFileOutputStream, 96, 96);  
            localFileOutputStream.close();  
        }  
        catch (Exception localException) {  
            localException.printStackTrace();  
        }  
    } 
}
