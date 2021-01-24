package calculator.models;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class NetworkCalculator {

    private String ipAddress;

    private String binaryIp;
    private String networkClass;
    private String totalNetworks;
    private String totalNodes;

    public NetworkCalculator (String ip) {
        this.ipAddress = ip;
    }

    public void startCalculate () {
        String[] strings = ipAddress.split("\\.");
        List<Integer> integerList = new ArrayList<>();

        for (String str: strings) {
            integerList.add(Integer.parseInt(str));
        }

        AtomicReference<String> binary = new AtomicReference<>("");
        integerList.forEach(x -> {
            String binaryStr = Integer.toBinaryString(x);
            binary.updateAndGet(v -> v + binaryAdapter(binaryStr) + " ");
        });

        binaryIp = binary.get();
        networkClass = classIdentifier(binaryIp.substring(0,9));

        Pair<String, String> features = featuresCalculator(networkClass);
        totalNetworks = features.getKey();
        totalNodes = features.getValue();
    }

    private AtomicReference<String> binaryAdapter (String str) {
        StringBuilder strBuilder = new StringBuilder(str);
        while (strBuilder.length() < 8 ) {
            strBuilder.insert(0, "0");
        }
        str = strBuilder.toString();

        return new AtomicReference<>(str);
    }

    private String classIdentifier (String first_octet) {
        if (first_octet.charAt(0) == '0') {
            return "A";
        }
        else if (first_octet.startsWith("10")) {
            return "B";
        }
        else if (first_octet.startsWith("110")) {
            return "C";
        }
        else if (first_octet.startsWith("1110")) {
            return "D";
        }
        else if (first_octet.startsWith("11110")) {
            return "E";
        }
        else return "Error: Class not Found :(";
    }

    private Pair<String, String> featuresCalculator (String networkClass) {
        switch (networkClass) {
            case "A":
                return new Pair<>(Double.toString(Math.pow(2, 8-1) - 2), Double.toString(Math.pow(2, 24) - 2));
            case "B":
                return new Pair<>(Double.toString(Math.pow(2, 16-2) - 2), Double.toString(Math.pow(2, 16) - 2));
            case "C":
                return new Pair<>(Double.toString(Math.pow(2, 24-3) - 2), Double.toString(Math.pow(2, 8) - 2));
            case "D":
                return new Pair<>("Multicast", "Multicast");
            case "E":
                return new Pair<>("Reserved", "Reserved");
            default:
                return new Pair<>("Error: Data calculation error", "Error: Data calculation error");
        }
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getBinaryIp() {
        return binaryIp;
    }

    public String getNetworkClass() {
        return networkClass;
    }

    public String getTotalNetworks() {
        return totalNetworks;
    }

    public String getTotalNodes() {
        return totalNodes;
    }
}