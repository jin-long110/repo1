package com.qingniao.partal;


//用户登陆

import com.core.common.Constants;
import com.core.common.LocalSessionProvider;
import com.core.pojo.user.User;
import com.core.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
public class LoginController {

    @Autowired
    LocalSessionProvider localSessionProvider;

    @Autowired
    LoginService loginService;

    //处理GET请求
    @RequestMapping(value = "/login.html",method = RequestMethod.GET)
    public String login(Model model,String url){
        model.addAttribute("url",url);
return "buyer/login";
    }

    //处理POST请求
    @RequestMapping(value = "/login.html",method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model,String username,String password,String code,String url){
        //去判断验证码是否输入
        if (code!=null){
            String scode = localSessionProvider.getAttribute(request, response, Constants.USER_CODE);
            if (scode.toLowerCase().equals(code.toLowerCase())){
                //判断用户名和密码
                if (username!=null&&username.trim().length()>0&&password!=null&&password.trim().length()>0){
                    //查询数据库
                    User cuser = loginService.getUserByUsernameAndPassword(username, password);
                    if (cuser!=null){
                        localSessionProvider.setAttribute(request,response,Constants.USER_NAME,cuser.getUsername());
                        //跳转到点击登陆页面
                        System.out.println(url);
                        model.addAttribute("username",username);
                        return "redirect:"+url;
                    }else {
                        model.addAttribute("error","账号或者密码错误");
                    }
                }else {
                    model.addAttribute("error","账号或者密码不存在");
                }
            }else {
                model.addAttribute("error","验证码输入错误");
            }
        }else {
            model.addAttribute("error","验证码不存在");
        }
        return "buyer/login";
    }

    //验证码
    // 生成验证码
    @RequestMapping(value = "/login/getCode.html")
    public void getCode(HttpServletRequest request, HttpServletResponse response){

            BufferedImage img = new BufferedImage(68, 22, BufferedImage.TYPE_INT_RGB);
            Graphics g = img.getGraphics();

            Random r = new Random();

            Color c = new Color(200, 150, 255);

            g.setColor(c);

            g.fillRect(0, 0, 68, 22);

            StringBuffer sb = new StringBuffer();

            char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

            int index, len = ch.length;

            for (int i = 0; i < 4; i++) {

                index = r.nextInt(len);

                g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt

                        (255)));

                g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));

                g.drawString("" + ch[index], (i * 15) + 3, 18);

                sb.append(ch[index]);

            }
            // 把上面生成的验证码放到Session域中
            localSessionProvider.setAttribute(request, response, Constants.USER_CODE,sb.toString());
            try {
                ImageIO.write(img, "JPG", response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
