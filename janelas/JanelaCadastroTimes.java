package janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gerencia.*;
import gerencia.Time;

public class JanelaCadastroTimes extends JFrame implements ActionListener {
    private JTextField campoNome;
    private JTextField campoGritoDeGuerra;
    private JTextField campoAnoFundacao;
    private JButton cadastrar;
    private JButton listar;
    private JButton preencherCadastro;
    private JButton limpar;
    private JButton voltar;
    private JTextArea area;
    private CadastroTimes cadastro;

    public JanelaCadastroTimes(CadastroTimes cadastro) {
        super("Cadastro de times");
        this.cadastro = cadastro;
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void inicializarComponentes() {
        campoNome = new JTextField(30);
        campoGritoDeGuerra = new JTextField(30);
        campoAnoFundacao = new JTextField(30);

        cadastrar = new JButton("Cadastrar time");
        limpar = new JButton("Limpar");
        listar = new JButton("Listar times");
        voltar = new JButton("Voltar");
        preencherCadastro = new JButton("Preencher cadastro automaticamente!");

        area = new JTextArea(15, 30);
        area.setEditable(false);
    }

    private void configurarLayout() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Nome:"));
        inputPanel.add(campoNome);
        inputPanel.add(new JLabel("Grito de guerra:"));
        inputPanel.add(campoGritoDeGuerra);
        inputPanel.add(new JLabel("Ano de fundação:"));
        inputPanel.add(campoAnoFundacao);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.add(cadastrar);
        buttonPanel.add(limpar);
        buttonPanel.add(listar);
        buttonPanel.add(voltar);
        buttonPanel.add(preencherCadastro);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(area), BorderLayout.SOUTH);

        this.add(mainPanel);
    }

    private void configurarEventos() {
        cadastrar.addActionListener(this);
        limpar.addActionListener(this);
        listar.addActionListener(this);
        voltar.addActionListener(this);
        preencherCadastro.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cadastrar) {
            cadastrarTime();
        } else if (e.getSource() == listar) {
            area.setText(listarTimes());
        } else if (e.getSource() == limpar) {
            limparCampos();
        } else if (e.getSource() == voltar) {
            verificarEVoltar();
        }
    }

    private void cadastrarTime() {
        try {
            String nome = campoNome.getText();
            if (nome.isEmpty()) {
                area.setText("O nome do time não pode estar vazio.");
            } else {
                if (cadastro.getQuantidadeAtual() >= 16) {
                    area.setText("Não é possível cadastrar mais de 16 times.");
                } else {
                    String gritoDeGuerra = campoGritoDeGuerra.getText();
                    int anoFundacao = Integer.parseInt(campoAnoFundacao.getText());
                    Time time = new Time(nome, gritoDeGuerra, anoFundacao);
                    if (cadastro.adicionarTime(time)) {
                        area.setText("Time cadastrado.");
                    } else {
                        area.setText("Time com esse nome já existe.");
                    }
                }
            }
        } catch (NumberFormatException ex) {
            area.setText("Erro ao cadastrar: Os dados são inválidos.");
        }
    }
    private void limparCampos() {
        campoNome.setText("");
        campoGritoDeGuerra.setText("");
        campoAnoFundacao.setText("");
    }

    private String listarTimes() {
        StringBuilder descricao = new StringBuilder();
        if (cadastro.getQuantidadeAtual() == 0) {
            return "Nenhum time foi cadastrado!";
        }
        Time[] times = cadastro.clonarTimes();
        for (int i = 0; i < times.length; i++) {
            Time time = times[i];
            descricao.append("Time n°").append(i + 1)
                    .append("\nNome: ").append(time.getNome())
                    .append("\nGrito de Guerra: ").append(time.getGritoDeGuerra())
                    .append("\nAno de Fundação: ").append(time.getAnoFundacao())
                    .append("\n\n");
        }
        return descricao.toString();
    }
    private void verificarEVoltar() {
        int quantidadeAtual = cadastro.getQuantidadeAtual();
        if (quantidadeAtual >= 8 && quantidadeAtual % 2 == 0) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Para voltar, deve haver mais de 8 times cadastrados e o número de times deve ser par.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}


