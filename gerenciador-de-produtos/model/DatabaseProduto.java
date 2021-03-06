package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabaseProduto {
    private Path currentPath;
    private Path filepath;

    public DatabaseProduto() {
        currentPath = Paths.get(System.getProperty("user.dir"));
        filepath = Paths.get(currentPath.toString(), "gerenciador-de-produtos", "db", "produto.csv");
    }

    public void insert(String nome, String fabricante, double valor) {
        try {
            File file = new File(filepath.toString());

            if (!file.exists()) {
                FileWriter fileWriter = new FileWriter(filepath.toString(), true);
                fileWriter.write("nome;fabricante;valor\n");
                fileWriter.write(nome + ";" + fabricante + ";" + valor + "\n");
                fileWriter.close();
            } else {
                FileWriter fileWriter = new FileWriter(filepath.toString(), true);
                fileWriter.write(nome + ";" + fabricante + ";" + valor + "\n");
                fileWriter.close();
            }
        } catch (Exception e) {
            System.out.println("Erro ao criar o banco de dados Produto");
        }
    }

    public void delete(String nome) {
        Path temporaryFilepath = Paths.get(currentPath.toString(), "gerenciador-de-produtos", "db", "temp-produto.csv");

        File originalFile = new File(filepath.toString());
        File temporaryFile = new File(temporaryFilepath.toString());

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(temporaryFile));

            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                if (currentLine.contains(nome)) continue;
                bufferedWriter.write(currentLine + System.getProperty("line.separator"));
            }
            bufferedReader.close();
            bufferedWriter.close();
            temporaryFile.renameTo(originalFile);
        } catch (Exception e) {
            System.out.println("Erro ao deletar a(s) linha(s) do arquivo!");
        }
    }

    public void sort() {
        try {
            FileReader fileReader = new FileReader(filepath.toString());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line  = bufferedReader.readLine();
            while (line != null) {
                System.out.println(line);
                line  = bufferedReader.readLine();
                String[] arrayLine = line.split(";");
                System.out.println(arrayLine[0]);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Banco de dados Produto não encontrado!");
        } catch (IOException e) {
            System.out.println("Erro na leitura do banco de dados Produto!");
        }
    }
}