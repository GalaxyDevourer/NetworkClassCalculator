package calculator.controllers;

import calculator.models.NetworkCalculator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class MainController {
    @FXML Button calculateBtn;
    @FXML TextField addressInput;

    @FXML Label binaryAddressLabel;
    @FXML Label classLabel;
    @FXML Label totalNetsLabel;
    @FXML Label totalNodesLabel;

    private NetworkCalculator calculator;

    private static final Pattern IP_PATTERN = Pattern.compile(
            "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$");

    @FXML public void start () {
        String ipAdress = addressInput.getText();

        if (IP_PATTERN.matcher(ipAdress).matches()) {
            calculator = new NetworkCalculator(ipAdress);
            calculator.startCalculate();

            setData();
        }
        else dialogRegMessage("IP Pattern Error", "Sorry, your address is invalid!",
                "Please, enter your address again!", Alert.AlertType.ERROR);
    }

    private void setData () {
        binaryAddressLabel.setText(calculator.getBinaryIp());
        classLabel.setText(calculator.getNetworkClass());
        totalNetsLabel.setText(calculator.getTotalNetworks());
        totalNodesLabel.setText(calculator.getTotalNodes());
    }

    private void dialogRegMessage(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
