import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GponCalculator extends JFrame {

    private JTextField txPowerField, rxSensitivityField, splitterLossField, fiberLossField, distanceField, safetyMarginField;
    private JTextField signalPowerField, noiseField;
    private JTextField downstreamField, upstreamField, overheadField;
    private JTextArea resultArea;

    public GponCalculator() {
        setTitle("GPON Calculator");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Orçamento de Potência", createPowerBudgetPanel());
        tabbedPane.addTab("Perda na Fibra", createFiberLossPanel());
        tabbedPane.addTab("Relação Sinal-Ruído", createSNRPanel());
        tabbedPane.addTab("Capacidade de Transmissão", createCapacityPanel());
        tabbedPane.addTab("Margem de Segurança", createPowerMarginPanel());
        tabbedPane.addTab("Perda por Divisão", createSplitterLossPanel());

        add(tabbedPane, BorderLayout.CENTER);

        resultArea = new JTextArea(8, 50);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createPowerBudgetPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel txPowerLabel = new JLabel("Potência de Transmissão (dBm):");
        txPowerField = new JTextField(10);
        JLabel rxSensitivityLabel = new JLabel("Sensibilidade do Receptor (dBm):");
        rxSensitivityField = new JTextField(10);
        JLabel splitterLossLabel = new JLabel("Perda no Splitter (dB):");
        splitterLossField = new JTextField(10);
        JLabel fiberLossLabel = new JLabel("Perda na Fibra (dB/km):");
        fiberLossField = new JTextField(10);
        JLabel distanceLabel = new JLabel("Distância Total (km):");
        distanceField = new JTextField(10);
        JLabel safetyMarginLabel = new JLabel("Margem de Segurança (dB):");
        safetyMarginField = new JTextField(10);

        JButton calcPowerBudgetButton = new JButton("Calcular");
        calcPowerBudgetButton.setPreferredSize(new Dimension(100, 25));
        calcPowerBudgetButton.setFont(new Font("Arial", Font.PLAIN, 12));
        calcPowerBudgetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateOpticalPowerBudget();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(txPowerLabel, gbc);

        gbc.gridx = 1;
        panel.add(txPowerField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(rxSensitivityLabel, gbc);

        gbc.gridx = 1;
        panel.add(rxSensitivityField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(splitterLossLabel, gbc);

        gbc.gridx = 1;
        panel.add(splitterLossField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(fiberLossLabel, gbc);

        gbc.gridx = 1;
        panel.add(fiberLossField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(distanceLabel, gbc);

        gbc.gridx = 1;
        panel.add(distanceField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(safetyMarginLabel, gbc);

        gbc.gridx = 1;
        panel.add(safetyMarginField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(calcPowerBudgetButton, gbc);

        return panel;
    }

    private JPanel createFiberLossPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel fiberLossLabel = new JLabel("Perda na Fibra (dB/km):");
        fiberLossField = new JTextField(10);
        JLabel distanceLabel = new JLabel("Distância Total (km):");
        distanceField = new JTextField(10);

        JButton calcFiberLossButton = new JButton("Calcular");
        calcFiberLossButton.setPreferredSize(new Dimension(100, 25));
        calcFiberLossButton.setFont(new Font("Arial", Font.PLAIN, 12));
        calcFiberLossButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateFiberLoss();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(fiberLossLabel, gbc);

        gbc.gridx = 1;
        panel.add(fiberLossField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(distanceLabel, gbc);

        gbc.gridx = 1;
        panel.add(distanceField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(calcFiberLossButton, gbc);

        return panel;
    }

    private JPanel createSNRPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel signalPowerLabel = new JLabel("Potência do Sinal Recebido (dBm):");
        signalPowerField = new JTextField(10);
        JLabel noiseLabel = new JLabel("Ruído no Receptor (dBm):");
        noiseField = new JTextField(10);

        JButton calcSNRButton = new JButton("Calcular");
        calcSNRButton.setPreferredSize(new Dimension(100, 25));
        calcSNRButton.setFont(new Font("Arial", Font.PLAIN, 12));
        calcSNRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateSNR();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(signalPowerLabel, gbc);

        gbc.gridx = 1;
        panel.add(signalPowerField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(noiseLabel, gbc);

        gbc.gridx = 1;
        panel.add(noiseField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(calcSNRButton, gbc);

        return panel;
    }

    private JPanel createCapacityPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel downstreamLabel = new JLabel("Velocidade Downstream (Gbps):");
        downstreamField = new JTextField(10);
        JLabel upstreamLabel = new JLabel("Velocidade Upstream (Gbps):");
        upstreamField = new JTextField(10);
        JLabel overheadLabel = new JLabel("Taxa de Sobrecarga (%) :");
        overheadField = new JTextField(10);

        JButton calcCapacityButton = new JButton("Calcular");
        calcCapacityButton.setPreferredSize(new Dimension(100, 25));
        calcCapacityButton.setFont(new Font("Arial", Font.PLAIN, 12));
        calcCapacityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateCapacity();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(downstreamLabel, gbc);

        gbc.gridx = 1;
        panel.add(downstreamField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(upstreamLabel, gbc);

        gbc.gridx = 1;
        panel.add(upstreamField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(overheadLabel, gbc);

        gbc.gridx = 1;
        panel.add(overheadField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(calcCapacityButton, gbc);

        return panel;
    }

    private JPanel createPowerMarginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel safetyMarginLabel = new JLabel("Margem de Segurança (dB):");
        safetyMarginField = new JTextField(10);

        JButton calcMarginButton = new JButton("Calcular");
        calcMarginButton.setPreferredSize(new Dimension(100, 25));
        calcMarginButton.setFont(new Font("Arial", Font.PLAIN, 12));
        calcMarginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatePowerMargin();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(safetyMarginLabel, gbc);

        gbc.gridx = 1;
        panel.add(safetyMarginField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(calcMarginButton, gbc);

        return panel;
    }

    private JPanel createSplitterLossPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel splitterFactorLabel = new JLabel("Fator de Divisão:");
        JTextField splitterFactorField = new JTextField(10);

        JButton calcSplitterLossButton = new JButton("Calcular");
        calcSplitterLossButton.setPreferredSize(new Dimension(100, 25));
        calcSplitterLossButton.setFont(new Font("Arial", Font.PLAIN, 12));
        calcSplitterLossButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateSplitterLoss(splitterFactorField.getText());
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(splitterFactorLabel, gbc);

        gbc.gridx = 1;
        panel.add(splitterFactorField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(calcSplitterLossButton, gbc);

        return panel;
    }

    private void calculateOpticalPowerBudget() {
        try {
            double txPower = Double.parseDouble(txPowerField.getText());
            double rxSensitivity = Double.parseDouble(rxSensitivityField.getText());
            double splitterLoss = Double.parseDouble(splitterLossField.getText());
            double fiberLoss = Double.parseDouble(fiberLossField.getText());
            double distance = Double.parseDouble(distanceField.getText());
            double safetyMargin = Double.parseDouble(safetyMarginField.getText());

            double totalFiberLoss = fiberLoss * distance;
            double totalLoss = splitterLoss + totalFiberLoss;
            double receivedPower = txPower - totalLoss;

            if (receivedPower >= rxSensitivity + safetyMargin) {
                resultArea.setText(String.format("Orçamento de Potência:\nPotência Recebida: %.2f dBm\nSensibilidade do Receptor: %.2f dBm\nMargem de Segurança: %.2f dB\nTotal de Perda: %.2f dB\n", receivedPower, rxSensitivity, safetyMargin, totalLoss));
            } else {
                resultArea.setText("A potência recebida não é suficiente para a sensibilidade do receptor com a margem de segurança.");
            }
        } catch (NumberFormatException e) {
            resultArea.setText("Por favor, insira valores numéricos válidos.");
        }
    }

    private void calculateFiberLoss() {
        try {
            double fiberLoss = Double.parseDouble(fiberLossField.getText());
            double distance = Double.parseDouble(distanceField.getText());

            double totalFiberLoss = fiberLoss * distance;
            resultArea.setText(String.format("Perda Total na Fibra: %.2f dB", totalFiberLoss));
        } catch (NumberFormatException e) {
            resultArea.setText("Por favor, insira valores numéricos válidos.");
        }
    }

    private void calculateSNR() {
        try {
            double signalPower = Double.parseDouble(signalPowerField.getText());
            double noise = Double.parseDouble(noiseField.getText());

            double snr = signalPower - noise;
            resultArea.setText(String.format("Relação Sinal-Ruído: %.2f dB", snr));
        } catch (NumberFormatException e) {
            resultArea.setText("Por favor, insira valores numéricos válidos.");
        }
    }

    private void calculateCapacity() {
        try {
            double downstream = Double.parseDouble(downstreamField.getText());
            double upstream = Double.parseDouble(upstreamField.getText());
            double overhead = Double.parseDouble(overheadField.getText());

            double totalCapacity = downstream + upstream - (downstream + upstream) * (overhead / 100);
            resultArea.setText(String.format("Capacidade de Transmissão: %.2f Gbps", totalCapacity));
        } catch (NumberFormatException e) {
            resultArea.setText("Por favor, insira valores numéricos válidos.");
        }
    }

    private void calculatePowerMargin() {
        try {
            double safetyMargin = Double.parseDouble(safetyMarginField.getText());
            resultArea.setText(String.format("Margem de Segurança: %.2f dB", safetyMargin));
        } catch (NumberFormatException e) {
            resultArea.setText("Por favor, insira valores numéricos válidos.");
        }
    }

    private void calculateSplitterLoss(String splitterFactorText) {
        try {
            int splitterFactor = Integer.parseInt(splitterFactorText);

            double splitterLoss = 10 * Math.log10(splitterFactor);
            resultArea.setText(String.format("Perda por Divisão: %.2f dB", splitterLoss));
        } catch (NumberFormatException e) {
            resultArea.setText("Por favor, insira um valor numérico válido para o fator de divisão.");
        }
    }

    public static void main(String[] args) {
        new GponCalculator();
    }
}
