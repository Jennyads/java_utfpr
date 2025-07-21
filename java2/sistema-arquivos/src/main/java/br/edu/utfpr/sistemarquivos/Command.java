package br.edu.utfpr.sistemarquivos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum Command {

    LIST() {
        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("LIST") || commands[0].startsWith("list");
        }

        @Override
        Path execute(Path path) throws IOException {
            var files = Files.list(path);

            files.forEach(p -> {
                String tipo = Files.isDirectory(p) ? "[DIR]" : "[FILE]";
                System.out.println(tipo + " " + p.getFileName());
            });

            return path;
        }

    },
    SHOW() {
        private String[] parameters = new String[]{};

        @Override
        void setParameters(String[] parameters) {
            this.parameters = parameters;
        }

        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("SHOW") || commands[0].startsWith("show");
        }

        @Override
        Path execute(Path path) {
            if (parameters.length < 2) {
                throw new UnsupportedOperationException("Uso incorreto de SHOW. Exemplo: SHOW arquivo.txt");
            }

            final var fileName = parameters[1];
            final var targetPath = path.resolve(fileName);

            if (!Files.exists(targetPath)) {
                throw new UnsupportedOperationException("Arquivo não encontrado: " + fileName);
            }

            if (Files.isDirectory(targetPath)) {
                throw new UnsupportedOperationException("SHOW não pode ser usado em diretórios.");
            }

            if (!fileName.toLowerCase().endsWith(".txt")) {
                throw new UnsupportedOperationException("Apenas arquivos .txt podem ser exibidos com SHOW.");
            }

            final var fileReader = new FileReader();
            fileReader.read(targetPath);

            return path;
        }

    },
    BACK() {
        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("BACK") || commands[0].startsWith("back");
        }

        @Override
        Path execute(Path path) {
            Path parent = path.getParent();

            if (parent == null || path.equals(Paths.get(Application.ROOT))) {
                throw new UnsupportedOperationException("Você já está no diretório raiz. Não é possível voltar mais.");
            }

            return parent;
        }

    },
    OPEN() {
        private String[] parameters = new String[]{};

        @Override
        void setParameters(String[] parameters) {
            this.parameters = parameters;
        }

        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("OPEN") || commands[0].startsWith("open");
        }

        @Override
        Path execute(Path path) {
            if (parameters.length < 2) {
                throw new UnsupportedOperationException("Uso incorreto de OPEN. Exemplo: OPEN nome_do_diretorio");
            }

            final var dirName = parameters[1];
            final var targetPath = path.resolve(dirName);

            if (!Files.exists(targetPath)) {
                throw new UnsupportedOperationException("Diretório não encontrado: " + dirName);
            }

            if (!Files.isDirectory(targetPath)) {
                throw new UnsupportedOperationException(dirName + " não é um diretório.");
            }

            return targetPath;
        }

    },
    DETAIL() {
        private String[] parameters = new String[]{};

        @Override
        void setParameters(String[] parameters) {
            this.parameters = parameters;
        }

        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("DETAIL") || commands[0].startsWith("detail");
        }

        @Override
        Path execute(Path path) {
            if (parameters.length < 2) {
                throw new UnsupportedOperationException("Uso incorreto de DETAIL. Exemplo: DETAIL nome_do_arquivo");
            }

            final var name = parameters[1];
            final var targetPath = path.resolve(name);

            if (!Files.exists(targetPath)) {
                throw new UnsupportedOperationException("Arquivo ou diretório não encontrado: " + name);
            }

            try {
                var attrs = Files.readAttributes(targetPath, java.nio.file.attribute.BasicFileAttributes.class);

                System.out.println("Nome: " + targetPath.getFileName());
                System.out.println("Tipo: " + (attrs.isDirectory() ? "Diretório" : "Arquivo"));
                System.out.println("Tamanho: " + attrs.size() + " bytes");
                System.out.println("Criado em: " + attrs.creationTime());
                System.out.println("Último acesso: " + attrs.lastAccessTime());
                System.out.println("Última modificação: " + attrs.lastModifiedTime());

            } catch (IOException e) {
                throw new UnsupportedOperationException("Erro ao acessar atributos do arquivo.");
            }

            return path;
        }

    },
    EXIT() {
        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("EXIT") || commands[0].startsWith("exit");
        }

        @Override
        Path execute(Path path) {
            System.out.print("Saindo...");
            return path;
        }

        @Override
        boolean shouldStop() {
            return true;
        }
    };

    abstract Path execute(Path path) throws IOException;

    abstract boolean accept(String command);

    void setParameters(String[] parameters) {
    }

    boolean shouldStop() {
        return false;
    }

    public static Command parseCommand(String commandToParse) {

        if (commandToParse.isBlank()) {
            throw new UnsupportedOperationException("Type something...");
        }

        final var possibleCommands = values();

        for (Command possibleCommand : possibleCommands) {
            if (possibleCommand.accept(commandToParse)) {
                possibleCommand.setParameters(commandToParse.split(" "));
                return possibleCommand;
            }
        }

        throw new UnsupportedOperationException("Can't parse command [%s]".formatted(commandToParse));
    }
}
