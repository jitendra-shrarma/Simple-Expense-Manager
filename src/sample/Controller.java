package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private StackPane changePane;
    @FXML private AnchorPane mainMenuPane;
    @FXML private JFXButton exitProgram;
    @FXML private AnchorPane showPane;
    @FXML private AnchorPane inputPane;
    @FXML private TextField username;
    @FXML private TextField amount;
    @FXML private JFXButton back;
    @FXML private GridPane matrix;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private Double[][] matrixData = new Double[3][3];
    HashMap<String, Integer> map = new HashMap<>();

    @FXML void AddAnExpense(MouseEvent event) {
        changePane.getChildren().clear();
        changePane.getChildren().add(inputPane);
    }

    @FXML void ShowExpense(MouseEvent event) {
        for (Node node : matrix.getChildren()) {
            if(matrix.getRowIndex(node) > 0 && matrix.getColumnIndex(node) > 0) {
                Label label = (Label) node;
                label.setText(df2.format(matrixData[matrix.getRowIndex(node)-1][matrix.getColumnIndex(node)-1]));
            }
        }
        changePane.getChildren().clear();
        changePane.getChildren().add(showPane);
    }

    @FXML void ExitProgram(MouseEvent event) {
        Stage stage = (Stage) exitProgram.getScene().getWindow();
        stage.close();
    }

    @FXML void AddExpense(MouseEvent event) {
        String name = username.getText();
        String paidString = amount.getText();
        if (name.length()!=0 && paidString.length()!=0 && map.containsKey(name)){
            Double paid;
            try {
                paid = Double.parseDouble(paidString);
            }catch (Exception e){
                paid = 0.0;
            }
            Double each = paid/3;
            Integer pos = map.get(name);
            for(int i=0; i<3; i++){
                if (pos == i){
                    continue;
                }else {
                    matrixData[i][pos] += each;
                    if (matrixData[pos][i] > matrixData[i][pos]) {
                        matrixData[pos][i] -= matrixData[i][pos];
                        matrixData[i][pos] = 0.0;
                    } else {
                        matrixData[i][pos] -= matrixData[pos][i];
                        matrixData[pos][i] = 0.0;
                    }
                }
            }
        }
        username.clear();
        amount.clear();
        back.fire();
    }

    @FXML void CancelExpense(MouseEvent event) {
        username.clear();
        amount.clear();
        back.fire();
    }

    @FXML void ShowMainMenu(ActionEvent event) {
        changePane.getChildren().clear();
        changePane.getChildren().add(mainMenuPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i=1; i<=3; i++){
            map.put(Character.toString((char) 64+i), i-1);
            Label label = new Label(Character.toString((char) 64+i));
            label.setFont(Font.font("16"));
            label.prefWidthProperty().bind(matrix.widthProperty().divide(4));
            label.prefHeightProperty().bind(matrix.heightProperty().divide(4));
            matrix.add(label, i, 0, 1, 1);
        }
        for(int i=1; i<=3; i++){
            Label label = new Label(Character.toString((char) 64+i));
            label.setFont(Font.font("16"));
            label.setPadding(new Insets(0,0,0,10));
            label.prefWidthProperty().bind(matrix.widthProperty().divide(4));
            label.prefHeightProperty().bind(matrix.heightProperty().divide(4));
            matrix.add(label, 0, i, 1, 1);
        }
        for(int i=1; i<=3; i++){
            for(int j=1; j<=3; j++){
                matrixData[i-1][j-1] = 0.0;
                Label label = new Label("0.00");
                label.setFont(Font.font("16"));
                label.prefWidthProperty().bind(matrix.widthProperty().divide(4));
                label.prefHeightProperty().bind(matrix.heightProperty().divide(4));
                matrix.add(label, i, j, 1, 1);
            }
        }
    }
}