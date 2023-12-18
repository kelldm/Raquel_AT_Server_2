package org.example;
import java.util.ArrayList;
import static spark.Spark.*;
import com.google.gson.Gson;
import javax.swing.*;
import java.awt.*;



public class Main {
    public static void main(String[] args) {
        ArrayList<String> codigos = new ArrayList<>();
        Gson gson = new Gson();
        codigos.add("0123456789");

        JFrame frame = new JFrame("Servidor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,2));

        JTextField inputField1 = new JTextField();

        inputField1.setFont((new Font("Arial", Font.PLAIN, 30)));


        JLabel label2 = new JLabel("Acesso:");
        label2.setFont((new Font("Arial", Font.BOLD, 30)));

        panel.add(label2);
        panel.add(inputField1);




        frame.add(panel);
        frame.setVisible(true);

        port(8080);

        post("/api/verifica", (request, response) -> {
            String corpoRequest = request.body();
            System.out.println("Corpo JSON: " + corpoRequest);
            Acesso codigo = gson.fromJson(corpoRequest, Acesso.class);
            inputField1.setText(codigo.codigo);


            if (codigos.contains(codigo.codigo)) {
                return "{\"ACK\": \n \"1\"}";
            } else {
                return "{\"ACK\": \n \"0\"}";
            }

        });
        post("/api/cadastrar", (request, response) -> {
            String corpoRequest = request.body();
            System.out.println("Corpo JSON: " + corpoRequest);
            Acesso codigo = gson.fromJson(corpoRequest, Acesso.class);
            codigos.add(codigo.codigo);

            return "Codigo de acesso cadastrado: " + codigo.codigo;
        });
    }

    private static class Acesso {
        String codigo;
    }

}