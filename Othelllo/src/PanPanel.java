import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

public class PanPanel extends JPanel implements MouseListener{
    Color Green = new Color(222, 198, 178);
//    Color Green = new Color(106, 141, 40);
    Color Brown = new Color(96, 77, 62);

    ImageIcon white = new ImageIcon("Img/whiteS");
    Image imageW = white.getImage(); // transform it
    Image newW = imageW.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
//    ImageIcon w  = new ImageIcon(newW);

    ImageIcon black= new ImageIcon("Img/blackS");
    Image imageB = black.getImage(); // transform it
    Image newB = imageB.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
    //    ImageIcon b  = new ImageIcon(newB);

    boolean click = false;
    boolean flag = true; //true:검정
    static int count = 0;
    static int distinction = 0;

    int[][] doll = new int[8][8];
    Color color = Color.black;
    Point p;
    int Bscore = 0;
    int Wscore = 0;
    int newX = 0;
    int newY = 0;
    int emptyCheck = 0;


    PanPanel(){
        addMouseListener(this);

        doll[3][3] = -1;
        doll[3][4] = 1;
        doll[4][3] = 1;
        doll[4][4] = -1;
        setBorder(new LineBorder(Brown,5));
        setLayout(null);
        setBounds(50,50, 800, 800);
        setBackground(Green);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        click = true;
       // if(passCheck()){
        //    count++;
       // }
        if (count % 2 == 0) {
            distinction = 1;
        } else {
            distinction = -1;
        }
        p = e.getPoint();
        int x = (int) p.getX() / 100;
        int y = (int) p.getY() / 100;

        if(doll[y][x] != 0){
            ;
        }
        else if (dollCheck(y, x)) {
            dollChange(y, x);
            doll[y][x] = distinction;//색깔 결정
            System.out.println("되니");
            repaint();
            count++;
        } else return;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(doll[i][j] == 1||doll[i][j] == -1){
                    continue;
                }
                if(doll[i][j] == 0)
                    return;
            }
        }
        String winner = "";
        if(Bscore < Wscore)
            winner = "White Win";
        else
            winner = "Black Win";
        JOptionPane.showMessageDialog(null,"GAME OVER\n Winner : " + winner );
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
            for (int i = 100; i <= 800; i += 100) {
                g.drawLine(i, 0, i, 800);
                g.drawLine(0, i, 800, i);
            }

                Bscore = 0;
                Wscore = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (doll[i][j] == 0) {
                        emptyCheck++;
                    } else if (doll[i][j] == 1) {
                        g.setColor(Color.black);
                        g.fillOval(100 * j + 5, 100 * i + 5, 90, 90);
                        Bscore++;
                    } else if (doll[i][j] == -1) {
                        g.setColor(Color.white);
                        g.fillOval(100 * j + 5, 100 * i + 5, 90, 90);
                        Wscore++;
                    }
                }
            }
        System.out.println(count);

    }
    public boolean Endline(int y, int x){
        return ( y < 0 || y > 7 || x < 0|| x > 7);

    }
    public void dollChange(int y, int x){
        boolean check = false;
        for(int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if(Endline(y+i,x+j))
                    continue;
                if(doll[y+i][x+j] == distinction * -1) {
                    newX = x + j;
                    newY = y + i;
                    while (true) {
                        if (Endline(newY,newX) || doll[newY][newX] == 0) {
                            check = true;
                            break;
                        }
                        else if(doll[newY][newX] == distinction){
                            check = false;
                            break;
                        }
                        else if(doll[newY][newX] == distinction * -1){
                            newX += j;
                            newY += i;
                        }
                        }
                            if(!check){
                                while(newX != x || newY != y){
                                    newX -= j;
                                    newY -= i;
                                    doll[newY][newX] = distinction;
                                }
                            }

                    }
                }
        }

    }
    public boolean dollCheck(int y, int x){
            for(int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (Endline(y + i, x + j))
                        continue;
                    if (doll[y + i][x + j] == distinction*-1 ) {
                       if (check2(y,x,i,j)){
                           repaint();
                           return true;
                         }
                        }
                    }
                }
            return false;
            }

    public boolean check2(int y, int x, int i, int j) {
        int NX = x + j;
        int NY = y + i;
        while (true) {
            if (Endline(NY, NX)||doll[NY][NX] == 0) {
                return false;
            } else if (doll[NY][NX] == distinction) {
                return true;
            } else if(doll[NY][NX] == distinction * -1){
                NX += j;
                NY += i;
            }
        }
    }
    public boolean passCheck(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(doll[i][j] == 0) {
                    if(!dollCheck(i, j))
                        continue;
                    else return false;
                }
            }
        }
        return true;
    }

    public void resetGame() {
        for (int i = 0; i < doll.length; i++) {
            for (int j = 0; j < doll[i].length; j++) {
                doll[i][j] = 0;
            }
        }
        doll[3][3] = -1;
        doll[3][4] = 1;
        doll[4][3] = 1;
        doll[4][4] = -1;
    
        Bscore = 2;
        Wscore = 2;
        count = 0;
    
        repaint();
    }
    

}
