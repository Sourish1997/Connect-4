import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class SplashPanel extends JPanel implements ActionListener {
    private ImageIcon logo = new ImageIcon("./images/connect-four.png");
    private JButton single_player, multi_player;
    private int selected = -1;

    public SplashPanel() {
        single_player = new JButton("Single Player");
        multi_player = new JButton("Multiplayer");
        single_player.setPreferredSize(new Dimension(200, 100));
        single_player.setBackground(Color.RED);
        single_player.setForeground(Color.WHITE);
        single_player.setFont(new Font("Arial", Font.BOLD, 26));
        multi_player.setBackground(Color.RED);
        multi_player.setForeground(Color.WHITE);
        multi_player.setFont(new Font("Arial", Font.BOLD, 26));
        multi_player.setPreferredSize(new Dimension(200, 100));
        logo.setImage(getScaledImage(logo.getImage(), 400, 100));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 20, 50, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(new JLabel(logo), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(single_player, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        add(multi_player, gbc);

        setBackground(new Color(255, 233, 150));

        single_player.addActionListener(this);
        multi_player.addActionListener(this);
    }

    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(single_player)) {
            selected = 0;
        } else {
            selected = 1;
        }
    }

    public int getSelected() {
        return selected;
    }

    public void resetSelected() {
        selected = -1;
    }
}
