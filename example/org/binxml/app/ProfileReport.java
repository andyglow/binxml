package org.binxml.app;

import java.util.ArrayList;

import org.binxml.util.DebugUtil;

/**
 * @author andy
 * @creationDate on 23.07.2004
 */
public class ProfileReport {

    private long fileSize;

    private ArrayList phases = new ArrayList();

    public Phase newPhase() {
        Phase p = new Phase();
        this.phases.add(p);
        return p;
    }

    public Record newRecord() {
        Record p = new Record();
        this.phases.add(p);
        return p;
    }

    class Record {

        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String toString() {
            return "<tr><td colspan=4>"+text+"</td></tr>";
        }
    }

    class Phase {

        public static final int JAXP = 0;

        public static final int BINXML = 1;

        private int responsibleApi;

        private long lapsedLime;

        public long getLapsedLime() {
            return lapsedLime;
        }

        public void setLapsedLime(long lapsedLime) {
            this.lapsedLime = lapsedLime;
        }

        private String name;

        private Object statistic;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getResponsibleApi() {
            return responsibleApi;
        }

        public void setResponsibleApi(int responsibleApi) {
            this.responsibleApi = responsibleApi;
        }

        public Object getStatistic() {
            return statistic;
        }

        public void setStatistic(Object statistic) {
            this.statistic = statistic;
        }

        private String getAPIName() {
            return responsibleApi == JAXP ? "JAXP" : "BINXML";
        }

        public String toString() {
            StringBuffer sb = new StringBuffer("<tr valign=top>");
            sb.append("<td>").append(getAPIName()).append("</td>");
            sb.append("<td>").append(getName()).append("</td>");
            sb.append("<td><small>").append(DebugUtil.formatDuration(getLapsedLime()))
                    .append(" s</small></td>");
            sb.append("<td>").append(
                    statistic == null ? "-" : statistic.toString()).append(
                    "</td>");
            return sb.append("</tr>").toString();
        }
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<html>");
        sb.append("<head>");
        sb.append("<title>BIN XML Profiling report</title>");
        sb.append("<style type=\"text/css\">");
        sb.append("<!--");
        sb.append("body { font-family: Georgia, \"Times New Roman\", Times, serif; }");
        sb.append("h1 { color: #333333; }");
        sb.append("th { text-align: left; background-color: #f0f0f0; }");
        sb.append("td { padding-right: 60px; border-bottom: 1px solid #f0f0f0; }");
        sb.append("small { font-family: Verdana, Arial, Helvetica; }");
        sb.append("-->");
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<h1>BIN XML Profiling report</h1>");
        sb.append("<div>Input file size: ").append(getFileSize()).append(" byte</div>");
        sb.append("<table cellpadding=4 cellspacing=0 border=0>");
        sb.append("<tr>");
        sb.append("<th nowrap>API</th>");
        sb.append("<th nowrap>Phase</th>");
        sb.append("<th nowrap>Duration</th>");
        sb.append("<th nowrap>Statistics</th>");
        sb.append("</tr>");
        for (int i = 0; i < phases.size(); i++) {
            sb.append(phases.get(i).toString());
        }
        return sb.append("</table></body></html>").toString();
    }
}