import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI extends Application  {

    private Stage primaryStage;
    private MenuBar menu;
    private MenuBar menu2;
    private Scene encryption;
    private Scene decryption;
    private Scene main;

    public GUI()
    {

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        menu = menuSetup();
        menu2 = menuSetup();

        BorderPane encryptionPane = new BorderPane();
        HBox middle = new HBox();
        Text encryptFieldText = new Text("Phrase to encrypt");
        TextField encryptField = new TextField();
        Text encryptKeyText = new Text("Encryption key");
        TextField encryptKeyField = new TextField();
        Button btnEncrypt = new Button("Encrypt");
        btnEncrypt.setOnAction(e -> xorEncrypt(encryptField.getText(), encryptKeyField.getText()));
        middle.getChildren().addAll(encryptFieldText, encryptField, encryptKeyText, encryptKeyField, btnEncrypt);
        encryptionPane.setCenter(middle);
        encryption = new Scene(encryptionPane,800, 600);
        encryptionPane.setTop(menu);

        BorderPane decryptionPane = new BorderPane();
        HBox middle2 = new HBox();
        TextField decryptField = new TextField();
        Button btnDecrypt = new Button("Decrypt");
        btnDecrypt.setOnAction(e -> binaryASCIIToString(decryptField.getText()));
        middle2.getChildren().addAll(decryptField, btnDecrypt);
        decryptionPane.setCenter(middle2);
        decryption = new Scene(decryptionPane,800, 600);
        decryptionPane.setTop(menu2);


        GridPane mainPane = new GridPane();
        Button btn1 = new Button("Encryption");
        btn1.setOnAction(e -> primaryStage.setScene(encryption));
        Button btn2 = new Button("Decryption");
        btn2.setOnAction(e -> primaryStage.setScene(decryption));
        mainPane.add(btn1, 1, 1);
        mainPane.add(btn2, 2, 1);
        mainPane.setAlignment(Pos.CENTER);
        main = new Scene(mainPane, 800, 600, Color.ANTIQUEWHITE);





        // Alignment and stage setting
        primaryStage.setScene(main);
        primaryStage.setTitle("XOR encryption & decryption");
        primaryStage.show();
    }

    public MenuBar menuSetup()
    {
        MenuBar menuBarSetUp = new MenuBar();
        Menu menuSetUp = new Menu("Options");
        MenuItem mainScene = new MenuItem("Main page");
        mainScene.setOnAction(e -> primaryStage.setScene(main));
        MenuItem encryptionScene = new MenuItem("Encryption");
        encryptionScene.setOnAction(e -> primaryStage.setScene(encryption));
        MenuItem decryptionScene = new MenuItem("Decryption");
        decryptionScene.setOnAction(e -> primaryStage.setScene(decryption));
        menuSetUp.getItems().addAll(mainScene, encryptionScene, decryptionScene);
        menuBarSetUp.getMenus().add(menuSetUp);

        return menuBarSetUp;
    }

    public String toBinaryASCII(String toASCIIString)
    {
        // Creates an ArrayList of characters from the provided string
        ArrayList<Character> charArray = new ArrayList<>();

        for (int i = 0; i < toASCIIString.length(); i++)
        {
            charArray.add(toASCIIString.charAt(i));
        }

        // Creates an ArrayList with the corresponding ASCII numbers of the characters
        ArrayList<Integer> asciiArray = new ArrayList<>();

        for (int j = 0; j < charArray.size(); j++)
        {
            asciiArray.add((int)charArray.get(j));
        }

        // Creates an ArrayList with the binary strings of the ASCII numbers
        ArrayList<String> binaryArray = new ArrayList<>();

        for (int l = 0; l < asciiArray.size(); l++)
        {
            binaryArray.add(Integer.toBinaryString(asciiArray.get(l)));
        }

        // Formats the binary strings to be of a homogeneous length of 8
        for (int k = 0; k < binaryArray.size(); k++)
        {
            // Checks for inhomogeneity in the binary strings
            if (binaryArray.get(k).length() < 8)
            {
                String fixedBinary = "";
                // Accounts for the amount of characters missing and adjusts them
                for (int h = 0; h < (8-binaryArray.get(k).length()); h++)
                {
                    fixedBinary += "0";
                }
                fixedBinary += binaryArray.get(k);
                binaryArray.set(k, fixedBinary);
            }
        }

        System.out.println(toString(binaryArray));
        System.out.println(toString(binaryArray).length());
        return toString(binaryArray);
    }


    public String binaryASCIIToString(String binaryASCIIString)
    {
        String readableString = "";
        char nextChar;
        // Creates a list of
        List<String> binaryASCIIArray = Arrays.asList(binaryASCIIString.split(" "));
        for (int i = 0; i < (binaryASCIIString.length()/9); i++)
        {
            nextChar = (char)Integer.parseInt(binaryASCIIArray.get(i), 2);
            readableString += nextChar;
        }
        System.out.println(readableString);

        return readableString;
    }


    public void xorEncrypt(String encryptString, String encryptKey)
    {
        String binaryString, binaryKey;
        binaryString = toBinaryASCII(encryptString);
        binaryKey = toBinaryASCII(encryptKey);


        List<String> binaryStringArray = new ArrayList<>(Arrays.asList(binaryString.split("(?!^)")));
        List<Integer> binaryIntegerArray = new ArrayList<>();
        for(String i:binaryStringArray){
            if (i.equals(" "))
            {
                binaryIntegerArray.add(null);
            }
            else {
                binaryIntegerArray.add(Integer.parseInt(i.trim()));
            }
        }

        List<String> binaryKeyArray = new ArrayList<>(Arrays.asList(binaryKey.split("(?!^)")));
        List<Integer> binaryIntegerKeyArray = new ArrayList<>();
        for(String i:binaryKeyArray){
            if (i.equals(" "))
            {
                binaryIntegerKeyArray.add(null);
            }
            else {
                binaryIntegerKeyArray.add(Integer.parseInt(i.trim()));
            }
        }

        int j = 0;
        String encryptedString = "";
        for (int i = 0; i < binaryIntegerArray.size(); i++)
        {
            if(binaryIntegerArray.get(i) == null && binaryIntegerKeyArray.get(j) == null)
            {
                encryptedString += " ";
            }
            else if (binaryIntegerArray.get(i) == null || binaryIntegerKeyArray.get(j) == null)
            {
                i = binaryIntegerArray.size();
            }
            else {
                encryptedString += binaryIntegerArray.get(i) ^ binaryIntegerKeyArray.get(j);
            }
            j++;
            if(j > 8)
            {
                j = 0;
            }
        }
        System.out.println(binaryString);
        System.out.println(binaryKey);
        System.out.println(encryptedString);

        //binaryIntegerArray.stream().forEach(e -> System.out.println(e));


    }


    public void xorDecrypt()
    {

    }


    public String toString(ArrayList<String> byteList)
    {
        String text = "";
        for (int i = 0; i < byteList.size(); i++)
        {
            text += byteList.get(i) + " ";
        }
        return text;
    }

    @Override
    public void stop()
    {
        System.exit(0);
    }
}

