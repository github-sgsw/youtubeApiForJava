import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class CreateLogFile implements AutoCloseable {
    private static final int FLUSH_THRESHOLD = 50;
    private static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("yy-M-dd");
    private static final DateTimeFormatter LOG_DATE_TIME_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final BufferedWriter bw;
    private final List<BufferedComment> commentBuffer = new ArrayList<>();
    private boolean closed = false;
    private int writtenCount = 0;

    CreateLogFile() throws IOException {
        Path directory = Paths.get("logs");
        Files.createDirectories(directory);

        Path file = directory.resolve(LocalDateTime.now().format(FILE_DATE_FORMAT) + "_配信コメント.csv");
        bw = Files.newBufferedWriter(file, StandardCharsets.UTF_8);
        bw.write("取得日時,アカウント名,コメント");
        bw.newLine();
        System.out.println("log file --> " + file.toAbsolutePath());
    }

    public synchronized void append(CommentDetailsModel commentDetailsModel) throws IOException {
        if (closed) {
            throw new IOException("ログファイルは既に閉じられています");
        }

        commentBuffer.add(new BufferedComment(LocalDateTime.now(), commentDetailsModel));
        if (commentBuffer.size() >= FLUSH_THRESHOLD) {
            flush();
        }
    }

    public synchronized void flush() throws IOException {
        if (commentBuffer.isEmpty()) {
            return;
        }

        for (BufferedComment bufferedComment : commentBuffer) {
            writeComment(bufferedComment);
        }
        writtenCount += commentBuffer.size();
        commentBuffer.clear();
        bw.flush();
    }

    @Override
    public synchronized void close() throws IOException {
        if (closed) {
            return;
        }

        try {
            flush();
        } finally {
            closed = true;
            bw.close();
            System.out.println("write comment count --> " + writtenCount);
        }
    }

    private void writeComment(BufferedComment bufferedComment) throws IOException {
        CommentDetailsModel commentDetailsModel = bufferedComment.commentDetailsModel();
        bw.write(escapeCsv(bufferedComment.fetchedAt().format(LOG_DATE_TIME_FORMAT)));
        bw.write(",");
        bw.write(escapeCsv(getDisplayName(commentDetailsModel)));
        bw.write(",");
        bw.write(escapeCsv(getDisplayMessage(commentDetailsModel)));
        bw.newLine();
    }

    private String getDisplayName(CommentDetailsModel commentDetailsModel) {
        return Optional.of(commentDetailsModel).filter(c -> c.getAuthorDetailsModel() != null)
                .map(c -> c.getAuthorDetailsModel().getDisplayName())
                .orElse("");
    }

    private String getDisplayMessage(CommentDetailsModel commentDetailsModel) {
        return Optional.of(commentDetailsModel).filter(c -> c.getSnippet() != null)
                .map(c -> commentDetailsModel.getSnippet().getDisplayMessage())
                .orElse("");
    }

    private String escapeCsv(String value) {
        return Optional.ofNullable(value)
                .map(v -> v.replace("\"", "\"\""))
                .map(v -> v.contains(",") || v.contains("\"") || v.contains("\n") || v.contains("\r") ? "\"" + v + "\"" : v)
                .orElse("");
    }

    private record BufferedComment(LocalDateTime fetchedAt, CommentDetailsModel commentDetailsModel) {}
}
