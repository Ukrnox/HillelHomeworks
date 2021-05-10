package my.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Queue;

public class MeetingPlanner extends HttpServlet
{
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Queue<String> locationsList = new ArrayDeque<>();
        for (int i = 1; i < 5; i++)
        {
            String temp = request.getParameter("Location" + i);
            if (!temp.equals("NA"))
            {
                HttpSession session = request.getSession(true);
                session.removeAttribute("massageIfAllNA");
                locationsList.add(request.getParameter("Location" + i));
            }
        }
        if (locationsList.isEmpty())
        {
            HttpSession session = request.getSession(true);
            session.removeAttribute("errorMassage");
            restart(request, response, "massageIfAllNA", "Make a choice!");
        }

        int day = Integer.parseInt(request.getParameter("day"));
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));
        int intervalInMin = Integer.parseInt(request.getParameter("intervalInMin"));

        try (PrintWriter writer = response.getWriter())
        {
            writer.println("<head>");
            writer.println("\t<meta charset='UTF-8'>");
            writer.println("\t<title>MeetingPlanner</title>");
            writer.println("<style>");
            writer.println("BODY\n{");
            writer.println("background: #c7b39b url(resources/img/1.png);\n}");
            writer.println("</style>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<table border='1' align='center'>" + "<caption>" + day + "." + month + "." + year + "</caption>");
            writer.println("<tr >");
            writer.println("<th>" + "UTC" + "</th>");
            for (String location : locationsList)
            {
                writer.println("<th>" + location + "</th>\n");
            }
            writer.println("</tr>\n");
            try
            {
                writer.println(meetingPlanner(year, month, day, intervalInMin, locationsList));
            }
            catch (DateTimeException e)
            {
                HttpSession session = request.getSession(true);
                if (session.getAttribute("massageIfAllNA") == null)
                {
                    restart(request, response, "errorMassage", e.getMessage());
                }
            }
            writer.println("</table>");
            writer.println("</table>");
            writer.println("<form action='/index.jsp' method='POST'>");
            writer.println("<p align='center'>");
            writer.println("<input type='submit' value='Restart' >");
            writer.println("</p");
            writer.println("</form>");
            writer.println("</body>");
        }
    }

    private void restart(HttpServletRequest request, HttpServletResponse response,
                         String attributeName, String attributeText) throws ServletException, IOException
    {
        HttpSession session = request.getSession(true);
        session.setAttribute(attributeName, attributeText);
        String path = "/index.jsp";
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }


    private ZonedDateTime neededZoneDate(int year, int month, int dayOfMonth, int minutes, ZoneId neededZone)
    {
        int hour = 0;
        if (minutes >= 60)
        {
            hour = minutes / 60;
            minutes = minutes % 60;
        }
        ZonedDateTime dateTimeUTC = ZonedDateTime.of(year, month, dayOfMonth, hour, minutes, 0, 0, ZoneOffset.UTC);
        return dateTimeUTC.withZoneSameInstant(neededZone);
    }

    private String checkColor(int checkHour)
    {
        String red = "style='background-color: rgb(190,0,0)'>";
        String nearGreen = "style='background-color: rgb(190, 255, 0)'>";
        String nearBlue = "style='background-color: rgb(190, 255, 241)'>";
        String result;
        if (checkHour <= 18 && checkHour >= 7)
        {
            result = nearBlue;
        }
        else
        {
            result = red;
        }
        if (checkHour <= 17 && checkHour >= 8)
        {
            result = nearGreen;
        }
        return result;
    }

    private String meetingPlanner(int year, int month, int dayOfMonth, int intervalInMin,
                                  Queue<String> locationsList)
    {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 1440; i += intervalInMin)
        {
            ZonedDateTime uTCDateTime = neededZoneDate(year, month, dayOfMonth, i, ZoneOffset.UTC);
            result.append("<tr>");
            result.append("<td align='center'").append("style='background-color: rgb(162, 163, 190)'>")
                    .append(uTCDateTime.format(dateTimeFormatter)).append("</td>");
            for (String location : locationsList)
            {
                ZoneId zoneId = ZoneId.of(location);
                ZonedDateTime zoneDateTimeDateTime = neededZoneDate(year, month, dayOfMonth, i, zoneId);
                String colorZoneId = checkColor(zoneDateTimeDateTime.getHour());
                result.append("<td align='center'").append(colorZoneId)
                        .append(zoneDateTimeDateTime.format(dateTimeFormatter)).append("</td>");
            }
            result.append("</tr>");
        }
        return result.toString();
    }
}