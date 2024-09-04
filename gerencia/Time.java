package gerencia;

public class Time {
    private String nome;
    private String gritoDeGuerra;
    private int pontosTotais;
    private static final int MIN_PONTOS = 0;

    private int anoFundacao;
    private int pontos;
    private int quantFlip;
    private int quantBlot;
    private int quantAdvrunghs;
    private int vitorias;


    public Time(String nome, String gritoDeGuerra, int anoFundacao) {
        this.nome = nome;
        this.gritoDeGuerra = gritoDeGuerra;
        this.anoFundacao = anoFundacao;
        this.pontos = 50;
        this.pontosTotais = 50;
        this.quantFlip = 0;
        this.quantBlot = 0;
        this.quantAdvrunghs = 0;
        this.vitorias = 0;
    }

    public String getNome() {
        return nome;
    }

    public String getGritoDeGuerra() {
        return gritoDeGuerra;
    }

    public int getAnoFundacao() {
        return anoFundacao;
    }

    public int getQuantAdvrunghs() {
        return quantAdvrunghs;
    }

    public int getQuantBlot() {
        return quantBlot;
    }

    public int getQuantFlip() {
        return quantFlip;
    }

    public int getPontosTotais() {
        return pontosTotais;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public void addPontos(int pontos) {
        this.pontosTotais += pontos;
    }

    public void addBlot() {
        this.quantBlot++;
    }

    public void addFlip() {
        this.quantFlip++;
    }

    public void addAdvrunghs() {
        this.quantAdvrunghs++;
        this.pontosTotais = Math.max(this.pontosTotais - 10, MIN_PONTOS);
    }

}