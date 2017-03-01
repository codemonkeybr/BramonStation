package br.eti.tmc.ufoviewer.controller;

import br.eti.tmc.ufoviewer.PersonalStreamedContent;
import br.eti.tmc.ufoviewer.entity.Analise;
import br.eti.tmc.ufoviewer.entity.Captura;
import br.eti.tmc.ufoviewer.service.MotorArquivoService;
import br.eti.tmc.ufoviewer.util.JsfUtil;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Controller;
import sun.security.jgss.TokenTracker;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by tessomc on 20/10/16.
 */
@ManagedBean
@ViewScoped
@Controller
public class IndexController {

    @Inject
    private MotorArquivoService motorArquivoService;


    List<Analise> listAnalises;
    List<Captura> capturas;

    List<Captura> capturasFiltradas;

    Date dataInicio;
    Date dataFim;

    Integer tipoFiltro;

    Captura captura;

    Boolean apenasAnalisadas;
    long espacoLivre;
    long espacoTotal;


    String caminho; //= "F:\\bramon\\!data"; //F:\bramon\!data  /Volumes/SAMSUNG/!data

    StreamedContent videoCaptura;


    @PostConstruct
    private void init() {


        capturas = new ArrayList<Captura>();
        capturasFiltradas = new ArrayList<Captura>();

        try {

            caminho = motorArquivoService.getCaminho();

            if (caminho == null || caminho.isEmpty())
                return;

            atualizaEspacoDisco();

            System.out.println("Caminho: " + caminho);
            capturas = motorArquivoService.varrePorCapturas(new File(caminho));


            System.out.println(capturas.size());

            if (capturas.size() > 0) {
                dataInicio = capturas.get(0).getData();
                dataFim = capturas.get(capturas.size() - 1).getData();
            }

            //Carrega por padrao as capturas do mes.
            tipoFiltro = 2;
            ajustaDatas();
            atualizarCapturas();
            //capturasFiltradas = capturas;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void atualizaEspacoDisco() {
        espacoLivre = new File(caminho).getUsableSpace();
        espacoTotal = new File(caminho).getTotalSpace();
    }

    public void inicializa() {
        init();
    }

    public void removerSelecionadas() {
        try {
            List<Captura> toRemove = new ArrayList<>();
            for (Captura cap : capturasFiltradas) {
                if (cap.isRemover()) {
                    System.out.println(cap.getCaminhoArquivo());
                    if (apagaArquivos(cap.getCaminhoArquivo()))
                        toRemove.add(cap);

                }

            }
            for (Captura c : toRemove)
                removeListaCaptura(c);
            JsfUtil.addSuccessMessage("Foram removidas " + toRemove.size() + " capturas!");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Erro ao remover captura! \n" + e.getMessage());
            e.printStackTrace();
        }

        atualizaEspacoDisco();

    }

    private void removeListaCaptura(Captura cap) {
        for (int i = 0; i < capturasFiltradas.size(); i++) {
            if (capturasFiltradas.get(i).equals(cap))
                capturasFiltradas.remove(i);
        }
        for (int i = 0; i < capturas.size(); i++) {
            if (capturas.get(i).equals(cap))
                capturas.remove(i);
        }

    }

    private boolean apagaArquivos(String caminhoArquivo) throws Exception {

        String xmlFile = caminhoArquivo;
        String aviFile = caminhoArquivo.replace("xml", "avi");
        String bmpFile = caminhoArquivo.replace(".xml", "M.bmp");
        String jpgPfile = caminhoArquivo.replace(".xml", "P.jpg");
        String jpgTfile = caminhoArquivo.replace(".xml", "T.jpg");

        if (apagaArquivo(xmlFile) &&
                apagaArquivo(bmpFile) &&
                apagaArquivo(jpgPfile) &&
                apagaArquivo(aviFile) &&
                apagaArquivo(jpgTfile))
            return true;
        else
            return false;


    }

    private boolean apagaArquivo(String caminhoArquivo) throws Exception {

        File file = new File(caminhoArquivo); // TODO pode ser particularidade do mac/unix

        if (file.exists())
            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
                return true;
            } else {
                throw new IOException("Nao foi possivel apagar o arquivo: " + caminhoArquivo);
            }
        else
            System.out.println("Aquivo nao encontrado " + caminhoArquivo);

        return false;
    }

    public void ajustaDatas() throws Exception {

        Date dateStart;
        Date dateEnd;

        Calendar gc = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        switch (tipoFiltro) {
            case 1:
                gc.set(Calendar.MONTH, new Date().getMonth() - 1);
                gc.set(Calendar.DAY_OF_MONTH, 1);
                dateStart = gc.getTime();
                gc.add(Calendar.MONTH, 1);
                gc.add(Calendar.DAY_OF_MONTH, -1);
                dateEnd = gc.getTime();

                dataInicio = dateStart;
                dataFim = dateEnd;
                System.out.println("Calculated month start date : " + format.format(dateStart));
                System.out.println("Calculated month end date : " + format.format(dateEnd));
                break;
            case 2:

                gc.set(Calendar.MONTH, new Date().getMonth());
                gc.set(Calendar.DAY_OF_MONTH, 1);
                dateStart = gc.getTime();
                gc.add(Calendar.MONTH, 1);
                gc.add(Calendar.DAY_OF_MONTH, -1);
                dateEnd = gc.getTime();

                dataInicio = dateStart;
                dataFim = dateEnd;
                System.out.println("Calculated month start date : " + format.format(dateStart));
                System.out.println("Calculated month end date : " + format.format(dateEnd));
                break;
            case 3:
                gc = Calendar.getInstance();
                gc.add(Calendar.DATE, -1);
                dateStart = gc.getTime();
                dateEnd = dateStart;

                dataInicio = dateStart;
                dataFim = dateEnd;
                System.out.println("Calculated month start date : " + format.format(dateStart));
                System.out.println("Calculated month end date : " + format.format(dateEnd));
                break;
            case 4:
                gc = Calendar.getInstance();
                dateStart = gc.getTime();
                dateEnd = dateStart;

                dataInicio = dateStart;
                dataFim = dateEnd;
                System.out.println("Calculated month start date : " + format.format(dateStart));
                System.out.println("Calculated month end date : " + format.format(dateEnd));
                break;
            case 5:
                if (capturas.size() > 0) {
                    dataInicio = capturas.get(0).getData();
                    dataFim = capturas.get(capturas.size() - 1).getData();
                }
        }

    }

    public void selecionaCaptura(Captura cap, String tipo) throws Exception {
        this.captura = cap;
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipo.equals("IMAGEM"))
            context.execute("PF('dlg1').show();");
        else if (tipo.equals("VIDEO"))
            context.execute("PF('dlg2').show();");

    }

    public void analisarRemocao() {
        for (int i =0; i < capturasFiltradas.size(); i++) {
            if (capturasFiltradas.get(i).getAnalise() == null){
                int result = analisarImagem(capturasFiltradas.get(i).getCaminhoThumbnail());
                if(result != 0 && result > 10)
                    capturasFiltradas.get(i).setRemover(true);
            }
        }
    }

    private int analisarImagem(String caminhoThumbnail) {
        try {

            File file = new File(caminhoThumbnail);
            BufferedImage image = ImageIO.read(file);
            int ocorrencias = 0;


            for (int x = 0; x < image.getWidth(); x++)
                for (int y = 0; y < image.getHeight(); y++) {
                    int clr = image.getRGB(x, y);
                    int red = (clr & 0x00ff0000) >> 16;
                    int green = (clr & 0x0000ff00) >> 8;
                    int blue = clr & 0x000000ff;

                    String hex = String.format("#%02x%02x%02x", red, green, blue);

                    if (hex.contains("ffff"))
                        ocorrencias = ocorrencias + 1;
                }
            return ((100 * ocorrencias) / (image.getWidth() * image.getHeight()));

                } catch(Exception e){
                    JsfUtil.addErrorMessage("Erro ao analisar imagem: " + e.getMessage());
                    return 0;
                }
            }

        public void analisaImagem () {
            try {
                File file = new File(captura.getCaminhoThumbnail());
                BufferedImage image = ImageIO.read(file);
                Map<String, Integer> color2counter = new HashMap<String, Integer>();

                int ocorrencias = 0;


                for (int x = 0; x < image.getWidth(); x++) {
                    for (int y = 0; y < image.getHeight(); y++) {
                        int clr = image.getRGB(x, y);
                        int red = (clr & 0x00ff0000) >> 16;
                        int green = (clr & 0x0000ff00) >> 8;
                        int blue = clr & 0x000000ff;

                        String hex = String.format("#%02x%02x%02x", red, green, blue);

                        if (hex.contains("ffff"))
                            ocorrencias = ocorrencias + 1;


                    }
                }

                System.out.println((100 * ocorrencias) / (image.getWidth() * image.getHeight()) + "% ffff");

            } catch (Exception e) {
                JsfUtil.addErrorMessage("Erro ao analisar imagem: " + e.getMessage());
            }
        }

        public void atualizarCapturas () throws Exception {
            System.out.println("Data Inicio:" + dataInicio + " Data Fim:" + dataFim);

            capturasFiltradas = motorArquivoService.filtrarCapturasPor(capturas, dataInicio, dataFim, apenasAnalisadas);

            JsfUtil.addSuccessMessage("Foram carregadas " + capturasFiltradas.size() + " capturas.");

        }

        public StreamedContent carregaImagem (String param) throws Exception {
            if (param != null && !param.isEmpty())
                return new PersonalStreamedContent(new FileInputStream(new File(param)), "image/jpg");
            return null;
        }

        public StreamedContent carregaVideo (String param) throws Exception {
            if (param != null && !param.isEmpty())
                return new PersonalStreamedContent(new FileInputStream(new File(param.replace("xml", "avi"))), "video/avi");
            return null;
        }
    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }


        public int getAnalisadas () {
            int i = 0;
            for (Captura c : capturasFiltradas)
                if (c.getAnalise() != null)
                    i++;

            return i;
        }

        public List<Captura> getCapturasFiltradas () {
            return capturasFiltradas;
        }

        public void setCapturasFiltradas (List < Captura > capturasFiltradas) {
            this.capturasFiltradas = capturasFiltradas;
        }

        public Date getDataInicio () {
            return dataInicio;
        }

        public void setDataInicio (Date dataInicio){
            this.dataInicio = dataInicio;
        }

        public Date getDataFim () {
            return dataFim;
        }

        public void setDataFim (Date dataFim){
            this.dataFim = dataFim;
        }

        public Integer getTipoFiltro () {
            return tipoFiltro;
        }

        public void setTipoFiltro (Integer tipoFiltro){
            this.tipoFiltro = tipoFiltro;
        }

        public Captura getCaptura () {
            return captura;
        }

        public void setCaptura (Captura captura){
            this.captura = captura;
        }

        public Boolean getApenasAnalisadas () {
            return apenasAnalisadas;
        }

        public void setApenasAnalisadas (Boolean apenasAnalisadas){
            this.apenasAnalisadas = apenasAnalisadas;
        }

        public String getCaminho () {
            return caminho;
        }

        public void setCaminho (String caminho){
            this.caminho = caminho;
        }

        public StreamedContent getVideoCaptura () {
            try {
                if (captura != null)
                    return new PersonalStreamedContent(new FileInputStream(new File(captura.getCaminhoArquivo().replace("xml", "avi"))), "video/avi");
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Erro ao Carregar Arquivo: " + captura.getCaminhoArquivo());
            }
            return null;
        }

        public String getEspacoLivrePerc(){
            return  Long.toString(((espacoLivre * 100) / espacoTotal));
        }

        public String getEspacoLivre(){
            return humanReadableByteCount(espacoLivre, true);
        }

    }
