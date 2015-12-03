import java.awt.*;
import javax.swing.*;


public class Snowflake extends JPanel
{
private int size;
private int level;
private double radius;
private final double TWOSQRT3 = 2*Math.sqrt(3.0);

public Snowflake()
{
size = 400;
radius = 3*size/8.0;
level = 6;
setPreferredSize(new Dimension(size,size));
repaint();
}

private void drawSegment(int l,double px,double py,
double qx, double qy, Graphics g)
{
if (l==0)
{
g.drawLine((int) px,(int) py,(int) qx,(int) qy);
return;
}
double ax = (2.0*px + qx)/3.0;
double ay = (2.0*py + qy)/3.0;
double cx = (2.0*qx + px)/3.0;
double cy = (2.0*qy + py)/3.0;
double bx = 0.5*(px + qx) + (qy - py)/TWOSQRT3;
double by = 0.5*(py + qy) + (px - qx)/TWOSQRT3;
drawSegment(l-1,px,py,ax,ay,g);
drawSegment(l-1,ax,ay,bx,by,g);
drawSegment(l-1,bx,by,cx,cy,g);
drawSegment(l-1,cx,cy,qx,qy,g);
return;
}

public void paintComponent(Graphics g)
{
super.paintComponent(g);
double x0 = size/2 + radius*Math.cos(0*(2*Math.PI)/3);
double y0 = size/2 + radius*Math.sin(0*(2*Math.PI)/3);

double x1 = size/2 + radius*Math.cos(1*(2*Math.PI)/3);
double y1 = size/2 + radius*Math.sin(1*(2*Math.PI)/3);

double x2 = size/2 + radius*Math.cos(2*(2*Math.PI)/3);
double y2 = size/2 + radius*Math.sin(2*(2*Math.PI)/3);

drawSegment(level,x0,y0,x1,y1,g);
drawSegment(level,x1,y1,x2,y2,g);
drawSegment(level,x2,y2,x0,y0,g);
}

public static void main(String[] arg)
{
JFrame frame = new JFrame("Koch Snowflake");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.getContentPane().add(new Snowflake());
frame.pack();
frame.setVisible(true);
}
}
