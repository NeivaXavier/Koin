package Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFReport {

	public static void generatePDF(String scenarioName, String stepsWithStatus) {
		try {
			String fileName = scenarioName.replaceAll(" ", "_") + ".pdf";
			String filePath = "target/reports/" + fileName;

			// Criação do diretório se não existir
			File directory = new File("target/reports/");
			if (!directory.exists()) {
				directory.mkdirs();
			}

			// Configuração do PdfWriter
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();

			// Definição de fontes
			Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
			Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

			// Adição de conteúdo ao documento
			Paragraph title = new Paragraph("Documento de Evidência Koin", titleFont);
			document.add(title);

			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			Paragraph executionTime = new Paragraph("Hora da Execução: " + timeStamp, textFont);
			document.add(executionTime);

			Paragraph steps = new Paragraph(stepsWithStatus, textFont);
			document.add(steps);

			// Fechamento do documento
			document.close();
			System.out.println("PDF criado: " + filePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
