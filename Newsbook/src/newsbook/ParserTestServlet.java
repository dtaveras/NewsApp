package newsbook;

import java.io.IOException;


import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.util.Vector;
import java.util.LinkedList;
import java.util.Enumeration;

import newsbook.*;
import newsbook.parsers.DiarioParser;
import newsbook.parsers.LoteriaNacional;
import newsbook.parsers.NewsObject;
import newsbook.parsers.LoteriaNacional.LotteryType;
import newsbook.parsers.AbstractNewsParser.NewsSection;

@SuppressWarnings("serial")
public class ParserTestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		System.out.println("Running ParserTest Servlet");
		req.setAttribute("title", "News Jsp Tester");

		String testOption = req.getParameter("TestOption");
		if (!testOption.equals("Lottery")) {
			DiarioParser elDiario = new DiarioParser();
			LinkedList<NewsObject> newsList = null;
			int successRes = -1;
			
			switch (testOption) {
			case "Education":
				successRes = elDiario.fillSection("Educacion");
				if (successRes != 1) {
					System.out.println("Failed to Fill EducationNewsList");
				}
				newsList = (LinkedList<NewsObject>) elDiario.getSection("Educacion").newsList;
				System.out.println("Education");

				break;
			case "National":
				successRes = elDiario.fillSection("Nacionales");
				if (successRes != 1) {
					System.out.println("Failed to Fill NationalesNewsList");
				}
				newsList = (LinkedList<NewsObject>) elDiario.getSection("Nacionales").newsList;
				System.out.println("National");

				break;
			default:
				System.out.println("Default");
			}

			if (successRes == 1){
				req.setAttribute("NewsList", newsList);
			}
			else {
				req.setAttribute("NewsList", null);
			}
		} else {
			System.out.println("Lottery Page");
			LoteriaNacional loteriaNacional = new LoteriaNacional();
			// LNS1, LNS2, QP, RL
			Vector<String> lns1_num = loteriaNacional
					.getLotteryNumbers(LotteryType.LOTERIA_NATIONAL_SORTEO_1);
			String lns1_time = loteriaNacional
					.getLotteryTime(LotteryType.LOTERIA_NATIONAL_SORTEO_1);
			String lns1_date = loteriaNacional
					.getLotteryDate(LotteryType.LOTERIA_NATIONAL_SORTEO_1);

			Vector<String> lns2_num = loteriaNacional
					.getLotteryNumbers(LotteryType.LOTERIA_NATIONAL_SORTEO_2);
			String lns2_time = loteriaNacional
					.getLotteryTime(LotteryType.LOTERIA_NATIONAL_SORTEO_2);
			String lns2_date = loteriaNacional
					.getLotteryDate(LotteryType.LOTERIA_NATIONAL_SORTEO_2);

			Vector<String> qp_num = loteriaNacional
					.getLotteryNumbers(LotteryType.QUINIELA_PALE);
			String qp_time = loteriaNacional
					.getLotteryTime(LotteryType.QUINIELA_PALE);
			String qp_date = loteriaNacional
					.getLotteryDate(LotteryType.QUINIELA_PALE);

			Vector<String> pgm_num = loteriaNacional
					.getLotteryNumbers(LotteryType.PEGA_MAS);
			String pgm_time = loteriaNacional
					.getLotteryTime(LotteryType.PEGA_MAS);
			String pgm_date = loteriaNacional
					.getLotteryDate(LotteryType.PEGA_MAS);

			req.setAttribute("lns1_num", lns1_num);
			req.setAttribute("lns1_time", lns1_time);
			req.setAttribute("lns1_date", lns1_date);

			req.setAttribute("lns2_num", lns2_num);
			req.setAttribute("lns2_time", lns2_time);
			req.setAttribute("lns2_date", lns2_date);

			req.setAttribute("qp_num", qp_num);
			req.setAttribute("qp_time", qp_time);
			req.setAttribute("qp_date", qp_date);

			req.setAttribute("pgm_num", pgm_num);
			req.setAttribute("pgm_time", pgm_time);
			req.setAttribute("pgm_date", pgm_date);

			RequestDispatcher reqDispatcher = getServletConfig()
					.getServletContext().getRequestDispatcher(
							"/DominicanaNews/Tests/loteriatest.jsp");
			reqDispatcher.forward(req, resp);
			return;
		}

		RequestDispatcher reqDispatcher = getServletConfig()
				.getServletContext().getRequestDispatcher(
						"/DominicanaNews/Tests/parsertest.jsp");
		reqDispatcher.forward(req, resp);
		return;
	}
}