package org.javaboy.vhr.config;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class VerificationCode {

    private int width=100;
    private int height=35;
    private String[] fontNames={"宋体","楷体","隶书","微软雅黑"};
    private Color bgColor=new Color(255,255,255);
    private Random random=new Random();
    private String codes="0123456789abcdefghijklmnopqrstuvwsyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String text;

    //获取一个随机颜色
    private  Color randomColor(){
        int red=random.nextInt(200);
        int green=random.nextInt(200);
        int blue=random.nextInt(200);
        Color color=new Color(red,green,blue);
        return color;
    }

    //获取一个随机字体
    private Font randomFont(){
        if(fontNames.length>0){
            String fontName=fontNames[random.nextInt(fontNames.length)];
            int style=random.nextInt(4);
            int size=random.nextInt(4)+20;
            Font font=new Font(fontName,style,size);

            return font;
        }

        return new Font("微软雅黑",Font.PLAIN,20);
    }


    //获取随机字符
    private char randomChar(){
        return codes.charAt(random.nextInt(codes.length()));

    }

    //创建空白BufferImage对象
    private BufferedImage getBi(){

        BufferedImage image= new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D gp2=(Graphics2D)image.getGraphics();
        gp2.setBackground(bgColor);
        gp2.fillRect(0,0,width,height);
        return image;
    }

    public BufferedImage getImage(){
        BufferedImage image=getBi();
        Graphics2D gp2=(Graphics2D) image.getGraphics();
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < 4; i++) {
            String s=randomChar()+"";
            sb.append(s);
            gp2.setColor(randomColor());
            gp2.setFont(randomFont());
            float x=i * width * 1.0f / 4;
            gp2.drawString(s,x,height-7);

        }
        this.text=sb.toString();
        drawLine(image);
        return image;
    }

    //绘制干扰线
    private void drawLine(BufferedImage image){
        Graphics2D gp2=(Graphics2D)image.getGraphics();
        int num=5;
        for (int i = 0; i < num; i++) {
            int x1=random.nextInt(width);
            int y1=random.nextInt(height);
            int x2=random.nextInt(width);
            int y2=random.nextInt(height);
            gp2.setStroke(new BasicStroke(1.3f));
            gp2.setColor(randomColor());
            gp2.drawLine(x1,y1,x2,y2);
        }

    }


    public String getText(){
        return text;
    }

    public static void output(BufferedImage image, OutputStream out) throws IOException {
        ImageIO.write(image,"JPEG",out);
    }


}
