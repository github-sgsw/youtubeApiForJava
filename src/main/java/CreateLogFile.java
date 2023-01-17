import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CreateLogFile {
    private ArrayList<CommentDetailsModel> cdmList;

    CreateLogFile(ArrayList<CommentDetailsModel> cdmList) {
        this.cdmList = cdmList;
    }

    /**
     * Map生成するときにreplaseAllかまして名前とコメントにあるコンマ消す
     * if, else、containsKeyでうんぬんかんぬんやってたけどたった4行でできた
     * コメント書くだけ書いてreplace実装していなかった;;
     * 他の機能もろもろ追加したらcsvべた書きじゃなくてライブラリ使ってやる
     */
    Collector<CommentDetailsModel, ?, Map<String, List<String>>> toMap = Collectors.groupingBy(cdModel ->
            cdModel.getAuthorDetailsModel().getDisplayName().replaceAll(",", ""),
            HashMap::new,
            Collectors.mapping(cdModel -> cdModel.getSnippet().getDisplayMessage().replaceAll(",", ""), Collectors.toList())
    );

    public Map<String, List<String> > createLogMap() {
        return cdmList.stream().collect(toMap);
    }

    public void createCsvFile(Map<String, List<String>> logMap) {
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