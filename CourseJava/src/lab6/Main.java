package lab6;

//Разработать консольное приложение, позволяющее просматривать файлы и каталоги файловой системы,
//а также создавать и удалять текстовые файлы.
//Для работы с текстовыми файлами необходимо реализовать функциональность записи (дозаписи) в файл.

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    private static File fileCourse;//hidden file for commands from CourseProject
    private static File fileAbsolutePath;//hidden file for path of current directory
    private static File fileOnlyWrite;
    private static File fileOnlyWriteMode;
    private static File currentObject;
    //private static File currentObjectConst;
    private static String command;
    private static String[] commandWord;

    //cd
    public static void folderOpen() {
        //new filepath
        if (commandWord.length > 1) {
            if (commandWord[1].compareTo("..") == 0) {                                          //open previous folder
                currentObject = (currentObject.getParentFile() != null) ? currentObject.getParentFile() : currentObject;
            } else if (new File(currentObject.getPath() + "\\" + commandWord[1]).isDirectory()) {   //open some folder
                currentObject = new File(currentObject.getPath() + "\\" + commandWord[1]);
            } else {
                System.out.print("The object isn't a directory or it doesn't exist!");
            }
        }
        listFiles();//list of content
    }
    //mkdir
    public static void folderCreate() {
        if (commandWord.length > 1) {
            File f = new File(currentObject.getPath() + "\\" + commandWord[1]);
            if (!f.exists()) {
                f.mkdir();
                listFiles();//list of content
            } else {
                System.out.print("The directory already exists!");
            }
        } else {
            System.out.print("You missed a command argument...");
        }
    }
    //rmdir
    public static void folderDelete() {
        if (commandWord.length > 1) {
            File f = new File(currentObject.getPath() + "\\" + commandWord[1]);
            if (f.isDirectory()) {
                f.delete();
                listFiles();//list of content
            } else {
                System.out.print("The object isn't a directory or it doesn't exist!");
            }
        } else {
            System.out.print("You missed a command argument...");
        }
    }
    //type
    public static void fileOpen() {
        if (commandWord.length > 1) {
            File f = new File(currentObject.getPath() + "\\" + commandWord[1]);
            if (f.isFile() && f.canRead()) {
                try (FileInputStream fin = new FileInputStream(f.getPath())) {
                    byte[] buffer = new byte[fin.available()];
                    fin.read(buffer, 0, fin.available());
                    for(int i=0; i<buffer.length;i++)
                        System.out.print((char)buffer[i]);
                } catch(IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.print("The object isn't a file or unreadable");
            }
        } else {
            System.out.print("You missed a command argument...");
        }
    }
    //crt
    public static void fileCreate() throws IOException {
        if (commandWord.length > 1) {
            File f = new File(currentObject.getPath() + "\\" + commandWord[1]);
            if (!f.exists()) {
                f.createNewFile();
                listFiles();//list of content
            } else {
                System.out.print("The file already exists!");
            }
        } else {
            System.out.print("You missed a command argument...");
        }
    }
    //del
    public static void fileDelete() {
        if (commandWord.length > 1) {
            File f = new File(currentObject.getPath() + "\\" + commandWord[1]);
            if (f.isFile()) {
                f.delete();
                listFiles();//list of content
            } else {
                System.out.print("The object isn't a file or it doesn't exist!");
            }
        } else {
            System.out.print("You missed a command argument...");
        }
    }
    //false - echo; true - append;
    //1 - echo; 2 - append;
    public static void fileWrite(boolean writeMode) {
        if (commandWord.length > 1) {
            File f = new File(currentObject.getPath() + "\\" + commandWord[1]);
            if (f.isFile() && f.canWrite()) {
                System.out.println("Type a content for the file (type \":q\" without quotes for a finish):");

                //setHiddenAttrib(Path.of(fileOnlyWriteMode.getPath()), false); //unhide file
                //setHiddenAttrib(Path.of(fileAbsolutePath.getPath()), false); //unhide file

                if (writeMode)
                    writeOrRewrite(fileOnlyWrite, writeMode, "\n", true);
                OpenAndRewriteFile(fileOnlyWriteMode, (!writeMode) ? "1" : "2", true);
                currentObject = new File(currentObject.getPath() + "\\" + commandWord[1]);


                //setHiddenAttrib(Path.of(fileOnlyWriteMode.getPath()), true); //hide file
                //setHiddenAttrib(Path.of(fileAbsolutePath.getPath()), true); //hide file


//                Scanner scanner = new Scanner(System.in);
//                String content = (writeMode ? "\n" : ""), lastLine = "";
//                do {
//                    content += lastLine;
//                    lastLine = scanner.nextLine();
//                    lastLine += (lastLine.compareTo(":q") != 0) ? "\n" : "";
//                } while (lastLine.compareTo(":q") != 0);
//                content = content.substring(0, content.length() - 1);//delete last char
//                try(FileOutputStream fos = new FileOutputStream(f, writeMode))
//                {
//                    byte[] buffer = content.getBytes();
//                    fos.write(buffer, 0, buffer.length);
//                }
//                catch(IOException ex){
//                    System.out.println(ex.getMessage());
//                }
            } else {
                System.out.print("The object isn't a file or it's unchangeable\n");
            }
        } else {
            System.out.print("You missed a command argument...");
        }
    }
    //help
    public static void fileHelp() {
        System.out.println("\"cd\"     <DIRECTORY> - open     a folder and list the content");
        System.out.println("\"mkdir\"  <DIRECTORY> - create   a folder");
        System.out.println("\"rmdir\"  <DIRECTORY> - remove   a folder");
        System.out.println("\"type\"   <FILE NAME> - open     a file");
        System.out.println("\"crt\"    <FILE NAME> - create   a file");
        System.out.println("\"del\"    <FILE NAME> - remove   a file");
        System.out.println("\"echo\"   <FILE NAME> - rewrite  a file");
        System.out.println("\"append\" <FILE NAME> - write    at the end'command file");
        System.out.println("\"exit\"               - shutdown a program");
        System.out.println("\"close\"");
    }

    public static void main(String[] args) throws IOException {
        commandWord = new String[2];

        fileOnlyWrite = new File("hidden_write.txt");
        fileOnlyWriteMode = new File("hidden_write_mode.txt");
        fileAbsolutePath = new File("hidden_path.txt");
        fileCourse = new File("hidden.txt");

        //currentObjectConst = Paths.get(".").toAbsolutePath().normalize().toFile();
        currentObject = Path.of(OpenAndReadFile(fileAbsolutePath, true)).toAbsolutePath().normalize().toFile();
        // scanner = new Scanner(System.in);
        //System.out.println("type \"help\" without quotes to get full list of commands.");
        //while (true) {
        //System.out.println("Input your command: ");
        //command = scanner.nextLine();

        //System.out.println(OpenAndReadFile(fileAbsolutePath));

        int writeMode = Integer.parseInt(OpenAndReadFile(fileOnlyWriteMode, true));//read write mode
        if (writeMode >= 3) {//:q
            //setHiddenAttrib(Path.of(fileOnlyWrite.getPath()), false); //unhide file
            //setHiddenAttrib(Path.of(fileOnlyWriteMode.getPath()), false); //unhide file

            String writeForFile = OpenAndReadFile(fileOnlyWrite, true);//read content for (echo or append)
            //System.out.println(writeForFile);
            writeOrRewrite(currentObject, writeMode != 3, writeForFile, false);//3 - echo end; 4 - append end
            OpenAndRewriteFile(fileOnlyWriteMode, "0", true);
            OpenAndRewriteFile(fileOnlyWrite, "", true);
            OpenAndRewriteFile(fileAbsolutePath, splitExcludeLast(currentObject.getPath().toString(), "\\\\"), true);//write path without filename

            currentObject = Path.of(OpenAndReadFile(fileAbsolutePath, true)).toAbsolutePath().normalize().toFile();
            System.out.println("\nCurrent   location: " + currentObject.toPath() + ">");

            //setHiddenAttrib(Path.of(fileOnlyWrite.getPath()), true); //hide file
            //setHiddenAttrib(Path.of(fileOnlyWriteMode.getPath()), true); //hide file
            return;
        } else if (writeMode >= 1)//echo or append
            return;

        System.out.println("\nCurrent   location: " + currentObject.toPath() + ">");
        command = OpenAndReadFile(fileCourse, true);//read command from CourseProject file
        if (command.length() <= 0) {
            return;
        }
        if ((commandWord = command.split(" ")).length > 2) {
            System.out.println("The command isn't correct! type \"help\" without quotes to get full list of commands.");
            return;
        }
        if (commandWord[0].compareTo("exit") == 0 || commandWord[0].compareTo("close") == 0)//shutdown a program
            return;

        try {
            switch (commandWord[0]) {
                case "cd"       -> folderOpen();
                case "mkdir"    -> folderCreate();
                case "rmdir"    -> folderDelete();
                case "type"     -> fileOpen();
                case "crt"      -> fileCreate();
                case "del"      -> fileDelete();
                case "echo"     -> fileWrite(false);
                case "append"   -> fileWrite(true);
                case "help"     -> fileHelp();
                default         -> System.out.println("The command \"" + command + "\" isn't correct! type \"help\" without quotes to get full list of commands.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            OpenAndRewriteFile(fileAbsolutePath, currentObject.toPath().toString(), true);
        }
       //}
        //scanner.close();
    }

    private static String OpenAndReadFile(File file, boolean hiddenFlag) {

        setHiddenAttrib(Path.of(file.getPath()), false); //unhide file

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
                sb.append(sCurrentLine + "\n");
            if (sb.length() > 0)
                sb = new StringBuilder(sb.substring(0, sb.length() - 1));//delete last char
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.print(ex.getStackTrace()[0].getMethodName());
            return "close";
        } finally {
            setHiddenAttrib(Path.of(file.getPath()), hiddenFlag);
        }

        return sb.toString();
    }

    private static void OpenAndRewriteFile(File fileForRewrite, String stringForRewrite, boolean hiddenFlag) {

        setHiddenAttrib(Path.of(fileForRewrite.getPath()), false); //unhide file

        //setHiddenAttrib(Path.of(fileForRewrite.getPath()), false);        //unhide file
        try(FileOutputStream fos = new FileOutputStream(fileForRewrite, false))//write file
        {
            byte[] buffer = stringForRewrite.getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch(IOException ex){
            System.out.println(ex.getMessage());
            System.out.print(ex.getStackTrace()[0].getMethodName());
        } finally {
            setHiddenAttrib(Path.of(fileForRewrite.getPath()), hiddenFlag);
        }
    }

    //writeMode: false - echo; true - append;
    private static void writeOrRewrite(File f, boolean writeMode, String content, boolean hiddenFlag) {

        setHiddenAttrib(Path.of(f.getPath()), false); //unhide file

        try(FileOutputStream fos = new FileOutputStream(f, writeMode)) {
            byte[] buffer = content.getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
            System.out.print(ex.getStackTrace()[0].getMethodName());
        } finally {
            setHiddenAttrib(Path.of(f.getPath()), hiddenFlag); //unhide file
        }
    }

    private static String splitExcludeLast(String str, String delimiter) {
        try {
            String[] temp = str.split(delimiter);
            str = "";
            for (int i = 0; i < temp.length; ++i) {
                if (i + 1 == temp.length)
                    break;
                str += temp[i] + delimiter;
            }
            str = str.substring(0, str.length() - 2);//delete two last chars
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return str;
    }

    //set hide attribute for a file
    private static void setHiddenAttrib(Path filePath, boolean hide) {
        try {
            Files.setAttribute(filePath, "dos:hidden", hide);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.print(ex.getStackTrace()[0].getMethodName());
        }
    }

    //list of content
    private static void listFiles() {
        File[] files = currentObject.listFiles(file -> !file.isHidden());
        ArrayList<String> filenames = new ArrayList<>();
        for (int i = 0; i < files.length; ++i)
            filenames.add(files[i].getName());
        //filenames.addAll(Arrays.asList(currentObject.list()));
        for (int i = 0; i < filenames.size(); ++i) {
            System.out.print(filenames.get(i) + " || ");
            if (i > 0 && i % 10 == 0)
                System.out.println();
        }
    }

}