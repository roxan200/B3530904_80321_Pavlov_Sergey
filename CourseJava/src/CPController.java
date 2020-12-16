import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

public class CPController implements Initializable {
    public static TextArea sTextArea;
    public static PrintStream streamOutputConsole;

    @FXML
    public VBox labs;

    @FXML
    public TextArea textConsoleOutput;

    @FXML
    public TextArea textConsoleInput;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sTextArea = textConsoleOutput;
        streamOutputConsole = new PrintStream(new Console(textConsoleOutput));
        System.setOut(streamOutputConsole);
        System.setErr(streamOutputConsole);//output all errors in textArea
    }

    public class Console extends OutputStream {
        private TextArea console;

        public Console(TextArea console) {
            this.console = console;
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) throws IOException {
            appendText(String.valueOf((char)b));
        }
    }
}