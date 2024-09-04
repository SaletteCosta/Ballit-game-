package janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import gerencia.*;
import gerencia.Time;

public class JanelaEntrada extends JFrame implements ActionListener {
    private JButton cadastrarEquipes;
    private JButton iniciarCampeonato;
    private JButton finalizar;
    private JButton featureExtra;
    private JButton advrungh;
    private CadastroTimes cadastro;
    private JanelaIniciarCampeonato janelaIniciarCampeonato;

    public JanelaEntrada(CadastroTimes cadastro) {
        super("BALLIT CHAMPIONSHIP");
        this.cadastro = cadastro;
        setLookAndFeel();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(300, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inicializarComponentes() {
        cadastrarEquipes = new JButton("Cadastro de equipes");
        iniciarCampeonato = new JButton("Iniciar o campeonato");
        finalizar = new JButton("Finalizar");
        advrungh = new JButton("Punir um time com Advrungh");
        featureExtra = new JButton("Feature extra");
    }

    private void configurarLayout() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(cadastrarEquipes);
        panel.add(iniciarCampeonato);
        panel.add(advrungh);
        panel.add(featureExtra);
        panel.add(finalizar);
        this.add(panel);
    }

    private void configurarEventos() {
        cadastrarEquipes.addActionListener(this);
        iniciarCampeonato.addActionListener(this);
        finalizar.addActionListener(this);
        featureExtra.addActionListener(this);
        advrungh.addActionListener(this);
    }

    private void gerarArquivoTxt() {
        try {
            String arquivo = "dados_do_campeonato.txt";
            FileWriter writer = new FileWriter(arquivo);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write("Dados do campeonato\n");
            bufferedWriter.write("====================\n");
            bufferedWriter.write("BALLIT CHAMPIONSHIP\n" +
                    "Campeonato de um jogo esquisito :)\n\n");

            Time[] times = cadastro.clonarTimes();
            for (int i = 0; i < times.length; i++) {
                Time time = times[i];
                bufferedWriter.write("Time n°" + (i + 1) + "\n");
                bufferedWriter.write("Nome: " + time.getNome() + "\n");
                bufferedWriter.write("Grito de guerra: " + time.getGritoDeGuerra() + "\n");
                bufferedWriter.write("Ano de fundação: " + time.getAnoFundacao() + "\n\n");
            }

            bufferedWriter.close();
            JOptionPane.showMessageDialog(this, "Arquivo criado com sucesso: " + arquivo, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cadastrarEquipes) {
            new janelas.JanelaCadastroTimes(cadastro);
        } else if (e.getSource() == iniciarCampeonato) {
            if (janelaIniciarCampeonato == null) {
                janelaIniciarCampeonato = new JanelaIniciarCampeonato(cadastro);
            } else {
                janelaIniciarCampeonato.setVisible(true);
            }
        } else if (e.getSource() == advrungh) {
            new JanelaPunicao(cadastro);
        } else if (e.getSource() == featureExtra) {
            gerarArquivoTxt();
        } else if (e.getSource() == finalizar) {
            this.dispose();
        }
    }
}






