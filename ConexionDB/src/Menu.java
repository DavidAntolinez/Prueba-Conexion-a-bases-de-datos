import javax.swing.*;
import java.awt.*;

public abstract class Menu  {

    private String title;

    public Menu(String title) {
        this.title = title;
    }

    abstract public void menu();

    public void msg(String msg) {
        JOptionPane.showMessageDialog(null, msg, "title", 1);
    }

    public String input(String msg) {
        return JOptionPane.showInputDialog(null, msg, title, 3) ;
    }

    public void msgScroll(String msg) {
        JTextArea textArea = new JTextArea(msg);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize( new Dimension( 500, 500 ) );
        JOptionPane.showMessageDialog(null, scrollPane, title, 1);
    }

    public Object inputSelect(String msg, String titleSelect, String[] options) {
        return JOptionPane.showInputDialog(null, msg, titleSelect, JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
    }    
}
