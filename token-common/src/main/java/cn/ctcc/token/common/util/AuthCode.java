package cn.ctcc.token.common.util;/*
 * Copyright(C) 2000-2011 THS Technology Limited Company, http://www.ths.com.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless requestuired by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * @Author: zk
 * @Date: 2019/2/27 16:53
 * @Description: 验证码生成
 * @Modified:
 * @version: V1.0
 */
public class AuthCode {

	public static void createAuthCode(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//生成随机数
		String authCode = UUID.randomUUID().toString().substring(0, 4);
		//把随机数放入Session中,并设置90秒后失效
		HttpSession session = request.getSession();
		//session.setAttribute(SystemConstant.AUTHCODE_IN_SESSION, authCode);
        //设置session的过期时间
		//90秒
        Long t=System.currentTimeMillis()+90*1000;
        //session.setAttribute(SystemConstant.AUTHCODE_IN_SESSION_TIMEOUT,t);

        //不能设置session的过期时间，这样会导致90秒后用户重新登录
        //session.setMaxInactiveInterval(90);

		//创建图片对象
		int width = 100;
		int height = 40;
		int imageType = BufferedImage.TYPE_INT_RGB;
		BufferedImage image = new BufferedImage(width, height, imageType);
		//画板
		Graphics g = image.getGraphics();
		g.setColor(new Color(0x79C7EB));
		//绘制一个实心的矩形
		g.fillRect(1, 1, width - 2, height - 2);
		// 把随机数画进图片中,设置随机数的颜色
		g.setColor(Color.RED);
		Font font = new Font("宋体", Font.BOLD + Font.ITALIC, 30);
		//设置随机数的字体和大小
		g.setFont(font);
		g.drawString(authCode, 10, 28);
		//干扰线
		g.setColor(Color.GRAY);
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			g.fillRect(r.nextInt(width), r.nextInt(height), 2, 2);
		}
		//关闭
		g.dispose();
		//把图片对象以流的方式保存出去
		ImageIO.write(image, "jpg", response.getOutputStream());
	}

}
