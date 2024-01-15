import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Random;

public class PasswordGenerator extends JFrame {

    private static final Color BACKGROUND_COLOR = new Color(253, 245, 251);
    private static final Color BUTTON_COLOR = new Color(51, 153, 255);

    private JButton button;
    private JButton copy;
    private JTextField jTextField;
    private JLabel answer;
    private JCheckBox uppercase;
    private JCheckBox lowercase;
    private JCheckBox numbers;
    private JCheckBox specialChars;

    public PasswordGenerator() {
        initializeGUI();
    }

    public void initializeGUI(){
        ImageIcon miniLogo=new ImageIcon("src/img/logo.png");

        this.setTitle("Password Generator");
        this.setSize(500,400);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Exit out of application (default is hide)
        this.setIconImage(miniLogo.getImage());
        this.getContentPane().setBackground(BACKGROUND_COLOR);

        // Create panels
        JPanel titlePanel=new JPanel();
        JPanel text=new JPanel();
        JPanel answerPanel=new JPanel();
        JPanel propertiesPanel=new JPanel();

        // Create and customize labels, button, and text field
        JLabel title=new JLabel("Password Generator");
        JLabel label=new JLabel("How many characters? ");
        button=new JButton("OK");
        copy=new JButton("Copy");
        jTextField=new JTextField();
        answer=new JLabel();
        uppercase =new JCheckBox("Capital Letters");
        uppercase.setFocusable(false);
        lowercase =new JCheckBox("Small Letters");
        lowercase.setFocusable(false);
        numbers=new JCheckBox("Numbers");
        numbers.setFocusable(false);
        specialChars=new JCheckBox("Special Chars");
        specialChars.setFocusable(false);
        uppercase.setSelected(true);
        lowercase.setSelected(true);
        numbers.setSelected(true);
        specialChars.setSelected(false);
        button.setFocusable(false);

        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_COLOR);

        jTextField.setPreferredSize(new Dimension(50,30));

        title.setFont(new Font("MV Boli", Font.PLAIN, 32));
        Font labelFont = label.getFont();
        label.setFont(new Font(labelFont.getName(), Font.PLAIN, 20));

        answer.setHorizontalAlignment(JLabel.CENTER); // Center the answer text

        uppercase.setToolTipText("Include capital letters in the password");
        lowercase.setToolTipText("Include small letters in the password");
        numbers.setToolTipText("Include numbers in the password");
        specialChars.setToolTipText("Include special chars in the password");

        // Add components to panels
        titlePanel.add(title);
        answerPanel.add(answer);
        propertiesPanel.add(uppercase);
        propertiesPanel.add(lowercase);
        propertiesPanel.add(numbers);
        propertiesPanel.add(specialChars);
        text.add(label);
        text.add(jTextField);
        text.add(button);
        text.add(answerPanel);
        text.add(copy);

        // Set layout manager
        this.setLayout(new GridLayout(3, 1));

        // Add padding to panels
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        text.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        titlePanel.setBackground(BACKGROUND_COLOR);
        text.setBackground(BACKGROUND_COLOR);
        answerPanel.setBackground(BACKGROUND_COLOR);
        propertiesPanel.setBackground(BACKGROUND_COLOR);
        uppercase.setBackground(BACKGROUND_COLOR);
        lowercase.setBackground(BACKGROUND_COLOR);
        specialChars.setBackground(BACKGROUND_COLOR);
        numbers.setBackground(BACKGROUND_COLOR);
        this.getContentPane().setBackground(BACKGROUND_COLOR);

        // Add panels to JFrame
        this.add(titlePanel);
        this.add(propertiesPanel);
        this.add(text);

        jTextField.addActionListener(e -> generatePassword());
        button.addActionListener(e -> generatePassword());
        copy.setVisible(false);
        copy.addActionListener(e -> copyToClipboard());

        this.setVisible(true);

    }

    public void generatePassword(){
        String s = jTextField.getText();
        try {
            int num = Integer.parseInt(s);
            if(num<=10&&num>0) {
                String generatedPassword = calculateGeneratedPassword(num);
                answer.setText("Generated Password: " + generatedPassword);
                copy.setVisible(true);
            }else {
                answer.setText("Please enter a number between 1 to 10.");
            }
        } catch (NumberFormatException ex) {
            answer.setText("Please enter a valid number.");
            copy.setVisible(false);
        }
    }

    public String calculateGeneratedPassword(int num){

        StringBuilder characters = new StringBuilder();

        if(uppercase.isSelected()){
            characters.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        }
        if(lowercase.isSelected()){
            characters.append("abcdefghijklmnopqrstuvwxyz");
        }
        if(numbers.isSelected()){
            characters.append("0123456789");
        }
        if(specialChars.isSelected()){
            characters.append("!@#$%^&*()-_=+");
        }

        if (characters.length() == 0) {
            // Handle the case where no character set is selected
            throw new IllegalArgumentException("At least one character set must be selected.");
        }

        Random random = new Random();

        StringBuilder password = new StringBuilder();

        // Generate the password by appending random characters
        for (int i = 0; i < num; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    public void copyToClipboard() {
        String generatedPassword = answer.getText().replace("Generated Password: ", "");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(generatedPassword), null);
        JOptionPane.showMessageDialog(this, "Password copied!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }


}
