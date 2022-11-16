import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class CreateLogFile {
    private ArrayList<CommentDetailsModel> cdmList;

    CreateLogFile(ArrayList<CommentDetailsModel> cdmList) {
        this.cdmList = cdmList;
    }
/*
    Consumer<CommentDetailsModel> toMap = i -> {
        String user = i.getAuthorDetailsModel().getDisplayName();
        String comment = i.getSnippet().getDisplayMessage();
        if (!logMap.containsKey(user)) {
            logMap.put(user.replaceAll(",", ""), new ArrayList<>());
        }
        logMap.get(user).add(comment.replaceAll(",", ""));
        System.out.println(logMap.keySet());
    };
*/
    public HashMap<String, ArrayList<String> > createLogMap() {
        HashMap<String, ArrayList<String> > logMap = new HashMap<>();

        cdmList.forEach(i -> {
            var user = Optional.ofNullable(i.getAuthorDetailsModel().getDisplayName());
            var comment = Optional.ofNullable(i.getSnippet().getDisplayMessage());

            if (!logMap.containsKey(user.get())) {
                logMap.put(user.get().replaceAll(",", ""), new ArrayList<>());
            }
            logMap.get(user).add(comment.orElse("deleteComment??").replaceAll(",", ""));
        });

        return logMap;
    }

    public void createCsvFile(HashMap<String, ArrayList<String>> logMap) {
        File file = new File("C:\\Users\\p20pr\\Documents\\配信ログ\\" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-M-dd")) + "_配信コメント.csv");
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write("視聴者名,コメント一覧");
            bw.newLine();
            logMap.forEach((key, value) -> {
                try {
                    bw.write(key);
                    value.forEach(comment -> {
                        try { bw.write("," + comment); } catch (IOException e) { e.printStackTrace(); }
                    });
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
