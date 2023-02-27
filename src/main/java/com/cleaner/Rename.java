package com.cleaner;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

class Rename {

    private File fromFolder;
    private String toFolder;

    private DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    public Rename(String from, String to) {
        this.fromFolder = new File(from);
        this.toFolder = to.endsWith("/")?to:to+"/";
    }

    public void rename(){
        File[] listOfFiles = fromFolder.listFiles();

        for (File file: listOfFiles) {
            if (file.getName().matches(".*.(xml|met|dita|ditamap)$")) renameAndCopy(file);
        }
    }

    private void renameAndCopy(File file) {
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".")+1);
        String newName = "";

        try {
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            switch (extension){
                case "met":
                    newName = doc.getDocumentElement().getAttribute("ishref");
                    break;
                default:
                    newName = doc.getDocumentElement().getAttribute("id");
                    break;
            }
            FileUtils.copyFile(file,new File(toFolder+newName+"."+extension));
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
