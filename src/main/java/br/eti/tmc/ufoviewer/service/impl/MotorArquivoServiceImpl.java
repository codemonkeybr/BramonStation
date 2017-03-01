package br.eti.tmc.ufoviewer.service.impl;

import br.eti.tmc.ufoviewer.entity.Captura;
import br.eti.tmc.ufoviewer.service.MotorArquivoService;
import br.eti.tmc.ufoviewer.entity.Analise;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by tessomc on 04/12/16.
 */
public class MotorArquivoServiceImpl implements MotorArquivoService {


    @Value("${caminho}")
    String caminho;

    @Override
    public List<Captura> varrePorCapturas(File caminho) {
        List<Captura> capturas = new ArrayList<Captura>();
        for (File arquivo : org.apache.commons.io.FileUtils.listFiles(caminho, new WildcardFileFilter("*.xml"), TrueFileFilter.INSTANCE)) {
            Captura cap = new Captura();
            cap.setCaminhoArquivo(arquivo.getAbsolutePath());
            cap.setData(extraiData(arquivo.getAbsolutePath().split(getOSBar())));
            cap.setAnalise(extraiAnalise(arquivo.getAbsolutePath()));
            cap.setNomeArquivo(arquivo.getName());

            capturas.add(cap);
        }
        return capturas;

    }

    private String getOSBar() {
        if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0){
            return "\\\\";
        } else
            return System.getProperty("file.separator");
    }

    @Override
    public List<Captura> filtrarCapturasPor(List<Captura> capturas, Date dataInicio, Date dataFim, Boolean apenasAnalisadas) {

        //bugfix para setar ate o fim do dia
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataFim);
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        cal.set(Calendar.MILLISECOND,0);

        dataFim = cal.getTime();

        List<Captura> resultado = new ArrayList<Captura>();
        for (Captura c : capturas)
            if ((c.getData().after(dataInicio) && c.getData().before(dataFim)))
                if (apenasAnalisadas == null)
                    resultado.add(c);
                else
                    if (apenasAnalisadas && c.getAnalise() != null )
                        resultado.add(c);
                    else if (!apenasAnalisadas)
                        resultado.add(c);

        return resultado;
    }


    private Analise extraiAnalise(String absolutePath) {

        //TODO para validar se existe analise feita, deve levar em consideracao o arquivo *A.XML criado pelo ufu analyser

        File xmlFile = new File(absolutePath.replace(".xml", "A.XML"));
        if (xmlFile.exists() && !xmlFile.isDirectory()) {

            Analise analise = new Analise();

            try {
                //File xmlFile = new File(f.getAbsolutePath().replace(".txt", "A.XML"));

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

                Document doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
                //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                //Dados gerais analise
                analise.setObservador(doc.getDocumentElement().getAttribute("observer"));

                //Dados EStacao
                analise.getEstacao().setCamera(doc.getDocumentElement().getAttribute("cam")); //lens  cap az ev rot lng lat
                analise.getEstacao().setCamera(doc.getDocumentElement().getAttribute("lens")); //lens  cap az ev rot lng lat
                analise.getEstacao().setCaptura(doc.getDocumentElement().getAttribute("cap")); //lens  cap az ev rot lng lat
                analise.getEstacao().setAzimute(doc.getDocumentElement().getAttribute("az")); //lens  cap az ev rot lng lat
                analise.getEstacao().setElevacao(doc.getDocumentElement().getAttribute("ev")); //lens  cap az ev rot lng lat
                analise.getEstacao().setRotacao(doc.getDocumentElement().getAttribute("rot")); //lens  cap az ev rot lng lat
                analise.getEstacao().setLongitude(doc.getDocumentElement().getAttribute("lng")); //lens  cap az ev rot lng lat
                analise.getEstacao().setLatitude(doc.getDocumentElement().getAttribute("lat")); //lens  cap az ev rot lng lat


                NodeList nList = doc.getElementsByTagName("ua2_object");

                //Dados do Objeto Analisado
                for (int temp = 0; temp < nList.getLength(); temp++) {

                    //System.out.println("\nCurrent Element :" + nNode.getNodeName());
                    if (nList.item(temp).getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nList.item(temp);
                        analise.getObjeto().setClasse(eElement.getAttribute("class"));
                        analise.getObjeto().setCdeg(new BigDecimal(eElement.getAttribute("cdeg")));
                        analise.getObjeto().setMagnitude(new BigDecimal(eElement.getAttribute("mag")));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.out.println(analise.toString());
            return analise;

        } else
            return null;

    }

    private Date extraiData(String[] split) {
        String dataStr = split[split.length - 1];

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String dateInString = "07/06/2013";
        try {

            return formatter.parse(dataStr.substring(1, 16));//M20161010_003237

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getCaminho() {
        return caminho;
    }
}
