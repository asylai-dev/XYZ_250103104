package task1;
public class CsvFormatter implements ReportFormatter {
    private static String quote(String value) { return "\"" + value.replace("\"", "\"\"") + "\""; }
    @Override public String format(String title, String[] headers, String[][] rows) {
        StringBuilder sb = new StringBuilder("# TITLE: ").append(title).append('\n');
        for (int j = 0; j < headers.length; j++) { if (j > 0) sb.append(','); sb.append(quote(headers[j])); }
        sb.append('\n');
        for (String[] row : rows) {
            for (int j = 0; j < headers.length; j++) { if (j > 0) sb.append(','); sb.append(quote(row[j])); }
            sb.append('\n');
        }
        return sb.toString();
    }
}
