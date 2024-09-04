package gerencia;

import java.util.ArrayList;
import java.util.List;

public class CadastroTimes {
    private Time[] times;
    private final int MAX_TIMES = 16;
    private int quantidadeAtual;
    private List<Time> finalistas;

    public CadastroTimes() {
        this.times = new Time[MAX_TIMES];
        this.quantidadeAtual = 0;
        this.finalistas = new ArrayList<>();
    }

    public boolean adicionarTime(Time time) {
        if (quantidadeAtual >= MAX_TIMES) {
            return false;
        }
        for (int i = 0; i < quantidadeAtual; i++) {
            if (times[i].getNome().equalsIgnoreCase(time.getNome())) {
                return false;
            }
        }
        times[quantidadeAtual] = time;
        quantidadeAtual++;
        return true;
    }

    public int getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public Time[] clonarTimes() {
        Time[] clone = new Time[quantidadeAtual];
        for (int i = 0; i < quantidadeAtual; i++) {
            clone[i] = times[i];
        }
        return clone;
    }

}
