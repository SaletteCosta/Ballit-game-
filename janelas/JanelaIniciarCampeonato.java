package janelas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;


import gerencia.*;
import gerencia.Time;

public class JanelaIniciarCampeonato extends JFrame implements ActionListener {
    private CadastroTimes cadastro;
    private JButton fechar;
    private JPanel painelPartidas;
    private JTextArea area;
    private int partidasConcluidas;
    private Map<JButton, Time[]> botoesPartidas;
    private ArrayList<Time> timesPerdedores;

    public JanelaIniciarCampeonato(CadastroTimes cadastro) {
        super("Início do campeonato");
        this.cadastro = cadastro;
        setLookAndFeel();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        if (permitirInicioCampeonato()) {
            gerarDuplas();
        }
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inicializarComponentes() {
        fechar = new JButton("Voltar");
        painelPartidas = new JPanel(new GridLayout(0, 1, 20, 20));
        area = new JTextArea(20, 40);
        area.setEditable(false);
        partidasConcluidas = 0;
        botoesPartidas = new HashMap<>();
        timesPerdedores = new ArrayList<>();
        this.setSize(600, 600);
    }

    private boolean permitirInicioCampeonato() {
        int quantidadeAtual = cadastro.getQuantidadeAtual();
        if (quantidadeAtual >= 8 && quantidadeAtual <= 16 && quantidadeAtual % 2 == 0) {
            return true;
        } else {
            if (quantidadeAtual < 8) {
                area.setText("Não é possível iniciar o campeonato com menos de 8 times cadastrados.");
            } else if (quantidadeAtual > 16) {
                area.setText("Não é possível iniciar o campeonato com mais de 16 times cadastrados.");
            } else {
                area.setText("Não é possível iniciar o campeonato com um número ímpar de times cadastrados.");
            }
            return false;
        }
    }

    private void gerarDuplas() {
        Time[] times = cadastro.clonarTimes();
        ArrayList<Time> listaTimes = new ArrayList<>(Arrays.asList(times));
        Collections.shuffle(listaTimes);
        painelPartidas.removeAll();
        StringBuilder descricaoDuplas = new StringBuilder("Duplas:\n");
        botoesPartidas.clear();
        for (int i = 0; i < listaTimes.size(); i += 2) {
            Time timeA = listaTimes.get(i);
            Time timeB = listaTimes.get(i + 1);
            String nomeDupla = "Dupla " + ((i / 2) + 1) + ": " + timeA.getNome() + " e " + timeB.getNome();
            descricaoDuplas.append(nomeDupla).append("\n");
            JButton botaoDupla = new JButton(nomeDupla);
            botaoDupla.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    janelaPartidaSelecionada(timeA, timeB, botaoDupla);
                }
            });
            painelPartidas.add(botaoDupla);
            botoesPartidas.put(botaoDupla, new Time[]{timeA, timeB});
        }
        area.setText(descricaoDuplas.toString());
        painelPartidas.revalidate();
        painelPartidas.repaint();
    }

    private void configurarLayout() {
        JPanel panel = new JPanel(new BorderLayout(40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(area);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel botoesPanel = new JPanel(new BorderLayout());
        botoesPanel.add(new JScrollPane(painelPartidas), BorderLayout.CENTER);
        botoesPanel.add(fechar, BorderLayout.SOUTH);

        panel.add(botoesPanel, BorderLayout.SOUTH);

        this.add(panel);
    }

    private void configurarEventos() {
        fechar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fechar) {
            this.setVisible(false);
        }
    }

    private void janelaPartidaSelecionada(Time timeA, Time timeB, JButton botaoDupla) {
        JFrame janelaDaPartida = new JFrame("Partida: " + timeA.getNome() + " v.s " + timeB.getNome());
        janelaDaPartida.setLayout(new BorderLayout(10, 10));

        JTextArea painel = new JTextArea();
        painel.setText(timeA.getNome() + " v.s " + timeB.getNome() + "\n");
        painel.setEditable(false);
        Font font = new Font("Serif", Font.PLAIN, 25);
        painel.setFont(font);

        JLabel pontosLabelA = new JLabel("Pontos " + timeA.getNome() + ": " + timeA.getPontos());
        JLabel pontosLabelB = new JLabel("Pontos " + timeB.getNome() + ": " + timeB.getPontos());

        JButton blotTimeA = new JButton("Blot para o time " + timeA.getNome());
        JButton blotTimeB = new JButton("Blot para o time " + timeB.getNome());
        JButton plifTimeA = new JButton("Plif para o time " + timeA.getNome());
        JButton plifTimeB = new JButton("Plif para o time " + timeB.getNome());
        JButton encerrarPartida = new JButton("Encerrar a partida");

        ActionListener acaoBotao = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == blotTimeA) {
                    timeA.setPontos(timeA.getPontos() + 5);
                    pontosLabelA.setText("Pontos " + timeA.getNome() + ": " + timeA.getPontos());
                    timeA.addBlot();
                } else if (e.getSource() == blotTimeB) {
                    timeB.setPontos(timeB.getPontos() + 5);
                    pontosLabelB.setText("Pontos " + timeB.getNome() + ": " + timeB.getPontos());
                    timeB.addBlot();
                } else if (e.getSource() == plifTimeA) {
                    timeA.setPontos(timeA.getPontos() + 1);
                    pontosLabelA.setText("Pontos " + timeA.getNome() + ": " + timeA.getPontos());
                    timeA.addFlip();
                } else if (e.getSource() == plifTimeB) {
                    timeB.setPontos(timeB.getPontos() + 1);
                    pontosLabelB.setText("Pontos " + timeB.getNome() + ": " + timeB.getPontos());
                    timeB.addFlip();
                }
            }
        };

        blotTimeA.addActionListener(acaoBotao);
        blotTimeB.addActionListener(acaoBotao);
        plifTimeA.addActionListener(acaoBotao);
        plifTimeB.addActionListener(acaoBotao);

        encerrarPartida.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeA.getPontos() > timeB.getPontos()) {
                    area.append("O time " + timeA.getNome() + " venceu.\n");
                    timesPerdedores.add(timeB);
                    timeA.addPontos(timeA.getPontos());
                } else if (timeB.getPontos() > timeA.getPontos()) {
                    area.append("O time " + timeB.getNome() + " venceu.\n");
                    timesPerdedores.add(timeA);
                    timeB.addPontos(timeB.getPontos());
                } else {
                    area.append("A partida terminou empatada. Será necessário um desempate.\n");
                    desempate(timeA, timeB);
                }
                timeA.setPontos(50);
                timeB.setPontos(50);
                botaoDupla.setEnabled(false);

                partidasConcluidas++;
                int totalPartidas = painelPartidas.getComponentCount();
                if (partidasConcluidas == totalPartidas) {
                    timesVencedoresProximaEtapa();
                }
                janelaDaPartida.dispose();
            }
        });

        JPanel panelPartida = new JPanel();
        panelPartida.setLayout(new GridLayout(0, 1, 20, 20));
        panelPartida.add(painel);
        panelPartida.add(pontosLabelA);
        panelPartida.add(pontosLabelB);
        panelPartida.add(blotTimeA);
        panelPartida.add(blotTimeB);
        panelPartida.add(plifTimeA);
        panelPartida.add(plifTimeB);
        panelPartida.add(encerrarPartida);

        janelaDaPartida.add(panelPartida, BorderLayout.CENTER);

        janelaDaPartida.setSize(600, 600);
        janelaDaPartida.setLocationRelativeTo(null);
        janelaDaPartida.setVisible(true);
    }
    private void timesVencedoresProximaEtapa() {
        Time[] times = cadastro.clonarTimes();
        ArrayList<Time> listaTimes = new ArrayList<>(Arrays.asList(times));
        listaTimes.removeAll(timesPerdedores);
        if (listaTimes.size() == 1) {
            Time campeao = listaTimes.get(0);
            area.append( "O time campeão é: " + campeao.getNome());
            exibirTabelaFinal();
            return;
        }

        gerarDuplasProximaFase(listaTimes);
    }
    private void gerarDuplasProximaFase(ArrayList<Time> listaTimes) {
        Collections.sort(listaTimes, new Comparator<Time>() {
            @Override
            public int compare(Time t1, Time t2) {
                return Integer.compare(t2.getPontos(), t1.getPontos());
            }
        });
        if (listaTimes.size() % 2 != 0) {
            listaTimes.remove(listaTimes.size() - 1);
            JOptionPane.showMessageDialog(
                    null,
                    "Não é possível dividir um número ímpar de times em duplas. O time com menor pontuação será desclassificado.",
                    "Atenção!",
                    JOptionPane.WARNING_MESSAGE
            );
        }
        Collections.shuffle(listaTimes);
        painelPartidas.removeAll();
        StringBuilder descricaoDuplas = new StringBuilder("Duplas da próxima fase:\n");
        botoesPartidas.clear();
        for (int i = 0; i < listaTimes.size(); i += 2) {
            Time timeA = listaTimes.get(i);
            Time timeB = listaTimes.get(i + 1);
            String nomeDupla = "Dupla " + ((i / 2) + 1) + ": " + timeA.getNome() + " e " + timeB.getNome();
            descricaoDuplas.append(nomeDupla).append("\n");
            JButton botaoDupla = new JButton(nomeDupla);
            botaoDupla.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    janelaPartidaSelecionada(timeA, timeB, botaoDupla);
                }
            });
            painelPartidas.add(botaoDupla);
            botoesPartidas.put(botaoDupla, new Time[]{timeA, timeB});
        }
        area.setText(descricaoDuplas.toString());
        painelPartidas.revalidate();
        painelPartidas.repaint();
        partidasConcluidas = 0;
    }
    public void exibirTabelaFinal() {
        Time[] todosTimes = cadastro.clonarTimes();
        Time[] tabelaCompleta = new Time[todosTimes.length];

        for (int i = 0; i < todosTimes.length; i++) {
            tabelaCompleta[i] = todosTimes[i];
        }
        Arrays.sort(tabelaCompleta, new Comparator<Time>() {
            @Override
            public int compare(Time tA, Time tB) {
                int pontosTotaisT1 = tA.getPontosTotais() - (tA.getQuantAdvrunghs() * 10);
                int pontosTotaisT2 = tB.getPontosTotais() - (tB.getQuantAdvrunghs() * 10);
                return Integer.compare(pontosTotaisT2, pontosTotaisT1);
            }
        });

        area.setText("");
        StringBuilder tabelaFinal = new StringBuilder("\nTabela final:\n");
        int posicao = 1;
        for (Time time : tabelaCompleta) {
            int pontuacaoFinal = time.getPontosTotais() - (time.getQuantAdvrunghs() * 10);

            tabelaFinal.append(posicao).append("º: ").append(time.getNome())
                    .append(" - Blots: ").append(time.getQuantBlot())
                    .append(", Plifs: ").append(time.getQuantFlip())
                    .append(", Advrunghs: ").append(time.getQuantAdvrunghs())
                    .append(", Pontuação final: ").append(pontuacaoFinal)
                    .append("\n");
            posicao++;
        }
        if (tabelaCompleta.length > 0) {
            Time campeao = tabelaCompleta[0];
            tabelaFinal.append("\nGrito de guerra do time campeão: ").append(campeao.getNome()).append("\n")
                    .append(campeao.getGritoDeGuerra());
        }

        area.append(tabelaFinal.toString());
    }


    private void desempate(Time timeA, Time timeB) {
        JFrame janelaGrusht = new JFrame("Grusht");
        janelaGrusht.setLayout(new GridLayout(4, 1));

        JLabel instrucoes = new JLabel("Grusht iniciado");
        janelaGrusht.add(instrucoes);

        JLabel pontuacaoTimeA = new JLabel("Pontos " + timeA.getNome() + ": 0");
        JLabel pontuacaoTimeB = new JLabel("Pontos " + timeB.getNome() + ": 0");
        janelaGrusht.add(pontuacaoTimeA);
        janelaGrusht.add(pontuacaoTimeB);

        JLabel timerLabel = new JLabel("Tempo restante: 60s");
        timerLabel.setFont(new Font("Serif", Font.BOLD, 20));
        janelaGrusht.add(timerLabel);
        janelaGrusht.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        janelaGrusht.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(
                        janelaGrusht,
                        "Não é possível fechar a janela durante o Grusht. Aguarde o encerramento automático após o final da contagem. ",
                        "Não é possível fechar a janela durante o Grusht.",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        Timer timer = new Timer(1000, new ActionListener() {
            private int tempo = 60;
            private int pontuacaoAtualA = 0;
            private int pontuacaoAtualB = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (tempo > 0) {
                    pontuacaoAtualA += (int) (Math.random() * 10);
                    pontuacaoAtualB += (int) (Math.random() * 10);
                    pontuacaoTimeA.setText("Pontos " + timeA.getNome() + ": " + pontuacaoAtualA);
                    pontuacaoTimeB.setText("Pontos " + timeB.getNome() + ": " + pontuacaoAtualB);
                    tempo--;
                    timerLabel.setText("Tempo restante: " + tempo + "s");
                } else {
                    ((Timer) e.getSource()).stop();
                    if (pontuacaoAtualA > pontuacaoAtualB) {
                        timeA.setPontos(timeA.getPontos() + 3);
                        area.append("O time " + timeA.getNome() + " venceu o Grusht.\n");
                        timesPerdedores.add(timeB);
                    } else if (pontuacaoAtualB > pontuacaoAtualA) {
                        timeB.setPontos(timeB.getPontos() + 3);
                        area.append("O time " + timeB.getNome() + " venceu o Grusht.\n");
                        timesPerdedores.add(timeA);
                    } else {
                        area.append("O Grusht terminou empatado. Tente novamente.\n");
                        desempate(timeA, timeB);
                    }
                    janelaGrusht.dispose();
                }
            }
        });
        timer.start();
        janelaGrusht.setSize(600, 600);
        janelaGrusht.setLocationRelativeTo(null);
        janelaGrusht.setVisible(true);
    }

}

