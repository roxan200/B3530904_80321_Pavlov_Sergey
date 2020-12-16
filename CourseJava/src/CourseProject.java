import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CourseProject extends Application {
    private File fileLabs;
    private File commandFile;
    private File fileAbsolutePath;
    private File fileOnlyWrite;
    private File fileOnlyWriteMode;
    private TextArea textInput;
    private static final int NUMBER_LAB = 8;
    private String currentObjectConst;
    private static ArrayList<HBox> hBox;
    //private Process runs;

    public static void main(String[] args) {
        //before public void init()
        Application.launch(args);
        //after public void stop()
    }

    @Override
    public void init() throws Exception {
        //before public void start; Don't use GUI here
        fileLabs = new File("labs.txt");        //file for description of programs
        fileAbsolutePath = new File("hidden_path.txt");
        fileOnlyWrite = new File("hidden_write.txt");
        fileOnlyWriteMode = new File("hidden_write_mode.txt");
        commandFile = new File("hidden.txt");   //file for lab6

        setHiddenAttrib(Path.of(commandFile.getPath()), false); //unhide file
        if (!commandFile.exists())                       //if the file doesn't exist
            commandFile.createNewFile();
        setHiddenAttrib(Path.of(commandFile.getPath()), true); //hide file

        super.init();
    }

    @Override
    public void start(Stage stage) throws IOException {
        currentObjectConst = Paths.get(".").toAbsolutePath().normalize().toString();
        Image imageMag = new Image(getClass().getResourceAsStream("mag32.png"));
        Image imageRun = new Image(getClass().getResourceAsStream("right32.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseProject.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        textInput = (TextArea)loader.getNamespace().get("textConsoleInput");
        VBox labs = (VBox)loader.getNamespace().get("labs");
        //HBox base = (HBox)loader.getNamespace().get("base");
        CPController.sTextArea.setWrapText(true);
        installEventHandler(textInput);

        hBox = new ArrayList<>(NUMBER_LAB);
        for (int i = 0; i < NUMBER_LAB; ++i) {
            hBox.add(new HBox());
            Button buttonRef = new Button();
            Button buttonRun = new Button();
            Label labelName = new Label();
            buttonRef.setGraphic(new ImageView(imageMag));
            buttonRun.setGraphic(new ImageView(imageRun));
            labelName.setText("LAB" + (i + 1) + ".JAVA");
            labelName.setFont(Font.font("Bernard MT Condensed", FontWeight.BOLD, 20));
            labelName.setAlignment(Pos.CENTER);
            //strech elements through their width and height
            buttonRef.setMaxWidth(Double.MAX_VALUE);
            buttonRun.setMaxWidth(Double.MAX_VALUE);
            labelName.setMaxWidth(Double.MAX_VALUE);
            buttonRef.setMaxHeight(Double.MAX_VALUE);
            buttonRun.setMaxHeight(Double.MAX_VALUE);
            labelName.setMaxHeight(Double.MAX_VALUE);
            HBox.setHgrow(buttonRef, Priority.ALWAYS);
            HBox.setHgrow(buttonRun, Priority.ALWAYS);
            HBox.setHgrow(labelName, Priority.ALWAYS);
            int finalI = i + 1;
            //add event click for description button
            buttonRef.setOnAction(event -> CPController.sTextArea.setText(split(OpenAndReadFile(fileLabs, false), "\n\n", finalI)));
            //add event click for run button
            buttonRun.setOnAction(actionEvent -> {
                CPController.sTextArea.setText("");
                if (finalI == 6) {                              //scipt for lab6
                    writeOrRewrite(fileAbsolutePath, false, currentObjectConst, true);
                    writeOrRewrite(fileOnlyWriteMode, false, "0", true);
                    writeOrRewrite(fileOnlyWrite, false, "", true);
                    setPressed("");
                } else
                    proccessRun(finalI);                         //run a certain program
                textInput.setDisable(finalI != 6 && finalI != 5);//console input is enabled only for lab6 and lab5
            });
            //add elements to ArrayList
            hBox.get(i).getChildren().addAll(buttonRef, buttonRun, labelName);
            hBox.get(i).setMaxWidth(Double.MAX_VALUE);
            labs.getChildren().add(hBox.get(i));
            VBox.setVgrow(hBox.get(i), Priority.ALWAYS);
        }

        scene.getStylesheets().add(getClass().getResource("CourseProject.css").toExternalForm());
        stage.setScene(scene);
        stage.getIcons().add(new Image(CourseProject.class.getResourceAsStream("right32.png")));
        stage.setTitle("Course Project");
        stage.setMinHeight(650);
        stage.setMinWidth(500);
        stage.setMaxHeight(950);
        stage.setMaxWidth(1000);
        stage.setMaximized(false);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        writeOrRewrite(fileAbsolutePath, false, currentObjectConst, true);//file for lab6
        writeOrRewrite(fileOnlyWriteMode, false, "0", true);
        writeOrRewrite(fileOnlyWrite, false, "", true);
        setPressed("");
        super.stop();
    }

    //handler for release of Enter key in "textConsoleInput"
    private void installEventHandler (final Node keyNode) {
        final EventHandler <KeyEvent> keyEventHandler = keyEvent -> {
            if (keyEvent.getCode () == KeyCode.ENTER) {
                //setPressed(keyEvent.getEventType() == KeyEvent.KEY_PRESSED);
                setPressed(textInput.getText());
                keyEvent.consume();//Not process the keyevents which were not caused due to released Enter
            }
        };
        //keyNode.setOnKeyPressed (keyEventHandler);
        keyNode.setOnKeyReleased (keyEventHandler);
    }

    //Enter handler for executing of lab6
    private void setPressed(String toWriteInFile) {
        System.out.print(toWriteInFile);
        setHiddenAttrib(Path.of(commandFile.getPath()), false);                //unhide file
        try(FileOutputStream fos = new FileOutputStream(commandFile, false)) //write file
        {
            byte[] buffer = toWriteInFile.getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch(IOException ex){
            System.out.println(ex.getMessage());
        } finally {
            int writeMode = Integer.parseInt(OpenAndReadFile(fileOnlyWriteMode, true));    //read write mode
            if (writeMode >= 1 && toWriteInFile.compareTo(":q\n") != 0) {                            //echo or append and it isn't end
                writeOrRewrite(fileOnlyWrite, true, toWriteInFile, true);         //write to temp file
            } else if (writeMode >= 1 && toWriteInFile.compareTo(":q\n") == 0) {                     //echo or append and it is end
                writeOrRewrite(fileOnlyWriteMode, false, (writeMode == 1) ? "3" : "4", true); //3 - echo end; 4 - append end
            }
            textInput.setText("");
            setHiddenAttrib(Path.of(commandFile.getPath()), true);                //hide file
            proccessRun(6);
        }
    }

    //run a certain program
    private void proccessRun(int finalI) {
        try {
            ProcessBuilder builder = new ProcessBuilder("C:\\Program Files\\Java\\jdk-15.0.1\\bin\\java.exe",
                    "-javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA Educational Edition 2020.2.2\\lib\\idea_rt.jar=5776:C:\\Program Files\\JetBrains\\IntelliJ IDEA Educational Edition 2020.2.2\\bin\"" +
                            "-Dfile.encoding=UTF-8 -classpath \"C:\\Users\\himro\\UserData\\Java2020\\CourseJava\\out\\production\\CourseJava\" lab" + finalI + ".Main");
            builder.redirectErrorStream(true);//output errors to console
            Process runs = builder.start();
            //type program's output in "textConsoleOutput"
            new Thread(() -> {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(runs.getOutputStream()))) { //input data
                    if (textInput.getText().length() > 0)
                        writer.write(textInput.getText());
                    textInput.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(runs.getInputStream()))) {   //output data
                    String line;
                    while ((line = reader.readLine()) != null)
                        System.out.println(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    //read lab's descriptions or writemode for lab6
    private static String OpenAndReadFile(File file, boolean hiddenFlag) {

        setHiddenAttrib(Path.of(file.getPath()), false);

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
                sb.append(sCurrentLine + "\n");
            sb = new StringBuilder(sb.substring(0, sb.length() - 1));//delete last char
        } catch (IOException ex) {
            ex.printStackTrace();
            return "close";
        } finally {
            setHiddenAttrib(Path.of(file.getPath()), hiddenFlag);
        }

        return sb.toString();
    }

    //writeMode: false - echo; true - append;
    private static void writeOrRewrite(File f, boolean writeMode, String content, boolean hiddenFlag) {

        setHiddenAttrib(Path.of(f.getPath()), false);

        try(FileOutputStream fos = new FileOutputStream(f, writeMode)) {
            byte[] buffer = content.getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch(IOException ex){
            System.out.println(ex.getMessage());
        } finally {
            setHiddenAttrib(Path.of(f.getPath()), hiddenFlag);
        }
    }

    //set hide attribute for a file
    private static void setHiddenAttrib(Path filePath, boolean hide) {
        try {
            Files.setAttribute(filePath, "dos:hidden", hide);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //split string and select certain element
    public static String split(String str, String delimiter, int select) {
        String[] temp = str.split(delimiter);
        for (int i = 0; i < temp.length; ++i)
            if (i == select - 1)
                return temp[i];
        return str;
    }

    //    private void OpenAndRewriteFile(File fileForRewrite, String stringForRewrite, boolean hiddenFlag) {
    //
    //        setHiddenAttrib(Path.of(fileForRewrite.getPath()), false);                  //unhide file
    //
    //        try(FileOutputStream fos = new FileOutputStream(fileForRewrite, false))   //write file
    //        {
    //            byte[] buffer = stringForRewrite.getBytes();
    //            fos.write(buffer, 0, buffer.length);
    //        } catch(IOException ex){
    //            System.out.println(ex.getMessage());
    //        } finally {
    //            setHiddenAttrib(Path.of(fileForRewrite.getPath()), hiddenFlag);     //hide file
    //        }
    //    }
}