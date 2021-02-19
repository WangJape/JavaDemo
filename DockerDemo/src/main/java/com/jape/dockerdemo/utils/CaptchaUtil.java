package com.jape.dockerdemo.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

/**
 *
 * @模块名：taf
 * @包名：pers.cc.taf.io.img
 * @类名称： CaptchaUtil
 * @类描述：【类描述】图片验证码工具类
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2018年9月11日上午10:06:40
 */
public class CaptchaUtil {
    /**
     * 图片的宽度
     */
    private int width = 160;

    /**
     * 图片的高度
     */
    private int height = 40;

    /**
     * 验证码字符个数
     */
    private int codeCount = 6;

    /**
     * 验证码干扰线数
     */
    private int lineCount = 150;

    /**
     * 验证码
     */
    private String code = null;

    /**
     * 验证码图片Buffer
     */
    private BufferedImage buffImg = null;

    /**
     * 验证码范围,去掉0(数字)和O(拼音)容易混淆的(小写的1和L也可以去掉,大写不用了)
     */
    private char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     * 默认构造函数,设置默认参数
     */
    public CaptchaUtil() {
        this.createCode();
    }

    /**
     * 构造函数
     *
     * @param width 图片宽
     * @param height 图片高
     */
    public CaptchaUtil(int width, int height) {
        this.width = width;
        this.height = height;
        this.createCode();
    }

    /**
     * 构造函数
     *
     * @param width 图片宽
     * @param height 图片高
     * @param codeCount 字符个数
     * @param lineCount 干扰线条数
     */
    public CaptchaUtil(int width, int height, int codeCount, int lineCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        this.createCode();
    }

    /**
     *
     * @方法名：createCode
     * @方法描述【方法功能描述】生成验证码
     * @修改描述【修改描述】
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2018年9月11日 上午10:10:35
     * @修改人：cc
     * @修改时间：2018年9月11日 上午10:10:35
     */
    public void createCode() {
        int x = 0, fontHeight = 0, codeY = 0;
        int red = 0, green = 0, blue = 0;

        x = width / (codeCount + 2);// 每个字符的宽度(左右各空出一个字符)
        fontHeight = height - 2;// 字体的高度
        codeY = height - 4;

        // 图像buffer
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 生成随机数
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体,可以修改为其它的
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
        // Font font = new Font("Times New Roman", Font.ROMAN_BASELINE, fontHeight);
        g.setFont(font);

        for (int i = 0; i < lineCount; i++) {
            // 设置随机开始和结束坐标
            int xs = random.nextInt(width);// x坐标开始
            int ys = random.nextInt(height);// y坐标开始
            int xe = xs + random.nextInt(width / 8);// x坐标结束
            int ye = ys + random.nextInt(height / 8);// y坐标结束

            // 产生随机的颜色值，让输出的每个干扰线的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }

        // randomCode记录随机产生的验证码
        StringBuffer randomCode = new StringBuffer();
        // 随机产生codeCount个字符的验证码。
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。
        code = randomCode.toString();
    }

    /**
     *
     * @方法名：write
     * @方法描述【方法功能描述】将验证码图片写入指定路径
     * @param path
     * @throws IOException
     * @修改描述【修改描述】
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2018年9月11日 上午10:10:55
     * @修改人：cc
     * @修改时间：2018年9月11日 上午10:10:55
     */
    public void write(String path) throws IOException {
        OutputStream sos = new FileOutputStream(path);
        this.write(sos);
    }

    /**
     *
     * @方法名：write
     * @方法描述【方法功能描述】将验证码图片写入指定输出流
     * @param sos
     * @throws IOException
     * @修改描述【修改描述】
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2018年9月11日 上午10:11:30
     * @修改人：cc
     * @修改时间：2018年9月11日 上午10:11:30
     */
    public void write(OutputStream sos) throws IOException {
        ImageIO.write(buffImg, "png", sos);
        sos.close();
    }

    /**
     *
     * @方法名：getBuffImg
     * @方法描述【方法功能描述】获取验证码图片
     * @return 验证码图片BufferedImage
     * @修改描述【修改描述】
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2018年9月11日 上午10:12:08
     * @修改人：cc
     * @修改时间：2018年9月11日 上午10:12:08
     */
    public BufferedImage getBuffImg() {
        return buffImg;
    }

    /**
     *
     * @方法名：getCode
     * @方法描述【方法功能描述】获取验证码
     * @return 验证码
     * @修改描述【修改描述】
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2018年9月11日 上午10:12:44
     * @修改人：cc
     * @修改时间：2018年9月11日 上午10:12:44
     */
    public String getCode() {
        return code;
    }

    public static void main(String[] args) {
        CaptchaUtil vCode = new CaptchaUtil(160, 40, 5, 150);
        try {
            String path = "D://a/" + new Date().getTime() + ".png";
            System.out.println(vCode.getCode() + " >" + path);
            vCode.write(path);

            // ValidateCode vCode = new ValidateCode(120,40,5,100);
            // session.setAttribute("code", vCode.getCode());
            // vCode.write(response.getOutputStream());

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
