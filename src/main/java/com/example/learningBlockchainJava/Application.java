package com.example.learningBlockchainJava;

import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
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

        List<Block> blockChain = new ArrayList<Block>();
        final String firstHash = "0";

        Block block1 = new Block("First Block", firstHash);
        blockChain.add(block1);
        System.out.println(String.format("isBlockChainValid = %b", isBlockChainValid(blockChain)));

        Block block2 = new Block("Second Block", block1.currentHash);
        blockChain.add(block2);
        System.out.println(String.format("isBlockChainValid = %b", isBlockChainValid(blockChain)));

        Block block3 = new Block("Third Block", block2.currentHash);
        blockChain.add(block3);
        System.out.println(String.format("isBlockChainValid = %b", isBlockChainValid(blockChain)));

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
        System.out.println(blockchainJson);

        System.out.println("learningBlockchainJava end...");
    }

    private static boolean isBlockChainValid(List<Block> blockChain) {
        if (blockChain.size() < 1) return true;

        for (int i = 1; i <= blockChain.size() - 1; i++) {
            Block currentBlock = blockChain.get(i - 1);
            Block nextBlock = blockChain.get(i);
            if (!(nextBlock.previousHash.equals(currentBlock.currentHash))) {
                return false;
            }
        }

        return true;
    }
}
