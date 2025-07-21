package br.edu.utfpr.sistemarquivos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader {

    public void read(Path path) {
        try {
            if (!Files.exists(path)) {
                System.out.println("Arquivo não encontrado.");
                return;
            }

            if (!Files.isRegularFile(path)) {
                System.out.println("O caminho não é um arquivo.");
                return;
            }

            if (!path.toString().toLowerCase().endsWith(".txt")) {
                System.out.println("Somente arquivos .txt podem ser exibidos.");
                return;
            }

            Files.lines(path).forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
