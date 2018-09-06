package com.example.learningBlockchainJava;

import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args)) {
            Application app = ctx.getBean(Application.class);
            app.run();
        } catch (Exception e) {
            System.out.println("Application Error!");
            e.printStackTrace();
        }
    }

    private void run() throws Exception {
        System.out.println("learningBlockchainJava start...");

        ArrayList<Block> blockchain = new ArrayList<Block>();
        final String firstHash = "0";

        blockchain.add(new Block("Hi im the first block", firstHash));
        blockchain.add(new Block("Yo im the second block", getPreviousHash(blockchain)));
        blockchain.add(new Block("Hey im the third block", getPreviousHash(blockchain)));

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);

        System.out.println("learningBlockchainJava end...");
    }

    private String getPreviousHash(ArrayList<Block> blockchain) {
        return blockchain.get(blockchain.size() - 1).hash;
    }
}
