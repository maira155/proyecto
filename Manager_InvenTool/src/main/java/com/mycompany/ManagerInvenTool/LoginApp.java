package com.mycompany.managerinventool;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class LoginApp extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JTextField campoCaptcha;
    private JLabel lblCaptcha;
    private JButton botonLogin;
    private String captcha;

    public LoginApp() {
        setTitle(" Login - Manager Inventool");
        setSize(450, 450); // más alto para captcha
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(new Color(34, 34, 34));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("Manager Inventool", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(new Color(255, 215, 0));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titulo, gbc);

        // Usuario
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel.add(lblUsuario, gbc);

        gbc.gridx = 1;
        campoUsuario = new JTextField(15);
        campoUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel.add(campoUsuario, gbc);

        // Contraseña
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setForeground(Color.WHITE);
        lblContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel.add(lblContrasena, gbc);

        gbc.gridx = 1;
        campoContrasena = new JPasswordField(15);
        campoContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel.add(campoContrasena, gbc);

        // Panel captcha
        JPanel panelCaptcha = new JPanel();
        panelCaptcha.setBackground(new Color(45, 45, 45));
        panelCaptcha.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
                "Verificación",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 12),
                Color.LIGHT_GRAY));
        panelCaptcha.setLayout(new GridBagLayout());
        GridBagConstraints gbcCaptcha = new GridBagConstraints();
        gbcCaptcha.insets = new Insets(5, 5, 5, 5);
        gbcCaptcha.fill = GridBagConstraints.HORIZONTAL;

        // Texto captcha
        JLabel textoCaptcha = new JLabel("Introduce el código:");
        textoCaptcha.setForeground(Color.LIGHT_GRAY);
        textoCaptcha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbcCaptcha.gridx = 0;
        gbcCaptcha.gridy = 0;
        gbcCaptcha.gridwidth = 2;
        panelCaptcha.add(textoCaptcha, gbcCaptcha);

        // Captcha visual
        lblCaptcha = new JLabel();
        lblCaptcha.setForeground(new Color(255, 215, 0));
        lblCaptcha.setFont(new Font("Courier New", Font.BOLD, 18));
        lblCaptcha.setHorizontalAlignment(SwingConstants.CENTER);
        lblCaptcha.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(3, 8, 3, 8)
        ));
        gbcCaptcha.gridy = 1;
        gbcCaptcha.gridwidth = 1;
        panelCaptcha.add(lblCaptcha, gbcCaptcha);

        // Campo de entrada captcha
        campoCaptcha = new JTextField(8);
        campoCaptcha.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbcCaptcha.gridx = 0;
        gbcCaptcha.gridy = 2;
        panelCaptcha.add(campoCaptcha, gbcCaptcha);

        // Botón refrescar captcha
        JButton btnRefrescar = new JButton("↻");
        btnRefrescar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefrescar.setMargin(new Insets(2, 5, 2, 5));
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.addActionListener(e -> generarCaptcha());
        gbcCaptcha.gridx = 1;
        panelCaptcha.add(btnRefrescar, gbcCaptcha);

        generarCaptcha();

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(panelCaptcha, gbc);

        // Botón de login
        gbc.gridy = 4;
        botonLogin = new JButton("Conectarse");
        botonLogin.setBackground(new Color(255, 215, 0));
        botonLogin.setForeground(new Color(34, 34, 34));
        botonLogin.setFont(new Font("Segoe UI", Font.BOLD, 18));
        botonLogin.setFocusPainted(false);
        botonLogin.addActionListener(e -> autenticar());
        panel.add(botonLogin, gbc);

        // Pie de página
        gbc.gridy = 5;
        JLabel footer = new JLabel("© 2025 Manager Inventool", SwingConstants.CENTER);
        footer.setForeground(Color.LIGHT_GRAY);
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        panel.add(footer, gbc);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void generarCaptcha() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(caracteres.charAt(rand.nextInt(caracteres.length())));
        }
        captcha = sb.toString();
        lblCaptcha.setText(captcha);
    }

    private void autenticar() {
        String usuario = campoUsuario.getText();
        String contrasena = new String(campoContrasena.getPassword());
        String captchaIngresado = campoCaptcha.getText();

        if (!captchaIngresado.equals(captcha)) {
            JOptionPane.showMessageDialog(this, " Captcha incorrecto. Intenta de nuevo.");
            generarCaptcha();
            return;
        }

        if (usuario.equals("admin") && contrasena.equals("1234")) {
            JOptionPane.showMessageDialog(this, " Bienvenido " + usuario + "!");
            this.dispose();
            new IndexApp().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, " Usuario o contraseña incorrectos.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginApp::new);
    }
}
