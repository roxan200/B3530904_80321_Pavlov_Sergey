import java.io.*;

public class test {
    public static void main(String[] str) {
        ProcessBuilder builder = new ProcessBuilder("C:\\Program Files\\Java\\jdk-15.0.1\\bin\\java.exe",
                "-javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA Educational Edition 2020.2.2\\lib\\idea_rt.jar=5776:C:\\Program Files\\JetBrains\\IntelliJ IDEA Educational Edition 2020.2.2\\bin\"" +
                        "-Dfile.encoding=UTF-8 -classpath \"C:\\Users\\himro\\UserData\\Java2020\\CourseJava\\out\\production\\CourseJava\" lab5.Main");
        builder.redirectErrorStream(true);//output errors to console


        try {
            Process process = builder.start();
            //Process process = Runtime.getRuntime().exec(command);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            writer.write("123 234 345");
            writer.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
