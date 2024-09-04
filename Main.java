import gerencia.CadastroTimes;
import janelas.JanelaEntrada;

public class Main {
    public static void main(String[] args) {
        CadastroTimes cadastro = new CadastroTimes();
        new JanelaEntrada(cadastro);
    }
}