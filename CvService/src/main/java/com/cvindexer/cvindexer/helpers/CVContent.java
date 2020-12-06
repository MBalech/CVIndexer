package com.cvindexer.cvindexer.helpers;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;


@Service
public class CVContent {

    /**
     * Permet de lire le contenu d'un fichier en fonction de son format
     * @param fileName string nom du fichier
     * @param type string type du fichier
     * @return contenu string contenu du ficher parser en string
     * @throws IOException
     */
    @PostMapping
    public String ReadFile(String fileName, String type) throws IOException {
        String contenu = "";
        if(type.equals("application/pdf")) contenu = readPdf(fileName); //Format pdf
        if(type.equals("application/msword")) System.out.println("Erreur");; // doc
        if(type.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) contenu = readDocx(fileName); //.docx

        contenu = parsed(contenu); //Permet d'enlever tous les sauts de lignes pour l'insertion en json
        return contenu;
    }

    /**
     * Permet d'enlever tous les sauts de lignes pour éviter les erreurs lors de l'indexiation
     * @param parsed string contenu du fichier non parsé
     * @return
     */
    private String parsed(String parsed){
        String contenu = "";
        for(int s=0; s < parsed.length(); s++){
            //Retire les sauts de ligne et tabulations
            if((int) parsed.charAt(s) != 9 && (int) parsed.charAt(s) != 10) {
                contenu += parsed.charAt(s);
            }
            else contenu += " ";
        }

       return  contenu;
    }

    /**
     * Permet de récupérer le contenu d'un fichier docx
     * @param fileName string nom du fichier
     * @return contenu string contenu du fichier
     * @throws IOException
     */
    private String readDocx(String fileName) throws IOException {
        //récupération fichier
        FileInputStream fps = new FileInputStream(fileName);

        XWPFDocument docu = new XWPFDocument(fps);
        //récupération de la liste de tous les paragraphes
        List<XWPFParagraph> data = docu.getParagraphs();

        String contenu = "";
        for(XWPFParagraph p : data) {
            contenu += p.getText(); //récupération de tous les paragraphes dans contenu
        }

        return contenu;
    }

    /**
     * Permet de récupérer le contenu d'un fichier pdf
     * @param fileName string nom du fichier
     * @return contenu string contenu du fichier
     * @throws IOException
     */
    private String readPdf(String fileName) throws IOException {
        String parsed = "";
        File f = new File(fileName);

        PDFParser parser = new PDFParser(new RandomAccessFile(f, "r"));
        parser.parse();

        //Récupère le document
        COSDocument cosDoc = parser.getDocument();

        //récupère le contenu pdf
        PDFTextStripper pdfStripper = new PDFTextStripper();
        PDDocument pdDoc = new PDDocument(cosDoc);
        //transforme le pdf en string
        parsed = pdfStripper.getText(pdDoc);

        cosDoc.close();
        return parsed;
    }
}
