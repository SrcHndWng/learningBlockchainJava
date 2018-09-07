package com.example.learningBlockchainJava;

import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    private final int difficulty = 5;
    private final String firstHash = "0";
    private List<Block> blockChain;

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

        blockChain = new ArrayList<Block>();

        addBlock(new Block("First Block", firstHash));
        addBlock(new Block("Second Block", blockChain.get(blockChain.size() - 1).currentHash));
        addBlock(new Block("Third Block", blockChain.get(blockChain.size() - 1).currentHash));

        // Test with thief block.
        addBlock(new Block("Thief Block", blockChain.get(blockChain.size() - 2).currentHash));

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
        System.out.println(blockchainJson);

        System.out.println("learningBlockchainJava end...");
    }

    private boolean isBlockChainValid(List<Block> blockChain) {
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

    public void addBlock(Block b) {
        b.mineBlock(difficulty);
        blockChain.add(b);
        if (!isBlockChainValid(blockChain)) {
            System.out.println(
                    "Block :"
                            + b.currentHash
                            + " - "
                            + b.previousHash
                            + " is not valid, removing it");
            blockChain.remove(b);
        }
    }
}
