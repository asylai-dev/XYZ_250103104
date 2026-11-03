package task1;
public class JsonFormatter implements ReportFormatter {
    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }
    @Override public String format(String title, String[] headers, String[][] rows) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"report\": \"").append(escape(title)).append("\",\n  \"records\": [\n");
        for (int i = 0; i < rows.length; i++) {
            sb.append("    {");
            for (int j = 0; j < headers.length; j++) {
                if (j > 0) sb.append(", ");
                sb.append('"').append(escape(headers[j])).append("\": \"").append(escape(rows[i][j])).append('"');
            }
            sb.append('}').append(i + 1 < rows.length ? ",\n" : "\n");
        }
        return sb.append("  ]\n}").toString();
    }
}
