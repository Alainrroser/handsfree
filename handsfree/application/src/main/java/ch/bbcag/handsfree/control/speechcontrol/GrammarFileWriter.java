package ch.bbcag.handsfree.control.speechcontrol;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GrammarFileWriter {

    public void write(List<String> commands, String name, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        writeVersion(writer);
        writer.write("\n");
        writeName(name, writer);
        writer.write("\n");
        writeCommands(commands, writer);
        writer.close();
    }

    private void writeVersion(FileWriter writer) throws IOException {
        writer.write("#JSGF V1.0;\n");
    }

    private void writeName(String name, FileWriter writer) throws IOException {
        writer.write("grammar " + name + ";\n");
    }

    private void writeCommands(List<String> commands, FileWriter writer) throws IOException {
        writer.write("public <commands> = (\n");

        for(int i = 0; i < commands.size(); i++) {
            String command = commands.get(i);

            writer.write("\t" + command);
            if(i != commands.size() - 1) {
                writer.write(" |");
            }
            writer.write("\n");
        }

        writer.write(");");
    }

}
