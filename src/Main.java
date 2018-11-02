import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JPanel implements ActionListener, KeyListener {
    static GameState game;
    int x = 90, y = 65;
    static javax.swing.Timer timer;

    private static JMenuBar menuBar;
    private static JMenu menus[];
    private static JMenuItem menuItems[];
    private static SplashPanel splashPanel;
    private static Main mainPanel;
    private static JFrame frame;
    private static Timer mainTimer;
    private static JLabel thinkingLabel;

    private static int gameMode = 0;
    private static int playerMode = 0;
    private static int depth = 6;

    private static boolean thinking = false;
    private static boolean ended = false;

    public Main() {
        int inits[][] = new int[6][7];
        for (int a = 0; a < 6; a++) {
            for (int b = 0; b < 7; b++) {
                inits[a][b] = 0;;
            }
        }

        thinkingLabel = new JLabel("Thinking...");
        thinkingLabel.setFont(new Font(thinkingLabel.getFont().getFontName(), Font.BOLD, 20));
        setLayout(null);
        add(thinkingLabel);
        Insets insets = getInsets();
        Dimension size = thinkingLabel.getPreferredSize();
        thinkingLabel.setBounds(350 + insets.left, 5 + insets.top,
                size.width, size.height);
        thinkingLabel.setForeground(new Color(183,73, 73));
        thinkingLabel.setVisible(false);

        if(gameMode == 0 && playerMode == 0)
            inits[5][3] = 1;
        game = new GameState(inits, false);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new javax.swing.Timer(40, this);
        timer.start();

        setBackground(new Color(255, 233, 150));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 0,0));
        g2.fillRoundRect(60, 35, 680, 590, 50, 50);
        g2.setColor(new Color(255, 233, 150));
        for (int a = 90, b = 0; b < 7; a += 90, b++) {
            for (int c = 65, d = 0; d < 6; c += 90, d++) {
                if (game.board[d][b] == 1)
                    g2.setColor(new Color(73, 115, 183));
                else if (game.board[d][b] == 2)
                    g2.setColor(new Color(183,73, 73));
                g2.fillOval(a, c, 80, 80);
                g2.setColor(new Color(255, 233, 150));
            }
        }
        if (game.maxPlayer)
            g2.setColor(new Color(73, 115, 183, 150));
        else
            g2.setColor(new Color(183, 73, 73, 150));

        g2.fillOval(x, y, 80, 80);
    }

    public static void init() {
        menuBar = new JMenuBar();
        menus = new JMenu[3];
        menuItems = new JMenuItem[7];
        menus[0] = new JMenu("        File        ");
        menus[1] = new JMenu("        Help        ");
        menus[2] = new JMenu("        Settings        ");
        menuItems[0] = new JMenuItem("        New Game       ");
        menuItems[1] = new JMenuItem("        How to Play        ");
        menuItems[2] = new JMenuItem("        About       ");
        menuItems[3] = new JMenuItem("        Exit       ");
        menuItems[4] = new JMenuItem("        Home        ");
        menuItems[5] = new JMenuItem("        Player Settings        ");
        menuItems[6] = new JMenuItem("        Computer Level        ");
        menus[0].add(menuItems[4]);
        menus[0].add(new JSeparator());
        menus[0].add(menuItems[0]);
        menus[0].add(new JSeparator());
        menus[0].add(menuItems[3]);
        menus[1].add(menuItems[1]);
        menus[1].add(new JSeparator());
        menus[1].add(menuItems[2]);
        menus[2].add(menuItems[5]);
        menus[2].add(new JSeparator());
        menus[2].add(menuItems[6]);
        menuBar.add(menus[0]);
        menuBar.add(menus[2]);
        menuBar.add(menus[1]);
        menuItems[0].addActionListener(e -> {
            timer.stop();
            int inits[][] = new int[6][7];
            for (int a = 0; a < 6; a++) {
                for (int b = 0; b < 7; b++) {
                    inits[a][b] = 0;
                }
            }
            if(gameMode == 0 && playerMode == 0)
                inits[5][3] = 1;
            game = new GameState(inits, false);

            thinking = false;
            ended = false;
            thinkingLabel.setVisible(false);

            timer.start();
        });
        menuItems[1].addActionListener(e -> JOptionPane.showMessageDialog(null, "Select a column using arrow keys and press enter to insert a new checker." +
                "\nTo win you must place 4 checkers in an row, horizontally, vertically or diagonally."));
        menuItems[2].addActionListener(e -> JOptionPane.showMessageDialog(null, "© Created by Sourish Banerjee"));
        menuItems[3].addActionListener(e -> System.exit(0));
        menuItems[4].addActionListener(e -> addSplashPanel());
        menuItems[5].addActionListener(e -> {
            Object[] possibilities = {"Computer Plays First", "User Plays First"};
            String selection = (String)JOptionPane.showInputDialog(
                    null,
                    "Make a Selection",
                    "Who Plays First?",
                    JOptionPane.QUESTION_MESSAGE,
                    new ImageIcon("./images/select.png"),
                    possibilities,
                    "Computer Plays First");

            //If a string was returned, say so.
            if ((selection != null) && (selection.length() > 0)) {
                if(selection.equals("Computer Plays First"))
                    playerMode = 0;
                else
                    playerMode = 1;
            }
        });
        menuItems[6].addActionListener(e -> {
            Object[] possibilities = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
            String selection = (String)JOptionPane.showInputDialog(
                    null,
                    "Make a Selection",
                    "Choose Computer Level",
                    JOptionPane.QUESTION_MESSAGE,
                    new ImageIcon("./images/select.png"),
                    possibilities,
                    "6");

            //If a string was returned, say so.
            if ((selection != null) && (selection.length() > 0)) {
                depth = Integer.parseInt(selection);
            }
        });
    }

    public static void addMainPanel() {
        frame.getContentPane().removeAll();
        mainPanel = new Main();
        frame.setJMenuBar(menuBar);
        menuBar.setVisible(true);
        frame.add(mainPanel);
        frame.validate();
        mainPanel.requestFocusInWindow();

        thinking = false;
        ended = false;

        if(gameMode == 1) {
            menuItems[5].setEnabled(false);
            menuItems[6].setEnabled(false);
        } else {
            menuItems[5].setEnabled(true);
            menuItems[6].setEnabled(true);

            playerMode = 0;
            depth = 6;
        }
    }

    public static void addSplashPanel() {
        frame.getContentPane().removeAll();
        frame.setJMenuBar(menuBar);
        menuBar.setVisible(false);
        frame.add(splashPanel);
        frame.validate();
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}

        init();

        frame = new JFrame("Connect Four");
        splashPanel = new SplashPanel();
        frame.setSize(800, 700);
        frame.setResizable(false);
        frame.setJMenuBar(menuBar);
        menuBar.setVisible(false);
        frame.add(splashPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        mainTimer = new javax.swing.Timer(40, e -> {
            if(splashPanel.getSelected() == 0) {
                splashPanel.resetSelected();
                gameMode = 0;
                addMainPanel();
            } else if(splashPanel.getSelected() == 1) {
                splashPanel.resetSelected();
                gameMode = 1;
                addMainPanel();
            }
        });

        mainTimer.start();
    }

    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == e.VK_LEFT) {
            if(thinking || ended)
                return;
            if (x > 90) {
                x -= 90;
            }
        }
        if (e.getKeyCode() == e.VK_RIGHT) {
            if(thinking || ended)
                return;
            if (x < 630)
                x += 90;
        }
        if (e.getKeyCode() == e.VK_ENTER) {
            if(thinking || ended)
                return;
            if (game.maxPlayer == false || gameMode == 1) {
                int allowed = game.addDisc((x / 90) - 1);
                if(allowed == -1)
                    return;

                Computation comp = new Computation();
                comp.refresh();

                if(comp.winCheck(game) == 2) {
                    JOptionPane.showMessageDialog(null, "Red Wins!!");
                    ended = true;
                    timer.stop();
                    return;
                } else if(comp.boardFilled(game)) {
                    JOptionPane.showMessageDialog(null, "It's a Draw!!");
                    ended = true;
                    timer.stop();
                    return;
                }

                if(gameMode == 0) {
                    thinking = true;
                    thinkingLabel.setVisible(true);
                    SwingWorker sw1 = new SwingWorker()
                    {

                        @Override
                        protected String doInBackground() throws Exception
                        {
                            int[] bestMove = comp.minimax(game, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
                            game.addDisc(bestMove[1]);
                            thinkingLabel.setVisible(false);
                            thinking = false;

                            if (comp.winCheck(game) == 1) {
                                JOptionPane.showMessageDialog(null, "Blue Wins!!");
                                ended = true;
                                timer.stop();

                            } else if (comp.boardFilled(game)) {
                                JOptionPane.showMessageDialog(null, "It's a Draw!!");
                                ended = true;
                                timer.stop();
                            }
                            String res = "Finished Execution";
                            return res;
                        }

                        @Override
                        protected void done() {}
                    };
                    sw1.execute();
                } else {
                    if (comp.winCheck(game) == 1) {
                        JOptionPane.showMessageDialog(null, "Blue Wins!!");
                        ended = true;
                        timer.stop();
                    } else if (comp.boardFilled(game)) {
                        JOptionPane.showMessageDialog(null, "It's a Draw!!");
                        ended = true;
                        timer.stop();
                        return;
                    }
                }
            }
        }
    }
    public void keyTyped(KeyEvent e) {}
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}