import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class Main extends JPanel implements ActionListener,KeyListener
{
GameState game;
int x=90,y=85;
javax.swing.Timer timer;
public Main()
{
int inits[][]=new int[6][7];
for(int a=0;a<6;a++)
{
for(int b=0;b<7;b++)
{
inits[a][b]=0;;
}
}
inits[5][3]=1;
game=new GameState(inits,false);
addKeyListener(this);
setFocusable(true);
setFocusTraversalKeysEnabled(false);
timer=new javax.swing.Timer(40,this);
timer.start();
}
public void paintComponent(Graphics g)
{
super.paintComponent(g);
Graphics2D g2=(Graphics2D)g;
g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
g2.fillRect(80,75,640,550);
g2.setColor(UIManager.getColor ( "Panel.background" ));
for(int a=90,b=0;b<7;a+=90,b++)
{
for(int c=85,d=0;d<6;c+=90,d++)
{
if(game.board[d][b]==1)
g2.setColor(Color.BLUE);
else if(game.board[d][b]==2)
g2.setColor(Color.RED);
g2.fillOval(a,c,80,80);
g2.setColor(UIManager.getColor ( "Panel.background" ));
}
}
if(game.maxPlayer)
g2.setColor(new Color(0,0,255,60));
else
g2.setColor(new Color(255,0,0,60));
g2.fillOval(x,y,80,80);
}
public static void main(String args[])
{
JFrame jf=new JFrame("Connect Four");
Main obj=new Main();
jf.setSize(800,700);
jf.setResizable(false);
jf.setLocationRelativeTo(null);
jf.add(obj);
jf.setVisible(true);
}
public void keyPressed(KeyEvent e)
{
}
public void keyReleased(KeyEvent e)
{
if(e.getKeyCode()==e.VK_LEFT)
{
if(x>90)
{
x-=90;
}
}
if(e.getKeyCode()==e.VK_RIGHT)
{
if(x<630)
x+=90;
}
if(e.getKeyCode()==e.VK_ENTER)
{
if(game.maxPlayer==false)
{
game.addDisc((x/90)-1);
Computation comp=new Computation();
comp.refresh();
int bestMove=comp.minimax(game,10,-100,100);
ArrayList<Integer> possibleMoves=new ArrayList<Integer>();
int count=0;
for(int moveVal:comp.childrenVals)
{
if(moveVal==bestMove)
possibleMoves.add(count);
count++;
}
System.out.println(bestMove);
System.out.println(comp.childrenVals);
System.out.println(possibleMoves);
if(possibleMoves.size()%2==0)
game.addDisc(possibleMoves.get(possibleMoves.size()/2));
else
game.addDisc(possibleMoves.get((int)((possibleMoves.size()/2)+1)));
if(comp.winCheck(game)==10)
{
JOptionPane.showMessageDialog(null,"Blue Wins!!");
timer.stop();
}
else if(comp.winCheck(game)==1)
{
JOptionPane.showMessageDialog(null,"Red Wins!!");
timer.stop();
}
}
}
}
public void keyTyped(KeyEvent e)
{
}
public void actionPerformed(ActionEvent e)
{
repaint();
}
}