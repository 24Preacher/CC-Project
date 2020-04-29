import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//SDASDASDASDASDAS
public class Client{

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Socket s = new Socket("localhost", 4444);
        PrintWriter pr = new PrintWriter(s.getOutputStream(),true);
        while (true){
            System.out.println("> ");
            String mensagem = input.readLine();
            pr.println(mensagem);
            if (mensagem.equals("quit"))
                break;

        }
        s.close();

    }

}
