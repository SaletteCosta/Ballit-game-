package janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gerencia.CadastroTimes;
import gerencia.Time;

public class JanelaPunicao extends JFrame implements ActionListener {
    private CadastroTimes cadastro;
    private JComboBox<String> comboBoxTimes;
    private JButton punir;
    private JButton voltar;
    private JTextArea area;
    private JLabel informarAviso;

    public JanelaPunicao(CadastroTimes cadastro) {
        super("Punir um time com Advrungh");
        this.cadastro = cadastro;

        inicializarComponentes();
        configurarLayout();
        configurarEventos();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    private void inicializarComponentes() {
        comboBoxTimes = new JComboBox<>();
        for (Time time : cadastro.clonarTimes()) {
            comboBoxTimes.addItem(time.getNome());
        }
        punir = new JButton("Aplicar punição");
        voltar = new JButton("Voltar");
        area = new JTextArea(10, 30);
        area.setEditable(false);

        informarAviso = new JLabel("Punições feitas após o encerramento do campeonato não terão efeito.");
        informarAviso.setHorizontalAlignment(JLabel.CENTER);
    }
    private void configurarLayout() {
        JPanel painelPunir = new JPanel();
        painelPunir.setLayout(new BorderLayout(10, 10));
        painelPunir.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboBoxPanel.add(new JLabel("Selecione o time:"));
        comboBoxPanel.add(comboBoxTimes);
        topPanel.add(informarAviso, BorderLayout.NORTH);
        topPanel.add(comboBoxPanel, BorderLayout.CENTER);
        painelPunir.add(topPanel, BorderLayout.NORTH);
        painelPunir.add(new JScrollPane(area), BorderLayout.CENTER);
        JPanel botoesPainel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoesPainel.add(punir);
        botoesPainel.add(voltar);
        painelPunir.add(botoesPainel, BorderLayout.SOUTH);
        this.add(painelPunir);
    }

    private void configurarEventos() {
        punir.addActionListener(this);
        voltar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == punir) {
            String nomeTime = (String) comboBoxTimes.getSelectedItem();
            Time time = null;
            for (Time t : cadastro.clonarTimes()) {
                if (t.getNome().equals(nomeTime)) {
                    time = t;
                    break;
                }
            }
            if (time != null) {
                time.addAdvrunghs();
                area.append("O time " + time.getNome() + " foi punido com Advrungh. Pontos atuais: " + time.getPontosTotais() + "\n");
            }
        } else if (e.getSource()==voltar){
            this.setVisible(false);
        }
    }
}
