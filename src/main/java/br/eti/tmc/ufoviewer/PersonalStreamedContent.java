package br.eti.tmc.ufoviewer;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tesso on 08/02/2017.
 */
public class PersonalStreamedContent extends DefaultStreamedContent {

    /**
     * Copies the given Inputstream and closes it afterwards
     */
    public PersonalStreamedContent(FileInputStream stream, String contentType) {
        super(copyInputStream(stream), contentType);
    }

    public static InputStream copyInputStream(InputStream stream) {
        if (stream != null) {
            try {
                byte[] bytes = IOUtils.toByteArray(stream);
                stream.close();
                return new ByteArrayInputStream(bytes);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            System.out.println("inputStream was null");
        }
        return new ByteArrayInputStream(new byte[] {});
    }
}